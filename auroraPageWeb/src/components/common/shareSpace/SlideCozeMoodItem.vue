<template>
    <div class="aurora-coze-slide-item-son">
      <div class="aurora-coze-slide-item-top aurora-coze-slide-radius">
        <div class="aurora-coze-slide-item-top-left aurora-coze-slide-item-top-avatar">
          <img :src="useUserInfo().getUserInfo(userUid).avatar" @click="goBackPage" alt="">
        </div>
        <div class="aurora-coze-slide-item-top-right">
<!--          <div class="aurora-coze-slide-info aurora-coze-slide-top-common">-->
<!--            <span>@{{moodItem.attributes.mood_user}}</span>-->
<!--            <span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}}</span>-->
<!--          </div>-->
          <div class="aurora-coze-slide-operation aurora-coze-slide-top-common">
            <div class="aurora-slide-operation-no-need">
              <span>@{{useUserInfo().getUserInfo(userUid).username}}</span>
              <span>&nbsp;&nbsp;发布于: {{moodItem.createTime}}</span>
              <!--TODO<span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{moodItem.createdTime}}</span>-->
              <!--TODO<span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}}</span>-->
              <!-- TODO<span v-if="!hideMin">&nbsp;{{cozeHour}}:{{cozeMinute}}</span>-->
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
          <li class="aurora-slide-item-font-common" v-html="moodItem.content"></li>
        </div>

        <div class="aurora-slide-item-photos" v-if="getPictureArr">
          <ul class="aurora-coze-slide-photo-box">
            <li class="aurora-coze-slide-photo-box-li" @click="setSlideBodyBg(item)"
								v-for="(item,index) in getPictureArr"
								:style="setBgUrl(item)" :key="index">
              <!--<img id="aurora-coze-slide-photo-img" :src="item.path" alt="">-->
							<!--<n-image :src="item.path" id="aurora-coze-slide-photo-img"/>-->
							<n-image :src="item" :width="100"/>
            </li>
          </ul>
        </div>
      </div>
    </div>
</template>

<script lang="ts">
import mediumZoom from "medium-zoom";
import gsap from "gsap";
import {PropType} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {useUserInfo} from "@/stores";
import {StringUtil} from "@/utils";
import {fileApi} from "@/service";

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
		userUid: {},
    moodItem: {
			type: Object as PropType<TalkVo>
		},
    isActive: false
  },
  created() {
    let moodLike = this.moodItem.likeNumber
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
      resolve(null)
    })
  },
  computed: {
		getPictureArr() {
			if (!StringUtil.haveLength(this.moodItem.pictureSrcList)) return []
			return this.moodItem.pictureSrcList.split(",")
		},
		StringUtil() {
			return StringUtil
		},
    setBgUrl() {
      return (photoUrl: string) => {
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
        let updatedAt = this.moodItem.updatedTime;
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
  },
  methods: {
		useUserInfo,
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
    setSlideBodyBg(photoUrl: string) {
      this.$emit("setSlideBodyBg",{
        photoUrl: photoUrl
      })
    },
    //点击头像回到上一步网页
    goBackPage() {
      this.$router.go(-1)
    },
    updateBgStyle(isActive: string, bgImg: string) {
    },
    moodComment(e: Event, moodItem: TalkVo) {
      this.$emit("moodComment", {
        moodItem
      })
    },
    setLikeNum(moodItem: TalkVo) {
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
        resolve(null)
      }).then(() => {
        if (!mood_link_status) {
          //没有点赞
					// TODO 执行加赞
					// let expiresTime = new Date().getTime() + 864000000;
					// let expires = new Date(expiresTime);
					// document.cookie = "mood_like_status_" + this.moodItem.id + "=1;expires=" + expires + ";";
					// this.cozeLikeTemp = mood_like + 1
					// this.moodLikeStatus = true
					// this.setLikeSuccess = true
        }else {
          //减赞
          // TODO 执行减赞
					// let expiresTime = new Date().getTime() + 864000000;
					// let expires = new Date(expiresTime);
					// document.cookie = "mood_like_status_" + this.moodItem.id + "=0;expires=" + expires + ";";
					// this.cozeLikeTemp = mood_like - 1
					// this.moodLikeStatus = false
					// this.setLikeSuccess = true
        }
      })

      this.$emit("moodLove", {
        moodItem
      })
    },
    moodLove(e: Event, moodItem: TalkVo) {
      new Promise((resolve,reject) => {
        if (!this.setLikeSuccess) {
          let loadingLikeStatus = setInterval(() =>{
            if (this.setLikeSuccess) {
              clearInterval(loadingLikeStatus)
              resolve(null)
            }
          },50)
        }else {
          resolve(null)
        }
      }).then(() => {
        this.setLikeNum(moodItem)
      })
    },
    moodPoster(e: Event, moodItem: TalkVo) {
      this.$emit("moodPoster", {
        moodItem
      })
    },
    moodEdit(e: Event, moodItem: TalkVo) {
      this.$emit("moodEdit",moodItem)
    }
  }
}
</script>