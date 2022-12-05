<template>
  <div>
    <el-input v-model="listQuery.workname" placeholder="请输入内容" style="width: 200px;margin-left: 40%" @click="getList"></el-input>
    <el-button icon="el-icon-search" circle></el-button>
    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="showdig0=true">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete" @click="showdig1=true"
                 :disabled="delrow.length>0?false:true">删除</el-button>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>
    <el-table
        border
        stripe
        style="width: 100%;margin-top: 20px"
        :data="list"
        @selection-change="setdelrow"
    >
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column label="#" prop="index" align="center" width="100px">
        <template slot-scope="{row,$index}">
          <div>
            {{ row.workid }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="200px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.name }}</div>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" width="200px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.starttime | date }}</div>
        </template>
      </el-table-column>
      <el-table-column label="停止时间" width="200px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.endtime | date }}</div>
        </template>
      </el-table-column>
      <el-table-column label="开放" width="200px" align="center">
        <template slot-scope="{row}">
          <el-switch
              v-model=row.open
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="updateWork(row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button @click="showupwork(row)" size="mini" type="primary" icon="el-icon-edit" circle></el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"
                style="margin-top: -20px;float: right"
                @pagination="getList"/>

    <el-dialog title="添加竞赛" :visible.sync="showdig0">
      <el-form :model="workform" ref="work" :rules="rules">
        <el-form-item label="竞赛名称" label-width=100px prop="name">
          <el-input v-model="workform.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="题目选择" label-width=100px prop="probelmid">
          <el-input v-model="workform.probelmid" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间选择" label-width=100px >
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

        <el-form-item label="类型选择" label-width=100px prop="type">
          <el-select v-model="workform.type" placeholder="请选择" @change=passwordtype>
            <el-option
                v-for="item in worktype"
                :key="item.value"
                :label="item.label"
                :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛密码" label-width=100px v-show="showpassword">
          <el-input v-model="workform.workpassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig0 = false">取 消</el-button>
        <el-button type="primary" @click="addWork('work')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改竞赛" :visible.sync="showdig2">
      <el-form :model="uprow">
        <el-form-item label="竞赛名称" label-width=100px>
          <el-input v-model="uprow.name" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="题目选择" label-width=100px>
          <el-input v-model="uprow.probelmid" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间选择" label-width=100px>
          <el-date-picker
              v-model="uprow.starttime"
              type="datetime"
              placeholder="选择开始时间">
          </el-date-picker>
          <el-date-picker
              v-model="uprow.endtime"
              type="datetime"
              placeholder="选择停止时间">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="类型选择" label-width=100px>
          <el-select v-model="uprow.type" placeholder="请选择" @change=passwordtype1>
            <el-option
                v-for="item in worktype"
                :key="item.value"
                :label="item.label"
                :value="item.label">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="竞赛密码" label-width=100px v-show="showpassword1">
          <el-input v-model="uprow.workpassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig2 = false">取 消</el-button>
        <el-button type="primary" @click="updwork">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="提示"
        :visible.sync="showdig1"
        width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="showdig1 = false">取 消</el-button>
    <el-button type="primary" @click="delWork()">确 定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {addWork, getWorkList, updateWork,delWork} from "@/api/work";


export default {
  name: "adminwork",
  components: {Pagination},
  data() {
    return {
      list: null,
      showdig0: false,
      showdig1: false,
      showpassword:false,
      showpassword1:false,
      showdig2:false,
      workform: {
        workid: undefined,
        name: undefined,
        probelmid: undefined,
        starttime: undefined,
        endtime: undefined,
        type: undefined,
        founder: undefined,
        workpassword: undefined,
      },
      worktype: [
        {
          label: '公有',
          value: 1
        },
        {
          label: '私有',
          value: 2
        }
      ],
      limit: 10,
      page: 1,
      total: 10,
      delrow:[],
      uprow:{
        workid: undefined,
        name: undefined,
        probelmid: undefined,
        starttime: undefined,
        endtime: undefined,
        type: undefined,
        founder: undefined,
        workpassword: undefined,
      },
      rules: {
        name: [
          { required: true, message: '请输入名称'},
        ],
        starttime: [
          { required: true, message: '请输入时间'},
        ],
        probelmid: [
          { required: true, message: '请输入题目'},
        ],
        type: [
          { required: true, message: '请输入题目'},
        ],
      },
      listQuery: {
        workname: "",
      }
    }
  },
  created() {
    this.getList()
  },
  filters: {
    sta(c) {
      if (c == 1) return "公有";
      return "私有";
    },
    date: function (timestamp) {
      var date = new Date(timestamp);//时间戳为10位需*1000，时间戳为13位的话不需乘1000
      var Y = date.getFullYear() + '-';
      var M = (date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1) + '-';
      var D = date.getDate() + ' ';
      var h = date.getHours() + ':';
      var m = date.getMinutes() + ':';
      var s = date.getSeconds();
      return Y + M + D + h + m + s;
    }
  },
  methods: {
    getList() {
      getWorkList(this.page, this.limit,this.listQuery).then(res => {
        this.list = res.data.rows;
        this.total = res.data.total;
      })
    },
    updwork(){
      if(this.uprow.endtime instanceof Object) this.uprow.endtime=this.uprow.endtime.getTime();
      if(this.uprow.starttime instanceof Object) this.uprow.starttime=this.uprow.starttime.getTime();
      this.updateWork(this.uprow)
      this.showdig2=false
    },
    updateWork(row) {
      updateWork(row).then(res => {
        this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        });
      })
    },
    delWork(){
      this.showdig1=false
      var ids=[]
      for(var i=0;i<this.delrow.length;i++){
        ids[i]=this.delrow[i].workid
      }
      console.log(ids);
      delWork(ids).then(res=>{
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        });
      })
    },
    addWork(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.showdig0 = false;
          this.workform.starttime = this.workform.starttime.getTime();
          this.workform.endtime = this.workform.endtime.getTime();
          console.log(this.workform);
          addWork(this.workform).then(res => {
            this.$message('添加成功');
          })
        }
      })
    },
    showupwork(row){
      this.uprow=row
      this.showdig2=true
    },
    setdelrow(val) {
      this.delrow=val
    },

    passwordtype(){
      if(this.workform.type=='私有') this.showpassword=true;
      else this.showpassword=false;
    },
    passwordtype1(){
      if(this.uprow.type=='私有') this.showpassword1=true;
      else this.showpassword1=false;
    }
  }
}
</script>

<style scoped>

</style>
