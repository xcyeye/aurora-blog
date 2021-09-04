<template>
  <div v-if="getShowComment">
    <!-- id 将作为查询条件 -->
    <!--<div>
      <span class="theme theme-common">vuepress-theme-ccds</span>
      <span class="author theme-common"> by qsyyke</span>
      <span class="version theme-common">Version</span>
      <span class="version-num theme-common">1.5.3</span>
      <span class="npm"></span>
    </div>-->
    <div style="display: none">
      <span :id="pathname" class="leancloud_visitors" data-flag-title="Your Article Title">
      <h2 ref="readDom" style="text-align: center;" id="comment-count" class="leancloud-visitors-count">444</h2>
      </span>
    </div>
    <div class="mobile-record">
      <div class="c-page page" id="c-page" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle">
        <div class="vcomments vcomment-bottom theme-default-content"></div>
      </div>
    </div>
  </div>
</template>

<script>
import myData from '@temp/my-data'
export default {
  name: "Comment",
  data() {
    return {
      Valine: '',
      pathname: '',
      themeProperty: null,
      adminUsername: null
    }
  },
  computed: {
    getAppId() {
      let appId = this.themeProperty.comment.appId
      if (appId === undefined || appId == null) {
        console.log("%c 如需启用评论，请传入appId值，可查看文档\nhttps://theme-ccds.cco.vin/","color: #9f86c0;")
        appId = ""
      }
      return appId
    },
    getAppKey() {
      let appKey = this.themeProperty.comment.appKey
      if (appKey === undefined || appKey == null) {
        console.log("%c 如需启用评论，请传入appKey值，可查看文档\nhttps://theme-ccds.cco.vin/","color: #9f86c0;")
        appKey = ""
      }
      return appKey
    },
    getPlaceholder() {
      let placeholder = this.themeProperty.comment.placeholder
      placeholder = placeholder === undefined || placeholder == null ? "在此输入评论内容..." : placeholder
      return placeholder
    },
    getAvatar() {
      let avatar = this.themeProperty.comment.avatar
      return avatar === undefined || avatar == null ? "robohash" : avatar
    },
    getPageSize() {
      let pageSize = this.themeProperty.comment.pageSize
      return pageSize === undefined || pageSize == null ? 10 : pageSize
    },
    getVisitor() {
      let visitor = this.themeProperty.comment.visitor
      return visitor === undefined || visitor === true ? true : visitor
    },
    getRecordIP() {
      let recordIP = this.themeProperty.comment.recordIP
      return recordIP === undefined || recordIP === false ? 'ccds' : recordIP
    },
    getAdminUsername() {
      let adminUsername = this.themeProperty.comment.adminUsername
      return adminUsername === undefined || adminUsername == null ? 'ccds' : adminUsername
    },
    getShowComment() {
      let showComment = this.themeProperty.comment.showComment
      return showComment === undefined || showComment === true ? 'ccds' : showComment
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
          this.adminUsername = myData[i].frontmatter.comment.adminUsername
        }
      }
      resolve()
    })
  },
  mounted() {
    new Promise((resolve,reject) => {
      setTimeout(() => {
        this.pathname = this.getPath()
        resolve()
      },200)
    }).then(() => {
      import("valine").then(res => {
        this.Valine = res.default
        this.init()
        this.getData()
      })
    })

    setTimeout(() => {
      this.addAdminId()

      document.querySelector(".vrow button").onclick = function (e) {
        //console.log(e.target)
        setTimeout(() => {
          new Promise((resolve,reject) => {
            let adminUsername = ''
            for (let i = 0; i < myData.length; i++) {
              if (myData[i].path === '/') {
                adminUsername = myData[i].frontmatter.comment.adminUsername
              }
            }
            resolve(adminUsername)
          }).then((adminUsername) => {
            let allUser = document.querySelectorAll(".vcards .vnick")
            //console.log(allUser)
            for (let i = 0; i < allUser.length; i++) {
              //console.log(allUser[i].innerText)
              //allUser[i].getEle
              if (allUser[i].innerText === adminUsername) {
                allUser[i].setAttribute("id","comment-admin")
              }else {
                allUser[i].setAttribute("id","comment-user")
              }
              //console.log(allUser[i])
            }
          })
        },500)
      }

      document.querySelector(".txt-center button").onclick = function (e) {
        //console.log(e.target)
        setTimeout(() => {
          new Promise((resolve,reject) => {
            let adminUsername = ''
            for (let i = 0; i < myData.length; i++) {
              if (myData[i].path === '/') {
                adminUsername = myData[i].frontmatter.comment.adminUsername
              }
            }
            resolve(adminUsername)
          }).then((adminUsername) => {
            let allUser = document.querySelectorAll(".vcards .vnick")
            //console.log(allUser)
            for (let i = 0; i < allUser.length; i++) {
              //console.log(allUser[i].innerText)
              //allUser[i].getEle
              if (allUser[i].innerText === adminUsername) {
                allUser[i].setAttribute("id","comment-admin")
              }else {
                allUser[i].setAttribute("id","comment-user")
              }
              //console.log(allUser[i])
            }
          })
        },500)
      }
    },1500)

  },
  methods: {
    init() {
      new this.Valine({
        el: '.vcomments',
        appId: this.getAppId,
        appKey: this.getAppKey,
        visitor: this.getVisitor,
        placeholder: this.getPlaceholder,
        avatar: this.getAvatar,
        highlight: true,
        path: this.pathname,
        pageSize: this.getPageSize,
        recordIP: this.getRecordIP,
      })
    },
    getPath() {
      return window.location.pathname
    },
    getData() {
      setTimeout(() => {
        let readCount = document.querySelector("#comment-count").innerText
        readCount = readCount === undefined || readCount === null ? 0 : readCount
        this.$store.commit("setReadCount",{
          readCount: readCount
        })
        let commentCount = document.querySelector(".vnum").innerText
        commentCount = commentCount === undefined || commentCount === null ? 0 : commentCount
        this.$store.commit("setCommentCount",{
          commentCount: commentCount
        })
      },1000)
    },
    addAdminId() {
      let allUser = document.querySelectorAll(".vcards .vnick")
      //console.log(allUser)
      //console.log(allUser)
      for (let i = 0; i < allUser.length; i++) {

        //allUser[i].getEle
        if (allUser[i].innerText === this.adminUsername) {
          allUser[i].setAttribute("id","comment-admin")
        }else {
          allUser[i].setAttribute("id","comment-user")
        }
      }
    }

  },
}
</script>

<style scoped>

</style>