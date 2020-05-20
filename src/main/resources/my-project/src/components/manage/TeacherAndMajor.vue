<template>
    <div>
        <!-- 面包屑导航 -->
        <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/managerHome' }">首页</el-breadcrumb-item>
        <el-breadcrumb-item>管理</el-breadcrumb-item>
        <el-breadcrumb-item>详情</el-breadcrumb-item>
        </el-breadcrumb>
        <!-- 教师列表 -->
  <el-table :data="teacherList" border stripe>
      <el-table-column type="index"></el-table-column>
      <el-table-column label="姓名" prop="name" ></el-table-column>
      <el-table-column label="用户名" prop="username"></el-table-column>
      <el-table-column label="角色" width='100px'>
          <template >
            <el-tag type="info" :key="role">{{role}}</el-tag>
          </template>
      </el-table-column>
      <el-table-column label="操作">
           <template slot-scope="scope">
               <el-tooltip class="item" effect="dark" content="查询关联专业" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-search" size="mini" @click="showMajor(scope)"></el-button>
                </el-tooltip>  
                <el-tooltip class="item" effect="dark" content="增加关联专业" placement="top" :enterable="false">
                <el-button type="primary" icon="el-icon-plus" size="mini" @click="showMajorDialog(scope)"></el-button>
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

    <el-dialog
        title="专业"
        :visible.sync="addDialogVisible"
        width="50%" @close="addDialogClosed">
        <el-form 
        ref="addFormRef" 
        label-width="70px" status-icon >
        <el-form-item label="名字">
        <el-input v-model="addName" :disabled="true"></el-input>
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
        <el-button type="primary" @click="addMajor">确 定</el-button>
        </span>
    </el-dialog>

    <el-dialog
        title="专业表"
        :visible.sync="majorDialogVisible"
        width="50%" >
        
        <el-table :data="teacherAndMajorList" style="width: 100%">
    <el-table-column >
      <template slot-scope="props">
        <el-form label-position="left" inline class="demo-table-expand">
          <el-form-item label="专业名称:">
            <span>{{ props.row.name }}</span>
          </el-form-item>
          <el-form-item label="年级:">
            <span>{{ props.row.grade }}</span>
          </el-form-item>
          <el-form-item label="创建时间:">
            <span>{{ props.row.createTime }}</span>
          </el-form-item>
           <el-form-item label="操作:">
                <el-button @click="delMajor(props)" type="text" size="small">删除</el-button>
            </el-form-item>
        </el-form>
      </template>
    </el-table-column>
        </el-table>
    </el-dialog>
    </div>
</template>
<script>
export default {
    name:'teacherAndMajor',
    data(){
        return{
            queryInfo:{
                query:'',
                start:1,
                size:5
            },
            
            teacherList:[],
            majorList:[],
            teacherAndMajorList:[],
            role:'教师',
            total:0,
            majorDialogVisible:false,
            addDialogVisible:false,
            id:'',
            addId:'',
            addName:'',
            value:''

            
        }
    },
    created(){
        this.getTeacherList()
    },
    methods:{
        showMajor(scope){
             this.axios.get("http://localhost:8080/manage/getTeacherAndMajor",
       {params:{
               id:scope.row.id,
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
              console.log(res)
              this.teacherAndMajorList = res.object.majorList;
              console.log(this.teacherAndMajorList)
              this.majorDialogVisible = true
              this.id=scope.row.id
          })
        },
        getTeacherList(){
            this.axios.get("http://localhost:8080/manage/queryTeacher",
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
              this.teacherList = res.object;
              this.total = res.total;
              console.log(this.total)
          })
        },
        handleSizeChange(newSize){
            this.queryInfo.size = newSize;
            this.getTeacherList();
        },
        handleCurrentChange(newPage){
            this.queryInfo.start = newPage;
             this.getTeacherList();

        },
        /*  取消添加框事件*/
        addDialogClosed(){
            this.$refs.addFormRef.resetFields();
        },
        delMajor(props){
            console.log(props.row)
            this.axios.get("http://localhost:8080/manage/delTeacherAndMajor",
       {params:{
               mid:props.row.id,
               tid:this.id
               
           }}).then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }
                this.majorDialogVisible = false
                return this.$message.success('删除成功')
                
              
          })
        },
        showMajorDialog(scope){
            this.addDialogVisible = true
            this.addId = scope.row.id
            this.addName = scope.row.name
            this.getMajor()
        },
        getMajor(){
            this.axios.get("http://localhost:8080/manage/getMajor").
            then((response)=>{
              let res = response.data;
              this.majorList = res.object;})
        },
        addMajor(){
            this.axios.get("http://localhost:8080/manage/addTeacherAndMajor",
            {params:{
               mid:this.value,
               tid:this.addId
               
           }}).
            then((response)=>{
              let res = response.data;
              if(res.state == 0){
                  return this.$message.error(res.object)
              }else{
                  this.addDialogVisible = false
                  return this.$message.success(res.object)
              }
              })
        }
    }
}
</script>
<style scoped>

</style>