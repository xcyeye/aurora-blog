<template>
  <div class="photo">
    <div class="photo-center">
      <div>
        <li><router-link to="/">首页</router-link></li>
        <li><router-link to="/photo/animate">欣赏</router-link></li>
        <li><router-link to="/photo/fall/">photo</router-link></li>
      </div>
    </div>
    <!--<div class="photo-html" v-if="!finishLoadImg" style="display: none"></div>-->
    <!--v-if="!loadingFinish"-->
    <!--: !$store.state.loadingFinish-->
    <!--<div  class="loadingAnimate">
      <div class="loader">
        <div class="inner one"></div>
        <div class="inner two"></div>
        <div class="inner three"></div>
      </div>
    </div>-->
    <router-view/>
  </div>
</template>

<script>
import myData from '@temp/my-data'
import $ from "jquery";
export default {
  name: "Photo",
  data() {
    return {
      photoData: '',
      photos: [],
      finishLoadImg: false

    }
  },
  created() {

  },
  mounted() {
    /*new Promise((resolve,reject) => {
      //document.querySelector(".photo-html").innerHTML = this.photoData.contentRendered
      resolve()
    }).then(() => {
      let allPhotos = document.querySelectorAll(".photo-html img");
      for (let i = 0; i < allPhotos.length; i++) {
        this.photos.push(allPhotos[i].src)

        if (i === allPhotos.length -1) {
          this.finishLoadImg = true
        }
      }
      setTimeout(() => {
        this.$store.commit("setPhotos",{
          photos: this.photos
        })
      },100)
    })*/
  }
}
</script>

<style lang="scss">
@import "../styles/theme.style.css";
@import "../styles/photo";
</style>