<template>
  <common :show-mood-edit="showMoodEdit" :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <div>
        <div class="link mood-control" v-for="(item,index) in onlineMoods">
          <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
               class="c-page"  id="c-page">
            <div class="moods-page" id="moods-page">
              <mood-item :show-online-mood="true" :moods="onlineMoods" :mood-item="item" :theme-property="themeProperty"
              />
            </div>
          </div>
        </div>
        <div class="link mood-control" v-for="(item,index) in moods">
          <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
               class="c-page"  id="c-page">
            <div class="moods-page" id="moods-page">
              <mood-item :moods="moods" :mood-item="item" :theme-property="themeProperty"
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
      onlineMoods: [],
      showMoodEdit: false,
      siteName: ''
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter

          let showOnlineMood = myData[i].frontmatter.showOnlineMood
          this.showMoodEdit = myData[i].frontmatter.showMoodEdit
          if (showOnlineMood === undefined || showOnlineMood == null) {
            showOnlineMood = false
          }

          if (this.showMoodEdit === undefined || this.showMoodEdit == null) {
            showOnlineMood = false
          }

          if (this.themeProperty.addMood != null && this.themeProperty.addMood !== undefined) {
            this.siteName = this.themeProperty.addMood.siteName
          }

          if (showOnlineMood) {
            //使用在线展示
            network.cors({
              baseURL: 'https://picture.cco.vin/',
              url: '/mood/all',
              method: 'GET',
              timeout: 70000,
              responseType: 'json',
              params: {
                siteName: this.siteName
              }
            }).then((res) => {
              this.onlineMoods = res.data.entity.moods
              this.$store.commit("setEditMoods",{
                editMoods: res.data.entity.moods
              })
            })
          }
        }
        if (myData[i].path.search("/moods/") === 0) {
          //是心情页面
          this.moods.push(myData[i])
        }
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
  /*mounted() {
    let showOnlineMood = this.themeProperty.showOnlineMood
    if (showOnlineMood === undefined || showOnlineMood == null) {
      showOnlineMood = false
    }

    if (showOnlineMood) {
      //使用在线展示
      network.cors({
        // baseURL: 'https://picture.cco.vin/',
        baseURL: 'http://localhost:8900/',
        url: '/mood/all',
        method: 'GET',
        timeout: 70000,
        responseType: 'json',
        params: {
          siteName: host
        }
      }).then((res) => {
        this.onlineMoods = res.data.entity.moods
        this.$store.commit("setEditMoods",{
          editMoods: res.data.entity.moods
        })
      })
    }
  },*/
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
    getOnlineMoods() {
      return this.$store.state.editMoods
    }
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
  watch: {
    getOnlineMoods(nV,oV) {
      this.onlineMoods = nV
    }
  }
})
</script>
<style>
  .mood-top-set {
    width: 100%;
    height: 4rem;
  }
</style>
