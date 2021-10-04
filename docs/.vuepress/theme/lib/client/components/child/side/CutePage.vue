<template>
  <div class="home-cut-page">
    <div class="home-cut-page-change" :class="{'disabled': currentPage<=1}">
      <span @click="prev" class="icon-chevron-left"></span>
    </div>

    <div class="home-cut-page-num">
      <li v-for="i in pageList" :id="getActiveId(i)"
          :key="i" :class="{'cutPageActive': getCurrentPage == i}">
        <span  @click="changePage(i)">{{i}}</span>
      </li>
    </div>

    <div class="home-cut-page-change" :class="{'disabled': currentPage>=pageTotal}">
      <span @click="next" class="icon-cheveron-right"></span>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CutePage',
  data() {
    return {
      // 当前页
      currentPage: 1,
      // 当前页输入框
      currentPageInput: 1,
      // 每页显示条目数
      pageSize_: 10,
      // 总页数
      pageTotal: 0,
      // 页码列表
      pageList: [],
      // 当前页码前后间隔数
      breakPageNum: 0,
      // 额外页码数
      otherPage: 0,
      // 每页显示页码数
      pageListCount_: 5
    }
  },
  props: {
    showHomePageImg: {
      type: Boolean,
      default() {
        return false;
      }
    },
    page: {
      type: Number,
      default() {
        return 1
      }
    },
    pageSize: {
      default: 10
    },
    total: {
      default: 0
    },
    pageSizeList: {
      default: function() {
        return [10, 15, 20, 25, 30]
      }
    },
    pageListCount: {
      default: 5
    },
    showJumper: {
      default: false
    },
    showSizes: {
      default: false
    }
  },
  methods: {
    setHomePageImg() {
      if (!this.showHomePageImg) {
        setTimeout(() => {
          let contentHtmlImg = document.querySelectorAll(".home-page-tag-content img")
          for (let i = 0; i < contentHtmlImg.length; i++) {
            contentHtmlImg[i].setAttribute("src","https://ooszy.cco.vin/img/ico/loading.png")
            contentHtmlImg[i].setAttribute("style","transform: scale(.2);")
          }
        },10)
      }
    },
    render(beginPage) {
      // 当总记录数小于显示页码数时, 将调整显示页码数为总记录数
      if (this.pageTotal <= this.pageListCount_) {
        this.pageListCount_ = this.pageTotal
        this.pageList = []
      }
      for(var index = beginPage, i = 0; i < this.pageListCount_; index++, i++) {
        this.pageList[i] = index
      }
    },
    changePage(page) {
      // 当前页切换
      this.currentPage = page
      this.$emit("changPage",this.currentPage)
      this.$store.commit("setCurrentPageNum",{
        currentPageNum: this.currentPage
      })
    },
    jump() {
      // 跳转页面
      if (this.currentPageInput > this.pageTotal) {
        this.currentPageInput = this.pageTotal
      } else if (this.currentPageInput < 1) {
        this.currentPageInput = 1
      }
      this.currentPage = this.currentPageInput
    },
    next() {
      // 下一页
      if (this.currentPage < this.pageTotal) {
        this.currentPage++
        this.$emit("changPage",this.currentPage)
        this.$store.commit("setCurrentPageNum",{
          currentPageNum: this.currentPage
        })
      }
    },
    prev() {
      // 上一页
      if (this.currentPage > 1) {
        this.currentPage--
        this.$emit("changePage",this.currentPage)
        this.$store.commit("setCurrentPageNum",{
          currentPageNum: this.currentPage
        })
      }
    },
    getBreakPageNum() {
      // 计数当前页码前后间隔数
      this.breakPageNum = parseInt(this.pageListCount / 2)
      // 如果每页显示的页码数是偶数的话则添加额外1个页码, 用于弥补偶数pageSize不显示最后一个页码的bug
      this.otherPage = this.pageListCount % 2 == 0 ? 1 : 0
    },
    totalInit() {
      // 当total有值时将开始计算页码数
      this.pageTotal = Math.ceil(this.total / this.pageSize_)
      this.getBreakPageNum()
      let beginPage = this.currentPage - this.breakPageNum < 1 ? 1 : this.currentPage - this.breakPageNum
      this.render(beginPage)
    }
  },
  computed: {
    getCurrentPage() {
      return this.$store.state.currentPageNum
    },
    getActiveId() {
      return (index) => {
        return this.getCurrentPage === index ? "cutPageActive" : ""
      }
    }
  },
  watch: {
    page(newValue,oleValue) {
      this.currentPage = this.page
    },
    currentPage(nv) {
      this.setHomePageImg()
      // 当前页修改时触发
      this.currentPageInput = this.currentPage
      if (this.currentPage > this.breakPageNum) {
        if (((this.pageTotal + this.otherPage) - this.breakPageNum) >= this.currentPage) {
          let beginPage = this.currentPage - this.breakPageNum
          this.render(beginPage)
        } else if ((this.currentPage + this.breakPageNum) >= this.pageTotal && this.currentPage <= this.pageTotal) {
          let beginPage = this.pageTotal - (this.pageListCount_ - 1)
          this.render(beginPage)
        }
      } else {
        this.render(1)
      }
      // 当前页修改时触发自定义事件
      this.$emit('changePage', this.currentPage)
    },
    pageSize() {
      this.pageSize_ = this.pageSize
    },
    pageSize_() {
      // 显示页码数修改时触发
      this.pageTotal = Math.ceil(this.total / this.pageSize_)
      this.pageListCount_ = this.pageTotal <= this.pageListCount_ ? this.pageTotal : this.pageListCount
      let beginPage = 1
      if (this.currentPage + this.breakPageNum >= this.pageTotal) {
        beginPage = this.pageTotal - (this.pageListCount_ - 1)
        beginPage = beginPage <= 1 ? 1 : beginPage
      } else if (this.currentPage - this.breakPageNum <= 1) {
        beginPage = 1
      } else {
        beginPage = this.currentPage - this.breakPageNum
        beginPage = beginPage <= 1 ? 1 : beginPage
      }

      if (this.currentPage >= this.pageTotal) this.currentPage = this.pageTotal
      this.render(beginPage)
      this.$emit('changePageSize', this.pageSize_)
    },
    total(newValue) {
      // 重置每页显示页码数
      this.pageListCount_ = this.pageListCount
      this.totalInit()
    }
  },
  created() {
    this.currentPage = this.page
    this.total && this.totalInit()
    this.pageSize_ = this.pageSize
  }
}
</script>
