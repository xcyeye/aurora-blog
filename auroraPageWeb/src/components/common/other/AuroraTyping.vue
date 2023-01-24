<template>
	<div class="aurora-typing-css aurora-typing-content">
		<div>{{typerObj.output}} |</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onMounted, ref} from "vue";
import EasyTyper from "easy-typer-js";
import {StringUtil} from "@/utils";

interface Typer {
	output: string;
	type: string;
	isEnd: boolean;
	speed: number;
	backSpeed: number;
	sleep: number;
	singleBack: boolean;
	sentencePause: boolean;
}

interface Props {
	typingContent: string
}

defineComponent({name: 'AuroraTyping'});

const props = withDefaults(defineProps<Props>(), {})

const typerObj = ref<Typer>({
	output: '',
	isEnd: false,
	speed: 100,
	singleBack: false,
	sleep: 2000,
	type: 'rollback',
	backSpeed: 100,
	sentencePause: false
})

const initTyped = (input: string) => {
	return new EasyTyper(typerObj.value, input, () => {
		setTimeout(() => {
			initTyped(input)
		}, 500)
	}, () => {})
}

onMounted(() => {
	if (StringUtil.haveLength(props.typingContent)) {
		initTyped(props.typingContent)
	}
})
</script>

<style scoped>

</style>