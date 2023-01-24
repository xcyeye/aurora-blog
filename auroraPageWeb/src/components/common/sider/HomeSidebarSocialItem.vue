<template>
  <div v-if="socialItem.show" class="sidebar-social-single" :style="getBgColor">
    <a target="_blank" :href="socialItem.aHref">
      <div class="sidebar-social-single-item">
				<svg-icon :icon="socialItem.icon" :style="socialItem.color ? `color: ${socialItem.color}` : ''"/>
      </div>
    </a>
    <div class="show-social-common" v-if="isShowSocialImg" id="show-img">
      <!--<slot name="show-img" ></slot>-->
      <img :src="socialItem.showImgSrc" alt="">
    </div>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";
import blogConfig from '@/config/blogConfig.json'
import {getRandomNum} from "@/utils";

export default {
  name: "HomeSidebarSocialItem",
  data() {
    return {
      hexRgb: '',
      themeProperty: ''
    }
  },
  props: {
		currentSiteInfo: {
			type: Object as PropType<SiteSettingInfo>
		},
    socialItem: {
      type: Object as PropType<SocialInfo>
    },
    sidebarWidthVar: {
      type: Number,
      default() {
        return 0.8
      }
    },
    sidebarRowVar: {
      type: Number,
      default() {
        return 5
      }
    },
    sidebarRowPcVar: {
      type: Number,
      default() {
        return 5
      }
    },
    sidebarWidthPcVar: {
      type: String,
      default() {
        return '20vw'
      }
    },
    sidebarCutWidth: {
      type: Number,
      default() {
        return 0.9
      }
    },
    showSocialImg: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  computed: {
    isShowSocialImg() {
      if (this.showSocialImg) {
        let showImg = this.socialItem.showImgSrc
        if (showImg === undefined) {
          return false
        }else {
          return true
        }
      }else {
        return false
      }
    },
    getBgColor() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," +
          this.hexRgb.b + ",.35); --sidebarWidth: " +
          this.sidebarWidthVar + "; --sidebarRow: " + this.sidebarRowVar +
          "; --sidebarWidthPc: " + this.sidebarWidthPcVar + "; --sidebarRowPC: " +
          this.sidebarRowPcVar + "; --sidebarCutWidth: " + this.sidebarCutWidth + ";"
    }
  },
  created() {
    let bgColor = ''
    if (blogConfig.randomColor !== undefined) {
      bgColor = blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)]
    }
    this.hexRgb = this.hexToRgb(bgColor)
  },
  methods:{
    hexToRgb(hex) {
      let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    }
  },
}
</script>

<style scoped>

</style>