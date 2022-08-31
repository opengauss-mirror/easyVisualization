<template>
  <el-container>
    <el-aside>
      <el-button @click="refreshEcharts">刷新</el-button>
    </el-aside>
    <el-main>
        <div id="surface" :style="{width: '100%', height:'100%'}"></div>
    </el-main>
  </el-container>
</template>

<script>
import * as echarts from 'echarts'
import 'echarts-gl' 
import 'echarts/lib/component/visualMap'
import axios from 'axios'
var myChart
export default {
  data(){
    return {
      echartsFormList: [],

      //保存echarts配置项
      option:{
        title: {
          text: '曲面图'
        },
        tooltip: {},
        visualMap: {
          show: false,
          dimension: 0,
          min: -1,
          max: 1,
          // inRange: {
          //   color: [
          //     '#313695',
          //     '#4575b4',
          //     '#74add1',
          //     '#abd9e9',
          //     '#e0f3f8',
          //     '#ffffbf',
          //     '#fee090',
          //     '#fdae61',
          //     '#f46d43',
          //     '#d73027',
          //     '#a50026'
          //   ]
          // }
        },
        xAxis3D: {
          type: 'value'
        },
        yAxis3D: {
          type: 'value'
        },
        zAxis3D: {
          type: 'value'
        },
        grid3D: {
          viewControl: {}
        },
        series: [
          {
            type:'surface',
            wireframe:{},
            data:[]
          }
        ]        
      },

      dataSourceSizeList:[],
      //数据项起止索引
      startIndex: 0,
      endIndex:10,
    }
  },
  computed: {
    dataSourceName(){
      return this.echartsFormList[0].dataSourceName
    },
    limit(){
      return this.endIndex - this.startIndex + 1
    },
    xOption(){
      return this.echartsFormList[0].xOption
    },
    yOption(){
      return this.echartsFormList[0].yOption
    },
    zOption(){
      return this.echartsFormList[0].zOption
    }        
  },
  watch: {
    option: {
      handler: function() {
        this.refreshEcharts()
      },
      deep: true      
    }
  },
  methods: {
    /**
     * 将源数据转换为曲面图能接受的datas
     * @param {源数据} sourceData 
     * @param {x轴数据项} xOption 
     * @param {y轴数据项} yOption 
     * @param {z轴数据项} zOption 
     */
    trans2Datas(sourceData, xOption, yOption, zOption) {
      let sourceDataSize = sourceData.length
      this.datas = []
      let datas = []
      for (let i = 0; i < sourceDataSize; i++) {
        let data = [sourceData[i][xOption], sourceData[i][yOption], sourceData[i][zOption]]
        datas.push(data)
      }
      this.option = {
        title: {
          text: '曲面图'
        },
        tooltip: {},
        visualMap: {
          show: false,
          dimension: 0,
          min: -1,
          max: 1,
        },
        xAxis3D: {
          type: 'value'
        },
        yAxis3D: {
          type: 'value'
        },
        zAxis3D: {
          type: 'value'
        },
        grid3D: {
          viewControl: {}
        },
        series: [
          {
            type:'surface',
            wireframe:{},
            data:[[0.3976555,0.46812832,0.24339181],[0.5096373,0.852295,0.41115838],[0.6445806,0.1444233,0.9014962],[0.7149737,0.18765211,0.8837727],[0.65729177,0.08934718,0.37066072],[0.7656155,0.4951011,0.48147178],[0.16795939,0.80078226,0.3644728],[0.004065633,0.8538982,0.6135],[0.57265735,0.64645505,0.3059389],[0.5140115,0.04043609,0.11033231],[0.6526939,0.83749485,0.09872198]]
          }
        ]
      };      
          myChart.setOption(this.option) ;
      // this.option.series[0].data = datas
    },
    
    /**
     * 得到datas
     * @param {数据源名称} dataSourceName 
     * @param {x轴数据项} xOption 
     * @param {y轴数据项} yOption 
     * @param {z轴数据项} zOption 
     */
    getDatas(dataSourceName, startIndex, limit) {
      axios({                        
          url:'/handle-data-source/read-data-source',
          method:"get",
          params:{  
              'dataSourceName':dataSourceName,
              'startIndex':startIndex,
              'limit':limit
          }        
      }).then(
          response => {
            let sourceData = JSON.parse(response.data.result).sourceData;
            this.trans2Datas(sourceData, this.xOption, this.yOption, this.zOption)
          },            
          error => {
      })        
    },
    refreshDatas() {
      this.getDatas(this.dataSourceName, this.startIndex, this.limit)
    },
    refreshEcharts() {
      myChart.setOption(this.option)
    }
  },
  created() {
    // 接收selectData组件提交的echartsFormList和echartsType
    this.echartsFormList = this.$store.state.echartsFormList;   
    this.refreshDatas(); 
  },
  mounted() {
    //初始化myChart图表
    myChart = echarts.init(document.getElementById('surface')) 

  },
}
</script>