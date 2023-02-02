<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto">
    <el-table
        style="width: 100%;margin-top: -20px;text-align: center"
        border
        :data="ranklist"
        :cell-class-name="tablecalss"
    >
      <el-table-column label="名次" width="60px" align="center">
        <template slot-scope="{row,$index}">
          <div>{{ $index+1}}</div>
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="index"  min-width="250px">
        <template slot-scope="{row}">
          <div>
            {{ row.username}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="总时长" prop="index"  width="130px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.times | date}}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="通过" prop="index"  width="130px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.pros}}
          </div>
        </template>
      </el-table-column>
      <el-table-column v-for="(item,index) in ranklist[0].time" :label="index+1" width="150px"
                       align="center">
        <template slot-scope="{row}">
          <div>{{ row.time[index] | date }}</div>
          <div style="font-size: 5px">{{row.count[index] | count}}</div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import {getRankWork} from "@/api/work";

export default {
  name: "workRank",
  data(){
    return {
      ranklist:[],
      problemid:null,
      pd:true
    }
  },
  filters: {
    date(t){
      if(t==-1) return '';
      return Math.floor(t/3600000);
    },
    count(c){
      if(c==0) return '';
      return c+'try'
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
        this.pd=this.ranklist.length>0?false:true;
        console.log(this.pd)
      })
    },
    tablecalss({row,column,rowIndex,columnIndex}){
      if(columnIndex>3) {
        if (row.time[columnIndex - 4] > 0) {
          return 'success';
        }
        else if (row.count[columnIndex - 4] > 0) {
          return 'fail';
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
