<template>
    <div>
        <div class="e-main">
            <div class='header'>
             <div class='icon'>
                <span style="font-size: 30px; color:#565656; position: relative;text-align: center;">考试页面</span>
                <el-row class='row'>
                <el-button class='logout' style="float:right" @click="logout"> 退出</el-button>
                </el-row>
            </div>
           
            </div>
            <div class="content">
                <img class="avatar" src="../../assets/imags/exam.jpg">
                <div class="c-left">
                    <div class="time" style="height:30px;text-align:center;">剩余时间：<br>
                        {{this.keepTime}}
                    </div><br>
                    <div class="current" style="height:30px;text-align:center;">当前进度：
                        <el-progress :text-inside="true" :stroke-width="20" :percentage="(this.answer.length/this.choiceQuestion.length)*100"></el-progress>
                    </div>
                    
                     
                </div>
                <div class="c-right">
                    <div class="title" style="height:20px;">
                       <font color="gray"> 试卷名称: {{this.name}} 总分:{{this.score}}分</font>
                    </div>
                    <div class="question" style="height:250px">
                        <div class="choice" style="height:200px;margin-top:20px">
                            <div style="height:20px;margin-bottom:20px">{{this.index+1+':'}}{{this.choiceQuestion[this.index].title}} {{this.choiceQuestion[this.index].score}}分</div>
                             <el-radio-group v-model="checkedValue" @change="getAnswer">
                               <div style="height:50px"> <el-radio :label="this.choiceQuestion[this.index].optionA"></el-radio></div>
                               <div style="height:50px"> <el-radio :label="this.choiceQuestion[this.index].optionB"></el-radio></div>
                                <div style="height:50px"><el-radio :label="this.choiceQuestion[this.index].optionC"></el-radio></div>
                               <div style="height:50px"> <el-radio :label="this.choiceQuestion[this.index].optionD"></el-radio></div>
                            </el-radio-group>

                        
                        
                          </div>
                    </div>
                    <div class="page" style="height:40px;text-align:center">
                        <el-button @click="pre">上一题</el-button>
                        <el-button @click="next">下一题</el-button>
                        <el-button type="success" @click="submit" >提交</el-button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import store from '../../store/index.js'
