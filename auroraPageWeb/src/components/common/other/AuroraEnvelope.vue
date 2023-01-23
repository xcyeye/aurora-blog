<template>
	<div class="contact-envelope" :class="{'contact-envelope-active': contactEnvelopeActive}">
		<div class="contact-envelope-head"></div>
		<div class="contact-envelope-paper">
			<slot/>
			<div class="contact-envelope-bottom"></div>
		</div>
		<div class="contact-envelope-body"></div>
	</div>
	<div v-if="false">
		<div class="wrapper centered">
			<article class="letter">
				<div class="side">
					<h1>Contact us</h1>
					<p>
						<textarea placeholder="Your message"></textarea>
					</p>
				</div>
				<div class="side">
					<p>
						<input type="text" placeholder="Your name">
					</p>
					<p>
						<input type="email" placeholder="Your email">
					</p>
					<p>
						<button id="sendLetter">Send</button>
					</p>
				</div>
			</article>
		
		</div>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent} from "vue";

interface Props {
	contactEnvelopeActive?: boolean
}

defineComponent({name: 'AuroraEnvelope'});

const props = withDefaults(defineProps<Props>(), {
	contactEnvelopeActive: false
})

</script>

<style scoped>

</style>