<template>
  <el-menu :default-active="activeIndex" style="width: 95%;margin-left: 2.5%;margin-top: 10px" class="el-menu-demo" mode="horizontal" @select="handleSelect">
    <el-menu-item index="1">公告</el-menu-item>
    <el-menu-item index="2" >题目</el-menu-item>
    <el-menu-item index="3" >排名</el-menu-item>
    <el-menu-item index="4" >状态</el-menu-item>
  </el-menu>

</template>

<script>
export default {
  name: "Menu",
  methods:{
    handleSelect(key) {
      switch (key) {
        case '1':
          this.$router.push('/workNotice/'+this.$route.params.id)
          break
        case '2':
          this.$router.push('/workPro/'+this.$route.params.id)
          break
        case '3':
          this.$router.push('/workRank/'+this.$route.params.id)
          break
        case '4':
          this.$router.push('/workState/'+this.$route.params.id)
          break
      }
    },
  }
}
</script>

<style scoped>
.me{
  margin-top: 50px;
}
</style>
