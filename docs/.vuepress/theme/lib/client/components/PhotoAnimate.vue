<template>
  <div id="photoApp" :class="loadingClass">
    <photo/>
    <photo-animate-item style="height: calc(100vh)" :element-id="id" :show-canvas="showCanvas"
                         :animation-solution="animationSolution"
                         :grid-max-width="gridMaxWidth" :grid-max-height="gridMaxHeight"
                         :grid-divider-width="gridDividerWidth" :grid-divider-color="gridDividerColor"
                         :slide-wait-time="slideWaitTime"
                         :use-animate="useAnimate" :animate-speed="animateSpeed"
                         :animate-speed-delay="animateSpeedDelay"
                         :animate-item-direction="animateItemDirection"
                         :animate-row-direction="animateRowDirection"
                         :animate-column-direction="animateColumnDirection"
                         :animate-show-order="animateShowOrder"
                         :animate-effect="animateEffect"
                         :canvas-animate-easing="canvasAnimateEasing"
                         :css3-animate-easing="css3AnimateEasing"
                         :image-list="imageList" :use-un-splash="useUnSplash" :un-splash-tag="unSplashTag"
                         :init-load-finish-callback="initLoadFinishCallback"
                         :photo-load-success-callback="photoLoadSuccessCallback"
                         :animate-begin-callback="animateBeginCallback"
                         :animate-end-callback="animateEndCallback"
    ></photo-animate-item>
  </div>
</template>

<script>
import $ from 'jquery'
import PhotoAnimateItem from "./child/PhotoAnimateItem";
export default {
  name: 'app',
  components: {
    PhotoAnimateItem
  },
  data() {
    return {
      id: 'gallery',
      animationSolution: 'byCanvas',
      showCanvas: true,
      gridMaxWidth: 150,
      gridMaxHeight: 100,
      gridDividerWidth: 1,
      gridDividerColor: '#fff',
      slideWaitTime: 4000,
      useAnimate: true,
      animateSpeed: 150,
      animateSpeedDelay: 10,
      animateItemDirection: 'snake',
      animateRowDirection: 'left',
      animateColumnDirection: 'top',
      animateShowOrder: 'singleItem',
      animateEffect: 'opacity',
      canvasAnimateEasing: 'SinusoidalInOut',
      css3AnimateEasing: 'ease',
      imageList: [],
      useUnSplash: true,
      unSplashTag: 'japan',
      currentImageObject: null,
      initLoadFinishCallback: () => {
        $(".loadingAnimate").fadeOut(400)

        this.loadingClass = 'loaded'
        if (this.isIE()) {
          return
        }
      },
      photoLoadSuccessCallback: (e) => {
        this.currentImageObject = e
      },
      animateBeginCallback: () => {
      },
      animateEndCallback: () => {
      },
      // Index Setting
      loadingClass: ''
    }
  },
  methods: {
    isIE () {
      return !!window.ActiveXObject || 'ActiveXObject' in window || (/Trident\/7\./).test(navigator.userAgent)
    }
  }
}
</script>
