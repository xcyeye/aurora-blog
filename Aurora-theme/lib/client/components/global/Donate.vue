<template>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="donate box-common" id="donate">
    <div class="donate-top" id="donate-top">
      <button @click="clickDonate">赞赏</button>
      <button @click="clickDonateList" >列表</button>
    </div>
    <div class="donate-pay-filed donate-bottom"
         :class="{donateActive: isActive}"
         :style="setSpanStyle" id="donate-bottom">
      <div class="donate-img donate-bottom-common theme-donate-qr-img" id="donate-img">
        <li :key="index" v-for="(item,index) in donateImg">
          <img :src="item" alt="">
        </li>
      </div>
      <div class="donate-pro" id="donate-pro">
        <div :key="index" v-for="(item,index) in donateProduct"   class="theme-donate-qr-img donate-bottom-common donate-pro-single">
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
      <!--下面是在线支付的设置-->
      <form v-if="setOnline" action="https://pay.cco.vin/pay/" method="post">
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
        <div :key="index" v-for="(item,index) in localDonateList"  class="donate-bottom-common donate-pro-single">
          <div class="theme-pro-img theme-pro-common aurora-theme-pro-name" id="pro-img-list">
            <span>{{item.name}}</span>
          </div>
          <div class="theme-pro-name theme-pro-common" id="pro-list-message">
            <span>{{item.msg}}</span>
          </div>
          <div class="theme-pro-price theme-pro-common">
            <span>{{item.prefix}}&nbsp;{{item.price}}</span>
          </div>
        </div>

        <!--下面时从网络中请求回来的数据-->
        <div v-if="setOnline" :key="index" v-for="(item,index) in donateList"  class="donate-bottom-common donate-pro-single">
          <div class="theme-pro-img theme-pro-common aurora-theme-pro-name" id="pro-img-list">
            <span>{{item.username}}</span>
          </div>
          <div class="theme-pro-name theme-pro-common" id="pro-list-message">
            <span>{{item.subject}}</span>
          </div>
          <div class="theme-pro-price theme-pro-common">
            <span>￥&nbsp;{{item.payMoney}}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
import {useThemeData} from "../../composables";
const network = require('../../public/js/network')
export default {
  name: "Donate",
  data() {
    return {
      hexToRgbColor: '',
      isActive: true,
      themeProperty: '',
      donateListActive: true,
      window: "",
      donateList: [],
      donate: '',
      donateImg: [],
      //打赏金额产品列表
      donateProduct: []
    }
  },
  created() {
    this.themeProperty = useThemeData().value
    this.donate = this.themeProperty.donate
    try {
      this.donateImg = this.donate.donateImg
    }catch (e) {
      this.donateImg = ['https://ooszy.cco.vin/img/blog-public/wxpay.png','https://ooszy.cco.vin/img/blog-public/zfbpay.jpg']
    }
    try {
      this.donateProduct = this.donate.donateProduct
    }catch (e) {
    }
    if (this.themeProperty.randomColor === undefined || this.themeProperty.randomColor == null) {
      let background_color = this.$store.state.defaultRandomColors[
          this.getRandomInt(0,this.$store.state.defaultRandomColors.length -1)]
      this.hexToRgbColor = this.hexToRgb(background_color)
    }else {
      let background_color = this.themeProperty.randomColor[
          this.getRandomInt(0,this.themeProperty.randomColor.length -1)]
      this.hexToRgbColor = this.hexToRgb(background_color)
    }
    if (this.themeProperty.donate !== undefined) {
      if (this.donate.onlineList) {
        network.req({
          baseURL: 'https://pay.cco.vin/pay/info'
        }).then((res) => {
          this.donateList = res
        })
      }
    }
  },
  mounted() {
    this.window = window
  },
  computed: {
    localDonateList() {
      if (this.themeProperty.donate !== undefined && this.themeProperty.donate != null) {
        return this.themeProperty.donate.donateList
      }else {
        return []
      }
    },
    setOnline() {
      if (this.donate === undefined) {
        return false
      }
      if (this.donate.onlineList === undefined) {
        return false
      }
      return this.donate.onlineList
    },
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