<template>
  <el-menu :default-active="activeIndex" router
           style="height: 100%" background-color="#545c64" text-color="#fff" active-text-color="#ffd04b"
           class="el-menu-demo">
    <el-menu-item>
      <div style="display:inline-block;font-size: 1.3rem">ZIME Online Judge</div>
    </el-menu-item>
    <el-menu-item index="1"><i class="el-icon-s-home"/>主页</el-menu-item>
    <el-submenu index="2">
      <template slot="title">
        <i class="el-icon-location"/><span>问题管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/admin/problem">用户列表</el-menu-item>
        </el-menu-item-group>
    </el-submenu>
    <el-menu-item index="/admin/work"><i class="el-icon-s-order"/>竞赛管理</el-menu-item>

    <el-submenu index="4">
      <template slot="title">
        <i class="el-icon-location"/><span>用户管理</span>
      </template>
      <el-menu-item-group>
        <el-menu-item index="/admin/user">用户</el-menu-item>
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

  methods: {},

}
</script>

<style scoped>

</style>
