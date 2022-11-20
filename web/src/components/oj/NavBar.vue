<template>
  <div>
    <el-menu :default-active="activeIndex" active-text-color="#ff5252cc" class="el-menu-demo" mode="horizontal" router>
      <el-menu-item class="zimeLogo">ZIME Online Judge</el-menu-item>
      <el-menu-item index="/home"><i class="el-icon-s-home"></i>主页</el-menu-item>
      <el-menu-item index="/problem"><i class="el-icon-s-grid"></i>问题</el-menu-item>
      <el-menu-item index="/work"><i class="el-icon-s-order"></i>竞赛/作业</el-menu-item>
      <el-menu-item index="4"><i class="el-icon-s-platform"></i>课程</el-menu-item>
      <el-menu-item index="/state"><i class="el-icon-s-marketing"></i>状态</el-menu-item>
      <el-menu-item index="6"><i class="el-icon-s-data"></i>排名</el-menu-item>
      <el-menu-item index="7"><i class="el-icon-question"></i>常见问题</el-menu-item>

      <!--      操作用户-->
      <el-menu-item-group class="user-menus">
        <el-dropdown trigger="click">
          <el-button type="text" style="display: flex;">
            <el-image v-if="user.headPortrait" :src="user.headPortrait" fit="cover" class="zimeUserImage"/>
            <el-image v-else :src="require('@/assets/img/image/user.png')" class="zimeUserImage"/>
            <i class="el-icon-caret-bottom"/>
          </el-button>
          <el-dropdown-menu>
            <el-dropdown-item>
              <el-link type="text" @click="to_user_information_page" :underline="false">个人主页</el-link>
            </el-dropdown-item>

            <el-dropdown-item v-if="isAdmin" divided>
              <el-link type="text" @click="to_admin_page" :underline="false">后台管理</el-link>
            </el-dropdown-item>

            <el-dropdown-item divided>
              <el-link type="text" @click="logout" :underline="false">退出登录</el-link>
            </el-dropdown-item>

          </el-dropdown-menu>
        </el-dropdown>
      </el-menu-item-group>
    </el-menu>

  </div>
</template>

<script>

export default {
  data() {
    return {
      activeIndex: this.$route.path,
      user: {
        username: 'admin',
        password: '123456',
        headPortrait: "", // 头像
      },
      dialogFormVisible: false,
      circleUrl: "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png",
      userName: 'admin',

      isAdmin: true,


    }
  },
  methods: {

    // 跳转到用户信息界面
    to_user_information_page() {
      this.$router.push('/user/' + this.user.id)
    },

    // 跳转到后台管理页面
    to_admin_page() {
      this.$router.push('/admin')
    },

    // 退出用户
    logout() {
      this.$store.commit('logout')

      this.$message.success("退出成功")
    }

  }
}
</script>

<style>
.zimeLogo {
  font-size: 1.5rem;
  font-weight: bold;
  color: #303133;
}

/* 移动到字体上出现下划线 */
.zimeLogo:after {
  content: '';
  width: 100%;
  height: 2px;
  position: absolute;
  bottom: 0;
  left: 0;
  transform: scaleX(0);
  background-color: #000000;
  transition-duration: 0.2s;
}

.zimeLogo:hover:after {
  transform: scaleX(1);
}

.zimeUserImage {
  height: 30px;
  width: 30px;
  margin-top: -25%;
  border-radius: 50%;
}

.user-menus {
  margin-right: 1%;
  float: right;
}

</style>
