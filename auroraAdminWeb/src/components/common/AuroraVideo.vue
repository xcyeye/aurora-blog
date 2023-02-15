<template>
	<div class="aurora-video">
		<video-player
			id="auroraVideoPlayer"
			class="video-player vjs-theme-forest aurora-video-player"
			crossorigin="anonymous"
			playsinline
			:controls="true"
			:responsive="true"
			:liveui="true"
			language="zh"
			:playback-rates="[0.5, 0.75, 1.0, 1.25, 1.5, 2.0]"
			:fluid="fluid"
			:fill="false"
			:user-actions="userActions"
			:poster="getVideoPoster"
			:sources="videoSourceList"
			:volume="0.3"
			:children="[
     	// 控制条
      'mediaLoader',
      'posterImage',
      // 未播放时的中间播放按钮
      'bigPlayButton',
      'loadingSpinner',
      'controlBar',
      'textTrackDisplay'
    ]"
			:control-bar="{
      volumePanel: false
    }"
			@play="handlePlayOrPause(true)"
			@pause="handlePlayOrPause(false)"
			@enterFullWindow="handleEnterFullWindowOrExit(true)"
			@exitFullWindow="handleEnterFullWindowOrExit(false)"
			@fullscreenchange="handleFullScreenChange"
		/>
	</div>
</template>

<script lang="ts" setup>
import {VideoPlayer} from "@videojs-player/vue";
import {computed, defineComponent, ref} from "vue";
import 'video.js/dist/video-js.css'
import {StringUtil} from "@/utils";

interface Props {
	/** 是否容器全屏 播放的时候 */
	fluid: boolean,
	videoSourceList: Array<VideoSource>
}

defineComponent({name: 'AuroraVideo'});

const props = withDefaults(defineProps<Props>(), {
	fluid: true
})

const isFullWindow = ref(false)
const isPlay = ref(false)

const userActions = {
	hotkeys: {
		muteKey: function(event: KeyboardEvent) {
			// disable mute key
		},
		fullscreenKey: function(event: KeyboardEvent) {
			return event.which === 70
		},
		playPauseKey: function(event: KeyboardEvent) {
			return event.which === 32
		}
	}
}

const getVideoPoster = computed(() => {
	if (props.videoSourceList && props.videoSourceList.length > 0) {
		if (StringUtil.haveLength(props.videoSourceList[0].poster)) {
			return props.videoSourceList[0].poster
		}
		return ''
	}
	return ''
})

const handlePlayOrPause = (playStatus: boolean) => {
	isPlay.value = playStatus
}

const handleEnterFullWindowOrExit = (enterStatus: boolean) => {
	isFullWindow.value = enterStatus
}

const handleDurationchange = (ev) => {
}

const handleFullScreenChange = (event) => {
}

</script>

<style scoped>

</style>
