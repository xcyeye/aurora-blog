<template>
  <div style="display: none">
    <div class="can-img">
      <h2>sldkfh</h2>
      <!--<h3>文字</h3>
      <img style="width: 5rem;height: 5rem"  src="https://www.bing.com/th?id=OHR.HowgillFells_ZH-CN1134328886_1920x1080.jpg&rf=LaDigue_1920x1080.jpg" alt="">-->
    </div>
  </div>
  <button @click="test">点击</button>

  <!--<div>
    <h1>图片</h1>
    <img :src="href" alt="">
  </div>-->
</template>

<script>
import html2canvas from 'html2canvas'
export default {
  name: "CanvImg",
  data() {
    return {
      href: ''
    }
  },
  methods: {
    test() {
      html2canvas(document.querySelector(".can-img"),{
        onclone: () => {
          //console.log("克隆了")
        },
        logging: false,
        removeContainer: false,
        allowTaint: true,
        //proxy: this._proxyURL,
        useCORS: true,
        width: 100,
        height: 200,
        backgroundColor: 'red'
        //foreignObjectRendering: true
      }).then(canvas => {
        document.body.appendChild(canvas)

        //canvas.toDataURL("image/png");
        this.href = this.convertCanvasToImage(canvas).src
        //console.log(this.href)
        //this.convertCanvasToImage(canvas)
      });
    },
    convertCanvasToImage(canvas) {
      var image = new Image();
      image.setAttribute("crossOrigin","anonymous")
      //image.crossOrigin="anonymous"
      image.src = canvas.toDataURL("image/png");
      return image;
    }
  }
}
</script>

<style scoped>
  .can-img {
    /*background-image: url("https://ooszy.cco.vin/img/blog-note/image-20210904175030428.png");*/
    background-repeat: no-repeat;
    background-size: contain;
    width: 10rem;
    height: 10rem;
    background-color: red;
    border-radius: 10px;
    /*background-color: plum;*/
  }
</style>