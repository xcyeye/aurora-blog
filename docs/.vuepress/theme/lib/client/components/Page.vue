<template>

  <div class="c-page-parent">
    <!--<div class="page-top-share">
        <poster/>
      </div>-->
    <main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="page" id="c-page">
      <slot name="top" />
      <!--:adsense-script="adsenseArr[0].script"-->
      <div class="page-top-share">
        <poster :key="+new Date()" :title="originPageData.title" :content="posterContent"/>
      </div>
      <div class="theme-default-content pageContent">
        <AdSense adsense-position="right"
                 :adsense-background-img="adsenseArr[0].adsenseBackgroundImg"
                 :adsense-message="adsenseArr[0].adsenseMessage"
        >
          <div v-html="adsenseArr[0].script">
          </div>
        </AdSense>
        <Content />
      </div>

      <PageMeta />
      <PageNav />
      <slot name="bottom" />
    </main>
  </div>
  <div>
    <donate v-if="themeProperty.donate.articlePage" />
    <!--<Poster/>-->
  </div>
  <div class="recommend-page">
    <RecommendPage :theme-property="themeProperty"/>
  </div>

  <!--<poster/>-->
  <comment></comment>
</template>

<script>
import Mood from "./Mood";
import { defineComponent } from 'vue'
import PageMeta from './PageMeta.vue'
import PageNav from './PageNav.vue'
import AdSense from './AdSense'
import RecommendPage from "./RecommendPage";
import {usePageData, useRouteLocale, useSiteData} from "@vuepress/client";
import $ from 'jquery'

