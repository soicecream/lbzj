<template>
  <el-menu :default-active="activeIndex" @select="handleSelect"
           style="height: 100%" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
           class="el-menu-demo">
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

    <el-submenu index="3">
      <template slot="title">
        <i class="el-icon-s-order"></i>
        <span>竞赛管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="3-1">竞赛列表</el-menu-item>
      </el-menu-item-group>
    </el-submenu>

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
  data() {
    return {
      activeIndex: "",
    }
  },

  methods: {
    handleSelect(key) {
      switch (key) {
        case '1':
          this.$router.push('/')
          break
        case '2-1':
          this.$router.push('/admin/problem')
          break
        case '3-1':
          this.$router.push('/admin/work')
          break
        case '4-1':
          this.$router.push('/admin/user')
          break
      }
    }
  }
}
</script>

<style scoped>

</style>
