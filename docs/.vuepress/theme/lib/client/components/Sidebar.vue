<template>
  <!---->
  <!--<aside :style="setIsShow" id="c-sidebar" class="sidebar c-sidebar">-->
  <aside :style="isShowStyle" id="c-sidebar" class="sidebar c-sidebar">
    <profile></profile>
    <NavbarLinks />

    <slot name="top" />

    <ul class="sidebar-links">
      <SidebarChild
        v-for="item in sidebarItems"
        :key="item.link || item.text"
        :item="item"
      />
    </ul>

    <slot name="bottom" />
  </aside>
</template>

<script lang="ts">
import { defineComponent } from 'vue'
import { useSidebarItems } from '../composables'
import NavbarLinks from './NavbarLinks'
import $ from 'jquery'

import { SidebarChild } from './SidebarChild'

export default defineComponent({
  name: 'Sidebar',

  components: {
    NavbarLinks,
    SidebarChild,
  },

  setup() {
    const sidebarItems = useSidebarItems()
    return {
      sidebarItems,
    }
  },
  data() {
    return {
      screenWidth: '',
      isShowStyle: 'display: none'
    }
  },
  mounted () {
    window.onresize = () => {
      let width = document.body.clientWidth
      if (width > 719) {
        $("#c-sidebar").css('display','none')
      }
    }

    let width = document.body.clientWidth
    if (width > 719) {
      this.isShowStyle = "display: none"
    }
  },
})
</script>
<style>
@import "../styles/theme.style.css";
</style>
