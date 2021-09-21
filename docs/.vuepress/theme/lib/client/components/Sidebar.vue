<template>
  <!---->
  <!--<aside :style="setIsShow" id="c-sidebar" class="sidebar c-sidebar">-->
  <aside :style="setSidebarStyle + showSidebar" id="c-sidebar" class="sidebar c-sidebar">
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
  props: {
    isPage: {
      type: Boolean,
      default() {
        return false
      }
    }
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
      showSidebar: "display: none;",
      setSidebarStyle: "",
      width: 800
    }
  },
  mounted () {
    this.width = document.body.clientWidth
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
  watch: {
    isPage(newValue,oldValue) {
      if (newValue && this.width > 719) {
        //是文章页面
        this.setSidebarStyle = "background-color: rgba(255,255,255,0);box-shadow: none;border-right: none;"
      }
    },
    width() {
      if (this.width < 719) {
        this.setSidebarStyle = "background-color: rgba(255,255,255,1);box-shadow: none;border-right: none;"
      }
    }
  }
})
</script>
