<template>
  <div class="coze-image-container" id="coze-image-container"></div>
  <div class="coze-box">
    <div class="coze-mood-list">
      <div :key="item.uid" v-for="(item,index) in talkArr" class="coze-enter-animate">
        <div class="coze-mood-control" v-if="item.show">
          <mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
                     @mood-edit="moodEdit" :user-uid="userUid" :mood-item="item">
            <slot name="coze-mood-content-par"></slot>
            <template #coze-mood-content>
              <slot name="coze-mood-content"></slot>
            </template>

            <slot name="coze-img-par"></slot>
            <template #coze-img>
              <slot name="coze-img"></slot>
            </template>

            <slot name="coze-mood-bottom-left-par"></slot>
            <template #coze-mood-bottom-left>
              <slot name="coze-mood-bottom-left"></slot>
            </template>

            <slot name="coze-mood-bottom-right-par"></slot>
            <template #coze-mood-bottom-right>
              <slot name="coze-mood-bottom-right"></slot>
            </template>
          </mood-item>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
//导入配置属性
import {defineComponent} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {talkApi} from "@/service";

const talkArr: Array<TalkVo> = []
export default defineComponent({
  name: 'Mood',
	props: {
		userUid: {
			type: String
		}
	},
  data() {
    return {
      //这是一个数组对象
      color: '',
			talkArr,
      showMoodEdit: true,
      openEditStatus: false,
      num: 1,
      currentMoodObj: {},
      verifyIdentifyStatus: false,
      showMoodControl: false
    }
  },
  emits: ['cozeSuccess','moodEdit','cozeCancelEdit'],
  created() {
    talkApi.queryListDataByCondition({delete: false, show: true, otherUid: this.userUid,pageSize: 300}).then(result => {
			if (result.data && result.data.result) {
				this.talkArr = result.data.result
			}
		})
  },
  methods: {
    moodComment(moodItem) {

    },
    moodLove(moodItem) {

    },
    moodPoster(moodItem) {

    },
    moodEdit(moodItem) {
      this.currentMoodObj = moodItem
      this.openEditStatus = !this.openEditStatus
      if (this.verifyIdentifyStatus) {
        //密码验证成功，直接显示
        this.showMoodControl = true
      }else {
        this.showMoodControl = true
      }
      this.$emit("moodEdit",{
        openEditStatus: this.openEditStatus
      })
    }
  },
})
</script>
<style lang="css">
/*@import "../public/font/iconfont.css";*/
</style>
