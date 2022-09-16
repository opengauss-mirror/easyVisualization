<template>
  <el-descriptions 
  v-if="this.discriptionType == 'bar' || this.discriptionType == 'ybar' || this.discriptionType == 'line' || this.discriptionType == 'scatter' || this.discriptionType == 'area' || this.discriptionType == 'pie'"  
  title="" 
  :column="3" 
  size="small" 
  border> 
    <el-descriptions-item :label="$t('discription.discriptions.name')">{{form.dataSourceName}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.item')">{{form.yOptions}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.label1')">{{form.xOption}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.order')">{{formIndex}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.option')">
        <el-button type="text" size="mini" @click="handleDelete">{{$t('discription.button.delete')}}</el-button>
    </el-descriptions-item>
  </el-descriptions>

  <el-descriptions 
  v-else-if="this.discriptionType == 'combination'" 
  title="" 
  :column="2" 
  size="small" 
  border> 
    <el-descriptions-item :label="$t('discription.discriptions.order')">{{formIndex}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.name')">{{form.dataSourceName}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.Column_chart_legend')">{{form.yOptions.bar_yOptions}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.Bar_chart_legend')">{{form.yOptions.line_yOptions}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.label1')">{{form.xOption}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.option')">
        <el-button type="text" size="mini" @click="handleDelete">{{$t('discription.button.delete')}}</el-button>
    </el-descriptions-item>
  </el-descriptions>

  <el-descriptions 
  v-else-if="this.discriptionType == 'radar'" 
  title="" 
  :column="3" 
  size="small" 
  border> 
    <el-descriptions-item :label="$t('discription.discriptions.order')">{{formIndex}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.name')">{{form.dataSourceName}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.dim')">{{form.yOptions}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.label_classify')">{{form.xOption}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.option')">
        <el-button type="text" size="mini" @click="handleDelete">{{$t('discription.button.delete')}}</el-button>
    </el-descriptions-item>
  </el-descriptions>  

  <el-descriptions 
  v-else-if="this.discriptionType == 'scatter3D'" 
  title="" 
  :column="3" 
  size="small" 
  border> 
    <el-descriptions-item :label="$t('discription.discriptions.order')">{{formIndex}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.name')">{{form.dataSourceName}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.option')">
      <el-button type="text" size="mini" @click="handleDelete">{{$t('discription.button.delete')}}</el-button>      
    </el-descriptions-item>    
    <el-descriptions-item :label="$t('discription.discriptions.x_axis')">{{form.xOption}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.y_axis')">{{form.yOption}}</el-descriptions-item>
    <el-descriptions-item :label="$t('discription.discriptions.z_axis')">{{form.zOption}}</el-descriptions-item>    
  </el-descriptions>   
</template>

<script>
  export default {
    props:[ 'form', 'formIndex', 'discriptionType' ],
    data () {
      return {};
    },
    methods: {
        handleDelete() {
            this.$parent.$parent.removeEchartsForm(this.formIndex);
        }
    }
  }
</script>