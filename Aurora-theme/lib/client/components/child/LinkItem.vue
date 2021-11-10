<template>
  <a id="link-a" target="_blank" :href="item.url">
    <div class="link-item" :style="setTopBg" id="link-item">
      <div class="link-top">
        <div class="link-img">
          <img id="link-img" :src="getLinkLogo(item.logo)" :alt="item.title">
        </div>
      </div>
      <div class="link-bottom" :style="setLinkColor(item.cover)">
        <div class="link-bottom-title link-bottom-common">
          <span :key="item.title" :data="item.title">{{getLinkTitle(item.title,getRandomIntNum)}}</span>
        </div>
        <div class="link-bottom-describe link-bottom-common">
          <span id="link-bottom-describe" :data="item.describe" :key="item.describe">{{getLinkDescribe(item.describe,getRandomIntNum)}}</span>
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
    item: null
  },
  created() {
    //console.log(this.item)
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
    getRandomIntNum() {
      let min = Math.ceil(0);
      let max = Math.floor(999999999);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    getLinkTitle() {
      return (title,num) => {
        return title
      }
    },
    getLinkDescribe() {
      return (describe,num) => {
        return describe
      }
    },
    getLinkLogo() {
      return (logo) => {
        let randomNum1 = this.getRandomInt(0,9999999999)
        let randomNum2 = this.getRandomInt(0,9999999999)
        let logoPath = logo + "?time=" + randomNum1 + logo + randomNum2
        return logoPath
      }
    },
    setTopBg() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ",.85);"
    },
    setLinkColor() {
      return (cover) => {
        //return 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + "); background-image: url("+ cover +");"
        if (cover === undefined || cover === "") {
          return "background-color: white;" + 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ");"
        }else {
          let randomNum1 = this.getRandomInt(0,9999999999)
          let randomNum2 = this.getRandomInt(0,9999999999)
          let logoPath = cover + "?time=" + randomNum1 + cover + randomNum2
          return "background-image: url("+ logoPath +");"
        }
      }
    }
  }
}
</script>