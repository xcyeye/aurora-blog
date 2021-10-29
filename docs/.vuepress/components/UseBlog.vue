<template>
  <!--docs/.vuepress/components/Aurora.vue-->
  <aurora-global :show-bg="true" :show-style-menu="true" :show-navbar="true">
    <template #top>
      <h1 style="margin: 0 auto">注册组件测试</h1>
    </template>

    <template #center>
      <div style="width: 30rem;height: 330rem;background: plum;margin: 0 auto"></div>
    </template>

    <template #bottom>
      <aurora-footer/>
    </template>
  </aurora-global>
</template>

<script>
export default {
  name: "Mycom"
}
</script>

<style scoped>

</style>