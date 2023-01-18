<template>
  <div class="timeline-box" id="timeline-box">
    <div class="timeline-item" v-for="(item,index) in allYearArr" :key="index">
      <div class="timeline-year">
        <span class="timeline-year-title">{{item}}</span>
      </div>
      <archive-item v-for="month in getAllMonthArr(item)"
										:month="month"
										:page-year="item"
										:article-arr="articleArrTemp"></archive-item>
    </div>
  </div>
</template>

<script lang="ts">
import {PropType} from "vue";
import {ArticleVo} from "@/bean/vo/article/ArticleVo";
import {removeDuplicateElement} from "@/utils";

const articleArrTemp: Array<ArticleVo> = []
const allYearArr: Array<string> = []
export default {
  name: "AuroraArchive",
  data() {
    return {
			articleArrTemp,
      allYearArr,
    }
  },
	props: {
		articleArr: {
			type: Object as PropType<Array<ArticleVo>>
		}
	},
  created() {
		if (this.articleArr && this.articleArr.length > 0) {
			this.loadShowData()
		}
  },
  computed: {
      getAllMonthArr() {
        return (year: string) => {
          let allMonthSet = new Set()
          for (let i = 0; i < this.articleArrTemp.length; i++) {
            if (year === this.getLocalTime(this.articleArrTemp[i].createTime)) {
              allMonthSet.add(this.getLocalTime(this.articleArrTemp[i].createTime, true))
            }

            if (i === this.articleArrTemp.length -1) {
              return Array.from(allMonthSet).sort(this.compareMonth(""))
            }
          }
        }
      }
  },
  methods: {
		loadShowData() {
			this.allYearArr = []
			this.articleArrTemp = []
			this.allYearArr = removeDuplicateElement(this.articleArr.map((v: ArticleVo) => this.getLocalTime(v.createTime as string)).concat())
			this.articleArrTemp = this.articleArr.sort(this.compare("createTime"))
		},
    compareMonth(updatedTime) {
      return (value1, value2) => {
        if (value2  > value1) {
          return  1;
        }  else  if (value2  < value1) {
          return  - 1;
        }  else {
          return  0;
        }
      }
    },
    compare(createTime) {
      return  function( object1, object2) {
        let value1  = object1.updatedTime;
        let value2  = object2.updatedTime;
        if (value2  < value1) {
          return  1;
        }  else  if (value2  > value1) {
          return  - 1;
        }  else {
          return  0;
        }
      }
    },
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
  },
	watch:{
		articleArr() {
			this.loadShowData()
		}
	}
}
</script>