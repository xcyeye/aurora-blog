<template>
  <div class="footer-item" id="footer-item">
    <span v-html="item"></span>
  </div>
</template>

<script>
export default {
  name: "FooterItem",
  props: {
    item: null
  },
}
</script>

<style scoped>

</style>