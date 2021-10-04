<template>
  <div class="photo">
    <div class="photo-center">
      <div id="photo-li">
        <li><router-link to="/">首页</router-link></li>
        <li><router-link to="/photo">photo</router-link></li>
      </div>
    </div>
    <div class="loadingAnimate">
      <div class="loader">
        <div class="inner one"></div>
        <div class="inner two"></div>
        <div class="inner three"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Photo",
  data() {
    return {
      photoData: '',
      photos: [],
      finishLoadImg: false
    }
  }
}
</script>