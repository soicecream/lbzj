<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;height: 88%">
    <div slot="header" class="clearfix">
      <span style="font-size: 1.4rem">题目列表</span>
  
      <el-input placeholder="请输入内容" size="small" style="float: right;width: 200px;height: 20px"><el-button slot="append" icon="el-icon-search"></el-button></el-input>
      <el-select size="small" v-model="listQuery.degree" placeholder="难度" clearable style="width: 90px;margin-right: 1rem;float: right" class="filter-item">
        <el-option v-for="item in degreeOptions" :key="item" :label="item" :value="item" />
      </el-select>
    </div>
    <el-table
      style="width: 100%;margin-top: -20px"
      :data="list"
    >
      <el-table-column label="#" prop="index" align="center" width="50px">
        <template slot-scope="{row,$index}">
          <div @click="ToPage(row.problemId)">
            {{ $index + 1 }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标题" width="150px" align="center">
        <template slot-scope="{row}">
          <div @click="ToPage(row.problemId)">{{ row.title }}</div>
        </template>
      </el-table-column>
      <el-table-column label="难度" min-width="150px" align="center">
        <template slot-scope="{row}">
          <el-rate
            v-model="row.degree"
            disabled
            :colors="{1:'#99A9BF', 2:'#e3f72a', 3:'#f79b2a',4:'#ff5100',5:'#FF0000'}">
          </el-rate>
        </template>
      </el-table-column>
      <el-table-column label="总提交数" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.submit }}</div>
        </template>
      </el-table-column>
      <el-table-column label="通过率" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.accepted,row.submit | acRate }}</div>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
                @pagination="getList"/>
  </el-card>

</template>

<script>
  import Pagination from '@/components/Pagination'
  import {fetchProblemsList} from "@/api/problem";
  import {removeWorkid} from '@/utils/auth'

  export default {
    name: 'ProblemList',
    components:{Pagination},
    created() {
      this.getList()
    },
    data(){
      return {
        list:null,
        limit:10,
        page:1,
        total:10,
        listQuery:{

        },
        degreeOptions: [1, 2, 3, 4, 5],
      }
    },
    filters: {
      acRate(solved,submit) {
        if(submit!=0)
        return (solved / submit * 100.0).toFixed(2) + "%"
        return 0+"%";
      },
    },
    methods:{
      getList(){
        fetchProblemsList(this.page,this.limit,this.listQuery).then(response=>{
          this.list = response.data.rows
          this.total = response.data.total
        })
      },
      ToPage(id){
        this.$router.push('/problem/'+id)
        removeWorkid();
      },
      toCreate(){
        this.$router.push('/problemCreate')
      }
    }
  }
</script>

<style>
</style>
