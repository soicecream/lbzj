<template>
  <div>
    <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;">
      <h2 style="text-align: center">{{list.name}}</h2>
      <el-progress style="margin-top: 20px" :stroke-width="15" :percentage="100" :format="f"></el-progress>
      <span style="margin-top: 20px">{{list.starttime }}</span>
      <span style="float:right;margin-top: 20px">{{list.endtime | date}}</span>
    </el-card>
    <Menu> </Menu>
    <router-view style="margin-top: 20px"></router-view>
  </div>
</template>

<script>

import {getWork} from '@/api/work'
import Menu from './Menu'
export default {
  name: 'WorkList',

  created() {
   this.getList()
  },
  components:{Menu},
  filters:{
    date:function (timestamp){
      var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-';
      var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
      var D = date.getDate() + ' ';
      var h = date.getHours() + ':';
      var m = date.getMinutes() + ':';
      var s = date.getSeconds();
      return Y+M+D+h+m+s;
    }
  },
  data(){
    return {
      list:null,
      value1:10
    }
  },
  methods:{
    getList(){
      getWork(this.$route.params.id.toString()).then(response=>{
        this.list = response.data
        console.log(this.list)
      })
    },

    f(){

    }
  }
}
</script>
<style>
</style>
