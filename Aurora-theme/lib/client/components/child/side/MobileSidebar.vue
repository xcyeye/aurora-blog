<template>
  <div :id="setShowMobileSidebar(1)" class="mobile-sidebar-control" @click="showMobileSidebar"></div>
  <div :id="setShowMobileSidebar(2)" :class="{mobileAnimate: !$store.state.openMobileSidebar}" class="mobile-sidebar">
    <HomeSidebar :show-article="false" :show-message="false"
                 :is-mobile-sidebar="true"
                 :show-sidebar-social="true"
                 :custom-id="'mobile-sidebar-custom'" :show-site="false"
                 :show-search="false"
                 v-if="isShowMobileSidebar"
                 :show-enter-animate="false"
                 :show-sidebar-link="false"
                 :is-show-catalog="true">
      <template #sidebar-son2>
        <div class="divider">
          <span></span><span>{{mobileCutText}}</span><span></span>
        </div>
      </template>
    </HomeSidebar>
  </div>
</template>

<script>
import {useThemeData} from "../../../composables";
export default {
  name: "MobileSidebar",
  data() {
    return {
      isShowMobileSidebar: false,
      themeProperty: ''
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
    mobileCutText() {
      if (this.themeProperty.mobileCutText !== undefined) {
        return this.themeProperty.mobileCutText
      }else {
        return 'Aurora'
      }
    },
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
  },
  created() {
    this.themeProperty = useThemeData().value
  },
  mounted() {
    if (document.body.clientWidth <= 719) {
      this.isShowMobileSidebar = true
    }
  }
}
</script>
