import * as echarts from 'echarts'
import axios from 'axios'
var myChart
export const pie = {
  data() {
    return {
      
      echartsFormList: [],
      
      echartsType: '',
      
      datasets: [],   
      
      series: [],
      
      option: {
        title: {
          text: 'echarts',
          show: true,
          textStyle:{
            color: '#000000',
            fontSize: 18
          },
          subtextStyle:{
            fontSize: 12
          },
          left:'',
          top:'',
          subtext:''
        },
        legend: {
          orient:'horizontal',
          left:'center',
          top:'top',
          textStyle:{
            fontSize:12
          }
        },
        toolbox:{
          show:true,
          right:60,
          feature:{                            
            dataView:{
              title:'datas',
              readOnly:false
            },
            saveAsImage:{
              type: 'jpg',
              show: true,
            }
          }
        },             
        boundaryGap:false,
        dataset:[],
        series:[],
        tooltip:{
          trigger:'item',
          
        }        
      },
      
      dataSourceSizeList:[],
      
      startIndex: 0,
      endIndex:10,
    
      
      echarts_title_show:true,
      
      echarts_title_position:'',
      
      echarts_title_text:'',
      
      echarts_title_subtext:'',
      
      echarts_title_textStyle_fontSize:18,
      
      echarts_title_subtextStyle_fontSize:12,
      
      echarts_title_textStyle_color:'#000000',

      
      echarts_legend_position:'',
      
      echarts_legend_orient:'',
      
      echarts_legend_textStyle_fontSize:12,

      
      echarts_serie_radius_interior:0,
      
      echarts_serie_radius_exterior:250,
      
      echarts_serie_center_left:50,
      
      echarts_serie_center_top:50,
      
      echarts_serie_itemStyle_borderRadius:0,
      
      echarts_serie_label_fontSize:12,
      
      echarts_serie_roseType:false
      
    }
  },
  computed: {
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
    datasets: {
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
    
    handle_echarts_title_textStyle_color(){
      this.option.title.textStyle.color = this.echarts_title_textStyle_color
      myChart.setOption(this.option)
    },


    
    handle_echarts_legend_position(value){
      switch(value){
        case '0':
          this.option.legend.left = 'left'
          this.option.legend.top = 'top'
          break;
        case '2':
          this.option.legend.left = 'right'
          this.option.legend.top = 'top'
          break;
        case '3':
          this.option.legend.left = 'right'
          this.option.legend.top = 'middle'
          break;
        case '4':
          this.option.legend.left = 'right'
          this.option.legend.top = 'bottom'
          break;
        case '5':
          this.option.legend.left = 'center'
          this.option.legend.top = 'bottom'
          break;
        case '6':
          this.option.legend.left = 'left'
          this.option.legend.top = 'bottom'
          break;
        case '7':
          this.option.legend.left = 'left'
          this.option.legend.top = 'middle'
          break;
        default:
          this.option.legend.left = 'center'
          this.option.legend.top = 'top'
          break;            
      }
      myChart.setOption(this.option)
        
    },
    
    handle_echarts_legend_orient(value){
      if(value == '0'){
        this.option.legend.orient = 'horizontal'
      }else{
        this.option.legend.orient = 'vertical'
      }
      myChart.setOption(this.option)
    },
    
    handle_echarts_legend_textStyle_fontSize(value){
      this.option.legend.textStyle.fontSize = value
      myChart.setOption(this.option)
    },


    
    handle_echarts_serie_radius_interior(value){
      this.option.series[0].radius[0] = value
      myChart.setOption(this.option)
    },
    
    handle_echarts_serie_radius_exterior(value){
      this.option.series[0].radius[1] = value
      myChart.setOption(this.option)
    },    
    
    handle_echarts_serie_center_left(value){
      this.option.series[0].center[0] = value + '%'
      myChart.setOption(this.option)
    },  
    
    handle_echarts_serie_center_top(value){
      this.option.series[0].center[1] = value + '%'
      myChart.setOption(this.option)
    },   
    
    handle_echarts_serie_itemStyle_borderRadius(value){
      this.option.series[0].itemStyle.borderRadius = value
      myChart.setOption(this.option)
    },
    
    handle_echarts_serie_label_fontSize(value){
      this.option.series[0].label.fontSize = value
      myChart.setOption(this.option)
    },
    
    handle_echarts_serie_roseType(value){
      this.option.series[0].roseType = value
      myChart.setOption(this.option)
    },

    
    handleNightingaleChange(){
      if(this.Nightingale){
        this.option.series[0].roseType = true
      }else{
        this.option.series[0].roseType = false
      }
      myChart.setOption(this.option)
    },

    async getDataSet(echartsFormList, startIndex, limit) {
      this.datasets = [];
      this.dataSourceSizeList = [];
      let length = echartsFormList.length;
      for(let i = 0; i < length; i++) {
        await this.fetchDataSource(echartsFormList[i].dataSourceName, startIndex, limit);
        this.fetchDataSourceSize(echartsFormList[i].dataSourceName);
      }
    },

    updateDataSet() {
      this.getDataSet(this.echartsFormList, this.startIndex, this.endIndex - this.startIndex + 1)
      this.option.dataset = this.datasets
      this.refreshEcharts
    },

    fetchDataSource(dataSourceName, startIndex, limit) {
      var dataSet = {
        source: []
      }
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
            
            dataSet.source = JSON.parse(response.data.result).sourceData
            this.datasets.push(dataSet)
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

    initSeries() {
      this.series = [];
      let echartsFormList = this.echartsFormList;
      let echartsFormListSize = echartsFormList.length;
      for(let i = 0; i < echartsFormListSize; i++) {
        let echartsForm = echartsFormList[i];
        let yOption = echartsForm.yOptions
        var serie = {
          name: echartsForm.dataSourceName + '-' + yOption,
          datasetIndex: 0,
          type: 'pie',
          radius: [0,250],
          center:['50%','50%'], 
          roseType: false,    
          itemStyle:{
            borderRadius:0
          },                         
          label:{
            fontSize:12
          },    
          encode: {
            itemName: echartsForm.xOption,
            value: echartsForm.yOption
          },            
        };
        this.series.push(serie)
      }
    },  

    initOption() {
      this.option.dataset = this.datasets
      this.option.series = this.series
    }, 

    refreshEcharts() {
      myChart.setOption(this.option)
    }       
  },
  created() {
    
    this.echartsFormList = this.$store.state.echartsFormList;  

    
    this.getDataSet(this.echartsFormList, 0, 10);

    
    this.initSeries();    

    
    this.initOption(); 
  },
  mounted() {    
    myChart = echarts.init(document.getElementById('pie'))
  },
};