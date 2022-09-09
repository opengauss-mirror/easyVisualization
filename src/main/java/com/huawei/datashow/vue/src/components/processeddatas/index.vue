<template>
  <el-container>    
      <el-aside 
          id="aside_datasources" 
          width="10%"
          v-loading="el_aside_loading"
          :element-loading-text="$t('loading.text')"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)">   
        <el-button id="refreshDataSourceListButton" size="mini" type="success" icon="el-icon-refresh" @click='fetchDataSourceList'>
          {{$t('processeddatas.button.refreshDataSourceListButton')}}</el-button>      
        <el-alert id="alert_datasource" :title="$t('processeddatas.el-alert.alert_datasource')" type="info" center :closable="false"></el-alert>
        <el-menu @select='handleSelect'>
          <el-menu-item v-for="item in this.dataSourceList" :index="item">{{item}}</el-menu-item>
        </el-menu>   
        <el-button id="addSourceDataButton" size="mini" type="primary" icon="el-icon-plus" @click='handleAddSourceData'>{{$t('processeddatas.button.addSourceDataButton')}}</el-button> 
      </el-aside>        
      

      <el-container id="main"> 
        <router-view></router-view>
        <DataSource 
          v-if="sourceDataIsActive" 
          v-loading="!sourceDataIsActive"
          :data-source-name="this.dataSourceName">
        </DataSource>
        <el-aside id="selectdata" width="40%" v-if="selectDataIsActive">  
            <SelectData :data-source-list="dataSourceList"></SelectData>                  
        </el-aside>
      </el-container>
  </el-container>
</template>



<script>
import router from '@/router'
import DataSource from '@/components/datasource'
import SelectData from '@/components/selectdata'
import axios from 'axios'

export default { 
data() {
  return {
    sourceDataIsActive:false,

    selectDataIsActive:false,

    dataSourceList:[],

    dataSourceName:'',

    el_aside_loading:true
  }
},

methods: {
  async fetchDataSourceList(){
    this.el_aside_loading = true
    await axios({
      url:'/handle-data-source/get-data-source-list',
      method:"get",
    }).then(
      response => {
        this.dataSourceList = JSON.parse(response.data.result)
      },
      error => {    
      }
    )
    this.el_aside_loading = false    
    this.$store.commit('updateDataSourceList', this.dataSourceList)    
  },  
  handleAddSourceData(){
    this.selectDataIsActive = false
    this.sourceDataIsActive = false
    router.push('/home/processeddatas/addsourcedata')
  },
  handleSelect(index,indexPath){
    router.push('/home/processeddatas')
    this.$store.commit('updateWhichModuleAmI','ProcessedDatas')
    this.dataSourceName = index

    this.sourceDataIsActive = false

    this.$nextTick(function (){       
      this.sourceDataIsActive = true       
    })              
  },

  handelSelectDataIsActive(){
    this.selectDataIsActive = !this.selectDataIsActive
  }
},     

components:{
  DataSource,
  SelectData,
},    

mounted() {
  this.$store.commit('updateWhichModuleAmI','ProcessedDatas')
  this.fetchDataSourceList()
},


}
</script>

<style scoped>

@import '@/assets/css/processeddatas/processeddatas.css';

</style>

