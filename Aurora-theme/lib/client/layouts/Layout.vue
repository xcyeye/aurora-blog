<template>
  <common :is-sticky-sidebar="!frontmatter.home" :head-line="headLine"
          :is-show-head-line="!frontmatter.home"
          :is-home-page="frontmatter.home"
          :is-show-catalog="!frontmatter.home"
          :is-show-top-img="getIsHome">
    <template #center1>
      <Home :theme-property="themeProperty" :show-random-say="true" :is-home=frontmatter.home v-if="frontmatter.home" />
    </template>
    <template #center2>
      <Page :path-name="page.path" v-if="!frontmatter.home" @getHeadLine="getHeadLine" :theme-property="themeProperty" :key="page.path">
        <template #top>
          <slot name="page-top" />
        </template>
        <template #bottom>
          <slot name="page-bottom" />
        </template>
      </Page>
    </template>
  </common>
  <home-bottom v-if="frontmatter.home">
    <template #home-footer>
      <Footer :theme-property="themeProperty"
              :is-home="!frontmatter.home"
              :is-show-footer="true">
      </Footer>
    </template>
  </home-bottom>
</template>

<script lang="ts">
import {
  computed,
  defineComponent,
  onMounted,
  onUnmounted,
  ref,
  Transition,
} from 'vue'
import { useRouter } from 'vue-router'
import { usePageData, usePageFrontmatter } from '@vuepress/client'
import type { DefaultThemePageFrontmatter } from '../../shared'
import {
  useScrollPromise,
  useSidebarItems, useThemeData,
  useThemeLocaleData,
} from '../composables'
import $ from 'jquery'

//导入组件
import Home from '../components/Home.vue'
import Page from '../components/Page.vue'


export default defineComponent({
  name: 'Layout',
  components: {
    Home,
    Page,
    Transition,
  },
  props: {
    showPrintTextValue: {
      type: Boolean,
      default() {
        return false
      }
    },
  },
  setup() {
    const page = usePageData()
    const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()

    const themeLocale = useThemeLocaleData()

    // navbar
    const shouldShowNavbar = computed(
        () =>
            frontmatter.value.navbar !== false && themeLocale.value.navbar !== false
    )

    // sidebar
    const sidebarItems = useSidebarItems()
    const isSidebarOpen = ref(false)
    const toggleSidebar = (to?: boolean): void => {
      isSidebarOpen.value = typeof to === 'boolean' ? to : !isSidebarOpen.value
      if (isSidebarOpen.value) {
        $("#c-sidebar").css("display","block")
      }
    }
    const touchStart = { x: 0, y: 0 }
    const onTouchStart = (e): void => {
      touchStart.x = e.changedTouches[0].clientX
      touchStart.y = e.changedTouches[0].clientY
    }
    const onTouchEnd = (e): void => {
      const dx = e.changedTouches[0].clientX - touchStart.x
      const dy = e.changedTouches[0].clientY - touchStart.y
      if (Math.abs(dx) > Math.abs(dy) && Math.abs(dx) > 40) {
        if (dx > 0 && touchStart.x <= 80) {
          toggleSidebar(true)
        } else {
          toggleSidebar(false)
        }
      }
    }

    // classes

    // close sidebar after navigation
    let unregisterRouterHook
    onMounted(() => {
      const router = useRouter()
      unregisterRouterHook = router.afterEach(() => {
        toggleSidebar(false)
      })
    })
    onUnmounted(() => {
      unregisterRouterHook()
    })

    // handle scrollBehavior with transition
    const scrollPromise = useScrollPromise()
    const onBeforeEnter = scrollPromise.resolve
    const onBeforeLeave = scrollPromise.pending

    return {
      frontmatter,
      page,
      shouldShowNavbar,
      toggleSidebar,
      onTouchStart,
      onTouchEnd,
      onBeforeEnter,
      onBeforeLeave,
    }
  },
  created() {
    this.themeProperty = useThemeData().value
  },
  mounted() {
    this.$router.beforeEach((to,from,next) => {
      next()
    })

  },
  data() {
    return {
      isShow: false,
      animeImg: '',
      headLine: '',
      isShowSideBar: '',
      themeProperty: null,
    }
  },
  methods: {
    showPrintText(value) {
      console.log(value)
    },
    getHeadLine(title) {
      this.headLine = title
    }
  },
  computed: {
    setBackgroundUrl() {
      return "background-image: url(" + this.animeImg + ");"
    },
    getIsHome() {
      const frontmatter = usePageFrontmatter<DefaultThemePageFrontmatter>()
      let isHome = frontmatter.value.home
      this.isShowSideBar = isHome === undefined;
      return this.isShowSideBar
    }
  },
})
</script>