<template>
  <div :class="getPosition" class="adsense" id="adsense">
    <div :style="setBackgroundImg" class="adsense-item" id="adsense-item">
      <h3 class="adsense-message">{{adsenseMessage}}</h3>
      <div class="insert-adsense">
        <slot ></slot>
      </div>
    </div>
  </div>
</template>

<script>

export default {
  name: "AdSense",

  props: {
    adsensePosition: {
      type: String,
      default() {
        return 'center'
      }
    },
    adsenseMessage: {
      type: String,
      default() {
        return '官网用户专享'
      }
    },
    adsenseBackgroundImg: {
      type: String,
      default() {
        return '/img/33.jpg'
      }
    },
    adsenseScript: {
      type: String,
      default() {
        return '<a href="https://vipblogs.cn">主题</a>'
      }
    }
  },
  computed: {
    getPosition() {
      return  this.adsensePosition === 'center' ? 'adsense-center' : 'adsense-right'
    },
    setBackgroundImg() {
      return "background: url("+ this.adsenseBackgroundImg + ");"
    }
  }
}
</script>

<style scoped>

</style>