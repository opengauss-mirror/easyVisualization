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
        
    >
        <h3 class="login_title">{{$t('login.form.formName')}}</h3>
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
        <el-button type="primary" @click="login" class="login-submit">{{$t('login.button.confirmButton')}}</el-button>
        
    </el-form>
</template>

<script>
import axios from 'axios'
import router from '@/router'

export default {
    name:'Login',
    data(){
        return{
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
        login(){
            if(this.form.port == ''){
                this.form.port = '5432'
            }
            this.$refs['form'].validate((valid) => {
                if(valid){
                    axios({
                        url:'/updateDataSource/addHikariCP',
                        method:"post",
                        data:this.DTOform,
                    }).then(
                        response => {
                            if(response.data == 'success'){
                                router.push('/home/processeddatas/uisourcedata')  
                                this.$notify({
                                    title: $t('success'),
                                    type: "success"
                                });                                            
                            }else{
                                this.$notify({
                                    title: $t('fail'),
                                    type: "error"
                                });
                            }
                        },
                        error => {}
                    )                    
                }
            })

            
        },
    },
    mounted() {
        console.log(this.$t)
    },
}
</script>

<style scoped>
    @import '@/assets/css/login/login.css';
</style>

