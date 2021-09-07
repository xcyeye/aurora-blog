<template>
  <div>
    <button @click="append">点击添加</button>
    <div class="div1">
      <div class="div2" :class="{isIndex: isIndex}"></div>
    </div>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      isIndex: false
    }
  },
  methods: {
    append() {
      this.isIndex = !this.isIndex
    }
  }
}
</script>

<style scoped>
  .div1 {
    width: 7rem;
    height: 7rem;
    background-color: red;
    display: inline-block;
  }

  .div2 {
    width: 6rem;
    height: 9rem;
    background-color: blue;
    display: inline-block;
  }
  .isIndex {
    z-index: -6;
  }
</style>