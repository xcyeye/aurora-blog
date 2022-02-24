<template>
    <div class="aurora-coze-slide-item-son">
      <div class="aurora-coze-slide-item-top aurora-coze-slide-radius">
        <div class="aurora-coze-slide-item-top-left aurora-coze-slide-item-top-avatar">
          <img :src="$withBase(getAvatar)" @click="goBackPage" alt="">
        </div>
        <div class="aurora-coze-slide-item-top-right">
<!--          <div class="aurora-coze-slide-info aurora-coze-slide-top-common">-->
<!--            <span>@{{moodItem.attributes.mood_user}}</span>-->
<!--            <span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}}</span>-->
<!--          </div>-->
          <div class="aurora-coze-slide-operation aurora-coze-slide-top-common">
            <div class="aurora-slide-operation-no-need">
              <span>@{{moodItem.attributes.mood_user}}</span>
              <span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}}</span>
              <span v-if="!hideMin">&nbsp;{{cozeHour}}:{{cozeMinute}}</span>
            </div>
            <div class="aurora-slide-operation-box">
              <div class="mood-edit-single-common">
                <span @click="moodComment($event,moodItem)" class="aurora-coze-font aurora-coze-custom-comment"></span>
              </div>
              <div :class="getMoodLike" class="mood-edit-single-common">
                <span :class="{'mood_like_love_active': moodLikeStatus}" @click="moodLove($event,moodItem)" class="aurora-coze-font aurora-coze-custom-love"></span>&nbsp;
                <span>{{getCozeMoodLink}}</span>
              </div>
              <div class="mood-edit-single-common">
                <span @click="moodEdit($event,moodItem)" class="aurora-coze-font aurora-coze-custom-edit"></span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <div class="aurora-coze-slide-item-bottom">
        <div class="aurora-slide-item-desc-text aurora-slide-item-desc-common">
          <li class="aurora-slide-item-font-common" v-html="moodItem.attributes.mood_content"></li>
        </div>

        <div class="aurora-slide-item-photos" v-if="moodItem.attributes.mood_photos.length">
          <ul class="aurora-coze-slide-photo-box">
            <li class="aurora-coze-slide-photo-box-li" @click="setSlideBodyBg(item.photoUrl)" v-for="(item,index) in moodItem.attributes.mood_photos" :style="setBgUrl(item.photoUrl)" :key="index">
<!--              <img id="aurora-coze-slide-photo-img" :src="item.photoUrl" alt="">-->
            </li>
          </ul>
        </div>
      </div>
    </div>
</template>

<script>
import mediumZoom from "medium-zoom";
import { Query,Object } from 'leancloud-storage'
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
  console.warn(e)
}
import gsap from "gsap";

