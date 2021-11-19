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
                <span @click="goPage(item.path)">{{item.title === "" ? noTitle : item.title}}</span>
              </div>
              <div :data="item.title" class="timeline-create-page-time">
                <span>{{item.time}}</span>
              </div>
            </div>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
let noTitle = '暂时没有标题'
try {
  noTitle = __NO_TITLE__
}catch (e) {

}
export default {
  name: "TimelineItem",
  data() {
    return {
      allMonth: [],
      currentMonthPageArr: [],
      noTitle: noTitle
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < this.allPageDataArr.length; i++) {
        if (this.allPageDataArr[i].year === this.pageYear && this.allPageDataArr[i].month === this.month) {
          this.currentMonthPageArr.push(this.allPageDataArr[i])
        }
      }
      resolve()
    }).then(() => {
      this.currentMonthPageArr.sort(this.compare(""))
    })
  },
  props: {
    month: '',
    pageYear: '',
    allPageDataArr: {
      type: Array,
      default() {
        return []
      }
    }
  },
  methods: {
    goPage(path) {
      this.$router.push(path)
    },
    compare(updatedTime) {
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

<style scoped>

</style>