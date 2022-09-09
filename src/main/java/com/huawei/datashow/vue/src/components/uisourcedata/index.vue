<template>
  <el-container>
    <el-aside id="aside" width="270px">
      <el-scrollbar id="scrollbar">
          <el-button 
            id="backButton" 
            size="mini" 
            type="info" 
            icon="el-icon-back" 
            @click='back'>
            {{$t('back')}}
          </el-button>
          <el-alert
            :title="$t('uisourcedata.el-alert.Select_database_connection')"
            type="info"
            :closable="false">
          </el-alert>

          <el-form
            label-width="auto">
            <el-form-item
              id="item-poll-name"
              :label="$t('uisourcedata.el-form-item.item-poll-name')"
              >
              <el-select 
              id="select-poll-name"
              v-model="pollName" 
                
              @change="handelPollChange" 
              size="mini"
              clearable
              >
                <el-option
                  v-for="pollName in pollNames"
                  :key="pollName"
                  :label="pollName"
                  :value="pollName"
                  >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form> 

          <el-alert
            :title="$t('uisourcedata.el-alert.schema')"
            type="info"
            :closable="false">
          </el-alert>

          <el-form
            label-width="auto">
            <el-form-item
              id="item-schema"
              :label="$t('uisourcedata.el-form-item.item-schema')"
              >
              <el-select 
              id="select-schema"
              v-model="schemaName" 
                
              @change="handelSchemaNameChange" 
              size="mini"
              clearable
              >
                <el-option
                  v-for="item in schemaNames"
                  :key="item.schemaname"
                  :label="item.schemaname"
                  :value="item.schemaname"
                  >
                </el-option>
              </el-select>
            </el-form-item>
          </el-form>             

          <el-alert
            :title="$t('uisourcedata.el-alert.table')"
            type="info"
            :closable="false"
          >
          </el-alert>          

          <el-menu 
            @select='handleTableSelect'
            >
            <el-menu-item v-for="table in this.tables" :index="table.tablename">{{table.tablename}}</el-menu-item>
          </el-menu>

          <el-form label-width="auto" v-if="this.table != ''">
            <el-alert
              :title="$t('uisourcedata.el-alert.Data_filtering_filter')"
              type="success"
              center
              :closable="false"
              v-if="this.table != ''"
              :description="$t('uisourcedata.el-alert.Descrpition_filter')"
            >
            </el-alert>  

            <el-form-item id="item-table-headers" :label="$t('uisourcedata.el-form-item.item-table-headers')">
              <el-select 
              id="select-table-headers"
              v-model="choose_table_headers"        
              size="mini"
              multiple
              >
                <el-option
                  v-for="header in tableHeader"
                  :key="header"
                  :label="header"
                  :value="header"
                  >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item id="item-where-table-header" :label="$t('uisourcedata.el-form-item.item-where-table-header')">
              <el-select id="select-where-table-header" v-model="where_table_header"   size="mini" clearable>
                <el-option v-for="header in tableHeader" :key="header" :label="header" :value="header"></el-option>
              </el-select>
            </el-form-item> 

            <el-form-item id="item-where-operate" :label="$t('uisourcedata.el-form-item.item-where-operate')">
              <el-select id="select-where-operate" v-model="where_table_header_operate"   size="mini" clearable :disabled="this.where_table_header==''">
                <el-option key="where_table_header_bigger_than" :label="$t('uisourcedata.el-option.bigger')" value=">"></el-option>
                <el-option key="where_table_header_smaller_than" :label="$t('uisourcedata.el-option.less')" value="<"></el-option>
                <el-option key="where_table_header_equals" :label="$t('uisourcedata.el-option.equal')" value="="></el-option>
                <el-option key="where_table_header_between" :label="$t('uisourcedata.el-option.Custom_closed_range')" value="BETWEEN"></el-option>
              </el-select>
            </el-form-item> 

            <el-form-item id="item-where-bigger-smaller-equals" :label="$t('uisourcedata.el-form-item.item-where-bigger-smaller-equals')" v-if="this.where_table_header_operate != 'BETWEEN' && this.where_table_header_operate != ''" >
              <el-input v-model="where_table_header_bigger_smaller_equals" size="mini" :placeholder="this.where_table_header" :disabled="this.where_table_header==''"></el-input>
            </el-form-item> 

            <el-form-item id="item-where-scope-0" :label="$t('uisourcedata.el-form-item.item-where-scope-0')" v-if="this.where_table_header_operate == 'BETWEEN'" :disabled="this.where_table_header==''">
              <el-input v-model="where_table_header_scope[0]" size="mini" :placeholder="this.where_table_header" :disabled="this.where_table_header==''"></el-input>
            </el-form-item>     
            <el-form-item id="item-where-scope-1" :label="$t('uisourcedata.el-form-item.item-where-scope-1')" v-if="this.where_table_header_operate == 'BETWEEN'" :disabled="this.where_table_header==''">
              <el-input v-model="where_table_header_scope[1]" size="mini" :placeholder="this.where_table_header" :disabled="this.where_table_header==''"></el-input>
            </el-form-item>                      

            
          <el-alert
            id="alert-sort"
            :title="$t('uisourcedata.el-alert.Data_filtering_sort')"
            type="success"
            center
            :closable="false"
            v-if="this.table != ''"
            :description="$t('uisourcedata.el-alert.Descrpition_sort')"
            
          >
          </el-alert>
            
            <el-form-item
              id="item-sort-headers"
              :label="$t('uisourcedata.el-form-item.item-sort-headers')"
              >
              <el-select 
              id="select-sort-headers"
              v-model="order_by_headers" 
                
              size="mini"
              multiple
              >
                <el-option
                  v-for="header in tableHeader"
                  :key="header"
                  :label="header"
                  :value="header"
                  >
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item
              id="item-sort-desc-or-asc"
              :label="$t('uisourcedata.el-form-item.item-sort-desc-or-asc')"
              >
              <el-select 
              id="select-sort-desc-or-asc"
              v-model="order_by_header_desc_or_asc" 
              size="mini"
              clearable
              >
                <el-option
                  key="ASC"
                  :label="$t('uisourcedata.el-option.asc')"
                  value="ASC"
                  >
                </el-option>

                <el-option
                  key="DESC"
                  :label="$t('uisourcedata.el-option.desc')"
                  value="DESC"
                  >
                </el-option>
              </el-select>
            </el-form-item> 

          <el-alert
            id="alert-group"
            :title="$t('uisourcedata.el-alert.alert-group')"
            center
            type="success"
            :closable="false"
            v-if="this.table != ''"
            :description="$t('uisourcedata.el-alert.Descrpition_group')"
          >
          </el-alert>

          <el-form-item
            id="item-group-header"
            :label="$t('uisourcedata.el-form-item.item-group-header')"
            >
            <el-select 
            id="select-group-header"
            v-model="group_by_header" 
              
            size="mini"
            clearable
            >
              <el-option
                v-for="header in tableHeader"
                :key="header"
                :label="header"
                :value="header"
                >
              </el-option>
            </el-select>
          </el-form-item> 

          <el-form-item
            id="item-aggregate-header"
            :label="$t('uisourcedata.el-form-item.item-aggregate-header')"
            >
            <el-select 
            id="select-aggregate-header"
            v-model="aggregate_header" 
              
            size="mini"
            clearable
            >
              <el-option
                v-for="header in tableHeader"
                :key="header"
                :label="header"
                :value="header"
                :disabled="header == group_by_header"
                >
              </el-option>
            </el-select>
          </el-form-item>  

          <el-form-item
            id="item-aggregate-function"
            :label="$t('uisourcedata.el-form-item.item-aggregate-func')">
            <el-select 
            id="select-aggregate-function"
            v-model="aggregate_function" 
              
            size="mini"
            :disabled="aggregate_header==''"
            clearable
            >
              <el-option
                v-for="aggregate_function in aggregate_functions"
                :key="aggregate_function.value"
                :label="aggregate_function.label"
                :value="aggregate_function.value"
                >
              </el-option>
            </el-select>
          </el-form-item>   

          <el-form-item
            id="item-aggregate-name"
            :label="$t('uisourcedata.el-form-item.item-aggregate-name')"
            >
            <el-input
              id="input-aggregate-name"
              v-model="aggregate_header_new_name"
              size="mini"
              :placeholder="this.aggregate_function"
              :disabled="aggregate_header==''"
            >
            </el-input>
          </el-form-item>                   
            
          <el-button size="small" type="danger"  @click="handle_reset_sift" v-if="this.table != ''">{{$t('uisourcedata.button.button_handle_reset_sift')}}</el-button>
          <el-button size="small" type="primary" @click="handle_sift" v-if="this.table != ''">{{$t('uisourcedata.button.button_handle_sift')}}</el-button>                
          </el-form>        
      </el-scrollbar>         
    </el-aside>
    <SourceData v-if="sourceDataIsActive" v-loading="loading" :count="count"></SourceData>
