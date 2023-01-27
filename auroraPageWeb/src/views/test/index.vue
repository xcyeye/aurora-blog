<template>
	<!--<div class="test">-->
	<!--	&lt;!&ndash;<aurora-slider-picture></aurora-slider-picture>&ndash;&gt;-->
	<!--	&lt;!&ndash;<aurora-bubble>&ndash;&gt;-->
	<!--	&lt;!&ndash;	asdfkhasdf&ndash;&gt;-->
	<!--	&lt;!&ndash;	<div>lajdfh</div>&ndash;&gt;-->
	<!--	&lt;!&ndash;</aurora-bubble>&ndash;&gt;-->
	<!--	&lt;!&ndash;<aurora-card custom-style="background-color: rebeccapurple">&ndash;&gt;-->
	<!--	&lt;!&ndash;	<div>&ndash;&gt;-->
	<!--	&lt;!&ndash;		asdflhasd&ndash;&gt;-->
	<!--	&lt;!&ndash;	</div>&ndash;&gt;-->
	<!--	&lt;!&ndash;	<aurora-bubble/>&ndash;&gt;-->
	<!--	&lt;!&ndash;</aurora-card>&ndash;&gt;-->
	<!--	&lt;!&ndash;<aurora-bubble/>&ndash;&gt;-->
	<!--	&lt;!&ndash;<div class="flower">&ndash;&gt;-->
	<!--	&lt;!&ndash;	<aurora-button-flower/>&ndash;&gt;-->
	<!--	&lt;!&ndash;</div>&ndash;&gt;-->
	<!--	&lt;!&ndash;<aurora-envelope>&ndash;&gt;-->
	<!--	&lt;!&ndash;	<div class="par"></div>&ndash;&gt;-->
	<!--	&lt;!&ndash;</aurora-envelope>&ndash;&gt;-->
	<!--	<aurora-typing-css>-->
	<!--	</aurora-typing-css>-->
	<!--</div>-->
	<app-loading/>
</template>

<script lang="ts" setup>
import {defineComponent, nextTick} from "vue";
import AuroraCard from "@/components/common/other/AuroraCard.vue";
import AuroraBubble from "@/components/common/other/AuroraBubble.vue";
import AuroraTypingCss from "@/components/common/other/AuroraTypingCss.vue";

defineComponent({name: 'index'});

const click = () => {
	nextTick(() => {
		import('@/assets/flowers-less').then(result => {
			result.initBurst()
		})
	})
}
</script>

<style scoped lang="css">
.test {
	width: 10rem;
	/*height:40rem;*/
	height: auto;
	background-color: rgba(12, 22, 45, 0.91);
	position: relative;
	margin: 0 auto;
	vertical-align: middle;
}

.flower {
	height: 10rem;
	width: 10rem;
	background-color: #008AC5;
	position: relative;
}

.par {
	height: 500px;
	background-color: rebeccapurple;
	width: 100%;
	/*height: 30rem;*/
	/*width: 100%;*/
	/*background-color: red;*/
}
</style>