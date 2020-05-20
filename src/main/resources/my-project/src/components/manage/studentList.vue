<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
  <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
  <el-breadcrumb-item>管理</el-breadcrumb-item>
  <el-breadcrumb-item>详情</el-breadcrumb-item>
</el-breadcrumb>
<el-card class="box-card">
    <el-row :gutter="20">
      <el-col :span="7">
        <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable 
        @clear="queryStudent">
        <el-button slot="append" icon="el-icon-search" @click="queryStudent"></el-button>
    </el-input>
      </el-col>
      <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
      </el-col>
  </el-row>
  <!-- 教师列表 -->
  <el-table :data="studentList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="姓名" prop="name"></el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="学号" prop="studentId"></el-table-column>
      <el-table-column label="专业" prop="major"></el-table-column>
      <el-table-column label="年级" prop="grade"></el-table-column>
      <el-table-column label="性别" prop="sex"></el-table-column>
      <el-table-column label="电话" prop="tel"></el-table-column>
      <el-table-column label="邮箱" prop="email"></el-table-column>
      
      <el-table-column label="角色" >
          <template slot-scope="scope" >
            <el-tag type="info" :key="role">{{role}}</el-tag>
            <el-tooltip class="item" effect="dark" content="重置密码" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="resetPwd(scope.row.id)"></el-button>
                </el-tooltip>
          </template>
      </el-table-column>
      <el-table-column label="操作">
           <template slot-scope="scope">
               <el-tooltip class="item" effect="dark" content="编辑" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope)"></el-button>
                </el-tooltip>
                <el-tooltip class="item" effect="dark" content="删除" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-delete" size="mini" @click="removeStudent(scope.row.id)"></el-button>
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
        title="添加用户"
        :visible.sync="addDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form :model="this.addForm" :rules="addFormRules" 
        ref="addFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="用户名" prop="username">
        <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
        <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="姓名" prop="name">
        <el-input v-model="addForm.name"></el-input>
        </el-form-item>
        <el-form-item label="学号" prop="stuNum">
        <el-input v-model="addForm.stuNum"></el-input>
        </el-form-item>
        <el-form-item label="性别" prop="sex">
        <el-input v-model="addForm.sex" ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="tel">
        <el-input v-model="addForm.tel" ></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
        <el-input v-model="addForm.email"></el-input>
        </el-form-item>
        <el-select v-model="value" placeholder="请选择专业" width=70px>
        <el-option
        v-for="item in this.majorList"
        :key="item.name"
        :label="item.name"
        :value="item.id">
        </el-option>
        </el-select>
        </el-form>
        <!-- 底部 -->
        <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addStudent">确 定</el-button>
        </span>
    </el-dialog>

    <el-dialog
    title="修改学生" @close="editDialogClosed"
    :visible.sync="editDialogVisible"
    width="50%">
    <el-form ref="editFormRef" :model="editForm" label-width="70px" :rules="editFormRules">
        <el-form-item label="用户名">
            <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop=email>
            <el-input v-model="editForm.email" ></el-input>
        </el-form-item>
        <el-form-item label="手机号" prop="tel">
            <el-input v-model="editForm.tel" ></el-input>
        </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editStudentInfo()">确 定</el-button>
    </span>
    </el-dialog>
    </div>
