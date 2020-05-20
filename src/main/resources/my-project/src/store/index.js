
import Vuex from 'vuex'
import Vue from 'vue'

 Vue.use(Vuex)

const store = new Vuex.Store({
    state:{
        startTime:'1',
        endTime:'2'
    },
    getters:{
        // 参数列表state指的是state数据mut
        getStartTime(state){
            return state.startTime;
        },
        getEndTime(state){
            return state.endTime;
        }
    },
    mutations:{
        setStartTime(state,time){
            state.startTime = time;
        },
        setEndTime(state,time){
            state.endTime = time;
        }
    }
});
export default store;