<template>
  <el-container id="container">
    <el-header id="header">
      <img id="img_logo" alt="Vue logo" src="../../assets/opengauss.svg" align="center" >
    </el-header>
    <el-main id="main">
      <router-view></router-view>
    </el-main>
  </el-container>
</template>

<style scoped>
  @import '@/assets/css/home/home.css';
</style>

<script>
import router from '@/router';
  export default {
    data() {
      return {}
    },
    computed: {},
    components:{},
    methods: {},
    mounted(){
      router.push('/home/processeddatas/addsourcedata')
    }
  };
</script>