<template>
  <div ref="pageItemTop" class="home-page-scroll home-page-tag-item sidebar-single-enter-animate" id="home-page-tag-item">
    <div class="home-page-tag-img" @click="goRead(event,pageItem.articleUrl)">
      <div class="home-page-img-gradual"></div>
      <img id="home-page-img" :data-src="getPageUrl(pageItem)" :src="homePageLazyLoadingImg" alt="">
    </div>

    <div class="home-page-tag-con">
      <div class="home-page-tag-title">
        <router-link :to="pageItem.articleUrl">
          <span>{{getPageItemTitle}}</span>
        </router-link>
      </div>
      <div ref="homePageDom" :class="getPageClass" class="home-page-tag-content">
        <div class="home-page-tag-content-rendered" style="display: block" v-html="pageItem.contentRendered"></div>
      </div>
      <div class="home-page-tag-bottom">
        <div v-if="pageItem.frontmatter.stick" class="home-page-stick">
          <div>
            <span class="aurora-stick aurora-iconfont-common"></span>
            <span class="aurora-stick-label">置顶</span>
          </div>
        </div>
        <div v-if="getTime !== ''" class="home-page-info">
          <div class="home-page-info-time">
            <span class="aurora-iconfont-common aurora-home-time"></span>
            <span>{{getTime}}</span>
          </div>
        </div>
        <div class="home-page-tag-tag-desc" v-if="getPageTag(pageItem).length !== 0" id="home-page-tag-tag-desc">
          <div>
            <span class="aurora-iconfont-common aurora-home-tag"></span>
            <span class="home-page-tag-span" :key="index" v-for="(item,index) in getPageTag(pageItem)">{{item}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {useThemeLocaleData} from '../../../composables'
export default {
  name: "HomePageItem",
  data() {
    return {
      homePageLazyLoadingImg: 'https://ooszy.cco.vin/img/blog-note/aurora-loading.gif'
    }
  },
  props: {
    themeProperty: '',
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
    },
    index: 0
  },
  computed: {
    getPageItemTitle() {
      let title = this.pageItem.title
      if (title === "") {
        if (this.themeProperty.tagNoTitle !== undefined) {
          title = this.themeProperty.tagNoTitle
        }else {
          title = "暂时还没有标题"
        }
      }
      return title
    },
    getPageClass() {
      return 'homePageConImg' + this.index
    },
    getTime() {
      return this.getLocalTime(this.pageItem.pageCreateTime)
    },
    getPageUrl() {
      return (item) => {
        let num1 = this.getRandomInt(-9999,999)
        let num2 = this.getRandomInt(0,300)
        let num3 = this.getRandomInt(0,30)
        let num = num2 / num3 * num1 + num2

        const themeLocale = useThemeLocaleData()

        let homePageImgApi = themeLocale.value.homePageImgApi

        if (homePageImgApi === undefined) {
          homePageImgApi = this.$store.state.defaultHomePageImgApi
        }
        let path = homePageImgApi + "?time=" + num
        return item.frontmatter.coverUrl === undefined ? path : item.frontmatter.coverUrl
      }
    },
    getPageTag() {
      return (item) => {
        if (item.tag.length === 0) {
          return item.categories
        }else {
          return item.tag
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
    goRead(e,url) {
      this.$router.push(url)
    },
    getLocalTime(time) {
      if (time === undefined) {
        //没有时间戳
        return ''
      }

      if (time === 0) {
        //没有时间戳
        return ''
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
    handleScroll() {
      let clientHeight = document.documentElement.clientHeight
      let homePageScrolls = document.querySelectorAll(".home-page-scroll")
      for (let i = 0; i < homePageScrolls.length; i++) {
        let distance_top = homePageScrolls[i].getBoundingClientRect().top
        if (distance_top < clientHeight) {
          //加载图片
          let elementsByTagName = homePageScrolls[i].getElementsByTagName("img");
          let dataSrc = elementsByTagName[0].getAttribute("data-src");
          elementsByTagName[0].setAttribute("src",dataSrc)
        }
      }
    },
    setImgDom() {
      let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
      new Promise((resolve,rejcet) => {
        for (let i = 0; i < contentHtmlImg.length; i++) {
          contentHtmlImg[i].setAttribute("src","")
        }
        resolve()
      }).then(() => {
        let contentHtmlImgs = document.querySelectorAll(".home-page-tag-content img")
        for (let i = 0; i < contentHtmlImgs.length; i++) {
          let nodeParent = contentHtmlImgs[i].parentNode
          nodeParent.removeChild(contentHtmlImgs[i])
        }
      })
    }
  },
  created() {
    if (this.themeProperty.homePageLazyLoadingImg !== undefined) {
      this.homePageLazyLoadingImg = this.themeProperty.homePageLazyLoadingImg
    }
  },
  mounted() {
    let h1s = document.querySelectorAll(".home-page-tag-content h1");
    let h2s = document.querySelectorAll(".home-page-tag-content h2");
    let h3s = document.querySelectorAll(".home-page-tag-content h3");
    let h4s = document.querySelectorAll(".home-page-tag-content h4");
    let h5s = document.querySelectorAll(".home-page-tag-content h5");

    for (let i = 0; i < 5; i++) {
      let hTag = document.querySelectorAll(".home-page-tag-content h"+ (i + 1));
      for (let j = 0; j < hTag.length; j++) {
        let aElement = hTag[j].getElementsByTagName("a");
        aElement[0].innerText = ""
      }
    }

    let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
    new Promise((resolve,rejcet) => {
      for (let i = 0; i < contentHtmlImg.length; i++) {
        contentHtmlImg[i].setAttribute("src","")
      }
      resolve()
    }).then(() => {
      let contentHtmlImgs = document.querySelectorAll(".home-page-tag-content img")
      for (let i = 0; i < contentHtmlImgs.length; i++) {
        let nodeParent = contentHtmlImgs[i].parentNode
        nodeParent.removeChild(contentHtmlImgs[i])

      }
    })
    window.addEventListener('scroll', this.handleScroll, true)
  }
}
</script>
