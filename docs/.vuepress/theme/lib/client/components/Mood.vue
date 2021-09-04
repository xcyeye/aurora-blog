<template>
  <common :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <!--<template #center1>
      <div class="mood-top-set">

      </div>
    </template>
    <template #center2>
      <b-center class="">
        <template #page-center1>
          <div class="mood-single">
            <div class="mood-bg">
              <div class="mood-top">
                <div class="mood-top-left">
                  <div class="mood-top-left-img moot-top-common">
                    <div>
                      <img src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt="">
                    </div>
                  </div>
                  <div class="mood-top-mood moot-top-common">
                    <span>心情</span>
                  </div>
                </div>
                <div class="mood-top-right">
                  <div>
                    <span>该主题是我自己独立开发，是一款基于vue主题进行了解，想改成自己想要的主题，太难了，最近在press，对默认学vue</span>
                  </div>
                </div>
              </div>
              <div class="mood-center">
                <div class="mood-content">
                  <span>该主题是我自己独立开发，是一款基于vuepress，对默认主题进行了大量修改，我以前使用的是博客主题，但是我对PHP不了解，想改成自己想要的主题，太难了，最近在学vue，正好看到可以使用vuepress来搭建 博客，就将默认主题改成现在这个样，并且喜欢vuepress的最主要原因是，它可以 直接将本地的markdown文档进行编译部署，我原来博客，我记了几个月的笔记，不太想再慢慢从本地复制到WordPress进行发布，以至于博客几个月没有发布文章了，虽然有技术可以解决，但是 还是喜欢vuepress，简直是懒癌福音，如果你喜欢的话，可以 在我的GitHub进行下载，使用文档我还没有写，该主题已将所有的配置进行抽离，你现在看到的所有信息，都抽离在了一个配置文件中，可以做到开箱即用，并且我自己也写了几个组件，包括文章页</span>
                </div>
              </div>
            </div>
            <div class="mood-bottom">
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
                <li class="mood-bottom-li"><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>
            </div>
          </div>
        </template>
      </b-center>
    </template>-->
    <template #center1>
      <div>
        <div class="link mood-control" v-for="(item,index) in moods">
          <!--style="background-color: rgb(192, 185, 221);"-->
          <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
               class="c-page"  id="c-page">
            <!--<h2>df</h2>-->
            <div class="moods-page" id="moods-page">
              <mood-item :mood-item="item" :theme-property="themeProperty"
              />
            </div>
          </div>
        </div>
      </div>
    </template>
    <template #center2>
      <comment/>
    </template>
  </common>
</template>

<script lang="ts">
import {
  defineComponent,
  Transition,
} from 'vue'

import MoodItem from './child/MoodItem'
import myData from '@temp/my-data'

//导入配置属性

const network = require('../public/js/network.js')
export default defineComponent({
  name: 'Mood',

  components: {
    Transition,
    MoodItem
  },
  data() {
    return {
      //这是一个数组对象
      color: null,
      ico: null,
      showMessage: null,
      themeProperty: null,
      hexToRgbColor: null,
      moods: [],
    }
  },
  created() {



    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
        if (myData[i].path.search("/moods/") === 0) {
          //是心情页面
          this.moods.push(myData[i])
        }
        /*if (myData[i].path === '/moods/') {
          this.moods = myData[i].frontmatter.mood
        }*/
      }
    })

    this.friendLinks = this.themeProperty.friendLinks
    this.siteInformation = this.themeProperty.siteInformation
    this.ico = this.themeProperty.ico.linkIco
    this.showMessage = this.themeProperty.isShowMessage
    let background_color = this.themeProperty.randomColor[
        this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    this.hexToRgbColor = this.hexToRgb(background_color)
  },
  computed: {
    setBottomStyle() {
      return (index) => {
        return "color: "+this.color + ";"
      }
    },
    setIco() {
      return 'background-image: url('+this.ico+');'
    },
    setSpanStyle() {
      return "background-color: rgba(" + this.hexToRgbColor.r + "," +
          this.hexToRgbColor.g + "," + this.hexToRgbColor.b + "," +
          (this.$store.state.varOpacity * 1.2) + ");"
    },
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    hexToRgb(hex) {
      var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    },
  },
})
</script>
<style>
  .mood-top-set {
    width: 100%;
    height: 4rem;
  }
</style>