export default defineComponent({
  name: 'Page',
  components: {
    PageMeta,
    PageNav,
    AdSense,
    RecommendPage,
    Mood
  },
  data() {
    return {
      adsenseArr: null,
      insertAdsenseRule: '',
      adsenseLength: 3,
      lazyLoadingImg: null,
      originPageData: '',
      posterContent: 'sdfsdf',
      title: ''
    }
  },
  props: {
    themeProperty: null
  },
  created() {
    //console.log("-------------create-----------")
    setTimeout(() => {
      this.getPosterText().then((res) => {
        this.posterContent = res
        //console.log(this.posterContent)
      })
    },1000)
    const page = usePageData()
    this.originPageData = page
    //console.log(this.originPageData.title)
    //this.title = this.originPageData.value.
    this.$emit('getHeadLine',page.value.title)
    this.adsenseArr = this.themeProperty.adsenseArr
    this.insertAdsenseRule = this.themeProperty.insertAdsenseRule
    this.adsenseLength = this.themeProperty.adsenseLength
    let lazyLoadingImg = this.themeProperty.lazyLoadingImg
    this.lazyLoadingImg = lazyLoadingImg === undefined ? "https://ooszy.cco.vin/img/blog-public/ljz.gif" : lazyLoadingImg

    $(window).on("scroll",() => {
      //console.log("scroll")
      this.start()
    })

  },

  methods: {
    getPosterText() {
      return new Promise((resolve,reject) => {
        let allP = $(".pageContent p")
        let content = ''
        if (allP.length > 30) {
          for (let i = 0; i < 30; i++) {
            content = content + allP[i].innerText
          }
        }else {
          for (let i = 0; i < allP.length; i++) {
            content = content + allP[i].innerText
          }
        }
        content = content.replace(/\r\n/g,"")
        content = content.replace(/\n/g,"");
        content = content.replace(/\s/g,"")
        content = content.replace("#","")
        content = content.replace("#\n","")
        content = content.replace("##\n","")
        content = content.replace("##","")

        this.$store.commit("setPosterContent",{
          posterContent: content
        })
        this.$store.commit("setPosterStatus",{
          posterStatus: 1
        })
        resolve(content)
      })
    },
    start() {
      //console.log("----------start-----------")
      let imgs = $(".pageContent img")
      for (let i = 0; i < imgs.length; i++) {
        let clientWidth = document.body.clientWidth
        let img = imgs[i]
        let top = img.offsetTop
        let scrollTop = $(window).scrollTop();
        let scrollCz = ''
        if (clientWidth < 600) {
          scrollCz = top - scrollTop - 350
        }else {
          scrollCz = top - (scrollTop + 50)
        }
        if (scrollCz < 0) {
          this.loadImg(img)
        }
      }
    },
    loadImg(img) {
      //console.log(img)
      let originSrc = img.getAttribute("data-origin")
      //console.log("data-origin: " + originSrc)
      img.setAttribute("src",originSrc)
    },
    getContent(allH2,length) {
      return new Promise((resolve,reject) => {
        let h2Content = ''
        let h2KeyContent = ''
        for (let i = 0; i < length; i++) {
          //console.log(allH2[i].innerText)
          let h2Text = allH2[i].innerText.replace("#\n","")
          h2Content = h2Content + "," + (i + 1) +"、" + h2Text
          h2KeyContent = h2KeyContent + "," + h2Text
        }

        h2Content = h2Content.substr(1,h2Content.length)
        h2KeyContent = h2KeyContent.substr(1,h2KeyContent.length)

        resolve({h2Content,h2KeyContent})
      })
    },
    getPContent(pContents) {
      return  new Promise((resolve,reject) => {
        let pText = ''
        for (let i = 0; i < 3; i++) {
          pText = pText + pContents[i].innerText
        }
        resolve(pText)
      })
    },
    setDesc(desc) {

      /*let frontmatterDesc = this.originPageData.frontmatter.description
      if (frontmatterDesc === undefined || frontmatterDesc === null) {
        let isExist = false

        new Promise((resolve,reject) => {
          let childmete = $("head").get(0).children;
          for (let i = 0; i < childmete.length; i++) {
            let name = childmete[i].getAttribute("name");

            if (name === "description") {
              console.log(childmete[i])
              //childmete[i].setAttribute("cont",desc);
              $("head").get(0).removeChild(childmete[i])
              isExist = true
            }
          }
          resolve()
        }).then(() => {
          /!*console.log(isExist)
          if (!isExist) {
            let metaKey = $('<meta name="description" content="'+desc+'">')
            //console.log(metaKey.get(0))
            $("head").get(0).appendChild(metaKey.get(0))
          }*!/

          let metaKey = $('<meta name="description" content="'+desc+'">')
          //console.log(metaKey.get(0))
          $("head").get(0).appendChild(metaKey.get(0))
        })
      }*/

      let isExist = false

      new Promise((resolve,reject) => {
        let childmete = $("head").get(0).children;
        for (let i = 0; i < childmete.length; i++) {
          let name = childmete[i].getAttribute("name");

          if (name === "description") {
            //console.log(childmete[i])
            /*setTimeout(() => {

            },500)*/
            childmete[i].setAttribute("content",desc);
            //$("head").get(0).removeChild(childmete[i])
            isExist = true
          }
        }
        resolve()
      }).then(() => {
        if (!isExist) {
          let metaKey = $('<meta name="description" content="'+desc+'">')
          //console.log(metaKey.get(0))
          $("head").get(0).appendChild(metaKey.get(0))
        }
      })



    },
    setKeyword(keyword) {
      let isExist = false

      new Promise((resolve,reject) => {
        let childmete = $("head").get(0).children;
        for (let i = 0; i < childmete.length; i++) {
          let name = childmete[i].getAttribute("name");

          if (name === "keyword") {
            childmete[i].setAttribute("content",keyword);

            isExist = true
          }
        }
        resolve()
      }).then(() => {
        if (!isExist) {
          let metaKey = $('<meta name="keyword" content="'+keyword+'">')
          $("head").get(0).appendChild(metaKey.get(0))
        }
      })
    },
    setMeta() {
      //设置meta标签的keyword和description
      //获取tip类名的内容 theme-default-content

      let frontmatterDesc = this.originPageData.frontmatter.description
      let frontmatterKeyword = this.originPageData.frontmatter.keyword

      let h2Content = ''
      let h2KeyContent = ""

      let allH2 = $(".pageContent h2")
      let allContents = $(".pageContent p")

      if (frontmatterKeyword === undefined
          || frontmatterKeyword === null
          || frontmatterKeyword.length === 0) {
        //frontmatterKeyword中，不存在keyword

        if (allH2.length < 13) {
          this.getContent(allH2,allH2.length).then((content) => {
            h2KeyContent = content.h2KeyContent
            h2Content = content.h2Content

            this.getPContent(allContents).then((pText) => {
              if (h2KeyContent.length < 80) {
                h2KeyContent = h2KeyContent + pText.substr(0,80 - h2KeyContent.length)
                this.setKeyword(h2KeyContent)
              }else {
                this.setKeyword(h2KeyContent)
              }
            })
          });
        }else {
          this.getContent(allH2,13).then((content) => {
            h2KeyContent = content.h2KeyContent
            h2Content = content.h2Content

            this.getContent(allH2,allH2.length).then((content) => {
              h2KeyContent = content.h2KeyContent
              h2Content = content.h2Content

              this.getPContent(allContents).then((pText) => {
                if (h2KeyContent.length < 80) {
                  h2KeyContent = h2Content + pText.substr(0,80 - h2KeyContent.length)
                  this.setKeyword(h2KeyContent)
                }else {
                  this.setKeyword(h2KeyContent)
                }
              })
            });
          });
        }
      }else {
        let keyword = ''
        new Promise((resolve,reject) => {
          for (let i = 0; i < frontmatterKeyword.length; i++) {
            keyword = keyword + "," + frontmatterKeyword[i]
          }
          keyword = keyword.substr(1,keyword.length)
          resolve()
        }).then(() => {
          this.setKeyword(keyword)
        })
      }

      setTimeout(() => {
        if (frontmatterDesc === undefined || frontmatterDesc === null) {
          /*
          * 如果页面中，没有进行frontmatter的配置，则自动生成desc
          * 获取页面中，4个h2标签中的内容，并且获取第一段的内容作为描述
          * */


          if (allH2.length < 13) {
            this.getContent(allH2,allH2.length).then((content) => {
              h2KeyContent = content.h2KeyContent
              h2Content = content.h2Content

              this.getPContent(allContents).then((pText) => {
                if (h2Content.length < 140) {
                  h2Content = h2Content + pText.substr(0,140 - h2Content.length)
                  this.setDesc(h2Content)
                }else {
                  this.setDesc(h2Content)
                }
              })
            });
          }else {
            this.getContent(allH2,13).then((content) => {
              h2KeyContent = content.h2KeyContent
              h2Content = content.h2Content

              this.getContent(allH2,allH2.length).then((content) => {
                h2KeyContent = content.h2KeyContent
                h2Content = content.h2Content

                this.getPContent(allContents).then((pText) => {
                  if (h2Content.length < 140) {
                    h2Content = h2Content + pText.substr(0,140 - h2Content.length)
                    this.setDesc(h2Content)
                  }else {
                    this.setDesc(h2Content)
                  }
                })
              });
            });
          }
        }
      },500)


    }
  },
  mounted() {
    let imgs = $(".pageContent img")
    for (let i = 0; i < imgs.length; i++) {
      let originSrc = imgs[i].src
      imgs[i].setAttribute("data-origin",originSrc)
      imgs[i].src = this.lazyLoadingImg
    }

    let h1s = $("#c-page h1")
    if (h1s.length > 0) {
      $(h1s[0]).css('display','none')
    }

    //console.log("--------------m-------------")
    this.setMeta()
    //下面就是自动添加广告

    new Promise((resolve,reject) => {
      let adsenseNodes = []
      for (let i = 0; i < this.adsenseArr.length; i++) {

        if (this.adsenseArr[i].position === 'center') {
          let node = $("<AdSense adsense-position=\""+this.adsenseArr[i].position+"\"\n" +
              "                 :adsense-background-img=\""+this.adsenseArr[i].adsenseBackgroundImg+"\"\n" +
              "                 :adsense-message=\""+this.adsenseArr[i].adsenseMessage+"\"\n" +
              "\n" +
              "        >\n" +
              "          <div v-html=\""+this.adsenseArr[i].script+"\">\n" +
              "\n" +
              "          </div>\n" +
              "        </AdSense>").get(0)

          adsenseNodes.push(node)
        }
      }
      resolve(adsenseNodes)
    }).then((adsenseNodes) => {
      /*console.log(adsenseNodes)
      console.log("allContents.length: "+ allContents.length)
      console.log("allContents.length / this.insertAdsenseRule: "+ allContents.length / this.insertAdsenseRule)

      let div = $("<h2>插入测试</h2>").get(0)
      $(".theme-default-content").get(0).insertBefore(div,allContents[6])
      if (allContents.length < this.insertAdsenseRule) {
        //所有p标签的长度不够，则直接插入在最后
        console.log(allContents[allContents.length -5])
        $(".theme-default-content").get(0).insertBefore(adsenseNodes[0],allContents[allContents.length -5])
      }

      if (allContents.length > this.insertAdsenseRule *2) {
        for (let i = 1; i < allContents.length / this.insertAdsenseRule; i++) {
          if (adsenseNodes.length >= allContents.length / this.insertAdsenseRule) {
            //文章中间的长度大于所有p标签除以广告规则的长度
            if (this.adsenseLength >= allContents.length / this.insertAdsenseRule) {
              for (let j = 0; j < allContents.length / this.insertAdsenseRule; j++) {
                $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
              }
            }else {
              //小于广告规则长度
              for (let j = 0; j < this.adsenseLength; j++) {
                $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
              }
            }
          }else {
            //所有符合的广告小于
            for (let j = 0; j < adsenseNodes.length; j++) {
              $(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
            }
          }
          //$(".theme-default-content").get(0).insertBefore(adsenseNodes[j],allContents[this.insertAdsenseRule * i])
        }
      }else {
        console.log(allContents[this.insertAdsenseRule])
        $(".theme-default-content").get(0).insertBefore(adsenseNodes[0],allContents[this.insertAdsenseRule])
      }*/
    })
  }
})
</script>

