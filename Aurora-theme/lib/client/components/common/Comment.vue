<template>
  <div id="control-comment" class="aurora-control-comment-box" v-if="showComment">
    <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="theme-comment-box" :class="{'show-theme-comment-box': showCommentAnimateClass}" @click="showCommentAnimate">
      <span class="aurora-comment-common aurora-iconfont-common page-comment-icon" ></span>
      <span class="aurora-comment-common aurora-comment-text">点击评论</span>
    </div>
    <div class="mobile-record aurora-comment-animate" :class="{'aurora-show-comment-animate': showCommentAnimateClass}">
      <div class="page box" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle">
        <div id="waline" class="vcomment-bottom theme-default-content"></div>
      </div>
    </div>
  </div>
</template>
<script>
import {useThemeData} from "../../composables";
import $ from "jquery";
export default {
  name: "Comment",
  data() {
    return {
      showCommentAnimateClass: false,
      themeProperty: '',
      showComment: false,
      serverURL: '',
      emojis: [
        'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/alus',
        'https://cdn.jsdelivr.net/gh/walinejs/emojis@1.0.0/bilibili',
      ],
      avatar: 'monsterid'
    }
  },
  props: {
    pathName: {
      type: String,
      default() {
        return ''
      }
    }
  },
  mounted() {
    if (this.showComment) {
      this.$nextTick(() => {
        import('@waline/client').then(module => {
          module.default({
            el: '#waline',
            serverURL: this.serverURL,
            path: this.pathName,
            avatar: this.avatar,
            emoji: this.emojis,
            visitor: true, // 阅读量统计
          })
        })

        setTimeout(() => {
          let vtabImgs = document.querySelectorAll(".vtabs .vtab img")
          for (let i = 0; i < vtabImgs.length; i++) {
            vtabImgs[i].className = "vemoji"
          }
        },2000)
      })
    }

  },
  created() {
    this.themeProperty = useThemeData().value
    if (this.themeProperty.comment !== undefined) {
      this.showComment = this.themeProperty.comment.showComment
      if (this.themeProperty.comment.serverURL === undefined) {
        console.warn("开启评论功能，请传入serverURL值，配置请查看: https://waline.js.org/guide/get-started.html  https://aurora.cco.vin/config/comment/")
      }else {
        this.serverURL = this.themeProperty.comment.serverURL
      }

      if (this.themeProperty.comment.emojis !== undefined) {
        this.emojis = this.themeProperty.comment.emojis
      }

      if (this.themeProperty.comment.avatar !== undefined) {
        this.avatar = this.themeProperty.comment.avatar
      }
    }
  },
  methods: {
    showCommentAnimate() {
      if (this.showCommentAnimateClass) {
        setTimeout(() => {
          this.showCommentAnimateClass = !this.showCommentAnimateClass
        },500)
      }else {
        this.showCommentAnimateClass = !this.showCommentAnimateClass
      }
      $(".aurora-comment-animate").slideToggle(500)
    }
  }
}
</script>

<style>
.theme-default-content:not(.custom) {
  max-width: 100%;
}
</style>
