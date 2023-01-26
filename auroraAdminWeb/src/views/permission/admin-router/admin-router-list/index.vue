<template>
	<div>
		<show-table-data
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true, scrollX: 1800}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod">
			<template #cardHeader1>
				<n-button strong secondary tertiary round type="success" @click="handleAddRoleAction">添加</n-button>
				<n-button strong secondary tertiary round type="success" @click="handleAddRoleAction">测试</n-button>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {Condition, PageData} from "@/bean/core/bean";
import {DataTableColumn, NA, NButton, NSpace, NSwitch, NTag, NText} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {emitter, getRandomTagType, StringUtil, transformRouteNameToRoutePath} from "@/utils";
import {useRouterPush} from "@/composables";
import RequestResult = Service.RequestResult;
import {bulletinApi, roleApi} from "@/service";
import {RoleVo} from "@/bean/vo/admin/RoleVo";
import {BulletinVo} from "@/bean/vo/article/BulletinVo";
import {Role} from "@/bean/pojo/admin/Role";
import {adminRouterApi} from "@/service/api/admin/adminRouterApi";
import {AdminRouterVo} from "@/bean/vo/admin/AdminRouterVo";
import {AdminRouter} from "@/bean/pojo/admin/AdminRouter";
import { useIconRender } from '@/composables';

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const router = useRouterPush()
const { iconRender } = useIconRender();

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<AdminRouterVo>>> => {
	return adminRouterApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: AdminRouterVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.name} 路由◔ ‸◔?`,
		positiveText: '删除',
		negativeText: '取消',
		onPositiveClick: () => {
			adminRouterApi.physicalDeleteData(data as AdminRouter).then(result => {
				if (result.data === 1) {
					window.$message?.success(`删除 ${data.name} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}
	})
}

const handleModifyAction = (data: RoleVo) => {
	emitter.emit('adminRouterManageModifyAdminRouterAction', data)
}

const handleAddRoleAction = () => {
	emitter.emit('adminRouterManageAddAdminRouterAction')
}

const updateAdminRouterInfo = (data: AdminRouterVo) => {
  adminRouterApi.updateData(data as AdminRouter).then(result => {
		if (!result.error && result.data === 1) {
			window.$message?.success('操作成功')
		}
	})
}

const createColumns = (): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '图标',
			key: 'icon',
			titleColSpan: 1,
			width: 50,
			fixed: 'left',
			render(row: AdminRouterVo) {
				return h(
					iconRender({
						icon: row.icon!
					})
				)
			}
		},
		{
			title: '组件名称',
			key: 'name',
			fixed: 'left',
			titleColSpan: 1,
			width: 150,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '路由路径',
			key: 'path',
			titleColSpan: 1,
			width: 150,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '一级路由',
			key: 'son_router_uids',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				if (!StringUtil.haveLength(row.parentRouterUid)) {
					return h(
						NTag,
						{
							type: 'success',
							bordered: false
						},
						{
							default: () => '是'
						}
					)
				}else {
					return h(
						NTag,
						{
							type: 'error',
							bordered: false
						},
						{
							default: () => '否'
						}
					)
				}
			}
		},
		{
			title: '外链',
			key: 'href',
			titleColSpan: 1,
			width: 130,
			ellipsis: true,
			render(row: AdminRouterVo) {
				if (!StringUtil.haveLength(row.href)) return ''
				return h(
					NA,
					{
						href: row.href,
						target: '_blank'
					},
					{
						default: () => h(
							NText,
							{

							},
							{
								default: () => row.href
							}
						)
					}
				)
			}
		},
		{
			title: '标题',
			key: 'title',
			titleColSpan: 1,
			width: 130,
			ellipsis: {
				tooltip: true
			}
		},
		{
			title: '本地图标',
			key: 'title',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				if (!StringUtil.haveLength(row.localIcon)) {
					return h(
						NTag,
						{
							type: 'error',
							bordered: false
						},
						{
							default: () => '无'
						}
					)
				}
				return h(
					iconRender({
						icon: row.localIcon!
					})
				)
			}
		},
		{
			title: '登录权限',
			key: 'requiresAuth',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.requiresAuth,
						onUpdateValue(value: boolean) {
							window.$dialog?.success({
								positiveText: '确定',
								negativeText: '取消',
								title: '确定修改么',
								onPositiveClick() {
									row.requiresAuth = value
									updateAdminRouterInfo(row)
								}
							})
						}
					}
				)
			}
		},
		{
			title: '页面缓存',
			key: 'keepAlive',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.keepalive,
						onUpdateValue(value: boolean) {
							window.$dialog?.success({
								positiveText: '确定',
								title: '确定修改么',
								negativeText: '取消',
								onPositiveClick() {
									row.keepalive = value
									updateAdminRouterInfo(row)
								}
							})
						}
					}
				)
			}
		},
		{
			title: '隐藏',
			key: 'hide',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.hide,
						onUpdateValue(value: boolean) {
							window.$dialog?.success({
								positiveText: '确定',
								title: '确定修改么',
								negativeText: '取消',
								onPositiveClick() {
									row.hide = value
									updateAdminRouterInfo(row)
								}
							})
						}
					}
				)
			}
		},
		{
			title: '不可关闭',
			key: 'affix',
			titleColSpan: 1,
			width: 100,
			render(row: AdminRouterVo) {
				return h(
					// @ts-ignore
					NSwitch, {
						value: row.affix,
						onUpdateValue(value: boolean) {
							window.$dialog?.success({
								positiveText: '确定',
								title: '确定修改么',
								negativeText: '取消',
								onPositiveClick() {
									row.affix = value
									updateAdminRouterInfo(row)
								}
							})
						}
					}
				)
			}
		},
		{
			title: '创建时间',
			key: 'createTime',
			titleColSpan: 1,
			sorter: 'default',
			sortOrder: false,
			width: 170
		},
		{
			title: '操作',
			key: 'actions',
			fixed: 'right',
			width: 200,
			render(row) {
				return h(
					NSpace,
					{
						justify: 'center'
					},
					{
						default:() => Array.of(
							h(
								NButton,
								{
									size: 'small',
									type: 'warning',
									ghost: true,
									onClick: () => handleDeleteAction(row)
								},
								{ default: () => '删除' }
							),
							h(
								NButton,
								{
									size: 'small',
									type: 'primary',
									ghost: true,
									onClick: () => handleModifyAction(row)
								},
								{ default: () => '编辑' }
							)
						)
					}
				);
			}
		}
	]
}

const columns = ref<Array<DataTableColumn>>(createColumns())

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
