import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

import {setupAssets} from "@/plugins";
import {createStore} from "vuex";
import {vuexStore} from "@/stores/vuex";

/** 导入静态文件 css */
setupAssets();

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(vuexStore)

app.mount('#app')
