<template>
  <nav>
    <div class="menu">
      <p class="website_name">Aurora</p>
      <div class="menu_links">
        <a href="/link" class="link">link</a>
        <a href="/mood" class="link">mood</a>
        <a href="/tag" class="link">tag</a>
      </div>
      <div class="menu_icon">
        <span class="icon"></span>
      </div>
    </div>
  </nav>

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

      <div class="text">
        <article>
          <p>Uh oh! Looks like you got lost. <br>Go back to the homepage if you dare!</p>
          <button @click="goHome">i dare!</button>
        </article>
      </div>

    </div>
  </section>
</template>

<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  name: '404',
  methods: {
    goHome() {
      this.$router.push("/")
    }
  }
})
</script>
<style lang="scss">
@import "../styles/404";
</style>