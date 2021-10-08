<template>
  <div id="control-comment" v-if="getShowComment">
    <!-- id 将作为查询条件 -->
    <div style="display: none">
      <span :id="pathname" class="leancloud_visitors" data-flag-title="Your Article Title">
      <h2 ref="readDom" style="text-align: center;" id="comment-count"
          class="leancloud-visitors-count">444</h2>
      </span>
    </div>
    <div class="mobile-record">
      <div class="page" id="article-page" :style="$store.state.borderRadiusStyle + $store.state.opacityStyle">
        <div class="vcomments vcomment-bottom theme-default-content"></div>
      </div>
    </div>
  </div>
</template>

<script>
import myData from '@temp/my-data'
import {useThemeData} from "../../composables";
export default {
  name: "Comment",
  data() {
    return {
      Valine: '',
      pathname: '',
      themeProperty: '',
      adminUsername: null
    }
  },
  computed: {
    getAppId() {
      let appId = ''
      try {
        appId = this.themeProperty.comment.appId
      }catch (e) {
        console.log("%c 如需启用评论，请传入appId值，可查看文档\nhttps://aurora.cco.vin/","color: #9f86c0;")
      }
      return appId
    },
    getAppKey() {
      let appKey = ''
      try {
        appKey = this.themeProperty.comment.appKey
      }catch (e) {
        console.log("%c 如需启用评论，请传入appKey值，可查看文档\nhttps://aurora.cco.vin/config/comment/","color: #9f86c0;")
      }
      return appKey
    },
    getPlaceholder() {
      let placeholder = ''
      try {
        placeholder = this.themeProperty.comment.placeholder
        placeholder = placeholder === undefined || placeholder == null ? "在此输入评论内容..." : placeholder
      }catch (e) {
        placeholder = "在此输入评论内容..."
      }
      return placeholder
    },
    getAvatar() {

      let avatar = 'robohash'
      try {
        let avatar = this.themeProperty.comment.avatar
        avatar = avatar === undefined || avatar == null ? "robohash" : avatar
      }catch (e) {

      }
      return avatar
    },
    getPageSize() {
      let pageSize = 10
      try {
        pageSize = this.themeProperty.comment.pageSize
        pageSize = pageSize === undefined || pageSize == null ? 10 : pageSize
      }catch (e) {

      }
      return pageSize
    },
    getVisitor() {
      // let visitor = this.themeProperty.comment.visitor
      let visitor = true
      try {
        visitor = this.themeProperty.comment.visitor
        visitor = visitor === undefined || visitor == null ? true : visitor
      }catch (e) {

      }
      return visitor
    },
    getRecordIP() {
      // let recordIP = this.themeProperty.comment.recordIP
      let recordIP = false
      try {
        recordIP = this.themeProperty.comment.recordIP
        recordIP = recordIP === undefined || recordIP == null ? false : recordIP
      }catch (e) {

      }
      return recordIP
    },
    getAdminUsername() {
      // let adminUsername = this.themeProperty.comment.adminUsername
      let adminUsername = 'qsyyke'
      try {
        adminUsername = this.themeProperty.comment.adminUsername
        adminUsername = adminUsername === undefined || adminUsername == null ? 'aurora' : adminUsername
      }catch (e) {

      }
      return adminUsername
    },
    getShowComment() {
      let showComment = false
      try {
        showComment = this.themeProperty.comment.showComment
        showComment = showComment === undefined || showComment == null ? true : showComment
      }catch (e) {

      }
      return showComment
    }
  },
  created() {
    this.themeProperty = useThemeData().value
    try {
      this.adminUsername = this.themeProperty.comment.adminUsername
    }catch (e) {
      this.adminUsername = 'Aurora'
    }
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

      document.querySelector(".vrow .vsubmit").onclick = function (e) {
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