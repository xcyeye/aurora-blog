<template>
  <div
       :class="{tagItem: isTagItem}"
       :style="setBackgroundStyle"
       class="tag-item" id="tag-item">
    <span style="color: rgba(255,255,255,.8)">{{tag}}{{itemSplit(tagPageLength)}}{{tagPageLength}}</span>
  </div>
</template>

<script>
export default {
  name: "TagItem",
  data() {
    return {
      tagPageLength: "",
      hexRgb: ''
    }
  },
  props: {
    showTagLength: {
      type: Boolean,
      default () {
        return true
      }
    },
    themeProperty: '',
    tag: '',
    padding: {
      type: Number,
      default() {
        return 10
      }
    },
    isCategories: {
      type: Boolean,
      default () {
        return false
      }
    },
    isTagItem: {
      type: Boolean,
      default() {
        return true;
      }
    }
  },
  computed: {
    setBackgroundStyle() {
      return 'background-color: rgba(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + ",.45); padding: " + this.padding +"px;"
    },
    itemSplit() {
      return (tagPageLength) => {
        if (!this.showTagLength) {
          return ""
        }
        if (tagPageLength === 0 || tagPageLength === undefined) {
          return " "
        }else {
          if (this.themeProperty.split !== undefined) {
            return this.themeProperty.split
          }else {
            return ' '
          }
        }
      }
    }
  },
  methods: {
    hexToRgb(hex) {
      let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
  },

  created() {
    let bgColor = ''
    if (this.themeProperty.randomColor !== undefined) {
      bgColor = this.themeProperty.randomColor[this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    }else {
      bgColor = this.$store.state.defaultRandomColors[this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
    }
    this.hexRgb = this.hexToRgb(bgColor)

    let allPages = this.$store.state.allPageMap
    new Promise((resolve,reject) => {
      let temPage = []
      for (let i = 0; i < allPages.length; i++) {
        let tagArr = []
        if (!this.isCategories) {
          tagArr = allPages[i].tag
        }else {
          tagArr = allPages[i].categories
        }
        for (let j = 0; j < tagArr.length; j++) {
          let pageTag = tagArr[j]
          if (this.tag === pageTag) {
            temPage.push(allPages[i])
          }
          continue
        }
      }
      resolve(temPage)
    }).then((temPage) => {
      if (temPage.length >0) {
        if (this.showTagLength) {
          this.tagPageLength = temPage.length
        }else {
          this.tagPageLength = ""
        }
      }
    })
  },
}
</script>