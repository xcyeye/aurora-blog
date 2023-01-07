<template>
	<div>
		<n-space justify="space-around">
			<router-link to="/user/1522074993315815424">用户首页</router-link>
			<router-link to="/friendLink/1522074993315815424">友情链接</router-link>
			<router-link to="/archive/1522074993315815424">时间轴</router-link>
			<router-link to="/tag-category/1522074993315815424">标签</router-link>
			<router-link to="/about/1522074993315815424">关于</router-link>
			<router-link to="/shareSpace/1522074993315815424">说说1</router-link>
			<router-link to="/shareSpace-page/1522074993315815424">说说2</router-link>
			<router-link to="/article/1522074993315815424">文章</router-link>
		</n-space>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});
</script>

<style scoped>

</style>