import Logout from '../../assets/js/Logout';
export default {
    
    name:'exam',
    data(){
        return {
            checkedValue:'',
            name:'',
            score:'',
            choiceQuestion:[],
            index:0,
            answer:[],
            choice:[],
            keepTime: '倒计时',
            limittime:120,
            settime: '',
            startTime:'',
            endTime:''
                
            
        }
    },
    created(){
        this.getPaper();
        this.StartCountDown();
        var mydate = new Date();
        this.startTime = this.formatDate(mydate);
        this.$store.commit('setStartTime',this.startTime);
        
    },
    methods:{
        formatDate(inputTime) {
            var date = new Date(inputTime);
            var y = date.getFullYear();
            var m = date.getMonth() + 1;
            m = m < 10 ? ('0' + m) : m;
            var d = date.getDate();
            d = d < 10 ? ('0' + d) : d;
            var h = date.getHours();
            h = h < 10 ? ('0' + h) : h;
            var minute = date.getMinutes();
            var second = date.getSeconds();
            minute = minute < 10 ? ('0' + minute) : minute;
            second = second < 10 ? ('0' + second) : second;
            return y + '-' + m + '-' + d+' '+h+':'+minute+':'+second;
        },
        getPaper(){
            this.axios.get("http://localhost:8080/student/getPaperDetail",
       {params:{
               id:this.$route.params.id
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
              this.name = res.object.name
              this.score = res.object.score;
              this.choiceQuestion = res.object.choiceQuestion;
              console.log(this.choiceQuestion)
          })
        },
        submit(){
            this.$confirm('即将交卷, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
            }).then(() => {
            this.$message({
                type: 'success',
                message: '交卷成功!'
                
            });
                
                console.log(this.answer)
            if(this.answer.length != this.choiceQuestion.length){
                this.$message.error("请选择完所有的问题再提交");
                return;
            }
            let idAndAnswer = '';
            let thisAnswer = '';
            for(var i=0;i<this.choiceQuestion.length;i++){
                if(this.choiceQuestion[i].optionA == this.answer[i]){
                    idAndAnswer = idAndAnswer+this.choiceQuestion[i].id;
                    idAndAnswer = idAndAnswer+'_'
                    idAndAnswer = idAndAnswer+'A;'
                    console.log("A")
                }
                if(this.choiceQuestion[i].optionB == this.answer[i]){
                    idAndAnswer = idAndAnswer+this.choiceQuestion[i].id;
                    idAndAnswer = idAndAnswer+'_'
                    idAndAnswer = idAndAnswer+'B;'
                    console.log("B")
                }
                if(this.choiceQuestion[i].optionC == this.answer[i]){
                    idAndAnswer = idAndAnswer+this.choiceQuestion[i].id;
                    idAndAnswer = idAndAnswer+'_'
                    idAndAnswer = idAndAnswer+'C;'
                    console.log("C")
                }
                if(this.choiceQuestion[i].optionD == this.answer[i]){
                    idAndAnswer = idAndAnswer+this.choiceQuestion[i].id;
                    idAndAnswer = idAndAnswer+'_'
                    idAndAnswer = idAndAnswer+'D;'
                    console.log("D")
                }
            }
            idAndAnswer=idAndAnswer.substring(0,idAndAnswer.length-1)
            console.log(idAndAnswer)
             this.axios.get("http://localhost:8080/student/getThisScore",
            {params:{
               id:this.$route.params.id,
               questionAndAnswer:idAndAnswer
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }else{
                  
                  thisAnswer = res.object;
                  console.log(thisAnswer)
                  
              }
          })
            this.axios.get("http://localhost:8080/student/submitPaper",
            {params:{
               id:this.$route.params.id,
               questionAndAnswer:idAndAnswer
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }else{
                  var mydate = new Date();
                    this.endTime = this.formatDate(mydate);
                  this.$store.commit('setEndTime',this.endTime);
                this.$router.push({ path:'/score',name:'score',params:{
                 id:this.$route.params.id,
                 answer:idAndAnswer,
                 thisAnswer:thisAnswer

                }});
                  return this.$message.success(res.object)
              }
          })
            }).catch(() => {
            this.$message({
                type: 'info',
                message: '取消提交试卷'
            });          
            });
            
            
        },
        pre()
        {
            if(this.index>0){
                this.choice[this.index] = this.checkedValue;
                this.index--;
                this.checkedValue = this.choice[this.index];
            }
        },
        next(){
            if(this.index<this.choiceQuestion.length-1){
                this.choice[this.index] = this.checkedValue;
                this.index++;
                this.checkedValue = this.choice[this.index];
                
            }
        },
        getAnswer(){
            this.answer[this.index] = this.checkedValue;
        },
         StartCountDown() {
            var mydate = new Date();
            mydate.setMinutes(mydate.getMinutes() + this.limittime);
            this.settime=mydate;

            setInterval(() => {
                this.timeDown()
            }, 100)
        },
        timeDown() {
            const endTime = new Date(this.settime);
            const nowTime = new Date();
            let leftTime = parseInt((endTime.getTime() - nowTime.getTime()) / 1000);
            //let d = parseInt(leftTime / (24 * 60 * 60));
            let h = this.formate(parseInt(leftTime / (60 * 60) % 24));
            let m = this.formate(parseInt(leftTime / 60 % 60));
            let s = this.formate(parseInt(leftTime % 60));
            if (leftTime <= 0) {
                
                alert("时间到，系统自动交卷");
                this.submit();
            }
            this.keepTime = `${h}:${m}:${s}`;
        },
        formate(time) {
            if (time >= 10) {
                return time
            } else {
                return `0${time}`
            }
        },
        logout(){
        Logout.methods.logout();
        this.isLogin=false
      },
    }
}
</script>
<style scoped>
.e-main{
    width:100%;
    height:100%;
}
.content{
    margin-top: 20px;
    height: 500px;
    overflow: hidden;

}
.avatar {
      position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
     background-color:#000;filter:Alpha(Opacity=60);opacity:0.6;
     z-index: -1;
    }
.c-left{
    width: 320px;
    height: 200px;
    margin-left: 20px;
    float: left;
}
.button1{
    height:40px;
    width:70px;
    text-align:center;
    margin:0 auto;
    margin-top: 5px;
}
.c-right{
    height: 430px;
    width:1147px;
    float: right;
    
}
</style>