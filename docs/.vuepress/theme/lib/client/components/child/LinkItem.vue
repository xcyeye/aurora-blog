<template>
  <a id="link-a" target="_blank" :href="item.url">
    <div class="link-item" :style="setTopBg" id="link-item">
      <div class="link-top">
        <div class="link-img">
          <img id="link-img" :src="item.logo" :alt="item.title">
        </div>
      </div>
      <div class="link-bottom" :style="setLinkColor">
        <div class="link-bottom-title link-bottom-common">
          <span>{{item.title}}</span>
        </div>
        <div class="link-bottom-describe link-bottom-common">
          <span id="link-bottom-describe">{{item.describe}}</span>
        </div>
      </div>
    </div>
  </a>
</template>

<script>

export default {
  name: "LinkItem",
  data() {
    return {
      hexRgb: ''
    }
  },
  props: {
    themeProperty: '',
    item: null,
    /*linkTopStyle: {
      type: String,
      default() {
        return 'background-color: pink'
      }
    },
    linkBottomStyle: {
      type: String,
      default() {
        return 'background-color: #a9def9'
      }
    },
    color: {
      type: String,
      default() {
        return '#2c3e50'
      }
    }*/
  },
  created() {
    let bgColor = ''
    if (this.themeProperty.randomColor !== undefined) {
      bgColor = this.themeProperty.randomColor[this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    }else {
      bgColor = this.$store.state.defaultRandomColors[this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
    }
    this.hexRgb = this.hexToRgb(bgColor)
  },
  methods:{
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
    }
  },
  computed: {
    setTopBg() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ",.7);"
    },
    setLinkColor() {
      return 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ");"
    }
  }
}
</script>

<style scoped>

</style>