<template>
  <div>
    <el-form :model="form" label-width="auto" v-if="this.formType == 'bar' || this.formType == 'line' || this.formType == 'scatter' || this.formType == 'area' || this.formType == 'ybar'">
      <el-form-item :label="$t('dialog-form.el-form-item.choose_data_source')">
        <el-select v-model="form.dataSourceName" @change="fetchColumnNames">
          <el-option
            v-for="dataSourceName in dataSourceList"
            :label="dataSourceName"
            :value="dataSourceName"
            :disabled="unavailableDataSourceList.indexOf(dataSourceName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_legend')">
        <el-select multiple v-model="form.yOptions" >
          <el-checkbox v-model="form_y_options_select_all" size="mini" @change="form_y_options_selectAll">{{$t('selectAll')}}</el-checkbox>
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_Select_Category_Axis_Labels')">
        <el-select v-model="form.xOption"  >
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName"
            :disabled="form.yOptions.indexOf(columnName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>  
    </el-form>
  
    <el-form :model="combinationForm" label-width="auto" v-if="this.formType == 'combination'">
      <el-form-item :label="$t('dialog-form.el-form-item.choose_data_source')">
        <el-select v-model="combinationForm.dataSourceName" @change="fetchColumnNames">
          <el-option
            v-for="dataSourceName in dataSourceList"
            :label="dataSourceName"
            :value="dataSourceName"
            :disabled="unavailableDataSourceList.indexOf(dataSourceName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_a_column_chart_legend')">
        <el-select multiple v-model="combinationForm.yOptions.bar_yOptions"  >
          <el-checkbox v-model="combination_form_bar_y_options_select_all" size="mini" @change="combination_form_bar_y_options_selectAll">{{$t('selectAll')}}</el-checkbox>
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_a_line_chart_legend')">
        <el-select multiple v-model="combinationForm.yOptions.line_yOptions"  >
          <el-checkbox v-model="combination_form_line_y_options_select_all" size="mini" @change="combination_form_line_y_options_selectAll">{{$t('selectAll')}}</el-checkbox>
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_category_label')">
        <el-select v-model="combinationForm.xOption"  >
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName"
            :disabled="combinationForm.yOptions.bar_yOptions.indexOf(columnName) != -1 || combinationForm.yOptions.line_yOptions.indexOf(columnName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>  
    </el-form>  
  
    <el-form :model="form" label-width="auto" v-if="this.formType == 'pie'">
      <el-form-item :label="$t('dialog-form.el-form-item.choose_data_source')">
        <el-select v-model="form.dataSourceName"   @change="fetchColumnNames">
          <el-option
            v-for="dataSourceName in dataSourceList"
            :label="dataSourceName"
            :value="dataSourceName"
            :disabled="unavailableDataSourceList.indexOf(dataSourceName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_legend')">
        <el-select v-model="form.yOptions"  >
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_category_label')">
        <el-select v-model="form.xOption"  >
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName"
            :disabled="form.yOptions.indexOf(columnName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>  
    </el-form>  
  
    <el-form :model="form" label-width="auto" v-if="this.formType == 'radar'">
      <el-form-item :label="$t('dialog-form.el-form-item.choose_data_source')">
        <el-select v-model="form.dataSourceName"   @change="fetchColumnNames">
          <el-option
            v-for="dataSourceName in dataSourceList"
            :label="dataSourceName"
            :value="dataSourceName"
            :disabled="unavailableDataSourceList.indexOf(dataSourceName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_dimension')">
        <el-select multiple v-model="form.yOptions"  >
          <el-checkbox v-model="form_y_options_select_all" size="mini" @change="form_y_options_selectAll">{{$t('selectAll')}}</el-checkbox>
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_category_label')">
        <el-select v-model="form.xOption"  >
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName"
            :disabled="form.yOptions.indexOf(columnName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>  
    </el-form>    
  
    <el-form :model="scatter3DForm" label-width="auto" v-if="this.formType == 'scatter3D'">
      <el-form-item :label="$t('dialog-form.el-form-item.choose_data_source')">
        <el-select v-model="scatter3DForm.dataSourceName"   @change="fetchColumnNames">
          <el-option
            v-for="dataSourceName in dataSourceList"
            :label="dataSourceName"
            :value="dataSourceName"
            :disabled="unavailableDataSourceList.indexOf(dataSourceName) != -1">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_x-axis_data')">
        <el-select v-model="scatter3DForm.xOption">
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_y-axis_data')">
        <el-select v-model="scatter3DForm.yOption">
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>
  
      <el-form-item :label="$t('dialog-form.el-form-item.choose_z-axis_data')">
        <el-select v-model="scatter3DForm.zOption">
          <el-option
            v-for="columnName in columnNames"
            :label="columnName"
            :value="columnName">
          </el-option>
        </el-select>  
      </el-form-item>    
    </el-form>  
  
    <div slot="footer" class="dialog-footer">
      <el-button @click="handleCancle">{{$t('dialog-form.button.button_handleCancle')}}</el-button>
      <el-button type="primary" @click="handleConfirm">{{$t('dialog-form.button.button_handleConfirm')}}</el-button>
    </div>
  </div>
  
  </template>
  
  <script>
  import axios from 'axios'
    export default {
      props:[ 'formType', 'dataSourceList', 'unavailableDataSourceList' ],
      data() {
        return {
          columnNames:[],
          form_y_options_select_all:false,
          form: {
            dataSourceName: '',
            xOption:'',
            yOptions: []
          },
          combination_form_bar_y_options_select_all:false,
          combination_form_line_y_options_select_all:false,
          combinationForm: {
            dataSourceName: '',
            xOption:'',
            yOptions: {
              bar_yOptions:[],
              line_yOptions:[]
            }
          },
          scatter3DForm: {
            dataSourceName: '',
            xOption:'',
            yOption:'',
            zOption:''
          },
                 
        };
      },
      methods: {
          /**
           * Close dialog
           */
          handleCancle() {
              this.$parent.$parent.$parent.dialogFormVisible = false
          },
          /**
           * Submit form
           */
          handleConfirm() {
            if (this.formType == 'combination') {
              this.$parent.$parent.$parent.addEchartsForm(this.combinationForm);
              this.combinationForm = {
                dataSourceName: '',
                xOption:'',
                yOptions: {
                  bar_yOptions:[],
                  line_yOptions:[]
                }          
              }
            }
            else if (this.formType == 'scatter3D') {
              this.$parent.$parent.$parent.addEchartsForm(this.scatter3DForm);
              this.scatter3DForm = {
                dataSourceName: '',
                xOption:'',
                yOption:'',
                zOption:''
              }            
            }
            else {
              this.$parent.$parent.$parent.addEchartsForm(this.form);
              this.form = {
                dataSourceName: '',
                xOption:'',
                yOptions: []                
              }            
            }
            this.$parent.$parent.$parent.dialogFormVisible = false
          },
          /**
           * Get columns' names in data source 
           * @param {Name of currently selected data source} dataSourceName
           */
          fetchColumnNames(dataSourceName) {
              axios({
                  url:'/handle-data-source/read-data-source',
                  method:"get",
                  params:{
                      'dataSourceName':dataSourceName,
                      'startIndex':0,
                      'limit':1                   
                  }
              }).then(
                  response => {
                      console.log(response.data.result)
                      this.columnNames = JSON.parse(response.data.result).columnNames                    
                  },
                  error => {}
              )              
          },
          form_y_options_selectAll(val) {
            this.form.yOptions = []
            if (val) {
              for (let i = 0; i < this.columnNames.length; i++) {
                this.form.yOptions.push(this.columnNames[i]);
              }
            }
          },
          combination_form_bar_y_options_selectAll(val) {
            this.combinationForm.yOptions.bar_yOptions = []
            if (val) {
              for (let i = 0; i < this.columnNames.length; i++) {
                this.combinationForm.yOptions.bar_yOptions.push(this.columnNames[i]);
              }
            }            
          },
          combination_form_line_y_options_selectAll(val) {
            this.combinationForm.yOptions.line_yOptions = []
            if (val) {
              for (let i = 0; i < this.columnNames.length; i++) {
                this.combinationForm.yOptions.line_yOptions.push(this.columnNames[i]);
              }
            }            
          },          
      },
    };
  </script>
  
  <style scoped>
    @import '@/assets/css/dialog/dialog-form/dialog-form.css';
  </style>