 <template>
   <div class="photo-bg"></div>
   <div class="loadingAnimate">
     <div class="loader">
       <div class="inner one"></div>
       <div class="inner two"></div>
       <div class="inner three"></div>
     </div>
   </div>
   <div class="photo-html" style="display: none"></div>
   <div class="photo-waterfull">
     <div class="waterfull">
       <div class="v-waterfall-content" id="v-waterfall">
         <div v-for="(img, index) in waterfallList" :key="index"
              class="v-waterfall-item hover-fall hover-div"
              :style="getStyle(img)">
           <img style="opacity: 1"  @mouseenter="imgEnter" @mouseleave="imgLeave" @click="openImg" class="medium-zoom-image hover-img hover-div" :src="img.src" alt="">
         </div>
       </div>
     </div>
   </div>
 </template>

 <script>
 import myData from '@temp/my-data'
 import $ from 'jquery'
 import mediumZoom from "medium-zoom";

 export default {
   name: "v-waterfall",
   data() {
     return {
       waterfallList: [],
       // waterfallImgWidth: 100,
       waterfallImgWidth: 200,// 每个盒子的宽度
       // waterfallImgCol: 5,// 瀑布流的列数
       waterfallImgCol: 5,// 瀑布流的列数
       waterfallImgRight: 0,// 每个盒子的右padding
       waterfallImgBottom: -45,// 每个盒子的下padding
       waterfallDeviationHeight: [],
       imgList: [],
       clientWidth: 500,
       height: 500,
       photos: [],
       photoData: null,
       existPhotos: false
     }
   },
   created() {
     for (let i = 0; i < myData.length; i++) {
       if (myData[i].path === '/photos/') {
         this.photoData = myData[i]
         this.existPhotos = true
       }
     }
   },
   mounted() {
     if (this.existPhotos) {
       new Promise((resolve,reject) => {
         setTimeout(() => {
           document.querySelector(".photo-html").innerHTML = this.photoData.contentRendered
           resolve()
         },500)

       }).then(() => {
         let allPhotos = document.querySelectorAll(".photo-html img");
         for (let i = 0; i < allPhotos.length; i++) {
           this.imgList.push(allPhotos[i].src)
         }
         this.imgPreloading()
         $(".loadingAnimate").fadeOut(400)
       })
     }else {
       this.imgList = [
           'https://ooszy.cco.vin/img/blog-note/image-20210910160320304.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160335602.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160455739.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160510785.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160523968.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160541591.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160601558.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160650553.png?x-oss-process=style/pictureProcess1',
           'https://ooszy.cco.vin/img/blog-note/image-20210910160702449.png?x-oss-process=style/pictureProcess1'
       ]
       this.imgPreloading()
       $(".loadingAnimate").fadeOut(400)
     }

     setTimeout(() => {
       this.clientWidth = document.body.offsetWidth
       this.height = document.body.offsetHeight
       this.clientWidth = this.clientWidth  * 0.97

       if (this.clientWidth < 550) {
         this.waterfallImgCol = 2
         this.waterfallImgWidth = (this.clientWidth - this.waterfallImgRight * 2) / 2
         this.start()
       }else {
         this.waterfallImgWidth =(this.clientWidth - this.waterfallImgRight * 5)  / this.waterfallImgCol
         this.start()
       }
     },200)
     setTimeout(() => {
       this.mountedStatus = true
     },50)
   },
   methods: {
     imgEnter(e) {

     },
     imgLeave(e) {
     },
     openImg(e) {
       const zoom = mediumZoom(e.target)
       zoom.open()
     },
     start() {
       setTimeout(() => {
         this.calculationWidth();
       },50)
     },
     //计算每个图片的宽度或者是列数
     calculationWidth() {
       let domWidth = document.getElementById("v-waterfall").offsetWidth;
       if (!this.waterfallImgWidth && this.waterfallImgCol) {
         //this.waterfallImgWidth每个盒子的宽度
         this.waterfallImgWidth = (domWidth - this.waterfallImgRight * this.waterfallImgCol * 2) / this.waterfallImgCol;
       } else if (this.waterfallImgWidth && !this.waterfallImgCol) {
         this.waterfallImgCol = Math.floor(domWidth / (this.waterfallImgWidth + this.waterfallImgRight))
       }
       //初始化偏移高度数组
       this.waterfallDeviationHeight = new Array(this.waterfallImgCol);
       for (let i = 0; i < this.waterfallDeviationHeight.length; i++) {
         this.waterfallDeviationHeight[i] = 0;
       }

       this.imgPreloading()
     },
     //图片预加载
     imgPreloading() {
       for (let i = 0; i < this.imgList.length; i++) {
         let aImg = new Image();
         aImg.src = this.imgList[i];
         aImg.onload = aImg.onerror = (e) => {
           let imgData = {};
           imgData.height = this.waterfallImgWidth / aImg.width * aImg.height;
           imgData.originHeight = this.waterfallImgWidth / aImg.width * aImg.height;
           imgData.src = this.imgList[i];
           imgData.title = '...';
           imgData.info = '...';
           this.waterfallList.push(imgData);
           this.rankImg(imgData);
         }
       }
     },
     //瀑布流布局
     rankImg(imgData) {
       let {
         waterfallImgWidth,
         waterfallImgRight,
         waterfallImgBottom,
         waterfallDeviationHeight,
         waterfallImgCol
       } = this;
       let minIndex = this.filterMin();

       //imgData.top = top === 0 ? 0 : top - 40;
       imgData.top = waterfallDeviationHeight[minIndex];
       //console.log("top:" + top)
       imgData.left = minIndex * (waterfallImgRight + waterfallImgWidth);
       // waterfallDeviationHeight[minIndex] += imgData.height + waterfallImgBottom;// 不加文字的盒子高度
       waterfallDeviationHeight[minIndex] += imgData.height + waterfallImgBottom + 56;// 加了文字的盒子高度，留出文字的地方（这里设置56px）
     },
     filterMin() {
       const min = Math.min.apply(null, this.waterfallDeviationHeight);
       return this.waterfallDeviationHeight.indexOf(min);
     }
   },
   computed: {
     getStyle() {
       return (img) => {
         let interval = (this.clientWidth - this.waterfallImgWidth * this.waterfallImgCol) /2
         return {top:img.top + 20 +'px',left:img.left + interval +'px',width:this.waterfallImgWidth+'px',height:img.height}
       }
     },
     getDescStyle() {
       return (img) => {
         let interval = (this.clientWidth - this.waterfallImgWidth * this.waterfallImgCol) /2
         return {top:img.top + 20 +'px',left:img.left + interval +'px',width:this.waterfallImgWidth+'px',height:img.height}
       }
     },
   }
 }
 </script>

 <style scoped>
 </style>