<template>
  <el-container style="background-color: rgb(238,238,238)">
    <el-header>
      <NavBar :user="user" :isAdmin="isAdmin" ref="navBar"/>
    </el-header>
    <el-main class="oj-content">
      <router-view @refreshUser="getUser"/>
    </el-main>
  </el-container>
</template>
<script>
import NavBar from '../../components/oj/NavBar'
import {getuser} from "@/api/user";
import {getToken} from "@/utils/auth";

export default {
  name: 'oj-app',
  components: {
    NavBar
  },
  data() {
    return {
      user: {},
      isAdmin: true,
    }
  },

  created() {
    this.check_admin()
    this.getUser()

  },

  methods: {
    check_admin() {
      // if (JSON.parse(localStorage.getItem("user")).menus)
      //   this.isAdmin = true
    },

    getUser() {
      getuser().then(res => {
        this.user = res.data
      })
      // const id = JSON.parse(localStorage.getItem("user")).id
      // this.request.get("user/" + id).then(res => {
      //   if (res.code === '200') {
      //     this.user = res.data
      //   } else {
      //     this.$message.error(res.message)
      //   }
      // })
    },

  },
}
</script>


<style>
.oj-content {
  margin-top: 20px;
  padding: 0 3%;
}
</style>

