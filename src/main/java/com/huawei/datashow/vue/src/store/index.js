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
    updateDataSourceList(state,value){
        state.dataSourceList = value
    }
}

const state = {    
    whichModuleAmI:'',
    echartsFormList:[],
    dataSourceList:[]
}


export default new Vuex.Store({ 
    actions,
    mutations,
    state
})