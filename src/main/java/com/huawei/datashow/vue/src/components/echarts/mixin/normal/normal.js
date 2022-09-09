import * as echarts from 'echarts'
import axios from 'axios'
var myChart 
export const normal = {
    data() {
        return {
          echartsFormList: [],
          datasets: [],   
          series: [],
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
            legend: {
              orient:'horizontal',
              left:'center',
              top:'top',
              textStyle:{
                fontSize:12
              }
            },
            grid: {
              left:'10%',
              right:'10%',
              top:60,
              bottom:70
            },
            dataZoom: [
            {
                id: 'dataZoomX',
                type: 'slider',
                show: true,
                xAxisIndex: [0],
                filterMode: 'filter'
            }],
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
            tooltip: {
                trigger: 'axis',
                axisPointer: { type: 'cross' }
            },        
            boundaryGap:false,
            dataset:[],
            xAxis: {
                name: '',
                nameTextStyle: {
                    color:'',
                    fontSize:12
                },
                type: 'category',
                axisLabel:{
                    fontSize:12,   
                    interval:0,
                    rotate:0
                }
            },
            yAxis: {
                name: '',
                nameTextStyle: {
                    color:'',
                    fontSize:12
                },                
                axisLabel:{
                    fontSize:12,
                },
            },
            series:[]
          },
          dataSourceSizeList:[],
          startIndex: 0,
          endIndex:10,
          
          origin_option:{},
          
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
    
          
          echarts_grid_left:10,
          
          echarts_grid_right:10,
          
          echarts_grid_top:60,
          
          echarts_grid_bottom:70,
    
          echarts_xAxis_name:'',

          echarts_xAxis_nameTextStyle_color:'#000000',

          echarts_xAxis_nameTextStyle_fontSize:12,

          echarts_xAxis_axisLabel_fontSize:12,
          
          echarts_xAxis_axisLabel_interval:0,
          
          echarts_xAxis_axisLabel_rotate:0,

          echarts_yAxis_max:0,

          echarts_yAxis_min:0,

          echarts_yAxis_name:'',

          echarts_yAxis_nameTextStyle_color:'#000000',

          echarts_yAxis_nameTextStyle_fontSize:12,          
          
          echarts_yAxis_axisLabel_fontSize:12,
    
          
          echarts_data_zoom_is_active:true,
          
          echarts_series_label_show:false,            
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

    
    handle_echarts_series_name(){
        this.option.series = this.series
        myChart.setOption(this.option)
    },
    
    handle_echarts_series_color(){
        this.option.series = this.series
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


    
    handle_echarts_grid_left(value){
        this.option.grid.left = value + '%'
        myChart.setOption(this.option)
    },
    
    handle_echarts_grid_right(value){
        this.option.grid.right = value + '%'
        myChart.setOption(this.option)
    },      
    
    handle_echarts_grid_top(value){
        this.option.grid.top = value
        myChart.setOption(this.option)
    },  
    
    handle_echarts_grid_bottom(value){
        this.option.grid.bottom = value
        myChart.setOption(this.option)
    },     

    handle_echarts_xAxis_name(value){
        this.option.xAxis.name = value
        myChart.setOption(this.option)
    },

    handle_echarts_xAxis_nameTextStyle_color(){
        this.option.xAxis.nameTextStyle.color = this.echarts_xAxis_nameTextStyle_color
        myChart.setOption(this.option)
    },

    handle_echarts_xAxis_nameTextStyle_fontSize(value){
        this.option.xAxis.nameTextStyle.fontSize = value
        myChart.setOption(this.option)
    },
    
    
    handle_echarts_xAxis_axisLabel_fontSize(value){
        this.option.xAxis.axisLabel.fontSize = value
        myChart.setOption(this.option)
    },
    
    handle_echarts_xAxis_axisLabel_interval(value){
        this.option.xAxis.axisLabel.interval = value
        myChart.setOption(this.option)
    },
    
    handle_echarts_xAxis_axisLabel_rotate(value){
        this.option.xAxis.axisLabel.rotate = value
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_max(value){
        this.option.yAxis.max = value
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_min(value){
        this.option.yAxis.min = value
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_name(value){
        this.option.yAxis.name = value
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_nameTextStyle_color(){
        this.option.yAxis.nameTextStyle.color = this.echarts_yAxis_nameTextStyle_color
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_nameTextStyle_fontSize(value){
        this.option.yAxis.nameTextStyle.fontSize = value
        myChart.setOption(this.option)
    },

    handle_echarts_yAxis_axisLabel_fontSize(value){
        this.option.yAxis.axisLabel.fontSize = value
        myChart.setOption(this.option)
    },
    
    handle_echarts_data_zoom_is_active(value){
        this.option.dataZoom[0].show = value
        myChart.setOption(this.option) 
    },       
    
    handle_echarts_series_label_show(value){
        for(let i = 0; i < this.series.length; i++){
        this.series[i].label.show = value
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
        myChart = echarts.init(document.getElementById('normal'));   
    },    
}