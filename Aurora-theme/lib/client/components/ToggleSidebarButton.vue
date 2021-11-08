<template>
  <div class="toggle-sidebar-button" :class="{'aurora-mobile-open': mobileOpenStatus}" @click="openMobileSidebar">
    <svg
      class="icon"
      xmlns="http://www.w3.org/2000/svg"
      aria-hidden="true"
      role="img"
      viewBox="0 0 448 512"
    >
      <path
        fill="currentColor"
        d="M436 124H12c-6.627 0-12-5.373-12-12V80c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12zm0 160H12c-6.627 0-12-5.373-12-12v-32c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12zm0 160H12c-6.627 0-12-5.373-12-12v-32c0-6.627 5.373-12 12-12h424c6.627 0 12 5.373 12 12v32c0 6.627-5.373 12-12 12z"
      />
    </svg>
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
