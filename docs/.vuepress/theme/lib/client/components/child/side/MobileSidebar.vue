<template>
  <div :id="setShowMobileSidebar(1)" class="mobile-sidebar-control" @click="showMobileSidebar"></div>
  <div :id="setShowMobileSidebar(2)" class="mobile-sidebar">
    <HomeSidebar :show-article="false" :show-message="false"
                 :is-mobile-sidebar="true"
                 :custom-id="'mobile-sidebar-custom'" :show-site="false"
                 :show-search="false"
                 :is-show-catalog="true">
      <template #sidebar-son1>
        <div class="divider">
          <span></span><span>Aurora</span><span></span>
        </div>
      </template>
    </HomeSidebar>
  </div>
</template>

<script>
export default {
  name: "MobileSidebar",
  data() {
    return {
      isShowMobileSidebar: false
    }
  },
  props: {
    showNavbar: {
      type: Boolean,
      default() {
        return false
      }
    }
  },
  computed: {
    setShowMobileSidebar() {
      return (index) => {
        if (2 === index) {
          if (this.$store.state.openMobileSidebar) {
            return 'mobileSidebar'
          }else {
            return ''
          }
        }else {
          if (this.$store.state.openMobileSidebar) {
            return 'mobile-sidebar-control'
          }else {
            return ''
          }
        }
      }
    }
  },
  methods: {
    showMobileSidebar() {
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: !this.$store.state.openMobileSidebar
      })
    }
  }
}
</script>

<style scoped>

</style>