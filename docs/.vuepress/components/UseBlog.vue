<template>
  <!--docs/.vuepress/components/Aurora.vue-->
  <aurora-global :show-bg="true" :show-style-menu="true" :show-navbar="true">
    <template #top>
      <div class="use-blog-top">
        <top-image :is-show-top-img="true"
                   :theme-property="themeConfig"
                   :is-show-head-line="false"
                   :show-mood-edit="false"
                   head-line="">
        </top-image>
        <div class="use-blog-message mood-article">
          <div id="theme-default-content" class="theme-default-content pageContent medium-zoom-content">
            <h2 class="use-blog-title">🎉🎉🎉🎉🎉</h2>
            <span class="use-blog-desc">
            这里是Aurora主题的示例博客站点，只要你正使用Aurora主题，都可以在此页面添加你的博客站点
          </span>
            <ul>
              <li>在此页面底部留言</li>
              <li>在<a target="_blank" href="https://github.com/qsyyke/aurora-docs/blob/main/docs/.vuepress/public/user.js">Github</a>中进行提交</li>
            </ul>

            <div class="language-javascript ext-js line-numbers-mode"><pre class="language-javascript"><code><span class="token punctuation">{</span>
    title<span class="token operator">:</span> <span class="token string">"网站标题"</span><span class="token punctuation">,</span>
    url<span class="token operator">:</span> <span class="token string">"站点链接"</span><span class="token punctuation">,</span>
    logo<span class="token operator">:</span> <span class="token string">"站点logo"</span><span class="token punctuation">,</span>
    describe<span class="token operator">:</span> <span class="token string">"站点描述"</span><span class="token punctuation">,</span>
    cover<span class="token operator">:</span> <span class="token string">'站点封面'</span>
<span class="token punctuation">}</span>
</code></pre><div class="line-numbers"><span class="line-number">1</span><br><span class="line-number">2</span><br><span class="line-number">3</span><br><span class="line-number">4</span><br><span class="line-number">5</span><br><span class="line-number">6</span><br><span class="line-number">7</span><br></div></div>
          </div>
        </div>
      </div>
    </template>
    <template #center>
      <div class="use-blog mood-article" id="use-blog">
        <a v-for="(item,index) in sites" id="link-a" target="_blank" :href="item.url">
          <div class="link-item use-link-item" :style="setTopBg(index)" id="link-item">
            <!--<div class="link-item" :style="setTopBg" id="link-item">-->
            <div class="link-top">
              <div class="link-img">
                <img id="link-img" :src="item.logo" :alt="item.title">
              </div>
            </div>
            <div class="link-bottom" :style="setLinkColor(index,item.cover)" >
              <!--<div class="link-bottom" >-->
              <div class="link-bottom-title link-bottom-common">
                <span>{{item.title}}</span>
              </div>
              <div class="link-bottom-describe link-bottom-common">
                <span id="link-bottom-describe">{{item.describe}}</span>
              </div>
            </div>
          </div>
        </a>
      </div>
    </template>
    <template #bottom>
      <div class="use-blog-comment">
        <comment :path-name="$route.path"/>
      </div>
      <aurora-footer/>
    </template>
  </aurora-global>
</template>

<script>
import { sites } from '../public/user'

export default {
  name: "UseBlog",
  data() {
    return {
      themeConfig: {},
      sites: [],
      colors: [],
      randomColors: ['#ffcad4','#d8e2dc','#8d99ae','#b8f2e6','#84c7d0',
        '#aed9e0','#00b4d8','#caf0f8','#fbc4ab','#fdc5f5',
        '#84dcc6','#a9def9','#fcf6bd','#f0a6ca','#b9faf8',
        '#42a5f5','#ff9800','#b39ddb','#6d45bb','#b388ff',
        '#1565c0','#26c6da','#5e548e','#90f1ef','#5b5f97',
        '#bbe6e4','#42bfdd','#72ddf7','#8093f1','#9ed8d8',
        '#7ea8be','#ef90b3','#b892ef','#c0b9dd','#c0d9dd',
        '#75c9c8','#ded9e2','#b5e2fa','#62b6cb','#5fa8d3',
        '#0fa3b1','#b5e2fa','#5fa8d3','#62b6cb','#b892ff'],
    }
  },
  created() {
    this.sites = sites
    for (let i = 0; i < this.sites.length; i++) {
      let color = this.randomColors[this.getRandomInt(0,this.randomColors.length -1)]
      let hexToRgb = this.hexToRgb(color);
      this.colors.push(hexToRgb)
    }
  },
  methods: {
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min; //不含最大值，含最小值
    },
    hexToRgb(hex) {
      let result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
      return result ? {
        r: parseInt(result[1], 16),
        g: parseInt(result[2], 16),
        b: parseInt(result[3], 16)
      } : null;
    }
  },
  computed: {
    setLinkColor() {
      return (item,cover) => {
        //return 'color: rgb(' + this.hexRgb.r +"," + this.hexRgb.g + "," + this.hexRgb.b + "); background-image: url("+ cover +");"
        if (cover === undefined || cover === "") {
          return "background-color: white;" + 'color: rgb(' + this.colors[item].r +"," + this.colors[item].g + "," + this.colors[item].b + ");"
        }else {
          let randomNum1 = this.getRandomInt(0,9999999999)
          let randomNum2 = this.getRandomInt(0,9999999999)
          let logoPath = cover + "?time=" + randomNum1 + cover + randomNum2
          return "background-image: url("+ logoPath +");"
        }
      }
    },
    setTopBg() {
      return (index) => {
        return 'background-color: rgba(' + this.colors[index].r +"," + this.colors[index].g + "," + this.colors[index].b + ",.7);"
      }
    },
    /*setLinkColor() {
      return (index) => {
        return 'color: rgb(' + this.colors[index].r +"," + this.colors[index].g + "," + this.colors[index].b + ");"
      }
    }*/
  }
}
</script>

<style scoped lang="css">
.use-blog {
  margin: 0 auto;
  height: max-content;
}
#use-blog {
  margin-bottom: 3rem;
}
.use-blog-message,.use-blog-comment {
  margin: 0 auto;
}
.use-blog-code {
  background: rgba(255,255,255,0);
}
.use-blog-message {
  margin-top: 3rem;
  margin-bottom: 3rem;
}
@media screen and (min-width: 719px) {
  .use-blog,.use-blog-comment,.use-blog-message {
    width: 70%;
  }
  #link-item {
    width: calc(85% / 3);
  }
}

@media screen and (max-width: 719px) {
  .use-blog,.use-blog-comment,.use-blog-message {
    width: 96%;
  }

  #link-item {
    width: 100%;
  }
  .use-blog-comment {
    margin-bottom: 3rem;
  }
}
.use-link-item {
  margin-top: 2rem;
}
</style>