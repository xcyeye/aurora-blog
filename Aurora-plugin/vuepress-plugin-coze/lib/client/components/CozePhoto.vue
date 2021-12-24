 <template>
   <div class="photo">
     <div class="photo-center">
       <div id="photo-li">
       </div>
     </div>
     <div :style="getLoadingAnimate" class="loadingAnimate">
       <div class="loader">
         <div class="inner one"></div>
         <div class="inner two"></div>
         <div class="inner three"></div>
       </div>
     </div>
   </div>
   <div class="photo-bg"></div>
   <div class="photo-waterfull">
     <div class="waterfull">
       <div class="v-waterfall-content" id="v-waterfall">
         <div :data="waterfallList.length" v-for="(img, index) in waterfallList" :key="img.src"
              class="v-waterfall-item hover-fall hover-div"
              :style="getStyle(img)">
           <img style="opacity: 1" @mouseenter="imgEnter" @mouseleave="imgLeave" @click="openImg" class="medium-zoom-image hover-img hover-div" :src="img.src" alt="">
         </div>
       </div>
     </div>
   </div>
 </template>

 <script>
 import mediumZoom from "medium-zoom";
 import { Query } from 'leancloud-storage'

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
       existPhotos: false,
       loadingAnimateSuccess: false,
       isMounted: false,
       noShowNum: 0,
       showNum: 0,
       loadingSingle: false,
       photoLength: 0,
       isImgPreloading: false
     }
   },
   created() {
     //从leanCloud获取所有的数据
     const query = new Query('Talk');
     query.find().then((talks) => {
       this.photoLength = talks.length
       if (talks.length === 0) {
         this.loadingAnimateSuccess = true
       }
       for (let i = 0; i < talks.length; i++) {
         if (talks[i].attributes.mood_photos.length === 0) {
           if (i === talks.length -1) {
             this.loadingAnimateSuccess = true
           }
         }
         for (let j = 0; j < talks[i].attributes.mood_photos.length; j++) {
           //判断是否显示
           if (!talks[i].attributes.mood_show) {
             continue
             if (i === talks.length -1) {
               this.loadingAnimateSuccess = true
             }
           }

           this.imgList.push(talks[i].attributes.mood_photos[j].photoUrl)
           if (i === talks.length -1 && j === talks[i].attributes.mood_photos.length -1) {
             this.loadingAnimateSuccess = true
           }
         }
       }
     })
   },
   watch: {
     loadingAnimateSuccess() {
     },
     isMounted() {
       let loadImgs = setInterval(() => {
         if (this.loadingAnimateSuccess) {
           clearInterval(loadImgs)
           this.imgPreloading()
         }
       },50)
     }
   },
   mounted() {
     //$(".loadingAnimate").fadeOut(400)
     setTimeout(() => {
       this.clientWidth = document.body.offsetWidth
       this.height = document.body.offsetHeight
       //this.clientWidth = this.clientWidth  * 0.97

       if (this.clientWidth < 550) {
         this.waterfallImgCol = 2
         this.waterfallImgWidth = (this.clientWidth - this.waterfallImgRight * 2) / 2
         this.calculationWidth();
       }else {
         this.waterfallImgWidth =(this.clientWidth - this.waterfallImgRight * 5)  / this.waterfallImgCol
         this.calculationWidth();
       }
       this.isMounted = true
     },200)
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
       if (this.loadingAnimateSuccess) {
         this.imgPreloading()
       }
     },
     //图片预加载
     imgPreloading() {
       this.isImgPreloading = true
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
           imgData.originWidth = aImg.width
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
       imgData.left = minIndex * (waterfallImgRight + waterfallImgWidth);
       // waterfallDeviationHeight[minIndex] += imgData.height + waterfallImgBottom;// 不加文字的盒子高度
       waterfallDeviationHeight[minIndex] += imgData.height + waterfallImgBottom + 56;// 加了文字的盒子高度，留出文字的地方（这里设置56px)
       this.waterfallList.push(imgData);
       //console.log(1)
     },
     filterMin() {
       const min = Math.min.apply(null, this.waterfallDeviationHeight);
       return this.waterfallDeviationHeight.indexOf(min);
     }
   },
   computed: {
     getLoadingAnimate() {
       if (this.loadingAnimateSuccess) {
         return "display: none;"
       }
     },
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
