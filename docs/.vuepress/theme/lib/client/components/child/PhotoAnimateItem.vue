<template>
  <div>
    <div style="height: calc(100vh)" ref="fuckingDiv" :id="elementId" class="vue-fucking-gallery-container">
      <div :data="getCur" class='vue-fucking-gallery-div' v-show="currentImageObject!==null && showCanvas">
        <div v-show="animationSolution==='byCanvas'">
          <canvas ref="fuckingByCanvas"></canvas>
        </div>
        <ul ref="fuckingByCss3" v-show="animationSolution==='byCss3'"></ul>
      </div>
    </div>
  </div>
</template>

<script>
import ZRender from 'zrender'

export default {
  name: 'PhotoAnimateItem',
  $el: null,
  data () {
    return {
      drawImageObjectCanvas: null,
      rootZRender: null,
      css3Area: null,
      css3GridParent: null,
      css3SlideGridList: null,
      css3SlideGridAList: null,
      css3SlideGridBList: null,
      zRenderStaticImageGroup: null,
      zRenderAnimationImageGroup: null,
      currentImageObject: null,
      nextImageObject: null,
      unSplashUrl: null,
      useUnSplashService: false,
      rowCount: 1,
      columnCount: 1,
      gridItemCount: 1,
      normalRowItemWidth: null,
      lastRowItemWidth: null,
      normalColumnItemHeight: null,
      lastColumnItemHeight: null,
      excludeDividerElementWidth: null,
      excludeDividerElementHeight: null,
      activeImageListIndex: 0,
      nextImageStartLoadTime: 0,
      loadErrorTimes: 0,
      animationRunning: false,
      isLoadingImage: false,
      windowResizeSetTimer: null,
      waitAnimationFinishTimer: null,
      snakeSort: null
    }
  },
  props: {
    elementId: {
      type: String,
      default: 'vue-fucking-gallery'
    },
    animationSolution: {
      type: String,
      default: 'byCanvas',
      validator: function (value) {
        // You should use one of below solution.
        return ['byCanvas', 'byCss3'].indexOf(value) !== -1
      }
    },
    showCanvas: {
      type: Boolean,
      default: true
    },
    gridMaxWidth: {
      type: Number,
      default: 200,
      validator: function (value) { // As for performance, this value should not be less than 48
        return value >= 48 && Number.isInteger(value)
      }
    },
    gridMaxHeight: {
      type: Number,
      default: 200,
      validator: function (value) { // As for performance, this value should not be less than 48
        return value >= 48 && Number.isInteger(value)
      }
    },
    gridDividerWidth: {
      type: Number,
      default: 1,
      validator: function (value) { // I think it is enough as a max value of 100, most people use 1 or 0 for this
        return value >= 0 && value <= 100 && Number.isInteger(value)
      }
    },
    gridDividerColor: {
      type: String,
      default: '#fff',
      validator: function (value) {
        // No alpha
        return /^#?([a-fA-F0-9]{3}|[a-fA-F0-9]{6})$/.test(value)
      }
    },
    slideWaitTime: {
      type: Number,
      default: 5000,
      validator: function (value) {
        // Do not be less than 1000, the slide wait time not only depends on this, also load next page time
        return value >= 1000 && Number.isInteger(value)
      }
    },
    useAnimate: {
      type: Boolean,
      default: true
    },
    animateSpeed: {
      type: Number,
      default: 150,
      validator: function (value) {
        // Do not be less than 100
        return value >= 100 && Number.isInteger(value)
      }
    },
    animateSpeedDelay: {
      type: Number,
      default: 10,
      validator: function (value) {
        // Do not be less than 5
        return value >= 5 && Number.isInteger(value)
      }
    },
    animateItemDirection: {
      type: String,
      default: 'left',
      validator: function (value) {
        // You should use one of below direction, it sets every grid item's animation direction,
        // use none will force set animateEffect to opacity,
        // use random will force set animateShowOrder to random,
        // if set snake, first start item will set to left top 0, ignore animateRowDirection and animateColumnDirection,
        // and animateShowOrder will force set to singleItem
        return ['left', 'top', 'right', 'bottom', 'random', 'none', 'snake'].indexOf(value) !== -1
      }
    },
    animateRowDirection: {
      type: String,
      default: 'left',
      validator: function (value) {
        // You should use one of below direction, it sets show direction of item in row,
        // set left and row item will show from left to right,
        // set random will auto choose one in left and right.
        return ['left', 'right', 'random'].indexOf(value) !== -1
      }
    },
    animateColumnDirection: {
      type: String,
      default: 'top',
      validator: function (value) {
        // You should use one of below direction, it sets show direction of row in column,
        // set bottom and last row will show at first,
        // set random will auto choose one in top and bottom.
        return ['top', 'bottom', 'random'].indexOf(value) !== -1
      }
    },
    animateShowOrder: {
      type: String,
      default: 'singleItem',
      validator: function (value) {
        // You should use one of below order type, it sets show all line or single item at the same time,
        // set singleItem and next item show after last item show,
        // set multiLine and all item in same row or column show at the same time,
        // if animateItemDirection sets left or right, then animateRowDirection will use and set left or right,
        // if animateItemDirection sets top or bottom, then animateColumnDirection will use and set top or bottom,
        // set random will auto set every item show time in random,
        // and ignore animateRowDirection and animateColumnDirection.
        return ['singleItem', 'multiLine', 'random'].indexOf(value) !== -1
      }
    },
    animateEffect: {
      type: String,
      default: 'opacity',
      validator: function (value) {
        // You should use one of below effect
        return ['opacity', 'none', 'sameRandom', 'eachRandom'].indexOf(value) !== -1
      }
    },
    canvasAnimateEasing: {
      type: String,
      default: 'SinusoidalInOut',
      validator: function (value) {
        // You should use one of below easing, see http://echarts.baidu.com/gallery/editor.html?c=line-easing for example,
        // use SameRandom will choose one from other easing, and all grid item will use it at this animation activity,
        // use EachRandom will choose one from other easing, and every grid item has its own easing.
        return ['Linear', 'QuadraticIn', 'QuadraticOut', 'QuadraticInOut',
          'CubicIn', 'CubicOut', 'CubicInOut', 'QuarticIn', 'QuarticOut',
          'QuarticInOut', 'QuinticIn', 'QuinticOut', 'QuinticInOut', 'SinusoidalIn',
          'SinusoidalOut', 'SinusoidalInOut', 'ExponentialIn', 'ExponentialOut',
          'ExponentialInOut', 'CircularIn', 'CircularOut', 'CircularInOut',
          'ElasticIn', 'ElasticOut', 'ElasticInOut', 'BackIn', 'BackOut',
          'BackInOut', 'BounceIn', 'BounceOut', 'BounceInOut', 'sameRandom', 'eachRandom'].indexOf(value) !== -1
      }
    },
    css3AnimateEasing: {
      type: String,
      default: 'ease',
      validator: function (value) {
        // You should use one of below easing, cubic-bezier support
        return ['linear', 'ease', 'ease-in', 'ease-out', 'ease-in-out', 'sameRandom', 'eachRandom'].indexOf(value) !== -1 ||
            /^cubic-bezier\((1|(0\.\d+)),(1|(0\.\d+)),(1|(0\.\d+)),(1|(0\.\d+))\)$/.test(value)
      }
    },
    imageList: {
      type: Array,
      default: () => {
        return []
      }
    },
    useUnSplash: {
      type: Boolean,
      default: false
    },
    unSplashTag: {
      type: String,
      default: 'japan'
    },
    initLoadFinishCallback: Function, // Only call at the first image loaded.
    photoLoadSuccessCallback: Function, // Call every time load success, include init load.
    animateBeginCallback: Function, // Call at the begin of animation.
    animateEndCallback: Function // Call at the end of animation.
  },
  watch: {
    elementId (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.$el = this.$refs.fuckingDiv
    },
    animationSolution (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      if (newVal === 'byCanvas') {
        this.clearCss3()
      } else {
        this.clearCanvas()
      }
      this.resizeElement(false, true)
    },
    showCanvas (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      if (newVal === true) {
        this.start()
        return
      }
      this.clearCanvas()
      this.clearCss3()
    },
    gridMaxWidth (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.resizeElement(false, true)
    },
    gridMaxHeight (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.resizeElement(false, true)
    },
    gridDividerWidth (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.resizeElement(false, true)
    },
    gridDividerColor (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.redrawDividerColor()
    },
    imageList (newVal, oldVal) {
      if (newVal.length === oldVal.length && newVal.every(a => oldVal.some(b => a === b)) && oldVal.every(_b => newVal.some(_a => _a === _b))) {
        return
      }
      this.initImageSource()
    },
    useUnSplash (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.initImageSource()
    },
    unSplashTag (newVal, oldVal) {
      if (newVal === oldVal) {
        return
      }
      this.initImageSource()
    }
  },
  mounted () {
    this.$el = this.$refs.fuckingDiv
    this.rootZRender = ZRender.init(this.$refs.fuckingByCanvas)
    this.css3Area = this.$refs.fuckingByCss3
    this.drawImageObjectCanvas = ZRender.util.createCanvas()
    this.redrawDividerColor()
    this.initImageSource()
    this.resizeElement(false, false)
    if (this.showCanvas) {
      this.start()
    }
    window.onresize = () => {
      if (this.windowResizeSetTimer !== null) {
        clearTimeout(this.windowResizeSetTimer)
      }
      this.windowResizeSetTimer = setTimeout(() => {
        this.resizeElement(true, true)
        this.windowResizeSetTimer = null
      }, 20)
    }
  },
  methods: {
    start () {
      this.loadImage()
    },
    clearCanvas () {
      if (this.zRenderStaticImageGroup !== null) {
        this.rootZRender.remove(this.zRenderStaticImageGroup)
        this.zRenderStaticImageGroup = null
      }
      if (this.zRenderAnimationImageGroup !== null) {
        this.rootZRender.remove(this.zRenderAnimationImageGroup)
        this.zRenderAnimationImageGroup = null
      }
      this.animationRunning = false
      if (this.windowResizeSetTimer !== null) {
        clearTimeout(this.windowResizeSetTimer)
      }
      if (this.waitAnimationFinishTimer !== null) {
        clearTimeout(this.waitAnimationFinishTimer)
      }
    },
    clearCss3 () {
      this.css3Area.innerHTML = ''
      if (this.css3GridParent !== null) {
        if (this.isIE()) {
          this.css3GridParent.removeNode(true)
        } else {
          this.css3GridParent.remove()
        }
      }
      this.css3SlideGridList = null
      this.css3SlideGridAList = null
      this.css3SlideGridBList = null
    },
    reset () {
      this.clearCanvas()
      this.clearCss3()
      this.currentImageObject = null
      this.nextImageObject = null
    },
    initImageSource () {
      this.activeImageListIndex = 0
      let url = 'https://source.unsplash.com/random/'
      url += this.$el.offsetWidth
      url += 'x'
      url += this.$el.offsetHeight
      url += '?'
      if (this.unSplashTag !== null || this.unSplashTag.trim().length > 0) {
        url += this.unSplashTag.trim()
      }
      this.unSplashUrl = url
      //console.log("-----------url---------------")
      //console.log(url)
      this.useUnSplashService = this.useUnSplash === true || (this.imageList === null || this.imageList.length < 1)
    },
    loadImage (notResetStartTime) {
      if (this.isLoadingImage === true) {
        return
      }
      this.isLoadingImage = true
      if (notResetStartTime !== true) {
        this.nextImageStartLoadTime = new Date().getTime()
      }
      let imageObj = new Image()
      imageObj.setAttribute('crossOrigin', 'Anonymous')
      imageObj.src = this.getImageUrl()
      if (imageObj.complete) {
        this.imageLoaded(imageObj)
        return
      }
      imageObj.onload = () => {
        this.imageLoaded(imageObj)
      }
      imageObj.onerror = () => {
        this.loadErrorTimes++
        if (this.useUnSplashService === false && this.loadErrorTimes > this.imageList.length) {
          // All images are error, stop loading
          this.isLoadingImage = false
          return
        }
        this.setActiveImageListToNext()
        this.loadImage(true)
      }
    },
    imageLoaded (imageObj) {
      //console.log("--------image------对象-----------")
      //console.log(imageObj.src)
      this.isLoadingImage = false
      if (!this.showCanvas) {
        return
      }
      let firstTime = this.currentImageObject === null
      if (firstTime) {
        if (this.initLoadFinishCallback !== null && (typeof this.initLoadFinishCallback) !== 'undefined') {
          // Do callback
          this.initLoadFinishCallback()
        }
        this.currentImageObject = imageObj
      } else {
        // check if image are same
        let currentImageToDataURL = this.getImageObjectCanvas(this.currentImageObject,
            {width: this.currentImageObject.width, height: this.currentImageObject.height}).toDataURL()
        let nextImageObjectToDataURL = this.getImageObjectCanvas(imageObj,
            {width: imageObj.width, height: imageObj.height}).toDataURL()
        if (currentImageToDataURL === nextImageObjectToDataURL) {
          // loaded the same image, often happened in unsplash service
          this.setActiveImageListToNext()
          this.loadImage(true)
          return
        }
        this.nextImageObject = imageObj
      }
      if (this.photoLoadSuccessCallback !== null && (typeof this.photoLoadSuccessCallback) !== 'undefined') {
        // Do callback
        this.photoLoadSuccessCallback(this.currentImageObject)
      }
      this.loadErrorTimes = 0
      let timeDiff = new Date().getTime() - this.nextImageStartLoadTime
      if (firstTime) {
        this.redraw()
        this.setActiveImageListToNext()
        this.loadImage()
        return
      }
      if (timeDiff >= this.slideWaitTime) {
        this.setActiveImageListToNext()
        this.startAnimation()
        return
      }
      setTimeout(() => {
        this.setActiveImageListToNext()
        this.startAnimation()
      }, this.slideWaitTime - timeDiff)
    },
    resizeElement (windowResize, needRedraw) {
      this.rowCount = Math.floor((this.$el.offsetWidth + this.gridDividerWidth) / (this.gridMaxWidth + this.gridDividerWidth))
      this.columnCount = Math.floor((this.$el.offsetHeight + this.gridDividerWidth) / (this.gridMaxHeight + this.gridDividerWidth))
      this.gridItemCount = this.rowCount * this.columnCount
      this.excludeDividerElementWidth = this.$el.offsetWidth - this.gridDividerWidth * (this.rowCount - 1)
      this.excludeDividerElementHeight = this.$el.offsetHeight - this.gridDividerWidth * (this.columnCount - 1)
      this.normalRowItemWidth = Math.floor(this.excludeDividerElementWidth / this.rowCount)
      this.normalColumnItemHeight = Math.floor(this.excludeDividerElementHeight / this.columnCount)
      this.lastRowItemWidth = this.excludeDividerElementWidth - (this.rowCount - 1) * this.normalRowItemWidth
      this.lastColumnItemHeight = this.excludeDividerElementHeight - (this.columnCount - 1) * this.normalColumnItemHeight
      this.rotationSnakeSort()
      if (this.animationSolution === 'byCanvas') {
        this.resizeCanvas(windowResize, needRedraw)
        return
      }
      this.resizeCss3(windowResize, needRedraw)
    },
    resizeCanvas (windowResize, needRedraw) {
      this.rootZRender.resize({
        width: this.$el.offsetWidth,
        height: this.$el.offsetHeight
      })
      if (windowResize || needRedraw) {
        this.redraw(windowResize === true)
      }
    },
    resizeCss3 (windowResize, needRedraw) {
      this.clearCss3()
      let gridHtml = ''
      let htmlTemp = {
        parentTemp: '<li class="slider-parent"></li>',
        gridTemp: '<div class="slider-grid">' +
            '<div class="slider-grid-a"></div>' +
            '<div class="slider-grid-b"></div>' +
            '</div>'
      }
      this.css3Area.innerHTML = htmlTemp.parentTemp
      this.css3GridParent = this.css3Area.querySelector('.slider-parent')
      for (let i = 0; i < this.gridItemCount; i++) {
        gridHtml += htmlTemp.gridTemp
      }
      this.css3GridParent.innerHTML = gridHtml
      this.css3SlideGridList = this.css3GridParent.querySelectorAll('.slider-grid')
      this.css3SlideGridAList = this.css3GridParent.querySelectorAll('.slider-grid-a')
      this.css3SlideGridBList = this.css3GridParent.querySelectorAll('.slider-grid-b')
      ZRender.util.each(this.css3SlideGridList, (gridItem, index) => {
        let top = Math.floor(index / this.rowCount) % this.columnCount * (this.normalColumnItemHeight + this.gridDividerWidth)
        let left = index % this.rowCount * (this.normalRowItemWidth + this.gridDividerWidth)
        let gridItemWidth = (index % this.rowCount < this.rowCount - 1) ? this.normalRowItemWidth : this.lastRowItemWidth
        let gridItemHeight = Math.floor(index / this.rowCount) < (this.columnCount - 1) ? this.normalColumnItemHeight : this.lastColumnItemHeight
        gridItem.style['left'] = left + 'px'
        gridItem.style['top'] = top + 'px'
        gridItem.style['width'] = gridItemWidth + 'px'
        gridItem.style['height'] = gridItemHeight + 'px'
      })
      if (windowResize || needRedraw) {
        this.redraw(windowResize === true)
      }
    },
    redraw (forceRedraw) {
      // If set force redraw, then any animation will stop and redraw the new image.
      if (!this.showCanvas) {
        return
      }
      if (this.currentImageObject === null) {
        this.loadImage()
        return
      }
      this.redrawDividerColor()
      if (this.animationSolution === 'byCanvas') {
        this.redrawCanvas(forceRedraw)
        return
      }
      this.redrawCss3(forceRedraw)
    },
    redrawCanvas (forceRedraw) {
      if (this.animationRunning) {
        if (forceRedraw !== true) {
          return
        }
        // Clear animation and its finish timer and do callback, then redraw.
        if (this.waitAnimationFinishTimer !== null) {
          clearTimeout(this.waitAnimationFinishTimer)
        }
        if (this.nextImageObject !== null) {
          this.currentImageObject = this.nextImageObject
          this.nextImageObject = null
        }
        this.loadImage()
        if (this.zRenderAnimationImageGroup !== null) {
          this.rootZRender.remove(this.zRenderAnimationImageGroup)
          this.zRenderAnimationImageGroup = null
        }
        this.rootZRender.clearAnimation()
        if (this.animateEndCallback !== null && (typeof this.animateEndCallback) !== 'undefined') {
          this.animateEndCallback()
        }
      }
      this.animationRunning = false
      if (this.zRenderStaticImageGroup !== null) {
        this.rootZRender.remove(this.zRenderStaticImageGroup)
        this.zRenderStaticImageGroup = null
      }
      this.zRenderStaticImageGroup = new ZRender.Group()
      ZRender.util.each(this.getZRenderImageListForCanvas(this.currentImageObject), (image) => {
        this.zRenderStaticImageGroup.add(image)
      })
      this.rootZRender.add(this.zRenderStaticImageGroup)
      this.rootZRender.refreshImmediately()
    },
    redrawCss3 (forceRedraw) {
      if (this.animationRunning) {
        if (forceRedraw !== true) {
          return
        }
        // Clear animation and its finish timer and do callback, then redraw.
        if (this.waitAnimationFinishTimer !== null) {
          clearTimeout(this.waitAnimationFinishTimer)
        }
        if (this.nextImageObject !== null) {
          this.currentImageObject = this.nextImageObject
          this.nextImageObject = null
        }
        this.loadImage()
      }
      this.animationRunning = false
      this.resetGridB()
      let canvasList = this.getImageCanvasListForCss3(this.currentImageObject)
      ZRender.util.each(this.css3SlideGridAList, (gridItem, index) => {
        gridItem.style['background-image'] = 'url(' + canvasList[index] + ')'
        gridItem.style['background-repeat'] = 'no-repeat'
        gridItem.style['background-size'] = '100% 100%'
        gridItem.style['-moz-background-size'] = '100% 100%'
        gridItem.style['-webkit-background-size'] = '100% 100%'
        gridItem.style['-o-background-size'] = '100% 100%'
      })
    },
    resetGridB () {
      ZRender.util.each(this.css3SlideGridBList, (gridItem, index) => {
        gridItem.style['display'] = 'none'
        gridItem.style['background-image'] = ''
        gridItem.style['animation-name'] = ''
        gridItem.style['-moz-animation-name'] = ''
        gridItem.style['-webkit-animation-name'] = ''
        gridItem.style['-o-animation-name'] = ''
        gridItem.style['animation-duration'] = ''
        gridItem.style['-moz-animation-duration'] = ''
        gridItem.style['-webkit-animation-duration'] = ''
        gridItem.style['-o-animation-duration'] = ''
        gridItem.style['animation-delay'] = ''
        gridItem.style['-moz-animation-delay'] = ''
        gridItem.style['-webkit-animation-delay'] = ''
        gridItem.style['-o-animation-delay'] = ''
        gridItem.style['animation-iteration-count:'] = ''
        gridItem.style['-moz-animation-iteration-count:'] = ''
        gridItem.style['-webkit-animation-iteration-count:'] = ''
        gridItem.style['-o-animation-iteration-count:'] = ''
        gridItem.style['animation-fill-mode'] = ''
        gridItem.style['-moz-animation-fill-mode'] = ''
        gridItem.style['-webkit-animation-fill-mode'] = ''
        gridItem.style['-o-animation-fill-mode'] = ''
        gridItem.style['animation-timing-function'] = ''
        gridItem.style['-moz-animation-timing-function'] = ''
        gridItem.style['-webkit-animation-timing-function'] = ''
        gridItem.style['-o-animation-timing-function'] = ''
        gridItem.style['top'] = 0 + 'px'
        gridItem.style['left'] = 0 + 'px'
      })
    },
    redrawDividerColor () {
      if (this.rootZRender !== null) {
        this.rootZRender.dom.style.backgroundColor = this.gridDividerColor
      }
      if (this.css3Area !== null) {
        this.css3Area.style.backgroundColor = this.gridDividerColor
      }
    },
    startAnimation () {
      if (this.useAnimate !== true) {
        this.noUseAnimationFinished()
        return
      }
      this.animationRunning = true
      if (this.animateBeginCallback !== null && (typeof this.animateBeginCallback) !== 'undefined') {
        // Do callback
        this.animateBeginCallback()
      }
      let maxRunTime
      if (this.animationSolution === 'byCanvas') {
        maxRunTime = this.calcAnimationAndReturnMaxRunTimeByCanvas()
      } else {
        maxRunTime = this.calcAnimationAndReturnMaxRunTimeByCss3()
      }
      this.waitAnimationFinishTimer = setTimeout(() => {
        this.animationFinished()
      }, maxRunTime + 250)
    },
    animationFinished () {
      this.animationRunning = false
      this.currentImageObject = this.nextImageObject
      this.nextImageObject = null
      this.waitAnimationFinishTimer = null
      if (this.zRenderAnimationImageGroup !== null) {
        this.rootZRender.remove(this.zRenderAnimationImageGroup)
        this.zRenderAnimationImageGroup = null
      }
      if (this.animateEndCallback !== null && (typeof this.animateEndCallback) !== 'undefined') {
        this.animateEndCallback()
      }
      this.redraw()
      this.loadImage()
    },
    noUseAnimationFinished () {
      this.animationRunning = false
      this.currentImageObject = this.nextImageObject
      this.nextImageObject = null
      if (this.zRenderAnimationImageGroup !== null) {
        this.rootZRender.remove(this.zRenderAnimationImageGroup)
        this.zRenderAnimationImageGroup = null
      }
      this.redraw()
      this.loadImage()
    },
    getBaseAnimationConfig () {
      let resultConfig = {}
      resultConfig.animateItemDirection = this.animateItemDirection
      resultConfig.animateShowOrder = this.animateShowOrder
      if (resultConfig.animateItemDirection === 'random') {
        resultConfig.animateShowOrder = 'random'
      }
      if (resultConfig.animateItemDirection === 'snake') {
        resultConfig.animateShowOrder = 'singleItem'
      }

      let canvasAnimateEasingTemp = this.canvasAnimateEasing
      if (canvasAnimateEasingTemp === 'sameRandom') {
        canvasAnimateEasingTemp = this.getCanvasRandomEasing()
      }
      resultConfig.canvasAnimateEasing = canvasAnimateEasingTemp

      let css3AnimateEasingTemp = this.css3AnimateEasing
      if (css3AnimateEasingTemp === 'sameRandom') {
        css3AnimateEasingTemp = this.getCss3RandomEasing()
      }
      resultConfig.css3AnimateEasing = css3AnimateEasingTemp

      let animateRowDirectionTemp = this.animateRowDirection
      if (animateRowDirectionTemp === 'random') {
        animateRowDirectionTemp = ['left', 'right'][parseInt(Math.random() * 2, 10)]
      }
      resultConfig.animateRowDirection = animateRowDirectionTemp

      let animateColumnDirectionTemp = this.animateColumnDirection
      if (animateColumnDirectionTemp === 'random') {
        animateColumnDirectionTemp = ['top', 'bottom'][parseInt(Math.random() * 2, 10)]
      }
      resultConfig.animateColumnDirection = animateColumnDirectionTemp

      let animateEffectCanUseArray = []
      if (resultConfig.animateItemDirection === 'none') {
        animateEffectCanUseArray = ['opacity']
      } else {
        switch (this.animateEffect) {
          case 'none':
            animateEffectCanUseArray = ['none']
            break
          case 'sameRandom':
            animateEffectCanUseArray = [['opacity', 'none'][parseInt(Math.random() * 3, 10)]]
            break
          case 'eachRandom':
            animateEffectCanUseArray = ['opacity', 'none']
            break
          default:
            animateEffectCanUseArray = [this.animateEffect]
            break
        }
      }
      resultConfig.animateEffectCanUseArray = animateEffectCanUseArray
      return resultConfig
    },
    getItemSingleConfig (item, baseConfig, index) {
      let storeConfig = {}
      if (item !== null) {
        storeConfig = {x: item.style.x, y: item.style.y}
      }
      storeConfig.animateItemDirection = baseConfig.animateItemDirection
      storeConfig.animateRowDirection = baseConfig.animateRowDirection
      storeConfig.animateColumnDirection = baseConfig.animateColumnDirection
      if (storeConfig.animateItemDirection === 'random') {
        storeConfig.animateItemDirectionRunning = ['left', 'top', 'right', 'bottom'][parseInt(Math.random() * 4, 10)]
      } else if (storeConfig.animateItemDirection === 'snake') {
        let indexInRow = index % this.rowCount
        let indexInColumn = Math.floor(index / this.rowCount)
        if (indexInRow >= indexInColumn &&
            indexInRow <= (this.rowCount - (indexInColumn + 1)) &&
            indexInColumn <= (Math.floor(this.columnCount / 2) - (this.columnCount % 2 === 0 ? 1 : 0))) {
          storeConfig.animateItemDirectionRunning = 'left'
        } else if (indexInRow >= (this.columnCount - indexInColumn - 1) &&
            indexInRow < (this.rowCount - (this.columnCount - indexInColumn)) &&
            indexInColumn > (Math.floor(this.columnCount / 2) - (this.columnCount % 2 === 0 ? 1 : 0))) {
          storeConfig.animateItemDirectionRunning = 'right'
        } else if (indexInColumn > indexInRow &&
            indexInColumn < (this.columnCount - (indexInRow + 1)) &&
            indexInRow <= Math.ceil(this.rowCount / 2)) {
          storeConfig.animateItemDirectionRunning = 'bottom'
        } else {
          storeConfig.animateItemDirectionRunning = 'top'
        }
      } else {
        storeConfig.animateItemDirectionRunning = storeConfig.animateItemDirection
      }
      storeConfig.animateShowOrder = baseConfig.animateShowOrder
      storeConfig.animateEffect = baseConfig.animateEffectCanUseArray[parseInt(Math.random() * baseConfig.animateEffectCanUseArray.length, 10)]
      if (baseConfig.canvasAnimateEasing === 'eachRandom') {
        storeConfig.canvasAnimateEasing = this.getCanvasRandomEasing()
      }
      if (baseConfig.css3AnimateEasing === 'eachRandom') {
        storeConfig.css3AnimateEasing = this.getCss3RandomEasing()
      }
      storeConfig.runTime = Math.floor(this.animateSpeed * this.animateSpeedDelay)
      storeConfig.delayTime = this.calcDelayTime(baseConfig, index)
      return storeConfig
    },
    rotationSnakeSort () {
      let k = 1
      let result = []
      for (let i = 0; i < this.columnCount; i++) {
        result[i] = []
      }
      let small = Math.min(this.rowCount, this.columnCount)
      let count = Math.ceil(small / 2)
      for (let i = 0; i < count; i++) {
        let maxRight = this.rowCount - 1 - i
        let maxBottom = this.columnCount - 1 - i
        for (let j = i; j <= maxRight; j++) {
          result[i][j] = k++
        }
        for (let j = i; j < maxBottom; j++) {
          result[j + 1][maxRight] = k++
        }
        for (let j = maxRight - 1; j >= i; j--) {
          if (result[maxBottom][j]) {
            break
          }
          result[maxBottom][j] = k++
        }
        for (let j = maxBottom - 1; j > i; j--) {
          if (result[j][i]) break
          result[j][i] = k++
        }
      }
      this.snakeSort = result
    },
    calcDelayTime (config, index) {
      if (config.animateItemDirection === 'snake') {
        let newIndex = this.snakeSort[Math.floor(index / this.rowCount)][index % this.rowCount]
        return Math.ceil(this.animateSpeed * newIndex / (this.animateSpeedDelay * 0.2))
      }
      if (config.animateShowOrder === 'random') {
        return Math.ceil(this.animateSpeed * parseInt(Math.random() * this.animateSpeedDelay, 10))
      }
      if (config.animateShowOrder === 'multiLine') {
        switch (config.animateItemDirection) {
          case 'left':
            return Math.ceil(this.animateSpeed * (index % this.rowCount) / (this.animateSpeedDelay * 0.2))
          case 'right':
            return Math.ceil(this.animateSpeed * (this.rowCount - index % this.rowCount - 1) / (this.animateSpeedDelay * 0.2))
          case 'top':
            return Math.floor(this.animateSpeed * Math.floor(index / this.rowCount) / (this.animateSpeedDelay * 0.2))
          case 'bottom':
            return Math.floor(this.animateSpeed * (this.columnCount - Math.floor(index / this.rowCount) - 1) / (this.animateSpeedDelay * 0.2))
        }
        return Math.ceil(this.animateSpeed * parseInt(Math.random() * this.animateSpeedDelay, 10))
      }
      if (config.animateShowOrder === 'singleItem') {
        // From right to left
        if (config.animateRowDirection === 'right') {
          switch (config.animateColumnDirection) {
            case 'bottom':
              let currentLineDesc = this.columnCount - Math.floor(index / this.rowCount)
              let newIndexDesc = (this.rowCount - index % this.rowCount - 1) + this.rowCount * currentLineDesc
              return Math.ceil(this.animateSpeed * newIndexDesc / (this.animateSpeedDelay * 0.2))
            default:
              let currentLine = Math.floor(index / this.rowCount)
              let newIndex = (this.rowCount - index % this.rowCount - 1) + this.rowCount * currentLine
              return Math.ceil(this.animateSpeed * newIndex / (this.animateSpeedDelay * 0.2))
          }
        }
        // From left to right
        switch (config.animateColumnDirection) {
          case 'bottom':
            let currentLineDesc = this.columnCount - Math.floor(index / this.rowCount)
            let newIndexDesc = index % this.rowCount + this.rowCount * currentLineDesc
            return Math.ceil(this.animateSpeed * newIndexDesc / (this.animateSpeedDelay * 0.2))
          default:
            return Math.ceil(this.animateSpeed * index / (this.animateSpeedDelay * 0.2))
        }
      }
      return Math.ceil(this.animateSpeed * parseInt(Math.random() * this.animateSpeedDelay, 10))
    },
    calcAnimationAndReturnMaxRunTimeByCanvas () {
      if (this.zRenderAnimationImageGroup !== null) {
        this.rootZRender.remove(this.zRenderAnimationImageGroup)
        this.zRenderAnimationImageGroup = null
      }
      this.zRenderAnimationImageGroup = new ZRender.Group()
      let zRenderImageList = this.getZRenderImageListForCanvas(this.nextImageObject)
      let maxRunTime = 0
      let baseConfig = this.getBaseAnimationConfig()
      ZRender.util.each(zRenderImageList, (item, index) => {
        item.initConfigData = this.getItemSingleConfig(item, baseConfig, index)
        if (item.initConfigData.animateItemDirection !== 'none') {
          item.setClipPath(new ZRender.Rect({
            cursor: null,
            shape: {
              x: item.style.x,
              y: item.style.y,
              width: item.style.width,
              height: item.style.height
            }
          }))
        }
        switch (item.initConfigData.animateEffect) {
          case 'opacity':
            item.style.opacity = 0
            break
          default:
            break
        }
        switch (item.initConfigData.animateItemDirectionRunning) {
          case 'left':
            item.style.x = item.style.x - item.style.width
            break
          case 'top':
            item.style.y = item.style.y - item.style.height
            break
          case 'right':
            item.style.x = item.style.x + item.style.width
            break
          case 'bottom':
            item.style.y = item.style.y + item.style.height
            break
          default:
            break
        }
      })
      ZRender.util.each(zRenderImageList, (image) => {
        this.zRenderAnimationImageGroup.add(image)
      })
      this.rootZRender.add(this.zRenderAnimationImageGroup)
      ZRender.util.each(zRenderImageList, (item) => {
        if (item.initConfigData.runTime + item.initConfigData.delayTime > maxRunTime) {
          maxRunTime = item.initConfigData.runTime + item.initConfigData.delayTime
        }
        item.animateTo({
          style: {
            opacity: 1,
            x: item.initConfigData.x,
            y: item.initConfigData.y
          }
        }, item.initConfigData.runTime, item.initConfigData.delayTime, item.initConfigData.canvasAnimateEasing)
      })
      return maxRunTime
    },
    calcAnimationAndReturnMaxRunTimeByCss3 () {
      this.resetGridB()
      let canvasList = this.getImageCanvasListForCss3(this.nextImageObject)
      let maxRunTime = 0
      let baseConfig = this.getBaseAnimationConfig()
      ZRender.util.each(this.css3SlideGridBList, (gridItem, index) => {
        let singleConfig = this.getItemSingleConfig(null, baseConfig, index)
        if (singleConfig.runTime + singleConfig.delayTime > maxRunTime) {
          maxRunTime = singleConfig.runTime + singleConfig.delayTime
        }
        let useOpacity = false
        switch (singleConfig.animateEffect) {
          case 'opacity':
            gridItem.style['opacity'] = 0
            useOpacity = true
            break
          default:
            gridItem.style['opacity'] = 1
            break
        }
        let animationName = ''
        switch (singleConfig.animateItemDirectionRunning) {
          case 'left':
            gridItem.style['left'] = '-100%'
            animationName = useOpacity ? 'fromLeftToRightWithOpacityChange' : 'fromLeftToRight'
            break
          case 'top':
            gridItem.style['top'] = '-100%'
            animationName = useOpacity ? 'fromTopToBottomWithOpacityChange' : 'fromTopToBottom'
            break
          case 'right':
            gridItem.style['left'] = '100%'
            animationName = useOpacity ? 'fromRightToLeftWithOpacityChange' : 'fromRightToLeft'
            break
          case 'bottom':
            gridItem.style['top'] = '100%'
            animationName = useOpacity ? 'fromBottomToTopWithOpacityChange' : 'fromBottomToTop'
            break
          default:
            break
        }
        gridItem.style['display'] = 'block'
        gridItem.style['background-image'] = 'url(' + canvasList[index] + ')'
        gridItem.style['background-repeat'] = 'no-repeat'
        gridItem.style['background-size'] = '100% 100%'
        gridItem.style['-moz-background-size'] = '100% 100%'
        gridItem.style['-webkit-background-size'] = '100% 100%'
        gridItem.style['-o-background-size'] = '100% 100%'
        gridItem.style['animation-iteration-count:'] = '1'
        gridItem.style['-moz-animation-iteration-count:'] = '1'
        gridItem.style['-webkit-animation-iteration-count:'] = '1'
        gridItem.style['-o-animation-iteration-count:'] = '1'
        gridItem.style['animation-fill-mode'] = 'forwards'
        gridItem.style['-moz-animation-fill-mode'] = 'forwards'
        gridItem.style['-webkit-animation-fill-mode'] = 'forwards'
        gridItem.style['-o-animation-fill-mode'] = 'forwards'
        gridItem.style['animation-timing-function'] = singleConfig.css3AnimateEasing
        gridItem.style['-moz-animation-timing-function'] = singleConfig.css3AnimateEasing
        gridItem.style['-webkit-animation-timing-function'] = singleConfig.css3AnimateEasing
        gridItem.style['-o-animation-timing-function'] = singleConfig.css3AnimateEasing
        gridItem.style['animation-name'] = animationName
        gridItem.style['-moz-animation-name'] = animationName
        gridItem.style['-webkit-animation-name'] = animationName
        gridItem.style['-o-animation-name'] = animationName
        gridItem.style['animation-duration'] = singleConfig.runTime + 'ms'
        gridItem.style['-moz-animation-duration'] = singleConfig.runTime + 'ms'
        gridItem.style['-webkit-animation-duration'] = singleConfig.runTime + 'ms'
        gridItem.style['-o-animation-duration'] = singleConfig.runTime + 'ms'
        gridItem.style['animation-delay'] = singleConfig.delayTime + 'ms'
        gridItem.style['-moz-animation-delay'] = singleConfig.delayTime + 'ms'
        gridItem.style['-webkit-animation-delay'] = singleConfig.delayTime + 'ms'
        gridItem.style['-o-animation-delay'] = singleConfig.delayTime + 'ms'
      })
      return maxRunTime
    },
    calcImageNewSizeToCoverScreen (imageObject) {
      let sourceWidth = imageObject.width
      let sourceHeight = imageObject.height
      let newWidth = 0
      let newHeight = 0
      let fromLeft = 0
      let fromTop = 0
      // Cover the image to parent
      if (sourceWidth / sourceHeight <= this.excludeDividerElementWidth / this.excludeDividerElementHeight) {
        newWidth = this.excludeDividerElementWidth
        newHeight = Math.ceil(sourceHeight * this.excludeDividerElementWidth / sourceWidth)
        fromLeft = 0
        let canUseHeight = newWidth * this.excludeDividerElementHeight / this.excludeDividerElementWidth
        fromTop = Math.round((newHeight - canUseHeight) / 2)
      } else {
        newHeight = this.excludeDividerElementHeight
        newWidth = Math.ceil(sourceWidth * this.excludeDividerElementHeight / sourceHeight)
        fromTop = 0
        let canUseWidth = newHeight * this.excludeDividerElementWidth / this.excludeDividerElementHeight
        fromLeft = Math.round((newWidth - canUseWidth) / 2)
      }
      return {height: newHeight, width: newWidth, fromTop: fromTop, fromLeft: fromLeft}
    },
    getImageObjectCanvas (imageObject, calcImageNewSize) {
      let zRenderImage = new ZRender.Image({
        cursor: null,
        style: {
          image: imageObject,
          x: 0,
          y: 0,
          width: calcImageNewSize.width,
          height: calcImageNewSize.height
        }
      })
      let zRender = ZRender.init(this.drawImageObjectCanvas, {
        width: calcImageNewSize.width,
        height: calcImageNewSize.height
      })
      zRender.add(zRenderImage)
      this.drawImageObjectCanvas = zRender.painter.getRenderedCanvas()
      zRender.dispose()
      return this.drawImageObjectCanvas
    },
    getImageCanvasListForCss3 (imageObject) {
      let canvasList = []
      let newSize = this.calcImageNewSizeToCoverScreen(imageObject)
      let imageCanvas = this.getImageObjectCanvas(imageObject, newSize)

      for (let i = 0; i < this.gridItemCount; i++) {
        let imagePortionPositionX = newSize.fromLeft + (i % this.rowCount) * this.normalRowItemWidth
        let imagePortionPositionY = newSize.fromTop + Math.floor(i / this.rowCount) % this.columnCount * this.normalColumnItemHeight
        let imageWidth = (i % this.rowCount < this.rowCount - 1) ? this.normalRowItemWidth : this.lastRowItemWidth
        let imageHeight = (Math.floor(i / this.rowCount) < this.columnCount - 1)
            ? this.normalColumnItemHeight
            : this.lastColumnItemHeight
        let canvas = this.getImagePortion(imageCanvas, imagePortionPositionX, imagePortionPositionY, imageWidth, imageHeight)
        canvasList.push(canvas.toDataURL())
        canvas = null
      }
      return canvasList
    },
    getZRenderImageListForCanvas (imageObject) {
      let zRenderImageList = []
      let newSize = this.calcImageNewSizeToCoverScreen(imageObject)
      let imageCanvas = this.getImageObjectCanvas(imageObject, newSize)
      for (let i = 0; i < this.gridItemCount; i++) {
        let imagePositionX = (i % this.rowCount) * (this.normalRowItemWidth + this.gridDividerWidth)
        let imagePositionY = Math.floor(i / this.rowCount) % this.columnCount * (this.normalColumnItemHeight + this.gridDividerWidth)
        let imagePortionPositionX = newSize.fromLeft + (i % this.rowCount) * this.normalRowItemWidth
        let imagePortionPositionY = newSize.fromTop + Math.floor(i / this.rowCount) % this.columnCount * this.normalColumnItemHeight
        let imageWidth = (i % this.rowCount < this.rowCount - 1) ? this.normalRowItemWidth : this.lastRowItemWidth
        let imageHeight = (Math.floor(i / this.rowCount) < this.columnCount - 1)
            ? this.normalColumnItemHeight
            : this.lastColumnItemHeight
        let image = new ZRender.Image({
          cursor: null,
          progressive: i + 1,
          style: {
            image: this.getImagePortion(imageCanvas, imagePortionPositionX, imagePortionPositionY, imageWidth, imageHeight),
            x: imagePositionX,
            y: imagePositionY,
            width: imageWidth,
            height: imageHeight
          }
        })
        zRenderImageList.push(image)
      }
      return zRenderImageList
    },
    getImagePortion: function (imageCanvas, imgLeft, imgTop, imageWidth, imageHeight) {
      let zCanvas = ZRender.util.createCanvas()
      let zCanvasContext = zCanvas.getContext('2d')
      zCanvas.width = imageWidth
      zCanvas.height = imageHeight
      zCanvasContext.drawImage(imageCanvas, imgLeft, imgTop, imageWidth, imageHeight, 0, 0, imageWidth, imageHeight)
      return zCanvas
    },
    getImageUrl () {
      return this.useUnSplashService
          ? this.unSplashUrl + '&sig=' + new Date().getTime()
          : this.imageList[this.activeImageListIndex]
    },
    setActiveImageListToNext () {
      if (this.useUnSplashService) {
        this.activeImageListIndex = 0
        return
      }
      this.activeImageListIndex = this.activeImageListIndex === this.imageList.length - 1 ? 0 : this.activeImageListIndex + 1
    },
    getCanvasRandomEasing () {
      let easingArray = ['Linear', 'QuadraticIn', 'QuadraticOut', 'QuadraticInOut',
        'CubicIn', 'CubicOut', 'CubicInOut', 'QuarticIn', 'QuarticOut',
        'QuarticInOut', 'QuinticIn', 'QuinticOut', 'QuinticInOut', 'SinusoidalIn',
        'SinusoidalOut', 'SinusoidalInOut', 'ExponentialIn', 'ExponentialOut',
        'ExponentialInOut', 'CircularIn', 'CircularOut', 'CircularInOut',
        'ElasticIn', 'ElasticOut', 'ElasticInOut', 'BackIn', 'BackOut',
        'BackInOut', 'BounceIn', 'BounceOut', 'BounceInOut']
      return easingArray[parseInt(Math.random() * easingArray.length, 10)]
    },
    getCss3RandomEasing () {
      let easingArray = ['linear', 'ease', 'ease-in', 'ease-out', 'ease-in-out']
      return easingArray[parseInt(Math.random() * easingArray.length, 10)]
    },
    isIE () {
      return !!window.ActiveXObject || 'ActiveXObject' in window || (/Trident\/7\./).test(navigator.userAgent)
    }
  },
  computed: {
    getCur() {
      //console.log(this.currentImageObject)
    }
  }
}
</script>