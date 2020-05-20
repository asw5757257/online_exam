<template>
    <div>
        <div class="header">
        <div>
            <img src="../assets/imags/test.png" alt="" style="width:5%; height:auto">
            <span>学生考试系统</span>
        </div>
        <el-button type="info" style="float:right" @click="logout">退出</el-button>
    </div>
    <div class="notice">
        <el-table
      :data="noticeList" stripe
      style="width: 100%">
      <el-table-column prop="noticeContent" label="通知内容" width="800">
      </el-table-column>
      <el-table-column prop="createTime" label="发布日期" width="400">
      </el-table-column>
      
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.start"
       :page-sizes="[1, 2, 5, 10]"
      :page-size="this.queryInfo.size"
      layout="total, sizes, pager, jumper"
      :total="total">
    </el-pagination> 
    </div>
        <div class='menu'>
            <el-button type="primary" @click="getAll">全部试卷</el-button>
            <el-button type="primary" @click="getUnfinish">未完成试卷</el-button>
            <el-button type="primary" @click="getScore">全部成绩</el-button>
        </div>
        <div class='paper'>
            <el-row>
        <el-col :span="5" v-for="(item, index) in paperList" :key="index" :offset="index >= 0 ? 2 : 0">
            <el-card :body-style="{ padding: '0px'}">
        <img src="../assets/imags/paper1.jpg" class="image">
        <div style="padding: 14px;">
        <span>{{item.paperName}}</span>
        <div class="bottom clearfix">
          <time class="time">{{ item.publicTime }}</time>
          
            <el-button type="text" class="button" @click="toExam(item)">进入考试</el-button>
          
        </div>
      </div>
    </el-card>
  </el-col>
</el-row>
        </div>

<el-dialog
        title="成绩表"
        :visible.sync="scoreDialogVisible"
        width="52%"  >
        
            <el-table :data="score" border style="width: 100%" fit>
            <el-table-column fixed prop="studentName" label="学生" >
            </el-table-column>
            <el-table-column prop="studentId" label="学号" >
            </el-table-column>
            <el-table-column prop="major" label="专业" >
            </el-table-column>
            <el-table-column prop="paperName" label="试卷" >
            </el-table-column>
            <el-table-column prop="score" label="成绩" >
            </el-table-column>
            <el-table-column prop="finishTime" label="完成时间" >
            </el-table-column>
        </el-table>


        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="scoreDialogVisible = false">确定</el-button>
        </span>
    </el-dialog>

    </div>
</template>
<script>
import Logout from '../assets/js/Logout';
export default {

     data() {
    return {
        queryInfo:{
                start:1,
                size:5
            },
            total:10,
        noticeList:[],
        paperList:[],
        currentDate: new Date(),
        score:[],
        scoreDialogVisible:false
        
    }
     },
     created(){
        this.getNoticeList();
        this.getAllPaperList();
       
        
    },
     methods:{
            

         handleSizeChange(newSize){
            this.queryInfo.size = newSize;
            this.getNoticeList();
        },
        handleCurrentChange(newPage){
            this.queryInfo.start = newPage;
             this.getNoticeList();

        },
        getNoticeList(){
            this.axios.get("http://localhost:8080/notice/showNotice",
       {params:{
               start:this.queryInfo.start,
               size:this.queryInfo.size,
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('获取通知列表失败')
              }
              console.log(res)
              this.noticeList = res.object;
              this.total = res.total;
              console.log(this.total)
          })
        },
        getAllPaperList(){
            this.axios.get("http://localhost:8080/student/getAllPapers").then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('获取试卷列表失败')
              }
              console.log(res)
              this.paperList = res.object;
              
              
          })
        },
        getAll(){
            this.getAllPaperList();
        },
        getUnfinish(){
            this.axios.get("http://localhost:8080/student/getUnfinishedPaper").then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('获取试卷列表失败')
              }
              console.log(res)
              this.paperList = res.object;
              
              
          })
        },
        getScore(){
            this.scoreDialogVisible = true;
            this.axios.get("http://localhost:8080/student/queryScore").then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('获取成绩列表失败')
              }
              console.log(res)
              this.score = res.object;
              
              
          })
        },
        toExam(item)
        {   
            this.$confirm('即将进入考试页面, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
            this.$message({
                type: 'success',
                message: '开始考试!'
                
            });
                console.log(item)
                this.$router.push({name:'exam',params:{id:item.paperId}});
            }).catch(() => {
            this.$message({
                type: 'info',
                message: '已经取消进入考试'
            });          
            });
            
        },
        logout(){
        Logout.methods.logout();
        this.isLogin=false
        }
     }
}
</script>
<style lang="less" scoped>
.header{
        background-color: #fff;
        display: flex;
        justify-content: space-between;
        padding-left: 0;
        align-items: center;
        color:#000;
        font-size: 20px;
        > div{
            display: flex;
            align-items: center;
            span{
                margin-left: 15px;
            }
        }
        
    }

    .time {
    font-size: 13px;
    color: #999;
  }
  
  .bottom {
    margin-top: 13px;
    line-height: 12px;
  }

  .button {
    padding: 0;
    float: right;
  }

  .image {
    width: 100%;
    display: block;
  }

  .clearfix:before,
  .clearfix:after {
      display: table;
      content: "";
  }
  
  .clearfix:after {
      clear: both
  }
  .menu{
      width: 80%;
      height: 100px;
      position: relative;
      margin-left: 120px;
      
  }
  .paper{
      width: 100%;
      
  }
  .notice{
      width: 80%;
      height: 350px;
      position: relative;
      margin-left:120px;
  }
</style>