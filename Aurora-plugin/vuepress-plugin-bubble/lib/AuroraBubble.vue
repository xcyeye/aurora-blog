<template>
  <div class="aurora-bubble" :style="getBubbleStyle">
    <div class="aurora-bubble-box" id="aurora-bubble-box"></div>
    <canvas class="aurora-bubble-canvas" id="aurora-bubble-canvas"></canvas>
  </div>
</template>

<script>
//获取用户传入的配置
let bubbleNumber = 0.15
let bubbleAlpha = 0.7
let alphaChangeSpeed = 0.0005
let size = 0.5
let sizeChangeSpeed = 0.002
let riseSpeed = 0.9
let color = '255,255,255'
let zIndex = -1

try {
  bubbleNumber = __BUBBLE_NUMBER__
  bubbleAlpha = __BUBBLE_ALPHA__
  alphaChangeSpeed = __ALPHA_CHANGE_SPEED__
  size = __SIZE__
  sizeChangeSpeed = __SIZE_CHANGE_SPEED__
  riseSpeed = __RISE_SPEED__
  color = __COLOR__
  zIndex = __Z_INDEX__
}catch (e) {
  console.warn(e)
}

const bubble = require('./bubble')
export default {
  name: "AuroraBubble",
  computed: {
    getBubbleStyle() {
      return '--aurora-bubble-z-index: ' + zIndex + "';"
    }
  },
  mounted() {
    bubble.bubble(bubbleNumber,bubbleAlpha,alphaChangeSpeed,size,sizeChangeSpeed,riseSpeed,color)
  }
}
</script>

<style lang="css" scoped>
  .aurora-bubble {
    position: fixed;
    top: 0;
    right: 0;
    width: 100%;
    height: 100%;
    z-index: var(--aurora-bubble-z-index,-1);
  }

  .aurora-bubble-box,.aurora-bubble-canvas {
    width: 100%;
    height: 100%;
    position: absolute;
    top: 0;
    /*background: red;*/
  }
</style>