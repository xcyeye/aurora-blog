<!--此组件在v1.3.2之后就不使用了，改用HomeSidebarSocialItem.vue-->
<template>
  <div :style="style" id="home-social">
    <home-social-item
        :is-home-page="isHomePage"
        :social="item"
        v-for="item in socialArr"
    >
      <template v-slot:show-img>
        <img :src="item.showImgSrc" alt="">
      </template>
    </home-social-item>
  </div>
</template>

<script>
import HomeSocialItem from "./HomeSocialItem.vue";

export default {
  name: "HomeSocial",
  components: {
    HomeSocialItem
  },
  data() {
    return {
      socials: [],
      style: '',
      socialArr: []
    }
  },
  props:{
    isShowHomeSocialId: {
      type: Boolean,
      default() {
        return true;
      }
    },
    isHomePage: {
      type: Boolean,
      default() {
        return true
      }
    },
    themeProperty: ''
  },
  computed: {
    isImgSrc(item) {
      if (item.showImgSrc !== undefined) return true
    }
  },
  created() {
    if (this.themeProperty.socials !== undefined && this.themeProperty.socials != null) {
      this.socials = this.themeProperty.socials
    }

    new Promise((resolve,reject) => {
      let socialArr = []
      let socialMaxLength = this.themeProperty.socialMaxLength
      if (socialMaxLength === undefined || socialMaxLength == null) {
        socialMaxLength = 7
      }

      if (this.socials.length > socialMaxLength) {
        for (let i = 0; i < socialMaxLength; i++) {
          socialArr.push(this.socials[i])
        }
      }else {
        socialArr = this.socials
      }
      resolve(socialArr)
    }).then((socialArr) => {
      this.socialArr = socialArr
    } )
  },
  methods: {
    setWidth() {
      if (window.screen.width < 400) {
        this.style = 'width: 100%'
        return
      }
      this.style = 'width: '+ (this.socials.length*2)*40+"px"
    },
  }
}
</script>

<style scoped>
  /*@import "../../../styles/theme.style.css";*/
</style>