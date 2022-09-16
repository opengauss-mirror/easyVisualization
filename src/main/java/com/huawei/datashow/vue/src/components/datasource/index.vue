<template>
    <!-- This component is used to edit the data source -->
    <el-container>   
        <el-main id="main">
            <el-table 
            id="table"
            stripe
            :data="sourcedata_filted"
            @selection-change="handleSelectionChange"
            :border="true"
            @row-contextmenu="rowContextmenu"
            v-loading.fullscreen.lock="el_table_loading"
            :element-loading-text="$t('loading.text')">
                <el-table-column fixed="left" type="selection" width="80"></el-table-column>
                <el-table-column v-for="header in this.headers" :label="header" :property="header" :column-key="header"></el-table-column>
                <el-table-column fixed="right" :label="$t('operation')" width="120">
                    <template slot-scope="scope">
                        <el-button @click="deleteRow(scope.$index, scope.row)" type="text" size="small">{{$t('remove')}}</el-button>
                    </template>
                </el-table-column>            
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
            :disabled="multipleRowSelection.length == 0"
            @click="deleteSelection()" 
            icon="el-icon-scissors">
            {{$t('datasource.button.button_deleteSelection')}}
          </el-button>
          <el-button 
            :disabled="!editStatus"
            type="info" 
            @click="confirmReload()" 
            icon="el-icon-refresh-right">
            {{$t('datasource.button.button_reload')}}
          </el-button>          
          <el-button 
          :disabled="!editStatus"
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

        editStatus:false,

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
      sourcedata_filted() {
        var sourcedata_filted = []
        for (let i = 0; i < this.sourcedata.length; i++) {
            if (JSON.stringify(this.sourcedata[i]) != '{}') {
                sourcedata_filted.push(this.sourcedata[i])
            }
        }
        return sourcedata_filted
      }
    },

    methods: { 
        handleSelectionChange(val) {
            this.multipleRowSelection = val;
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

        confirmReload() {
            this.$confirm(this.$t('datasource.message.reloadMessage'), this.$t('tips'), {
                confirmButtonText: this.$t('confirm'),
                cancelButtonText: this.$t('cancel'),
                type: 'warning'
            }).then(() => {  
                this.reload()             
            }).catch(() => {
                this.showMessage(this.$t('cancel'), this.$t('cancel'), "info")  
            });  
        },

        async reload() {
            this.dataSourceEdit.deleteColumnName = []
            this.dataSourceEdit.deleteRowIndex = []
            await axios({
            url:'/handle-data-source/reload-data-source',
            method:"get",
            params:{
                'dataSourceName':this.dataSourceName
            }
            }).then(
            response => {
                this.showMessage(this.$t('success'), "", "success")
            },
            error => {
                this.showMessage(this.$t('fail'), response.data.message, "error")
            })      
            this.fetchDataSource()            
        },

        async preserve(){
            if (this.count < 10000) {
                this.$confirm(this.$t('datasource.message.preserveMessage'), this.$t('tips'), {
                    confirmButtonText: this.$t('confirm'),
                    cancelButtonText: this.$t('cancel'),
                    type: 'warning'
                }).then(() => {
                    this.$parent.$parent.$parent.el_aside_loading = true
                    this.saveEdit()                  
                }).catch(() => {
                    this.showMessage(this.$t('cancel'), this.$t('cancel'), "info")      
                });  
                return ;
            }

            this.$confirm(this.$t('datasource.message.bigSizeMessage'), this.$t('tips'), {
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
            this.$confirm(this.$t('datasource.message.deleteSourceData'), this.$t('tips'), {
                confirmButtonText: this.$t('confirm'),
                cancelButtonText: this.$t('cancel'),
                type: 'warning'
            }).then(() => {
                axios({
                url:'/handle-data-source/remove-data-source',
                method:"get",
                params:{
                    'dataSourceName':this.dataSourceName
                }
                }).then(
                response => {
                    this.showMessage(this.$t('success'), "", "success")  
                    this.sourcedata = [] 
                    this.headers = []
                    this.$parent.$parent.$parent.fetchDataSourceList()       
                },
                error => {
                    this.$notify({
                        title: this.$t('tips'),
                        message: response.data.message,
                        type: 'error',
                        duration: 3000
                    });              
                })  
                this.$parent.$parent.$parent.closeSourceData()
            }).catch(() => {
                this.$notify({
                    type: 'info',
                    title: this.$t('cancel')
                });          
            });            

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
            console.log(rowIndexInDataSource)
            this.dataSourceEdit.deleteRowIndex.push(rowIndexInDataSource)
            this.removeRowAndColumn()
        },

        // This function references the code of https://www.jianshu.com/p/02d42f689c58 
        handleTwo () {
            this.dataSourceEdit.deleteColumnName.push(this.currentColumn.property)
            this.removeRowAndColumn()
        },

        deleteRow (index, row) {
            if (JSON.stringify(row) == "{}") {
                this.showMessage(this.$t('error'), this.$t('datasource.message.deleteMessage'), "error")
                return
            }
            let rowIndexInDataSource = this.getRowIndexInDataSource(row)
            this.dataSourceEdit.deleteRowIndex.push(rowIndexInDataSource)
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
            this.fetchEditStatus()
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

        fetchEditStatus() {
            axios({
            url:'/handle-data-source/get-edit-status',
            method:"get",
            params:{
                'dataSourceName':this.dataSourceName
            }
            }).then(
            response => {
                this.editStatus = JSON.parse(response.data.result)
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
                this.showMessage(this.$t('success'), "", "success")
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
                if (response.data.code == 500) {
                    this.showMessage(this.$t('fail'), response.data.message, "error")
                } else {
                    this.showMessage(this.$t('success'), "", "success")
                }
            },
            error => {
                this.showMessage(this.$t('fail'), response.data.message, "error")
            })   
            this.fetchDataSource()
            this.fetchDataSourceSize()
            this.$parent.$parent.$parent.el_aside_loading = false   
            this.dataSourceEdit.deleteColumnName = []
            this.dataSourceEdit.deleteRowIndex = []
        },

        // Get the index of row in data source
        getRowIndexInDataSource(row) {
            let rowIndexInCurrentPage
            for (let i = 0; i < this.sourcedata.length; i++) {
                if (JSON.stringify(this.sourcedata[i]) == JSON.stringify(row)) {
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
                type: type,
                duration: 1000
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

<style scoped>
@import '@/assets/css/datasource/datasource.css';
.el-table .hidden-row {
    display: none;
  }
</style>