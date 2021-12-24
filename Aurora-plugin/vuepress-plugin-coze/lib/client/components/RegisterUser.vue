<template>
  <canvas v-show="registerSuccess" id="coze-flowers-canvas"></canvas>
  <div class="coze-register-user">
    <div class="add-mood-pwd coze-login" id="add-mood-pwd">
      <!--<div class="poster-cancel">
        <span class="coze-home-menu-ico"></span>&nbsp;
      </div>-->
      <div id="pro-single-mood" class="pro-single pro-message">
        <div class="add-mood-pwd" id="mood-verify-pwd">
          <span>{{verifyText}}</span>
        </div>
      </div>
      <div class="coze-donate-pay" id="">
        <form v-on:submit.prevent>
          <div class="pro-single pro-message">
            <div class="donate-bottom-input">
              <input autocomplete type="text" placeholder="请输入用户名" v-model="username" name="username">
            </div>
            <br>
            <div class="donate-bottom-input">
              <input autocomplete placeholder="输入6到20位包含数字,字母密码" v-model="password" name="password" type="password">
            </div>
            <br>
            <div class="donate-bottom-input">
              <input autocomplete placeholder="请输入邮箱" v-model="email" name="email" type="text">
            </div>
          </div>

          <div class="coze-pro-common pro-message register-button">
            <div class="donate-bottom-button coze-pro-common">
              <button @click="verifyIdentify">注册</button>
            </div>
            <div class="donate-bottom-button coze-pro-common">
              <button @click="loginOut">登出</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
</template>

<script>
import {flowers} from "../public/js/flowers";
import { Query, User,Object } from 'leancloud-storage';
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
import AddMood from "./AddMood.vue";
export default {
  name: "RegisterUser",
  components: {
    AddMood
  },
  data() {
    return {
      password: '',
      username:'',
      email: '',
      verifyText: '请注册(●￣(ｴ)￣●)',
      registerSuccess: false
    }
  },
  methods: {
    loginOut() {
      const currentUser = User.current();
      if (currentUser) {
        this.verifyText = '你已经退出登录(●￣(ｴ)￣●)'
        User.logOut();
      } else {
        this.verifyText = '你未登录或者注册(●￣(ｴ)￣●)'
      }
    },
    setMoodClass() {
      const Talk = Object.extend('Talk');
      const talk = new Talk();

      let photoArr = [{
        objectId: "61869d9b49cd7e59a03e09c8",
        photoName: "1GyQsaZ8MhjTVKi.jpg",
        photoUrl: "https://ooszy.cco.vin/img/blog-note/image-20211014230618458.png"
      },
        {
          objectId: "61869d9b49cd7e59a03e09c8",
          photoName: "1GyQsaZ8MhjTVKi.jpg",
          photoUrl: "https://ooszy.cco.vin/img/blog-note/image-20211009083404086.png"
        }
      ]
      talk.set('mood_title', "Vuepress-theme-Aurora");
      talk.set('mood_content', "A vuepress-based animation blog theme, simple, beautiful, multi-color, multiple custom functions, " +
          "providing article poster sharing, talk, photo album, comment and other features," +
          "一款基于vuepress的动漫类博客主题，简洁，漂亮，多色彩，多种自定义功能，提供文章海报分享，说说，相册，评论，侧边栏，" +
          "自动生成文章侧边栏等特色功能\n" +
          "主题地址: https://github.com/qsyyke/vuepress-theme-aurora");
      talk.set("mood_like",1000)
      talk.set("mood_comment","")
      talk.set("mood_photos",photoArr)
      talk.set("mood_show",true)
      talk.set("mood_user",this.username)
      talk.save().then(() => {
        setTimeout(() => {
          this.verifyText = "设置成功,欢迎小主(●￣(ｴ)￣●)"
        },3000)
        return
      },(err) => {
        setTimeout(() => {
          this.verifyText = "设置失败,正在重新设置..."
        },3000)
        this.setMoodClass()
      })
    },
    isEmail(str){
      let reg = /^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$/g
      return reg.test(str)
    },
    isValidUsername(str){
      let reg = /^[0-9a-zA-Z|_]{4,20}$/g
      return reg.test(str)
    },
    isValidPassword(str){
      if(str.length<6||str.length>20){
        return false
      }
      if(/[^a-zA-Z0-9_]/.test(str)){
        return false
      }
      if(/(^[a-z]+$|^[A-Z]+$|^\d+$|^_+$)/.test(str)){
        return false
      }
      return true
    },
    showSuccessFlowers() {
      let flowersNum = 1600
      if (document.body.clientWidth < 719) {
        flowersNum = 670
      }
      flowers(flowersNum)
      setTimeout(() => {
        this.registerSuccess = false
      },5000)
    },
    verifyIdentify() {
      this.registerSuccess = false

      if (!this.isValidUsername(this.username)) {
        this.verifyText = "用户名重复或格式错误(●￣(ｴ)￣●)"
        setTimeout(() => {
          this.verifyText = '请注册(●￣(ｴ)￣●)'
        },3000)
        return
      }

      if (!this.isValidPassword(this.password)) {
        this.verifyText = "密码重复或格式错误(●￣(ｴ)￣●)"
        setTimeout(() => {
          this.verifyText = '请注册(●￣(ｴ)￣●)'
        },3000)
        return
      }

      if (!this.isEmail(this.email)) {
        this.verifyText = "邮箱重复或格式错误(●￣(ｴ)￣●)"
        setTimeout(() => {
          this.verifyText = '请注册(●￣(ｴ)￣●)'
        },3000)
        return
      }

      let administrator = 0
      //判断是否存在talk数据
      new Promise((resolve,reject) => {
        const query = new Query('Talk');
        query.count().then((count) => {
          resolve()
        },(err) => {
          //没有数据，不存在这个TalkClass
          administrator = 1
          resolve()
        });
      }).then(() => {
        const user = new User();
        user.setUsername(this.username);
        user.setPassword(this.password);
        user.setEmail(this.email);
        user.set('administrator', administrator);
        user.signUp().then((user) => {
          this.registerSuccess = true
          this.showSuccessFlowers()
          // 注册成功
          if (administrator === 1) {
            this.verifyText = "注册成功,正在进行设置,请等待"
            this.setMoodClass()
          }else {
            this.verifyText = "注册成功,欢迎小主(●￣(ｴ)￣●)"
          }
        }, (error) => {
          this.verifyText = error
          // 注册失败（通常是因为用户名已被使用）
          setTimeout(() => {
            this.verifyText = '请注册(●￣(ｴ)￣●)'
          },3000)
        });
      })

      /*if (!this.isEmail(this.email) || !this.isValidUsername(this.username) || !this.isValidPassword(this.password)) {
        this.verifyText = "请正确输入信息(●￣(ｴ)￣●)"
        setTimeout(() => {
          this.verifyText = '请注册(●￣(ｴ)￣●)'
        },3000)
      }else {

      }*/
    },
  }
}
</script>
<style lang="css">
.coze-register-user {
  background: rgba(144, 241, 239, 0.35);
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -webkit-justify-content: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  position: fixed;
  top: 0px;
  right: 0;
  width: 100%;
  height: 100%;
  z-index: 100;
}
</style>