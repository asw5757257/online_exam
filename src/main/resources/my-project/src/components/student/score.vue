<template>
    <div class="all">
        <div class="header">
            <div>
            <img src="../../assets/imags/test.png" alt="" style="width:5%; height:auto">
            <span>试卷结果页面</span>
           
        </div>
        <el-button type="info" style="float:right" @click="logout">退出</el-button>
        </div>
        <div class="main">
            <el-card class="box-card">
                <div class="detail">
                    <el-row :gutter="20">
                        <el-col :span="10"><p style="border-top:1px silver solid;text-align:center"></p></el-col>
                        <el-col :span="2"><div class="grid-content bg-purple" style=" text-align:center;color: #606266;">成绩详情</div></el-col>
                        <el-col :span="10"><p style="border-top:1px silver solid;text-align:center"></p></el-col>
                    </el-row>
                    <el-row :gutter="20" style="margin-top:5px">
                        <el-col :span="8"><div class="text1">考试名称：{{this.paperName}}</div></el-col>
                        <el-col :span="8"><div class="text1">考生姓名：{{this.name}}</div></el-col>
                        <el-col :span="8"><div class="text1">总得分：{{this.score}}</div></el-col>
                    </el-row>
                    <el-row :gutter="20" style="margin-top:5px">
                        <el-col :span="8"><div class="text1">开始时间：{{this.$store.getters.getStartTime}}</div></el-col>
                        <el-col :span="8"><div class="text1">结束时间：{{this.$store.getters.getEndTime}}</div></el-col>
                        <el-col :span="8"><div class="text1">耗时：({{this.value}})</div></el-col>
                    </el-row>
<!-- 
                    <el-row :gutter="20" style="margin-top:5px">
                        <el-col :span="24"> -->
                            <div v-for="(questions, index) in choiceQuestion" v-bind:key="index">
                                <div class="question">
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        
                                        <el-col :span="24">{{index+1}}:{{questions.title}}:</el-col>
                                        <el-col :span="24">{{questions.score}}分</el-col>
                                        
                                    </el-row>
                                    </div>
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        
                                        <el-col :span="24">A:{{questions.optionA}}</el-col>
                                        
                                    </el-row>
                                    </div>
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        
                                        <el-col :span="24">B:{{questions.optionB}}</el-col>
                                        
                                    </el-row>
                                    </div>
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        
                                        <el-col :span="24">C:{{questions.optionC}}</el-col>
                                    </el-row>
                                    </div>
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        <el-col :span="24">D:{{questions.optionD}}</el-col>
                                    </el-row>
                                    </div>
                                    <div class="col">
                                    <el-row :gutter="20" style="margin-top:5px">
                                        
                                        <el-col :span="24">参考答案:{{correct[index]}}</el-col>
                                        <el-col :span="24">你的答案:{{incorrect[index]}}</el-col>
                                        
                                    </el-row>
                                    </div>
                                </div>
                            </div>
                        <!-- </el-col>
                        
                    </el-row> -->
                 
                </div>
                <div class="list">

                </div>
            </el-card>
        </div>
        <div class="footer">
            <div class="btn">
            <el-button plain @click="returnHome">返回试卷主页</el-button>
            </div>
        </div>
    </div>
</template>
<script>
import store from '../../store/index.js'
export default {
    name:'score',
    store,
    data(){
        return{
            id:'',
            myAnswer:this.$route.params.answer,
            answer:this.$route.params.thisAnswer,
            choiceQuestion:[],
            correct:[],
            incorrect:[],
            name:'',
            paperName:'',
            score:'',
            value:''
        }
    },
    created(){
        this.getPaper();
        this.setAnswer();
        this.getUser();
        this.getScore();
        this.value = this.getValue(this.$store.getters.getStartTime,this.$store.getters.getEndTime)
        
        
    },
    methods:{
        getPaper(){
            this.axios.get("http://localhost:8080/student/getPaperDetail",
       {params:{
               id:this.$route.params.id
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
              this.paperName = res.object.name
              
              this.choiceQuestion = res.object.choiceQuestion;
              console.log(this.choiceQuestion)
          })
        },

        getScore(){
            this.axios.get("http://localhost:8080/student/queryThisScore",
       {params:{
               id:this.$route.params.id
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
              
              this.score = res.object;
              console.log(this.score)
              
          })
        },
        getUser(){
            this.axios.get("http://localhost:8080/student/getUser",).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
              
              this.name = res.object.name;
              
          })
        },
        setAnswer(){
            var answerStr = this.myAnswer.split(';');
            var correctStr = this.answer.split(';');
            for(var i = 0; i < answerStr.length; i++){
                var str = answerStr[i].split('_');
                var cstr = correctStr[i].split('_');
                this.incorrect[i] = str[1];
                this.correct[i] = cstr[1];
            }
            console.log(this.incorrect);
            console.log(this.correct);
            
        },
        getValue(time1,time2){
        time1 = new Date(time1);    //转换为中国标准时间
        time2 = new Date(time2);
        time1 = time1.getTime();    //转换为时间戳
        time2 = time2.getTime();
        var runTime = (time2 - time1) / 1000;       //开始得出时间差,然后计算
              
        runTime = runTime % (86400 * 365);
        
        runTime = runTime % (86400 * 30);
        
        runTime = runTime % 86400;
        var hour = Math.floor(runTime / 3600);
        runTime = runTime % 3600;
        var minute = Math.floor(runTime / 60);
        runTime = runTime % 60;
        var second = runTime;
        
        return hour+'小时:'+minute+'分:'+second+"秒";
    },
    returnHome(){
         this.$router.push({ path:'/studentHome',name:'studentHome'})
    }
    }
}
</script>
<style lang="less"  scoped>
.all{
    height: 100%;
    width:100%;
}
.header{
    height: 10%;
    width:100%;
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
.main{
    
    width: 100%;
}
.detail{
    width: 70%;
    height: 120px;
    position: relative;
    left: 15%;
    
}
.box-card{
    height: 100%;
}
.text1{
    text-align: right;
    vertical-align: middle;
    float: left;
    font-size: 14px;
    color: #606266;
    line-height: 40px;
    padding: 0 12px 0 0;
    -webkit-box-sizing: border-box;
    box-sizing: border-box;
}
.question{
    background: #f6f7fa;
    border-radius: 4px;
    margin-bottom: 21px;
    padding: 12px 0 12px 22px;
    position: relative;
    min-height: 240px;
    color: #666;
    text-align: left;
}
.col{
    margin-top: 20px;
}
.footer{
    margin-top:10px;
}
.btn{
    width:126px;
    margin: 0 auto;
}
</style>