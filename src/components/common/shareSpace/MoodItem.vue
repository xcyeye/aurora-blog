<template>
  <div class="coze-mood-item-parent">
    <div class="coze-mood-item" id="coze-mood-item">
      <div class="mood-item-left mood-item-img-parent" id="mood-item-left">
        <div class="mood-item-img" id="coze-mood-item-img">
          <!--<img :src="useUserInfo().getUserInfo(userUid).avatar" alt="">-->
          <n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" round :size="50"/>
        </div>
      </div>
      <div class="mood-item-right" id="mood-item-right">
        <div class="coze-mood-item-content">
          <div id="mood-item-content" class="mood-item-content mood-item-right-common">
            <span v-html="moodItem.content"></span>
            <div class="coze-mood-time">
              <span>@{{useUserInfo().getUserInfo(userUid).username}}</span>
              <span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}}</span>
            </div>
            <slot name="coze-mood-content"></slot>
          </div>
        </div>
      </div>
    </div>
    <div class="mood-img">
      <div class="mood-img-left">
        <slot name="coze-img"></slot>
      </div>
      <div class="mood-img-right" id="mood-img-right">
        <div class="mood-li-control">
          <li v-for="(item,index) in pictureArr" :data="item.path" :key="item.uid" id="mood-img-li">
            <!--<img @click="openImg" class="medium-zoom-image" id="mood-bottom-img" :src="item.path" :alt="item.fileName">-->
            <n-image :src="item.path" id="mood-bottom-img" />
          </li>
        </div>
      </div>
    </div>
    <div class="mood-edit">
      <div class="coze-mood-bottom-left"></div>
      <div class="mood-edit-right">
        <slot name="coze-mood-bottom-left"></slot>
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
        <slot name="coze-mood-bottom-right"></slot>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import gsap from "gsap";
import mediumZoom from 'medium-zoom'
import {PropType} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {useUserInfo} from "@/stores";
import {FileVo} from "@/bean/vo/file/fileVo";
import {StringUtil} from "@/utils";
import {fileApi} from "@/service";

const pictureArr: Array<FileVo> = []
export default {
  name: "MoodItem",
  data() {
    return {
      title: '',
      content: '',
      moodLikeStatus: false,
      setLikeSuccess: true,
			pictureArr,
      cozeYearTemp: 0,
      cozeMonthTemp: 0,
      cozeDayTemp: 0,
      cozeLikeTemp: 0,
      showImageHeight: false
    }
  },
  props: {
    moodItem: {
			type: Object as PropType<TalkVo>
		},
		userUid: {
			type: String
		}
  },
  created() {
    let moodLike = this.moodItem.likeNumber
    gsap.to(this.$data, {duration: 1.5, cozeLikeTemp: moodLike, ease: 'sine'})
		if (StringUtil.haveLength(this.moodItem.pictureUids)) {
			this.moodItem.pictureUids?.split(",").forEach((v: string) => {
				fileApi.queryOneDataByUid({uid: v}).then(result => {
					if (result.data && StringUtil.haveLength(result.data.path)) {
						this.pictureArr.push(result.data)
					}
				})
			})
		}
  },
  mounted() {
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
    getUpdatedTime() {
      let updatedAt = this.moodItem.createTime;
      let day = new Date(updatedAt).getDate();
      let month = new Date(updatedAt).getMonth() + 1;
      let year = new Date(updatedAt).getFullYear();
      gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
    },
    getMoodLike() {
      if (this.moodLikeStatus) {
        return "mood_like_active"
      }
    }
  },
  methods: {
		useUserInfo,
    getLocalTime(time: string | number | Date) {
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
          // TODO
					// let expiresTime = new Date().getTime() + 864000000;
					// let expires = new Date(expiresTime);
					// document.cookie = "mood_like_status_" + this.moodItem.id + "=1;expires=" + expires + ";";
					// this.cozeLikeTemp = mood_like + 1
					// this.moodLikeStatus = true
					// this.setLikeSuccess = true
        }else {
          //减赞
          // TODO
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
    moodLove(e,moodItem) {
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
    moodPoster(e,moodItem) {
      this.$emit("moodPoster", {
        moodItem
      })
    },
    moodEdit(e,moodItem) {
      this.$emit("moodEdit",moodItem)
    },
    openImg(e) {
      const zoom = mediumZoom(e.target)
      zoom.open()
    }
  }
}
</script>