<template>
 <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;">
   <el-collapse accordion>
     <el-collapse-item>
       <template slot="title">
         <i class="header-icon el-icon-info"  style="color: #3498db;font-size: 20px">账号设置</i>
       </template>
    <el-form  :model="user" :rules="rules" ref="user" class="demo-form-inline">
    <el-form-item label="昵称">
      <el-input v-model="user.nick"></el-input>
    </el-form-item>
    <el-form-item label="学校" >
      <el-input v-model="user.school"></el-input>
    </el-form-item>
    <el-form-item label="当前密码" prop="possword">
      <el-input v-model="user.possword" ></el-input>
    </el-form-item>
    <el-form-item label="新密码">
      <el-input v-model="possword1" ></el-input>
    </el-form-item>
    <el-form-item label="确认新密码">
      <el-input v-model="possword2"></el-input>
    </el-form-item>
    <el-form-item label="邮箱">
      <el-input v-model="user.email"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" @click="submitForm('user')">保存</el-button>
    </el-form-item>

     </el-form>
     </el-collapse-item>

     <el-collapse-item>
       <template slot="title">
         <i class="header-icon el-icon-info"  style="color: #3498db;font-size: 20px">资料设置</i>
       </template>
       <el-avatar :size="120" :src="require('@/assets/img/image'+user.avatar)"></el-avatar>
     <el-upload
         class="upload-demo"
         drag
         :data= userid
         :before-upload="beforeAvatarUpload"
         action="http://localhost:8081/api/users/upload2"
         multiple>
       <i class="el-icon-upload"></i>
       <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
       <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过2MB</div>
     </el-upload>
       <mavon-editor v-model="user.introduce" :toolbars=bars />
       <el-button type="primary" @click="up">主要按钮</el-button>
     </el-collapse-item>

   </el-collapse>
 </el-card>
</template>

<script>
import {getuser,formuser,updateuser} from "@/api/user";

export default {
  name: "userData",
  data(){
    return{
      bars:{
        bold: true, // 粗体
        italic: true, // 斜体
        header: true, // 标题
        underline: true, // 下划线
        strikethrough: true, // 中划线
        mark: true, // 标记
        superscript: true, // 上角标
        subscript: true, // 下角标
        quote: true, // 引用
        ol: true, // 有序列表
        ul: true, // 无序列表
        link: true, // 链接
        imagelink: false, // 图片链接
        code: true, // code
        table: true, // 表格
        fullscreen: true, // 全屏编辑
        readmodel: true, // 沉浸式阅读
        htmlcode: true, // 展示html源码
        help: true, // 帮助
        /* 1.3.5 */
        undo: true, // 上一步
        redo: true, // 下一步
        trash: true, // 清空
        save: true, // 保存（触发events中的save事件）
        /* 1.4.2 */
        navigation: true, // 导航目录
        /* 2.1.8 */
        alignleft: true, // 左对齐
        aligncenter: true, // 居中
        alignright: true, // 右对齐
        /* 2.2.1 */
        subfield: true, // 单双栏模式
        preview: true, // 预览
      },
      user:null,
      userid:{
        userid:null
      },
      possword:null,
      possword1:null,
      possword2:null,
      rules: {
        possword: [
          { required: true, message: '请输入密码'},
        ],
      },
    }
  },

  methods:{
    getList(){
      getuser().then(response=>{
        this.user = response.data;
        this.userid.userid=this.user.userId
      })
    },

    //账号设置提交
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
         if(this.possword1!=this.possword2){
           this.$notify.error({
             title: '错误',
             message: '两次密码错误'
           });
         }
         else {
           formuser(this.user,this.possword1).then(res=>{
             this.$message({
               showClose: true,
               message: '修改成功',
               type: 'success'
             });
           })
         }
        }
      });
    },

    //资料设置提交
    up(){
      updateuser(this.user).then(res=>{
        this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        });
      })
    },

    //图片上传限制
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 2;

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!');
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!');
      }
      return isJPG && isLt2M;
    }
  },
  created() {
    this.getList()
  }
}
</script>

<style scoped>

</style>
