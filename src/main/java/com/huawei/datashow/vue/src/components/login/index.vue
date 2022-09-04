<template>
    <el-form
        :model="form"
        status-icon
        :rules="rules"
        ref="form"
        label-width="100px"
        class="login_container"
        id = "login_container"
        size="mini"
        v-loading.fullscreen.lock="loading"
        :element-loading-text="$t('loading.text')"
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)">

        <h3 class="login_title">{{$t('login.form.formName')}} 
            <el-tooltip class="item" placement="top-start" effect="light">
                <div slot="content">  
                    <h3>
                        1.{{$t('login.tips.pg_hba_conf1')}}                 
                        <el-link href="https://opengauss.org/zh/docs/2.1.0/docs/Developerguide/配置服务端远程连接.html" target="_blank">{{$t('login.tips.pg_hba_conf2')}}</el-link>  
                        {{$t('login.tips.pg_hba_conf3')}}<br>
                        2.{{$t('login.tips.ban_omm')}}
                    </h3>
                </div>
                <i class="el-icon-info"></i>
            </el-tooltip>
        </h3>
        
        <el-form-item 
            :label="$t('login.form.pollName')"
            label-width="80px"
            prop="pollName"
        >
            <el-input 
                type="input" 
                v-model="form.pollName"
                autocomplete="off">
            </el-input>
        </el-form-item>  

        
        <el-form-item 
            :label="$t('login.form.host')"
            label-width="80px"
            prop="host"
        >
            <el-input 
                type="input" 
                v-model="form.host"
                autocomplete="off">
            </el-input>
        </el-form-item>      
        

        <el-form-item 
            :label="$t('login.form.port')"
            label-width="80px"
            prop="port"
        >
            <el-input 
                type="input" 
                v-model="form.port"
                autocomplete="off"
                :placeholder="$t('login.form.portPlaceholder')">
            </el-input>
        </el-form-item>  
        
        <el-form-item 
            :label="$t('login.form.database')"
            label-width="80px"
            prop="database"
        >
            <el-input 
                type="input" 
                v-model="form.database"
                autocomplete="off">
            </el-input>
        </el-form-item>         

        <el-form-item 
            :label="$t('login.form.username')"
            label-width="80px"
            prop="username"
            class="username"
        >
            <el-input 
                type="input" 
                v-model="form.username"
                autocomplete="off">
            </el-input>
        </el-form-item>

        <el-form-item 
            :label="$t('login.form.password')"
            label-width="80px"
            prop="password"
        >
            <el-input 
                type="password" 
                v-model="form.password"
                autocomplete="off">
            </el-input>
        </el-form-item>      
        <el-button type="primary" size="small" @click="login" class="login-submit">{{$t('confirm')}}</el-button>   
        <el-button type="info" size="small" @click="back" class="login-submit">{{$t('back')}}</el-button>          
    </el-form>
    
    
</template>

<script>
import axios from 'axios'
import router from '@/router'

export default {
    name:'Login',
    data(){
        return{
            loading:false,
            form:{
                username:'',
                password:'',
                host:'',
                port:'',
                database:'',
                pollName:''
            },
            rules:{
                username:[
                    { required : true, message:this.$t('login.message.notNull'),trigger:"blur"}
                ],
                password:[
                    { required : true, message:this.$t('login.message.notNull'),trigger:"blur"}
                ],
                host:[
                    { required : true, message:this.$t('login.message.notNull'),trigger:"blur"}
                ],
                database:[
                    { required : true, message:this.$t('login.message.notNull'),trigger:"blur"}
                ],
                pollName:[
                    { required : true, message:this.$t('login.message.notNull'),trigger:"blur"}
                ]
                
            }
        }
    },
    computed: {
        DTOform(){
            return {
                username:this.form.username,
                password:this.form.password,
                url:'jdbc:opengauss://' + this.form.host + ':' + this.form.port + '/' + this.form.database,
                pollName:this.form.pollName,
                driverClassName:'org.opengauss.Driver' 
            }                
                     
        }
    },
    methods:{
        async login(){
            if(this.form.port == ''){
                this.form.port = '5432'
            }
            this.$refs['form'].validate((valid) => {
                if(valid){
                   this.submit()
                }
            })
        },
        async submit() {
            this.loading = true
            await axios({
                url:'/updateDataSource/addHikariCP',
                method:"post",
                data:this.DTOform,
            }).then(
                response => {
                    if(response.data.code == 200){
                        router.push('/home/processeddatas/uisourcedata')  
                        this.$notify({
                            title: this.$t('success'),
                            type: "success",
                            message: response.data.message,
                        });                                            
                    }else {
                        this.$alert(response.data.message, this.$t('tips'), {
                            confirmButtonText: this.$t('confirm')
                        });
                    }                        
                },
                error => {}
            )
            this.loading = false 
        },
        back() {
            router.push("/home/processeddatas/addsourcedata")
        }
    },
    mounted() {},
}
</script>

<style scoped>
    @import '@/assets/css/login/login.css';
</style>

