<template>
  <common :is-sticky-sidebar="true" :is-show-side-bar="false"
          :show-sidebar-link="false"
          :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <div class="link" :data="linkItem.title" :key="linkItem.title" v-for="(linkItem,linkIndex) in themeProperty.friendLinks">
        <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
             class="box link-common" id="c-link">
          <div>
            <div class="about-title link-title">
              <div class="about-title-single">
                <span class="about-title-single-value">{{linkItem.title}}</span>
              </div>
            </div>
            <div v-for="(item,index) in linkItem.links" :data="item.url" :key="item.url">
              <LinkItem
                  :theme-property="themeProperty"
                  :item="item"/>
            </div>
          </div>
        </div>
      </div>
      <BCenter v-if="siteInformation !== ''">
        <template #page-center1>
          <div class="about-title link-title">
            <div class="about-title-single">
              <span class="about-title-single-value">友链申请</span>
            </div>
          </div>
          <div class="self-site">
            <div class="language-javascript ext-js line-numbers-mode"><pre class="language-javascript"><code><span class="token punctuation">{</span>
    title<span class="token operator">:</span> <span class="token string">"{{siteInformation.title}}"</span><span class="token punctuation">,</span><span class="token comment">//博客名称</span>
    url<span class="token operator">:</span> <span class="token string">"{{siteInformation.url}}"</span><span class="token punctuation">,</span><span class="token comment">//博客url</span>
    logo<span class="token operator">:</span> <span class="token string">"{{siteInformation.logo}}"</span><span class="token punctuation">,</span><span class="token comment">//博客logo</span>
    describe<span class="token operator">:</span> <span class="token string">"{{siteInformation.describe}}"</span><span class="token punctuation">,</span><span class="token comment">//博客描述</span>
    cover<span class="token operator">:</span> <span class="token string">"{{siteInformation.cover}}"</span><span class="token punctuation">,</span><span class="token comment">//博客截屏</span>
    <span class="token comment">//{{siteInformation.contact}}</span>
<span class="token punctuation">}</span><span class="token punctuation">,</span>
</code></pre><div class="line-numbers"><span class="line-number">1</span><br><span class="line-number">2</span><br><span class="line-number">3</span><br><span class="line-number">4</span><br><span class="line-number">5</span><br><span class="line-number">6</span><br><span class="line-number">7</span><br><span class="line-number">8</span><br></div></div>

            <ul class="link-info-desc">
              <li v-for="(linkItem,index) in siteInformation.otherDescribe" :key="linkItem" v-html="linkItem"></li>
            </ul>
          </div>
        </template>
      </BCenter>
      <comment :path-name="$route.path" />
    </template>
  </common>
</template>

<script lang="ts">
import {
  defineComponent, ref,
  Transition,
} from 'vue'

import LinkItem from './child/LinkItem.vue'

import {useThemeData} from "../composables";

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
    getRandomLinks() {
      return (linkArr) => {
        let linkArrs = []
        this.shuffleArray(linkArr).then((arr) => {
          linkArrs = arr;
        })
      }
    },
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
    shuffleArray(array) {
      return new Promise((resolve,reject) => {
        for (let i = array.length - 1; i > 0; i--) {
          let random1 = this.getRandomInt(0,array.length -1)
          let temp = array[i]
          array[i] = array[random1]
          array[random1] = temp
          array[i] = {
            name: 'qsyyke',
            sex: 'nan'
          }
        }
        resolve(array)
      })
    },
    shuffle(arr){
      return new Promise((resolve,reject) => {
        let l = arr.length
        let index, temp
        while(l>0){
          index = Math.floor(Math.random()*l)
          temp = arr[l-1]
          arr[l-1] = arr[index]
          arr[index] = temp
          l--
        }
        resolve(arr)
      })
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
      for (let i = 0; i < this.themeProperty.friendLinks.length; i++) {
        /*this.shuffleArray(this.themeProperty.friendLinks[i].links).then((arr) => {
          // this.themeProperty.friendLinks[i].links = arr
        })*/
      }
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
