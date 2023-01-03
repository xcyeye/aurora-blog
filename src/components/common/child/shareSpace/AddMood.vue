<template>
  <div :class="{cozeEditMood: openEditStatus}">
    <div v-show="getShowMoodControl(1)" class="add-mood-pwd coze-login" id="add-mood-pwd">
      <div class="poster-cancel">
        <span class="coze-home-menu-ico" @click="cancel"></span>&nbsp;
      </div>
      <div id="pro-single-mood" class="pro-single pro-message">
        <div class="add-mood-pwd" id="mood-verify-pwd">
          <span>{{verifyText}}</span>
        </div>
      </div>
      <div class="coze-donate-pay" id="">
        <form v-on:submit.prevent>
          <div class="coze-pro-common pro-message">
            <div class="donate-bottom-input coze-pro-common">
              <input autocomplete :style="setVerifyStyle" type="text" placeholder="请输入用户名" v-model="username" name="username">
            </div>
            <div class="donate-bottom-button coze-pro-common">
              <button @click="verifyIdentify">登录</button>
            </div>
          </div>
          <div class="pro-single pro-message">
            <div class="donate-bottom-input">
              <input autocomplete :style="setVerifyStyle" placeholder="请输入密码" v-model="password" name="password" type="password">
            </div>
          </div>
        </form>
      </div>
    </div>

    <div v-show="getShowMoodControl(2)" class="add-mood" id="add-mood">
      <!--<div v-show="true" class="add-mood" id="add-mood">-->
      <div class="poster-cancel">
        <span class="coze-home-menu-ico" @click="cancelEdit"></span>&nbsp;
      </div>
      <div v-if="verifyIdentifyStatus_" class="edit-mood coze-donate-pay" id="coze-donate-pay">
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
          <div class="mood_checkbox" id="mood_checkbox">
            <div class="mood_checkbox_left" id="mood_checkbox_left">
              <input v-if="!moodUpdateStatus" id="upload-file" type="file" @change="getFile($event)" name="photos" multiple="multiple">
              <input v-if="moodUpdateStatus" id="upload-file" class="show_mood_status_checkbox" type="checkbox" name="mood_show" v-model="showMood">
            </div>
            <div v-if="moodUpdateStatus" id="mood_checkbox_right" class="mood_checkbox_right">
              <span>是否显示</span>
            </div>

          </div>
          <div class="mood_update_status" id="mood_update_status">
            <button @click="edit" :disabled="isOrigin" id="upload-button">{{buttonChangeStatus ? '提交': '更新'}}</button>
            <button @click="changeMoodStatus" id="upload-button">切换</button>
          </div>
        </div>
        <div :class="{editMoodLoad: fileLoadStatus}" class="pro-single pro-message edit-upload" id="edit-mood-load">
          <span></span>
          <span class="loadText">{{resultText}}</span>
        </div>
        <div v-if="false" class="pro-single pro-message edit-upload" id="edit-mood-load">
          <div class="file-upload-status">
            <li v-for="item in progressUploadArr">
              <span class="coze-file-upload-name coze-file-upload-common">{{item.fileName}}</span>
              <div class="coze-file-upload-status-par">
                <span :style="getUploadingStyle(item.percent)" class="coze-file-upload-status coze-file-upload-common"></span>
                <span>{{item.percent}}%</span>
              </div>
            </li>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { User ,File, Object} from 'leancloud-storage'
let appId = ''
let appKey = ''
let masterKey = ''
let onlyAdministrator = true;
let avatar = 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png'
try {
  appId = __APP_ID__;
  appKey = __APP_KEY__;
  masterKey = __Master_Key__;
  avatar = __AVATAR_PATH__;
  onlyAdministrator = __ONLY_ADMINISTRATOR
}catch (e) {
  console.warn("你必须在插件中传入appId,appKey,masterKey配置项")
}

