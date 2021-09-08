<template>
  <div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <button @click="append">点击添加</button>
    <div class="div1" :style="height">
    </div>
  </div>
</template>

<script>
export default {
  name: "Test",
  data() {
    return {
      height: 'height: 2rem',
      length: 2
    }
  },
  methods: {
    append() {
      this.length += 1
      setTimeout(() => {
        this.height = 'height: ' + this.length + "rem;"
      },1500)
    }
  }
}
</script>

<style scoped>
  .div1 {
    width: 7rem;
    background-color: red;
    display: inline-block;
  }
</style>