</el-container>
</template>


<script>
import axios from 'axios'
import SourceData from '@/components/sourcedata'
import router from '@/router';
export default {
data() {
  return {
    pollName:'',
    
    pollNames:[],
    
    database:'',
    
    databases:[],
    
    schemaName:'',
    
    schemaNames:[],
    
    tables:[],
    
    table:'',
    
    tableHeader:[],

    choose_table_headers:[],
    
    where_table_header:'',
    
    where_table_header_operate:'',
    
    where_table_header_bigger_smaller_equals:'',
    
    where_table_header_scope:['',''],

    order_by_headers:[],
    
    order_by_header_desc_or_asc:'',
    
    group_by_header:'',
    
    aggregate_header:'',
    
    aggregate_functions:[
      {
        value: 'AVG',
        label: this.$t('avg')
      },
      {
        value: 'COUNT',
        label: this.$t('count')
      },
      {
        value: 'MAX',
        label: this.$t('max')
      },
      {
        value: 'MIN',
        label: this.$t('min')
      },
      {
        value: 'SUM',
        label: this.$t('sum')
      }
    ],
    aggregate_function:'',
    aggregate_header_new_name:'',
    sql:'',
    sourceDataIsActive:false,
    loading:false,
    dialogFormVisible:false,
    columnTimeData:'',
    sourcedata:[],      
    count:0,     
    start:0,      
    limit:10
  }
},
computed: {
  limit_sql(){
    return ''
  },
  
  filter_sql(){
    if(this.choose_table_headers.length == 0){ 
      if(this.where_table_header == ''){ 
        return this.sql + this.limit_sql 
      }else{                                  
        if(this.where_table_header_operate != 'BETWEEN'){ 
          return this.sql + ' WHERE ' + this.where_table_header + " " + this.where_table_header_operate + " '" + this.where_table_header_bigger_smaller_equals + "'" + this.limit_sql
        }else{                                            
          return this.sql + ' WHERE ' + this.where_table_header + " " + this.where_table_header_operate + " '" + this.where_table_header_scope[0] + "' AND '" + this.where_table_header_scope[1] + "'" + this.limit_sql
        }
      }
    }else{                                  
      if(this.where_table_header == ''){      
        return 'SELECT ' + this.choose_table_headers.toString() + ' FROM ' + this.table + this.limit_sql 
      }else{                                  
        if(this.where_table_header_operate != 'BETWEEN'){ 
          return 'SELECT ' + this.choose_table_headers.toString() + ' FROM ' + this.table + ' WHERE ' + this.where_table_header + " " + this.where_table_header_operate + " '" + this.where_table_header_bigger_smaller_equals + "'" + this.limit_sql
        }else{                                            
          return 'SELECT ' + this.choose_table_headers.toString() + ' FROM ' + this.table + ' WHERE ' + this.where_table_header + " " + this.where_table_header_operate + " '" + this.where_table_header_scope[0] + "' AND '" + this.where_table_header_scope[1] + "'" + this.limit_sql
        }          
      }
    }
  },

  
  order_sql(){
    if(this.group_sql != ''){ 
      return this.group_sql
    }
    if(this.order_by_headers.length == 0){ 
      return this.filter_sql                      
    }else{                                    
      if(this.order_by_header_desc_or_asc == 'DESC'){ 
        return this.filter_sql + ' ORDER BY ' + this.order_by_headers.toString() + ' DESC'
      }else{                                          
        return this.filter_sql + ' ORDER BY ' + this.order_by_headers.toString()
      }
    }
  },

  
  group_sql(){
    if(this.group_by_header == ''){           
      if(this.aggregate_header == ''){          
        return ''
      }else{                                    
        if(this.aggregate_function == ''){        
          return ''
        }else{                                    
          return "SELECT " + this.aggregate_function + "(" + this.aggregate_header + ")" + this.computed_aggregate_header_new_name + " FROM " + this.table
        }         
      }          
    }else{                                     
      if(this.aggregate_header == ''){           
        return "SELECT " + this.group_by_header + " FROM " + this.table + " GROUP BY " + this.group_by_header
      }else{                                     
        if(this.aggregate_function == ''){        
          return "SELECT " + this.group_by_header + " FROM " + this.table + " GROUP BY " + this.group_by_header
        }else{                                    
          return "SELECT " + this.group_by_header + "," + this.aggregate_function + "(" + this.aggregate_header + ")" +  this.computed_aggregate_header_new_name + " FROM " + this.table + " GROUP BY " + this.group_by_header
        }           
      }
    }
  },

  
  computed_aggregate_header_new_name(){
    if(this.aggregate_header_new_name == ''){
      return ''
    }else{
      return " AS " + this.aggregate_header_new_name + ""
    }
  }
},
components:{
  SourceData
},
methods: {
  async getCount(){
    await axios({
      url:'/getCount',
      method:'get',
      params:{
        'pollName': this.pollName,
        'sql': this.order_sql
      }
    }).then(
      response => {
        this.count = response.data[0].count
      },
      error => {}
    )      
  },
  handelPollChange(pollName){
    this.schemaName = ''
    axios({
      url:'/getSchema',
      method:"get",
      params:{
        'pollName':this.pollName,
        'sql': "SELECT schemaname FROM pg_tables group by schemaname"
      }
    }).then(
      response => {
        this.schemaNames = response.data
      },
      error => {    
      }
    )      
  },
  handelSchemaNameChange(){
    this.table = ''
    axios({
      url:'/getTables',
      method:"get",
      params:{
        'pollName':this.pollName,
        'sql': "SELECT tablename FROM pg_tables WHERE schemaname = '" + this.schemaName + "'"
      }
    }).then(
      response => {
        this.tables = response.data
      },
      error => {    
      }
    )      
  },    
  
  async handleTableSelect(index){
    this.table = index
    this.loading = true
    this.sql = 'SELECT * FROM ' + this.table
    await axios({
      url:'/showSourceData',
      method:"get",
      params:{
        'pollName':this.pollName,
        'sql': this.sql,
        'startIndex': this.start,
        'limit': this.limit
      }
    }).then(
      response => {
        this.sourcedata = response.data           
        this.tableHeader = Object.keys(response.data[0])              
      },
      error => {}
    )
    this.getCount()
    this.sourceDataIsActive = false;
    this.$nextTick(function (){
      this.sourceDataIsActive = true
    }) 
    this.loading = false
  },   

  
  handle_sift(){
    this.loading = true
    axios({
      url:'/showSourceData',
      method:"get",
      params:{
        'pollName':this.pollName,
        'sql': this.order_sql,
        'startIndex': this.start,
        'limit': this.limit
      }
    }).then(
      response => {
        this.sourcedata = response.data
        this.sourceDataIsActive = false;
        this.$nextTick(function (){
          this.sourceDataIsActive = true
        })       
        this.loading = false         
      },
      error => {}
    )
    this.getCount()
  },

  
  handle_reset_sift(){
    
    this.choose_table_headers = [],
    this.where_table_header = '',
    this.where_table_header_equals = '',
    this.order_by_headers = [],
    this.order_by_header_desc_or_asc = '',
    this.group_by_header = '',
    this.aggregate_header = '',
    this.aggregate_function = '',
    this.aggregate_header_new_name = ''
    axios({
      url:'/showSourceData',
      method:"get",
      params:{
        'pollName':this.pollName,
        'sql': this.sql + ' LIMIT 10 '
      }
    }).then(
      response => {
        this.sourcedata = response.data
        this.sourceDataIsActive = false;
        this.$nextTick(function (){
          this.sourceDataIsActive = true
        })                
      },
      error => {
      }
    )         
  },

  back() {
    router.push("/home/processeddatas/addsourcedata")
  }
},
mounted() {
  this.$store.commit('updateWhichModuleAmI','/UISourceData')
  axios({
    url:'/updateDataSource/now',
    method:"get"
  }).then(
    response => {
      this.pollNames = response.data
    },
    error => {
      console.log('axios Error!',JSON.parse(error.data))
    }
  )     
}
};
</script>

<style scoped>
@import '@/assets/css/uisourcedata/uisourcedata.css';
</style>