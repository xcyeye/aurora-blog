<template>
  <header ref="navbar" :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle"
          :class="{'header-bg-show': props.showHeaderBg}"
          id="c-navbar" class="navbar">
    <ToggleSidebarButton />

    <span ref="siteBrand">
      <RouterLink to="/">
        <img
            style="border-radius: 30px"
            class="logo"
            :src="withBase(getLogoImg)"
            :alt="siteBrandTitle"
        />
        <span
            class="site-name"
            :style="setLogoColor"
        >
          {{getLogoTitle}}
        </span>
      </RouterLink>
    </span>

    <div class="navbar-links-wrapper" :style="linksWrapperStyle">
      <slot name="before" />

      <NavbarLinks class="can-hide" />

      <slot name="after" />

      <ToggleDarkModeButton v-if="enableDarkMode" />

      <NavbarSearch/>
    </div>
  </header>
</template>

<script setup lang="ts">

import NavbarLinks from './NavbarLinks.vue'
import ToggleDarkModeButton from './ToggleDarkModeButton.vue'
import ToggleSidebarButton from './ToggleSidebarButton.vue'

import { computed, onMounted, ref } from 'vue'
import {useRouteLocale, useSiteLocaleData, withBase} from '@vuepress/client'
import {useThemeData, useThemeLocaleData} from '../composables'

defineEmits(['toggle-sidebar'])
const routeLocale = useRouteLocale()
const siteLocale = useSiteLocaleData()
const themeLocale = useThemeLocaleData()
const navbar = ref<HTMLElement | null>(null)
const siteBrand = ref<HTMLElement | null>(null)
let props = defineProps({
  showHeaderBg: {
    type: Boolean,
    default() {
      return false
    }
  }
})
//设置logo颜色
const setLogoColor = computed(() => {
  let logoColor = useThemeData().value.logoColor === undefined || useThemeData().value.logoColor == null ? "#2c3e50" : useThemeData().value.logoColor
  return "color: "+ logoColor + ";"
})
let setHeaderStyle = computed(() => {
  if (props.showHeaderBg) {
  }else {
  }
})
const getLogoTitle = computed(() => {
  let logoTitle = useThemeData().value.logoTitle
  if (logoTitle === undefined) {
    logoTitle = "Aurora"
  }
  return logoTitle
})
const getLogoImg = computed(() => {
  const themeLocale = useThemeLocaleData()
  let src = themeLocale.value.logo
  if (src === undefined || src === null) {
    console.warn("%c you need to set the logo field value,the default is: https://ooszy.cco.vin/img/ico/yuan.png","color: pink;")
    return "https://ooszy.cco.vin/img/ico/yuan.png"
  }else {
    return  src
  }
})
const siteBrandLogo = computed(() => {
  return  themeLocale.value.logo
})
const siteBrandTitle = computed(() => siteLocale.value.title)
const linksWrapperMaxWidth = ref(0)
const linksWrapperStyle = computed(() => {
  if (!linksWrapperMaxWidth.value) {
    return {}
  }
  return {
    maxWidth: linksWrapperMaxWidth.value + 'px',
  }
})
const enableDarkMode = computed(() => themeLocale.value.darkMode)
// avoid overlapping of long title and long navbar links
onMounted(() => {
  // TODO: migrate to css var
  // refer to _variables.scss
  const MOBILE_DESKTOP_BREAKPOINT = 719
  const navbarHorizontalPadding =
      getCssValue(navbar.value, 'paddingLeft') +
      getCssValue(navbar.value, 'paddingRight')
  const handleLinksWrapWidth = (): void => {
    if (window.innerWidth <= MOBILE_DESKTOP_BREAKPOINT) {
      linksWrapperMaxWidth.value = 0
    } else {
      linksWrapperMaxWidth.value =
          navbar.value!.offsetWidth -
          navbarHorizontalPadding -
          (siteBrand.value?.offsetWidth || 0)
    }
  }
  handleLinksWrapWidth()
  window.addEventListener('resize', handleLinksWrapWidth, false)
  window.addEventListener('orientationchange', handleLinksWrapWidth, false)
})
function getCssValue(el: HTMLElement | null, property: string): number {
  // NOTE: Known bug, will return 'auto' if style value is 'auto'
  const val = el?.ownerDocument?.defaultView?.getComputedStyle(el, null)?.[
      property
      ]
  const num = Number.parseInt(val, 10)
  return Number.isNaN(num) ? 0 : num
}
</script>