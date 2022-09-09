<template>
  <div id="addsourcedata">
    <el-row id="addsourcedata_ways">
        <el-col :span="12">
            <el-card :body-style="{padding: '0px'}" shadow="hover" >
                <img src="@/assets/database-2-line.svg" class="image" id="img_database">
                <div class="description">
                    <span id="span_opengauss">{{$t('addsourcedata.span.span_opengauss')}}</span>
                    <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="toLogin">{{$t('addsourcedata.button.button_toLogin')}}</el-button>
                        <el-button type="text" class="button" @click="toUISourceData">{{$t('addsourcedata.button.button_toUISourceData')}}</el-button>
                    </div>
                </div>
            </el-card>
        </el-col>
        
        <el-col :span="12">
            <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <!-- This svg is from https://remixicon.com -->
                <img src="@/assets/folder-upload-line.svg" class="image" id="img_excel">
                <div class="description" >
                    <span id="span_excel">{{$t('addsourcedata.span.span_excel')}}</span>
                    <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="toUploadXlsOrXlsxFile">{{$t('addsourcedata.button.button_toUploadXlsOrXlsxFile')}}</el-button>
                        <el-button type="text" class="button"  @click="toUploadCSVOrTxtFile">{{$t('addsourcedata.button.button_toUploadCSVOrTXTFile')}}</el-button>
                    </div>
                </div>
            </el-card>            
        </el-col>
    </el-row>   
  </div> 
</template>

<script>
import router from '@/router';
  export default {
    data() {
      return {};
    },
    methods: {
      toLogin(){
        this.$parent.$parent.addSourceDataIsActive = false
        router.push('/home/processeddatas/login')
      },
      toUISourceData(){
        router.push('/home/processeddatas/uisourcedata')
      },
      toUploadXlsOrXlsxFile(){
        router.push('/home/processeddatas/uploadxlsorxlsxfile')
      },
      toUploadCSVOrTxtFile(){
        router.push('/home/processeddatas/uploadcsvortxtfile')
      }
    }
  }
</script>

<style scoped>
  @import '@/assets/css/addsourcedata/addsourcedata.css';
</style>