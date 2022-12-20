<template>
  <div>
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
		<button @click="click">click</button>
	</div>
</template>

<script lang="ts">

import mitt, { Emitter } from 'mitt';

type Events = {
	foo: string;
	bar?: number;
};

const emitter: Emitter<Events> = mitt<Events>();

import BetterScroll from "@/components/custom/BetterScroll.vue";

export default {
  name: 'Index',
  components: {
		BetterScroll
	},
  data() {
    return {};
  },
  computed: {},
  created() {},
  methods: {
    click() {
			emitter.emit('foo', '42');
    }
  }
};
</script>

<style scoped></style>
