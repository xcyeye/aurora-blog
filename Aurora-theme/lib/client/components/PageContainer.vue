<template>
  <page v-if="showPageContent" :theme-property="themeProperty"/>
</template>

<script>
import Page from './Page.vue'
export default {
  name: "PageContainer",
  components: {
    Page
  },
  props: {
    themeProperty: {}
  },
  data() {
    return {
      showPageContent: false
    }
  },
  created() {
    let frontmatter = this.$frontmatter;
    if (frontmatter.auroraLayout !== 'vuepress-aurora') {
      this.showPageContent = true
    }

    this.$router.beforeEach((to,from,next) => {
      if (frontmatter.auroraLayout !== 'vuepress-aurora') {
        this.showPageContent = true
      }
    })
  }
}
</script>

<style scoped>

</style>