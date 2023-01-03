<template>
	<div>
		<header id="c-navbar" class="aurora-header header-bg-show navbar">
			<n-space justify="space-around">
				<router-link to="/">首页</router-link>
				<router-link to="/friendLink">友情链接</router-link>
				<router-link to="/archive">时间轴</router-link>
				<router-link to="/tag-category">标签</router-link>
			</n-space>
		</header>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});
</script>