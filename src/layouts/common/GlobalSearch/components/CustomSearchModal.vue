<template>
  <n-modal
    v-model:show="show"
    :segmented="{ footer: 'soft' }"
    :closable="false"
    preset="card"
    footer-style="padding: 0; margin: 0"
    class="fixed left-0 right-0"
    :class="[isMobile ? 'wh-full top-0px rounded-0' : 'w-630px top-50px']"
    @after-leave="handleClose"
  >
    <n-card class="rounded-16px shadow-sm" size="small">
      <n-space vertical :size="25">
        <n-grid cols="4" item-responsive responsive="screen">
          <n-grid-item span="l:1">
            <n-tag checkable> Uid </n-tag>
          </n-grid-item>
          <n-grid-item span="l:3">
            <n-input
              ref="inputRef"
              v-model:value="condition.uid"
              round
              clearable
              placeholder="请输入Uid"
              @blur="validateUid"
            >
              <template #prefix>
                <icon-uil-search class="text-15px text-[#c2c2c2]" />
              </template>
            </n-input>
          </n-grid-item>
        </n-grid>

        <n-grid cols="4" item-responsive responsive="screen">
          <n-grid-item span="l:1">
            <n-tag checkable> 其他Uid </n-tag>
          </n-grid-item>
          <n-grid-item span="l:3">
            <n-input
              ref="inputRef"
              v-model:value="condition.otherUid"
              round
              clearable
              placeholder="请输入其他Uid"
              @blur="validateOtherUid"
            >
              <template #prefix>
                <icon-uil-search class="text-15px text-[#c2c2c2]" />
              </template>
            </n-input>
          </n-grid-item>
        </n-grid>

        <n-grid cols="4" item-responsive responsive="screen">
          <n-grid-item span="l:1">
            <n-tag checkable> 关键词 </n-tag>
          </n-grid-item>
          <n-grid-item span="l:3">
            <n-input ref="inputRef" v-model:value="condition.keyword" round clearable placeholder="请输入关键词">
              <template #prefix>
                <icon-uil-search class="text-15px text-[#c2c2c2]" />
              </template>
            </n-input>
          </n-grid-item>
        </n-grid>

        <n-date-picker
          v-model:value="startAndEndTime"
          type="datetimerange"
          :shortcuts="rangeShortcuts"
          @clear="clearDate"
          @update:show="handleSelectDate"
        />
      </n-space>

      <n-divider />
      <n-space vertical :size="20">
        <n-grid x-gap="12" :cols="3">
          <n-gi>
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <n-tag checkable>删除</n-tag>
              </n-gi>
              <n-gi>
                <n-switch v-model:value="condition.delete" />
              </n-gi>
            </n-grid>
          </n-gi>

          <n-gi>
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <n-tag checkable>展示</n-tag>
              </n-gi>
              <n-gi>
                <n-switch v-model:value="condition.show" />
              </n-gi>
            </n-grid>
          </n-gi>

          <n-gi>
            <n-grid x-gap="12" :cols="2">
              <n-gi>
                <n-tag checkable>状态</n-tag>
              </n-gi>
              <n-gi>
                <n-switch v-model:value="condition.status" />
              </n-gi>
            </n-grid>
          </n-gi>
        </n-grid>
        <!--<n-divider />-->

        <n-row :gutter="[0, 24]">
          <n-col :span="24">
            <div style="display: flex; justify-content: flex-end">
              <n-space justify="end">
								<n-button round type="warning" @click="handleResetSearchButton"> 重置 </n-button>
								<n-button round type="primary" @click="handleClickSearchButton"> 确定 </n-button>
							</n-space>
            </div>
          </n-col>
        </n-row>
      </n-space>
    </n-card>
    <template #footer>
      <search-footer v-if="!isMobile" />
    </template>
  </n-modal>
</template>

<script lang="ts" setup>
import {computed, nextTick, onBeforeMount, ref, watch} from 'vue';
import {onKeyStroke, useDebounceFn} from '@vueuse/core';
import {EnumMittEventName} from '@/enum';
import {useBasicLayout} from '@/composables';
import {emitter, formatTime} from '@/utils';
import type {Condition} from '@/theme/core/bean';
import SearchFooter from './SearchFooter.vue';

