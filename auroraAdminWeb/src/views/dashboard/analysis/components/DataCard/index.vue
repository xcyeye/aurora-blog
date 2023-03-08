<template>
  <n-grid cols="s:1 m:2 l:4" responsive="screen" :x-gap="16" :y-gap="16">
    <n-grid-item v-for="item in cardDataArr" :key="item.id">
      <gradient-bg class="h-100px" :start-color="item.colors[0]" :end-color="item.colors[1]">
        <h3 class="text-16px">{{ item.title }}</h3>
        <div class="flex justify-between pt-12px">
          <svg-icon :icon="item.icon" class="text-32px" />
          <count-to
            :prefix="item.unit"
            :start-value="1"
            :end-value="item.value"
            class="text-30px text-white dark:text-dark"
          />
        </div>
      </gradient-bg>
    </n-grid-item>
  </n-grid>
</template>

<script setup lang="ts">
import { GradientBg } from './components';
import {useAuthStore} from "@/store";
import {articleApi, commentApi, emailApi, emailLogApi, fileApi, linkApi, talkApi, userApi} from "@/service";
import {ref} from "vue";
import {StringUtil} from "@/utils";

defineOptions({ name: 'DashboardAnalysisDataCard' });

interface CardData {
  id: string;
  title: string;
  value: number;
  unit: string;
  colors: [string, string];
  icon: string;
	showRole?: string
}

const authStore = useAuthStore()
const cardDataArr = ref<Array<CardData>>([
	{
		id: 'visit',
		title: '用户',
		value: 0,
		unit: '',
		colors: ['#ec4786', '#b955a4'],
		icon: 'fa:user',
		showRole: 'ROLE_root'
	},
	{
		id: 'amount',
		title: '文章',
		value: 0,
		unit: '',
		colors: ['#865ec0', '#5144b4'],
		icon: 'fa:pagelines'
	},
	{
		id: 'download',
		title: '评论',
		value: 0,
		unit: '',
		colors: ['#56cdf3', '#719de3'],
		icon: 'fa:commenting'
	},
	{
		id: 'trade',
		title: '留言',
		value: 0,
		unit: '',
		colors: ['#fcbc25', '#f68057'],
		icon: 'fa:comments'
	},
	{
		id: 'email',
		title: '邮件',
		value: 0,
		unit: '',
		colors: ['#b9faf8', '#b8d0e8'],
		icon: 'fa:envelope'
	},
	{
		id: 'talk',
		title: '说说',
		value: 0,
		unit: '',
		colors: ['#f2b5d4', '#f7d6e0'],
		icon: 'fa:modx'
	},
	{
		id: 'friend-link',
		title: '友情链接',
		value: 0,
		unit: '',
		colors: ['#7bdff2', '#b2f7ef'],
		icon: 'fa:paper-plane'
	},
	{
		id: 'file',
		title: '文件',
		value: 0,
		unit: '',
		colors: ['#ef6351', '#f38375'],
		icon: 'fa:file'
	}
])
const cardData: CardData[] = [
  {
    id: 'visit',
    title: '用户',
    value: 0,
    unit: '',
    colors: ['#ec4786', '#b955a4'],
    icon: 'fa:user',
		showRole: 'ROLE_root'
  },
  {
    id: 'amount',
    title: '文章',
    value: 0,
    unit: '',
    colors: ['#865ec0', '#5144b4'],
    icon: 'fa:pagelines'
  },
  {
    id: 'download',
    title: '评论',
    value: 0,
    unit: '',
    colors: ['#56cdf3', '#719de3'],
    icon: 'fa:commenting'
  },
  {
    id: 'trade',
    title: '留言',
    value: 0,
    unit: '',
    colors: ['#fcbc25', '#f68057'],
    icon: 'fa:comments'
  },
	{
		id: 'email',
		title: '邮件',
		value: 0,
		unit: '',
		colors: ['#b9faf8', '#b8d0e8'],
		icon: 'fa:envelope'
	},
	{
		id: 'talk',
		title: '说说',
		value: 0,
		unit: '',
		colors: ['#f2b5d4', '#f7d6e0'],
		icon: 'fa:modx'
	},
	{
		id: 'friend-link',
		title: '友情链接',
		value: 0,
		unit: '',
		colors: ['#7bdff2', '#b2f7ef'],
		icon: 'fa:paper-plane'
	},
	{
		id: 'file',
		title: '文件',
		value: 0,
		unit: '',
		colors: ['#ef6351', '#f38375'],
		icon: 'fa:file'
	}
];

const loadUserData = () => {
	// cardDataArr.value = []
	commentApi.queryTotalCount({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '评论') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'download',
			// 	title: '评论',
			// 	value: result.data,
			// 	unit: '',
			// 	colors: ['#56cdf3', '#719de3'],
			// 	icon: 'fa:commenting'
			// })
		}
	})

	userApi.queryTotalCount({}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '用户') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'visit',
			// 	title: '用户',
			// 	value: result.data,
			// 	unit: '',
			// 	colors: ['#ec4786', '#b955a4'],
			// 	icon: 'fa:user',
			// 	showRole: 'ROLE_root'
			// })
		}
	})

	articleApi.queryTotalCount({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '文章') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'amount',
			// 	title: '文章',
			// 	value: result.data,
			// 	unit: '',
			// 	colors: ['#865ec0', '#5144b4'],
			// 	icon: 'fa:pagelines'
			// })
		}
	})

	if (authStore.userInfo.verify_email) {
		emailApi.queryEmailByUserUid({userUid: authStore.userInfo.user_uid}).then(result => {
			if (result.data && StringUtil.haveLength(result.data.email)) {
				emailLogApi.queryTotalCount({receiver: result.data.email, send: true}).then(result => {
					if (result.data) {
						cardDataArr.value.forEach(v => {
							if (v.title === '邮件') {
								v.value = result.data
							}
						})
						// cardDataArr.value.push({
						// 	id: 'email',
						// 	title: '邮件',
						// 	value: result.data,
						// 	unit: '',
						// 	colors: ['#b9faf8', '#b8d0e8'],
						// 	icon: 'fa:envelope'
						// })
					}
				})
			}
		})
	}else {
		window.$message?.error('您的账户未绑定邮箱，请先绑定')
		// cardDataArr.value.push({
		// 	id: 'email',
		// 	title: '邮件',
		// 	value: 0,
		// 	unit: '',
		// 	colors: ['#b9faf8', '#b8d0e8'],
		// 	icon: 'fa:envelope'
		// })
	}

	talkApi.queryTotalCount({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '说说') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'talk',
			// 	title: '说说',
			// 	value: 0,
			// 	unit: '',
			// 	colors: ['#f2b5d4', '#f7d6e0'],
			// 	icon: 'fa:modx'
			// })
		}
	})

	linkApi.queryTotalCount({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '友情链接') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'friend-link',
			// 	title: '友情链接',
			// 	value: result.data,
			// 	unit: '',
			// 	colors: ['#7bdff2', '#b2f7ef'],
			// 	icon: 'fa:paper-plane'
			// })
		}
	})

	fileApi.queryTotalCount({userUid: authStore.userInfo.user_uid}).then(result => {
		if (result.data) {
			cardDataArr.value.forEach(v => {
				if (v.title === '文件') {
					v.value = result.data
				}
			})
			// cardDataArr.value.push({
			// 	id: 'file',
			// 	title: '文件',
			// 	value: result.data,
			// 	unit: '',
			// 	colors: ['#ef6351', '#f38375'],
			// 	icon: 'fa:file'
			// })
		}
	})

}

loadUserData()
</script>

<style scoped></style>
