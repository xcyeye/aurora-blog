<template>
	<div>
		<n-card :bordered="false" class="rounded-16px shadow-sm">
			<div :id="echartsId" ref="echarts" style="height: calc(65vh)"></div>
		</n-card>
	</div>
</template>

<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import * as echarts from 'echarts';
import {rand} from '@vueuse/core';
import {useAuthStore} from '@/store';
import {TagVo} from "@/bean/vo/article/TagVo";
import {Article} from "@/bean/pojo/article/Article";
import {Condition} from "@/bean/core/bean";
import {articleApi, tagApi} from "@/service";
import {ECharts} from "echarts";
import {ECBasicOption} from "echarts/types/dist/shared";

let myChart: ECharts;

interface GraphCategory {
	name?: string;
	uid?: string;
}

interface GraphLink {
	source?: string;
	target?: string;
}

interface GraphNode {
	id?: string;
	name?: string;
	symbolSize?: number;
	label?: {
		show?: boolean;
	};
	x?: number;
	y?: number;
	value?: string;
	category?: string;
}

const graphCategories = ref<Array<GraphCategory>>([])
const graphCategoryNodes = ref<Array<GraphNode>>([])
const graphNodes = ref<Array<GraphNode>>([])
const graphLinks = ref<Array<GraphLink>>([])
const tagArr = ref<Array<TagVo>>([])
const articleArr = ref<Array<Article>>([])
const condition =ref<Condition>({})
const echartsId = ref(`main${new Date().getTime()}`);
const authStore = useAuthStore()

const calculateSymbolSizeByCreateTime = (createTime: string | null | undefined): number => {
	if (!createTime) {
		return 2
	}
	// 获取年份
	const year = createTime.split('-')[0];
	// 截取年份的最后两位数字，作为大小
	return parseInt(year.substring(2, year.length), 4);
};

const getRandomCoordinateX = () => {
	return rand(-400, 400);
};

const getRandomCoordinateY = () => {
	return rand(-400, 400);
};

// 加载数据
async function loadAllCategoryData() {
	tagArr.value = []
	graphCategories.value = []
	graphCategoryNodes.value = []

	// 加载所有的类别
	condition.value.pageSize = 1000000;
	await tagApi.queryListDataByCondition(condition.value).then(result => {
		if (result.data && result.data?.result) {
			Promise.all(
				result.data?.result.map(tag => {
					tagArr.value.push(tag);
					graphCategories.value.push({ name: tag.title as string, uid: tag.uid as string });

					// 将类别节点也封装成图节点
					graphCategoryNodes.value.push({
						id: tag.title as string,
						name: tag.title as string,
						symbolSize: calculateSymbolSizeByCreateTime(tag.createTime as string),
						x: getRandomCoordinateX(),
						y: getRandomCoordinateY(),
						value: tag.title as string,
						category: tag.title as string
					});
					return true;
				})
			);
		}
	});
}

async function loadAllArticleData() {
	articleArr.value = [];
	const condition: Condition = {
		delete: null,
		status: null,
		show: null
	}
	condition.pageSize = 100000;
	condition.otherUid = authStore.userInfo.user_uid

	await articleApi.queryListDataByCondition(condition).then(result => {
		if (result.data && result.data.result) {
			Promise.all(
				result.data.result.map(article => {
					articleArr.value.push(article);
					return true;
				})
			);
		}
	});
}

async function packageArticleNode() {
	// 文章
	await Promise.all(
		articleArr.value.map(async article => {
			// 因为每篇文章的类别会存在多个，使用,分割开的
			let split: Array<string> = article.tagNames?.split(',')!;
			if (!split) {
				split = new Array<string>();
			}
			await Promise.all(
				split.map(articleCategory => {
					graphNodes.value.push({
						id: (article.uid as string) + articleCategory,
						name: article.title as string,
						symbolSize: calculateSymbolSizeByCreateTime(article.createTime as string),
						x: getRandomCoordinateX(),
						y: getRandomCoordinateY(),
						value: article.title as string,
						category: articleCategory as string,
						label: {
							show: true
						}
					});
					return true;
				})
			);

			return true;
		})
	);
}

async function rePackageGraph() {
	await loadAllCategoryData();
	await loadAllArticleData();
	await packageArticleNode();

	await Promise.all(
		graphCategoryNodes.value.map(graphCategoryNode => {
			graphNodes.value.push(graphCategoryNode);
			return true;
		})
	);
	// 建立关系
	await Promise.all(
		graphCategoryNodes.value.map(async graphCategoryNode => {
			await Promise.all(
				graphNodes.value.map(graphNode => {
					if (graphCategoryNode.id === graphNode.category) {
						graphLinks.value.push({ source: graphCategoryNode.id, target: graphNode.id });
						graphCategoryNode.symbolSize =
							graphCategoryNode.symbolSize === undefined ? 10 : graphCategoryNode.symbolSize + 10;
					}
					return true;
				})
			);
		})
	);
}

const setOption = () => {
	const option: ECBasicOption = {
		title: {
			text: '',
			// subtext: 'Default layout',
			// top: 'bottom',
			// left: 'right'
		},
		tooltip: {},
		legend: [
			{
				// selectedMode: 'single',
				data: graphCategories.value.map(value => {
					return value.name as string;
				})
			}
		],
		animationDuration: 1500,
		animationEasingUpdate: 'quinticInOut',
		series: [
			{
				name: 'Les Miserables',
				type: 'graph',
				layout: 'none',
				data: graphNodes.value,
				links: graphLinks.value,
				categories: graphCategories.value,
				roam: true,
				label: {
					position: 'right',
					formatter: '{b}'
				},
				lineStyle: {
					color: 'source',
					curveness: 0.3
				},
				emphasis: {
					focus: 'adjacency',
					lineStyle: {
						width: 10
					}
				}
			}
		]
	};
	myChart.setOption(option);
};

onMounted(() => {
	type EChartsOption = echarts.EChartsOption;
	const chartDom = document.getElementById(echartsId.value)!;
	myChart = echarts.init(chartDom, 'dark');
	let option: EChartsOption;
	myChart.showLoading();

	rePackageGraph().then(data => {
		myChart.hideLoading();
		setOption();
	});
	window.onresize = () => {
		myChart.resize();
	};
});

const changeTab = (value: string | number) => {
	if (value === 'echarts') {
		const time = setInterval(() => {
			if (document.getElementById(echartsId.value)) {
				const chartDom = document.getElementById(echartsId.value)!;
				myChart = echarts.init(chartDom, 'dark');
				setOption();
				clearInterval(time);
			}
		}, 20);
	}
};
</script>

<style scoped lang="less"></style>
