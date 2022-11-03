<template>
  <el-menu :default-active="activeIndex" class="el-menu-demo"  @select="handleSelect"
           style="height: 100%"
           background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b">
    <el-menu-item>
      <div style="display:inline-block;font-size: 1.3rem">ZIME Online Judge</div>
    </el-menu-item>
    <el-menu-item index="1"><i class="el-icon-s-home"></i>主页</el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>问题管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="2-1">用户列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="3"><i class="el-icon-s-order"></i>竞赛管理</el-menu-item>

    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-location"></i>
        <span>用户管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="4-1">用户</el-menu-item>
        <el-menu-item index="4-2">选项2</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

  </el-menu>

</template>

<script>
export default {
  name: "adminNavBar",
  methods: {
    handleSelect(key) {
      switch (key) {
        case '1':
          this.$router.push('/')
          breakW
        case '2-1':
          this.$router.push('/adminProblem')
          break
        case '3':
          this.$router.push('/adminwork')
          break
        case '4-1':
          this.$router.push('/adminuser')
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
