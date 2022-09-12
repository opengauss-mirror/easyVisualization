import * as echarts from 'echarts'
import axios from 'axios'
var myChart
export const radar = {
  data(){
    return {
      
      echartsFormList: [],
      
      echartsType: '',
      
      datas:[],  
      
      option: {
        title: {
          text: this.$t('title'),
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
        tooltip: {
            
            confine: true,
            enterable: true, 
        },        
        legend: {
          data: [],
          orient:'vertical',
          left:'right',
          top:'top',
          textStyle:{
            fontSize:12
          }        
        },
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
        radar: {
          radius:'75%',
          center:['50%','50%'],
          axisName:{
            fontSize:12
          },
          indicator: []
        },
        series: [{
          name: '',
          type: "radar",
          data: [],
          label: {
            show: true
          },                 
        }]     
      },      

      dataSourceSizeList:[],
      
      startIndex: 0,
      endIndex:0,      


      
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
      

      
      echarts_radar_radius:75,
      
      echarts_radar_center_left:50,
      
      echarts_radar_center_top:50,
      
      echarts_radar_axisName_fontSize:12




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
    datas: {
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

    
    handle_echarts_radar_radius(value){
      this.option.radar.radius = value + '%'
      myChart.setOption(this.option)
    },

    
    handle_echarts_radar_center_left(value){
      this.option.radar.center[0] = value + '%'
      myChart.setOption(this.option)
    },

    
    handle_echarts_radar_center_top(value){
      this.option.radar.center[1] = value + '%'
      myChart.setOption(this.option)
    },

    
    handle_echarts_radar_axisName_fontSize(value){
      this.option.radar.axisName.fontSize = value
      myChart.setOption(this.option)
    },




    fetchDataSource(dataSourceName, startIndex, limit) {
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
            this.getDatas(JSON.parse(response.data.result).sourceData);
            this.fetchDataSourceSize(dataSourceName);
          },            
          error => {
      })       
    },

    /**
     * Trans source data to radar's datas and refresh option.legend.data
     * @param {sourceData} sourceData 
     */
    getDatas(sourceData) {
      var datas = []
      var legend_data = []
      let name = this.echartsFormList[0].xOption;
      let yOptions = this.echartsFormList[0].yOptions;
      for(let i = 0; i < sourceData.length; i++) {
        let data = {
          name: sourceData[i][name].toString(),
          value:[]
        };       
        for (let j = 0; j < yOptions.length; j++) {
          data.value.push(sourceData[i][yOptions[j]]);
        }
        legend_data.push(data.name.toString())         
        datas.push(data);
      }
      this.datas = datas;
      this.option.legend.data = legend_data
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

    initIndicator() {
      this.option.radar.indicator = []
      for(let i = 0; i < this.echartsFormList[0].yOptions.length; i++) {
        let indicator = {
          name: this.echartsFormList[0].yOptions[i].toString()
        }
        this.option.radar.indicator.push(indicator)
      }
    },

    initSeries() {
      this.option.series[0].name = this.echartsFormList[0].xOption;
      this.option.series[0].data = this.datas;
    },    

    refreshEcharts() {
      this.initSeries()
      myChart.setOption(this.option)
    },       

    updateDatas() {
      this.fetchDataSource(this.echartsFormList[0].dataSourceName, this.startIndex, this.endIndex - this.startIndex + 1)
    }

  },
  created() {
    
    this.echartsFormList = this.$store.state.echartsFormList;  
    this.fetchDataSource(this.echartsFormList[0].dataSourceName, 0 ,1);
    this.initIndicator();
  },
  mounted() {
    myChart = echarts.init(document.getElementById('radar')) 
  }
}