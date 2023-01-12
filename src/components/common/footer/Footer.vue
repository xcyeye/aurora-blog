<template>
  <div :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle" class="sidebar-single-enter-animate footer-layout-center" v-if="!isHome">
    <div v-if="isShowFooter"
         class="footer box" id="footer">
      <FooterItem v-for="(item,index) in footerArr" :key="index" :item="item"></FooterItem>
      <div class="footer-item" v-if="isShowRunTime" id="footer-item">
        <span >{{runTime}}</span>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";

export default {
  name: "Footer",
  data() {
    return {
      footerArr: [],
      isShowRunTime: true,
      startRunTime: '8/7/2021 12:22:00',
      runTime: '',
      prefixRuntime: '小破站已运行',
    }
  },
  props: {
		currentSiteInfo: {
			type: Object as PropType<SiteSettingInfo>
		},
    isShowFooter: {
      type: Boolean,
      default() {
        return true
      }
    },
    isHome: {
      type: Boolean,
      default() {
        return false;
      }
    }
  },
  created() {
    if (this.currentSiteInfo.footerInfo !== undefined && this.currentSiteInfo.footerInfo.enable) {
      this.footerArr = this.currentSiteInfo.footerInfo
			let showThemeCopyright = this.currentSiteInfo.footerInfo.isShowThemeCopyright
			if (showThemeCopyright === undefined || showThemeCopyright == null || showThemeCopyright) {
				//默认为TRUE，显示页脚主题版权
				let themeCopyright = "theme&nbsp;<a href='https://github.com/vuepress-aurora/vuepress-theme-aurora' target='_blank'>Aurora</a>" +
					"&nbsp;by&nbsp;<a href='https://aurora.xcye.xyz/' target='_blank'>qsyyke</a>"
				// this.footerArr.push(themeCopyright)
				let set = new Set()
				for (let i = 0; i < this.footerArr.length; i++) {
					set.add(this.footerArr[i])
				}
				set.add(themeCopyright)
				let setArr = Array.from(set)
				this.footerArr = setArr
		
			}
	
			if (this.currentSiteInfo.footerInfo.isShowRunTime !== undefined) {
				this.isShowRunTime = this.currentSiteInfo.footerInfo.isShowRunTime
			}
			if (this.currentSiteInfo.footerInfo.startRunTime !== undefined) {
				this.startRunTime = this.currentSiteInfo.footerInfo.startRunTime
			}
			if (this.currentSiteInfo.footerInfo.prefixRuntime !== undefined) {
				this.prefixRuntime = this.currentSiteInfo.footerInfo.prefixRuntime
			}
			this.showRuntime()
    }else {
      this.footerArr = [
          'Copyright © by xcye All Rights Reserved'
      ]
    }
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