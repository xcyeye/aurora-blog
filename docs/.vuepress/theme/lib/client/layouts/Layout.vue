<template>
  <common :head-line="headLine" :is-show-head-line="!frontmatter.home" :is-show-top-img="getIsHome">
    <template #center1>
      <Home :theme-property="themeProperty" :is-home=frontmatter.home v-if="frontmatter.home" />
    </template>
    <template #center2>
      <Transition
          v-if="!frontmatter.home"
          @before-enter="onBeforeEnter"
          @before-leave="onBeforeLeave"
      >
        <Page @getHeadLine="getHeadLine" :theme-property="themeProperty" :key="page.path">
          <template #top>
            <slot name="page-top" />
          </template>
          <template #bottom>
            <slot name="page-bottom" />
          </template>
        </Page>
      </Transition>
    </template>
  </common>
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
  useSidebarItems,
  useThemeLocaleData,
} from '../composables'
import $ from 'jquery'

//导入组件
import Home from '../components/Home.vue'
import Page from '../components/Page.vue'
import myData from '@temp/my-data'


export default defineComponent({
  name: 'Layout',

  components: {
    Home,
    Page,
    Transition,
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
    const containerClass = computed(() => [
      {
        'no-navbar': !shouldShowNavbar.value,
        'no-sidebar': !sidebarItems.value.length,
        'sidebar-open': isSidebarOpen.value,
      },
      frontmatter.value.pageClass,
    ])

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
      containerClass,
      shouldShowNavbar,
      toggleSidebar,
      onTouchStart,
      onTouchEnd,
      onBeforeEnter,
      onBeforeLeave,
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    })
    this.$store.dispatch('getAnimeImg').then(() => {
      this.animeImg = this.$store.state.animeImg
    })
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
      themeProperty: null
    }
  },
  methods: {
    handleScroll(e) {
      let scrollTop = $(document).scrollTop()
      if (document.body.clientWidth < 500) {
        return
      }
      if (scrollTop >= 300 && !this.isShow) {
        $("#c-sidebar").show(500)
        this.isShow = true
      }else {
        if (scrollTop < 300) {
          //小于300，隐藏
          $("#c-sidebar").hide(500)
          this.isShow = false
        }
      }
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
