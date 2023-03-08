<template>
 <!-- 这是一个标签云 -->
  <div class="sidebar-tag-item">
    <div v-for="(item,index) in tagArr" class="sidebar-tag-single">
      <span @click="$emit('clickCloudTag',{
        tagItem: item
      })" class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item.title}}</span>
    </div>
  </div>
</template>

<script lang="ts">
import blogConfig from '@/config/blogConfig.json'
import {PropType} from "vue";
import {TagVo} from "@/bean/vo/article/TagVo";
import {CategoryVo} from "@/bean/vo/article/CategoryVo";
import {getRandomNum} from "@/utils";
export default {
  name: "TagCloud",
  props: {
    tagArr: {
      type: Object as PropType<Array<TagVo>> | Object as PropType<Array<CategoryVo>>,
      default() {
        return []
      }
    }
  },
  computed: {
    setTagItemStyle() {
      return (index) => {
        let color = ''
				color = blogConfig.randomColor[
					getRandomNum(0,blogConfig.randomColor.length -1)]

        let fontSize = this.getRandomInt(12,30)
        return "color: " + color + "; font-size: " + fontSize + "px;";
      }
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
  }
}
</script>

<style scoped>

</style>