<template>
  <el-container id="echarts_container">
    <el-aside width="300px" >
    <el-scrollbar>
      <el-alert id="alert-data-source-size" :title="'Total: ' + dataSourceSizeMax"  type="success" :closable="false"    ></el-alert>       
      <el-alert :title="$t('Radar.el-form-item.data_range')" type="info" :closable="false"   ></el-alert>       
      <el-form label-position="left" label-width="40%"> 
        <el-form-item :label="$t('Radar.el-form-item.data_start_index')"  >
          <el-input-number v-model="startIndex" @change="refreshDatas" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
        </el-form-item>    
        <el-form-item :label="$t('Radar.el-form-item.data_end_index')"  >
          <el-input-number v-model="endIndex" @change="refreshDatas" :min="0" :max="this.dataSourceSizeMax" size="mini"></el-input-number>
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

      </el-form> 
      </el-scrollbar>
    </el-aside>
    <el-main>
        <div id="scatter3D" :style="{width: '99.9%', height:'99.9%', background:'white'}"></div>
    </el-main>
  </el-container>
</template>

<script>
import { scatter3D } from '@/components/echarts/mixin/scatter3D/scatter3D';
export default {
  mixins: [scatter3D],
}
</script>

<style scoped>
  @import '@/assets/css/echarts/echarts.css';
</style>