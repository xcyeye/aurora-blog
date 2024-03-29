<template>
  <div>
		<div class="coze-mood-item" id="coze-mood-item">
			<div class="mood-item-right" id="mood-item-right">
				<div class="coze-mood-item-content">
					<render-markdown :user-uid="userUid" :markdown-content="moodItem.content"/>
					<div class="coze-mood-time">
						<span>@{{useUserInfo().getUserInfo(userUid).username}}</span>
						<span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}} {{cozeHourTemp}}:{{cozeMinuteTemp}}:{{cozeSecondTemp}}</span>
					</div>
				</div>
			</div>
		</div>
		<div class="mood-img-right" v-if="getTalkPictureArr" id="mood-img-right">
			<aurora-gallery :user-uid="userUid" :show-load-more-but="false" :picture-list="getTalkPictureArr"/>
		</div>
		<div class="mood-edit">
			<div class="mood-edit-right">
				<div class="mood-edit-single-common">
					<n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" round/>
				</div>
				<div class="mood-edit-single-common">
					<talk-comment :talk-info="moodItem"/>
				</div>
				<div :class="getMoodLike" class="mood-edit-single-common">
					<give-like :show-default-style="false"
										 :give-like-info="moodItem"
										 like-number-field-name="likeNumber"
										 cookie-name="talk_give_like_status"
										 :control-like-number="false"
										 :update-like-num-request-method="updateTalkNumMethod"
										 :like-number="moodItem.likeNumber"
										 :show-like-num="false"/>
				</div>
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
import {fileApi, talkApi} from "@/service";
import RequestResult = Service.RequestResult;
import RenderMarkdown from "@/components/common/content/RenderMarkdown.vue";

const pictureArr: Array<FileVo> = []
export default {
  name: "MoodItem",
	components: {RenderMarkdown},
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
      cozeHourTemp: 0,
      cozeMinuteTemp: 0,
      cozeSecondTemp: 0,
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
		getTalkPictureArr() {
			if (!StringUtil.haveLength(this.moodItem?.pictureSrcList)) return undefined
			return this.moodItem.pictureSrcList.split(',').map((v: string) => {
				const file: FileVo = {
					path: v
				}
				return file
			}).concat()
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
    getUpdatedTime() {
      let updatedAt = this.moodItem.createTime;
      let day = new Date(updatedAt).getDate();
      let month = new Date(updatedAt).getMonth() + 1;
      let year = new Date(updatedAt).getFullYear();
      let hours = new Date(updatedAt).getHours();
      let minutes = new Date(updatedAt).getMinutes();
      let seconds = new Date(updatedAt).getSeconds();
      gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
      gsap.to(this.$data, {duration: 1, cozeHourTemp: hours, ease: 'sine'})
      gsap.to(this.$data, {duration: 1, cozeMinuteTemp: minutes, ease: 'sine'})
      gsap.to(this.$data, {duration: 1, cozeSecondTemp: seconds, ease: 'sine'})
    },
    getMoodLike() {
      if (this.moodLikeStatus) {
        return "mood_like_active"
      }
    }
  },
  methods: {
		useUserInfo,
		updateTalkNumMethod(talk: TalkVo): Promise<RequestResult<void>> {
			return talkApi.updateTalkLikeNum(talk);
		},
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
<style lang="css">
.aurora-give-like-heart-svg-default {
	color: #f5f5f5;
}

.aurora-give-like-heart-default-active {
	color: #ef476f;
}
</style>