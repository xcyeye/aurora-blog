<template>
  <common :is-sticky-sidebar="true" :is-show-side-bar="false"
          :show-sidebar-link="false"
          :is-show-top-img="true" :is-show-head-line="false">
    <template #center1>
      <div class="link">
        <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle"
             class="box link-common" id="c-link">
          <div class="aurora-theme-archive">
            <AuroraArchive/>
          </div>
        </div>
      </div>

    </template>
  </common>
</template>

<script>
export default {
  name: "Archive"
}
</script>

<style scoped>

</style>