<template>
  <div
       :class="{tagItem: isTagItem}"
       :style="setBackgroundStyle"
       class="tag-item" id="tag-item">
    <span style="color: rgba(255,255,255,.8)">{{tag.title}}{{itemSplit(tagPageLength)}}{{tagPageLength}}</span>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";
import {TagVo} from "@/bean/vo/article/TagVo";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";
import blogConfig from '@/config/blogConfig.json'
import {getRandomNum} from "@/utils";

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
    tag: {
			type: Object as PropType<TagVo> | Object as PropType<CategoryVo>
		},
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
          return '343'
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
		console.log(this.tag);
    let bgColor = ''
		bgColor = blogConfig.randomColor[getRandomNum(0,blogConfig.randomColor.length -1)]
    this.hexRgb = this.hexToRgb(bgColor)
  },
}
</script>