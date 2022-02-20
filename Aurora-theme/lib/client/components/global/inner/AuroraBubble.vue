<template>
  <div class="aurora-top-bubble">
    <div class="aurora-top-bubble-par">
      <div class="aurora-top-bubble-box" id="aurora-top-bubble-box"></div>
      <canvas class="aurora-top-bubble-canvas" id="aurora-top-bubble-canvas"></canvas>
    </div>
  </div>
</template>

<script>
export default {
  name: "AuroraBubble",
  data() {
    return {

    }
  },
  props: {
    bubbleNumber: {
      type: Number,
      default() {
        return 0.15
      }
    },
    bubbleAlpha: {
      type: Number,
      default() {
        return 0.7
      }
    },
    alphaChangeSpeed: {
      type: Number,
      default() {
        return 0.0005
      }
    },
    size: {
      type: Number,
      default() {
        return 0.5
      }
    },
    sizeChangeSpeed: {
      type: Number,
      default() {
        return 0.002
      }
    },
    riseSpeed: {
      type: Number,
      default() {
        return 0.9
      }
    },
    color: {
      type: String,
      default() {
        return '255,255,255'
      }
    },
  },
  created() {

  },
  mounted() {
    this.$nextTick(() =>{
      import("../../../public/js/bubble").then(module => {
        module.bubble(this.bubbleNumber,this.bubbleAlpha,this.alphaChangeSpeed,this.size,this.sizeChangeSpeed,this.riseSpeed,this.color)
      })
    })
  },
}
</script>