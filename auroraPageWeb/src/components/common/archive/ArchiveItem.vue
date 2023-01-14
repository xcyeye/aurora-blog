<template>
  <div class="timeline-single" id="timeline-single">
    <div class="timeline-create-links" id="timeline-create-links">
      <div class="timeline-create-time" id="timeline-create-time">
        <span>{{month}} 月 {{currentMonthPageArr.length}} 篇</span>
      </div>

      <div class="timeline-create-link-single" id="timeline-create-link-single">
        <ul>
          <li v-for="(item,index) in currentMonthPageArr" :key="index">
            <div class="timeline-line">
              <div class="timeline-line-icon-par">
                <div class="timeline-line-icon"></div>
              </div>
              <div class="timeline-line-bottom"></div>
            </div>
            <div class="timeline-content">
              <div :data="item.title" class="timeline-title">
                <span @click="goPage(`/article/${item.uid}`)">{{item.title === "" ? noTitle : item.title}}</span>
              </div>
              <div :data="item.title" class="timeline-create-page-time">
                <span>{{item.createTime}}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";

const currentMonthPageArr: Array<ArticleVo> = []
export default {
  name: "TimelineItem",
  data() {
    return {
      allMonth: [],
      currentMonthPageArr,
      noTitle: '暂时还没有标题'
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < this.articleArr.length; i++) {
        if (this.getLocalTime(this.articleArr[i].createTime) === this.pageYear && this.getLocalTime(this.articleArr[i].createTime, true) === this.month) {
          this.currentMonthPageArr.push(this.articleArr[i])
        }
      }
      resolve(null)
    }).then(() => {
      this.currentMonthPageArr.sort(this.compare(""))
    })
  },
  props: {
    month: '',
    pageYear: '',
    articleArr: {
      type: Object as PropType<Array<ArticleVo>>
    }
  },
  methods: {
		getLocalTime(time: string | number | Date, isMonth?: boolean) {
			if (time === undefined) {
				//没有时间戳
				return ''
			}
		
			if (time === 0) {
				//没有时间戳
				return ''
			}
		
			let date = new Date(time);
			let month = date.getMonth() + 1
			if (isMonth) {
				return month
			}
			return date.getFullYear();
		},
    goPage(path: string) {
      this.$router.push(path)
    },
    compare(updatedTime: string) {
      return  function( object1, object2) {
        let value1  = object1.updatedTime;
        let value2  = object2.updatedTime;
        if (value2  > value1) {
          return  1;
        }  else  if (value2  < value1) {
          return  - 1;
        }  else {
          return  0;
        }
      }
    },
  }
}
</script>