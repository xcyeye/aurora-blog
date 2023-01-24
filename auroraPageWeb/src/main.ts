import { createApp } from 'vue'

import App from './App.vue'
import router from './router'

import {setupAssets} from "@/plugins";
import {vuexStore} from "@/stores/vuex";
import {setupStore} from "@/stores";
// import vueMetaManager from '@/global/vue-meta'
import { createMetaManager, defaultConfig, deepestResolver } from 'vue-meta'

/** 导入静态文件 css */
setupAssets();

const app = createApp(App)

app.use(router)
app.use(vuexStore)
// app.use(vueMetaManager)
app.use(createMetaManager(false , defaultConfig, deepestResolver))
// store plugin: pinia
setupStore(app);

app.mount('#app')