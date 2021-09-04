<template>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="c-page" id="c-page" style="text-align: center">
    <p class="message" v-html="message"></p>
  </div>
</template>

<script>
import myData from '@temp/my-data'

export default {
  name: "Message",
  data() {
    return {
      message: '',
      themeProperty: null
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
    }).then(() => {
      this.message = this.themeProperty.message
    })
  }
}
</script>

<style scoped>

</style>