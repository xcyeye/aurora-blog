import { createStore } from 'vuex'

const actions = require("./actions");
const mutations = require('./mutations')
const state = require('./state')

export default createStore({
    state,
    mutations,
    actions,
    modules: {
    }
})
