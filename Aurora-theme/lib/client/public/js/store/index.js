import { createStore } from 'vuex'

//const actions = require("./actions");
import mutations from "./mutations";
import state from "./state";

export default createStore({
    state,
    mutations,
    //actions,
    modules: {
    }
})