</template>
<script>
export default {
    name:'studentList',
    data(){
        var checkEmail = (rule,value,cb)=>{
            const regEmail = /^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/
            if(regEmail.test(value)){
                return cb()
            }
            cb(new Error('请输入合法邮箱'))
        }

        var checkTel = (rule,value,cb)=>{
            const regTel = /^1[3456789]\d{9}$/
            if(regTel.test(value)){
                return cb()
            }
            cb(new Error('请输入合法手机号'))
        }

        var checkSex = (rule,value,cb)=>{
            if(this.addForm.sex=='男'||this.addForm.sex=='女'){
                 return cb()
            }
            cb(new Error('请输入正确性别:男 女'))
        }
        return{
            queryInfo:{
                query:'',
                start:1,
                size:5
            },
            studentList:[],
            majorList:[],
            total:0,
            role:'学生',
            value:'',
            addDialogVisible:false,
            editDialogVisible:false,
            addForm:{
                username:'',
                stuNum:'',
                password:'',
                name:'',
                sex:'',
                tel:'',
                email:'',
                majorId:'',
            },
            addFormRules:{
                username:[
                    {required:true,
                    message:'请输入用户名',
                    trigger:'blur'},{min:3,max:10,
                    message:'用户名长度在3~10个字符之间',trigger:'blur'}
                ],
                password:[
                    {required:true,
                    message:'密码',
                    trigger:'blur'},{min:6,max:15,
                    message:'用户名长度在6~15个字符之间',trigger:'blur'}
                ],
                name:[
                    {required:true,
                    message:'姓名',
                    trigger:'blur'}
                ],
                sex:[
                    {required:true,
                    message:'性别',
                    trigger:'blur'},
                    {validator:checkSex}
                ],
                tel:[
                    {required:true,
                    message:'手机号',
                    trigger:'blur'},
                    {validator:checkTel}
                ],
                email:[
                     {required:true,
                    message:'邮箱',
                    trigger:'blur'},
                    {validator:checkEmail}
                    
                ],
                grade:[
                    {required:true,
                    message:'年级',
                    trigger:'blur'}
                ]

            },
            editForm:{
            },
            editFormRules:{
                email:[
                    {required:true,
                    message:'邮箱',
                    trigger:'blur'},
                    {validator:checkEmail}
                ],
                tel:[
                    {required:true,
                    message:'手机号',
                    trigger:'blur'},
                    {validator:checkTel}
                ]
            },
        }
    },
    created(){
        this.getStudentList();
        this.getMajorList();
    },
    methods:{
        getMajorList(){
            this.axios.get("http://localhost:8080/manage/getMajor").
            then((response)=>{
              let res = response.data;
              this.majorList = res.object;})
        },
        getStudentList(){
            this.axios.get("http://localhost:8080/manage/queryStudent",
       {params:{
               start:this.queryInfo.start,
               size:this.queryInfo.size,
               query:this.queryInfo.query
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error('获取教师列表失败')
              }
              console.log(res)
              this.studentList = res.object;
              this.total = res.total;
              console.log(this.total)
          })
        },
        handleSizeChange(newSize){
            this.queryInfo.size = newSize;
            this.getStudentList();
        },
        handleCurrentChange(newPage){
            this.queryInfo.start = newPage;
             this.getStudentList();

        },
        queryStudent(){
            if(this.queryInfo.query == ''){
                this.getStudentList()
            }else{
                this.queryInfo.start=1;
                this.getStudentList();
            }
        },
        /*  取消添加框事件*/
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
        },
        addStudent(){
            this.$refs.addFormRef.validate(async valid=>{
                if(!valid || this.value == '') return
                this.addForm.majorId = this.value;
                this.axios.post("http://localhost:8080/manage/addOrModifyStudent",this.addForm).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error('添加学生失败')
                        
                    }
                    this.addDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getStudentList();
                    return this.$message.success('添加学生成功')
                })
            } )
        },
        editDialogClosed(){
            this.$refs.editFormRef.resetFields();
        },
        editStudentInfo(){
            this.$refs.editFormRef.validate(valid=>{
                if(!valid) return
                console.log(this.editForm)
                this.axios.post("http://localhost:8080/manage/addOrModifyStudent",this.editForm).
                then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error('修改学生失败')
                        
                    }
                    this.editDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getStudentList();
                    return this.$message.success('修改学生成功')
                })

            })
        },
        /* 获取当前行数据 */
        showEditDialog(scope){
            this.editDialogVisible = true;
            this.editForm = scope.row;
        },
        /* 根据id删除 */
        removeStudent(id){
          this.$confirm('此操作将永久删除该教师, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.axios.get("http://localhost:8080/manage/delStudent",{
                    params: {
                    id: id
                    }
                }).then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error('删除学生失败')
                        
                    }
                    this.editDialogVisible = false;
                    this.queryInfo.start = 1;
                    this.getStudentList();
                    return this.$message.success('删除学生成功')
                })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          });          
        });
        
        },
        resetPwd(id){
            this.$confirm('此操作将重置该教师密码为123456, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.axios.get("http://localhost:8080/manage/resetStudentPwd",{
                    params: {
                    id: id
                    }
                }).then((response)=>{
                    let res = response.data
                    if(res.state == 0){
                        
                        return this.$message.error(res.object)
                        
                    }
                    this.queryInfo.start = 1;
                    this.getStudentList();
                    return this.$message.success(res.object)
                })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消重置密码'
          });          
        });
        }
    }
}
</script>
<style lang="less" coped>

</style>