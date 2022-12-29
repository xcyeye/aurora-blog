<template>
	<div class="wh-full">
		<n-card class="h-full shadow-sm rounded-16px">
			<n-tabs type="line" animated>
				<n-tab-pane name="list" tab="列表">
					Hey Jude
				</n-tab-pane>
				<n-tab-pane name="scan" tab="扫描">
					<n-transfer
						ref="transfer"
						v-model:value="transferInterfaceArr"
						:options="transferInterfaceOptions"
						:render-source-list="renderSourceList"
						source-filterable
					/>
				</n-tab-pane>
				<n-tab-pane name="control" tab="手动添加">
					Hey Jude
				</n-tab-pane>
				<template #suffix>
					<n-space justify="end">
						<n-button strong secondary tertiary round type="info" @click="handleScanAllInterfaceAction">扫描</n-button>
						<n-button strong secondary tertiary round type="success" @click="handleScanAddInterfaceAction">添加</n-button>
					</n-space>
				</template>
			</n-tabs>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, ref} from "vue";
import {NTree, TransferRenderSourceList, TreeOption} from "naive-ui";
import {interfaceInfoApi} from "@/service/api/auth/interfaceInfoApi";

defineComponent({name: 'index'});

type BlogTreeOption = {
	label: string
	value: string
	children?: BlogTreeOption[]
}

interface ModuleServiceInterfaceProperties {
	moduleTagName?: string | null,
	interfaceArr?: {
		summary?: string | null,
		requestPath?: string | null,
		requestMethod?: string | null
	}[]
}

interface InterfaceProperties {
	serviceTagName: string | null,
	servicePathArr: Array<ModuleServiceInterfaceProperties> | []
}

const interfacePropertiesArr = ref<Array<InterfaceProperties>>([])
const transferInterfaceArr = ref<Array<string>>([])
const transferInterfaceOptions = ref<Array<BlogTreeOption>>([])

const renderSourceList: TransferRenderSourceList = function ({
																															 onCheck,
																															 pattern
																														 }) {
	return h(
		// @ts-ignore
		NTree,
		{
			checkboxPlacement: 'right',
			style: 'margin: 0 4px;',
			keyField: 'value',
			checkable: true,
			selectable: false,
			blockLine: true,
			checkOnClick: true,
			data: transferInterfaceOptions.value,
			pattern,
			checkedKeys: transferInterfaceArr.value,
			onUpdateCheckedKeys: (checkedKeys: Array<string | number>) => {
				onCheck(checkedKeys)
			}
		})
}

const handleScanAllInterfaceAction = () => {
	interfaceInfoApi.queryListSwaggerConfig().then(result => {
		if (result.data) {
			result.data.urls?.forEach((v, index) => {
				const parentOneTreeOption: BlogTreeOption = {
					label: v.name!,
					value: v.name!,
					children: new Array<BlogTreeOption>()
				}
				interfaceInfoApi.querySingleSwaggerInterfaceInfo(v.name!).then(result1 => {
					if (!result1.tags) return
					const interfaceProperties :InterfaceProperties = {
						serviceTagName: v.name!,
						servicePathArr: []
					}
					result1.tags.forEach((tag, tagIndex) => {
						const moduleServiceInterfaceProperties :ModuleServiceInterfaceProperties = {
							moduleTagName: tag.name,
							interfaceArr: []
						}
						const parentTwoTreeOption: BlogTreeOption = {
							label: tag.name!,
							value: tag.name!,
							children: new Array<BlogTreeOption>()
						}
						moduleServiceInterfaceProperties.moduleTagName = tag.name
						result1.requestPaths?.forEach(pathInfo => {
							// @ts-ignore
							if (pathInfo.tags!.indexOf(tag.name) !== -1) {
								const parentThreeTreeOption: BlogTreeOption = {
									label: `${pathInfo.requestMethod}-${pathInfo.summary}-${pathInfo.requestPath}`,
									value: pathInfo.requestPath!
								}
								parentTwoTreeOption.children?.push(parentThreeTreeOption)
								moduleServiceInterfaceProperties.interfaceArr?.push({
									summary: pathInfo.summary,
									requestMethod: pathInfo.requestMethod,
									requestPath: pathInfo.requestPath
								})
							}
						})
						// @ts-ignore
						interfaceProperties.servicePathArr.push(moduleServiceInterfaceProperties)
						parentOneTreeOption.children?.push(parentTwoTreeOption)
					})
					transferInterfaceOptions.value.push(parentOneTreeOption)
					interfacePropertiesArr.value.push(interfaceProperties)
				})
			})
		}
	})
}

const handleScanAddInterfaceAction = () => {

}
</script>

<style scoped>

</style>
