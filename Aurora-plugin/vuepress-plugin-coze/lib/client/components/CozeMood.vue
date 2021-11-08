<template>
  <div class="coze-box">
    <add-mood :verify-identify-status="verifyIdentifyStatus" :show-mood-control="showMoodControl"
              :current-mood-obj="currentMoodObj" @save-data-success="saveDataSuccess" @cancel="cancel"
              :open-edit-status="openEditStatus"/>
    <div class="coze-mood-list">
      <div :key="index" v-for="(item,index) in moods" class="coze-enter-animate">
        <div class="coze-mood-control" v-if="item.attributes.mood_show">
          <mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
                     @mood-edit="moodEdit" :mood-item="item">
            <slot name="coze-mood-content-par"></slot>
            <template #coze-mood-content>
              <slot name="coze-mood-content"></slot>
            </template>

            <slot name="coze-img-par"></slot>
            <template #coze-img>
              <slot name="coze-img"></slot>
            </template>

            <slot name="coze-mood-bottom-left-par"></slot>
            <template #coze-mood-bottom-left>
              <slot name="coze-mood-bottom-left"></slot>
            </template>

            <slot name="coze-mood-bottom-right-par"></slot>
            <template #coze-mood-bottom-right>
              <slot name="coze-mood-bottom-right"></slot>
            </template>
          </mood-item>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {
  defineComponent
} from 'vue'

import MoodItem from "./MoodItem.vue";
import AddMood from './AddMood.vue'
const AV = require('leancloud-storage');
const { Query, User } = AV;
const appId = __APP_ID__;
const appKey = __APP_KEY__;
const masterKey = __Master_Key__;
const avatar = __AVATAR_PATH__;
//导入配置属性
export default defineComponent({
  name: 'Mood',
  components: {
    MoodItem,
    AddMood
  },
  data() {
    return {
      //这是一个数组对象
      color: '',
      moods: [],
      showMoodEdit: true,
      openEditStatus: false,
      num: 1,
      currentMoodObj: {},
      verifyIdentifyStatus: false,
      showMoodControl: false
    }
  },
  emits: ['cozeSuccess'],
  created() {
    const query = new AV.Query('Talk');
    query.descending('createdAt');
    query.find().then((talks) => {
      this.moods = talks
      this.$emit("cozeSuccess",{
        cozeMoods: this.moods
      })
      /*if (talks.length === 0) {
        this.moods = [{
          attributes: {
            mood_comment: "",
            mood_content: "这是默认内容",
            mood_like: 0,
            mood_photos: [{
              objectId: "61869d9b49cd7e59a03e09c8",
              photoName: "1GyQsaZ8MhjTVKi.jpg",
              photoUrl: "https://lc-gluttony.s3.amazonaws.com/c5GfUYtqdSYx/AeBxej8RVowlnCyR5gcWf8b5A4TcYW2v/1GyQsaZ8MhjTVKi.jpg"
            }],
            mood_title: "这是标题",
            mood_show: true
          },
          id: "61869da949cd7e59a03e09cc",
        }]
      }*/
    });

    //验证用户是否登录
    const currentUser = AV.User.current();
    if (currentUser) {
      this.verifyIdentifyStatus = true
    } else {
      this.verifyIdentifyStatus = false
    }
  },
  methods: {
    saveDataSuccess() {
      const query = new AV.Query('Talk');
      query.descending('createdAt');
      query.find().then((talks) => {
        this.moods = talks
        if (talks.length === 0) {
          this.moods = [{
            attributes: {
              mood_comment: "",
              mood_content: "这是内容",
              mood_like: 0,
              mood_photos: [{
                objectId: "61869d9b49cd7e59a03e09c8",
                photoName: "1GyQsaZ8MhjTVKi.jpg",
                photoUrl: "https://lc-gluttony.s3.amazonaws.com/c5GfUYtqdSYx/AeBxej8RVowlnCyR5gcWf8b5A4TcYW2v/1GyQsaZ8MhjTVKi.jpg"
              }],
              mood_title: "这是标题",
            },
            id: "61869da949cd7e59a03e09cc",
          }]
        }
      });
    },
    cancel(openEditStatus) {
      this.openEditStatus = openEditStatus.openEditStatus
      this.showMoodControl = openEditStatus.openEditStatus
    },
    moodComment(moodItem) {

    },
    moodLove(moodItem) {

    },
    moodPoster(moodItem) {

    },
    moodEdit(moodItem) {
      this.currentMoodObj = moodItem
      this.openEditStatus = !this.openEditStatus
      if (this.verifyIdentifyStatus) {
        //密码验证成功，直接显示
        this.showMoodControl = true
      }else {
        this.showMoodControl = true
      }
    }
  },
})
</script>
<style lang="css">
@import "../public/font/iconfont.css";
</style>
