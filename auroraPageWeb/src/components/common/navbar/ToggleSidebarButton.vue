<template>
  <div class="toggle-sidebar-button" :class="{'aurora-mobile-open': mobileOpenStatus}" @click="openMobileSidebar">
		<svg-icon icon="ic:baseline-density-medium"/>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

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
      this.$store.state.mobileOpenStatus = !this.$store.state.mobileOpenStatus
      this.mobileOpenStatus = !this.mobileOpenStatus
      this.$store.commit("setOpenMobileSidebar",{
        openMobileSidebar: !this.$store.state.openMobileSidebar
      })
    }
  }
})
</script>
