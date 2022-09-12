<template>
  <!-- This component is used to show the data source -->
  <el-container>   
    <el-main id="main">
      <el-table 
      id="table"
      stripe
      :data="sourcedata"
      @selection-change="handleSelectionChange"
      :border="true"
      height="90%"
      v-loading.fullscreen.lock="loading"
      :element-loading-text="$t('loading.text')">
          <el-table-column v-for="(value,key,index) in this.sourcedata[0]" :label="key" :property="key" :column-key="key"></el-table-column>
      </el-table>

      <el-pagination
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-sizes="[10, 20, 30, 40, 50]"
        :page-size="pagesize"
        :total="this.count"
        layout="total, sizes, prev, pager, next, jumper">
      </el-pagination>
    </el-main>

    <el-footer 
    id="footer"
    height="50px"
    >
      <el-button 
        type="primary" 
        @click="submit()" >
        {{$t('sourcedata.button.button_submit')}}
      </el-button>   
    </el-footer>         
  </el-container>
</template>

<script>
import axios from 'axios'
var flag = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>《》/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？ ]");
export default {
  props:[ 'count' ],
  data() {
    return {
      sourcedata:[],
      multipleSelection: [],
      pagesize:10,
      currentPage:1,
      startIndex:0,
      limit:10,
      whichModuleAmI:'',
      loading:false
    }
  },
  computed: {
    iconType(){
      if(this.isLeft){
        return "el-icon-turn-off"
      }else{
        return "el-icon-open"
      }
    },
  },

  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },

    handleSizeChange(val){
        this.pagesize = val;
        this.startIndex= this.pagesize * (this.currentPage - 1)
        this.limit = this.pagesize
        axios({
          url:'/showSourceData',
          method:"get",
          params:{
            'pollName':this.$parent.$parent.pollName,
            'sql': this.$parent.$parent.order_sql,
            'startIndex':this.startIndex,
            'limit':this.limit
          }
        }).then(
          response => {
            this.sourcedata = response.data     
          },
          error => {
        })           
    },
    handleCurrentChange(currentPage) {
        this.currentPage = currentPage;
        this.startIndex = this.pagesize * (this.currentPage - 1)
        this.limit = this.pagesize
        axios({
          url:'/showSourceData',
          method:"get",
          params:{
            'pollName':this.$parent.$parent.pollName,
            'sql': this.$parent.$parent.order_sql,
            'startIndex':this.startIndex,
            'limit':this.limit
          }
        }).then(
          response => {
            this.sourcedata = response.data     
          },
          error => {
        }) 
    },
    async submit() {
      if (this.count == 0) {
        this.$notify({
          title: this.$t('tips'),
          message: this.$t('sourcedata.message.empty'),
          type: 'error',
          duration: 2000
        })     
        return 
      }
      var statuss = false
      var dataSourceName = ''
      await this.$prompt(this.$t('sourcedata.message.data_source_name'), this.$t('submit'), {
        confirmButtonText: this.$t('confirm'),
        cancelButtonText: this.$t('cancel'),
      }).then(({ value }) => {
        var invalidInput = flag.test(value)
        if (value == null || invalidInput || this.$store.state.dataSourceList.indexOf(value) !== -1 || value.indexOf('\\') !== -1) {    
          this.$notify({
            title: this.$t('tips'),
            message: this.$t('sourcedata.message.wrong_data_source_name'),
            type: 'error',
            duration: 3000
          });                 
          statuss = false
        } else {
          dataSourceName = value
          this.loading = true
          statuss = true
        } 
      }).catch(() => {
        this.$notify({
          type: 'info',
          title: this.$t('cancel')
        })
        statuss = false      
      })

      if(!statuss){
        return
      }
      
      await axios({
        url:'/handle-data-source/save-data-source',
        method:"post",
        params:{
          'pollName':this.$parent.$parent.pollName,
          'sql': this.$parent.$parent.order_sql,
          'dataSourceName':dataSourceName
        }
      }).then(
        response => {             
          this.$notify({
            title: this.$t('success'),
            message: "",
            type: 'success',
            duration: 2000
          });                       
        },
        error => {}
        )  

      this.loading = false        
      this.$parent.$parent.$parent.$parent.$parent.fetchDataSourceList() 
    },
  },
  components:{},
  mounted() {
    axios({
      url:'/showSourceData',
      method:"get",
      params:{
        'pollName':this.$parent.$parent.pollName,
        'sql': this.$parent.$parent.order_sql,
        'startIndex':this.startIndex,
        'limit':this.limit
      }
    }).then(
      response => {
        this.sourcedata = response.data     
      },
      error => {
    })      
  },
}
</script>

<style scoped>
@import '@/assets/css/sourcedata/sourcedata.css';
</style>
