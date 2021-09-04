<template>
  <a id="link-a" target="_blank" :href="item.url">
    <div class="link-item" :style="linkTopStyle" id="link-item">
      <div class="link-top">
        <div class="link-img">
          <img id="link-img" :src="item.logo" :alt="item.title">
        </div>
      </div>
      <div class="link-bottom" :style="linkBottomStyle">
        <div class="link-bottom-title link-bottom-common">
          <span>{{item.title}}</span>
        </div>
        <div class="link-bottom-describe link-bottom-common">
          <span id="link-bottom-describe">{{item.describe}}</span>
        </div>
      </div>
    </div>
  </a>
</template>

<script>

export default {
  name: "LinkItem",
  props: {
    item: null,
    linkTopStyle: {
      type: String,
      default() {
        return 'background-color: pink'
      }
    },
    linkBottomStyle: {
      type: String,
      default() {
        return 'background-color: #a9def9'
      }
    },
    color: {
      type: String,
      default() {
        return '#2c3e50'
      }
    }
  }
}
</script>

<style scoped>

</style>