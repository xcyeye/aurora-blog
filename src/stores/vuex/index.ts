import { createStore } from 'vuex'
import {state} from "@/stores/vuex/state";
import {mutations} from "@/stores/vuex/mutations";

export const vuexStore = createStore({
    state,
    mutations,
    modules: {
    }
})
