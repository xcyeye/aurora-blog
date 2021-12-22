<template>
  <div class="timeline-box" id="timeline-box">
    <div class="timeline-item" v-for="(item,index) in allYearArr" :key="index">
      <div class="timeline-year">
        <span class="timeline-year-title">{{item}}</span>
      </div>
      <archive-item v-for="month in getAllMonthArr(item)" :month="month" :page-year="item" :all-page-data-arr="allPageDataArr"></archive-item>
    </div>
  </div>
</template>

<script>
import pageData from '@temp/page-data'
import ArchiveItem from "./ArchiveItem.vue";
let excludes = []
try {
  excludes = __EXCLUDES__;
}catch (e) {
  console.warn(e)
}
export default {
  name: "AuroraArchive",
  components: {
    ArchiveItem
  },
  data() {
    return {
      allPageDataArr: [],
      allYearArr: []
    }
  },
  created() {
    let newPageArr = []
    let allPageArr = []
    let allYearSet = new Set()
    new Promise((resolve,reject) => {
      newPageArr = pageData.filter((pageData) => {
        let includes = excludes.includes(pageData.path);
        let title = pageData.title
        //git commit的时间

        let updatedTime = ''
        try {
          updatedTime = pageData.data.git.updatedTime
        }catch (e) {
          updatedTime = "2021/01/01"
        }

        if (pageData.data.frontmatter.date !== undefined) {
          updatedTime = new Date(pageData.data.frontmatter.date).getTime()
        }

        if (!includes) {
          allYearSet.add(this.getLocalTime(updatedTime,true,false,false))
          allPageArr.push({
            title: title,
            updatedTime: updatedTime,
            path: pageData.path,
            year: this.getLocalTime(updatedTime,true,false,false),
            month: this.getLocalTime(updatedTime,false,true,false),
            time: this.getLocalTime(updatedTime,false,false,false),
            day: this.getLocalTime(updatedTime,false,false,true),
          })
        }
        return !excludes.includes(pageData.path);
      })
      resolve()
    }).then(() => {
      //设置时间
      this.allPageDataArr = allPageArr.sort(this.compare("updatedTime"))
      this.allYearArr = Array.from(allYearSet)
    })
  },
  computed: {
      getAllMonthArr() {
        return (item) => {
          let allMonthSet = new Set()
          for (let i = 0; i < this.allPageDataArr.length; i++) {
            if (item === this.allPageDataArr[i].year) {
              allMonthSet.add(this.allPageDataArr[i].month)
            }

            if (i === this.allPageDataArr.length -1) {
              return Array.from(allMonthSet).sort(this.compareMonth(""))
            }
          }
        }
      }
  },
  methods: {
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
    compare(updatedTime) {
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
    getLocalTime(time,isYear,isMonth,isDay) {
      if (time === undefined) {
        //没有时间戳
        return ''
      }

      if (time === 0) {
        //没有时间戳
        return ''
      }

      let date = new Date(time);
      let day = date.getDate()
      let year = date.getFullYear()
      let month = date.getMonth() + 1
      let hours = date.getHours()
      let min = date.getMinutes()
      let sec = date.getSeconds()
      if (isYear) {
        return year
      }
      if (isMonth) {
        return month
      }
      if (isDay) {
        return day
      }
      return year + "-" + month + "-" + day + " "
    },
  }
}
</script>