<template>
<div>
    <div style="height:100%; width:100%">    
  
      <div class='header'>
        <div class='icon'>
            <span style="font-size: 30px; color:#565656; position: relative;text-align: center;margin-top:20px">考试系统</span>
                <el-row class='row'>
                <el-button class='login' style="float:right" @click="login" v-if="!isLogin">登录</el-button>
                <el-button class='login' style="float:right" v-if="isLogin" @click="logout">退出</el-button>
               
                </el-row>
            
        </div>
      </div>
      <div class='content'>
        <div class='content-mask'>
            <div class='word'>
                <h2>在线考试系统</h2>
                <h4>基于Spring Boot搭建的在线考试系统</h4>
                <h4>提供用户管理、考试等功能</h4>
                
            </div>
            <div class='button'>
            <el-row>
                <el-button type="primary" size="medium"  @click="ToHome">开始使用</el-button>
            </el-row>
            </div>
      </div>
      </div>
      <div class="elRow">
      <el-row :gutter="20">
        <el-col :span="8"><div class="grid-content bg-purple">
            <span class='number'>
            <h2 ><countTo :startVal='startVal' :endVal='100' :duration='3000'></countTo></h2>
            <h3>试卷数</h3>
            </span>
            </div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple"><span class='number'>
            <h2><countTo :startVal='startVal' :endVal='300' :duration='3000'></countTo></h2>
            <h3>试题数</h3>
            </span></div></el-col>
        <el-col :span="8"><div class="grid-content bg-purple"><span class='number'>
            <h2><countTo :startVal='startVal' :endVal='50' :duration='3000'></countTo></h2>
            <h3>学生数</h3>
            </span></div></el-col>
        </el-row>
        </div>

        <div class="lunbo">
            <div class="block1">
            <span class="demonstration"></span>
            <el-carousel height="250px" :interval="5000" arrow="always">
            <el-carousel-item v-for="(img,index) in imgList1" :key="index">
            <img v-bind:src="img.url">
            </el-carousel-item>
            </el-carousel>
            </div>
            <div class="block2">
            <span class="demonstration"></span>
            <el-carousel height="250px" :interval="5000" arrow="always">
            <el-carousel-item v-for="(img,index) in imgList2" :key="index">
            <img v-bind:src="img.url">
            </el-carousel-item>
            </el-carousel>
            </div>
        </div>
<o-footer></o-footer>
</div>
</div>
</template>
<script>
import Logout from '../assets/js/Logout';
import countTo from 'vue-count-to';
import OFooter from './common/footer'
export default {
    components: { OFooter,countTo },
    data () {
      return {
        startVal: 0,
        endVal: 2017,
        imgList1:[
        {url:require('../assets/imags/轮播1.jpg')},
        {url:require('../assets/imags/轮播2.jpg')},
        {url:require('../assets/imags/轮播3.jpg')},
        {url:require('../assets/imags/轮播4.jpg')},
        {url:require('../assets/imags/轮播5.jpg')},
        {url:require('../assets/imags/轮播6.jpg')},
        
      ],
      imgList2:[
        {url:require('../assets/imags/轮播7.jpg')},
        {url:require('../assets/imags/轮播8.jpg')},
        {url:require('../assets/imags/轮播9.jpg')},
        {url:require('../assets/imags/轮播10.jpg')},
        {url:require('../assets/imags/轮播11.jpg')},
        {url:require('../assets/imags/轮播12.jpg')},
        
      ],
      isLogin:false,
      role:''
      }
    },
    created(){
        if(this.$cookies.get('User') != null){
            this.isLogin = true;
        }
        this.role = this.$cookies.get('User');
    },
    methods:{
        login(){
            this.$cookies.get('User')
            this.$router.push({name:'login',path:'/login'});
        },
        ToHome(){
            if(this.$cookies.get('User') != null){
                if(this.role == '0'){
                     this.$router.push({path:'/managerHome'});
                }
                if(this.role == '1'){
                    this.$router.push({path:'/teacherHome'});
                }
                if(this.role == '2'){
                    this.$router.push({path:'/studentHome'});
                }
            }else{
                this.$router.push({name:'login',path:'/login'});
            }
        },
        logout(){
        Logout.methods.logout();
        this.isLogin=false
      },
        
    }
    
}
</script>
<style lang="less" scoped>
.header{
    height: 20px;
}
.icon{
    width:90%;
    height: 50px;
}
.row{
    width: 10%;
    position: relative;
    text-align:right;
    text-align:center;
    margin-top: 10px;
    top: 0%;
    float:right 
}
.elRow{
    height: 400px;
}
.login{
    position: relative;
    text-align:center;
    
}
.el-main{
    padding-left: 0;
    
}
.el-footer{
    padding-left: 0;
    
}
.content{
    position:relative;
    top: 0%;
    height: 300px;
    background: url("../assets/imags/bg2.jpg") no-repeat ;
    background-size: 100% 300px;
}
.content h2{
    position: relative;
    text-align: center;
    top: 0%;
    z-index: 10;
    color: #fff;
    font-size: 40px;
}
.content h4{
    position: relative;
    text-align: center;
    z-index: 10;
    color: #fff;
    font-size: 25px;
}
.content-mask{
    height: 300px;
    background-color:#000;filter:Alpha(Opacity=60);opacity:0.6;
}
.word{
    height:70%;
    position: relative;
    top: 15%;
}
.button{
    position: relative;
    bottom: -10%;
    text-align:center;
    margin: 0 auto;
    
    
}
.el-col{
    height:190px;
}
.bg-purple{
    height:100%;
    background: url("../assets/imags/star.png") no-repeat ;
    background-position: center;
}
.bg-purple h2 h2{
    color: gray;
}
.number{
    position: relative;
    text-align:center;
    margin: 0 auto;
    top: 80%;
}
.footer-container{
    height: 200px;
    background-color: black;
}

.lunbo{
    height:300px;
    width: 100%;
}
  .block1{
    height:300px;
    width: 50%;
    float: left;
    margin: 0 auto;

  }
  .block2{
    height:300px;
    width: 50%;
    float: right;
    margin: 0 auto;

  }
  .el-carousel__item {
  width: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  .carousel-image {
    max-width: 100%;
    max-height: 100%;
  }
}
</style>