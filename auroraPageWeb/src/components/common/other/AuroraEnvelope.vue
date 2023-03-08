<template>
	<div class="contact-envelope" :style="getEnvelopeStyle" :class="{'contact-envelope-active': contactEnvelopeActive}">
		<div ref="contactEnvelopeHead" class="contact-envelope-head"></div>
		<div class="contact-envelope-paper">
			<div ref="contactEnvelopeContent">
				<slot/>
			</div>
			<div class="contact-envelope-bottom"></div>
		</div>
		<div ref="contactEnvelopeBody" class="contact-envelope-body"></div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref} from "vue";

interface Props {
	contactEnvelopeActive?: boolean
}

defineComponent({name: 'AuroraEnvelope'});

const props = withDefaults(defineProps<Props>(), {
	contactEnvelopeActive: false
})

const contactEnvelopeHead = ref<HTMLElement>()
const contactEnvelopeHeadHeightStyle = ref<string>('')
const contactEnvelopeBody = ref<HTMLElement>()
const contactEnvelopeContent = ref<HTMLElement>()
const contactEnvelopeBodyHeightStyle = ref<string>('')

const getEnvelopeHeadHeight = computed(() => {
		if (contactEnvelopeHead.value && contactEnvelopeHead.value?.offsetWidth < 719) {
			return `height: ${contactEnvelopeHead.value?.offsetWidth / 530 * 316}px`
		}
})

const getEnvelopeBodyHeight = computed(() => {
	if (contactEnvelopeHead.value && window.screen.width < 719) {
		const top = contactEnvelopeHead.value?.offsetWidth / 530 * 316 - contactEnvelopeHead.value?.offsetWidth / 530 * 260 / 2
		return `height: ${contactEnvelopeHead.value?.offsetWidth / 530 * 260}px; top: ${top}px;`
	}
})

const getEnvelopePaperHeight = computed(() => {
	if (contactEnvelopeHead.value && window.screen.width < 719) {
		const top = 170 - (184 - (contactEnvelopeHead.value?.offsetWidth / 530 * 316 - contactEnvelopeHead.value?.offsetWidth / 530 * 260 / 2))
		return `height: ${contactEnvelopeHead.value?.offsetWidth / 530 * 260}px; top: ${top}px;`
	}
})

const getEnvelopeStyle = computed(() => {
	let style = ''
	if (contactEnvelopeHead.value && contactEnvelopeHead.value?.offsetWidth < 719) {
		style = `--contact-envelope-head: ${contactEnvelopeHead.value?.offsetWidth / 530 * 316}px;`
	}
	if (contactEnvelopeHead.value && window.screen.width < 719) {
		const top = 170 - (184 - (contactEnvelopeHead.value?.offsetWidth / 530 * 316 - contactEnvelopeHead.value?.offsetWidth / 530 * 260 / 2))
		style = style + `--contact-envelope-body: ${contactEnvelopeHead.value?.offsetWidth / 530 * 260}px; --contact-envelope-paper-top: ${top}px;`
	}
	if (contactEnvelopeHead.value && window.screen.width < 719) {
		const top = contactEnvelopeHead.value?.offsetWidth / 530 * 316 - contactEnvelopeHead.value?.offsetWidth / 530 * 260 / 2
		style = style + `--contact-envelope-body: ${contactEnvelopeHead.value?.offsetWidth / 530 * 260}px; --contact-envelope-body-top: ${top}px;`
	}
	if (contactEnvelopeContent.value) {
		style = style + `--contact-envelope-paper-content-height: ${contactEnvelopeContent.value?.offsetHeight}px;`
	}
	return style
})

onMounted(() => {
	// console.log(contactEnvelopeHead.value?.offsetWidth);
})

</script>

<style scoped>

</style>