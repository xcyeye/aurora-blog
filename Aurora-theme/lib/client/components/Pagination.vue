<!--这是一个分页组件-->
<template>
  <div class="home-cut-page">
    <div @click="prev" v-if="total !== 0" class="home-cut-page-change" :class="{'disabled': currentPage<=1}">
      <span class="home-menu-ico cute-page-icon-common" style="--homeIcoCode: '\e6aa'"></span>
    </div>

    <div class="home-cut-page-num">
      <li v-for="i in pageList" @click="changePage(i)" :id="getActiveId(i)"
          :key="i" :class="{'cutPageActive': currentPage === i}">
        <span>{{i}}</span>
      </li>
    </div>

    <div @click="next" v-if="total !== 0" id="cute-page-right" class="home-cut-page-change" :class="{'disabled': currentPage>=pageTotal}">
      <span class="home-menu-ico cute-page-icon-common" style="--homeIcoCode: '\e6ac'"></span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CutePage',
  data() {
    return {
      // 总页数
      pageTotal: 0,
      // 页码列表
      pageList: [],
      // 当前页码前后间隔数
      breakPageNum: 0,
      // 额外页码数
      otherPage: 0,
      // 每页显示页码数
      pageListCount_: 5,
      currentPageNum: 1
    }
  },
  props: {
    // 当前页
    currentPage: {
      type: Number,
      default() {
        return 1
      }
    },
    pageSize: {
      type: Number,
      default() {
        return 10
      }
    },
    //总数据条数
    total: {
      type: Number,
      default() {
        return 0
      }
    },
    pageListCount: {
      type: Number,
      default() {
        return 5
      }
    }
  },
  methods: {
    render(beginPage) {
      // 当总记录数小于显示页码数时, 将调整显示页码数为总记录数
      if (this.pageTotal <= this.pageListCount_) {
        this.pageListCount_ = this.pageTotal
        this.pageList = []
      }

      for(let index = beginPage, i = 0; i < this.pageListCount_; index++, i++) {
        this.pageList[i] = index
      }
    },
    changePage(page) {
      // 当前页切换
      this.currentPageNum = page
    },
    next() {
      // 下一页
      if (this.currentPageNum < this.pageTotal) {
        this.currentPageNum++
      }
    },
    prev() {
      // 上一页
      if (this.currentPageNum > 1) {
        this.currentPageNum--
      }
    },
    getBreakPageNum() {
      // 计数当前页码前后间隔数
      this.breakPageNum = parseInt(this.pageListCount_ / 2)
      // 如果每页显示的页码数是偶数的话则添加额外1个页码, 用于弥补偶数pageSize不显示最后一个页码的bug
      this.otherPage = this.pageListCount_ % 2 === 0 ? 1 : 0
    },
    totalInit() {
      // 当total有值时将开始计算页码数
      this.pageTotal = Math.ceil(this.total / this.pageSize)
      this.getBreakPageNum()
      let beginPage = this.currentPageNum - this.breakPageNum < 1 ? 1 : this.currentPageNum - this.breakPageNum
      if (this.pageTotal - this.currentPageNum) {}
      this.render(beginPage)
    }
  },
  computed: {
    getActiveId() {
      return (index) => {
        return this.currentPageNum === index ? "cutPageActive" : ""
      }
    }
  },
  watch: {
    pageTotal(nV) {
      // 当前页修改时触发
      if (this.currentPageNum > this.breakPageNum) {
        if (((this.pageTotal + this.otherPage) - this.breakPageNum) >=this.currentPageNum) {
          let beginPage = this.currentPageNum - this.breakPageNum
        } else if ((this.currentPageNum + this.breakPageNum) >= this.pageTotal && this.currentPageNum <= this.pageTotal) {
          let beginPage = this.pageTotal - (this.pageListCount_ - 1)
          this.render(beginPage)
        }
      } else {
        this.render(1)
      }
    },
    currentPage(nV) {
      this.currentPageNum = nV
    },
    currentPageNum(nv) {
      this.currentPageNum = nv;
      this.getBreakPageNum()
      // 当前页修改时触发
      if (this.currentPageNum > this.breakPageNum) {
        if (((this.pageTotal + this.otherPage) - this.breakPageNum) >=this.currentPageNum) {
          let beginPage = this.currentPageNum - this.breakPageNum
          this.render(beginPage)
        } else if ((this.currentPageNum + this.breakPageNum) >= this.pageTotal && this.currentPageNum <= this.pageTotal) {
          let beginPage = this.pageTotal - (this.pageListCount_ - 1)
          this.render(beginPage)
        }
      } else {
        this.render(1)
      }

      // 当前页修改时触发自定义事件
      this.$emit('changePage', this.currentPageNum)
    },
    total(newValue) {
      // 重置每页显示页码数
      this.pageListCount_ = this.pageListCount
      this.totalInit()
    }
  },
  created() {

    this.currentPageNum = this.currentPage
    this.totalInit()
  }
}
</script>