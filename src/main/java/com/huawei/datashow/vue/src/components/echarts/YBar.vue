<template>
  <el-container id="echarts_container">
    <el-aside width="18%">
      <el-scrollbar>
        <el-alert id="alert-data-source-size":title="'Total: ' + dataSourceSizeMax"  type="success" :closable="false"></el-alert>       
        <el-alert title="选择数据范围" type="info" :closable="false"       ></el-alert>       
        <el-form label-width="auto"> 
          <el-form-item :label="$t('YBar.el-form-item.data_start_index')"  >
            <el-input-number v-model="startIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
          </el-form-item>    
          <el-form-item :label="$t('YBar.el-form-item.data_end_index')"  >
            <el-input-number v-model="endIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
          </el-form-item>                          
        </el-form> 

        <el-alert :title="$t('YBar.el-alert.title_set')" type="info" :closable="false"       ></el-alert>
        <el-form label-width="auto">
          <el-form-item
            :label="$t('YBar.el-form-item.whether_show_title')"
             
            >
            <el-select 
              v-model="echarts_title_show" 
              size="mini"
                
              @change="handle_echarts_title_show"
              >
                <el-option
                  :key="true"
                  :label="$t('YBar.el-option.show')"
                  :value="true"
                  >
                </el-option>
                <el-option
                  :key="false"
                  :label="$t('YBar.el-option.hide')"
                  :value="false"
                  >
                </el-option>
              </el-select>
          </el-form-item>  
    
          <el-form-item
            :label="$t('YBar.el-form-item.title_position')"
             
            >
            <el-select 
              v-model="echarts_title_position" 
              size="mini"
                
              @change="handle_echarts_title_position"
              :disabled="this.echarts_title_show==false"
              >
              <el-option key="0" :label="$t('YBar.el-option.top_left')" value="0"></el-option>
              <el-option key="1" :label="$t('YBar.el-option.top')" value="1"></el-option>
              <el-option key="2" :label="$t('YBar.el-option.top_right')" value="2"></el-option>
              <el-option key="3" :label="$t('YBar.el-option.right')"   value="3"></el-option>
              <el-option key="4" :label="$t('YBar.el-option.bottom_right')" value="4"></el-option>
              <el-option key="5" :label="$t('YBar.el-option.bottom')" value="5"></el-option>
              <el-option key="6" :label="$t('YBar.el-option.bottom_left')" value="6"></el-option>
              <el-option key="7" :label="$t('YBar.el-option.left')"   value="7"></el-option>
            </el-select>
          </el-form-item>       

          <el-form-item
            :label="$t('YBar.el-form-item.chart_title')">
            <el-input
              v-model="echarts_title_text"
              size="mini" 
              @change="handle_echarts_title_text"
              :disabled="this.echarts_title_show==false">
            </el-input>
          </el-form-item> 
          
          <el-form-item
            :label="$t('YBar.el-form-item.chart_subtitle')"
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
            :label="$t('YBar.el-form-item.title_size')"
             
            >
            <el-input-number v-model="echarts_title_textStyle_fontSize" @change="handle_echarts_title_textStyle_fontSize" :min="10" :max="50" size="mini" :disabled="this.echarts_title_show==false"></el-input-number>
          </el-form-item>      

          <el-form-item
            :label="$t('YBar.el-form-item.subtitle_size')"
             
            >
            <el-input-number v-model="echarts_title_subtextStyle_fontSize" @change="handle_echarts_title_subtextStyle_fontSize" :min="7" :max="33" size="mini" :disabled="this.echarts_title_subtext==''"></el-input-number>
          </el-form-item>                     

          <el-form-item
            :label="$t('YBar.el-form-item.change_title_color')"
             
            >
            <el-color-picker v-model="echarts_title_textStyle_color" size="small"   @change="handle_echarts_title_textStyle_color" :disabled="this.echarts_title_show==false"></el-color-picker>
          </el-form-item> 
        </el-form> 

        <el-alert :title="$t('YBar.el-alert.legend_setting')" type="info" :closable="false"       ></el-alert>       
        <el-form label-width="auto"> 
          <!-- 必须加这个v-if，否则这个部分先于mounted加载，会出错！-->
          <el-form-item
            :label="$t('YBar.el-form-item.change_legend_name')"
             
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

          <!-- 必须加这个v-if，否则这个部分先于mounted加载，会出错！-->
          <el-form-item
            :label="$t('YBar.el-form-item.change_legend_color')"
             
            :v-if="this.series.length != 0"
            >
            <el-color-picker v-model="serie.itemStyle.color" size="small"   @change="handle_echarts_series_color" v-for="serie in this.series"></el-color-picker>
          </el-form-item>    

          <el-form-item
            :label="$t('YBar.el-form-item.legend_display_position')"
             
            >
            <el-select 
              v-model="echarts_legend_position" 
              size="mini"
                
              @change="handle_echarts_legend_position"
              >
              <el-option key="0" :label="$t('YBar.el-option.top_left')" value="0"></el-option>
              <el-option key="1" :label="$t('YBar.el-option.top')" value="1"></el-option>
              <el-option key="2" :label="$t('YBar.el-option.top_right')" value="2"></el-option>
              <el-option key="3" :label="$t('YBar.el-option.right')"   value="3"></el-option>
              <el-option key="4" :label="$t('YBar.el-option.bottom_right')" value="4"></el-option>
              <el-option key="5" :label="$t('YBar.el-option.bottom')" value="5"></el-option>
              <el-option key="6" :label="$t('YBar.el-option.bottom_left')" value="6"></el-option>
              <el-option key="7" :label="$t('YBar.el-option.left')"   value="7"></el-option>
            </el-select>
          </el-form-item> 

          <el-form-item
            :label="$t('YBar.el-form-item.legend_layout')"
             
            >
            <el-select 
              v-model="echarts_legend_orient" 
              size="mini"
                
              @change="handle_echarts_legend_orient"
              >
              <el-option key="0" :label="$t('YBar.el-option.horizontal_arrangement')" value="0"></el-option>
              <el-option key="1" :label="$t('YBar.el-option.vertical_arrangement')" value="1"></el-option>
              </el-select>
          </el-form-item>     


          <el-form-item
            :label="$t('YBar.el-form-item.legend_size')"
             
            >
            <el-input-number v-model="echarts_legend_textStyle_fontSize" @change="handle_echarts_legend_textStyle_fontSize" :min="7" :max="33" size="mini"></el-input-number>
          </el-form-item>       


            
        </el-form>
        
        <el-alert :title="$t('YBar.el-alert.Drawing_grid_settings')" type="info" :closable="false"       ></el-alert>       
        <el-form label-width="auto"> 
          <el-form-item :label="$t('YBar.el-form-item.grid_left_distance')"  >
            <el-input-number v-model="echarts_grid_left" @change="handle_echarts_grid_left" :min="0" :max="40" size="mini"></el-input-number>
          </el-form-item>     
          
          <el-form-item :label="$t('YBar.el-form-item.grid_right_distance')"  >
            <el-input-number v-model="echarts_grid_right" @change="handle_echarts_grid_right" :min="0" :max="40" size="mini"></el-input-number>
          </el-form-item>  

          <el-form-item :label="$t('YBar.el-form-item.grid_top_distance')"  >
            <el-input-number v-model="echarts_grid_top" @change="handle_echarts_grid_top" :min="0" :max="200" size="mini"></el-input-number>
          </el-form-item>

          <el-form-item :label="$t('YBar.el-form-item.grid_bottom_distance')"  >
            <el-input-number v-model="echarts_grid_bottom" @change="handle_echarts_grid_bottom" :min="0" :max="200" size="mini"></el-input-number>
          </el-form-item>               

        </el-form>      

        <el-alert :title="$t('YBar.el-alert.Horizontal_axis_settings')" type="info" :closable="false"       ></el-alert>  
        <el-form label-width="auto">
          <el-form-item :label="$t('YBar.el-form-item.Horizontal_font_size')"  >
            <el-input-number v-model="echarts_xAxis_axisLabel_fontSize" @change="handle_echarts_xAxis_axisLabel_fontSize" :min="7" :max="33" size="mini"></el-input-number>
          </el-form-item>   

          <el-form-item :label="$t('YBar.el-form-item.Tab_spacing')"  >
            <el-input-number v-model="echarts_xAxis_axisLabel_interval" @change="handle_echarts_xAxis_axisLabel_interval" :min="0" :max="3" size="mini"></el-input-number>
          </el-form-item>     

          <el-form-item :label="$t('YBar.el-form-item.Label_rotation_angle')"  >
            <el-input-number v-model="echarts_xAxis_axisLabel_rotate" @change="handle_echarts_xAxis_axisLabel_rotate" :min="-90" :max="90" size="mini"></el-input-number>
          </el-form-item>              
        </el-form>

        <el-alert :title="$t('YBar.el-alert.Vertical_axis_settings')" type="info" :closable="false"       ></el-alert> 
        <el-form label-width="auto">
          <el-form-item :label="$t('YBar.el-form-item.Vertical_font_size')"  >
            <el-input-number v-model="echarts_yAxis_axisLabel_fontSize" @change="handle_echarts_yAxis_axisLabel_fontSize" :min="7" :max="33" size="mini"></el-input-number>
          </el-form-item>                
        </el-form>

        <el-alert :title="$t('YBar.el-alert.other_settings')" type="info" :closable="false"       ></el-alert>   
        <el-form label-width="auto"> 
          <el-form-item :label="$t('YBar.el-form-item.Bottom_zoom_controls')"  >
            <el-select v-model="echarts_data_zoom_is_active" size="mini"    @change="handle_echarts_data_zoom_is_active">
              <el-option key="1" :label="$t('YBar.el-option.display')" :value="true"></el-option>
              <el-option key="0" :label="$t('YBar.el-option.not_display')" :value="false"></el-option>
            </el-select>        
          </el-form-item>     

          <el-form-item :label="$t('YBar.el-form-item.Graphic_text_label')"  >
            <el-select v-model="echarts_series_label_show" size="mini"    @change="handle_echarts_series_label_show">
              <el-option key="1" :label="$t('YBar.el-option.display')" :value="true"></el-option>
              <el-option key="0" :label="$t('YBar.el-option.not_display')" :value="false"></el-option>
            </el-select>        
          </el-form-item>         
        </el-form> 
      </el-scrollbar>
    </el-aside>

    <el-main>
        <div id="bar" :style="{width: '99.9%', height:'99.9%', background:'white'}"></div>
    </el-main>
  </el-container>
</template>


<script>
import { ybar } from '@/components/echarts/mixin/ybar/ybar'
export default {
  props: [],
  mixins: [ybar],
}
</script>

<style scoped>
  @import '@/assets/css/echarts/echarts.css';
</style>
