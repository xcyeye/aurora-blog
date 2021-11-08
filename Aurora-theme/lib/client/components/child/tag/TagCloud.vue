<template>
 <!-- 这是一个标签云 -->
  <div class="sidebar-tag-item">
    <div v-for="(item,index) in tagArr" class="sidebar-tag-single">
      <span @click="$emit('clickCloudTag',{
        tagItem: item
      })" class="home-sidebar-tag-hover" :key="index" :style="setTagItemStyle(index)">{{item}}</span>
    </div>
  </div>
</template>

<script>
export default {
  name: "TagCloud",
  props: {
    themeProperty: '',
    tagArr: {
      type: Array,
      default() {
        return []
      }
    }
  },
  computed: {
    setTagItemStyle() {
      return (index) => {
        let color = ''
        if (this.themeProperty.randomColor !== undefined && this.themeProperty.randomColor != null) {
          color = this.themeProperty.randomColor[
              this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
        }else {
          color = this.$store.state.defaultRandomColors[
              this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
        }

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