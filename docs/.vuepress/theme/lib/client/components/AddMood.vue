<template>
  <div class="add-mood-button">
    <span @click="openEdit" class="">添加说说</span>
  </div>

  <div :class="{editMood: openEditStatus}">
    <div style="display: none" class="add-mood-pwd donate-pay" id="add-mood-pwd">
      <div class="poster-cancel"><span class="icon-close" @click="cancel"></span></div>
      <div class="pro-single pro-message">
        <div class="add-mood-pwd">
          <span>请验证身份</span>
        </div>
      </div>
      <div class="donate-pay" id="">
        <div class="pro-common pro-message">
          <div class="donate-bottom-input pro-common">
            <input :style="setVerifyStyle" type="text" placeholder="请输入用户名" v-model="username" name="username">
          </div>
          <div class="donate-bottom-button pro-common">
            <button @click="verifyIdentify" :disabled="isEmpty">提交</button>
          </div>
        </div>
        <div class="pro-single pro-message">
          <div class="donate-bottom-input">
            <input :style="setVerifyStyle" id="pro-input-message" placeholder="请输入密码" v-model="password" name="password" type="password">
          </div>
        </div>
      </div>
    </div>

    <div class="add-mood" style="display: none" id="add-mood">
      <div class="poster-cancel">
        <span @click="cancelEdit" class="icon-close"></span>
      </div>
      <div v-if="$store.state.verifyStatus" class="edit-mood donate-pay" id="donate-pay">
        <div class="pro-single pro-message">
          <div class="donate-bottom-input pro-common">
            <input placeholder="请输入说说标题" type="text" v-model="title" name="title">
          </div>
        </div>
        <div class="pro-single pro-message">
          <div class="donate-bottom-input pro-common edit-content">
            <textarea class="textarea-content" placeholder="请输入说说内容" v-model="content" name="" id="textarea-content" cols="30" rows="10"></textarea>
          </div>
        </div>
        <div class="pro-single pro-message edit-upload" id="upload-control">
          <input id="upload-file" type="file" @change="getFile($event)" name="photos" multiple="multiple">
          <button @click="edit" :disabled="isOrigin" id="upload-button">增加</button>
        </div>
        <div :class="{editMoodLoad: fileLoadStatus}" class="pro-single pro-message edit-upload" id="edit-mood-load">
          <span></span>
          <span class="loadText">{{resultText}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import myData from '@temp/my-data'
const network = require('../public/js/network.js')
export default {
  name: "AddMood",
  data() {
    return {
      title: '',
      content: '',
      password: '',
      files: [],
      mood: null,
      userVerify: false,
      username: '',
      setVerifyStyle: '',
      openEditStatus: false,
      fileLoadStatus: false,
      resultText: '',
      originTitle: '',
      originContent: '',
      isOrigin: false
    }
  },
  computed: {
    isEmpty() {
      if (this.username === "" || this.password === "") {
        return true
      }else {
        return false
      }
    }
  },
  created() {
  },
  methods: {
    cancel() {
      $("#add-mood-pwd").slideUp(200)
      this.openEditStatus = !this.openEditStatus
    },
    cancelEdit() {
      $("#add-mood").slideUp(200)
      this.openEditStatus = !this.openEditStatus
    },
    getFile(e) {
      this.formData = new FormData()
      for (let i = 0; i < e.target.files.length; i++) {
        this.files.push(e.target.files[i])
      }
    },
    edit() {
      if (this.content === "") {
        this.resultText = "内容为空"
        this.isOrigin = true
        setTimeout(() => {
          this.resultText = ""
          this.isOrigin = false
        },1500)
        return
      }

      if (this.originContent === this.content
          || this.originTitle === this.title) {
        this.resultText = "内容或标题未改变"
        this.isOrigin = true
        setTimeout(() => {
          this.resultText = ""
          this.isOrigin = false
        },1500)
        return
      }

      this.fileLoadStatus = true
      let formData = new FormData();
      for (let i = 0; i < this.files.length; i++) {
        formData.append("photos",this.files[i]);
      }
      formData.append("title",this.title)
      formData.append("content",this.content)
      formData.append("appId","lnZxmObbJSp3o8Zea2KXxPwat")
      formData.append("appKey","6TleVWdLeVwpOKv9eXtTQUam7")
      network.cors({
        baseURL: 'https://picture.cco.vin/',
        url: '/mood/add',
        method: 'POST',
        timeout: 70000,
        headers: {
          "Content-type": "multipart/form-data;"
        },
        data: formData,
        responseType: 'json',
      }).then((res) => {
        this.mood = res.data.entity.mood
        this.originContent = this.content
        this.originTitle = this.title
        this.fileLoadStatus = false
        this.resultText = "插入成功数: " + res.data.entity.addNum
        setTimeout(() => {
          this.resultText = ""
        },1500)

        new Promise((resolve,reject) => {
          let arr = this.$store.state.editMoods
          let temArr = []
          for (let i = 0; i < arr.length; i++) {
            temArr.push(arr[i])
          }
          resolve(temArr)
        }).then((temArr) => {

          temArr.push(this.mood)
          this.$store.commit("setEditMoods",{
            editMoods: temArr
          })
        })
      })
    },
    openEdit() {
      this.openEditStatus = !this.openEditStatus
      if (this.$store.state.verifyStatus) {
        //密码验证成功，直接显示
        $("#add-mood").slideDown(200)
      }else {
        $("#add-mood-pwd").slideToggle(200)
      }
    },
    verifyIdentify() {
      network.cors({
        baseURL: 'https://picture.cco.vin/',
        url: '/user/verify',
        method: 'POST',
        timeout: 70000,
        responseType: 'json',
        params: {
          username: this.username,
          password: this.password,
          verify: 'pwd'
        }
      }).then((res) => {
        let result = res.data.entity.exist
        if (result === 1) {
          //密码正确
          this.$store.commit("setVerifyStatus",{
            verifyStatus: true
          })

          // $("#add-mood-pwd").hide(1)
          $("#add-mood-pwd").css({
            display: 'none'
          })
          $("#add-mood").slideDown(200)
        }else {
          //密码错误，设置颜色
          this.setVerifyStyle = "color: red;"

          setTimeout(() => {
            this.setVerifyStyle = "";
          },3000)
        }
      })
    }
  }
}
</script>

<style scoped>
  @import "../styles/theme.style.css";
</style>