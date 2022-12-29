<template>
	<div>
		<show-table-data
			:data-table-info="{title: 'Oauth客户端配置', rowKey: 'clientId', striped: true, scrollX: 2500}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod">
			<template #cardHeader1>
				<n-space>
					<n-button strong secondary tertiary round type="success" @click="handleAddUserAction">添加配置</n-button>
				</n-space>
			</template>
		</show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onMounted, ref} from "vue";
import {UserVo} from "@/theme/vo/admin/UserVo";
import {Condition, PageData} from "@/theme/core/bean";
import {userApi} from "@/service";
import {DataTableColumn, NButton, NSpace, NTag} from "naive-ui";
import {emitter} from "@/utils";
import {EnumMittEventName} from "@/enum";
import {User} from "@/theme/pojo/admin/User";
import {oauthClientApi} from "@/service/api/auth/oauthClientApi";
import {OauthClientDetailsVo} from "@/theme/vo/auth/OauthClientDetailsVo";
import RequestResult = Service.RequestResult;
import {authorizedGrantTypes} from "@/constants";
import {copyContent} from "@/plugins";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const typeArr: string[] = ['error' , 'primary' , 'info' , 'success' , 'warning']

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<OauthClientDetailsVo>>> => {
	return oauthClientApi.queryListDataByCondition(condition);
}

// 定义方法
const handleDeleteAction = (data: UserVo) => {
	window.$dialog?.warning({
		title: `删除 ${data.username} ◔ ‸◔?`,
		content: '用户删除被永久删除之后，此用户绑定的信息将会被取消',
		positiveText: '删除',
		negativeText: '永久删除',
		onPositiveClick: () => {
			userApi.logicDeleteData(data as User).then(result => {
				if (result.data === 1) {
					condition.value.delete = false
					window.$message?.error(`删除 ${data.username} 成功 ○|￣|_`);
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		},
		onNegativeClick: () => {
			userApi.physicalDeleteData(data as User).then(result => {
				if (result.data === 1) {
					window.$message?.error(`永久删除 ${data.username} 成功 ㄟ( ▔, ▔ )ㄏ `)
					condition.value.delete = false
					emitter.emit(EnumMittEventName.reloadData)
				}
			})
		}

	})
}

const handleModifyAction = (data: OauthClientDetailsVo) => {
	emitter.emit('oauthManageModifyOauthClientConfigAction', data)
}

const handleAddUserAction = () => {
	emitter.emit('oauthAddUserOauthClientConfigAction')
}

const createColumns = ({
	getTagInfo
											 }: {
	getTagInfo: (row: UserVo) => string
}): Array<DataTableColumn> => {
  return [
		{
			type: 'selection'
		},
		{
			title: '客户端ID',
			key: 'clientId',
			titleColSpan: 1,
			sortOrder: false,
			sorter: 'default',
			fixed: 'left',
			width: 130
		},
		{
			title: '资源ID',
			key: 'resourceIds',
			titleColSpan: 1,
			width: 150,
			ellipsis: true
		},
		{
			title: '客户端权限',
			key: 'scope',
			titleColSpan: 1,
			maxWidth: 250,
			minWidth: 100,
			ellipsis: true
		},
		{
			title: '授权类型',
			key: 'authorizedGrantTypes',
			width: 600,
			render (row: OauthClientDetailsVo) {
				if (!row.authorizedGrantTypes) {
					return h(
						NTag,
						{
							bordered: false,
							type: 'info'
						},
						{
							default: () => '无授权信息'
						}
					)
				}
				const authorizedGrantTypeArr: Array<string> = row.authorizedGrantTypes?.split(",");
				return authorizedGrantTypeArr.map((tagKey, index) => {
					return h(
						// @ts-ignore
						NTag,
						{
							style: {
								marginRight: '6px'
							},
							type: typeArr[index % typeArr.length],
							bordered: false
						},
						{
							default: () => {
								if (authorizedGrantTypes.indexOf(tagKey) === -1) {
									return '无效授权类型'
								}else {
									return tagKey
								}
							}
						}
					)
				})
			}
		},
		{
			title: '权限',
			key: 'authorities',
			width: 250,
			ellipsis: true,
			render (row: OauthClientDetailsVo) {
				if (!row.authorities) {
					return h(
						NTag,
						{
							style: {
								marginRight: '6px'
							},
							bordered: false,
							type: 'info'
						},
						{
							default: () => '无权限'
						}
					)
				}
				const authoritiesArr: Array<string> = row.authorities?.split(",");
				return authoritiesArr.map((tagKey, index) => {
					return h(
						// @ts-ignore
						NTag,
						{
							style: {
								marginRight: '6px'
							},
							type: typeArr[index % typeArr.length],
							bordered: false
						},
						{
							default: () => tagKey
						}
					)
				})
			}
		},
		{
			title: '令牌过期时间',
			key: 'accessTokenValidity',
			titleColSpan: 1,
			width: 130
		},
		{
			title: '刷新令牌过期时间',
			key: 'refreshTokenValidity',
			titleColSpan: 1,
			width: 140
		},
		{
			title: '授权码模式跳转URI',
			key: 'webServerRedirectUri',
			titleColSpan: 1,
			width: 200,
			ellipsis: true,
			render(row: OauthClientDetailsVo) {
				return h(
					NTag, {
						bordered: false,
						type: 'success',
						checkable: true,
						onUpdateChecked() {
							copyContent(row.webServerRedirectUri!)
						}
					},
					{
						default: () => row.webServerRedirectUri
					}
				)
			}
		},

		{
			title: '自动授权',
			key: 'autoapprove',
			width: 100,
			titleColSpan: 1,
			render(row: OauthClientDetailsVo) {
				return h(
					NTag, {
						bordered: false,
						type: row.autoapprove ? 'warning' : 'success'
					},
					{
						default: () => row.autoapprove ? '自动' : '禁止'
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
			title: '最后更新时间',
			key: 'updateTime',
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

const columns = ref<Array<DataTableColumn>>(createColumns({
	getTagInfo(row: UserVo) {
		if (row.gender === 'MALE') return 'success'
		if (row.gender === 'FEMALE') return 'warning'
		return 'error'
	}
}))

// 挂载emit
onMounted(() => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

</script>

<style scoped>

</style>
