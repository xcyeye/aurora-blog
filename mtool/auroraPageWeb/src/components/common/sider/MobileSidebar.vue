<template>
  <div :id="setShowMobileSidebar(1)" class="mobile-sidebar-control" @click="showMobileSidebar"></div>
  <div :id="setShowMobileSidebar(2)" :class="{mobileAnimate: true}" class="mobile-sidebar">
    <HomeSidebar :show-article="false" :show-message="false"
                 :is-mobile-sidebar="true"
                 :show-sidebar-social="true"
                 :custom-id="'mobile-sidebar-custom'" :show-site="false"
                 :show-search="false"
								 :user-uid="userUid"
                 v-if="isShowMobileSidebar"
                 :show-enter-animate="false"
                 :show-sidebar-link="false"
                 :is-show-catalog="true">
      <template #sidebar-son2>
        <div class="divider">
          <span></span><span>Aurora</span><span></span>
        </div>
      </template>
    </HomeSidebar>
  </div>
</template>

<script lang="ts">
import {useThemeStore} from "@/stores";

const themeStore = useThemeStore()
export default {
  name: "MobileSidebar",
  data() {
    return {
      isShowMobileSidebar: false,
    }
  },
  props: {
    showNavbar: {
      type: Boolean,
      default() {
        return false
      }
    },
		userUid: {
			type: String
		}
  },
  computed: {
    setShowMobileSidebar() {
      return (index: number) => {
        if (2 === index) {
          if (themeStore.currentTheme.mobileOpenStatus) {
            return 'mobileSidebar'
          }else {
            return ''
          }
        }else {
          if (themeStore.currentTheme.mobileOpenStatus) {
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
			const currentTheme = themeStore.currentTheme
			currentTheme.mobileOpenStatus = !currentTheme.mobileOpenStatus
			themeStore.setCurrentThemeStore(currentTheme)
    }
  },
  mounted() {
    if (document.body.clientWidth <= 719) {
      this.isShowMobileSidebar = true
    }
  }
}
</script>
