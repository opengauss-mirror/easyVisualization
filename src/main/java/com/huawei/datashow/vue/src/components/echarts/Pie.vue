<template>
  <el-container id="echarts_container">
    <el-aside width="300px">
      <el-scrollbar>
        <el-alert id="alert-data-source-size" :title="'Total: ' + dataSourceSizeMax" type="success" :closable="false" ></el-alert>       
        <el-alert :title="$t('Radar.el-form-item.data_range')" type="info" :closable="false"></el-alert>       
        <el-form label-width="auto"> 
          <el-form-item :label="$t('Pie.el-form-item.data_start_index')">
            <el-input-number v-model="startIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
          </el-form-item>    
          <el-form-item :label="$t('Pie.el-form-item.data_end_index')" >
            <el-input-number v-model="endIndex" @change="updateDataSet" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
          </el-form-item>                          
        </el-form>     

        <el-alert :title="$t('Pie.el-alert.title_set')" type="info" :closable="false"></el-alert>
        <el-form label-width="auto">
          <el-form-item
            :label="$t('Pie.el-form-item.whether_show_title')"
            >
            <el-select 
              v-model="echarts_title_show" 
              size="mini"
              @change="handle_echarts_title_show"
              >
                <el-option
                  :key="true"
                  :label="$t('Pie.el-option.show')"
                  :value="true"
                  >
                </el-option>
                <el-option
                  :key="false"
                  :label="$t('Pie.el-option.hide')"
                  :value="false"
                  >
                </el-option>
              </el-select>
          </el-form-item>  
    
          <el-form-item
            :label="$t('Pie.el-form-item.title_position')"
            >
            <el-select 
              v-model="echarts_title_position" 
              size="mini"
              @change="handle_echarts_title_position"
              :disabled="this.echarts_title_show==false"
              >
              <el-option key="0" :label="$t('Pie.el-option.top_left')" value="0"></el-option>
              <el-option key="1" :label="$t('Pie.el-option.top')" value="1"></el-option>
              <el-option key="2" :label="$t('Pie.el-option.top_right')" value="2"></el-option>
              <el-option key="3" :label="$t('Pie.el-option.right')"   value="3"></el-option>
              <el-option key="4" :label="$t('Pie.el-option.bottom_right')" value="4"></el-option>
              <el-option key="5" :label="$t('Pie.el-option.bottom')" value="5"></el-option>
              <el-option key="6" :label="$t('Pie.el-option.bottom_left')" value="6"></el-option>
              <el-option key="7" :label="$t('Pie.el-option.left')"   value="7"></el-option>
              </el-select>
          </el-form-item>       

          <el-form-item
            :label="$t('Pie.el-form-item.chart_title')"
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
            :label="$t('Pie.el-form-item.chart_subtitle')"
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
            :label="$t('Pie.el-form-item.title_size')"
            >
            <el-input-number v-model="echarts_title_textStyle_fontSize" @change="handle_echarts_title_textStyle_fontSize" :min="10" :max="50" size="mini" :disabled="this.echarts_title_show==false"></el-input-number>
          </el-form-item>      

          <el-form-item
            :label="$t('Pie.el-form-item.subtitle_size')"
            >
            <el-input-number v-model="echarts_title_subtextStyle_fontSize" @change="handle_echarts_title_subtextStyle_fontSize" :min="7" :max="33" size="mini" :disabled="this.echarts_title_subtext==''"></el-input-number>
          </el-form-item>                     

          <el-form-item
            :label="$t('Pie.el-form-item.change_title_color')"
            >
            <el-color-picker v-model="echarts_title_textStyle_color" size="small" @change="handle_echarts_title_textStyle_color" :disabled="this.echarts_title_show==false"></el-color-picker>
          </el-form-item> 
        </el-form> 

        <el-alert :title="$t('Pie.el-alert.legend_setting')" type="info" :closable="false"></el-alert>       
        <el-form label-width="auto">     

          <el-form-item
            :label="$t('Pie.el-form-item.legend_display_position')"
            >
            <el-select 
              v-model="echarts_legend_position" 
              size="mini"
              
              @change="handle_echarts_legend_position"
              >
              <el-option key="0" :label="$t('Pie.el-option.top_left')" value="0"></el-option>
              <el-option key="1" :label="$t('Pie.el-option.top')" value="1"></el-option>
              <el-option key="2" :label="$t('Pie.el-option.top_right')" value="2"></el-option>
              <el-option key="3" :label="$t('Pie.el-option.right')"   value="3"></el-option>
              <el-option key="4" :label="$t('Pie.el-option.bottom_right')" value="4"></el-option>
              <el-option key="5" :label="$t('Pie.el-option.bottom')" value="5"></el-option>
              <el-option key="6" :label="$t('Pie.el-option.bottom_left')" value="6"></el-option>
              <el-option key="7" :label="$t('Pie.el-option.left')"   value="7"></el-option>
              </el-select>
          </el-form-item> 

          <el-form-item
            :label="$t('Pie.el-form-item.legend_layout')"
            
            >
            <el-select 
              v-model="echarts_legend_orient" 
              size="mini"
              
              @change="handle_echarts_legend_orient"
              >
                <el-option key="0" :label="$t('Pie.el-option.horizontal_arrangement')" value="0"></el-option>
                <el-option key="1" :label="$t('Pie.el-option.vertical_arrangement')" value="1"></el-option>
              </el-select>
          </el-form-item>     


          <el-form-item
            :label="$t('Pie.el-form-item.legend_size')"
            
            >
            <el-input-number v-model="echarts_legend_textStyle_fontSize" @change="handle_echarts_legend_textStyle_fontSize" :min="7" :max="33" size="mini"></el-input-number>
          </el-form-item>       


            
        </el-form>
          
        <el-alert :title="$t('Pie.el-alert.Pie_chart_settings')" type="info" :closable="false"></el-alert>   
        <el-form label-width="auto"> 

          <el-form-item :label="$t('Pie.el-form-item.Pie_chart_inner_diameter')" >
            <el-input-number v-model="echarts_serie_radius_interior" @change="handle_echarts_serie_radius_interior" :min="0" :max="250" size="mini"></el-input-number>
          </el-form-item>  

          <el-form-item :label="$t('Pie.el-form-item.Pie_chart_outer_diameter')" >
            <el-input-number v-model="echarts_serie_radius_exterior" @change="handle_echarts_serie_radius_exterior" :min="250" :max="500" size="mini"></el-input-number>
          </el-form-item>  

          <el-form-item :label="$t('Pie.el-form-item.Left_distance_of_pie_chart')" >
            <el-input-number v-model="echarts_serie_center_left" @change="handle_echarts_serie_center_left" :min="0" :max="100" size="mini"></el-input-number>
          </el-form-item>

          <el-form-item :label="$t('Pie.el-form-item.Top_distance_of_pie_chart')" >
            <el-input-number v-model="echarts_serie_center_top" @change="handle_echarts_serie_center_top" :min="0" :max="100" size="mini"></el-input-number>
          </el-form-item> 

          <el-form-item :label="$t('Pie.el-form-item.text_label_size')" >
            <el-input-number v-model="echarts_serie_label_fontSize" @change="handle_echarts_serie_label_fontSize" :min="0" :max="30" size="mini"></el-input-number>
          </el-form-item>         

          <el-form-item :label="$t('Pie.el-form-item.Pie_chart_roundness')" >
            <el-input-number v-model="echarts_serie_itemStyle_borderRadius" @change="handle_echarts_serie_itemStyle_borderRadius" :min="0" :max="100" size="mini"></el-input-number>
          </el-form-item>         

          <el-form-item :label="$t('Pie.el-form-item.Nightingale_diagram')" >
            <el-select v-model="echarts_serie_roseType" size="mini" @change="handle_echarts_serie_roseType">
              <el-option key="1" :label="$t('Pie.el-option.open')" :value="true"></el-option>
              <el-option key="0" :label="$t('Pie.el-option.close')" :value="false"></el-option>
            </el-select>        
          </el-form-item>         
        </el-form>  
      </el-scrollbar>
    </el-aside>

    <el-main>
        <div id="pie" :style="{width: '99%', height:'99%', background:'white'}"></div>
    </el-main>
  </el-container>
</template>


<script>
import { pie } from '@/components/echarts/mixin/pie/pie'
export default {
  props: [],
  mixins: [pie],
};
</script>

<style scoped>
  @import '@/assets/css/echarts/echarts.css';
</style>