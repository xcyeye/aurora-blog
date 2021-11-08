<template>
  <common :is-sticky-sidebar="true"
          :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <CozeMood @coze-success="cozeSuccess"></CozeMood>
    </template>
    <template #center2>
      <comment v-show="showComment" :path-name="$route.path" />
    </template>
  </common>
</template>

<script lang="ts">
import {
  defineComponent,
  Transition,
} from 'vue'
//导入配置属性
export default defineComponent({
  name: 'Mood',

  components: {
    Transition
  },
  data() {
    return {
      //这是一个数组对象
      color: '',
      ico: '',
      showComment: false
    }
  },
  created() {
    //如果手机端侧边栏打开的，那么就关闭
    if (this.$store.state.openMobileSidebar) {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: false
      })
    }
  },
  methods: {
    cozeSuccess(cozeMoodData) {
      setTimeout(() => {
        this.showComment = true
      },500)
    }
  }
})
</script>
<style>
.coze-box {
  margin-top: -1.5rem;
}
</style>