defineOptions({ name: 'SearchModal' });

interface Props {
  /** 弹窗显隐 */
  value: boolean;
}

const props = defineProps<Props>();

interface Emits {
  (e: 'update:value', val: boolean): void;
}

// 定义condition
const conditionInfo: Condition = {
  delete: false,
  status: true,
  show: true
};

const emit = defineEmits<Emits>();

const { isMobile } = useBasicLayout();
const inputRef = ref<HTMLInputElement>();
const condition = ref(conditionInfo);
const originCondition = ref<Condition>()
const startAndEndTime = ref();
const rangeShortcuts = ref({
  此刻: () => {
    const nowDate = new Date().getTime();
    return [nowDate - 1000 * 60 * 60 * 24, nowDate];
  },
  近五天: () => {
    const nowDate = new Date().getTime();
    return [nowDate - 1000 * 60 * 60 * 24 * 5, nowDate];
  }
});

// @ts-ignore
const handleSearch = useDebounceFn(search, 300);

const show = computed({
  get() {
    return props.value;
  },
  set(val: boolean) {
    emit('update:value', val);
  }
});

watch(show, async val => {
  if (val) {
    /** 自动聚焦 */
    await nextTick();
    inputRef.value?.focus();
  }
});

/** 验证输入的uid */
const validateUid = () => {
  const uidRegex = /^[0-9]+/;
  if (condition.value.uid !== null) {
    if (!uidRegex.test(`${condition.value.uid}`)) {
      condition.value.uid = null;
    }
  }
};

/** 验证其他uid字段 */
const validateOtherUid = () => {
  const uidRegex = /^[0-9]+/;
  if (condition.value.otherUid !== null) {
    if (!uidRegex.test(`${condition.value.otherUid}`)) {
      condition.value.otherUid = null;
    }
  }
};

/** 清除选择的时间 */
const clearDate = () => {
  condition.value.startTime = null;
  condition.value.endTime = null;
};

/** 处理选择的时间 */
const handleSelectDate = (showControl: boolean) => {
  if (!showControl) {
    if (startAndEndTime.value !== null) {
      condition.value.startTime = formatTime(startAndEndTime.value[0]);
      condition.value.endTime = formatTime(startAndEndTime.value[1]);
    }
  }
};

/** 处理点击确定之后的逻辑 */
const handleClickSearchButton = () => {
	if (condition.value.keyword === '') {
		condition.value.keyword = null;
	}
	if (condition.value.otherUid === '') {
		condition.value.otherUid = null;
	}
	if (condition.value.uid === '') {
		condition.value.uid = null;
	}
	// 将当前的condition发送到当前使用的页面
	emitter.emit(EnumMittEventName.globalSearchCondition, condition.value);
	handleClose();
};

/** 处理点击重置之后的逻辑 */
const handleResetSearchButton = () => {
	if (originCondition.value) {
		condition.value = originCondition.value
		if (!originCondition.value?.delete) condition.value.delete = false
		if (!originCondition.value?.show) condition.value.show = false
		if (!originCondition.value?.status) condition.value.status = false
		// 将当前的condition发送到当前使用的页面
		emitter.emit(EnumMittEventName.resetGlobalSearchCondition, originCondition.value);
	}
	handleClose();
}

/** 查询 */
function search() {}

function handleClose() {
  show.value = false;
}

/** key up */
function handleUp() {}

/** key down */
function handleDown() {}

// TODO 这里有个bug，在编辑文章的时候，只要按下回车，就会发送请求
/** key enter */
function handleEnter() {
  // handleClickSearchButton();
}

onKeyStroke('Escape', handleClose);
onKeyStroke('Enter', handleEnter);
onKeyStroke('ArrowUp', handleUp);
onKeyStroke('ArrowDown', handleDown);

onBeforeMount(() => {
	emitter.on(EnumMittEventName.resetGlobalSearchCondition, e => {
		if (e) {
			originCondition.value = e as Condition
			if (!originCondition.value?.delete) condition.value.delete = false
			if (!originCondition.value?.show) condition.value.show = false
			if (!originCondition.value?.status) condition.value.status = false
		}
	})
})
</script>

<style lang="scss" scoped></style>
