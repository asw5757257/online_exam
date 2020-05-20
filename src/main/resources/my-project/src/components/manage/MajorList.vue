<template>
    <div>
        
         <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/managerHome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>管理</el-breadcrumb-item>
        <el-breadcrumb-item>详情</el-breadcrumb-item>
        </el-breadcrumb>
        <el-card class="box-card">
        <el-row :gutter="20">
      <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加</el-button>
      </el-col>
  </el-row>
        <el-table :data="majorList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="名字" prop="name" ></el-table-column>
      <el-table-column label="年级" prop="grade"></el-table-column>
      <el-table-column label="创建时间" prop="createTime"></el-table-column>
    <el-table-column label="上次修改时间" prop="updateTime"></el-table-column>
      
      <el-table-column label="操作">
           <template slot-scope="scope">
               <el-tooltip class="item" effect="dark" content="修改" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit(scope)"></el-button>
                </el-tooltip>  
                <el-tooltip class="item" effect="dark" content="删除" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-delete" size="mini" @click="deleteMajor(scope)"></el-button>
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
    <!-- 增加 -->
    <el-dialog
        title="专业"
        :visible.sync="addDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form :model="this.addForm" :rules="addFormRules" 
        ref="addFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="专业" prop="name">
        <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
        <el-input v-model="addForm.grade"></el-input>
        </el-form-item>
        </el-form>
        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addMajor">确 定</el-button>
        </span>
    </el-dialog>
    <!-- 修改 -->
    <el-dialog
        title="专业"
        :visible.sync="editDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form :model="this.editForm" :rules="addFormRules" 
        ref="editFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="专业" prop="name">
        <el-input v-model="editForm.name"></el-input>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
        <el-input v-model="editForm.grade"></el-input>
        </el-form-item>
        </el-form>
        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editMajor">确 定</el-button>
        </span>
    </el-dialog>
    </div>
</template>
<script>
export default {
    name:'majorList',
    created(){
        this.getMajor()
    },
    data(){
        var checkGrade = (rule,value,cb)=>{
            const reg = /^\d{4}$/
            if(reg.test(value)){
                return cb()
            }
            cb(new Error('请输入正确年纪'))
        }
        return{
            queryInfo:{
                query:'',
                start:1,
                size:5
            },
            majorList:[],
            total:0,
            addDialogVisible:false,
            editDialogVisible:false,
            addForm:{
                name:'',
                grade:''
            },
            editForm:{
                
            },
            addFormRules:{
                name:[
                    {required:true,
                    message:'请输入专业名字',
                    trigger:'blur'}
                ],
                grade:[
                    {required:true,
                    message:'请输入年级',
                    trigger:'blur'},
                     {validator:checkGrade}
                ]
            }
        }
    },
    methods:{
        getMajor(){
            this.axios.get("http://localhost:8080/manage/queryMajor",
       {params:{
               start:this.queryInfo.start,
               size:this.queryInfo.size,
               query:this.queryInfo.query
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
             
              this.majorList = res.object;
              this.total = res.total
              
          })
        },
        handleSizeChange(newSize){
            this.queryInfo.size = newSize;
            this.getMajor();
        },
        handleCurrentChange(newPage){
            this.queryInfo.start = newPage;
             this.getMajor();

        },
        /*  取消添加框事件*/
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
        },
        addMajor(){
            this.$refs.addFormRef.validate(async valid=>{
                if(!valid || this.value == '') return
                this.axios.post("http://localhost:8080/manage/addOrModifyMajor",this.addForm).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error(res.object)
                        
                    }
                    this.addDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getMajor();
                    return this.$message.success(res.object)
                })
            } )
        },
        edit(scope){
            this.editDialogVisible = true
            this.editForm = scope.row;
        },
        editMajor(){
            this.$refs.editFormRef.validate(async valid=>{
                if(!valid || this.value == '') return
                this.axios.post("http://localhost:8080/manage/addOrModifyMajor",this.editForm).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error(res.object)
                        
                    }
                    this.editDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getMajor();
                    return this.$message.success(res.object)
                })
            } )
        },
        deleteMajor(scope){
            this.axios.get("http://localhost:8080/manage/delMajor",{params:{
               id:scope.row.id
           }}).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error(res.object)
                        
                    }
                    
                    this.queryInfo.start = 1;
                    this.getMajor();
                    return this.$message.success(res.object)
                })
        }
    }
}
</script>
<style scoped>

</style>