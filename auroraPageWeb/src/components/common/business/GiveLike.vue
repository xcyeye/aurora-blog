<template>
	<div :class="showDefaultStyle ? 'aurora-give-like-heart' : 'aurora-give-like-heart-default'" @click="handleClickLikeAction">
		<div style="cursor: pointer" :class="getHeartSvgStyle">
			<svg-icon icon="bi:suit-heart-fill"/>
		</div>
		<div v-if="showLikeNum" :class="showDefaultStyle ? 'aurora-give-like-heart-num' : 'aurora-give-like-heart-num-default'">{{likeNumber}}</div>
	</div>
</template>

<script lang="ts" setup>
import {computed, defineComponent, onMounted, ref, watch} from "vue";
import {isNotEmptyObject} from "@/utils/business";
import {StringUtil} from "@/utils";
import RequestResult = Service.RequestResult;
import {getIcon} from "@iconify/vue";

interface LikeInfo {
	icon: string,
	showLikeNum?: boolean,
	giveLikeInfo: any,
	controlLikeNumber: boolean,
	likeNumberFieldName: string,
	multiClickGiveLike?: boolean,
	cookieName: string,
	likeNumber?: number,
	showDefaultStyle?: boolean,
	updateLikeNumRequestMethod: (giveLikeInfo: any) => Promise<RequestResult<void>>
}

defineComponent({name: 'GiveLike'});

const props = withDefaults(defineProps<LikeInfo>(), {
	icon: 'bi:suit-heart-fill',
	showLikeNum: true,
	multiClickGiveLike: false,
	showDefaultStyle: true
})

const emits = defineEmits(['finishGiveLikeAction'])

const effectiveGiveInfoStatus = ref(false)
const giveLikeStatus = ref(false)

const getHeartSvgStyle = computed(() => {
	let className = ''
	if (props.showDefaultStyle) {
		className = 'aurora-give-like-heart-svg'
	}else {
		className = 'aurora-give-like-heart-svg-default'
	}
	if (giveLikeStatus.value && props.showDefaultStyle) {
		className = className + " " + 'aurora-give-like-heart-active'
	}
	if (giveLikeStatus.value && !props.showDefaultStyle) {
		className = className + " " + 'aurora-give-like-heart-default-active'
	}
	return className
})

const initGiveLikeStatus = () => {
	if (!effectiveGiveInfoStatus.value) return
	let cookie = document.cookie;
	const cookieList = cookie.split(';')
	let giveLikeStatusTemp = false
	for(let i = 0; i < cookieList.length; i++) {
		const arr = cookieList[i].split('=')
		let cookieName =  props.cookieName + props.giveLikeInfo['uid']
		let cookieOriginName = arr[0].replace(" ","")
		if (cookieName === cookieOriginName) {
			if (arr[1] === '1') {
				giveLikeStatus.value = true
				giveLikeStatusTemp = true
			}
		}
		if (i === cookieList.length - 1) {
			if (!giveLikeStatusTemp) {
				giveLikeStatus.value = false
			}
		}
	}
}

const storeGiveLikeStatus = () => {
	let expiresTime = new Date().getTime() + 864000000;
	let expires = new Date(expiresTime);
	document.cookie = props.cookieName + props.giveLikeInfo['uid'] + "=1;expires=" + expires + ";";
	giveLikeStatus.value = true
	emits('finishGiveLikeAction')
}

const updateLikeNum = () => {
	// 非受控模式，后端直接修改
	if (!props.controlLikeNumber) {
		props.updateLikeNumRequestMethod(props.giveLikeInfo).then(result => {
			if (!result.error) {
				storeGiveLikeStatus()
				window.$message?.success('点赞成功 o(￣▽￣)ｄ')
			}
		})
	}else {
		// 受控模式，需要修改点赞数
		props.giveLikeInfo[props.likeNumberFieldName] = props.giveLikeInfo[props.likeNumberFieldName] ? (props.giveLikeInfo[props.likeNumberFieldName] + 1) : 1
		props.updateLikeNumRequestMethod(props.giveLikeInfo).then(result => {
			if (!result.error) {
				storeGiveLikeStatus()
			}
		})
	}
}

const handleClickLikeAction = () => {
	if (giveLikeStatus.value && !props.multiClickGiveLike) {
		window.$message?.success('只能点赞一次o(￣▽￣)ｄ')
		return
	}
	if (!giveLikeStatus.value) {
		updateLikeNum()
	}else {
		// 如果支持多次点赞
		if (props.multiClickGiveLike) {
			updateLikeNum()
		}
	}
}

const setEffectiveStatus = () => {
	if (!props.giveLikeInfo || !isNotEmptyObject(props.giveLikeInfo) || !StringUtil.haveLength(props.giveLikeInfo['uid'])) {
		// window.$message?.error('请传入giveLikeInfo')
		console.error('请传入giveLikeInfo')
	}else {
		effectiveGiveInfoStatus.value = true
		initGiveLikeStatus()
	}
}

onMounted(() => {
	setEffectiveStatus()
})

watch(() => props.giveLikeInfo, (nv, ov) => {
	setEffectiveStatus()
	initGiveLikeStatus()
})
</script>

<style scoped lang="css">
.aurora-give-like {
	/*margin-top: 1rem;*/
	/*width: 100%;*/
	/*height: 5rem;*/
	/*font-size: 2rem;*/
}

.aurora-give-like-heart {
	display: flex;
	/*justify-content: center;*/
	/*align-items: center;*/
}

.aurora-give-like-heart-svg {
	cursor: pointer;
	display: flex;
	justify-content: center;
	align-items: center;
	padding: 1rem;
	border-radius: 100px;
	transition: all 1s;
	background-color: #f5f5f5;
	color: #ef476f;
	/*margin: 0 auto;*/
}

.aurora-give-like-heart-num {
	display: flex;
	margin-top: .5rem;
	font-size: 1.5rem;
	justify-content: center;
	align-items: center;
	margin-left: .5rem;
}

/*.aurora-article-like-heart:hover {*/
/*    background-color: #f5f5f5;*/
/*    color: #ef476f;*/
/*}*/

.aurora-give-like-heart-active {
	color: #f5f5f5;
	background-color: #ef476f;
}
</style>