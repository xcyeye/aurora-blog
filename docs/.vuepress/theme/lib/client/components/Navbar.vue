<template>
  <header ref="navbar" :style="$store.state.borderRadiusStyle" id="c-navbar" class="navbar">
    <ToggleSidebarButton @toggle="$emit('toggle-sidebar')" />

    <span ref="siteBrand">
      <RouterLink :to="siteBrandLink">
        <!-- v-if="siteBrandLogo"-->
        <img
          style="border-radius: 30px"

          class="logo"
          :src="getLogoImg"
          :alt="siteBrandTitle"
        />

        <!--:class="{ 'can-hide': siteBrandLogo }"-->
        <!--v-if="siteBrandTitle"-->
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

      <NavbarSearch />
    </div>
  </header>
</template>

<script setup lang="ts">
let themeProperty = null
import myData from '@temp/my-data'
new Promise((resolve,reject) => {
  for (let i = 0; i < myData.length; i++) {
    if (myData[i].path === '/') {
      themeProperty = myData[i].frontmatter
    }
  }
  resolve()
})
import { computed, onMounted, ref } from 'vue'
import { useRouteLocale, useSiteLocaleData, withBase } from '@vuepress/client'
import { useThemeLocaleData } from '../composables'
import NavbarLinks from './NavbarLinks.vue'
import ToggleDarkModeButton from './ToggleDarkModeButton.vue'
import ToggleSidebarButton from './ToggleSidebarButton.vue'

defineEmits(['toggle-sidebar'])

const routeLocale = useRouteLocale()
const siteLocale = useSiteLocaleData()
const themeLocale = useThemeLocaleData()

const navbar = ref<HTMLElement | null>(null)
const siteBrand = ref<HTMLElement | null>(null)
const siteBrandLink = computed(
  () => themeLocale.value.home || routeLocale.value
)

//设置logo颜色
const setLogoColor = computed(() => {
  return "color: "+ themeProperty.logoColor + ";"
})

const getLogoTitle = computed(() => {
  let logoTitle = themeProperty.logoTitle
  if (logoTitle === undefined || logoTitle === null) {
    logoTitle = "ccds"
  }
  return logoTitle
})

const getLogoImg = computed(() => {
  const themeLocale = useThemeLocaleData()
  let src = themeLocale.value.logo
  if (src === undefined || src === null) {
    console.log("%c you need to set the logo field value,the default is: https://ooszy.cco.vin/img/ico/yuan.png","color: pink;")
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
