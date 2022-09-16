<template>
  <el-container id="echarts_container">
    <el-aside width="300px" >
    <el-scrollbar>
      <el-alert id="alert-data-source-size" :title="'Total: ' + dataSourceSizeMax"  type="success" :closable="false"    ></el-alert>       
      <el-alert :title="$t('Radar.el-form-item.data_range')" type="info" :closable="false"   ></el-alert>       
      <el-form label-position="left" label-width="40%"> 
        <el-form-item :label="$t('Radar.el-form-item.data_start_index')"  >
          <el-input-number v-model="startIndex" @change="updateDatas" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
        </el-form-item>    
        <el-form-item :label="$t('Radar.el-form-item.data_end_index')"  >
          <el-input-number v-model="endIndex" @change="updateDatas" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
        </el-form-item>                          
      </el-form>     
      <el-alert :title="$t('Radar.el-alert.title_set')" type="info" :closable="false"   ></el-alert>
      <el-form label-position="left" label-width="40%">
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
          <el-color-picker v-model="echarts_title_textStyle_color" size="small"       @change="handle_echarts_title_textStyle_color" :disabled="this.echarts_title_show==false"></el-color-picker>
        </el-form-item> 
      </el-form> 

      <el-alert :title="$t('Radar.el-alert.legend_setting')" type="info" :closable="false"   ></el-alert>       
      <el-form label-position="left" label-width="40%">     

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

      <el-alert :title="$t('Radar.el-alert.Radar_chart_settings')" type="info" :closable="false"   ></el-alert>   
      <el-form label-position="left" label-width="40%"> 

        <el-form-item :label="$t('Radar.el-form-item.Radar_left_distance')"  >
          <el-input-number v-model="echarts_radar_center_left" @change="handle_echarts_radar_center_left" :min="0" :max="100" size="mini"></el-input-number>
        </el-form-item> 

        <el-form-item :label="$t('Radar.el-form-item.Radar_upper_side_distance')"  >
          <el-input-number v-model="echarts_radar_center_top" @change="handle_echarts_radar_center_top" :min="0" :max="100" size="mini"></el-input-number>
        </el-form-item>  


        <el-form-item :label="$t('Radar.el-form-item.Radar_radius')"  >
          <el-input-number v-model="echarts_radar_radius" @change="handle_echarts_radar_radius" :min="0" :max="100" size="mini"></el-input-number>
        </el-form-item>  

        <el-form-item :label="$t('Radar.el-form-item.Category_abel_size')"  >
          <el-input-number v-model="echarts_radar_axisName_fontSize" @change="handle_echarts_radar_axisName_fontSize" :min="0" :max="30" size="mini"></el-input-number>
        </el-form-item>                
      </el-form>       
         

      
      </el-scrollbar>
    </el-aside>

    <el-main>
        <div id="radar" :style="{width: '99%', height:'99%', background:'white',border:'1px dashed #64AFFC',margin:'0'}"></div>
    </el-main>
  </el-container>
</template>

<script>
import { radar } from '@/components/echarts/mixin/radar/radar'
export default {
  mixins: [radar],
}
</script>

<style scoped>
  @import '@/assets/css/echarts/echarts.css';
</style>