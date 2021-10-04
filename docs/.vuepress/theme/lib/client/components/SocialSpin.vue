<template>
  <add-mood @cancel="cancel" :open-edit-status="openEditStatus"/>
  <div id="social_spin_po" class="fail_about social_spin_po">
    <span class="bg_links social portfolio" >
      <span @click="openEdit" class="icon"></span>
    </span>
    <a class="bg_links social dribbble" href="https://github.com/qsyyke/vuepress-theme-aurora" target="_blank">
      <span class="icon"></span>
    </a>
    <div class="">
      <a id="social_spin" class="bg_links social_logo"></a>
    </div>
  </div>
</template>

<script>
import AddMood from "./child/AddMood";
import myData from '@temp/my-data'
import $ from "jquery";
export default {
  name: "SocialSpin",
  components: {
    AddMood
  },
  data() {
    return {
      themeProperty: null,
      openEditStatus: false
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    })
  },
  methods:{
    cancel(openEditStatus) {
      this.openEditStatus = openEditStatus.openEditStatus
    },
    openEdit() {
      this.openEditStatus = !this.openEditStatus
      if (this.$store.state.verifyStatus) {
        //密码验证成功，直接显示
        $("#add-mood").slideDown(200)
      }else {
        $("#add-mood-pwd").slideToggle(200)
      }
    },
  },
  computed: {
    setSpinBg() {
      return (item) => {
        return 'background-image: url('+item.svg+');'
      }
    }
  }
}
</script>

<style scoped lang="scss">
  @import "../styles/404";
</style>