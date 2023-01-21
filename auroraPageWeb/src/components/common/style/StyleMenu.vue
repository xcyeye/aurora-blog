<!--此组件是样式控制面板-->
<template>
  <div class="setColor">
    <div @click="clickSetColor"
         id="setIco" class="setIco bg_color">
			<parcel-style>
				<svg-icon class="global-common-animate" icon="bi:gear-fill"/>
			</parcel-style>
    </div>

    <div class="welcome-parent" id="welcome-parent">
			<aurora-card custom-style="width: 24%;" :class="{'style-menu-box': styleMenuActiveStatus}" class="m-4 p-4 f4 color-shadow-small bg-gray-800-mktg rounded-2 signup-content-container welcome">
				<template #content>
					<div class="custom-bottom custom-common">
						<div style="flex: 1" class="custom-bottom-span custom-common-span">
							<li class="custom-li" v-for="(item,index) in fontColorArr">
								<span :style="setColorBack(item)" :data-color="item" @click="setColor($event,item)"></span>
							</li>
						</div>
					</div>
					<div class="custom-bottom custom-common">
						<n-slider @update:value="handleChangeBorderRadius" v-model:value="borderRadius" :min="0" :max="45" :step="1" :tooltip="false" />
					</div>
				</template>
			</aurora-card>
    </div>
  </div>
</template>

<script lang="ts">
import blogConfig from '@/config/blogConfig.json';
import {useThemeStore} from "@/stores";
import ParcelStyle from "@/components/common/other/ParcelStyle.vue";

export default {
  name: "HomeWelcome",
	components: {ParcelStyle},
  data() {
    return {
      setColorStyle: "display: none;",
      fontArr: [],
      colorArr: [],
      isFitter: true,
      opacity: 1,
      blur: 5,
      borderRadius: 20,
      maxFontColorArr: 8,
      fontColorArr: [],
      fontFamilyArr: [],
      currentColor: '',
      currentFont: '',
			styleMenuActiveStatus: false
    }
  },
  created() {
		this.maxFontColorArr = blogConfig.maxFontColorArr
	
		this.fontArr = blogConfig.fontFamily
		this.colorArr = blogConfig.fontColor
    this.isFitter = blogConfig.isFitter

    if (this.colorArr.length < this.maxFontColorArr) {
      this.fontColorArr = this.colorArr
    }else {
      this.fontColorArr = this.colorArr.slice(0,this.maxFontColorArr)
    }

    if (this.fontArr.length < this.maxFontColorArr) {
      this.fontFamilyArr = this.fontArr
    }else {
      this.fontFamilyArr = this.fontArr.slice(0,this.maxFontColorArr)
    }

    //获取主题配置默认圆角，模糊度，透明度
		this.borderRadius = blogConfig.defaultBorderRadius
		this.opacity = blogConfig.defaultOpacity
		this.blur = blogConfig.defaultBlur

    this.$store.commit('setVarFilterBlur',{
      varFilterBlur: this.blur
    })
    this.$store.commit('setVarBorderRadius',{
      varBorderRadius: this.borderRadius
    })

    this.$store.commit('setVarOpacity',{
      varOpacity: this.opacity
    })
  },
  computed: {
    getColorStyle() {
      return this.setColorStyle
    },
    setColorBack() {
      return (item: string) => {
        return "background-color: " + item + ";";
      }
    },
    setFamilyBack() {
      return (item: string) => {
        return "font-family: " + item + ";";
      }
    }
  },
  methods: {
		useThemeStore,
		handleChangeBorderRadius(value: number) {
			const themeTemp: Theme.Setting = useThemeStore().currentTheme
			themeTemp.borderRadius = value
			useThemeStore().setCurrentThemeStore(themeTemp)
		},
    clickSetColor() {
			this.styleMenuActiveStatus = !this.styleMenuActiveStatus
    },
    setColor(e: Event,color: string) {
			const themeTemp: Theme.Setting = useThemeStore().currentTheme
			themeTemp.fontColor = color
      useThemeStore().setCurrentThemeStore(themeTemp)
    },
    setFont(e: Event,font: string) {
      this.currentFont = font
      this.$store.commit('setFontFamilyStyle',{
        fontFamily: this.currentFont,
        color: this.currentColor
      })
      this.$emit('setBodyStyle')
    },
    setImg() {
      this.$emit('setBodyWallpaper')
    },
    setIsFitter() {
      //isFitter
      //设置是否要毛玻璃特性，通过类名控制
      this.$emit('setIsFitter',!this.isFitter)
      this.isFitter = !this.isFitter
      this.$store.commit('setIsFitter',{
        isFitter: this.isFitter
      })
    }
  },
  props: {
		userUid: {
			type: String
		}
  }
}
</script>
