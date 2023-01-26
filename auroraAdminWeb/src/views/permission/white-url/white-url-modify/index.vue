<template>
	<div>
		<n-drawer :width="502" :native-scrollbar="true" v-model:show="showDrawer" placement="left">
			<n-drawer-content :title="!addStatus ? `编辑 ${modifyWhiteUrlInfo.url}` : '新增白名单'">
				<n-space vertical hoverable>
					<n-card hoverable class="rounded-16px shadow-sm" size="small">
						<n-form ref="addUserFormRef" :model="modifyWhiteUrlInfo">
							<n-form-item path="password" label="白名单地址">
								<n-input
									:round="true"
									v-model:value="modifyWhiteUrlInfo.url"
									type="text"
									@keydown.enter.prevent
								/>
							</n-form-item>
						</n-form>
						<n-row :gutter="[0, 24]">
							<n-col :span="24">
								<div style="display: flex; justify-content: flex-end">
									<n-button round type="primary" @click="handleClickModifyAction">
										{{ !addStatus ? '更新' : '添加' }}
									</n-button>
								</div>
							</n-col>
						</n-row>
					</n-card>
				</n-space>
			</n-drawer-content>
		</n-drawer>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import {EnumMittEventName} from "@/enum";
import {emitter, StringUtil} from "@/utils";
import {WhiteUrl} from "@/bean/pojo/admin/WhiteUrl";
import {whiteUrlApi} from "@/service";

defineComponent({name: 'index'});

// 定义data
const showDrawer = ref<boolean>(false)
const modifyWhiteUrlInfo = ref<WhiteUrl>({})
const addStatus = ref(false)

// 定义方法
const handleClickModifyAction = () => {
	if (!StringUtil.haveLength(modifyWhiteUrlInfo.value.url)) {
		window.$message?.error('白名单地址不能为空')
		return
	}
	if (!/^(DELETE|POST|GET|PUT):\/?/.test(modifyWhiteUrlInfo.value.url!)) {
		window.$message?.error('白名单地址不符合规范，格式必须为 Method:/Path')
		return;
	}
	if (addStatus.value) {
		whiteUrlApi.insertData(modifyWhiteUrlInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('插入白名单成功')
				emitter.emit(EnumMittEventName.reloadData)
				showDrawer.value = false
			}
		})
	}else {
		whiteUrlApi.updateData(modifyWhiteUrlInfo.value).then(result => {
			if (!result.error) {
				window.$message?.success('操作成功')
				showDrawer.value = false
				emitter.emit(EnumMittEventName.reloadData)
			}
		})
	}
}

// 监听mitt
onMounted(() => {
	emitter.on('whiteUrlManageAddWhiteUrlAction', e => {
		modifyWhiteUrlInfo.value = {}
		showDrawer.value = !showDrawer.value
		addStatus.value = true
	})
	emitter.on('whiteUrlManageModifyWhiteUrlAction', e => {
		showDrawer.value = !showDrawer.value
		addStatus.value = false
		if (e) {
			modifyWhiteUrlInfo.value = e as WhiteUrl
		}
	})
})

</script>

<style scoped>

</style>
