<template>
<div>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;height: 88%">
    <div slot="header" class="clearfix">
      <span style="font-size: 1.4rem">竞赛列表</span>
      <el-input placeholder="请输入内容" size="small" style="float: right;width: 200px;height: 20px" v-model="listQuery.workname">
        <el-button slot="append" icon="el-icon-search" @click="getList"></el-button></el-input>
    </div>
    <el-table
        style="width: 100%;margin-top: -20px"
        :data="list"
        @row-click="pdwork"
        :show-header="false"
    >
      <el-table-column  min-width="500px" align="left">
        <template slot-scope="{row}">
          <h2>{{ row.name }}</h2>
          <div style="font-size: 15px">
          <i class="el-icon-s-order" style="color:#00b7ff;"></i>
          <span>{{row.starttime | timestampToTime}}</span>

          <i class="el-icon-time" style="color:#00b7ff;margin-left: 15px" ></i>
          <span>{{row.starttime,row.endtime | tian}}</span>

            <el-tag :type="row.type=='公有'?'success':'danger'" style="margin-left: 15px">{{row.type}}赛</el-tag>

            <el-tag style="float: right" :type="row.endtime-new Date().getTime()>0?'success':'danger'"  effect="dark">
              {{row.endtime | tian1}}</el-tag>
          </div>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
                @pagination="getList"/>
  </el-card>


  <el-dialog title="竞赛" :visible.sync="showaddwork">
    <el-form>
      <el-form-item label="竞赛密码" label-width=100px>
        <el-input v-model="workpassword" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="pdwork1()">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {getWorkList,addWork} from '@/api/work'
import {getWorkid, setWorkid} from '@/utils/auth'


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
      listQuery: {
        workname: "",
      },
      workform:{
        workid:undefined,
        name:undefined,
        probelmid:undefined,
        starttime:undefined,
        endtime:undefined,
        type:undefined,
        founder:undefined,
        workpassword:undefined
      },
      worktype:[
        {
          label:'公有',
          value:1
        },
        {
          label:'私有',
          value:2
        }
      ],
      showaddwork:false,
      workpassword:'',
      workpassword1:'',
      workid:''
    }
  },
  filters:{
    timestampToTime(timestamp) {
      var date = new Date(timestamp );//时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-';
      var M = (date.getMonth()+1 < 10 ? '0'+(date.getMonth()+1) : date.getMonth()+1) + '-';
      var D = (date.getDate() < 10 ? '0'+(date.getDate()) : date.getDate()) + ' ';
      var h = (date.getHours() < 10 ? '0'+(date.getHours()) : date.getHours()) + ':';
      var m = (date.getMinutes() < 10 ? '0'+(date.getMinutes()) : date.getMinutes()) + ':';
      var s = (date.getSeconds() < 10 ? '0'+(date.getSeconds()) : date.getSeconds());
      return Y+M+D+h+m+s;
    },
    tian(start,end){
      return Math.floor((end-start)/86400000)+'天';
    },
    tian1(end){
      return end-new Date().getTime()>0?'进行中':'已结束'
    }
  },
  methods:{
    getList(){
      getWorkList(this.page,this.limit,this.listQuery).then(response=>{
        this.list = response.data.rows
        this.total = response.data.total
      })
    },

    ToPage(id) {
      this.$router.push('/work/'+id)
    },
    pdwork(row, column, event){
      if(row.type=='私有'){
        this.showaddwork=true;
        this.workpassword1=row.workpassword;
        this.workid=row.workid;
      }
      else {
        setWorkid(row.workid);
        console.log(getWorkid())
        this.$router.push('/work/'+row.workid);
      }
    },

    pdwork1(){
      if(this.workpassword==this.workpassword1)  {
        setWorkid(this.workid);
        this.$router.push('/work/'+this.workid);
      }
      else this.$message("密码错误");
      this.showaddwork=false;
    },

    toCreate(){
      this.$router.push('/problemCreate')
    }
  }
}
</script>
<style>
.gy{

}
.sy{

}
</style>
