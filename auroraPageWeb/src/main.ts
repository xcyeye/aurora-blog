import { createApp } from 'vue'

import App from './App.vue'

import {setupAssets} from "@/plugins";
import {setupStore} from "@/stores";
// import vueMetaManager from '@/global/vue-meta'
import { createMetaManager, defaultConfig, deepestResolver } from 'vue-meta'
import {setupRouter} from "@/router";

/** 导入静态文件 css */
// setupAssets();

// const app = createApp(App)

// app.use(router)
// app.use(vueMetaManager)
// app.use(createMetaManager(false , defaultConfig, deepestResolver))
// store plugin: pinia
// setupStore(app);

// app.mount('#app')

async function setupApp() {
// import assets: js、css
  setupAssets();

  const app = createApp(App);

  // store plugin: pinia
  setupStore(app);

  // vue-meta
  app.use(createMetaManager(false , defaultConfig, deepestResolver))

  // vue router
  await setupRouter(app);

  // mount app
  app.mount('#app');
}

setupApp()