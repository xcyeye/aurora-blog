<template>
  <div v-if="isShowItem" id="home-social-item">
    <div class="home-social-single-item" style="--sidebarWidth: 1;" :style="getBgColor">
      <a target="_blank" :href="social.aHref">
        <div class="sidebar-social-single-item">
          <svg class="home-sidebar-social-icon" aria-hidden="true">
            <use :xlink:href="social.symbol"></use>
          </svg>
        </div>
      </a>
    </div>
    <div class="show-social-common" v-if="isShow"
         id="show-img">
      <slot name="show-img" ></slot>
    </div>
  </div>
</template>

<script>
import {useThemeData} from "../../../composables";

export default {
  name: "HomeSocial",
  data() {
    return {
      isActive: false,
      activeStyle: '',
      hexRgb: '',
      themeProperty: ''
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
    getBgColor() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ",.35);"
    },
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
  created() {
    this.themeProperty = useThemeData().value
    let bgColor = ''
    if (this.themeProperty.randomColor !== undefined) {
      bgColor = this.themeProperty.randomColor[this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    }else {
      bgColor = this.$store.state.defaultRandomColors[this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
    }
    this.hexRgb = this.hexToRgb(bgColor)
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
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    hexToRgb(hex) {
      let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    }
  }
}
</script>

<style scoped>
/*@import "../../../styles/theme.style.css";*/
</style>