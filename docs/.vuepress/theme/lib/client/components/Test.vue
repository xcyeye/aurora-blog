<template>
  <div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <button @click="append">点击添加</button>
    <div class="div1">
    </div>
  </div>
</template>

<script>
import $ from 'jquery'
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
      $(".div1").css("height",'10rem')
    }
  }
}
</script>

<style scoped>
  .div1 {
    width: 7rem;
    background-color: red;
    display: inline-block;
    height: 2rem;
  }
</style>