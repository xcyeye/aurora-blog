<template>
	<div>
		<n-button @click="button">button</n-button>
		<show-table-data
			:data-table-row-data="userDataArr"
			:data-table-info="{title: '用户管理', rowKey: 'uid', striped: true}"
			:data-table-columns="columns"
			:pagination="pagination"></show-table-data>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, h, onBeforeMount, onMounted, ref} from "vue";
import {UserVo} from "@/theme/vo/admin/UserVo";
import {Condition, Pagination} from "@/theme/core/bean";
import {emailApi, userApi} from "@/service";
import {DataTableColumn, NAvatar, NTag} from "naive-ui";
import emitter from "@/utils/mitt";
import {EnumMittEventName} from "@/enum";

defineComponent({name: 'index'});

// 定义方法

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
			titleColSpan: 1
		},
		{
			title: '昵称',
			key: 'nickname',
			titleColSpan: 1
		},
		{
			title: '描述',
			key: 'userSummary',
			titleColSpan: 2,
			ellipsis: true,
			colSpan() {
				return 2
			}
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
			title: '性别',
			key: 'gender',
			titleColSpan: 1,
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
			title: '昵称',
			key: 'nickname',
			titleColSpan: 1,
		},
		{
			title: '创建时间',
			key: 'createTime',
			titleColSpan: 1,
			sorter: 'default',
			width: 170
		},
		{
			title: 'Action',
			key: 'actions',
			fixed: 'right',
			width: 300,
			render(row: UserVo) {

			}
		}
	]
}

// 定义data
const userDataArr = ref<Array<UserVo>>([])
const condition = ref<Condition>({
	delete: null,
	// 账户是否锁住
	status: null
})
const pagination = ref<Pagination>({
	pageNum: 1,
	pageSize: 20
})
const columns = ref<Array<DataTableColumn>>(createColumns({
	getTagInfo(row: UserVo) {
		if (row.gender === 'MALE') return 'success'
		if (row.gender === 'FEMALE') return 'warning'
		return 'error'
	}
}))

// 加载数据
// 从后端获取用户数据
const loadData = () => {
	userApi.queryListDataByCondition(condition.value).then(result => {
		if (result.data) {
			pagination.value.pageSize = result.data.pages!
			pagination.value.pageTotal = result.data.total!
			if (result.data.result) {
				userDataArr.value = result.data.result
			}
		}
	})
}

// 调用方法
loadData();

// 挂载emit
onMounted(() => {
	emitter.on(EnumMittEventName.globalSearchCondition, event => {
		if (event) {
			condition.value = event as Condition;
			loadData();
		}
	});
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value);
})

// 测试方法
const button = () => {
	emitter.emit(EnumMittEventName.resetGlobalSearchCondition, condition.value)
}

</script>

<style scoped>

</style>
