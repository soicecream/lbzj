<template>
  <div>
    <div style="margin-top: 120px;float:none">
      <el-avatar :size="120" :src="require('@/assets/img/image/'+user.avatar)" style="margin-left: 45%"></el-avatar>
    </div>

  <el-card class="box-card" style="width: 70%;margin: 0.5rem auto;margin-top: -45px;height: 88%">
  <div style="margin: 0.5rem auto;margin-top: 70px">
    <div style="margin-left: 44%"><i class="el-icon-user-solid"></i>{{user.nick}}</div>
    <div style="margin-left: 44%"><i class="el-icon-school"></i>{{user.school}}</div>
  </div>

    <el-tabs v-model="tabsname" @tab-click="">
      <el-tab-pane label="个人介绍" name="first">
        <mavon-editor v-model="user.introduce" :subfield="false" :defaultOpen="'preview'" :toolbarsFlag="false" /></el-tab-pane>
      <el-tab-pane label="做题情况" name="second"><el-tag v-for="(item,index) in prolist" style="margin-left: 9px">
        {{item.problemId}}</el-tag></el-tab-pane>
    </el-tabs>
  </el-card>
 </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {getuser, getusersPro} from "@/api/user";


export default {
  name: 'ProblemList',
  components:{Pagination},
  created() {
    this.getList()
  },
  data(){
    return {
      user:null,
      prolist:null,
      tabsname:'first'
    }
  },
  methods:{
    getList(){
      getuser(this.$route.params.id).then(response=>{
        this.user = response.data;
        this.getProList(this.user.userId)
      })
    },
    getProList(userid){
      getusersPro(userid).then(res=>{
        this.prolist=res.data;
      })
    },
  }

}
</script>

<style>
</style>
