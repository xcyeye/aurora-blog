import { createApp } from 'vue'

import App from './App.vue'
import router from './router'

import {setupAssets} from "@/plugins";
import {vuexStore} from "@/stores/vuex";
import {setupStore} from "@/stores";

/** 导入静态文件 css */
setupAssets();

const app = createApp(App)

app.use(router)
app.use(vuexStore)
// store plugin: pinia
setupStore(app);

app.mount('#app')
