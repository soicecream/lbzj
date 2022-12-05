<template>
  <div>
    <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;">
      <h2 style="text-align: center">{{list.name}}</h2>
      <div>
        <span style="margin-top: 20px;float: left">开始时间：{{list.starttime | date }}</span>
        <span style="margin-top: 20px;float: right">结束时间：{{list.endtime | date}}</span>
      </div>
      <el-progress style="margin-top: 100px" :stroke-width="15" :percentage="progresszhi"
                   :format="f"></el-progress>
      <div :class="worktime>0?'start':'end'"  style="text-align: center;margin-top: 20px;font-size: 20px">
       {{worktime|time}}</div>
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
    },
    time:function (t){
    if(t<0) return 'Ended'
    var h=''
    if(t>3600000) {
      var t1=Math.floor(t/3600000)
      t=t-3600000*t1
      h=t1+':'
    }
    if(t>60000){
      var t1=Math.floor(t/60000)
      t=t-60000*t1
      h=h+t1+':'
    }
    else h=h+'00:'
    return h+Math.floor(t/1000);
    }
  },
  data(){
    return {
      list:null,
      value1:10,
      worktime:'',
      time:'',
      progresszhi:0
    }
  },
  methods:{
    getList(){
      getWork(this.$route.params.id.toString()).then(response=>{
        this.list = response.data
        console.log(this.list)
        this.start()
      })
    },
    start(){
     this.worktime=this.list.endtime-new Date().getTime()
     this.progresszhi=100-this.worktime/(this.list.endtime-this.list.starttime)*100;
     console.log(this.worktime/(this.list.endtime-this.list.starttime))
     this.time=setInterval(this.f1,1000)
    },

    f1(){
      this.worktime=this.worktime-1000;
      if(this.worktime<0) clearInterval(this.time);
    },

    f(){

    },
    ys(){
      if(this.worktime<0) return 'end';
      return 'start'
    }
  }
}
</script>
<style>
.start{
  color: #60E760;
}
.end{
 color: red;
}
</style>
