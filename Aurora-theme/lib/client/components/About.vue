<template>
  <common
      :show-sidebar="false"
      :is-show-side-bar="false"
      :is-show-top-img="false"
      :is-show-head-line="false">
    <template #center1>
      <Home :home-height-var="'max-content'" :socials-arr="socialsArr"
            :sidebar-width-pc-var="'65vw'"
            :show-random-say="true"
            :navbar-style="'margin-top: 0;'" :theme-property="themeProperty" />
    </template>
    <template #center2>
      <div class="about">
        <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
             v-for="(item) in themeProperty.about" :data="item.title" :key="item.title" class="introduce box">
          <div class="about-title">
            <div class="about-title-single">
              <span class="about-title-single-value">{{item.title}}</span>
            </div>
          </div>
          <div v-if="item.showTag" id="about-tag" class="about-tag">
            <div class="tag-div" id="tag-div" :key="tagIndex" v-for="(tag,tagIndex) in item.tag">
              <TagItem
                  :theme-property="themeProperty"
                  :show-tag-length="false"
                  :is-tag-item="false"
                  :tag="tag"
                  :padding="5"
              />
            </div>
          </div>
          <li v-if="!item.bar" :data="desc" :key="desc" v-for="(desc,index) in item.describe"
              class="about-desc" v-html="desc"></li>
          <li v-if="item.bar" v-for="desc in item.describe" class="about-desc">
            <span class="about-bar-title">{{desc.name}}</span>
            <span :style="setSpanStyle(desc.score)">{{desc.score}}%</span>
          </li>
        </div>
      </div>
    </template>
    <template #center4>
      <Donate v-if="getDonateAbout"/>
    </template>
    <template #center5>
      <comment :path-name="$route.path"/>
    </template>
  </common>
</template>
<script lang="ts">
import {
  defineComponent,
  Transition,
} from 'vue'
import Home from './Home.vue'
import TagItem from './child/tag/TagItem.vue'

import {useThemeData} from "../composables";

export default defineComponent({
  name: 'About',
  components: {
    Transition,
    Home,
    TagItem,
  },
  data() {
    return {
      windowHeight:0,
      themeProperty: '',
      socialsArr: []
    }
  },
  computed: {
    getDonateAbout() {

      try {
        return this.themeProperty.donate.aboutPage
      }catch (e) {
        return true
      }
    },
    getIndex() {
      return (index,length)=> {
        if (index === 0 && length === 1) {
          return ""
        }
        return index+1 + ". "
      }
    },
    setSpanStyle() {

      return (score) => {
        let newScore = score * 0.8
        let background_color = ''
        if (this.themeProperty.randomColor !== undefined) {
          background_color = this.themeProperty.randomColor[
              this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        }else {
          background_color = this.$store.state.defaultRandomColors[
              this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
        }

        let style = 'width: '+ newScore + "%;" + "background-color: "+background_color + ";"
        // console.log(style)
        // return 'width: '+ newScore + "%;"
        return style
      }
    },
    setIco() {
      let aboutIco = ''
      try {
        aboutIco = this.themeProperty.ico.aboutIco
      }catch (e) {
        aboutIco = "https://ooszy.cco.vin/img/ico/cat.svg"
      }
      return 'background-image: url('+ aboutIco +');'
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    }
  },

  created() {

    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }
    this.themeProperty = useThemeData().value

    let socials = this.themeProperty.socials
    if (socials !== undefined) {
      new Promise((resolve, reject) => {
        for (let i = 0; i < socials.length; i++) {
          if (socials[i].show) {
            this.socialsArr.push(socials[i])
          }
        }
        resolve()
      }).then(() => {

      })
    }
  },
})
</script>