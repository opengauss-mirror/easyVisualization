import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const actions = {}

const mutations = {
    updateWhichModuleAmI(state,value){
        state.whichModuleAmI = value
    },
    updateEchartsFormList(state,value){
        state.echartsFormList = value
    },
}

const state = {    
    whichModuleAmI:'',
    echartsFormList:[],
}


export default new Vuex.Store({ 
    actions,
    mutations,
    state
})