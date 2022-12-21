<template>
  <div>
		<n-space vertical>
			<n-card :title="props.dataTableInfo.title" class="h-full shadow-sm rounded-16px">
				<!-- 空白页展示 -->
				<loading-empty-wrapper
					:loading="loadingEmptyWrapperData.loading"
					:empty="loadingEmptyWrapperData.empty"
					:loading-size="props.loadingEmptyWrapperProps.loadingSize"
					:placeholder-class="props.loadingEmptyWrapperProps.placeholderClass"
					:empty-desc="props.loadingEmptyWrapperProps.emptyDesc"
					:icon-class="props.loadingEmptyWrapperProps.iconClass"
					:desc-class="props.loadingEmptyWrapperProps.descClass"
					:show-network-reload="props.loadingEmptyWrapperProps.showNetworkReload" >
					<!-- 数据展示相关 -->
					<n-space vertical>
						<n-data-table
							:row-key="getRowKey"
							:columns="dataTableColumns"
							:data="dataTableRowData"
							@update:checked-row-keys="handleCheckedRowKeys"/>
					</n-space>
				</loading-empty-wrapper>
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
			<n-row :gutter="[0, 24]">
				<n-col :span="24">
					<div style="display: flex; justify-content: right">
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
					</div>
				</n-col>
			</n-row>
		</n-space>
  </div>
</template>

<script lang="ts" setup>

const data = Array.from({ length: 46 }).map((_, index) => ({
	name: `Edward King ${index}`,
	age: 32,
	address: `London, Park Lane no. ${index}`
}))

import {DataTableColumns, DataTableRowKey} from "naive-ui";
import {RowData} from "naive-ui/es/data-table/src/interface";
import {reactive, watch} from "vue";

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
	pageNum: number,
	pageSize: number,
	pageTotal: number,
	pageSizes: number[]
}

interface DataTableInfo {
	title?: string,
	rowKey: string
}

interface Props {
	/** 和加载数据展示页相关的属性 */
	loadingEmptyWrapperProps?: LoadingEmptyWrapperProps,
	dataTableColumns: DataTableColumns,
	dataTableRowData: Array<RowData>,
	pagination: DataTablePaginationProps,
	dataTableInfo: DataTableInfo
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
	dataTableRowData: () => new Array<RowData>(),
	dataTableInfo: () => {
		return {
			rowKey: ''
		}
	}
});

// 定义data
const loadingEmptyWrapperData = reactive({
	loading: false,
	empty: false
})

// 方法
const handleChangePageSize = (pageSize: number):void => {
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

// 监听数据变化
watch(props.dataTableRowData, (newValue, oldValue) => {
	if (newValue.length === 0) {
		loadingEmptyWrapperData.empty = true;
		loadingEmptyWrapperData.loading = false;
	}else {
		loadingEmptyWrapperData.empty = false;
		loadingEmptyWrapperData.loading = false;
	}
})

</script>

<style scoped></style>
