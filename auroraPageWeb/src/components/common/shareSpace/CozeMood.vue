<template>
  <div class="coze-image-container" id="coze-image-container"></div>
  <div class="coze-box">
    <div class="coze-mood-list">
			<aurora-card :id="item.uid" :key="item.uid" v-for="(item,index) in talkArr" class="coze-enter-animate">
				<mood-item @mood-comment="moodComment" @mood-love="moodLove" @mood-poster="moodPoster"
									 @mood-edit="moodEdit" :user-uid="userUid" :mood-item="item">
				</mood-item>
			</aurora-card>
    </div>
  </div>
</template>

<script lang="ts">
//导入配置属性
import {defineComponent} from "vue";
import {TalkVo} from "@/bean/vo/article/TalkVo";
import {talkApi} from "@/service";
import {StringUtil} from "@/utils";
import smoothscroll from 'smoothscroll-polyfill';

const talkArr: Array<TalkVo> = []
export default defineComponent({
  name: 'Mood',
	props: {
		userUid: {
			type: String
		},
		talkUid: {
			type: String
		}
	},
  data() {
    return {
      //这是一个数组对象
      color: '',
			talkArr,
      showMoodEdit: true,
      openEditStatus: false,
      num: 1,
      currentMoodObj: {},
      verifyIdentifyStatus: false,
      showMoodControl: false
    }
  },
  emits: ['cozeSuccess','moodEdit','cozeCancelEdit'],
  created() {
    talkApi.queryListDataByCondition({delete: false, show: true, otherUid: this.userUid,pageSize: 300}).then(result => {
			if (result.data && result.data.result) {
				this.talkArr = result.data.result
			}
		})
  },
  methods: {
		jumpTargetTalk() {
			let number = 1
			if (StringUtil.haveLength(this.talkUid)) {
				if (document) {
					smoothscroll.polyfill();
					const time = setInterval(() => {
						if (number === 15) {
							clearInterval(time)
						}
						if (document.getElementById(this.talkUid)) {
							document.getElementById(this.talkUid)!.scrollIntoView({behavior: "smooth", block: 'start'})
							clearInterval(time)
						}
						number = number + 1
					}, 10)
				}
			}
		},
    moodComment(moodItem) {

    },
    moodLove(moodItem) {

    },
    moodPoster(moodItem) {

    },
    moodEdit(moodItem) {
      this.currentMoodObj = moodItem
      this.openEditStatus = !this.openEditStatus
      if (this.verifyIdentifyStatus) {
        //密码验证成功，直接显示
        this.showMoodControl = true
      }else {
        this.showMoodControl = true
      }
      this.$emit("moodEdit",{
        openEditStatus: this.openEditStatus
      })
    }
  },
	mounted() {
		this.jumpTargetTalk()
	},
	watch: {
		talkUid() {
			this.jumpTargetTalk()
		}
	}
})
</script>
<style lang="css">
/*@import "../public/font/iconfont.css";*/
</style>
