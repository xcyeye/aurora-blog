<template>
  <a :href="'https://github.com/' + username + '/' + repo" target="_blank">
    <div class="github-pins" id="github-pins">
      <div class="github-pins-inner">
        <img :src="'https://github-readme-stats.vercel.app/api/pin/?username='+ username + '&repo=' + repo" alt="">
      </div>
    </div>
  </a>
</template>

<script>
export default {
  name: "Pins",
  props: {
    username: {
      type: String,
      default() {
        return "vuepress-aurora"
      }
    },
    repo: {
      type: String,
      default() {
        return "vuepress-theme-aurora"
      }
    }
  }
}
</script>

<style scoped>

</style>