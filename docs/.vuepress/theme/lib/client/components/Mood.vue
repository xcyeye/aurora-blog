<template>
  <common :is-sticky-sidebar="true" :show-mood-edit="showMoodEdit" :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <div class="mood-first">

        <!--在线编写的心情文件-->
        <div class="link mood-control" v-for="(item,index) in onlineMoods">
          <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
               class="c-page online-mood"  id="article-page">
            <div class="moods-page" id="moods-page">
              <mood-item :show-online-mood="true" :moods="onlineMoods" :mood-item="item" :theme-property="themeProperty"/>
            </div>
          </div>
        </div>

        <!--下面是在本地存放于docs/mood中的md文件-->
        <div class="link mood-control" v-for="(item,index) in moods">
          <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
               class="c-page"  id="article-page">
            <div class="moods-page" id="moods-page">
              <mood-item :moods="moods" :mood-item="item" :theme-property="themeProperty"/>
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
import {useThemeData} from "../composables";

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
      color: '',
      ico: '',
      showMessage: '',
      themeProperty: '',
      hexToRgbColor: '',
      moods: [],
      onlineMoods: [],
      showMoodEdit: false,
      siteName: ''
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

    let showOnlineMood = this.themeProperty.showOnlineMood
    this.showMoodEdit = this.themeProperty.showMoodEdit
    if (showOnlineMood === undefined) {
      showOnlineMood = false
    }

    if (this.showMoodEdit === undefined) {
      showOnlineMood = false
    }

    if (this.themeProperty.addMood !== undefined) {
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

    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path.search("/moods/") === 0) {
          //是心情页面
          this.moods.push(myData[i])
        }
      }
      resolve()
    })

    if (this.themeProperty.ico !== undefined) {
      this.ico = this.themeProperty.ico.linkIco
    }
    if (this.themeProperty.isShowMessage !== undefined) {
      this.showMessage = this.themeProperty.isShowMessage
    }

    let background_color = ''
    if (this.themeProperty.randomColor !== undefined) {
      background_color = this.themeProperty.randomColor[
          this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    }else {
      background_color = this.$store.state.defaultRandomColors[
          this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
    }
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
