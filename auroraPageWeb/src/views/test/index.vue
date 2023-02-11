<template>
	<!--<div class="test-parent">-->
	<!--	<div class="son1">我是son1</div>-->
	<!--	<span>我是son1和son2中间的元素</span>-->
	<!--	<div class="son2">我是son2</div>-->
	<!--</div>-->
	<aurora-login :show-login-modal="true" user-uid="34856"/>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

defineComponent({name: 'index'});
</script>

<style scoped lang="css">
.test-parent {
	position: relative;
	width: 70%;
	margin: 0 auto;
	height: 20rem;
	background-color: black;
	overflow: scroll;
}
.son1 {
	height: 10rem;
	background-color: red;
}
.son2 {
	height: 15rem;
	background-color: palevioletred;
	position: absolute;
	bottom: 0;
	right: 0;
	width: 100%;
}
.son2:after {
	content: '';
	display: block;
	clear: both;
	width: 100%;
}
</style>