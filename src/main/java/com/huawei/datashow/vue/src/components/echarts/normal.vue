<template>
    <el-container id="echarts_container">
      <el-aside width="18%">
        <el-scrollbar >
          <el-alert id="alert-data-source-size" :title="'Total: ' + dataSourceSizeMax" type="success" :closable="false" ></el-alert>       
          <el-alert :title="$t('Radar.el-form-item.data_range')" type="info" :closable="false"></el-alert>       
          <el-form label-width="auto"> 
            <el-form-item :label="$t('Radar.el-form-item.data_start_index')">
              <el-input-number v-model="startIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini" width="10px"></el-input-number>
            </el-form-item>    
            <el-form-item :label="$t('Radar.el-form-item.data_end_index')">
              <el-input-number v-model="endIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
            </el-form-item>                          
          </el-form> 
  
          <el-alert :title="$t('Radar.el-alert.title_set')" type="info" :closable="false"></el-alert>
          <el-form label-width="auto">
            <el-form-item
              :label="$t('Radar.el-form-item.whether_show_title')"
              >
              <el-select 
                v-model="echarts_title_show" 
                size="mini"
                @change="handle_echarts_title_show"
                >
                  <el-option
                    :key="true"
                    :label="$t('Radar.el-option.show')"
                    :value="true"
                    >
                  </el-option>
                  <el-option
                    :key="false"
                    :label="$t('Radar.el-option.hide')"
                    :value="false"
                    >
                  </el-option>
                </el-select>
            </el-form-item>  
      
            <el-form-item
              :label="$t('Radar.el-form-item.title_position')"
              >
              <el-select 
                v-model="echarts_title_position" 
                size="mini"
                @change="handle_echarts_title_position"
                :disabled="this.echarts_title_show==false"
                >
                  <el-option key="0" :label="$t('Radar.el-option.top_left')" value="0"></el-option>
                  <el-option key="1" :label="$t('Radar.el-option.top')" value="1"></el-option>
                  <el-option key="2" :label="$t('Radar.el-option.top_right')" value="2"></el-option>
                  <el-option key="3" :label="$t('Radar.el-option.right')"   value="3"></el-option>
                  <el-option key="4" :label="$t('Radar.el-option.bottom_right')" value="4"></el-option>
                  <el-option key="5" :label="$t('Radar.el-option.bottom')" value="5"></el-option>
                  <el-option key="6" :label="$t('Radar.el-option.bottom_left')" value="6"></el-option>
                  <el-option key="7" :label="$t('Radar.el-option.left')"   value="7"></el-option>
                </el-select>
            </el-form-item>       
  
            <el-form-item
             :label="$t('Radar.el-form-item.chart_title')"
              >
              <el-input
                v-model="echarts_title_text"
                size="mini"
                @change="handle_echarts_title_text"
                :disabled="this.echarts_title_show==false"
              >
              </el-input>
            </el-form-item> 
            
            <el-form-item
             :label="$t('Radar.el-form-item.chart_subtitle')"
              >
              <el-input
                v-model="echarts_title_subtext"
                size="mini"
                @change="handle_echarts_title_subtext"
                :disabled="this.echarts_title_show==false"
              >
              </el-input>
            </el-form-item>   
  
            <el-form-item
              :label="$t('Radar.el-form-item.title_size')"
              >
              <el-input-number v-model="echarts_title_textStyle_fontSize" @change="handle_echarts_title_textStyle_fontSize" :min="10" :max="50" size="mini" :disabled="this.echarts_title_show==false"></el-input-number>
            </el-form-item>      
  
            <el-form-item
              :label="$t('Radar.el-form-item.subtitle_size')"
              >
              <el-input-number v-model="echarts_title_subtextStyle_fontSize" @change="handle_echarts_title_subtextStyle_fontSize" :min="7" :max="33" size="mini" :disabled="this.echarts_title_subtext==''"></el-input-number>
            </el-form-item>                     
  
            <el-form-item
              :label="$t('Radar.el-form-item.change_title_color')"
              >
              <el-color-picker v-model="echarts_title_textStyle_color" size="small" @change="handle_echarts_title_textStyle_color" :disabled="this.echarts_title_show==false"></el-color-picker>
            </el-form-item> 
          </el-form> 
  
          <el-alert :title="$t('Radar.el-alert.legend_setting')" type="info" :closable="false"></el-alert>       
          <el-form label-width="auto"> 
            <!-- must add this v-if-->
            <el-form-item
              :label="$t('normal.item.item_legend_name')"
              :v-if="this.series.length != 0"
              >
              <el-input
                v-for="serie in this.series"
                v-model="serie.name"
                size="mini"
                @change="handle_echarts_series_name"
              >
              </el-input>
            </el-form-item>    
  
            <!-- must add this v-if-->
            <el-form-item
              :label="$t('normal.item.item_legend_color')"
              :v-if="this.series.length != 0"
              >
              <el-color-picker v-model="serie.itemStyle.color" size="small" @change="handle_echarts_series_color" v-for="serie in this.series"></el-color-picker>
            </el-form-item>    
  
            <el-form-item
              :label="$t('Radar.el-form-item.legend_display_position')"
              >
              <el-select 
                v-model="echarts_legend_position"
                size="mini"
                @change="handle_echarts_legend_position"
                >
                <el-option key="0" :label="$t('Radar.el-option.top_left')" value="0"></el-option>
                <el-option key="1" :label="$t('Radar.el-option.top')" value="1"></el-option>
                <el-option key="2" :label="$t('Radar.el-option.top_right')" value="2"></el-option>
                <el-option key="3" :label="$t('Radar.el-option.right')"   value="3"></el-option>
                <el-option key="4" :label="$t('Radar.el-option.bottom_right')" value="4"></el-option>
                <el-option key="5" :label="$t('Radar.el-option.bottom')" value="5"></el-option>
                <el-option key="6" :label="$t('Radar.el-option.bottom_left')" value="6"></el-option>
                <el-option key="7" :label="$t('Radar.el-option.left')"   value="7"></el-option>
              </el-select>
            </el-form-item> 
  
            <el-form-item
              :label="$t('Radar.el-form-item.legend_layout')"
              >
              <el-select 
                v-model="echarts_legend_orient"
                size="mini"
                @change="handle_echarts_legend_orient"
                >
                  <el-option key="0" :label="$t('Radar.el-option.horizontal_arrangement')" value="0"></el-option>
                  <el-option key="1" :label="$t('Radar.el-option.vertical_arrangement')" value="1"></el-option>
                </el-select>
            </el-form-item>     
  
  
            <el-form-item
              :label="$t('Radar.el-form-item.legend_size')"
              >
              <el-input-number v-model="echarts_legend_textStyle_fontSize" @change="handle_echarts_legend_textStyle_fontSize" :min="7" :max="33" size="mini"></el-input-number>
            </el-form-item>       
  
  
              
          </el-form>
          
          <el-alert :title="$t('normal.alert.axis_position')" type="info" :closable="false"></el-alert>       
          <el-form label-width="auto"> 
            <el-form-item :label="$t('normal.item.axis_left')" >
              <el-input-number v-model="echarts_grid_left" @change="handle_echarts_grid_left" :min="0" :max="40" size="mini"></el-input-number>
            </el-form-item>     
            
            <el-form-item :label="$t('normal.item.axis_right')" >
              <el-input-number v-model="echarts_grid_right" @change="handle_echarts_grid_right" :min="0" :max="40" size="mini"></el-input-number>
            </el-form-item>  
  
            <el-form-item :label="$t('normal.item.axis_top')" >
              <el-input-number v-model="echarts_grid_top" @change="handle_echarts_grid_top" :min="0" :max="200" size="mini"></el-input-number>
            </el-form-item>
  
            <el-form-item :label="$t('normal.item.axis_bottom')" >
              <el-input-number v-model="echarts_grid_bottom" @change="handle_echarts_grid_bottom" :min="0" :max="200" size="mini"></el-input-number>
            </el-form-item>               
  
          </el-form>      
  
          <el-alert :title="$t('normal.alert.xAxis_config')" type="info" :closable="false"></el-alert>  
          <el-form label-width="auto">
            <el-form-item :label="$t('normal.item.xAxis_font_size')">
              <el-input-number v-model="echarts_xAxis_axisLabel_fontSize" @change="handle_echarts_xAxis_axisLabel_fontSize" :min="7" :max="33" size="mini"></el-input-number>
            </el-form-item>   
  
            <el-form-item :label="$t('normal.item.xAxis_label_distance')" >
              <el-input-number v-model="echarts_xAxis_axisLabel_interval" @change="handle_echarts_xAxis_axisLabel_interval" :min="0" :max="3" size="mini"></el-input-number>
            </el-form-item>     
  
            <el-form-item :label="$t('normal.item.xAxis_label_angel')" >
              <el-input-number v-model="echarts_xAxis_axisLabel_rotate" @change="handle_echarts_xAxis_axisLabel_rotate" :min="-90" :max="90" size="mini"></el-input-number>
            </el-form-item>              
          </el-form>
  
          <el-alert :title="$t('normal.alert.yAxis_config')" type="info" :closable="false"></el-alert> 
          <el-form label-width="auto">
            <el-form-item :label="$t('normal.item.yAxis_font_size')" >
              <el-input-number v-model="echarts_yAxis_axisLabel_fontSize" @change="handle_echarts_yAxis_axisLabel_fontSize" :min="7" :max="33" size="mini"></el-input-number>
            </el-form-item>                
          </el-form>
  
          <el-alert :title="$t('normal.alert.other_config')" type="info" :closable="false"></el-alert>   
          <el-form label-width="auto"> 
            <el-form-item :label="$t('normal.item.data_zoom')" >
              <el-select v-model="echarts_data_zoom_is_active" size="mini"  @change="handle_echarts_data_zoom_is_active">
                <el-option key="1" :label="$t('show')" :value="true"></el-option>
                <el-option key="0" :label="$t('hidden')" :value="false"></el-option>
              </el-select>        
            </el-form-item>     
  
            <el-form-item :label="$t('normal.item.echarts_text_label')" >
              <el-select v-model="echarts_series_label_show" size="mini" @change="handle_echarts_series_label_show">
                <el-option key="1" :label="$t('show')" :value="true"></el-option>
                <el-option key="0" :label="$t('hidden')" :value="false"></el-option>
              </el-select>        
            </el-form-item>         
          </el-form> 
        </el-scrollbar>
      </el-aside>
  
      <el-main>
          <div id="normal" :style="{width: '99.9%', height:'99.9%', background:'white'}"></div>
      </el-main>
    </el-container>
  </template>
  
  
  <script>
  import { normal } from './mixin/normal/normal'
  export default {
    props:[ 'echartsType' ],
    mixins: [normal],
    methods: {
      initSeries() {
          this.series = [];
          let echartsFormList = this.echartsFormList;
          let echartsFormListSize = echartsFormList.length;
          if (this.echartsType != 'combination') {
            for(let i = 0; i < echartsFormListSize; i++) {
              let echartsForm = echartsFormList[i];
              let yOptionsSize = echartsForm.yOptions.length;
              for(let j = 0; j < yOptionsSize; j++) {
                let yOption = echartsForm.yOptions[j]
                var serie = {}
                if (this.echartsType == 'area') {
                    serie = {
                    name: '',
                    datasetIndex: 0,
                    type: 'line',
                    encode: {
                        x: '',
                        y: ''
                    },           
                    itemStyle: {
                        color : 'auto'
                    },
                    areaStyle:{}, 
                    label:{
                        show:false,
                        position:'top'
                    }        
                  }
                } else {
                    serie = {
                    name: '',
                    datasetIndex: 0,
                    type: this.echartsType,
                    encode: {
                        x: '',
                        y: ''
                    },           
                    itemStyle: {
                        color : 'auto'
                    },
                    label:{
                        show:false,
                        position:'top'
                    }        
                  };                
                }
                serie.datasetIndex = i;
                serie.encode.x = echartsForm.xOption
                serie.name = echartsForm.dataSourceName + "  " + yOption
                serie.encode.y = yOption
                this.series.push(serie)
              }
            }            
          } else {
            for(let i = 0; i < echartsFormListSize; i++) {
              let echartsForm = echartsFormList[i];

              let bar_yOptionsSize = echartsForm.yOptions.bar_yOptions.length;
              for(let j = 0; j < bar_yOptionsSize; j++) {
                let bar_yOption = echartsForm.yOptions.bar_yOptions[j];
                var serie = {
                  name: '',
                  datasetIndex: 0,
                  type: 'bar',
                  encode: {
                    x: '',
                    y: ''
                  },
                  itemStyle: {
                    color : 'auto'
                  },
                  label:{
                    show:false,
                    position:'top'
                  }        
                };
                serie.datasetIndex = i;
                serie.encode.x = echartsForm.xOption
                serie.name = echartsForm.dataSourceName + "  " + bar_yOption
                serie.encode.y = bar_yOption
                this.series.push(serie)          
              }
              
              let line_yOptionsSize = echartsForm.yOptions.line_yOptions.length;
              for(let j = 0; j < line_yOptionsSize; j++) {
                let line_yOption = echartsForm.yOptions.line_yOptions[j]
                var serie = {
                  name: '',
                  datasetIndex: 0,
                  type: 'line',
                  encode: {
                    x: '',
                    y: ''
                  },
                  itemStyle: {
                    color : 'auto'
                  },
                  label:{
                    show:false,
                    position:'top'
                  }        
                };
                serie.datasetIndex = i;
                serie.encode.x = echartsForm.xOption
                serie.name = echartsForm.dataSourceName + "  " + line_yOption
                serie.encode.y = line_yOption
                this.series.push(serie)        
              }
            }            
          }

      }, 
    }
  }
  </script>
  

<style scoped>
  @import '@/assets/css/echarts/echarts.css';
</style>