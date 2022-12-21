<template>
  <div>
    <show-table-data
			:data-table-info="{title: '测试表格', rowKey: 'name'}"
			:data-table-columns="createColumns"
			:data-table-row-data="showData"
			:pagination="pagination"
			@handleCheckedRowKeys="handleCheckedRowKeys"
			@handleChangePageSize="handleChangePageSize"
			@handleChangePageNum="handleChangePageNum">
			<template #cardHeader1>
				<n-button>这是按钮1</n-button>
			</template>
		</show-table-data>
		<!--<loading-empty-wrapper :loading="true"/>-->
    <!--<app-loading/>-->
    <!--<dark-mode-container :inverted="true"/>-->
    <!--<dark-mode-switch :dark="true"/>-->
    <!--<exception-base/>-->
    <!--<hover-container tooltip-content="aurora"/>-->
    <!--<naive-provider/>-->
    <!--<count-to :end-value="100999" :autoplay="true" prefix="xcyeye" suffix="aurora" separator=""/>-->
    <!--<github-link link="https://github.com/xcyeye"/>-->
    <!--<icon-select/>-->
    <!--<image-verify/>-->
    <!--<svg-icon/>-->
    <!--<login-agreement/>-->
    <!--<loading-empty-wrapper :loading="true"/>-->
    <!--<button @click="click">click</button>-->
  </div>
</template>

<script lang="ts" setup>

type RowData = {
	key: number
	name: string
	age: number
	address: string
}

type aa = RowData;

import {reactive} from "vue";
import {DataTableColumns, DataTableRowKey} from "naive-ui";

const pagination = reactive({
	pageNum: 1,
	pageSize: 10,
	pageTotal: 100,
	pageSizes: [5, 10, 15]
})

interface DataStr {
	name: string,
	age: number,
	address: string
}

const aa:DataStr[] = [];
const showData = reactive(aa)
const handleChangePageSize = (pageSize: number):void => {
	// console.log(`pageSize ${pageSize}`)
	pagination.pageSize = pageSize
	showData.splice(0, showData.length)
	for (let i = pagination.pageNum * pagination.pageSize; i < pagination.pageNum * pagination.pageSize + pagination.pageSize; i++) {
		showData.push({
			name: `xcye ${i}`,
			age: i,
			address: `云南省保山市${i}`
		})
	}
}

const handleChangePageNum = (page: number):void => {
	// console.log(`page ${page}`)
	pagination.pageNum = page
	showData.splice(0, showData.length)
	for (let i = pagination.pageNum * pagination.pageSize; i < pagination.pageNum * pagination.pageSize + pagination.pageSize; i++) {
		showData.push({
			name: `xcye ${i}`,
			age: i,
			address: `云南省保山市${i}`
		})
	}
}

const handleCheckedRowKeys = (rowKeys: DataTableRowKey) => {
	console.log(rowKeys)
}

const createColumns: DataTableColumns<RowData> = [
	{
		type: 'selection',
		disabled (row: RowData) {
			return row.name === 'Edward King 3'
		}
	},
	{
		title: 'Name',
		key: 'name'
	},
	{
		title: 'Age',
		key: 'age'
	},
	{
		title: 'Address',
		key: 'address'
	}
]
</script>

<style scoped></style>
