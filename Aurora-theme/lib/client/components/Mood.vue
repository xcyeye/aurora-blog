<template>
  <common :is-sticky-sidebar="true" :show-sidebar-animate-class="false"
          :is-show-side-bar="false" :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <!--<CozeLogin @coze-login="cozeLogin"></CozeLogin>-->
      <CozeMood @coze-cancel-edit="cozeCancelEdit" @mood-edit="moodEdit" @coze-success="cozeSuccess"></CozeMood>
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
    cozeCancelEdit(openEditStatus) {
      // if (!openEditStatus.openEditStatus) {
      //   let cozeMoodAurora = document.querySelector(".coze-mood-aurora");
      //   cozeMoodAurora.className = "theme-container coze-mood-aurora sidebar-single-enter-animate"
      // }
    },
    moodEdit(openEditStatus) {

    },
    cozeLoginOut(data) {
      console.log("点击登出按钮")
      console.log(data)
    },
    cozeLogin(data) {
      console.log("点击注册按钮")
      console.log(data)
    },
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
