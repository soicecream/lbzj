<template>
  <div>
  <el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item>
      <div style="display:inline-block;font-size: 1.3rem">ZIME Online Judge</div>
    </el-menu-item>
    <el-menu-item index="1"><i class="el-icon-s-home"></i>主页</el-menu-item>
    <el-menu-item index="2"><i class="el-icon-s-grid"></i>问题</el-menu-item>
    <el-menu-item index="3"><i class="el-icon-s-order"></i>竞赛/作业</el-menu-item>
    <el-menu-item index="4"><i class="el-icon-s-platform"></i>课程</el-menu-item>
    <el-menu-item index="5"><i class="el-icon-s-marketing"></i>状态</el-menu-item>
    <el-menu-item index="6"><i class="el-icon-s-data"></i>排名</el-menu-item>
    <el-menu-item index="7"><i class="el-icon-question"></i>常见问题</el-menu-item>
    <div v-show="!loginState" style="margin-top:10px;float:right;height: 100%">
      <el-button round @click="dialogFormVisible=true">登录</el-button>
      <el-button round @click="handleRegister">注册</el-button>
    </div>

    <div v-show="loginState" style="margin-top:10px;float:right;height: 100%">
      <span>
        <el-avatar size="medium" :src="circleUrl"></el-avatar>
      </span>
      <span>
        <el-dropdown style="height: 100%" @command="handleCommand">
          <span class="el-dropdown-link" style="font-size: 1.5rem;color: black">
          {{ userName }}<i class="el-icon-arrow-down el-icon--right"></i>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item command="page">个人主页</el-dropdown-item>
            <el-dropdown-item command="management" >后台管理</el-dropdown-item>
            <el-dropdown-item command="logOut">退出登录</el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </span>
    </div>
  </el-menu>

    <el-dialog :visible.sync="dialogFormVisible" title="登录页面">
      <el-form :model="loginform">
        <el-form-item label="账号" label-width="200px">
          <el-input v-model="loginform.username" autocomplete="off" />
        </el-form-item>
        <el-form-item label="密码" label-width="200px">
          <el-input v-model="loginform.password" autocomplete="off" />
        </el-form-item>
        <el-form-item  label-width="200px">
          <el-button>注册</el-button>
          <el-button type="primary" @click="Login">登录</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import {login,logout} from '@/api/user'
import {setToken, getToken, removeToken} from "@/utils/auth";

export default {
  data() {
    return {
      activeIndex: '1',
      loginform:{
        username:'',
        password:'',
      },
      dialogFormVisible:false,
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      userName:'admin',
      loginState : getToken()==undefined?false:true
    }
  },
  methods: {
    handleSelect(key) {
      switch (key) {
        case '1':
          this.$router.push('/home')
          break
        case '2':
          this.$router.push('/problem')
          break
        case '3':
          this.$router.push('/work')
          break
        case '5':
          this.$router.push('/state')
          break
        case '6':
          this.$router.push('/ranking')
          break
        default:
          this.$router.push('/home')
          break
      }
    },
     Login(){
      login(this.loginform).then(res=>{
        this.dialogFormVisible=false;
        this.loginState=true;
        setToken(res.data);
        this.$message('登录成功');
      })
    },
    handleRegister(){
     this.$store.commit("changeSum",5);
    },
    handleCommand(c){
     switch (c){
       case 'management':
         this.$router.push('/admin')
         break
      case 'logOut':
          logout().then(res=>{
            removeToken()
            this.loginState=false;
          })
        break
       case 'page':
       this.$router.push('/user/null')
     }
    }

  }
}
</script>

<style>

</style>
