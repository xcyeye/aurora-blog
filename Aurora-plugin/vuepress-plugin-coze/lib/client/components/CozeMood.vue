<template>
  <div class="coze-image-container" id="coze-image-container"></div>
  <div class="coze-box">
    <add-mood :verify-identify-status="verifyIdentifyStatus" :show-mood-control="showMoodControl"
              :current-mood-obj="currentMoodObj" @save-data-success="saveDataSuccess" @cancel="cancel"
              :open-edit-status="openEditStatus"/>
    <div class="coze-mood-list">
      <div :key="item.attributes.mood_content" v-for="(item,index) in moods" class="coze-enter-animate">
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
import { Query, User } from 'leancloud-storage';
let appId = ''
let appKey = ''
let masterKey = ''
let avatar = 'https://ooszy.cco.vin/img/blog-note/avatar-aurora.png'
try {
  appId = __APP_ID__;
  appKey = __APP_KEY__;
  masterKey = __Master_Key__;
  avatar = __AVATAR_PATH__;
}catch (e) {
  console.warn("你必须在插件中传入appId,appKey,masterKey配置项")
  console.warn(e)
}
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
  emits: ['cozeSuccess','moodEdit','cozeCancelEdit'],
  created() {
    const query = new Query('Talk');
    query.descending('createdAt');
    query.find().then((talks) => {
      if (talks.length === 0) {
        this.moods = [{
          attributes: {
            mood_user: 'aurora',
            mood_comment: "",
            mood_content: "这个是当你leanCloud中，有Talk类，但是里面没有任何数据时，默认显示的内容，如果你Talk中，一条数据也没有，请不要隐藏此条数据，" +
                "否则你将不能发布说说，只能删除Talk类，重新初始化",
            mood_like: 12,
            mood_photos: [{
              objectId: "61869d9b49cd7e59a03e09c8",
              photoName: "1GyQsaZ8MhjTVKi.jpg",
              photoUrl: "https://ooszy.cco.vin/img/theme/%E4%B8%BB%E9%A2%98.jpg"
            }],
            mood_title: "这是标题",
            mood_show: true
          },
          id: "61869da949cd7e59a03e09cc",
        }]
        this.$emit("cozeSuccess",{
          cozeMoods: []
        })
      }else {
        this.moods = talks
        this.$emit("cozeSuccess",{
          cozeMoods: this.moods
        })
      }
    });

    //验证用户是否登录
    const currentUser = User.current();
    if (currentUser) {
      this.verifyIdentifyStatus = true
    } else {
      this.verifyIdentifyStatus = false
    }
  },
  methods: {
    saveDataSuccess() {
      const query = new Query('Talk');
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

      this.$emit("cozeCancelEdit",{
        openEditStatus: this.openEditStatus
      })
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
      this.$emit("moodEdit",{
        openEditStatus: this.openEditStatus
      })
    }
  },
})
</script>
<style lang="css">
@import "../public/font/iconfont.css";
</style>
