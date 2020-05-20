<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/teacherHome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>教师</el-breadcrumb-item>
        <el-breadcrumb-item>详情</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card class="box-card">
        <el-row :gutter="20">
      <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">发布通知</el-button>
      </el-col>
  </el-row>
        <el-table :data="noticeList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="通知" prop="noticeContent" ></el-table-column>
      <el-table-column label="发布日期" prop="createTime"></el-table-column>
      <el-table-column label="操作">
           <template slot-scope="scope">
               <el-tooltip class="item" effect="dark" content="编辑" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope)"></el-button>
                </el-tooltip>
           </template>
      </el-table-column>
  </el-table>
        </el-card>
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

    <el-dialog
        title="通知"
        :visible.sync="addDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form :model="addForm" :rules="addFormRules" 
        ref="addFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="通知" prop="noticeContent">
        <el-input v-model="addForm.noticeContent"></el-input>
        </el-form-item>
        <el-form-item label="是否有效" prop="flag">
        <el-input v-model="addForm.flag"></el-input>
        </el-form-item>
        </el-form>
        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addNotice">确 定</el-button>
        </span>
    </el-dialog>

     <el-dialog
    title="修改通知" 
    :visible.sync="editDialogVisible"
    width="50%">
    <el-form ref="editFormRef" :model="editForm" label-width="70px" :rules="addFormRules">
        <el-form-item label="通知内容">
            <el-input v-model="editForm.noticeContent" ></el-input>
        </el-form-item>
        <el-form-item label="是否有效" prop=flag>
            <el-input v-model="editForm.flag" ></el-input>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editNotice()">确 定</el-button>
    </span>
    </el-dialog>
    </div>
</template>
<script>

export default {
    name:'notice',
    data(){
       var checkFlag  = (rule,value,cb)=>{
            const regAnswer = /^(Y|N)$/;
            if(regAnswer.test(value)){
                return cb()
            }
            cb(new Error('请输入合法状态 Y N'))
        }
        return{
            queryInfo:{
                start:1,
                size:5
            },
            addForm:{
                noticeContent:'',
                flag:''
            },
            editForm:{
                noticeContent:'',
                flag:'',
                id:''
            },
            noticeList:[],
            total:0,
            addDialogVisible:false,
            editDialogVisible:false,
            addFormRules:{
                noticeContent:[
                    {required:true,
                    message:'通知内容',
                    trigger:'blur'}
                ],
                flag:[
                    {required:true,
                    message:'是否有效',
                    trigger:'blur'},
                     {validator:checkFlag}
                    
                ],
            }

        }
    },
    created(){
        this.getNoticeList()
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
         /*  取消添加框事件*/
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
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
        addNotice(){
            this.$refs.addFormRef.validate(valid=>{
                if(!valid) return
            this.axios.post("http://localhost:8080/notice/addOrModifyNotice",this.addForm
       ).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }else{
                  this.addDialogVisible = false
                return this.$message.success(res.object)
              }
              
              
          })
          })
        },
        showEditDialog(scope){
            this.editDialogVisible = true;
            this.editForm = scope.row;
            console.log(scope.row)
        },
        editNotice(){
            this.$refs.editFormRef.validate(valid=>{
                if(!valid) return
            this.axios.post("http://localhost:8080/notice/addOrModifyNotice",this.editForm
       ).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }else{
                  this.editDialogVisible = false
                return this.$message.success(res.object)
              }
              
              
          })
          })
        }
    }
}
</script>
<style scoped>

</style>