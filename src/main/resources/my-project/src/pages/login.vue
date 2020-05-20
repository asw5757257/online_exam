<template>
<div class="login_container" >
  <div class="login_box">
    <div class="avatar_box">
      <img src="../assets/imags/logo.png" alt="">
    </div>
    <!-- 登陆表单 -->
    <el-form  ref="loginFormRef" label-width="0px" :model="loginForm" class="login_form" :rules="loginFormRules">
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" prefix-icon="el-icon-user"></el-input>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" prefix-icon="el-icon-lock" type="password"></el-input>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="primary" @click="login">登录</el-button>
        <el-button type="info" @click="resetPwd">找回密码</el-button>
        <el-button type="info" @click="resetLoginForm">重置</el-button>
      </el-form-item>
    </el-form>
    <el-select v-model="value" placeholder="请选择角色" class="option">
    <el-option 
      v-for="item in options"
      :key="item.value"
      :label="item.label"
      :value="item.value">
    </el-option>
  </el-select>
  </div>
  <el-dialog
  title="提示"
  :visible.sync="dialogVisible"
  width="50%">
  <el-form  ref="resetFormRef" label-width="70px" :model="resetForm" :rules="resetFormRules">
      <el-form-item prop="email" label="邮箱" >
        <el-input v-model="resetForm.email" prefix-icon="el-icon-message"></el-input>
        <el-button type="primary" style="margin-top:5px" @click="getCheckCode">获取邮箱验证码</el-button>
      </el-form-item>
      <el-form-item prop="checkCode" label="验证码">
        <el-input v-model="resetForm.checkCode" prefix-icon="el-icon-key" ></el-input>
      </el-form-item>
      <el-form-item prop="passwordNew" label="新密码">
        <el-input v-model="resetForm.passwordNew" prefix-icon="el-icon-lock" type="password"></el-input>
      </el-form-item>
      
      <el-form-item class="btns">
        <el-button type="primary"  @click="sendReset">重置</el-button>
      </el-form-item>
    </el-form>
</el-dialog>
  </div>
</template>

