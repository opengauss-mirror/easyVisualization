<template>
  <el-form id="form" label-position="left" label-width="20%">
    <el-form-item id="echarts-type" :label="$t('selectdata.el-form-item.echarts-type')">
      <el-select id="select-echarts-type" v-model="echartsType" size="small" @change="echartsFormList = []">
        <el-option
          v-for="item in echartsTypes"
          :label="item.label"
          :value="item.value">
        </el-option>
      </el-select>        
    </el-form-item>
    <Discription v-for="item,index in echartsFormList" :form="item" :form-index="index" :discription-type="echartsType"></Discription>
    <el-button id="button-add-echarts-form" type="info" :disabled="addEchartsFormDisabled" @click="dialogFormVisible = true" size="small" icon="el-icon-circle-plus-outline" v-if="echartsType!=''">
      {{$t('selectdata.button.button-add-echarts-form')}}</el-button>
    <el-dialog :title="$t('dialog-form.title1') + this.echartsType + $t('dialog-form.title2')" :visible.sync="dialogFormVisible">
      <DialogForm :form-type="echartsType" :data-source-list="dataSourceList" :unavailable-data-source-list="unavailableDataSourceList"></DialogForm>
    </el-dialog>
    <el-button id="button-to-echarts" type="primary" :disabled="toEchartsDiabled" @click="toEcharts" size="small" icon="el-icon-s-open" v-if="echartsType!=''">
      {{$t('selectdata.button.button-to-echarts')}}</el-button>
  </el-form>
</template>



<script>
import DialogForm from '@/components/dialog/dialog-form'
import Discription from '@/components/discription'
import router from '@/router';
  export default {
    props:[ 'dataSourceList' ],
    data() {
      return {
        echartsTypes:[
          {
            label:this.$t('echartsType.bar'),
            value:'bar'
          },
          {
            label:this.$t('echartsType.ybar'),
            value:'ybar'
          },
          {
            label:this.$t('echartsType.line'),
            value:'line'
          },
          {
            label:this.$t('echartsType.area'),
            value:'area'
          },
          {
            label:this.$t('echartsType.scatter'),
            value:'scatter'
          },
          {
            label:this.$t('echartsType.combination'),
            value:'combination'
          },
          {
            label:this.$t('echartsType.pie'),
            value:'pie'
          },
          {
            label:this.$t('echartsType.radar'),
            value:'radar'
          },
          {
            label:this.$t('echartsType.scatter3D'),
            value:'scatter3D'
          }

        ],
        echartsType:'',
        echartsFormList:[],
        dialogFormVisible: false  
      }
    },
    computed: {
      unavailableDataSourceList() {
        let unavailableDataSourceList = []
        let length = this.echartsFormList.length
        for(let i = 0; i < length; i++) {
          unavailableDataSourceList.push(this.echartsFormList[i].dataSourceName);
        }
        return unavailableDataSourceList;
      },
      addEchartsFormDisabled() {
        if ((this.echartsType == 'pie' || this.echartsType == 'radar' || this.echartsType == 'scatter3D') && this.echartsFormList.length == 1) {
          return true
        }         
      },
      toEchartsDiabled() {
        if (this.echartsFormList.length == 0) {
          return true
        }
      }
    },
    methods: {
      addEchartsForm(form) {
        this.echartsFormList.push(form)
      },
      removeEchartsForm(formIndex) {
        this.echartsFormList.splice(formIndex, 1)
      },
      toEcharts() {
        this.$store.commit("updateEchartsFormList",this.echartsFormList)
        this.$parent.$parent.$parent.$parent.sourceDataIsActive = false
        this.$parent.$parent.$parent.$parent.selectDataIsActive = false        
        router.push('/home/processeddatas/' + this.echartsType)
      }
    },
    components:{ DialogForm, Discription },      
    mounted() {},
    

  };
</script>

<style scoped>
  @import '@/assets/css/selectdata/selectdata.css';
</style>