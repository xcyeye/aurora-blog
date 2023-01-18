 <template>
   <div class="photo-box">
		 <div :style="getLoadingAnimate" :class="showBgColor ? 'loadingAnimateColor' : ''" class="photo-loading-box loadingAnimate">
			 <div class="loader">
				 <div class="inner one"></div>
				 <div class="inner two"></div>
				 <div class="inner three"></div>
			 </div>
		 </div>
		 <div class="photo-bg" :class="showBgColor ? 'show-bg-color' : ''"/>
		 <n-scrollbar>
			 <div class="photo-waterfull">
				 <div class="waterfull">
					 <div class="v-waterfall-content" id="v-waterfall" :style="`margin-top: ${imageIntervalWidth / 2}px`">
						 <div v-for="(img, index) in waterfallList" :key="img.src"
									class="v-waterfall-item hover-fall hover-div"
									:style="getStyle(img)">
							 <n-image @click="handleClickImage(img.src)" id="v-waterfall-item-img" style="opacity: 1;" class="hover-img hover-div medium-zoom-image" :src="img.src"/>
							 <!--<img style="opacity: 1" @mouseenter="imgEnter" @mouseleave="imgLeave" @click="openImg" class="medium-zoom-image hover-img hover-div" :src="img.src" alt="">-->
						 </div>
					 </div>
				 </div>
			 </div>
		 </n-scrollbar>
	 </div>
 </template>

 <script lang="ts">
 import {PropType} from "vue";
 
 interface ImageInfo {
	 height: number,
	 originHeight: number,
	 src: string,
	 title: string,
	 info: string,
	 originWidth: number,
	 width: number,
	 top?: number,
	 left?: number,
	 right?: number,
	 bottom?: number
 }
 
 const waterfallList: Array<ImageInfo> = []

 export default {
   name: "v-waterfall",
   data() {
     return {
			 waterfallImgCol: 5,
       waterfallList,
       // waterfallImgWidth: 100,
       waterfallImgWidth: 200,// 每个盒子的宽度
       waterfallImgRight: 0,// 每个盒子的右padding
       waterfallImgBottom: -45,// 每个盒子的下padding
       waterfallDeviationHeight: [],
       clientWidth: 500,
       height: 500,
       loadingAnimateSuccess: false,
			 imageIntervalWidth: 10
     }
   },
	 props: {
		 pictureSrcList: {
			 type: Object as PropType<Array<string>>
		 },
		 showBgColor: {
			 type: Boolean,
			 default() {
				 return true
			 }
		 },
		 pcWaterfallImgCol: {
			 type: Number,
			 default() {
				 return 5
			 }
		 },
		 mobileWaterfallImgCol: {
			 type: Number,
			 default() {
				 return 2
			 }
		 },
		 topHeight: {
			 type: Number,
			 default() {
				 return 0
			 }
		 },
		 leftWidth: {
			 type: Number,
			 default() {
				 return 0
			 }
		 }
	 },
	 emits: ['handleClickImage'],
	 mounted() {
     //$(".loadingAnimate").fadeOut(400)
		 this.setWaterfallCol()
		 
		 window.addEventListener('resize', this.setWaterfallCol)
   },
   methods: {
		 setWaterfallCol() {
			 this.waterfallList = []
			 const waterfallTime = setInterval(() => {
				 if (document.getElementById("v-waterfall")) {
					 clearInterval(waterfallTime)
					 this.clientWidth = document.getElementById("v-waterfall")!.offsetWidth
					 this.height = document.body.offsetHeight
					 //this.clientWidth = this.clientWidth  * 0.97
					 if (this.clientWidth < 550) {
						 this.imageIntervalWidth = 10
						 this.waterfallImgCol = this.mobileWaterfallImgCol
					 }else {
						 this.imageIntervalWidth = 15
						 this.waterfallImgCol = this.pcWaterfallImgCol
					 }
					 this.waterfallImgWidth =(this.clientWidth - this.waterfallImgRight * this.waterfallImgCol)  / this.waterfallImgCol
					 this.setLoadAnimate()
				 }
			 }, 10)
		 },
		 handleClickImage(pictureSrc: string) {
			 this.$emit("handleClickImage",{
				 photoUrl: pictureSrc
			 })
		 },
		 setLoadAnimate() {
			 // 最多加载15秒，如果15秒都还没有传入图片数组，则直接不显示500 * 2 * 15
			 this.calculationWidth();
			 if (!this.pictureSrcList || this.pictureSrcList.length === 0) {
				 let timeCount = 1
				 const time = setInterval(() => {
					 if (this.pictureSrcList && this.pictureSrcList.length > 0) {
						 clearInterval(time)
						 this.loadingAnimateSuccess = true
						 this.loadPictureInfo()
					 }
					 timeCount++
					 if (timeCount === 30) {
						 clearInterval(time)
						 this.loadingAnimateSuccess = true
						 this.loadPictureInfo()
					 }
				 }, 500)
			 }else {
				 this.loadPictureInfo()
			 }
		 },
		 loadPictureInfo() {
			 if (this.pictureSrcList?.length === 0) {
				 this.loadingAnimateSuccess = true
				 return
			 }
			 this.loadingAnimateSuccess = true
			 this.imgPreloading()
		 },
     //计算每个图片的宽度或者是列数
     calculationWidth() {
       let domWidth = document.getElementById("v-waterfall")!.offsetWidth;
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
     },
     //图片预加载
     imgPreloading() {
       for (let i = 0; i < this.pictureSrcList.length; i++) {
         let aImg = new Image();
         aImg.src = this.pictureSrcList[i];
         aImg.onload = aImg.onerror = (e) => {
           let imgData: ImageInfo = {
						 height: this.waterfallImgWidth / aImg.width * aImg.height,
						 originHeight: this.waterfallImgWidth / aImg.width * aImg.height,
						 src: this.pictureSrcList[i],
						 title: '...',
						 info: '...',
						 originWidth: aImg.width,
						 width: aImg.width
					 };
           this.rankImg(imgData);
         }
       }
     },
     //瀑布流布局
     rankImg(imgData: ImageInfo) {
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
			 waterfallDeviationHeight[minIndex] += imgData.height;// 加了文字的盒子高度，留出文字的地方（这里设置56px)
       this.waterfallList.push(imgData);
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
       return (img: ImageInfo) => {
         let interval = (this.clientWidth - this.waterfallImgWidth * this.waterfallImgCol) /2
				 // return {top:img.top! + 20 +'px', left:img.left! + interval + 10 +'px', width: this.waterfallImgWidth + 'px', height:img.height}
				 return {top:img.top! +'px', left:img.left! + (this.imageIntervalWidth / 2) + interval +'px', width: this.waterfallImgWidth - this.imageIntervalWidth + 'px', height:img.height}
				 // return {top:img.top! + this.topHeight +'px', left:img.left! + this.leftWidth +'px', width: this.waterfallImgWidth + 'px', height:img.height}
       }
     }
   }
 }
 </script>
