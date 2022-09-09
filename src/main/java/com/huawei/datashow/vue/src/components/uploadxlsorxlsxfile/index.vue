<template>
  <el-upload
    class="upload-demo"
    action="/uploadXlsOrXlsxFile"
    ref="upload"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :auto-upload="false"
    :limit="1"
    :on-exceed="handleExceed"
    :on-success="handleSuccess"
    :on-error="handleError"
    >
    <el-button slot="trigger" size="small" type="primary">{{$t('uploadxlsorxlsxfile.button.button_select_file')}}</el-button>
    <el-button id="button-submit" size="small" type="success" @click="submitUpload">{{$t('uploadxlsorxlsxfile.button.button_submitUpload')}}</el-button>
    <el-button type="info" size="small" @click="back" class="login-submit">{{$t('uploadcsvfile.button.button_back')}}</el-button> 
    <div slot="tip" class="el-upload__tip">{{$t('uploadxlsorxlsxfile.div.make_sure')}}</div>
    <div slot="tip" class="el-upload__tip">{{$t('uploadxlsorxlsxfile.div.size')}}</div>
  </el-upload>
</template>
<script>
import router from '@/router'
export default {
  data() {
    return {
      headers:{
        ContentType:"multipart/form-data"
      }
    };
  },
  methods: {
    handleExceed(files, fileList) {
      this.$message.warning(this.$t('uploadcsvfile.message.file_num'));
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    beforeRemove(file, fileList) {
      return this.$confirm(this.$t('remove') + `${ file.name }？`);
    },
    handleSuccess(response) {
      if (response.code == 200) {
        this.$notify({
          title: this.$t('tips'),
          message: response.message,
          type: 'success'
        });  
        this.$parent.$parent.$parent.fetchDataSourceList()
      } else {
        this.$notify({
          title: this.$t('tips'),
          message: response.message,
          type: 'error'
        });         
      }
    },
    handleError(err) {
      this.$notify({
          title: this.$t('tips'),
          message: this.$t('uploadcsvfile.message.upload_error'),
          type: 'error'
        }); 
    },
    back() {
      router.push("/home/processeddatas/addsourcedata")
    }
  }
}
</script>

<style scoped>
@import '@/assets/css/uploadxlsorxlsxfile/uploadxlsorxlsxfile.css';
</style>