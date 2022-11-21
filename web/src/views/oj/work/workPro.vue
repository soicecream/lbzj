<template>
  <div>
      <el-card class="box-card" style="width: 95%;margin: 0.5rem auto">
        <el-table
            style="width: 100%;margin-top: -20px"
            :data="list"
        >
          <el-table-column label="标题" prop="index"  width="400px">
            <template slot-scope="{row,$index}">
              <div @click="ToPage(row.problemId)">
                {{ $index+1 }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="标题" prop="index"  min-width="400px">
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
  </div>
</template>

<script>

import {getWorkid,getProblemid} from '@/api/work'

export default {
  name: 'workPro',
  created() {
    this.getProblem()
  },
  data(){
    return {
      list:null,
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
