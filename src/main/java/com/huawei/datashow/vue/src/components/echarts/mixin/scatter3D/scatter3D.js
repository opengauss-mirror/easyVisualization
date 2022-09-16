import * as echarts from 'echarts'
import 'echarts-gl' 
import 'echarts/lib/component/visualMap'
import axios from 'axios'
var myChart
export const scatter3D = {
  data(){
    return {
      echartsFormList: [],
      option:{
        title: {
          text: this.$t('title'),
          textStyle:{
            color: '#000000',
            fontSize: 18
          },
          subtextStyle:{
            fontSize: 12
          },
        },
        tooltip: {},
        toolbox:{
          show:true,
          right:60,
          feature:{
            saveAsImage:{
              type: 'jpg',
              show: true,
            }
          }
        }, 
        visualMap: {
          show: false,
          dimension: 0,
          min: -1,
          max: 1
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
            type:'scatter3D',
            wireframe:{},
            data:[]
          }
        ]        
      },

      dataSourceSizeList:[],
      startIndex: 0,
      endIndex:9,

      echarts_title_show:true,
      
      echarts_title_position:'',
      
      echarts_title_text:'',
      
      echarts_title_subtext:'',
      
      echarts_title_textStyle_fontSize:18,
      
      echarts_title_subtextStyle_fontSize:12,
    }
  },
  computed: {
    dataSourceName(){
      if (this.echartsFormList.length !== 0) {
        return this.echartsFormList[0].dataSourceName
      }
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
    },
    dataSourceSizeMax() {
      var dataSourceSizeMax = this.dataSourceSizeList[0];
      if (this.dataSourceSizeList.length > 1) {
        for(let i = 1; i < this.dataSourceSizeList.length; i++) {
          dataSourceSizeMax = Math.max(dataSourceSizeMax, this.dataSourceSizeList[i]);
        }        
      } 
      return dataSourceSizeMax
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
    handle_echarts_title_show(){
      this.option.title.show = this.echarts_title_show
      myChart.setOption(this.option)
    },
    
    handle_echarts_title_position(value){
      switch(value){
        case '1':
          this.option.title.left = 'center'
          this.option.title.top = 'top'
          break;
        case '2':
          this.option.title.left = 'right'
          this.option.title.top = 'top'
          break;
        case '3':
          this.option.title.left = 'right'
          this.option.title.top = 'middle'
          break;
        case '4':
          this.option.title.left = 'right'
          this.option.title.top = 'bottom'
          break;
        case '5':
          this.option.title.left = 'center'
          this.option.title.top = 'bottom'
          break;
        case '6':
          this.option.title.left = 'left'
          this.option.title.top = 'bottom'
          break;
        case '7':
          this.option.title.left = 'left'
          this.option.title.top = 'middle'
          break;
        default:
          this.option.title.left = 'left'
          this.option.title.top = 'top'
          break;            
      }
      myChart.setOption(this.option)        
    },
    
    handle_echarts_title_text(){
      this.option.title.text = this.echarts_title_text
      myChart.setOption(this.option)
    },
    
    handle_echarts_title_subtext(){
      this.option.title.subtext = this.echarts_title_subtext
      myChart.setOption(this.option)
    },
    
    handle_echarts_title_textStyle_fontSize(value){
      this.option.title.textStyle.fontSize = value
      myChart.setOption(this.option)
    },    
    
    handle_echarts_title_subtextStyle_fontSize(value){
      this.option.title.subtextStyle.fontSize = value
      myChart.setOption(this.option)
    },  

    trans2Datas(sourceData, xOption, yOption, zOption) {
      let sourceDataSize = sourceData.length
      let datas = []
      for (let i = 0; i < sourceDataSize; i++) {
        let data = [sourceData[i][xOption], sourceData[i][yOption], sourceData[i][zOption]]
        datas.push(data)
      }
      this.option.series[0].data = datas    
    },
    
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
            this.fetchDataSourceSize(dataSourceName)
            this.trans2Datas(sourceData, this.xOption, this.yOption, this.zOption)
          },            
          error => {
      })        
    },

    fetchDataSourceSize(dataSourceName) {
      axios({                        
          url:'/handle-data-source/get-data-source-size',
          method:"get",
          params:{  
              'dataSourceName':dataSourceName,
          }        
      }).then(
          response => {
            this.dataSourceSizeList.push(JSON.parse(response.data.result))
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
    this.echartsFormList = this.$store.state.echartsFormList;   
    this.refreshDatas(); 
    this.refreshEcharts();
  },
  mounted() {
    myChart = echarts.init(document.getElementById('scatter3D')) 
  },
}