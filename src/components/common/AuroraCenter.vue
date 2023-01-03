<template>
  <main :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="page box">
    <div class="theme-default-content">
      <slot name="page-center1"></slot>
      <slot name="page-center2"></slot>
      <slot name="page-center3"></slot>
      <slot name="page-center4"></slot>
      <slot name="page-center5"></slot>
    </div>
  </main>
</template>

<script>
export default {
  name: "Center"
}
</script>