<script>
export default {
  name: 'login',
  data(){
     var checkEmail = (rule,value,cb)=>{
            const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if(regEmail.test(value)){
                return cb()
            }
            cb(new Error('请输入合法邮箱'))
        }
       return {
        loginForm: {
          username: '',
          password: ''
        },
        resetForm:{
                email:'',
                passwordNew:'',
                checkCode:''
            },
         resetFormRules:{
                email:[
                    {required:true,
                    message:'邮箱',
                    trigger:'blur'},
                    {validator:checkEmail}
                ],
                passwordNew:[
                    {required:true,
                    message:'请输入合法密码',
                    trigger:'blur'},{min:6,max:15,
                    message:'密码长度在6~15个字符之间',trigger:'blur'}
                ],
                checkCode:[
                    {required:true,
                    message:'请输入验证码',
                    trigger:'blur'}
                ],

         },
       
        // 表单验证，需要在 el-form-item 元素中增加 prop 属性
        loginFormRules: {
          username: [
            {required: true, message: '用户名不能为空', trigger: 'blur'},
            { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            {required: true, message: '密码不可为空', trigger: 'blur'},
            { min: 6, max: 15, message: '长度在 6 到 15 个字符', trigger: 'blur' }
          ],
          value:[
              {required: true, message: '角色不能为空', trigger: 'blur'}
          ]
        },
        value:'',
         options: [{
          value: '学生',
          label: '学生'
        }, {
          value: '教师',
          label: '教师'
        }, {
          value: '管理员',
          label: '管理员'
        }],
        // 对话框显示和隐藏
        dialogVisible: false
      }
    },
    methods: {
      resetPwd(){
        if(this.value==''){
          this.$message.error("请选择角色");
        }
        else if(this.value=='管理员'){
          this.$message.error("管理员无法修改密码");
        }
        else{
            this.dialogVisible=true
        }
        

      },
      resetLoginForm(){
        this.$refs.loginFormRef.resetFields();
      },
      login(){
        this.$refs.loginFormRef.validate((valid)=>{
          
          if(!valid) return
          if(this.value==''){
            return
          }
          if(this.value=='管理员'){
                this.axios.get("http://localhost:8080/manage/login",
            {
                params:{
                    username:this.loginForm.username,
                    password:this.loginForm.password
                }
            }).then((response)=>{
                    let res = response.data;
                    if(res.state==1){
                      this.$message.success("登陆成功");
                      window.sessionStorage.setItem("token",res.object.role)
                      this.$router.push('/managerHome')
                    }else if(res.object=="登陆失败:密码不正确"){
                      this.$message.error("密码不正确");
                    }else if(res.object=="登陆失败:用户名不存在"){
                      this.$message.error("用户名不存在");
                    }else{
                      this.$message.error("系统错误");
                    }
                })
            }
            if(this.value=='教师'){
           this.axios.get("http://localhost:8080/teacher/login",
            {
                params:{
                    username:this.loginForm.username,
                    password:this.loginForm.password
                }
            }).then((response)=>{
                    let res = response.data;
                    
                    if(res.state==1){
                      this.$message.success("登陆成功");
                      window.sessionStorage.setItem("token",res.object.role)
                      this.$router.push('/teacherHome')
                    }else if(res.object=="登陆失败:密码不正确"){
                      this.$message.error("密码不正确");
                    }else if(res.object=="登陆失败:用户名不存在"){
                      this.$message.error("用户名不存在");
                    }else{
                      this.$message.error("系统错误");
                    }
                })
            }
            if(this.value=='学生'){
           this.axios.get("http://localhost:8080/student/login",
            {
                params:{
                    username:this.loginForm.username,
                    password:this.loginForm.password
                }
            }).then((response)=>{
                    let res = response.data;
                    
                    if(res.state==1){
                      this.$message.success("登陆成功");
                      window.sessionStorage.setItem("token",res.object.role)
                      this.$router.push('/studentHome')
                    }else if(res.object=="登陆失败:密码不正确"){
                      this.$message.error("密码不正确");
                    }else if(res.object=="登陆失败:用户名不存在"){
                      this.$message.error("用户名不存在");
                    }else{
                      this.$message.error("系统错误");
                    }
                })
            }

        })
      },
      getCheckCode(){
        const reg = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
        if(!reg.test(this.resetForm.email)){
               this.$message.error('请输入正确格式邮箱')
        }else{
          if(this.value=='学生'){
            this.axios.get("http://localhost:8080/student/getCheckCodeByEmail",
            {
                params:{
                    email:this.resetForm.email
                    
                }
            }).then((response)=>{
                    let res = response.data;
                    if(res.state == 0){
                       this.$message.error(res.object);
                    }
                    if(res.state == 1){
                      this.$message.success(res.object);
                    }
                })
          }
          if(this.value=='教师'){
            this.axios.get("http://localhost:8080/teacher/getCheckCodeByEmail",
            {
                params:{
                     email:this.resetForm.email
                }
            }).then((response)=>{
                    let res = response.data;
                    if(res.state == 0){
                       this.$message.error(res.object);
                    }
                    if(res.state == 1){
                      this.$message.success(res.object);
                    }
                    
                })
          }
        }
      },
      sendReset(){
        this.$refs.resetFormRef.validate((valid)=>{
          if(!valid) return
          if(this.value=='教师'){
           this.axios.get("http://localhost:8080/teacher/checkCheckCode",
            {
                params:{
                    email:this.resetForm.email,
                    passwordNew:this.resetForm.passwordNew,
                    checkCode:this.resetForm.checkCode
                }
            }).then((response)=>{
                    let res = response.data;
                    if(res.state == 0){
                      this.$message.error(res.object)
                    }
                    if(res.state == 1){
                      this.$message.success(res.object)
                      this.$router.push('/home')
                    }
                    
                })
            }
            if(this.value=='学生'){
           this.axios.get("http://localhost:8080/student/checkCheckCode",
            {
                params:{
                    email:this.resetForm.email,
                    passwordNew:this.resetForm.passwordNew,
                    checkCode:this.resetForm.checkCode
                }
            }).then((response)=>{
                    let res = response.data;
                    if(res.state == 0){
                      this.$message.error(res.object)
                    }
                    if(res.state == 1){
                      this.$message.success(res.object)
                      this.$router.push('/home')
                    }
                    
                })
            }
        })
      }
      

  }
  
}
</script>
<style lang="scss" scoped>
.login_container{
  background-image: url('../assets/imags/bg.jpg');
  background-size:contain;
  background-repeat:no-repeat;
    background-size: 100% 100%;
  height: 100%;
}
.login_box{
  width:450px;
  height: 300px;
  background-color: #fff;
  border-radius: 3px;
  position:absolute;
  left: 50%;
  top:50%;
  transform: translate(-50%,-50%);
}
.avatar_box{
  height:130px;
  width: 250px;
  
  //border-radius: 50%;
  padding:absolute;
  box-shadow: 0 0 10 px #ddd;
  position:absolute;
  left:50%;
  transform: translate(-50%,-100%);
  //background-color: #fff;
  img{
    width:100%;
    height:100%;
    //border-radius: 50%;
    //background-color: #eee;
  }
}
.btns{
  display: flex;
  justify-content: flex-end;
}
.login_form{
  position: absolute;
  bottom: 0;
  width:100%;
  padding: 0 20px;
  box-sizing: border-box;
}
.option{
  position:absolute;
  width: 30%;
  display: flex;
  justify-content: flex-start;
  bottom: 80%;
  right: 5%;
  
}
</style>