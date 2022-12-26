<template>
	<div>
		<show-picture :show-drawer-operation="false" :picture-src-arr="pictureArr"></show-picture>
	</div>
</template>

<script setup lang="ts">
import ShowPicture from "@/components/file/ShowPicture.vue";

const pictureArr = [
	{
		uid: '1603239846169460736',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-wqve97.png'
	},
	{
		uid: '1603239846169460737',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-q22jv7.png'
	},
	{
		uid: '1607274975418302464',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/image1672039190585.png'
	},
	{
		uid: '1607276299765596160',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/image1672039506271.png'
	},
	{
		uid: '1607277208964874240',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/image1672039723040.png'
	},
	{
		uid: '1603239846169460736',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-wqve97.png'
	},
	{
		uid: '1603239846169460736',
		src: 'http://127.0.0.1/aurora-upload/png/2022/12/wallhaven-wqve97.png'
	},

]
</script>

<style scoped>
.light-green {
	height: 108px;
	background-color: rgba(0, 128, 0, 0.12);
}
.green {
	height: 108px;
	background-color: rgba(0, 128, 0, 0.24);
}
</style>
