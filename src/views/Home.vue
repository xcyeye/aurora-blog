<template>
	<home-content :theme-property="themeProperty" :show-random-say="true" :is-home="true"/>
	<home-bottom>
		<template #home-footer>
			<Footer :theme-property="themeProperty"
							:is-home="true"
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
import $ from 'jquery'
import {blogPageData} from "@/assets/config";

//导入组件
export default defineComponent({
	name: 'Layout',
	components: {
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
		
		// navbar
		const shouldShowNavbar = computed(
			() => false
		)
		
		// sidebar
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
		
		return {
			shouldShowNavbar,
			toggleSidebar,
			onTouchStart,
			onTouchEnd,
		}
	},
	created() {
		this.themeProperty = blogPageData
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
	},
})
</script>