<template>
  <div class="toggle-sidebar-button" :class="{'aurora-mobile-open': mobileOpenStatus}" @click="openMobileSidebar">
		<svg-icon icon="ic:baseline-density-medium"/>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import {useThemeStore} from "@/stores";

export default defineComponent({
  name: 'ToggleSidebarButton',

  emits: ['toggle'],
  data() {
    return {
      mobileOpenStatus: false
    }
  },
  methods: {
    openMobileSidebar() {
      this.mobileOpenStatus = !this.mobileOpenStatus
			const currentTheme = useThemeStore().currentTheme
			currentTheme.mobileOpenStatus = this.mobileOpenStatus
			useThemeStore().setCurrentThemeStore(currentTheme)
    }
  }
})
</script>
