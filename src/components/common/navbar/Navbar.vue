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
				<div>
					<router-link to="/">首页</router-link>
				</div>
				<div>
					<router-link to="/user/1522074993315815424">用户首页</router-link>
				</div>
		<div>		<router-link to="/friendLink/1522074993315815424">友情链接  </router-link></div>
			<div>	<router-link to="/archive/1522074993315815424">时间轴       </router-link></div>
				<div><router-link to="/tag-category/1522074993315815424">标签    </router-link></div>
				<div><router-link to="/about/1522074993315815424">关于           </router-link></div>
				<div><router-link to="/shareSpace/1522074993315815424">说说1     </router-link></div>
				<div><router-link to="/shareSpace-page/1522074993315815424">说说2</router-link></div>
				<div><router-link to="/article/1611023522247221248">文章         </router-link></div>
			</div>
		</header>
	</div>
</template>

<script setup lang="ts">
import {useUserInfo} from "@/stores";

interface Props {
	userUid: string,
	showHeaderBg?: boolean
}

const props = withDefaults(defineProps<Props>(), {
	showHeaderBg: true
})
</script>