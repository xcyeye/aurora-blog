<template>
  <div>
    <button @click="append">点击添加</button>
  </div>
  <!--<TestAppend/>-->
</template>

<script>
import $ from 'jquery'
import TestAppend from "./TestAppend";
import {createApp} from 'vue'
import Common from "./global/Common";
import Layout from "../layouts/Layout";
import About from "./About";
import Poster from "./global/Poster";

export default {
  name: "Test",
  methods: {
    append() {
      createApp(Poster).mount('#test')
    }
  }
}
</script>

<style scoped>

</style>