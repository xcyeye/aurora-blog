import { createApp } from 'vue'

import App from './App.vue'

import {setupAssets} from "@/plugins";
import {setupStore} from "@/stores";
// import vueMetaManager from '@/global/vue-meta'
import { createMetaManager, defaultConfig, deepestResolver } from 'vue-meta'
import {setupRouter} from "@/router";
import AppLoading from "@/components/common/other/AppLoading.vue";
import 'lazysizes';
// import a plugin
import 'lazysizes/plugins/parent-fit/ls.parent-fit';


/** 导入静态文件 css */
// setupAssets();

// const app = createApp(App)

// app.use(router)
// app.use(vueMetaManager)
// app.use(createMetaManager(false , defaultConfig, deepestResolver))
// store plugin: pinia
// setupStore(app);

// app.mount('#app')
// import VueVideoPlayer from '@videojs-player/vue'

async function setupApp() {
// import assets: js、css
  setupAssets();

  // app loading
  const appLoading = createApp(AppLoading);

  appLoading.mount('#appLoading');

  const app = createApp(App);

  // store plugin: pinia
  setupStore(app);

  // app.use(VueVideoPlayer)

  // vue-meta
  app.use(createMetaManager(false , defaultConfig, deepestResolver))

  // vue router
  await setupRouter(app);

  // mount app
  app.mount('#app');
}

setupApp()