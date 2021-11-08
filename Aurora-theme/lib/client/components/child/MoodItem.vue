<template>
  <div>
    <div class="mood-text"></div>
    <div class="mood-item" id="mood-item">
      <div class="mood-item-left mood-item-img-parent" id="mood-item-left">
        <div class="mood-item-img" id="mood-item-img">
          <img :src="getAvatar" alt="">
        </div>
      </div>
      <div class="mood-item-right" id="mood-item-right">

        <div id="mood-item-content" class="mood-item-content mood-item-right-common">
          <span>{{content}}</span>
        </div>
      </div>
    </div>
    <div class="mood-img">
      <!--<li><img id="mood-bottom-img" src="https://ooszy.cco.vin/img/blog-public/btg2.jpg" alt=""></li>-->
      <div class="mood-img-left">
        <!--<poster/>-->
      </div>
      <div class="mood-img-right" id="mood-img-right">
        <div class="mood-li-control">
          <li v-for="(item,index) in imgs" id="mood-img-li">
            <img @click="openImg" class="medium-zoom-image" id="mood-bottom-img" :src="item" alt="">
          </li>
        </div>
      </div>
    </div>
    <div class="mood-poster">
      <poster :title="title" :content="content" />
    </div>
  </div>
</template>

<script>
import mediumZoom from 'medium-zoom'

import $ from 'jquery'
export default {
  name: "MoodItem",
  data() {
    return {
      hexToRgbColor: null,
      imgs: [],
      title: '',
      content: ''
    }
  },
  props: {
    themeProperty: '',
    moodItem: null,
    moods: ''
  },
  created() {
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
  mounted() {
    //获取图片
    $(".mood-text").get(0).innerHTML = this.moodItem.contentRendered
    //获取标题
    this.title = this.moodItem.title
    let contents = $(".mood-text p")
    for (let i = 0; i < contents.length; i++) {
      this.content = this.content + contents[i].innerText
    }

    let imgs = $(".mood-text img");
    for (let i = 0; i < imgs.length; i++) {
      this.imgs.push(imgs[i].src)
    }

  },
  computed: {
    setSpanStyle() {
      return "background-color: rgba(" + this.hexToRgbColor.r + "," +
          this.hexToRgbColor.g + "," + this.hexToRgbColor.b + "," +
          (this.$store.state.varOpacity * 1.2) + ");"
    },
    getAvatar() {
      return this.getHeroImage
      //return this.moodItem.avatar === undefined ? this.getHeroImage : this.moodItem.avatar
    },
    getHeroImage() {
      let src = this.themeProperty.heroImg
      if (src === undefined) {
        console.log("you need to set the heroImg field value,the default is: https://ooszy.cco.vin/img/blog-public/avatar.jpg")
        return "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      }else {
        return src
      }
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
    openImg(e) {
      const zoom = mediumZoom(e.target)
      zoom.open()
    }
  }
}
</script>

<style scoped>

</style>