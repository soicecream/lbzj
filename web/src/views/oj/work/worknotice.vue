<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;">
    <span>{{list.notice | kg}}</span>
  </el-card>
</template>

<script>
import {getWork} from "@/api/work";

export default {
  name: "worknotice",
  data(){
    return{
      list:null,
    }
  },
  filters:{
    kg(zhi){
      if(zhi==null) return '暂无公告'
      else return zhi
    }
  },
  methods:{
    getList(){
      getWork(this.$route.params.id.toString()).then(response=>{
        this.list = response.data
      })
    }
  },
  created() {
    this.getList()
  }
}
</script>

<style scoped>

</style>
