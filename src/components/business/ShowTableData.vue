<template>
  <div>
		<n-space vertical>
			<n-card :title="props.dataTableInfo.title" class="h-full shadow-sm rounded-16px">
				<!-- 空白页展示 -->
				<!--<loading-empty-wrapper-->
				<!--	:loading="loadingEmptyWrapperData.loading"-->
				<!--	:empty="loadingEmptyWrapperData.empty"-->
				<!--	:loading-size="props.loadingEmptyWrapperProps.loadingSize"-->
				<!--	:placeholder-class="props.loadingEmptyWrapperProps.placeholderClass"-->
				<!--	:empty-desc="props.loadingEmptyWrapperProps.emptyDesc"-->
				<!--	:icon-class="props.loadingEmptyWrapperProps.iconClass"-->
				<!--	:desc-class="props.loadingEmptyWrapperProps.descClass"-->
				<!--	:show-network-reload="props.loadingEmptyWrapperProps.showNetworkReload" >-->
				<!--	-->
				<!--</loading-empty-wrapper>-->
				<!-- 数据展示相关 -->
				<n-space vertical>
					<n-data-table
						:scroll-x="props.dataTableInfo.scrollX"
						:striped="props.dataTableInfo.striped"
						class="h-480px"
						:flex-height="true"
						:row-key="getRowKey"
						:columns="dataTableColumns"
						:data="dataTableRowData"
						:render-expand-icon="renderExpandIcon"
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
			<n-pagination
				v-model:page="pagination.pageNum"
				v-model:page-size="pagination.pageSize"
				:item-count="pagination.pageTotal"
				:page-slot="10"
				show-size-picker
				:page-sizes="pagination.pageSizes"
				@update:page-size="handleChangePageSize"
				@update:page="handleChangePageNum"
			/>
		</n-space>
  </div>
</template>

<script lang="ts" setup>
import {DataTableColumn, DataTableRowKey} from "naive-ui";
import {RowData} from "naive-ui/es/data-table/src/interface";
import {onBeforeMount, onMounted, reactive, ref, VNode, watch} from "vue";

// 定义emit
const emits = defineEmits(['handleChangePageSize', 'handleChangePageNum', 'handleCheckedRowKeys'])

interface LoadingEmptyWrapperProps {
	/** 加载图标的大小 */
	loadingSize?: 'small' | 'medium' | 'large';
	/** 中间占位符的class */
	placeholderClass?: string;
	/** 空数据描述文本 */
	emptyDesc?: string;
	/** 图标的class */
	iconClass?: string;
	/** 描述文本的class */
	descClass?: string;
	/** 显示网络异常的重试点击按钮 */
	showNetworkReload?: boolean;
}

interface DataTablePaginationProps {
	pageNum?: number,
	pageSize?: number,
	pageTotal?: number,
	pageSizes?: number[],
	pageCount?: number
}

interface DataTableInfo {
	title?: string,
	rowKey: string,
	striped?: boolean,
	scrollX?: number
}

interface Props {
	/** 和加载数据展示页相关的属性 */
	loadingEmptyWrapperProps?: LoadingEmptyWrapperProps,
	dataTableColumns: Array<DataTableColumn>,
	dataTableRowData: Array<RowData>,
	pagination?: DataTablePaginationProps,
	dataTableInfo: DataTableInfo,
	renderExpandIcon?: () => VNode
}

const props = withDefaults(defineProps<Props>(), {
	loadingEmptyWrapperProps: () => {
		return {
			loadingSize: 'medium',
			placeholderClass: 'bg-white dark:bg-dark transition-background-color duration-300 ease-in-out',
			emptyDesc: '暂无数据',
			iconClass: 'text-320px text-primary',
			descClass: 'text-16px text-[#666]',
			showNetworkReload: false
		}
	},
	pagination: () => {
		return {
			pageNum: 1,
			pageSize: 10,
			pageTotal: 0,
			pageSizes: [10, 20, 30]
		}
	},
	dataTableInfo: () => {
		return {
			rowKey: '',
			striped: true,
			scrollX: undefined
		}
	}
});

// 定义data
const loadingEmptyWrapperData = reactive({
	loading: false,
	empty: false
})
const showTableDataArr = ref<Array<RowData>>()

// 方法
const handleChangePageSize = (pageSize: number):void => {
	if (props.pagination.pageSize === pageSize) return
	loadingEmptyWrapperData.loading = true;
	emits('handleChangePageSize', pageSize);
}

const handleChangePageNum = (page: number):void => {
	loadingEmptyWrapperData.loading = true;
	emits('handleChangePageNum', page);
}

const getRowKey = (rowData: object): string => {
	if (rowData.hasOwnProperty(props.dataTableInfo.rowKey)) {
		// @ts-ignore
		return rowData[props.dataTableInfo.rowKey]
	}
	return ''
}

const handleCheckedRowKeys = (rowKeys: DataTableRowKey) => {
	emits('handleCheckedRowKeys', rowKeys);
}

const assertPageSizes = () => {
	if (props.pagination) {
		if (props.pagination.pageSize && props.pagination.pageSizes) {
			if (props.pagination.pageSizes.indexOf(props.pagination.pageSize) === -1) {
				// 不存在
				props.pagination.pageSizes.push(props.pagination.pageSize)
			}
		}
	}
	setTimeout(() => {
		props.pagination.pageSizes?.sort((a: number, b: number) => a - b)
	}, 500)
}

// 监听数据变化
watch(props.dataTableRowData, (newValue, oldValue) => {
	console.log("数据变化了");
	if (newValue.length === 0) {
		loadingEmptyWrapperData.empty = true;
		loadingEmptyWrapperData.loading = false;
	}else {
		loadingEmptyWrapperData.empty = false;
		loadingEmptyWrapperData.loading = false;
	}
	assertPageSizes()
})

watch(props.pagination, (nv, ov) => {
	assertPageSizes()
})

onMounted(() => {
	if (!props.pagination.pageSizes) {
		props.pagination.pageSizes = [10, 20, 30]
	}
	assertPageSizes()
})

</script>

<style scoped></style>
