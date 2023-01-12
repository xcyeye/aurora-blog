<template>
	<div class="navbar-box">
		<header ref="navbar" :style="$store.state.borderRadiusStyle +
       $store.state.opacityStyle + $store.state.fontColorStyle +
       $store.state.fontFamilyStyle + $store.state.filterBlurStyle"
						:class="{'header-bg-show': props.showHeaderBg}"
						class="navbar">
		<span ref="siteBrand">
      <RouterLink :to="`/user/${props.userUid}`">
        <img
					style="border-radius: 30px"
					class="logo"
					:src="useUserInfo().getUserInfo(props.userUid).avatar"
				/>
        <span
					class="site-name"
					style="color: #2c3e50;"
				>
          {{useUserInfo().getUserInfo(props.userUid).username}}
        </span>
      </RouterLink>
    </span>
			
			<div class="navbar-links-wrapper">
				<div v-for="(item, index) in navbarInfoArr" :key="index">
					<navbar-link :navbar-info="item"/>
				</div>
			</div>
		</header>
	</div>
</template>

<script setup lang="ts">
import {useUserInfo} from "@/stores";
import {onBeforeMount, ref} from "vue";
import {siteSettingApi} from "@/service/api/admin/siteSettingApi";

interface Props {
	userUid: string,
	showHeaderBg?: boolean
}

const props = withDefaults(defineProps<Props>(), {
	showHeaderBg: true
})

const navbarInfoArr = ref<Array<NavbarInfo>>([])

const loadSiteSetting = () => {
	siteSettingApi.queryListDataByCondition({otherUid: props.userUid, keyword: `${props.userUid}NavbarInfo`}).then(result => {
		if (result.data && result.data.result  && result.data.result.length > 0) {
			const siteSetting: SiteSetting = result.data.result[0]
			// 取出第一个
			navbarInfoArr.value = JSON.parse(siteSetting.paramValue!)
			console.log(navbarInfoArr.value);
		}
	})
}

onBeforeMount(() => {
	loadSiteSetting()
})
</script>