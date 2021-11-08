<template>
  <nav class="fail_nav">
    <div class="menu">
      <p class="website_name">Aurora</p>
    </div>
  </nav>

  <div class="bottom_fail">
    <section class="wrapper">
      <div class="container">

        <div id="scene" class="scene" data-hover-only="false">


          <div class="circle" data-depth="1.2"></div>

          <div class="one" data-depth="0.9">
            <div class="content">
              <span class="piece"></span>
              <span class="piece"></span>
              <span class="piece"></span>
            </div>
          </div>

          <div class="two" data-depth="0.60">
            <div class="content">
              <span class="piece"></span>
              <span class="piece"></span>
              <span class="piece"></span>
            </div>
          </div>

          <div class="three" data-depth="0.40">
            <div class="content">
              <span class="piece"></span>
              <span class="piece"></span>
              <span class="piece"></span>
            </div>
          </div>

          <p class="p404" data-depth="0.50">404</p>
          <p class="p404" data-depth="0.10">404</p>

        </div>


      </div>
    </section>
  </div>
  <div class="back_home">
    <span @click="goHome" class="back_to_homepage">back to homepage</span>
  </div>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: '404',
  methods: {
    goHome() {
      this.$router.push("/")
    }
  },
})
</script>
<style lang="scss">
@import "../styles/404";
</style>