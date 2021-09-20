<template>
  <div class="c-page-parent" v-if="!isHome">
    <div v-if="isShowFooter" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="footer" id="c-page">
      <FooterItem v-for="item in footerArr" :item="item"></FooterItem>
      <div class="footer-item" v-if="isShowRunTime" id="footer-item">
        <span >{{runTime}}</span>
      </div>
    </div>
  </div>

  <div v-if="isHome" class="home-footer" id="home-footer">
    <span v-html="footerArr[0]"></span>
    <span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
    <span v-html="footerArr[1]"></span>
  </div>
</template>

<script>
import FooterItem from "../child/FooterItem"
export default {
  name: "Footer",
  components: {
    FooterItem
  },
  data() {
    return {
      footerArr: null,
      isShowRunTime: true,
      startRunTime: '8/7/2021 12:22:00',
      runTime: '',
      prefixRuntime: ''
    }
  },
  props: {
    isShowFooter: {
      type: Boolean,
      default() {
        return true
      }
    },
    themeProperty: null,
    isHome: {
      type: Boolean,
      default() {
        return false;
      }
    }
  },
  created() {
    console.log("-----------footer被创建-----")
    this.footerArr = this.themeProperty.footer
    let showThemeCopyright = this.themeProperty.isShowThemeCopyright
    if (showThemeCopyright === undefined || showThemeCopyright == null || showThemeCopyright === true) {
      //默认为TRUE，显示页脚主题版权
      let themeCopyright = "theme&nbsp;<a href='https://github.com/qsyyke/vuepress-theme-ccds' target='_blank'>ccds</a>" +
          "&nbsp;by&nbsp;<a href='https://theme-ccds.cco.vin' target='_blank'>qsyyke</a>"
      // this.footerArr.push(themeCopyright)
      let set = new Set()
      for (let i = 0; i < this.footerArr.length; i++) {
        set.add(this.footerArr[i])
      }
      set.add(themeCopyright)
      let setArr = Array.from(set)
      this.footerArr = setArr

    }
    this.isShowRunTime = this.themeProperty.isShowRunTime
    this.startRunTime = this.themeProperty.startRunTime
    this.prefixRuntime = this.themeProperty.prefixRuntime
    this.showRuntime()
  },
  methods: {
    showRuntime() {
      setTimeout(this.showRuntime, 1000);
      let X = new Date(this.startRunTime);
      let Y = new Date();
      let T = Y.getTime() - X.getTime();
      let M = 24 * 60 * 60 * 1000;
      let a = T / M;
      let A = Math.floor(a);
      let b = (a - A) * 24;
      let B = Math.floor(b);
      let c = (b - B) * 60;
      let C = Math.floor((b - B) * 60);
      let D = Math.floor((c - C) * 60);
      this.runTime = this.prefixRuntime + A + "天" + B + "小时" + C + "分" + D + "秒";
    }
  }
}
</script>

<style scoped>
  /*@import "../../styles/theme.style.css";*/
</style>