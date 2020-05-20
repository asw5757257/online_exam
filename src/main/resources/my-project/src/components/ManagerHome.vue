<template>
    
        <el-container style="height: 100%;">
  <el-header>
      <div>
        <img src="../assets/imags/test.png" alt="" style="width:5%; height:auto">
        <span>管理员系统</span>
      </div>
      <el-button type="info" style="float:right" @click="logout">退出</el-button>
  </el-header>
    
  <el-container>
    <el-aside :width="isCollapse? '64px' : '200px'">
      <div class="toggle-button" @click="toggleCollapse">|||</div>
    <el-menu
      background-color="#373D41"
      text-color="#fff"
      active-text-color="#409eff"
      unique-opened
      :collapse = "isCollapse"
      :collapse-transition="false"
      :router="true"
      :default-active="$route.path"
      >
      <el-submenu :index="item.menuId+''" v-for="item in menulist" :key="item.menuId">
        <template slot="title">
          <i :class="iconObj[item.menuId]"></i>
          <span>{{item.menuName}}</span>
        </template>
        <el-menu-item :index="'/'+subitem.path" v-for="subitem in item.children" :key="subitem.menuId">
           <template slot="title">
                <i class="el-icon-menu"></i>
            </template>
            <span>{{subitem.menuName}}</span>
             
        </el-menu-item>
      </el-submenu>
    </el-menu>
    </el-aside>

    <el-main>
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>
    
</template>

<script>
import Menu from '../mock/menu.json';
import Logout from '../assets/js/Logout';
export default {
    name:"managerHome",
    data(){
      return{
        //左侧菜单数据
        menulist:[],
        iconObj:{
          '1':'el-icon-s-custom',
          '2':'el-icon-user',
          '3':'el-icon-reading'
        },
        isCollapse:false
      }
    },
    created(){
      this.getMenuList()
    },
    methods:{
      logout(){
        Logout.methods.logout();
      },
      getMenuList(){
        let res = Menu.data;
        this.menulist=res;
      },
      toggleCollapse(){
        this.isCollapse = !this.isCollapse;
      }
    }
}
</script>

<style lang="less" scoped>
    .el-header{
        background-color: #373d41;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color:#fff;
        font-size: 20px;
        > div{
            display: flex;
            align-items: center;
            span{
                margin-left: 15px;
            }
        }
        
    }
    .el-aside{
        background-color: #373d41;
        .el-menu{
          border-right: 0;
        }
    }
    .el-main{
        background-color: #eaedf1;
    }
    .iconfont{
      margin-right: 10px;
    }
    .el-icon-menu{
      margin-left: 10px;
      
    }
    .toggle-button{
      background-color: #4a5064;
      font-size: 10px;
      line-height: 24px;
      color:#fff;
      text-align: center;
      letter-spacing: 0.2em;
      cursor: pointer;
    }
    
</style>