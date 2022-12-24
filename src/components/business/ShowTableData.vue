<template>
  <div>
		<n-space vertical>
			<n-card :title="props.dataTableInfo.title" class="h-full shadow-sm rounded-16px">
				<!-- 数据展示相关 -->
				<n-space vertical>
					<n-data-table
						:loading="dataTableLoadingStatus"
						:remote="true"
						:scroll-x="props.dataTableInfo.scrollX"
						:striped="props.dataTableInfo.striped"
						class="h-480px"
						:flex-height="true"
						:row-key="getRowKey"
						:columns="dataTableColumns"
						:data="dataTableRowData"
						:render-expand-icon="renderExpandIcon"
						:pagination="pagination"
						@update:page="handleChangePageNum"
						@update:page-size="handleChangePageSize"
						@update-sorter="handleSorterChange"
						@update:checked-row-keys="handleCheckedRowKeys"/>
				</n-space>
				<template #header-extra>
					<n-space justify="end">
						<slot name="cardHeader1"/>
						<slot name="cardHeader2"/>
						<slot name="cardHeader3"/>
						<slot name="cardHeader4"/>
						<slot name="cardHeader5"/>
						<slot name="cardHeader6"/>
					</n-space>
				</template>
			</n-card>
			<!--<n-pagination-->
			<!--	v-model:page="pagination.pageNum"-->
			<!--	v-model:page-size="pagination.pageSize"-->
			<!--	:item-count="pagination.pageTotal"-->
			<!--	:page-slot="10"-->
			<!--	show-size-picker-->
			<!--	:page-sizes="pagination.pageSizes"-->
			<!--	@update:page-size="handleChangePageSize"-->
			<!--	@update:page="handleChangePageNum"-->
			<!--/>-->
		</n-space>
  </div>
</template>

<script lang="ts" setup>
import {DataTableColumn, DataTableRowKey, DataTableSortState, PaginationProps, PaginationSizeOption} from "naive-ui";
import {RowData, TableBaseColumn} from "naive-ui/es/data-table/src/interface";
import {onBeforeMount, ref, VNode} from "vue";
import {Condition, PageData} from "@/theme/core/bean";
import RequestResult = Service.RequestResult;

// 定义emit
const emits = defineEmits(['handleChangePageSize', 'handleChangePageNum', 'handleCheckedRowKeys'])

interface DataTableInfo {
	title?: string,
	rowKey: string,
	striped?: boolean,
	scrollX?: number
}

interface Props {
	/** 和加载数据展示页相关的属性 */
	dataTableColumns: Array<TableBaseColumn>,
	pageSizes?: number[],
	dataTableInfo: DataTableInfo,
	renderExpandIcon?: () => VNode,
	queryDataMethod: (condition: Condition) => Promise<RequestResult<PageData<RowData>>>,
	queryCondition?: Condition
}

const props = withDefaults(defineProps<Props>(), {
	dataTableInfo: () => {
		return {
			rowKey: '',
			striped: true,
			scrollX: undefined
		}
	},
	queryCondition: () => {
		return {
			keyword: null,
			pageSize: 20,
			pageNum: 1
		}
	}
});

// 定义data
const dataTableRowData = ref<Array<RowData>>([])
const dataTableLoadingStatus = ref<boolean>(true)
const pagination = ref<PaginationProps>({
	pageSize: 20,
	page: 1,
	pageCount: 0,
	showSizePicker: true,
	pageSizes: [10, 20, 30],
})

// 方法
const loadData = (pageSize: number | null, pageNum: number | null, orderBy: string | null, order: string | null) => {
	if (!dataTableLoadingStatus.value) dataTableLoadingStatus.value = true
	if (pageSize) {
		pagination.value.pageSize = pageSize
		props.queryCondition.pageSize = pagination.value.pageSize
	}
	if (pageNum) {
		pagination.value.page = pageNum
		props.queryCondition.pageNum = pagination.value.page
	}
	if (orderBy && order) {
		let orderByStr = orderBy
		if (order === 'ascend') {
			orderByStr = orderByStr + " asc"
		}else if (order === 'descend') {
			orderByStr = orderByStr + " desc"
		}
		props.queryCondition.orderBy = orderByStr
	}
	props.queryDataMethod(props.queryCondition).then(result => {
		dataTableLoadingStatus.value = false
		if (result.data) {
			if (result.data.result) {
				dataTableRowData.value = result.data.result
				pagination.value.pageCount = result.data.pages!
				pagination.value.pageSize = result.data.pageSize!
			}else {
				dataTableRowData.value = []
			}
		}else {
			dataTableRowData.value = []
		}
	})
}

const handleChangePageSize = (pageSize: number):void => {
	loadData(pageSize, null, null, null);
	pagination.value.page = 1
	emits('handleChangePageSize', pageSize);
}

const handleChangePageNum = (page: number):void => {
	loadData(null, page, null, null)
	pagination.value.page = page
	emits('handleChangePageNum', page);
}

const getRowKey = (rowData: object): string => {
	if (rowData.hasOwnProperty(props.dataTableInfo.rowKey)) {
		// @ts-ignore
		return rowData[props.dataTableInfo.rowKey]
	}
	return ''
}

const handleCheckedRowKeys = (rowKeys: DataTableRowKey): undefined => {
	emits('handleCheckedRowKeys', rowKeys);
	return undefined
}

const handleSorterChange = (sorter: DataTableSortState): undefined => {
	props.dataTableColumns.forEach((column: TableBaseColumn) => {
		if (column.sortOrder === undefined) return
		if (!sorter) {
			column.sortOrder = false
			return;
		}
		if (column.key === sorter.columnKey) {
			column.sortOrder = sorter.order
			loadData(null, null, column.key as string, sorter.order as string)
		}else {
			column.sortOrder = false
		}
	})
	return undefined
}

const assertPageSizes = () => {
	if (props.pageSizes) {
		pagination.value.pageSizes = props.pageSizes
		if (pagination.value.pageSizes.indexOf(pagination.value.pageSize!) === -1) {
			pagination.value.pageSizes.push(pagination.value.pageSize!)
			// @ts-ignore
			pagination.value.pageSizes.sort((a: PaginationSizeOption, b: PaginationSizeOption) => (a.value - b.value))
		}
	}
}

// 监听数据变化
// watch(props.queryCondition, (newValue: Condition, oldValue: Condition) => {
// 	console.log("查询条件发生变化了");
// 	console.log(newValue);
// 	assertPageSizes();
// 	loadData(null, null)
// })

onBeforeMount(() => {
	assertPageSizes()
	loadData(null, null, null, null)
})

</script>

<style scoped></style>
