<template>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="donate" id="donate">
    <div class="donate-top" id="donate-top">
      <button @click="clickDonate">赞赏</button>
      <button @click="clickDonateList" >列表</button>
    </div>
    <div class="donate-pay-filed donate-bottom"
         :class="{donateActive: isActive}"
         :style="setSpanStyle" id="donate-bottom">
      <div class="donate-img donate-bottom-common" id="donate-img">
        <li :key="index" v-for="(item,index) in themeProperty.donate.donateImg">
          <img :src="item" alt="">
        </li>
      </div>
      <div class="donate-pro" id="donate-pro">
        <div :key="index" v-for="(item,index) in themeProperty.donate.donateProduct"   class="donate-bottom-common donate-pro-single">
          <div class="pro-img pro-common" id="pro-img">
            <img :src="item.img" alt="">
          </div>
          <div class="pro-name pro-common">
            <span>{{item.name}}</span>
          </div>
          <div class="pro-price pro-common">
            <span>{{item.prefix}}&nbsp;{{item.price}}</span>
          </div>
        </div>
      </div>
      <form action="https://pay.cco.vin/pay/" method="post">
        <div class="donate-pay" id="donate-pay">
          <div class="pro-common pro-message">
            <div class="donate-bottom-input pro-common">
              <input type="number" name="payMoney">
            </div>
            <div class="donate-bottom-button pro-common">
              <button>赞助</button>
            </div>
          </div>
          <div class="pro-single pro-message">
            <div class="donate-bottom-input">
              <input id="pro-input-message" name="msg" type="text">
              <input style="display:none" name="target" :value="getHost" type="text">
              <input style="display:none" name="type" value="xml" type="text">
            </div>
          </div>
        </div>
      </form>
    </div>
    <div class="donate-list-filed donate-bottom"
         :style="setSpanStyle"
         :class="{donateListActive: donateListActive}"
         id="donate-bottom2">
      <div class="donate-pro" id="donate-pro-list">
        <div :key="index" v-for="(item,index) in themeProperty.donate.donateList"   class="donate-bottom-common donate-pro-single">
          <div class="pro-img pro-common" id="pro-img-list">
            <span>{{item.name}}</span>
          </div>
          <div class="pro-name pro-common" id="pro-list-message">
            <span>{{item.msg}}</span>
          </div>
          <div class="pro-price pro-common">
            <span>{{item.prefix}}&nbsp;{{item.price}}</span>
          </div>
        </div>
        <div :key="index" v-for="(item,index) in donateList"
             class="donate-bottom-common donate-pro-single">
          <div class="pro-img pro-common" id="pro-img-list">
            <span>{{item.username}}</span>
          </div>
          <div class="pro-name pro-common" id="pro-list-message">
            <span>{{item.subject}}</span>
          </div>
          <div class="pro-price pro-common">
            <span>￥&nbsp;{{item.payMoney}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import myData from '@temp/my-data'
import $ from 'jquery'
const network = require('../../public/js/network')
export default {
  name: "Donate",
  data() {
    return {
      hexToRgbColor: null,
      isActive: true,
      themeProperty: null,
      donateListActive: true,
      window: "",
      donateList: null
    }
  },
  created() {
    new Promise((resolve,reject) => {
      for (let i = 0; i < myData.length; i++) {
        if (myData[i].path === '/') {
          this.themeProperty = myData[i].frontmatter
        }
      }
      resolve()
    })

    let background_color = this.themeProperty.randomColor[
        this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
    this.hexToRgbColor = this.hexToRgb(background_color)

    network.req({
      baseURL: 'https://pay.cco.vin/pay/info'
      //baseURL: 'http://localhost:8099/pay/info'
    }).then((res) => {
      this.donateList = res
    })

  },
  mounted() {
    this.window = window
  },
  computed: {
    setSpanStyle() {
      return "background-color: rgba(" + this.hexToRgbColor.r + "," +
          this.hexToRgbColor.g + "," + this.hexToRgbColor.b + "," +
          (this.$store.state.varOpacity * 1.2) + ");"
    },
    getHost() {
      return "https://blog.cco.vin"
    },
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    hexToRgb(hex) {
      var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    },
    clickDonate() {
      let display = $(".donate-list-filed").css("display")
      if (display === 'block') {
        $(".donate-list-filed").slideToggle(350,() => {
          setTimeout(() => {
            $(".donate-pay-filed").slideToggle(350)
          },150)
        })
      }else {
        $(".donate-pay-filed").slideToggle(350)
      }
    },
    clickDonateList() {
      let display = $(".donate-pay-filed").css("display")
      if (display === 'block') {
        $(".donate-pay-filed").slideToggle(350,() => {
          setTimeout(() => {
            $(".donate-list-filed").slideToggle(350)
          },150)
        })
      }else {
        $(".donate-list-filed").slideToggle(350)
      }

    }
  },
}
</script>

<style scoped>

</style>