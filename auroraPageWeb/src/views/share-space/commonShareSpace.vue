<template>
	<aurora-common :is-sticky-sidebar="true" :show-sidebar-animate-class="false"
					:is-show-side-bar="false" :user-uid="userUid" :is-show-top-img="true" :is-show-head-line="false">
		<template #center1>
			<!--<CozeLogin @coze-login="cozeLogin"></CozeLogin>-->
			<CozeMood :user-uid="userUid" @coze-cancel-edit="cozeCancelEdit" @mood-edit="moodEdit" @coze-success="cozeSuccess"/>
		</template>
		<template #center2>
			<blog-comment
				:page-uid="userUid"
				:page-path="`/showSpace/${userUid}`"
				:query-regexp="`^/showSpace/${userUid}.*`"
				reply-page-type="TALK"
				:user-uid="userUid"/>
		</template>
	</aurora-common>
</template>

<script lang="ts">
import {
	defineComponent,
	Transition,
} from 'vue'
import {StringUtil} from "@/utils";
import {useRouterPush} from "@/composables";

const routerPush = useRouterPush()
//导入配置属性
export default defineComponent({
	name: 'Mood',
	
	components: {
		Transition
	},
	data() {
		return {
			userUid: '',
			//这是一个数组对象
			color: '',
			ico: '',
			showComment: false
		}
	},
	created() {
		this.userUid = this.$route.params.userUid as string
		if (!StringUtil.haveLength(this.userUid)) {
			routerPush.routerPush({
				name: 'home'
			})
		}
	},
	methods: {
		cozeCancelEdit(openEditStatus: boolean) {
			// if (!openEditStatus.openEditStatus) {
			//   let cozeMoodAurora = document.querySelector(".coze-mood-aurora");
			//   cozeMoodAurora.className = "theme-container coze-mood-aurora sidebar-single-enter-animate"
			// }
		},
		moodEdit(openEditStatus: boolean) {
		
		},
		cozeLoginOut(data) {
			console.log("点击登出按钮")
			console.log(data)
		},
		cozeLogin(data) {
			console.log("点击注册按钮")
			console.log(data)
		},
		cozeSuccess(cozeMoodData) {
			setTimeout(() => {
				this.showComment = true
			},500)
		}
	}
})
</script>
<style lang="css">
.aurora-give-like-heart-svg-default {
	color: var(--fontColor);
}

.aurora-give-like-heart-default-active {
	color: #ef476f;
}
</style>
