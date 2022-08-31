<template>
    <!-- This component is used to edit the data source -->
    <el-container>   
        <el-main id="main">
            <el-table 
            id="table"
            stripe
            :data="sourcedata"
            @selection-change="handleSelectionChange"
            :border="true"
            @row-contextmenu="rowContextmenu"
            v-loading="el_table_loading"
            :element-loading-text="$t('loading.text')"
            element-loading-spinner="el-icon-loading"
            element-loading-background="rgba(0, 0, 0, 0.8)">
                <el-table-column fixed="left" type="selection" width="50"></el-table-column>
                <el-table-column v-for="header in this.headers" :label="header" :property="header" :column-key="header"></el-table-column>
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

            <ContextButton v-if="menuVisible" @foo="foo" ref="contextbutton" @handleOne="handleOne" @handleTwo="handleTwo"></ContextButton>
        </el-main>

        <el-footer 
        id="footer"
        height="50px"
        >
          <el-button 
            type="warning" 
            @click="deleteSelection()" 
            icon="el-icon-scissors">
            {{$t('datasource.button.button_deleteSelection')}}
          </el-button>
          <el-button 
            type="success" 
            @click="preserve()" 
            icon="el-icon-folder-checked">
            {{$t('datasource.button.button_preserve')}}
          </el-button>
          <el-button 
            type="danger" 
            @click="deleteSourceData()"
            icon="el-icon-folder-delete">
            {{$t('datasource.button.button_deleteSourceData')}}
          </el-button>
          <el-button 
            type="primary" 
            @click="fatherMethodHandelSelectDataIsActive()"
            :icon="iconType">
            {{$t('datasource.button.button_fatherMethodHandelSelectDataIsActive')}}
          </el-button>        
        </el-footer>      
    </el-container>
</template>

<style scoped>
  @import '@/assets/css/datasource/datasource.css';
</style>

