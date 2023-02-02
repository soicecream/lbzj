<template>
  <div>
    <el-input v-model="listQuery.nick" placeholder="请输入内容" style="width: 200px;margin-left: 40%"></el-input>
    <el-button icon="el-icon-search" circle @click="getList"></el-button>

    <div>
      <el-button type="success" size="mini" icon="el-icon-plus"  @click="showdig=true" v-has-perm="['ur:add']">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete"  @click="showdig1=true"
                 :disabled="delrow.length>0?false:true" v-has-perm="['ur:del']">删除</el-button>
      <el-button  size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button  type="warning"  size="mini" icon="el-icon-upload2">导出</el-button>
    </div>
    <el-table
        stripe
        border
        style="width: 100%;margin-top: 20px"
        :cell-style="{padding: '1'}"
        :data="list"
        @selection-change="setdelrow">
      <el-table-column
          type="selection"
          width="55">
      </el-table-column>
      <el-table-column label="用户昵称" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.userName}}</div>
        </template>
      </el-table-column>
      <el-table-column label="角色昵称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <el-select v-model="row.roleIds" multiple  @change="optionzhi(row)">
            <el-option
                v-for="item in options"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId">
            </el-option>
          </el-select>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
                @pagination="getList"/>


    <el-dialog title="添加用户" :visible.sync="showdig" >
      <el-form :model="data"  ref="user">
        <el-form-item label="用户编号" label-width=100px prop="userId">
          <el-input v-model="data.userId" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="角色昵称" label-width=100px prop="nick">
          <el-select v-model="data.roleIds" multiple placeholder="请选择">
            <el-option
                v-for="item in options"
                :key="item.roleId"
                :label="item.roleName"
                :value="item.roleId">
            </el-option>
          </el-select>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig = false">取 消</el-button>
        <el-button type="primary" @click="addpro(data,'user')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="提示"
        :visible.sync="showdig1"
        width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="showdig1 = false">取 消</el-button>
    <el-button type="primary" @click="delUser()">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {addusrerole, delusrerole, pageuserrole, upusrerole} from "@/api/userrole";
import {getroles} from "@/api/role";


export default {
  name: 'userrole',
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
        username:'',
        rolename:''
      },
      showdig:false,
      showdig1:false,
      showdig2:false,
      data:{
        userId:undefined,
        roleIds:undefined,
        username:undefined,
        roleNames:undefined
      },
      row:{
      },
      password:'',
      delrow:[],
      rules: {
        password: [
          { required: true, message: '请输入密码'},
        ],
        userId: [
          { required: true, message: '请输入编号'},
        ],
        nick: [
          { required: true, message: '请输入昵称'},
        ],
      },
      options:[],
      value:''
    }
  },
  methods:{
    getList() {
      pageuserrole(this.page, this.limit, this.listQuery).then(response => {
        this.list = response.data
        this.total = this.list.length
        getroles().then(res=>{
          this.options=res.data
        })
      })
    },
    optionzhi(zhi){
      upusrerole(zhi).then(res=>{
          this.$message({
            showClose: true,
            message: '修改成功',
            type: 'success'
          });
          this.getList()
      })
     console.log(zhi)
    },
    tableuppro(row){
      this.row=row
      this.showdig2=true
    },

    addpro(data,formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.showdig = false;
          addusrerole(data).then(res => {
            this.$message({
              showClose: true,
              message: '添加成功',
              type: 'success'
            });
          })
          this.getList()
        }
      })
    },

    setdelrow(val) {
      this.delrow=val
    },
    delUser(){
      this.showdig1=false
      var userids=[]

      for(var i=0;i<this.delrow.length;i++){
        userids[i]=this.delrow[i].userId
      }
      delusrerole(userids).then(res=>{
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        });
        this.getList()
      })
    },
  }
}
</script>

<style>
</style>
