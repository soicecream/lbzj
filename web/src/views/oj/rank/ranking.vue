<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;height: 88%">
    <div slot="header" class="clearfix">
      <span style="font-size: 1.4rem">题目列表</span>
    </div>

    <el-table
        style="width: 100%;margin-top: -20px"
        :data="list"
        @row-click="ToPage"
    >
      <el-table-column label="名次" prop="index" align="center" width="50px">
        <template slot-scope="{row,$index}">
          <div>
            {{ $index + 1 }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="用户" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.userId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="昵称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.nick }}</div>
        </template>
      </el-table-column>
      <el-table-column label="解决" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.solved }}</div>
        </template>
      </el-table-column>
      <el-table-column label="提交" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.submit }}</div>
        </template>
      </el-table-column>
      <el-table-column label="通过率" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.solved,row.submit | acRate }}</div>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
                @pagination="getList"/>
  </el-card>

</template>

<script>
import Pagination from '@/components/Pagination'
import {getusers} from "@/api/user";


export default {
  name: 'rank',
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
       username:''
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
       getusers(this.page,this.limit,this.listQuery).then(response=>{
        this.list = response.data.rows
        this.total = response.data.total
      })
    },
    ToPage(row, column, event){
      this.$router.push('/user/'+row.userId)
    },
    toCreate(){
      this.$router.push('/problemCreate')
    }
  }
}
</script>

<style>
</style>
