<template>
    <div class="header-area">
      <div class="clever-main-menu">
        <div class="classy-nav-container breakpoint-off">
          <nav class="classy-navbar justify-content-between" id="cleverNav">
            <a class="nav-brand" href="/">硕果云</a>
            <div class="classy-menu">
              <div class="classynav">
                <el-menu :default-active="activeIndex"
                         mode="horizontal"
                         text-color="rgba(0, 0, 0, 0.45)"
                         active-text-color="#232323"
                         :unique-opened=true
                         >
                  <el-menu-item index="/index" @click="open('/home')">首页</el-menu-item>
                  <el-menu-item index="/exams" @click="open('/exams')">考试</el-menu-item>
                  <el-menu-item index="/courses" @click="open('/courses')">课程</el-menu-item>
                  <el-submenu index="/other">
                    <template slot="title">记录</template>
                    <el-menu-item index="exam-record" @click="open('/exam-record')">考试记录</el-menu-item>
                  </el-submenu>
                  <el-submenu index="/u">
                    <template slot="title">帮助</template>
                    <el-menu-item index="u-source" @click="open('https://gitee.com/wells2333/spring-microservice-exam')">
                      源码地址
                    </el-menu-item>
                    <el-menu-item index="u-deploy" @click="open('https://www.kancloud.cn/tangyi/spring-microservice-exam/1322870')">
                      部署文档
                    </el-menu-item>
                    <el-menu-item index="c-log" @click="open('https://gitee.com/wells2333/spring-microservice-exam/blob/master/CHANGELOG.md')">
                      更新日志
                    </el-menu-item>
                    <el-menu-item index="c-overview"  @click="open('https://www.kancloud.cn/tangyi/spring-microservice-exam/1322864#6__112')">
                      规划总览
                    </el-menu-item>
                    <el-menu-item index="u-admin" @click="open('http://118.25.138.130:81')">
                      管理后台
                    </el-menu-item>
                  </el-submenu>
                  <!-- <el-submenu v-if="login" index="/user-info">
                    <template slot="title">
                      <img :src="userInfo.avatarUrl" style="height: 30px;border-radius: 50%;margin-right: 6px;"/>
                      {{userInfo.identifier}}
                    </template>
                    <el-menu-item index="account" @click="open('/account')">个人中心</el-menu-item>
                    <el-menu-item index="password" @click="open('/password')">修改密码</el-menu-item>
                    <el-menu-item index="logOut" @click="logOut">退出</el-menu-item>
                  </el-submenu> -->
                </el-menu>
                <div class="register-login-area" v-if="!login">
                  <a class="btn" target="_blank" @click="open('/register')">注册</a>
                  <a class="btn" target="_blank" @click="open('/login')">登录</a>
                </div>
              </div>
            </div>
          </nav>
        </div>
      </div>
    </div>
</template>

<script>

export default {
  created () {
    this.checkLogin()
  },
  // 检测路由变化
  watch: {
    $route () {
      this.checkLogin()
    }
  },
  data () {
    return {
      activeIndex: '/index',
      login: false,
      input: '',
      query: ''
    }
  },
  methods: {
    // 导航栏切换
    // open (path) {
    //   if (path.startsWith('http')) {
    //     window.open(path)
    //     return
    //   }
    //   if (path !== this.$route.fullPath) {
    //     if (this.$route.fullPath === '/start') {
    //       this.$confirm('是否要结束当前考试?', '提示', {
    //         confirmButtonText: '确定',
    //         cancelButtonText: '取消',
    //         type: 'warning'
    //       }).then(() => {
    //         // TODO 提交当前考试
    //         this.$emit('handleSubmitExam')
    //         this.$router.push({
    //           path: path,
    //           query: {}
    //         })
    //       }).catch(() => {})
    //     } else {
    //       this.$router.push({
    //         path: path,
    //         query: {}
    //       })
    //     }
    //   }
    // },
    // 注册
    handleRegister () {
      // 先退出
      // this.logOut()
      this.$router.push('/register')
    },
    // 登录
    handleLogin () {
      this.$router.push('/login')
    },
    // 登出
    logOut () {
    },
    // 检测登录
    checkLogin () {
    },
  }
}
</script>
