<template>
  <el-container style="height: 100vh; border: 1px solid #eee">
    <el-aside width="15%" style="height: 100vh;">
      <admin-nav-bar></admin-nav-bar>
    </el-aside>
    <el-main width="70%">
      <router-view></router-view>
    </el-main>

  </el-container>
</template>

<script>
import adminwork from "@/views/admin/work/work";
import adminNavBar from "@/components/admin/NavBar";
export default {
  name: "AdminIndex",
  components:{adminwork,adminNavBar},
  data () {
    return {
      count: 0
    }
  },
  methods: {
    load () {
      this.count += 2
    }
  }
}

</script>

<style scoped>
html,body,#app{
  height:100%;
  margin: 0px;
  padding: 0px;
}
</style>