export default {
  name: "AddMood",
  data() {
    return {
      verifyText: '请登录(●￣(ｴ)￣●)',
      title: '',
      content: '',
      password: '',
      files: [],
      mood: null,
      userVerify: false,
      username: '',
      setVerifyStyle: '',
      fileLoadStatus: false,
      resultText: '',
      originTitle: '',
      originContent: '',
      isOrigin: false,
      moodUpdateStatus: false,
      showMood: true,
      buttonChangeStatus: true,
      verifyIdentifyStatus_: false,
      showMoodControl_: false,
      progressUploadArr: [],
      showUploadStatus: true,
      uploadErrNum: 0
    }
  },
  props: {
    showMoodControl: {
      type: Boolean,
      default() {
        return false
      }
    },
    verifyIdentifyStatus: {
      type: Boolean,
      default() {
        return false
      }
    },
    openEditStatus: {
      type: Boolean,
      default() {
        return false
      }
    },
    currentMoodObj: {
      type: Object,
      default() {
        return {};
      }
    }
  },
  created() {
    this.verifyIdentifyStatus_ = this.verifyIdentifyStatus
    this.showMoodControl_ = this.showMoodControl
  },
  computed: {
    getUploadingStyle() {
      return (percent) => {
        return "width: " + percent * 0.7 + "%;"
      }
    },
    getShowMoodControl() {
      return (index) => {
        if (index === 1) {
          //判断是否显示登录
          if (this.verifyIdentifyStatus_) {
            return false
          }else {
            //身份没有验证成功
            if (this.showMoodControl_) {
              return true
            }
          }
        }
        if (index === 2) {
          //验证是否显示增加说说
          if (this.verifyIdentifyStatus_ && this.showMoodControl_) {
            return true
          }else {
            return false
          }
        }
      }

    },
  },
  watch: {
    progressUploadArr() {
    },
    username() {
      if (this.username === "" && this.password === "") {
        this.verifyText = "(￣へ￣)"
      }else {
        if (this.username !== "" && this.password !== "") {
          this.verifyText = '请登录(●￣(ｴ)￣●)'
        }else {
          this.verifyText = "(●￣(ｴ)￣●)"
        }
      }
    },
    password() {
      if (this.username === "" && this.password === "") {
        this.verifyText = "(￣へ￣)"
      }else {
        if (this.username !== "" && this.password !== "") {
          this.verifyText = '请登录(●￣(ｴ)￣●)'
        }else {
          this.verifyText = "(●￣(ｴ)￣●)"
        }
      }
    },
    showMoodControl(nV) {
      this.showMoodControl_ = this.showMoodControl
    },
    verifyIdentifyStatus(nV) {
      this.verifyIdentifyStatus_ = nV
    },
    currentMoodObj() {
      this.buttonChangeStatus = false
      this.buttonChangeStatus = !this.buttonChangeStatus
      this.progressUploadArr = []
      this.moodUpdateStatus = false
      this.content = ""
      this.title = ""
      this.showMood = this.currentMoodObj.attributes.mood_show
    }
  },
  methods: {
    changeMoodStatus() {
      //判断当前的用户名是否和发布说说的一样，管理员可以编写所有的说说，但是非管理员只能编写自己的

      const currentUser = User.current();
      let publishUser = this.currentMoodObj.attributes.mood_user
      let administrator = currentUser.attributes.administrator
      let currentUsername = currentUser.attributes.username

      if (administrator === 0) {
        if (publishUser !== currentUsername) {
          this.resultText = "没有权限修改其他用户说说(￣へ￣)"
          setTimeout(() => {
            this.resultText = ""
          },1500)
          return
        }
      }

      this.buttonChangeStatus = !this.buttonChangeStatus
      this.moodUpdateStatus = !this.moodUpdateStatus
      if (this.moodUpdateStatus) {
        this.title = this.currentMoodObj.attributes.mood_title
        this.content = this.currentMoodObj.attributes.mood_content
      }else {
        this.title = ""
        this.content = ""
      }
    },
    cancel() {
      this.$emit("cancel",{
        openEditStatus: !this.openEditStatus
      })
    },
    cancelEdit() {
      this.$emit("cancel",{
        openEditStatus: !this.openEditStatus
      })
    },
    getFile(e) {
      this.formData = new FormData()
      for (let i = 0; i < e.target.files.length; i++) {
        this.files.push(e.target.files[i])
      }
    },
    edit() {
      const currentUser = User.current();
      if (!currentUser) {
        this.resultText = '你已经退出了(●￣(ｴ)￣●)'
        return;
      }

      if (onlyAdministrator === undefined) {
        onlyAdministrator = true
      }

      if (onlyAdministrator) {
        //只有管理员可以发布说说
        if (currentUser.attributes.administrator === 0) {
          this.resultText = '你不是管理员(●￣(ｴ)￣●)'
          return;
        }
      }

      if (this.moodUpdateStatus) {
        if (this.content === this.currentMoodObj.attributes.mood_content && this.currentMoodObj.attributes.mood_show === this.showMood) {
          this.resultText = "内容或显示状态为改变"
          this.isOrigin = true
          setTimeout(() => {
            this.resultText = ""
            this.isOrigin = false
          },1500)
        }else {
          this.updateData()
        }
      }else {
        if (this.content === "") {
          this.resultText = "内容为空"
          this.isOrigin = true
          setTimeout(() => {
            this.resultText = ""
            this.isOrigin = false
          },1500)
          return
        }

        if (this.originContent === this.content) {
          this.resultText = "内容未改变"
          this.isOrigin = true
          setTimeout(() => {
            this.resultText = ""
            this.isOrigin = false
          },1500)
          return
        }

        let photoDataArr = []
        let uploadPhotoArr = []
        this.originContent = this.content
        if (this.files.length === 0) {
          this.saveData([],[])
        }else {
          new Promise((resolve,reject) => {
            for (let i = 0; i < this.files.length; i++) {
              let type = this.files[i].type
              if (type === "image/jpeg" || type === "image/png" || type === "image/gif" || type === "image/jpg") {
                uploadPhotoArr.push(this.files[i])
              }
            }
            resolve()
          }).then(() => {
            let uploadingDataSet = new Set()
            this.fileLoadStatus = true
            let saveTempUpdateIndex = []
            new Promise((resolveUpload,reject) => {
              for (let i = 0; i < uploadPhotoArr.length; i++) {
                const localFile = uploadPhotoArr[i];
                const fileUpload = new File(uploadPhotoArr[i].name, localFile);
                fileUpload.save({
                  onprogress: (progress) => {
                    let uploadData = {
                      fileName: uploadPhotoArr[i].name,
                      percent: progress.percent
                    }
                    uploadingDataSet.add(uploadData)
                    this.progressUploadArr = Array.from(uploadingDataSet)
                  }
                }).then((file) => {
                  saveTempUpdateIndex.push(i)
                  let objectId = file.id
                  let photoName = file.attributes.name
                  let photoUrl = file.attributes.url

                  let photoObject = {
                    objectId: objectId,
                    photoName: photoName,
                    photoUrl: photoUrl
                  }
                  photoDataArr.push(photoObject)
                  if (saveTempUpdateIndex.length === uploadPhotoArr.length - this.uploadErrNum) {
                    resolveUpload()
                    this.fileLoadStatus = false
                  }
                }, (err) => {
                  this.uploadErrNum ++
                  this.resultText = "上传失败: " + uploadPhotoArr[i].name
                  //resolveUpload()
                  setTimeout(() => {
                    this.resultText = ""
                  },1000)
                });
              }
            }).then(() => {
              this.saveData(photoDataArr,uploadPhotoArr)
            })
          })
        }
      }
    },
    updateData() {
      const talk = Object.createWithoutData('Talk', this.currentMoodObj.id);
      talk.set('mood_title', this.title);
      talk.set('mood_content', this.content);
      talk.set("mood_like",0)
      talk.set("mood_comment","")
      talk.set("mood_show",this.showMood)
      talk.save().then(() => {
        this.resultText = "修改成功"
        setTimeout(() => {
          this.resultText = ""
        },1500)
        this.$emit("saveDataSuccess")
      })
    },
    saveData(photoDataArr,originArr) {
      const currentUser = User.current();

      let administrator = 0
      let username = ""
      if (currentUser !== undefined) {
        administrator = currentUser.attributes.administrator
        username = currentUser.attributes.username
      }

      const array  = photoDataArr;
      const Talk = Object.extend('Talk');
      const talk = new Talk();
      talk.set('mood_title', this.title);
      this.content = this.content.replaceAll("\n","<br>")
      talk.set('mood_content', this.content);
      talk.set("mood_like",0)
      talk.set("mood_comment","")
      talk.set("mood_photos",array)
      talk.set("mood_show",this.showMood)
      talk.set("mood_user",username)
      talk.save().then(() => {
        this.files = []
        let message = "发布成功"
        if (originArr.length !== 0) {
          message = message + ",图片上传成功 "+ (originArr.length - this.uploadErrNum ) + " 失败 " + this.uploadErrNum
        }
        this.resultText = message
        this.progressUploadArr = []
        setTimeout(() => {
          this.resultText = ""
        },5000)
        this.$emit("saveDataSuccess")
      },(err) => {
        this.files = []
      })
    },
    openEdit() {
      this.openEditStatus = !this.openEditStatus
    },
    verifyIdentify() {
      if (this.username === "" || this.password === "") {
        this.verifyText = "是不是少点什么 (￣へ￣)"
        return;
      }else {
        User.logIn(this.username, this.password).then((user) => {
          this.verifyText = "欢迎小主(●￣(ｴ)￣●)"
          this.verifyIdentifyStatus_ = true
          this.showMoodControl_ = true
        }, (error) => {
          this.verifyText = "密码错误 (￣へ￣)"
          // 登录失败（可能是密码错误）
          this.setVerifyStyle = "color: red;"
          setTimeout(() => {
            this.setVerifyStyle = "";
          },3000)
        });
      }
    },
  }
}
</script>