export default {
  name: "MoodItem",
  data() {
    return {
      hideMin: false,
      title: '',
      content: '',
      moodLikeStatus: false,
      setLikeSuccess: true,

      cozeYearTemp: 0,
      cozeMonthTemp: 0,
      cozeDayTemp: 0,
      cozeHourTemp: 0,
      cozeMinuteTemp: 0,
      cozeSecondTemp: 0,
      cozeLikeTemp: 0,
      showImageHeight: false
    }
  },
  props: {
    moodItem: {},
    isActive: false
  },
  created() {
    let moodLike = this.moodItem.attributes.mood_like
    gsap.to(this.$data, {duration: 1.5, cozeLikeTemp: moodLike, ease: 'sine'})
  },
  mounted() {
    if (document.body.clientWidth < 480) {
      this.hideMin = true
    }

    let cookie = document.cookie;
    new Promise((resolve,reject) => {
      const cookieList = cookie.split(';')
      for(let i = 0; i < cookieList.length; i++) {
        const arr = cookieList[i].split('=')
        let cookieName =  'mood_like_status_' + this.moodItem.id
        let cookieOriginName = arr[0].replace(" ","")
        if (cookieName === cookieOriginName) {
          if (arr[1] === '1') {
            this.moodLikeStatus = true
          }
        }
      }
      resolve()
    })
  },
  computed: {
    setBgUrl() {
      return (photoUrl) => {
        if (this.isActive) {
          return '--aurora-coze-slide-photo-bg: url("' + photoUrl + '");'
        }
      }
    },
    setSlideItemStyle() {
      //background-image: linear-gradient(to right top, #fff1eb 0%, #ace0f9 100%);
      return ""
    },
    getCozeMoodLink() {
      return this.cozeLikeTemp.toFixed(0)
    },
    cozeYear() {
      return this.cozeYearTemp.toFixed(0)
    },
    cozeMonth() {
      return this.cozeMonthTemp.toFixed(0)
    },
    cozeDay() {
      return this.cozeDayTemp.toFixed(0)
    },
    cozeMinute() {
      return this.cozeMinuteTemp.toFixed(0)
    },
    cozeSecond() {
      return this.cozeSecondTemp.toFixed(0)
    },
    cozeHour() {
      return this.cozeHourTemp.toFixed(0)
    },
    getUpdatedTime() {
      if (this.isActive) {
        let updatedAt = this.moodItem.createdAt;
        let minutes = new Date(updatedAt).getMinutes();
        let seconds = new Date(updatedAt).getSeconds();
        let day = new Date(updatedAt).getDate();
        let month = new Date(updatedAt).getMonth() + 1;
        let year = new Date(updatedAt).getFullYear();
        let hour = new Date(updatedAt).getHours();
        gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
        gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
        gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
        gsap.to(this.$data, {duration: 2, cozeMinuteTemp: minutes, ease: 'sine'})
        gsap.to(this.$data, {duration: 2, cozeSecondTemp: seconds, ease: 'sine'})
        gsap.to(this.$data, {duration: 2, cozeHourTemp: hour, ease: 'sine'})
      }
    },
    getMoodLike() {
      if (this.moodLikeStatus) {
        return "mood_like_active"
      }
    },
    getAvatar() {
      if (__AVATAR_PATH__ !== undefined) {
        return __AVATAR_PATH__
      }else {
        return "https://pica.zhimg.com/80/v2-0653e99ab7a28223c488c27632526951_720w.jpg"
      }
    }
  },
  methods: {
    setUpdatedTime() {
      let updatedAt = this.moodItem.createdAt;
      let minutes = new Date(updatedAt).getMinutes();
      let seconds = new Date(updatedAt).getSeconds();
      let day = new Date(updatedAt).getDate();
      let month = new Date(updatedAt).getMonth() + 1;
      let year = new Date(updatedAt).getFullYear();
      let hour = new Date(updatedAt).getHours();
      gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeMinuteTemp: minutes, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeSecondTemp: seconds, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeHourTemp: hour, ease: 'sine'})
    },
    setSlideBodyBg(photoUrl) {
      this.$emit("setSlideBodyBg",{
        photoUrl: photoUrl
      })
    },
    //点击头像回到上一步网页
    goBackPage() {
      this.$router.go(-1)
    },
    updateBgStyle(isActive,bgImg) {
    },
    getLocalTime(time) {
      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      return year + "-" + month + "-" + day + " " + hours + ":" + min
    },
    moodComment(e,moodItem) {
      this.$emit("moodComment", {
        moodItem
      })
    },
    setLikeNum(moodItem) {
      this.setLikeSuccess = false
      let cookie = document.cookie;
      let mood_link_status = false
      new Promise((resolve,reject) => {
        const cookieList = cookie.split(';')
        for(let i = 0; i < cookieList.length; i++) {
          const arr = cookieList[i].split('=')
          let cookieName =  'mood_like_status_' + this.moodItem.id
          let cookieOriginName = arr[0].replace(" ","")
          if (cookieName === cookieOriginName) {
            if (arr[1] === '1') {
              mood_link_status = true
            }
          }
        }
        resolve()
      }).then(() => {
        if (!mood_link_status) {
          //没有点赞
          const query = new Query('Talk');
          query.get(this.moodItem.id).then((data) => {
            const mood_like = data.get('mood_like');
            const todo = Object.createWithoutData('Talk', this.moodItem.id);
            todo.set('mood_like', mood_like + 1);
            todo.save().then(() => {
              let expiresTime = new Date().getTime() + 864000000;
              let expires = new Date(expiresTime);
              document.cookie = "mood_like_status_" + this.moodItem.id + "=1;expires=" + expires + ";";
              this.cozeLikeTemp = mood_like + 1
              this.moodLikeStatus = true
              this.setLikeSuccess = true
            });
          });
        }else {
          //减赞
          const query = new Query('Talk');
          query.get(this.moodItem.id).then((data) => {
            const mood_like = data.get('mood_like');
            const todo = Object.createWithoutData('Talk', this.moodItem.id);
            todo.set('mood_like', mood_like - 1);
            todo.save().then(() => {
              let expiresTime = new Date().getTime() + 864000000;
              let expires = new Date(expiresTime);
              document.cookie = "mood_like_status_" + this.moodItem.id + "=0;expires=" + expires + ";";
              this.cozeLikeTemp = mood_like - 1
              this.moodLikeStatus = false
              this.setLikeSuccess = true
            });
          });
        }
      })

      this.$emit("moodLove", {
        moodItem
      })
    },
    moodLove(e,moodItem) {
      new Promise((resolve,reject) => {
        if (!this.setLikeSuccess) {
          let loadingLikeStatus = setInterval(() =>{
            if (this.setLikeSuccess) {
              clearInterval(loadingLikeStatus)
              resolve()
            }
          },50)
        }else {
          resolve()
        }
      }).then(() => {
        this.setLikeNum(moodItem)
      })
    },
    moodPoster(e,moodItem) {
      this.$emit("moodPoster", {
        moodItem
      })
    },
    moodEdit(e,moodItem) {
      this.$emit("moodEdit",moodItem)
    },
    openImg(e) {
      const zoom = mediumZoom(document.querySelector("#aurora-coze-slide-photo-img"))
      zoom.open()
    }
  }
}
</script>