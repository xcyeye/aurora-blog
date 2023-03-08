<template>
  <div class="footer-item" id="footer-item">
    <span v-html="item"></span>
  </div>
</template>

<script lang="ts">
export default {
  name: "FooterItem",
  props: {
    item: null
  },
}
</script>

<style scoped>

</style>