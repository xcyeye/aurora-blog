<!--此组件是样式控制面板-->
<template>
  <div class="setColor" :style="$store.state.borderRadiusStyle + $store.state.fontFamilyStyle + $store.state.fontColorStyle">
    <div v-if="isShowIco"
         @click="clickSetColor"
         id="setIco" class="setIco bg_color">
    </div>

    <div class="welcome-parent" id="welcome-parent">
      <div @click="clickSet" :class="customClass" :style="getColorStyle" class="m-4 p-4 f4 color-shadow-small bg-gray-800-mktg rounded-2 signup-content-container welcome" id="welcome">
        <span @click="clickSetColor" class="cancel aurora-iconfont-common aurora-style-cancel" id="cancel"></span>
        <h1 class="common-style" id="sr-only-h2"></h1>
        <div class="custom-top custom-common">
          <div class="custom-top-span custom-common-span">
            <span @click="setImg" class="aurora-iconfont-common aurora-style-img home-welcome-custom-icon"></span>&nbsp;
          </div>
          <div class="custom-top-span custom-common-span">
            <span @click="setIsFitter" class="aurora-iconfont-common aurora-style-blur home-welcome-custom-icon"></span>&nbsp;
          </div>
        </div>

        <div class="custom-bottom custom-common">
          <div style="flex: 1" class="custom-bottom-span custom-common-span">
            <li class="custom-li" v-for="(item,index) in fontColorArr">
              <span :style="setColorBack(item)" :data-color="item" @click="setColor($event,item)"></span>
            </li>
          </div>
        </div>

        <div class="custom-bottom custom-common">
          <div style="flex: 1" class="custom-bottom-span custom-common-span">
            <!--fontColor-->
            <li class="custom-li" v-for="(item,index) in fontFamilyArr">
              <span :style="setFamilyBack(item)" :data-color="item" @click="setFont($event,item)">{{getShowFont}}</span>
            </li>
          </div>
        </div>

        <div class="custom-bottom custom-common">
          <div class="custom-bottom-span custom-common-span">
            圆角
          </div>
          <div style="flex: 3" class="custom-bottom-span custom-common-span">
            <div class="input-range">
              <div class="input-left">
                <input autocomplete min="1" max="40" v-model="borderRadius" step="1" type="range">
              </div>
              <div class="input-right">
                {{borderRadius}}
              </div>
            </div>
          </div>
        </div>
        <!--<div class="custom-bottom custom-common">
          <div class="custom-bottom-span custom-common-span">
            模糊度
          </div>
          <div style="flex: 3" class="custom-bottom-span custom-common-span">
            <div class="input-range">
              <div class="input-left">
                <input autocomplete min="0" max="70" v-model="blur" step="0.1" type="range">
              </div>
              <div class="input-right">
                {{blur}}
              </div>
            </div>
          </div>
        </div>-->
        <div class="custom-bottom custom-common">
          <div class="custom-bottom-span custom-common-span">
            透明度
          </div>
          <div style="flex: 3" class="custom-bottom-span custom-common-span">
            <div class="input-range">
              <div class="input-left">
                <input autocomplete min="0" max="1" v-model="opacity" step="0.01" type="range">
              </div>
              <div class="input-right">
                {{opacity}}
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
export default {
  name: "HomeWelcome",
  data() {
    return {
      setColorStyle: "display: none;",
      fontArr: [],
      colorArr: [],
      isFitter: true,
      opacity: 1,
      blur: 5,
      borderRadius: 30,
      maxFontColorArr: 8,
      fontColorArr: [],
      fontFamilyArr: [],
      currentColor: '',
      currentFont: ''
    }
  },
  created() {
    if (this.themeProperty.maxFontColorArr !== undefined) {
      this.maxFontColorArr = this.themeProperty.maxFontColorArr
    }

    if (this.themeProperty.fontFamily !== undefined && this.themeProperty.fontFamily != null) {
      this.fontArr = this.themeProperty.fontFamily
    }else {
      this.fontArr = this.$store.state.defaultFont
    }

    if (this.themeProperty.fontColor !== undefined && this.themeProperty.fontColor != null) {
      this.colorArr = this.themeProperty.fontColor
    }else {
      this.colorArr = this.$store.state.defaultFontColor
    }

    this.isFitter = this.themeProperty.isFitter === undefined ? true : this.themeProperty.isFitter

    if (this.colorArr.length < this.maxFontColorArr) {
      this.fontColorArr = this.colorArr
    }else {
      this.fontColorArr = this.colorArr.slice(0,this.maxFontColorArr)
    }

    if (this.fontArr.length < this.maxFontColorArr) {
      this.fontFamilyArr = this.fontArr
    }else {
      this.fontFamilyArr = this.fontArr.slice(0,this.maxFontColorArr)
    }

    //获取主题配置默认圆角，模糊度，透明度
    if (this.themeProperty.defaultBorderRadius !== undefined) {
      this.borderRadius = this.themeProperty.defaultBorderRadius
    }

    if (this.themeProperty.defaultOpacity !== undefined) {
      this.opacity = this.themeProperty.defaultOpacity
    }

    if (this.themeProperty.defaultBlur !== undefined) {
      this.blur = this.themeProperty.defaultBlur
    }

    this.$store.commit('setVarFilterBlur',{
      varFilterBlur: this.blur
    })
    this.$store.commit('setVarBorderRadius',{
      varBorderRadius: this.borderRadius
    })

    this.$store.commit('setVarOpacity',{
      varOpacity: this.opacity
    })
  },
  computed: {
    getShowFont() {
      return this.themeProperty.showFont === undefined ? "程" : this.themeProperty.showFont
    },
    getColorStyle() {
      return this.setColorStyle
    },
    setColorBack() {
      return (item) => {
        return "background-color: " + item + ";";
      }
    },
    setFamilyBack() {
      return (item) => {
        return "font-family: " + item + ";";
      }
    }
  },
  methods: {
    clickSetColor() {
      $("#welcome").slideToggle(350)
      let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })

      setInterval(() => {
        let intervalTime = +new Date()
        let lastTime = intervalTime - this.$store.state.welcomeOpenTime
        let slideTime = this.themeProperty.slideTime
        if (slideTime === undefined || slideTime == null) {
          slideTime = 3000
        }

        if ($("#welcome").css("display") === 'block') {
          if (lastTime > slideTime) {
            $("#welcome").slideUp(350)
          }
        }
      },1500)
    },
    clickSet() {
      let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })
    },
    setColor(e,color) {
      this.currentColor = color
      this.$store.commit('setFontColorStyle',{
        fontFamily: this.currentFont,
        color: this.currentColor
      })
      this.$emit('setBodyStyle')
    },
    setFont(e,font) {
      this.currentFont = font
      this.$store.commit('setFontFamilyStyle',{
        fontFamily: this.currentFont,
        color: this.currentColor
      })
      this.$emit('setBodyStyle')
    },
    setImg() {
      this.$emit('setBodyWallpaper')
    },
    setIsFitter() {
      //isFitter
      //设置是否要毛玻璃特性，通过类名控制
      this.$emit('setIsFitter',!this.isFitter)
      this.isFitter = !this.isFitter
      this.$store.commit('setIsFitter',{
        isFitter: this.isFitter
      })
    }
  },
  props: {
    isShowIco: {
      type: Boolean,
      default() {
        return true
      }
    },
    themeProperty: '',
    customClass: {
      type: String,
      default() {
        return "";
      }
    }
  },
  watch: {
    blur(newBlur,oldBlur) {
      this.$store.commit('setVarFilterBlur',{
        varFilterBlur: newBlur
      })
      this.blur = this.$store.state.varFilterBlur
      let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })
    },
    opacity(newOpacity,oldOpacity) {
      this.$store.commit('setVarOpacity',{
        varOpacity: newOpacity
      })
      this.opacity = this.$store.state.varOpacity
      let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })
    },
    borderRadius(newBorderRadius,oldBorderRadius) {
      this.$store.commit('setVarBorderRadius',{
        varBorderRadius: newBorderRadius
      })
      this.borderRadius = this.$store.state.varBorderRadius
      let time = +new Date()
      this.$store.commit("setWelcomeOpenTime",{
        time: time
      })
    }
  }
}
</script>
