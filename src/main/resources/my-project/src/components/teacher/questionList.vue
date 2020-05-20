<template>
    <div>
    <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
  <el-breadcrumb-item :to="{ path: '/teacherhome' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>管理</el-breadcrumb-item>
  <el-breadcrumb-item>详情</el-breadcrumb-item>
</el-breadcrumb>
<!-- 卡片 -->
<el-card class="box-card">
  <!-- 搜索和添加 -->

  <el-row :gutter="20">
      <el-col :span="7">
        <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable
        @clear="this.getQuesiontList">
        <el-button slot="append" icon="el-icon-search" @click="queryQuestion" ></el-button>
    </el-input>
      </el-col>
      <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true;getMajorList()">添加试题</el-button>
      </el-col>
  </el-row>
  <!-- 试题列表 -->
  <el-table :data="questionList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="科目" prop="subject"></el-table-column>
      <el-table-column label="题目" prop="title"></el-table-column>
      <el-table-column label="选项" prop="content"></el-table-column>
      <el-table-column label="题型" prop="type"></el-table-column>
      <el-table-column label="状态" prop="flag"></el-table-column>
      <el-table-column label="创建人" prop="createdBy" > </el-table-column>
      <el-table-column label="操作">
          <template slot-scope="scope">
               <el-tooltip class="item" effect="dark" content="修改可用状态" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="editIsFlag(scope)"></el-button>
                </el-tooltip>
          </template>
      </el-table-column>
  </el-table>
  <!-- 分页区域 -->
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="queryInfo.start"
       :page-sizes="[1, 2, 5, 10]"
      :page-size="this.queryInfo.size"
      layout="total, sizes, pager, jumper"
      :total="total">
    </el-pagination>
     </el-card>

     <!-- 添加用户的对话框 -->
    <el-dialog
        title="添加试题"
        :visible.sync="addDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form :model="addForm" :rules="addFormRules" 
        ref="addFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="题目" prop="title">
        <el-input v-model="addForm.title"></el-input>
        </el-form-item>
        <el-form-item label="选项A" prop="OptionA">
        <el-input v-model="addForm.OptionA"></el-input>
        </el-form-item>
        <el-form-item label="选项B" prop="OptionB">
        <el-input v-model="addForm.OptionB"></el-input>
        </el-form-item>
        <el-form-item label="选项C" prop="OptionC">
        <el-input v-model="addForm.OptionC" ></el-input>
        </el-form-item>
        <el-form-item label="选项D" prop="OptionD">
        <el-input v-model="addForm.OptionD" ></el-input>
        </el-form-item>
        <el-form-item label="答案" prop="answer">
        <el-input v-model="addForm.answer"></el-input>
        </el-form-item>
        <el-select v-model="value" placeholder="请选择专业" width=70px>
        <el-option
        v-for="item in this.majorList"
        :key="item.name"
        :label="item.name"
        :value="item.name">
        </el-option>
        </el-select>
        </el-form>
        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addQuestion">确 定</el-button>
        </span>
    </el-dialog>
    </div>
</template>
<script>

export default {
    name:'questionList',
     data(){
         var checkAnswer = (rule,value,cb)=>{
            const regAnswer = /^(A|B|C|D)$/;
            if(regAnswer.test(value)){
                return cb()
            }
            cb(new Error('请输入合法答案 A B C D'))
        }
         return{
             queryInfo:{
                query:'',
                start:1,
                size:5
            },
            questionList:[],
            total:0,
            addDialogVisible:false,
            addForm:{
                title:'',
                content:'',
                answer:'',
                OptionA:'',
                OptionB:'',
                OptionC:'',
                OptionD:'',
                subject:'',
                type:'1'

            },
            majorList:[],
            value:'',
            addFormRules:{
                title:[
                    {required:true,
                    message:'题目',
                    trigger:'blur'}
                ],
                answer:[
                    {required:true,
                    message:'答案',
                    trigger:'blur'},
                     {validator:checkAnswer}
                    
                ],
                OptionA:[
                    {required:true,
                    message:'选项A',
                    trigger:'blur'}
                ], 
                OptionB:[
                    {required:true,
                    message:'选项B',
                    trigger:'blur'}
                ], 
                OptionC:[
                    {required:true,
                    message:'选项C',
                    trigger:'blur'}
                ], 
                OptionD:[
                    {required:true,
                    message:'选项D',
                    trigger:'blur'}
                ], 
            },
            isFlag:''
         }
     },
    created(){
        this.getQuesiontList()
    },
    methods:{
        getMajorList(){
            this.axios.get("http://localhost:8080/question/getMajor").
            then((response)=>{
              let res = response.data;
              this.majorList = res.object;})
        },
        getQuesiontList(){
        this.axios.get("http://localhost:8080/question/queryQuestions",
       {params:{
               start:this.queryInfo.start,
               size:this.queryInfo.size,
               query:this.queryInfo.query
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('res.object')
              }
              console.log(res)
              this.questionList = res.object;
              this.total = res.total;
              console.log(this.total)
          })
            },
            handleSizeChange(newSize){
            this.queryInfo.size = newSize;
            this.getQuesiontList();
        },
        handleCurrentChange(newPage){
            this.queryInfo.start = newPage;
             this.getQuesiontList();

        },
        queryQuestion(){
            if(this.queryInfo.query == ''){
                this.getQuesiontList()
            }else{
                this.queryInfo.start=1;
                this.getQuesiontList();
            }
        },
        /*  取消添加框事件*/
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
        },
        addQuestion(){
            this.$refs.addFormRef.validate(async valid=>{
                if(!valid || this.value == '') return
                this.addForm.subject = this.value;
                this.addForm.content = this.addForm.OptionA+';'+
                                        this.addForm.OptionB+';'+
                                        this.addForm.OptionC+';'+
                                        this.addForm.OptionD+';'
                this.axios.post("http://localhost:8080/question/addQuestion",this.addForm).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error(res.object)
                        
                    }
                    this.addDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getStudentList();
                    return this.$message.success('添加试题成功')
                })
            } )
        },
        editIsFlag(scope){
            this.isFlag = scope.row.flag=='可以使用'?'N':'Y';
            this.axios.get("http://localhost:8080/question/modifyQuestionFlag",{params:{
               id:scope.row.id,
               flag:this.isFlag
           }}).then((response)=>{
               let res = response.data
                    if(res.state == 0){
                        return this.$message.error(res.object)
                    }
                    this.getQuesiontList();
                    return this.$message.success('更改有效状态成功')
           })
        }
    }
}
</script>