<template>
  <div v-if="isShowItem" id="home-social-item">
    <a target="_blank" :href="social.aHref">
      <img :src="social.imgSrc" @mouseleave="mouseLeave" alt="">
    </a>
    <div class="show-social-common" v-if="isShow"
         id="show-img">
      <slot name="show-img" ></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomeSocial",
  data() {
    return {
      isActive: false,
      activeStyle: ''
    }
  },
  /*
  * social传入一个数组对象，格式为aHref,imgSrc形式
  * aHref为连接地址，imgSrc为图标地址
  * */
  props: {
    social: {
      type: Object,
      default() {
        return null
      }
    },
    isHomePage: {
      type: Boolean,
      default() {
        return true
      }
    }
  },
  computed: {
    getActiveClass() {
      if (this.isActive) {
        return 'social-active'
      }else {
        return 'social-no-active'
      }
    },
    isShow() {
      return this.social.showImgSrc !== undefined
    },
    isShowItem() {
      if (this.social.show) {
        if (this.isHomePage) {
          //是首页
          return this.social.isHome
        }else {
          //不是首页 显示所有的
          return true
        }
      }else {
        return this.social.show
      }
    }
  },
  methods: {
    mouseEnter() {
      console.log("-----------------")
      if (!this.isShow) return ""

      if (!this.isActive) {
        this.activeStyle = "display: block;"
      }else {
        this.activeStyle = "display: none;"
      }
      this.isActive = !this.isActive
    },
    mouseLeave() {
      if (!this.isShow) return ""

      if (!this.isActive) {
        this.activeStyle = "display: block;"
      }else {
        this.activeStyle = "display: none;"
      }
      this.isActive = !this.isActive
    }
  }
}
</script>

<style scoped>
@import "../../../styles/theme.style.css";
</style>