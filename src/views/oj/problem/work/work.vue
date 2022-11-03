<template>
  <div>
  <el-card class="box-card" style="width: 35%;margin: 0.5rem auto;height: 88%">
    <el-table
        style="width: 100%;margin-top: -20px"
        :data="list"
    >
      <el-table-column label="标题" prop="index"  width="400px">
        <template slot-scope="{row,$index}">
          <div @click="ToPage(row.problemId)">
            {{ row.title }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="分数" width="50px" align="center">
        <template slot-scope="{row}">
          <div @click="ToPage(row.problemId)">{{ row.degree }}</div>
        </template>
      </el-table-column>
    </el-table>
  </el-card>


    <el-card class="box-card" style="width: 35%;margin: 0.5rem auto;height: 88%">
      <el-table
          style="width: 100%;margin-top: -20px"
          :data="ranklist"
      >
        <el-table-column label="名次" width="150px">
          <template slot-scope="{row,$index}">
            <div>{{ $index+1}}</div>
          </template>
        </el-table-column>
        <el-table-column label="用户名" prop="index"  width="250px">
          <template slot-scope="{row}">
            <div >
              {{ row.username}}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="分数" width="50px" align="center">
          <template slot-scope="{row}">
            <div>{{ row.intrgeral }}</div>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>

import {getWorkid,getProblemid,getRankWork} from '@/api/work'

export default {
  name: 'WorkList',

  created() {
    this.getProblem()
    this.getRankList()
  },
  data(){
    return {
      list:null,
      ranklist:[],
      cs:[],
      problemid:null,

    }
  },
  methods:{
    getList(){
      getWorkid(this.problemid).then(response=>{
        this.list = response.data
        console.log(this.list)
      })
    },
    getRankList(){
      getRankWork(this.$route.params.id.toString()).then(response=>{
        this.ranklist=response.data;
        console.log(this.cs)
      })
    },
    getProblem(){
      getProblemid(this.$route.params.id.toString()).then(response=>{
        this.problemid=response.data
        this.getList()
      })
    },
    ToPage(id){
      this.$router.push('/problem/'+id)
    },
  }
}
</script>
<style>
</style>
