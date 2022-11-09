<template>
<div>
  <el-input v-model="input" placeholder="请输入内容" style="width: 200px;margin-left: 40%"></el-input>
  <el-button icon="el-icon-search" circle></el-button>
  <div>
    <el-button type="success" size="mini" icon="el-icon-plus"  @click="showdig0=true">添加</el-button>
    <el-button type="danger" size="mini" icon="el-icon-delete"  @click="showdig1=true">删除</el-button>
    <el-button  size="mini" type="info" icon="el-icon-download">导入</el-button>
    <el-button  type="warning"  size="mini" icon="el-icon-upload2">导出</el-button>
  </div>
  <el-table
      border
      stripe
      style="width: 100%;margin-top: 20px"
      :data="list"
  >
    <el-table-column label="#" prop="index" align="center" width="100px">
      <template slot-scope="{row,$index}">
        <div>
          {{ row.workid }}
        </div>
      </template>
    </el-table-column>
    <el-table-column label="标题" min-width="200px" align="center">
      <template slot-scope="{row}">
        <div >{{ row.name }}</div>
      </template>
    </el-table-column>
    <el-table-column label="开始时间" width="200px" align="center">
      <template slot-scope="{row}">
        <div >{{ row.starttime }}</div>
      </template>
    </el-table-column>
    <el-table-column label="停止时间" width="200px" align="center">
      <template slot-scope="{row}">
        <div >{{ row.endtime }}</div>
      </template>
    </el-table-column>
    <el-table-column label="类型" width="200px" align="center">
      <template slot-scope="{row,$index}">
        <el-select v-model=row.type placeholder="请选择" @change="updateWork(row)" >
          <el-option
              v-for="item in worktype"
              :key=$index
              :label="item.label"
              :value="item.label">
          </el-option>
        </el-select>
      </template>
    </el-table-column>
    <el-table-column label="开放" width="200px" align="center">
      <template slot-scope="{row}">
        <el-switch
            v-model=row.open
            active-color="#13ce66"
            inactive-color="#ff4949"
            active-value="1"
            inactive-value="0"
            @change="updateWork(row)">
        </el-switch>
      </template>
    </el-table-column>
  </el-table>

  <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
              @pagination="getList"/>

  <el-dialog title="添加竞赛" :visible.sync="showdig0">
    <el-form :model="workform">
      <el-form-item label="竞赛名称" label-width=100px>
        <el-input v-model="workform.name" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="题目选择" label-width=100px>
        <el-input v-model="workform.probelmid" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="时间选择" label-width=100px>
        <el-date-picker
            v-model="workform.starttime"
            type="datetime"
            placeholder="选择开始时间">
        </el-date-picker>
        <el-date-picker
            v-model="workform.endtime"
            type="datetime"
            placeholder="选择停止时间">
        </el-date-picker>
      </el-form-item>

      <el-form-item label="类型选择" label-width=100px>
        <el-select v-model="workform.type" placeholder="请选择" @change=pdtype>
          <el-option
              v-for="item in worktype"
              :key="item.value"
              :label="item.label"
              :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="竞赛密码" label-width=100px v-show="wpdtype">
        <el-input v-model="workform.workpassword" autocomplete="off"></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="showdig0 = false">取 消</el-button>
      <el-button type="primary" @click="addWork()">确 定</el-button>
    </div>
  </el-dialog>

</div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {addWork, getWorkList, updateWork} from "@/api/work";

export default {
  name: "adminwork",
  components:{Pagination},
  data(){
    return{
      list:null,
      showdig0:false,
      showdig1:false,
      workform:{
        workid:undefined,
        name:undefined,
        probelmid:undefined,
        starttime:undefined,
        endtime:undefined,
        type:undefined,
        founder:undefined,
        workpassword:undefined,
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
      limit:10,
      page:1,
      total:10
    }
  },
  created() {
    this.getList()
  },
  filters:{
    sta:function (c){
      if(c==1) return "公有";
      return "私有";
    },
  },
  methods:{
    getList(){
      getWorkList(this.page,this.limit).then(res=>{
        this.list=res.data.rows;
        this.total=res.data.total;
      })
    },
    updateWork(row){
         updateWork(row).then(res=>{

         })
    },
    pd(key){
     if(key==0) this.showdig0=true;
    },
    pdtype(key){
      if(key==2) this.wpdtype=true;
      else this.wpdtype=false;
    },
    addWork(key){
      this.showdig0=false;
      this.workform.starttime=this.workform.starttime.getTime();
      this.workform.endtime=this.workform.endtime.getTime();
      console.log(this.workform);
      addWork(this.workform).then(res=>{
        this.$message('添加成功');
      })
    },
  }
}
</script>

<style scoped>

</style>
