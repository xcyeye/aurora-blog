<template>
  <div class="aurora-coze-slide-body" :style="slideBodyBg">
    <div class="coze-image-container" id="coze-image-container"></div>
    <add-mood :verify-identify-status="verifyIdentifyStatus" :show-mood-control="showMoodControl"
              :current-mood-obj="currentMoodObj" @save-data-success="saveDataSuccess" @cancel="cancel"
              :open-edit-status="openEditStatus"/>

      <div class="aurora-coze-slide-shade aurora-coze-slide-radius">
        <div class="aurora-coze-slide-box-style-box">
          <div class="aurora-coze-slide-box-style"></div>
        </div>
        <swiper
            :grabCursor="true"
            :effect="'creative'"
            :creativeEffect="{
      prev: {
        shadow: false,
        translate: ['-120%', 0, -500],
      },
      next: {
        shadow: false,
        translate: ['120%', 0, -500],
      },
    }"
            :modules="modules"
    class="aurora-coze-slide-box aurora-coze-slide-radius"
  >
          <swiper-slide v-slot="{ isActive }" v-for="(item,index) in moods" :key="index" :style="setSlideItemStyle(index)" class="aurora-coze-slide-item aurora-coze-slide-radius">
            <slide-coze-mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
                                  @mood-edit="moodEdit" @set-slide-bodyBg="setSlideBodyBg" :data="updateBgStyle(isActive,item)" :mood-item="item" :is-active="isActive" />
          </swiper-slide>
  </swiper>
      </div>
  </div>
</template>
<script>
// Import Swiper Vue.js components
import { Swiper, SwiperSlide } from "swiper/vue";
import AddMood from './AddMood.vue'

import SlideCozeMoodItem from "./SlideCozeMoodItem.vue";

// Import Swiper styles
import "swiper/css";

import "swiper/css/effect-creative";

// import required modules
import { EffectCreative } from "swiper";

import {useThemeData} from "../composables";

//导入leanCloud依赖
import { Query, User } from 'leancloud-storage';
import gsap from "gsap";
let appId = ''
let appKey = ''
let masterKey = ''
let avatar = 'https://pic1.zhimg.com/80/v2-0653e99ab7a28223c488c27632526951_720w.jpg'
try {
  appId = __APP_ID__;
  appKey = __APP_KEY__;
  masterKey = __Master_Key__;
  avatar = __AVATAR_PATH__;
}catch (e) {
  console.warn("你必须在插件中传入appId,appKey,masterKey配置项")
  console.warn(e)
}

export default {
  name: "AuroraCozeMood",
  data() {
      return {
        themeProperty: {},
        abouts: [],
        hexRgb: '',
        slideBodyBg: '',
        showAboutPageBubble: true,

        //下面的是原本的CozeMood组件数据
        //这是一个数组对象
        color: '',
        moods: [],
        showMoodEdit: true,
        openEditStatus: false,
        num: 1,
        currentMoodObj: {},
        verifyIdentifyStatus: false,
        showMoodControl: false,
        randomColors: [
          "#ffcad4", "#d8e2dc", "#8d99ae", "#b8f2e6", "#84c7d0", "#aed9e0", "#00b4d8",
          "#caf0f8", "#fbc4ab", "#fdc5f5", "#84dcc6", "#a9def9", "#fcf6bd", "#f0a6ca",
          "#b9faf8", "#42a5f5", "#ff9800", "#b39ddb", "#6d45bb", "#b388ff", "#1565c0",
          "#26c6da", "#5e548e", "#90f1ef", "#5b5f97", "#bbe6e4", "#42bfdd", "#72ddf7",
          "#8093f1", "#9ed8d8", "#7ea8be", "#ef90b3", "#b892ef", "#c0b9dd", "#c0d9dd",
          "#75c9c8", "#ded9e2", "#b5e2fa", "#62b6cb", "#5fa8d3", "#0fa3b1", "#b5e2fa",
          "#5fa8d3", "#62b6cb", "#b892ff",
        ]
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
    setSlideBodyBg(photoUrl) {
      this.slideBodyBg = "--aurora-coze-slide-bgImg: url(" + photoUrl.photoUrl + ");"
    },
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
    },

    //点击头像回到上一步网页
    goBackPage() {
      this.$router.go(-1)
    },
    getInearGradientStyle() {
      //let hexRgb1 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
      //let hexRgb2 = this.hexToRgb(this.randomColors[this.getRandomInt(0,this.randomColors.length -1)])
      return "--aurora-coze-slide-bgImg: linear-gradient(to right top, " + this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] + " 0%, "+ this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] +" 100%);"
    },
    updateBgStyle(isActive,moodItem) {
      if(isActive) {
        if(moodItem.attributes.mood_photos.length !== 0) {
          //将图片设置为背景图片
          this.slideBodyBg = "--aurora-coze-slide-bgImg: url(" + moodItem.attributes.mood_photos[0].photoUrl + ");"
        }else {
          //如果没有图片，那么则使用渐变颜色作为背景颜色
          this.slideBodyBg = this.getInearGradientStyle()
        }
      }
    },
    hexToRgb(hex) {
      let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    },
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
  },
  components: {
    Swiper,
    SwiperSlide,
    SlideCozeMoodItem,
    AddMood
  },
  setup() {
      return {
      modules: [EffectCreative],
      };
  },
  computed: {
    getUpdatedTime() {
      let updatedAt = this.moodItem.createdAt;
      let day = new Date(updatedAt).getDate();
      let month = new Date(updatedAt).getMonth() + 1;
      let year = new Date(updatedAt).getFullYear();
      gsap.to(this.$data, {duration: 1.1, cozeYearTemp:year, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeMonthTemp: month, ease: 'sine'})
      gsap.to(this.$data, {duration: 2, cozeDayTemp: day, ease: 'sine'})
    },

    setSlideItemStyle() {
      //background-image: linear-gradient(to right top, #fff1eb 0%, #ace0f9 100%);
      return (index) => {

      }
    },
    setBarStyle() {
      return (score) => {
        return "background-color:" + this.randomColors[this.getRandomInt(0,this.randomColors.length -1)] + "; width: " + score * 0.85 + "%"
      }
    }
  },

};
</script>