<template>
	<n-dropdown
		v-if="navbarInfo.children && navbarInfo.children.length > 0"
		placement="bottom-start"
		trigger="click"
		size="small"
		:options="dropdownNavbarOption"
	>
		<div>
			<svg-icon v-if="navbarInfo.icon" :icon="navbarInfo.icon"/>
			<a>{{ navbarInfo.name }}</a>
		</div>
	</n-dropdown>
	<div v-else>
		<svg-icon v-if="navbarInfo.icon" :icon="navbarInfo.icon"/>
		<a v-if="navbarInfo.outLink" target="_blank" :href="navbarInfo.url">{{ navbarInfo.name }}</a>
		<router-link v-else :to="navbarInfo.url">{{ navbarInfo.name }}</router-link>
	</div>
</template>

<script lang="ts" setup>
import {defineComponent, onBeforeMount, ref, h, VNodeChild} from "vue";
import {DropdownMixedOption, DropdownOption} from "naive-ui/es/dropdown/src/interface";
import {NIcon} from "naive-ui";
import {MenuOption} from "naive-ui/es/menu/src/interface";
import SvgIcon from '@/components/icons/SvgIcon.vue'
import {RouterLink} from "vue-router";
import {StringUtil} from "@/utils";

defineComponent({name: 'NavbarLinks'});

interface Props {
	navbarInfo: NavbarInfo;
}

interface Option {
	label?: string | (() => VNodeChild),
	key?: string,
	children?: Array<Option>,
	disabled?: boolean,
	icon?: string | (() => VNodeChild)
}

const props = withDefaults(defineProps<Props>(), {});

const dropdownNavbarOption = ref<Array<Option>>([]);

const renderIcon = (navbar: NavbarInfo) => {
	if (!StringUtil.haveLength(navbar.icon)) return ''
	return h(
		SvgIcon, {
			icon: navbar.icon
		}
	);
};

const renderLink = (navbar: NavbarInfo) => {
	return () => {
		if (!StringUtil.haveLength(navbar.url)) {
			return navbar.name
		}
		if (navbar.outLink) {
			return h('a', {
				href: navbar.url,
				target: '_blank'
			}, {
				default: () => navbar.name
			});
		}else {
			return h(
				// @ts-ignore
				RouterLink,
				{
					to: navbar.url
				}, {
				default: () => navbar.name
			});
		}
	};
};

const packageDropdownNavbar = async (navbarArr: Array<NavbarInfo>): Promise<Array<Option>> => {
	return new Promise((resolve, reject) => {
		const optionArr: Array<Option> = [];
		for (let i = 0; i < navbarArr.length; i++) {
			const option: Option = {
				label: renderLink(navbarArr[i]),
				key: navbarArr[i].name,
				icon: renderIcon(navbarArr[i])
			};
			if (navbarArr[i].children && navbarArr[i].children!.length > 0) {
				packageDropdownNavbar(navbarArr[i].children as Array<NavbarInfo>).then(result => {
					option.children = result;
				});
			}
			optionArr.push(option);
			if (i === navbarArr.length - 1) {
				resolve(optionArr);
			}
		}
	});
};

onBeforeMount(() => {
	if (props.navbarInfo.children && props.navbarInfo.children.length > 0) {
		// @ts-ignore
		packageDropdownNavbar(props.navbarInfo.children).then(result => {
			dropdownNavbarOption.value = result
		})
	}
});
</script>

<style scoped>

</style>