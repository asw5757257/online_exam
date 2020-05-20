<template>
   <div class="login_container" >
  <div class="login_box">
    <div class="avatar_box">
      <img src="../assets/imags/logo.png" alt="">
    </div>
    <!-- 登陆表单 -->
    <el-form  ref="loginFormRef" label-width="0px" :model="resetForm" class="login_form" :rules="loginFormRules">
      <el-form-item prop="email">
        <el-input v-model="resetForm.email" prefix-icon="el-icon-email"></el-input>
        <el-button type="primary">获取邮箱验证码</el-button>
      </el-form-item>
      <el-form-item prop="passwordNew">
        <el-input v-model="resetForm.passwordNew" prefix-icon="el-icon-lock" type="password"></el-input>
      </el-form-item>
      <el-form-item class="btns">
        <el-button type="info" >重置</el-button>
      </el-form-item>
    </el-form>
  </div>
  </div>
</template>
<script>
export default {
    name:'resetPwd',
    data(){
        return{
            resetForm:{
                email:'',
                passwordNew:'',
                checkCode:''
            },

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