<template>
  <div>
		<div class="coze-mood-item" id="coze-mood-item">
			<!--<div class="mood-item-left mood-item-img-parent" id="mood-item-left">-->
			<!--  <div class="mood-item-img" id="coze-mood-item-img">-->
			<!--    &lt;!&ndash;<img :src="useUserInfo().getUserInfo(userUid).avatar" alt="">&ndash;&gt;-->
			<!--    <n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" :size="50" round/>-->
			<!--  </div>-->
			<!--</div>-->
			<div class="mood-item-right" id="mood-item-right">
				<div class="coze-mood-item-content">
					<render-markdown :markdown-content="moodItem.content"/>
					<div class="coze-mood-time">
						<span>@{{useUserInfo().getUserInfo(userUid).username}}</span>
						<span :data="getUpdatedTime">&nbsp;&nbsp;发布于: {{cozeYear}}-{{cozeMonth}}-{{cozeDay}} {{cozeHourTemp}}:{{cozeMinuteTemp}}:{{cozeSecondTemp}}</span>
					</div>
					<slot name="coze-mood-content"></slot>
					<!--<div id="mood-item-content" class="mood-item-content mood-item-right-common">-->
					<!--  &lt;!&ndash;<span v-html="moodItem.content"></span>&ndash;&gt;-->
					<!--	-->
					<!--</div>-->
				</div>
			</div>
		</div>
		<div class="mood-img-right" v-if="moodItem.pictureSrcList.split(',')" id="mood-img-right">
			<!--<div class="mood-li-control">-->
			<!--  <li v-for="(item,index) in pictureArr" :data="item.path" :key="item.uid" id="mood-img-li">-->
			<!--    &lt;!&ndash;<img @click="openImg" class="medium-zoom-image" id="mood-bottom-img" :src="item.path" :alt="item.fileName">&ndash;&gt;-->
			<!--    <n-image :src="item.path" id="mood-bottom-img" />-->
			<!--  </li>-->
			<!--</div>-->
			<photo-waterfall
				:picture-src-list="moodItem.pictureSrcList.split(',')"
				:mobile-waterfall-img-col="3"
				:pc-waterfall-img-col="4"
				:show-bg-color="false"/>
		</div>
		<div class="mood-edit">
			<!--<div class="coze-mood-bottom-left">-->
			<!--	<n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" round/>-->
			<!--</div>-->
			<div class="mood-edit-right">
				<div class="mood-edit-single-common">
					<!--<span @click="moodComment($event,moodItem)" class="aurora-coze-font aurora-coze-custom-comment"></span>-->
					<n-avatar :src="useUserInfo().getUserInfo(userUid).avatar" round/>
				</div>
				<slot name="coze-mood-bottom-left"></slot>
				<div class="mood-edit-single-common">
					<!--<span @click="moodComment($event,moodItem)" class="aurora-coze-font aurora-coze-custom-comment"></span>-->
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
					<!--<span :class="{'mood_like_love_active': moodLikeStatus}" @click="moodLove($event,moodItem)" class="aurora-coze-font aurora-coze-custom-love"></span>&nbsp;-->
					<!--<span>{{getCozeMoodLink}}</span>-->
				</div>
				<!--<div class="mood-edit-single-common">-->
				<!--  <span @click="moodEdit($event,moodItem)" class="aurora-coze-font aurora-coze-custom-edit"></span>-->
				<!--</div>-->
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