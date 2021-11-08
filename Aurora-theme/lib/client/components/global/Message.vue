<template>
  <div :style="$store.state.borderRadiusStyle + $store.state.opacityStyle" class="c-page" id="c-page" style="text-align: center">
    <p class="message" v-html="message"></p>
  </div>
</template>

<script>
import {useThemeData} from "../../composables";

export default {
  name: "Message",
  data() {
    return {
      message: '',
      themeProperty: ''
    }
  },

  created() {
    this.themeProperty = useThemeData().value
    this.message = this.themeProperty.message
  }
}
</script>
