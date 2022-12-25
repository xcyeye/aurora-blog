<template>
	<div>
		<show-table-data
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			:query-data-method="queryDataMethod"
			:page-sizes="[1,2,3,4,5,6]">
			<template #cardHeader1>
				<n-space>
					<n-button strong secondary tertiary round type="success" @click="handleAddUserAction">添加用户</n-button>
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
import {DataTableColumn, NAvatar, NButton, NSpace, NTag} from "naive-ui";
import {EnumMittEventName} from "@/enum";
import {User} from "@/theme/pojo/admin/User";
import RequestResult = Service.RequestResult;
import {emitter} from "@/utils";

defineComponent({name: 'index'});

// 定义data
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})

const queryDataMethod = (condition: Condition): Promise<RequestResult<PageData<UserVo>>> => {
	return userApi.queryListDataByCondition(condition);
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

const handleModifyAction = (data: UserVo) => {
	emitter.emit('userManageModifyUserAction', data)
}

const handleAddUserAction = () => {
	emitter.emit('userManageAddUserAction')
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
			title: '头像',
			key: 'avatar',
			titleColSpan: 1,
			width: 70,
			render(row: UserVo) {
				return h(
					// @ts-ignore
					NAvatar,
					{
						round: true,
						size: 'small',
						src: row.avatar,
						fallbackSrc: ''
					}
				)
			}
		},
		{
			title: '用户名',
			key: 'username',
			titleColSpan: 1,
			width: 120,
			sortOrder: false,
			sorter: 'default'
		},
		{
			title: '昵称',
			key: 'nickname',
			titleColSpan: 1,
			width: 130,
			ellipsis: true
		},
		{
			title: '描述',
			key: 'userSummary',
			titleColSpan: 1,
			minWidth: 100,
			maxWidth: 170,
			ellipsis: true
		},
		{
			title: '邮箱',
			key: 'verifyEmail',
			titleColSpan: 1,
			render(row: UserVo) {
				return h(
					NTag, {
						bordered: true,
						type: row.verifyEmail ? 'success' : 'warning'
					},
					{
						default: () => row.verifyEmail ? '已绑定' : '未绑定'
					}
				)
			}
		},
		{
			title: '状态',
			key: 'delete',
			width: 100,
			titleColSpan: 1,
			render(row: UserVo) {
				return h(
					NTag, {
						bordered: true,
						type: row.delete ? 'warning' : 'success'
					},
					{
						default: () => row.delete ? '已删除' : '正常'
					}
				)
			}
		},
		{
			title: '性别',
			key: 'gender',
			titleColSpan: 1,
			width: 100,
			render(row: UserVo) {
				return h(
					// @ts-ignore
					NTag,
					{
						bordered: true,
						type: getTagInfo(row)
					},
					{
						default: () => row.gender
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
