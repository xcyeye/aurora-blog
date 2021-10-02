<template>
  <div class="home-page-tag-item sidebar-single-enter-animate" id="home-page-tag-item">
    <div class="home-page-tag-img">
      <div class="home-page-img-gradual"></div>
      <img id="home-page-img" :src="getPageUrl(pageItem)" alt="">
    </div>

    <div class="home-page-tag-con">
      <div class="home-page-tag-title">
        <a href="">
          <span>{{pageItem.title}}</span>
        </a>
      </div>
      <div class="home-page-tag-content">
        <div class="home-page-tag-content-rendered" style="display: block" v-html="pageItem.contentRendered"></div>
      </div>
      <div class="home-page-tag-bottom">
        <div v-if="pageItem.frontmatter.stick" class="home-page-stick">
          <span class="icon-thumb-tack"></span>
        </div>
        <div class="home-page-info">
          <div class="home-page-info-time">
            <span class="icon-clock2"></span>
            <span>{{getTime(pageItem)}}</span>
          </div>
        </div>
        <div class="home-page-tag-tag-desc" id="home-page-tag-tag-desc">
          <div>
            <span class="tag-label"></span>
            <span class="home-page-tag-span" v-for="item in getPageTag(pageItem)">{{item}}</span>
          </div>
        </div>
        <div class="home-page-tag-enter">
          <div>
            <span class="icon-redo2"></span>
            <span class="home-page-read" @click="goRead(event,pageItem.articleUrl)">阅读</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "HomePageItem",
  props: {
    pageItem: {
      type: Object,
      default() {
        return ''
      }
    },
    showHomePageImg: {
      type: Boolean,
      default() {
        return false;
      }
    }
  },
  computed: {
    getTime() {
      return (item) => {
        return this.getLocalTime(item.data.git.updatedTime)
      }
    },
    getPageUrl() {
      return (item) => {
        let num1 = this.getRandomInt(-9999,999)
        let num2 = this.getRandomInt(0,300)
        let num3 = this.getRandomInt(0,30)
        let num = num2 / num3 * num1 + num2
        let path = "https://api.ixiaowai.cn/api/api.php?time=" + num
        return item.frontmatter.coverUrl === undefined ? path : item.frontmatter.coverUrl
      }
    },
    getPageTag() {
      return (item) => {
        if (item.categories.length === 0) {
          return item.tag
        }else {
          return item.categories
        }
      }
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    setHomePageImg() {
      let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
      for (let i = 0; i < contentHtmlImg.length; i++) {
        contentHtmlImg[i].setAttribute("src","https://ooszy.cco.vin/img/ico/loading.png")
        contentHtmlImg[i].setAttribute("style","transform: scale(.2);")
      }
    },
    goRead(e,url) {
      this.$router.push(url)
    },
    getLocalTime(time) {
      if (time === undefined) {
        //没有时间戳
      }
      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      return year + "-" + month + "-" + day + " "
    },
  },
  mounted() {
    let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
    for (let i = 0; i < contentHtmlImg.length; i++) {
      contentHtmlImg[i].setAttribute("src","https://ooszy.cco.vin/img/ico/loading.png")
      contentHtmlImg[i].setAttribute("style","transform: scale(.2);")
    }
  }
}
</script>

<style scoped>

</style>