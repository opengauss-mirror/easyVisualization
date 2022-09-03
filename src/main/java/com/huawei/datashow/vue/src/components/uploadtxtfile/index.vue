<template>
  <el-upload
    class="upload-demo"
    action="/uploadTXTFile"
    ref="upload"
    :on-remove="handleRemove"
    :before-remove="beforeRemove"
    :auto-upload="false"
    :limit="1"
    :on-exceed="handleExceed"
    >
    <el-button slot="trigger" size="small" type="primary">{{$t('uploadtxtfile.button.button_select_file')}}</el-button>
    <el-button id="button-submit" size="small" type="success" @click="submitUpload">{{$t('uploadtxtfile.button.button_submitUpload')}}</el-button>
    <div slot="tip" class="el-upload__tip">{{$t('uploadtxtfile.div.make_sure')}}</div>
  </el-upload>
</template>
<script>
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
    }
  }
}
</script>

<style scoped>
@import '@/assets/css/uploadtxtfile/uploadtxtfile.css';
</style>