<template>
  <div class="coze-mood-item-parent">
    <div class="coze-mood-item" id="coze-mood-item">
      <div class="mood-item-left mood-item-img-parent" id="mood-item-left">
        <div class="mood-item-img" id="coze-mood-item-img">
          <img :src="getAvatar" alt="">
        </div>
      </div>
      <div class="mood-item-right" id="mood-item-right">
        <div class="coze-mood-item-content">
          <div id="mood-item-content" class="mood-item-content mood-item-right-common">
            <span>{{moodItem.attributes.mood_content}}</span>
            <div class="coze-mood-time">
              <span>@{{moodItem.attributes.mood_user}}</span>&nbsp;&nbsp;
              <span>更新于: {{getUpdatedTime}}</span>
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
          <li v-for="(item,index) in moodItem.attributes.mood_photos" :key="index" id="mood-img-li">
            <img @click="openImg" class="medium-zoom-image" id="mood-bottom-img" :src="item.photoUrl" :alt="item.photoName">
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
          <span>{{moodLink === 0 ? "" : moodLink}}</span>
        </div>
        <!--<div class="mood-edit-single-common">
          <poster :title="moodItem.attributes.mood_title" :content="content" />
          &lt;!&ndash;<span @click="moodPoster($event,moodItem)" class="aurora-coze-font aurora-coze-custom-poster"></span>&ndash;&gt;
        </div>-->
        <div class="mood-edit-single-common">
          <span @click="moodEdit($event,moodItem)" class="aurora-coze-font aurora-coze-custom-edit"></span>
        </div>
        <slot name="coze-mood-bottom-right"></slot>
      </div>
    </div>
  </div>
</template>

<script>
const AV = require('leancloud-storage');
const { User } = AV;
const appId = __APP_ID__;
const appKey = __APP_KEY__;
const masterKey = __Master_Key__;
const avatar = __AVATAR_PATH__;

import mediumZoom from 'medium-zoom'
export default {
  name: "MoodItem",
  data() {
    return {
      title: '',
      content: '',
      moodLink: 0,
      moodLikeStatus: false
    }
  },
  props: {
    moodItem: {},
  },
  created() {
    this.moodLink = this.moodItem.attributes.mood_like
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
      resolve()
    })
  },
  computed: {
    getUpdatedTime() {
      let updatedAt = this.moodItem.updatedAt;
      return this.getLocalTime(updatedAt);
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
        return "https://ooszy.cco.vin/img/ico/yuan.png"
      }
    }
  },
  methods: {
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
    moodLove(e,moodItem) {
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
          const query = new AV.Query('Talk');
          query.get(this.moodItem.id).then((data) => {
            const mood_like = data.get('mood_like');
            const todo = AV.Object.createWithoutData('Talk', this.moodItem.id);
            todo.set('mood_like', mood_like + 1);
            todo.save().then(() => {
              document.cookie="mood_like_status_" + this.moodItem.id + "=1";
              this.moodLink = mood_like + 1
              this.moodLikeStatus = true
            });
          });
        }else {
          //减赞
          const query = new AV.Query('Talk');
          query.get(this.moodItem.id).then((data) => {
            const mood_like = data.get('mood_like');
            const todo = AV.Object.createWithoutData('Talk', this.moodItem.id);
            todo.set('mood_like', mood_like - 1);
            todo.save().then(() => {
              document.cookie="mood_like_status_" + this.moodItem.id + "=0";
              this.moodLink = mood_like - 1
              this.moodLikeStatus = false
            });
          });
        }
      })

      this.$emit("moodLove", {
        moodItem
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

<style scoped lang="css">
.mood_like_active {
  color: red;
}
</style>