<script>
import ContextButton from '@/components/contextbutton'
import axios from 'axios'

  export default {
    props:[ 'dataSourceName' ],
    data() {
      return {
        sourcedata:[],
        headers:[],

        dataSourceEdit:{
            deleteColumnName:[],
            deleteRowIndex:[]
        },
        
        pagesize:10,
        currentPage:1,
        startIndex:0,
        limit:10,
        count:0,

        multipleRowSelection: [],
        currentRow:'',
        currentColumn:'',

        whichModuleAmI:'',
        menuVisible:false,  
        isLeft:true,
        el_table_loading:false
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
      ParamDataSourceEditBean() {
        return {
            dataSourceName: this.dataSourceName,
            dataSourceEditBean: this.dataSourceEdit
        }
      },
    },

    methods: { 
        handleSelectionChange(val) {
            this.multipleRowSelection = val;
            console.log('@',val)
        },

        deleteSelection() {
            for (let i = 0; i < this.multipleRowSelection.length; i++) {
                if (JSON.stringify(this.multipleRowSelection[i]) == "{}") {
                    this.showMessage(this.$t('error'), this.$t('datasource.message.deleteMessage'), "error")
                    return
                }
                let rowIndexInDataSource = this.getRowIndexInDataSource(this.multipleRowSelection[i]);
                this.dataSourceEdit.deleteRowIndex.push(rowIndexInDataSource);
            }        
            this.removeRowAndColumn();         
        },

        handleSizeChange(val){
            this.pagesize = val;
            this.startIndex= this.pagesize * (this.currentPage - 1)
            this.limit = this.pagesize
            this.fetchDataSource()          
        },

        handleCurrentChange(currentPage) {
            this.currentPage = currentPage;
            this.startIndex = this.pagesize * (this.currentPage - 1)
            this.limit = this.pagesize
            this.fetchDataSource()  
        },

        async preserve(){
            if (this.count < 10000) {
                this.saveEdit();
                return;
            }
            this.$confirm(this.$t('bigSizeMessage'), this.$t('tips'), {
                confirmButtonText: this.$t('confirm'),
                cancelButtonText: this.$t('cancel'),
                type: 'warning'
            }).then(() => {
                this.$parent.$parent.$parent.el_aside_loading = true
                this.saveEdit()                  
            }).catch(() => {
                this.showMessage(this.$t('cancel'), this.$t('cancel'), "info")      
            });                  
        },

        deleteSourceData(){
            axios({
            url:'/handle-data-source/remove-data-source',
            method:"get",
            params:{
                'dataSourceName':this.dataSourceName
            }
            }).then(
            response => {
                this.$message({
                    message: response.data.message,
                    type: 'success'
                })    
                this.sourcedata = [] 
                this.$parent.$parent.$parent.fetchDataSourceList()        
            },
            error => {
                this.$message({
                    message: response.data.message,
                    type: 'error'
                })                
            })  
        },

        fatherMethodHandelSelectDataIsActive(){
            this.$parent.$parent.$parent.handelSelectDataIsActive()
            this.isLeft = !this.isLeft
        },

        // This function references the code of https://www.jianshu.com/p/02d42f689c58 
        rowContextmenu (row, column, event) {
            this.menuVisible = false
            this.menuVisible = true
            this.currentRow = row 
            this.currentColumn = column

            event.preventDefault()
            this.$nextTick(() => {
                this.$refs.contextbutton.init(row,column,event)
            })
        },

        // This function references the code of https://www.jianshu.com/p/02d42f689c58 
        foo() { 
            this.menuVisible = false
            document.removeEventListener('click', this.foo)
        },

        // This function references the code of https://www.jianshu.com/p/02d42f689c58 
        handleOne () {
            if (JSON.stringify(this.currentRow) == "{}") {
                this.showMessage(this.$t('error'), this.$t('datasource.message.deleteMessage'), "error")
                return
            }
            let rowIndexInDataSource = this.getRowIndexInDataSource(this.currentRow)
            this.dataSourceEdit.deleteRowIndex.push(rowIndexInDataSource)
            this.removeRowAndColumn()
        },

        // This function references the code of https://www.jianshu.com/p/02d42f689c58 
        handleTwo () {
            this.dataSourceEdit.deleteColumnName.push(this.currentColumn.property)
            this.removeRowAndColumn()
        },
        
        async fetchDataSource() {
            this.el_table_loading = true
            await axios({                        
                url:'/handle-data-source/read-data-source',
                method:"get",
                params:{  
                    'dataSourceName':this.dataSourceName,
                    'startIndex':this.startIndex,
                    'limit':this.limit
                }        
            }).then(
                response => {
                    this.sourcedata = JSON.parse(response.data.result).sourceData
                    this.headers = JSON.parse(response.data.result).columnNames
                },            
                error => {
            })   
            this.el_table_loading = false
        },
        
        fetchDataSourceSize() {
            axios({
            url:'/handle-data-source/get-data-source-size',
            method:"get",
            params:{
                'dataSourceName':this.dataSourceName
            }
            }).then(
            response => {
                this.count = JSON.parse(response.data.result)
            },
            error => {
            })                
        },

        async removeRowAndColumn(){
            await axios({
            url:'/handle-data-source/edit-data-source',
            method:"post",
            data:this.ParamDataSourceEditBean
            }).then(
            response => {
                this.showMessage(this.$t('success'), response.data.message, "success")
            },
            error => {
                this.showMessage(this.$t('fail'), response.data.message, "error")
            })      
            this.fetchDataSource()
        },

        async saveEdit(){
            this.$parent.$parent.$parent.el_aside_loading = true
            this.el_table_loading = true
            await axios({
            url:'/handle-data-source/save-edit-data-source',
            method:"get",
            params:{
                'dataSourceName':this.dataSourceName
            }            
            }).then(
            response => {
                this.showMessage(this.$t('success'), response.data.message, "success")
            },
            error => {
                this.showMessage(this.$t('fail'), response.data.message, "error")
            })   
            this.fetchDataSource()
            this.fetchDataSourceSize()
            this.$parent.$parent.$parent.el_aside_loading = false         
        },

        // Get the index of row in data source
        getRowIndexInDataSource(row) {
            let rowIndexInCurrentPage
            for (let i = 0; i < this.sourcedata.length; i++) {
                if (this.sourcedata[i] == row) {
                    rowIndexInCurrentPage = i
                    break
                } 
            } 
            let rowIndexInDataSource = rowIndexInCurrentPage + (this.currentPage - 1) * this.pagesize          
            return rowIndexInDataSource
        },


        /**
         * show message
         * @param {message title} title 
         * @param {message body} message 
         * @param {message type:'warning', 'success', 'info', 'error'} type 
         */
        showMessage(title, message, type){      
            this.$notify({
                title: title,
                message: message,
                type: type
            });                 
        },
   
    },
    components:{
      ContextButton
    },
    mounted() {
        this.whichModuleAmI = this.$store.state.whichModuleAmI
        this.fetchDataSourceSize()
        this.fetchDataSource()
    },
  }
</script>

