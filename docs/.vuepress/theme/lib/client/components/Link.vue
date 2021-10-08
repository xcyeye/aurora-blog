<template>
  <common :is-sticky-sidebar="true" :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <div class="link">
        <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
             class="article-page link-common" id="c-link">
<!--          <h2 class="link-center center-common">我的站点</h2>
          <div class="link-self">
            <a target="_blank" class="link-self-item" :href="siteInformation.url">
              <div :style="setSpanStyle(99)" class="link-item"  id="link-item">
                <div class="link-top">
                  <div class="link-img">
                    <img id="link-img" :src="siteInformation.logo" :alt="siteInformation.title">
                  </div>
                </div>
                <div class="link-bottom" :style="setBottomStyle(99)" >
                  <div class="link-bottom-title link-bottom-common">
                    <span>{{siteInformation.title}}</span>
                  </div>
                  <div class="link-bottom-describe link-bottom-common">
                    <span id="link-bottom-describe">{{siteInformation.describe}}</span>
                  </div>
                </div>
              </div>
            </a>
          </div>-->


          <h2 class="link-center center-common">我的朋友</h2>
          <LinkItem
              :theme-property="themeProperty"
              v-for="(item,index) in friendLinks" :item="item"/>
        </div>
      </div>
      <BCenter v-if="siteInformation !== ''">
        <template #page-center1>
          <h2>友链申请</h2>
          <div class="self-site">
            <ul>
              <li>title: {{siteInformation.title}}</li>
              <li>url: {{siteInformation.url}}</li>
              <li >logo: {{siteInformation.logo}}</li>
              <li>describe: {{siteInformation.describe}}</li>
              <li>email: {{siteInformation.email}}</li>

              <div>
                <span>申请友链请按照下面格式，在此页面留言，我看到就会进行添加</span>
                <p>博客名称&nbsp;&nbsp;&nbsp;&nbsp;博客地址&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;博客描述&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;博客logo</p>
                <span>友链申请要求</span>
                <li>1.拒绝涉政/涉黄/太多广告/盈利性站点</li>
                <li>2.站点能正常访问</li>
              </div>
            </ul>
          </div>
        </template>
      </BCenter>
      <comment/>
    </template>
  </common>
</template>

<script lang="ts">
import {
  defineComponent, ref,
  Transition,
} from 'vue'

import LinkItem from './child/LinkItem'
import {useThemeData} from "../composables";
//导入配置属性

const network = require('../public/js/network.js')
export default defineComponent({
  name: 'About',
  components: {
    Transition,
    LinkItem
  },
  data() {
    return {
      //这是一个数组对象
      friendLinks: [],
      siteInformation: '',
      color: '',
      ico: '',
      themeProperty: ''
    }
  },
  computed: {
    setSpanStyle() {
      return (index) => {
        let background_color = this.themeProperty.randomColor[
            this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        return "background-color: "+background_color + ";"
      }
    },
    setBottomStyle() {
      return (index) => {
        return "color: "+this.color + ";"
      }
    },
    setIco() {
      return 'background-image: url('+this.ico+');'
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    shuffle(arr){
      let l = arr.length
      let index, temp
      while(l>0){
        index = Math.floor(Math.random()*l)
        temp = arr[l-1]
        arr[l-1] = arr[index]
        arr[index] = temp
        l--
      }
      return arr
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
    if (this.themeProperty.friendLinks !== undefined && this.themeProperty.friendLinks != null) {
      this.friendLinks = this.shuffle(this.themeProperty.friendLinks)
    }

    if (this.themeProperty.siteInformation !== undefined) {
      this.siteInformation = this.themeProperty.siteInformation
    }
    try {
      this.ico = this.themeProperty.ico.linkIco
    }catch (e) {
      this.ico = "https://ooszy.cco.vin/img/ico/tea.svg"
    }
  },
})
</script>
