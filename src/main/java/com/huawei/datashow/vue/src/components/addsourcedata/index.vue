<template>
  <div id="addsourcedata">
    <el-row id="addsourcedata_ways">
        <el-col :span="8">
            <el-card :body-style="{padding: '0px'}" shadow="hover" >
                <img src="@/assets/opengauss.svg" class="image" id="img_opengauss">
                <div class="description">
                    <span id="span_opengauss">{{$t('addsourcedata.span.span_opengauss')}}</span>
                    <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="toLogin">{{$t('addsourcedata.button.button_toLogin')}}</el-button>
                        <el-button type="text" class="button" @click="toUISourceData">{{$t('addsourcedata.button.button_toUISourceData')}}</el-button>
                    </div>
                </div>
            </el-card>
        </el-col>
        
        <el-col :span="8">
            <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <!-- This svg is from https://remixicon.com -->
                <img src="@/assets/file-excel-2-line.svg" class="image" id="img_excel">
                <div class="description" >
                    <span id="span_excel">{{$t('addsourcedata.span.span_excel')}}</span>
                    <div class="bottom clearfix">
                        <el-button type="text" class="button" @click="toUploadXlsOrXlsxFile">{{$t('addsourcedata.button.button_toUploadXlsOrXlsxFile')}}</el-button>
                        <el-button type="text" class="button" @click="toUploadCSVFile">{{$t('addsourcedata.button.button_toUploadCSVFile')}}</el-button>
                    </div>
                </div>
            </el-card>            
        </el-col>

        <el-col :span="8">
            <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <!-- This svg is from https://remixicon.com -->
                <img src="@/assets/file-text-line.svg" class="image" id="img_txt">
                <div class="description">
                    <span id="span_txt">{{$t('addsourcedata.span.span_txt')}}</span>
                    <div class="bottom clearfix">
                        <el-button type="text" class="button"  @click="toUploadTxtFile">{{$t('addsourcedata.button.button_toUploadTxtFile')}}</el-button><br>
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
      toUploadCSVFile(){
        router.push('/home/processeddatas/uploadcsvfile')
      },
      toUploadTxtFile(){
        router.push('/home/processeddatas/uploadtxtfile')
      },      
    }
  }
</script>

<style scoped>
  @import '@/assets/css/addsourcedata/addsourcedata.css';
</style>