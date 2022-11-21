<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto">
    <el-table
        style="width: 100%;margin-top: -20px"
        border
        :data="ranklist"
        :cell-class-name="tablecalss"
    >
      <el-table-column label="名次" width="150px">
        <template slot-scope="{row,$index}">
          <div>{{ $index+1}}</div>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="index"  width="250px">
        <template slot-scope="{row}">
          <div>
            {{ row.username}}
          </div>
        </template>
      </el-table-column>
      <el-table-column v-for="(item,index) in ranklist[0].time" :label="index+1" width="100px"
      >
        <template slot-scope="{row}">
          <div>{{ row.time[index] }}</div>
          <div style="font-size: 5px">{{row.count[index]}}try</div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import {getProblemid, getRankWork} from "@/api/work";

export default {
  name: "workRank",
  data(){
    return {
      ranklist:[],
      problemid:null,

    }
  },
  created() {
    this.getRankList()
    this.getProblem()
  },
  methods:{
    getRankList(){
      getRankWork(this.$route.params.id.toString()).then(response=>{
        this.ranklist=response.data;
        console.log(this.ranklist)
      })
    },
    tablecalss({row,column,rowIndex,columnIndex}){
      if(columnIndex>1) {
        if (row.time[columnIndex - 2] > 0) {
          console.log(row.time[columnIndex - 2])
          return 'success';
        }
      }
      return ''
    }
  }
}
</script>

<style>
.success{
  background: #60E760;
}
.fail{
  background: #ff0000
}
.el-table .success-row {
  background: #f0f9eb;
}
</style>
