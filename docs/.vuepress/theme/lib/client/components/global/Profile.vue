<template>
  <div class="profile" id="profile">
    <div class="profile-top profile-common">
      <img :src="getHeroImage" alt="">
    </div>
    <div class="profile-bottom profile-common">
      <div class="profile-bottom-left profile-bottom-common">
        <span>文章</span>
        <a style="text-decoration: none" href="/tag">
          <span>{{$store.state.pageNum}}</span>
        </a>
      </div>
      <!--<div class="profile-bottom-center profile-bottom-common">
        <div></div>
      </div>-->
      <div class="profile-bottom-right profile-bottom-common">
        <span>标签</span>
        <a style="text-decoration: none" href="/tag">
          <span>{{$store.state.categories.length}}</span>
        </a>
      </div>
      <div class="profile-bottom-right profile-bottom-common">
        <span>类别</span>
        <a style="text-decoration: none" href="/tag">
          <span>{{$store.state.tagArr.length}}</span>
        </a>
      </div>
    </div>
  </div>
</template>

<script>
import myData from '@temp/my-data'
import {useThemeData} from "../../composables";

export default {
  name: "Profile",
  data() {
    return {
      themeProperty: ''
    }
  },
  created() {
    this.themeProperty = useThemeData().value
  },
  computed: {
    getHeroImage() {
      let src = this.themeProperty.heroLogo
      if (src === undefined) {
        console.log("you need to set the logo field value,the default is: \nhttps://ooszy.cco.vin/img/blog-public/avatar.jpg")
        return "https://ooszy.cco.vin/img/blog-public/avatar.jpg"
      }else {
        return src
      }
    }
  }
}
</script>
