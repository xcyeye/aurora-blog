<template>
  <div class="aurora-slide-body" :style="slideBodyBg">
      <div class="aurora-slide-shade aurora-slide-radius">
        <div class="aurora-slide-box-style-box">
          <div class="aurora-slide-box-style"></div>
        </div>
        <swiper
            :grabCursor="true"
            :effect="'creative'"
            :creativeEffect="{
      prev: {
        shadow: false,
        translate: ['-120%', 0, -500],
      },
      next: {
        shadow: false,
        translate: ['120%', 0, -500],
      },
    }"
            :modules="modules"
    class="aurora-slide-box aurora-slide-radius"
  >
          <AuroraBubble v-if="showAboutPageBubble"/>
    <swiper-slide v-slot="{ isActive }" v-for="(item,index) in abouts" :key="index" :style="setSlideItemStyle(index)" class="aurora-slide-item aurora-slide-radius">
      <div :data="updateBgStyle(isActive,item.bgImg,index)" class="aurora-slide-item-son">
            <div class="aurora-slide-item-top aurora-slide-radius">
              <div class="aurora-slide-item-top-left aurora-slide-item-top-avatar">
                <img :src="getAvatar" @click="goBackPage" alt="">
              </div>
              <div class="aurora-slide-item-top-right">
                <div class="aurora-slide-item-top-title aurora-slide-item-top-common">
                  <span>{{item.title}}</span>
                </div>
                <div v-if="item.tag" class="aurora-slide-item-top-tag aurora-slide-item-top-common">
                  <span v-for="(tagItem,index) in item.tag" :key="index">{{tagItem}}</span>
                </div>
              </div>
            </div>

            <div class="aurora-slide-item-bottom">
<!--              <div class="aurora-slide-item-bottom-box">-->
<!--                -->
<!--              </div>-->
              <div v-if="item.bar" class="aurora-slide-item-desc-common aurora-slide-item-desc-bar">
                <li class="aurora-slide-item-font-common aurora-slide-item-bar-li" v-for="(descItem,index) in item.describe" :key="index">
                  <span class="about-bar-title aurora-slide-item-bar-title">{{descItem.name}}</span>
                  <span class="aurora-slide-item-bar-score" :style="setBarStyle(descItem.score)">{{descItem.score}}%</span>
                </li>
              </div>
              <div v-else class="aurora-slide-item-desc-text aurora-slide-item-desc-common">
                <li class="aurora-slide-item-font-common" v-for="(descItem,index) in item.describe" :key="index" v-html="descItem"></li>
              </div>
            </div>
        </div>
    </swiper-slide>
  </swiper>
      </div>
  </div>
</template>
<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";

// Import Swiper styles
import "swiper/css";

import "swiper/css/effect-creative";

// import required modules
import { EffectCreative } from "swiper";

import {useThemeData, useThemeLocaleData} from "../composables";
import {withBase} from "@vuepress/client";
export default {
    name: "About",
    data() {
        return {
            themeProperty: {},
            abouts: [],
            randomColors: [],
            hexRgb: '',
            slideBodyBg: '',
          showAboutPageBubble: true
        }
    },
    components: {
        Swiper,
        SwiperSlide,
    },
    setup() {
        return {
        modules: [EffectCreative],
        };
    },
    computed: {
      getAvatar() {
        const themeLocale = useThemeLocaleData()
        let src = themeLocale.value.heroImg
        if (src === undefined) {
          console.warn("%c you need to set the heroImg field value,the default is: https://pica.zhimg.com/80/v2-0653e99ab7a28223c488c27632526951_720w.jpg","color: pink;")
          return "https://pica.zhimg.com/80/v2-0653e99ab7a28223c488c27632526951_720w.jpg"
        }else {
          return  withBase(src)
        }
      },
        setSlideBodyBg() {

        },
        setSlideItemStyle() {
            //background-image: linear-gradient(to right top, #fff1eb 0%, #ace0f9 100%);
            return (index) => {

            }
        },
        setBarStyle() {
            return (score) => {
                return "background-color:" + this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] + "; width: " + score * 0.85 + "%"
            }
        }
    },
    created() {
        this.themeProperty = useThemeData().value
        this.abouts = useThemeData().value.about
        this.randomColors = useThemeData().value.randomColor
        this.showAboutPageBubble = this.themeProperty.showAboutPageBubble

    },
    methods: {
        //点击头像回到上一步网页
        goBackPage() {
            this.$router.go(-1)
        },
        getInearGradientStyle() {
            //let hexRgb1 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
            //let hexRgb2 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
            return "--aurora-slide-bgImg: linear-gradient(to right top, " + this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] + " 0%, "+ this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] +" 100%);"
        },
        updateBgStyle(isActive,bgImg,index) {
            if(isActive) {
                if(bgImg !== undefined) {
                  //将图片设置为背景图片
                  this.slideBodyBg = "--aurora-slide-bgImg: url(" + withBase(bgImg) + ");"
                }else {
                  //如果没有图片，那么则使用渐变颜色作为背景颜色
                  this.slideBodyBg = this.getInearGradientStyle()
                }
            }

        },
        hexToRgb(hex) {
            let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
            return result ? {
                r: parseInt(result[1], 16),
                g: parseInt(result[2], 16),
                b: parseInt(result[3], 16)
            } : null;
        },
        getRandomInt(min, max) {
            min = Math.ceil(min);
            max = Math.floor(max);
            return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
        },
    },
};
</script>