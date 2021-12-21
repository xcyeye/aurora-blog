import { createStore } from 'vuex'

import mutations from "./mutations";
import state from "./state";

export default createStore({
    state,
    mutations,
    modules: {
    }
})
