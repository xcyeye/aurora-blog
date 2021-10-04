<template>
  <common
      :show-sidebar="false"
      :is-show-side-bar="false"
      :is-show-top-img="false"
      :is-show-head-line="false">
    <template #center1>
      <Home :navbar-style="'margin-top: 0;'" :theme-property="themeProperty" />
    </template>
    <template #center2>
      <div class="about">
        <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
             v-for="(item) in themeProperty.about" id="article-page" class="introduce">
          <h2 class="center-common about-center" id="about-title">{{item.title}}</h2>
          <div v-if="item.showTag" id="about-tag" class="about-tag">
            <div class="tag-div" id="tag-div" :key="tagIndex" v-for="(tag,tagIndex) in item.tag">
              <TagItem
                  :theme-property="themeProperty"
                  :is-tag-item="false"
                  :tag="tag"
                  :padding="5"
              />
            </div>
          </div>
          <li v-if="!item.bar" v-for="(desc,index) in item.describe"
              class="about-desc">{{getIndex(index,item.describe.length)}} {{desc}}</li>
          <li v-if="item.bar" v-for="desc in item.describe" class="about-desc">
            <span>{{desc.name}}</span>
            <span :style="setSpanStyle(desc.score)">{{desc.score}}%</span>
          </li>
        </div>
      </div>
    </template>
    <template #center4>
      <Donate v-if="themeProperty.donate.aboutPage"/>
    </template>
    <template #center5>
      <comment/>
    </template>

  </common>
</template>
<script lang="ts">
import {
  defineComponent,
  Transition,
} from 'vue'
import Home from './Home'
import TagItem from './child/tag/TagItem.vue'
import myData from '@temp/my-data'
//导入配置属性

const network = require('../public/js/network.js')
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
      themeProperty: null
    }
  },
  computed: {
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
        let background_color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        let style = 'width: '+ newScore + "%;" + "background-color: "+background_color + ";"
        // console.log(style)
        // return 'width: '+ newScore + "%;"
        return style
      }
    },
    setIco() {
      return 'background-image: url('+this.themeProperty.ico.aboutIco+');'
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
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
    })
  },
})
</script>