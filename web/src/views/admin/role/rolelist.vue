<template>
  <div>
    <el-input v-model="listQuery.nick" placeholder="请输入角色昵称" style="width: 200px;margin-left: 40%"></el-input>
    <el-button icon="el-icon-search" circle @click="getList"></el-button>

    <div>
      <el-button type="success" size="mini" icon="el-icon-plus"  @click="showdig=true" v-has-perm="['role:add']">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete"  @click="showdig1=true"
                 :disabled="delrow.length>0?false:true" v-has-permi="['role:del']">删除</el-button>
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
      <el-table-column label="Id" width="150px" align="center">
        <template slot-scope="{row}">
          <div >{{ row.roleId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="角色昵称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.roleName }}</div>
        </template>
      </el-table-column>
      <el-table-column label="权限字符" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.roleKey}}</div>
        </template>
      </el-table-column>
      <el-table-column label="描述" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.remark}}</div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.createTime}}</div>
        </template>
      </el-table-column>
      <el-table-column label="开放" width="200px" align="center">
        <template slot-scope="{row}">
          <el-switch
              v-model=row.status
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="uproles(row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" icon="el-icon-edit" circle @click="tableuppro(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="margin-top: -20px;float: right"
                @pagination="getList"/>


    <el-dialog title="添加用户" :visible.sync="showdig" >
      <el-form :model="data"  ref="user"  >

        <el-form-item label="用户名称" label-width=100px prop="nick">
          <el-input v-model="data.roleName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="角色字符" label-width=100px prop="password">
          <el-input v-model="data.roleKey" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="角色权限" label-width=100px>
          <el-tree
              ref="tree"
              @check-change="getCheckedNodes()"
              :data="options"
              show-checkbox
              node-key="menuId"
              :props="props">
          </el-tree>
        </el-form-item>

        <el-form-item label="描述" label-width=100px>
          <el-input v-model="data.remark" autocomplete="off" ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig = false">取 消</el-button>
        <el-button type="primary" @click="addrole(data,'user')">确 定</el-button>
      </div>
    </el-dialog>


    <el-dialog title="修改用户" :visible.sync="showdig2">
      <el-form :model="row" ref="user1">

        <el-form-item label="用户名称" label-width=100px prop="nick">
          <el-input v-model="row.roleName" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="角色字符" label-width=100px prop="password">
          <el-input v-model="row.roleKey" autocomplete="off" ></el-input>
        </el-form-item>

        <el-form-item label="角色权限" label-width=100px >
          <el-tree
              ref="tree1"
              @check-change="getCheckedNodes1()"
              :data="options"
              show-checkbox
              node-key="menuId"
              :props="props">
          </el-tree>
        </el-form-item>

        <el-form-item label="描述" label-width=100px>
          <el-input v-model="row.remark" autocomplete="off" ></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig2 = false">取 消</el-button>
        <el-button type="primary" @click="upuser(row,'user1')">确 定</el-button>
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
import {addrole, getrole, adminpagerole, delrole, uprole, getrolemenu, uproles} from "@/api/role";


export default {
  name: 'rolelist',
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
        nick:''
      },
      showdig:false,
      showdig1:false,
      showdig2:false,
      data:{
        roleId:'',
        roleName:'',
        roleKey:'',
        remark:'',
        roles:[]
      },
      row:{
        roleId:'',
        roleName:'',
        roleKey:'',
        remark:'',
        roles:null
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
      rolezhi1:[],
      props: {label:"menuName" },
      opt:[]
    }
  },
  filters:{
    opts(opt) {
     var opt1=[];
     for (var i=0;i<opt.length;i++) if(opt[i].menuType!='M') opt1.push(opt[i]);
     return opt1
    }
  },
  methods:{
    getCheckedNodes(data, checked, indeterminate){
      this.data.roles = this.$refs.tree.getCheckedKeys()
      var roless=this.$refs.tree.getHalfCheckedKeys();
      for(var i=0;i<roless.length;i++) this.data.roles.push(roless[i])
      console.log(this.data.roles)
    },
    getCheckedNodes1(data, checked, indeterminate){
      this.row.roles = this.$refs.tree1.getCheckedKeys()
      var roless=this.$refs.tree1.getHalfCheckedKeys();
      for(var i=0;i<roless.length;i++) this.row.roles.push(roless[i])
      console.log(this.$refs.tree1.getHalfCheckedKeys())
      console.log(this.row.roles)
    },
    getList() {
      adminpagerole(this.page, this.limit, this.listQuery).then(response => {
        this.list = response.data.rows
        this.total = response.data.total
      })
      getrole().then(res=>{
        this.options=res.data
      })
    },

    tableuppro(row){
          this.row.roleId=row.roleId,
          this.row.roleName=row.roleName,
          this.row.roleKey=row.roleKey,
          this.row.remark=row.remark,
      this.showdig2=true
      getrolemenu(row.roleId).then(res=>{
        var opt1=[];
        for (var i=0;i<res.data.length;i++) if(res.data[i]>=1000) opt1.push(res.data[i]);
        this.$refs.tree1.setCheckedKeys(opt1)
      })

    },

    upuser(row,formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.showdig2 = false
          uprole(row).then(res => {
            this.$message({
              showClose: true,
              message: '修改成功',
              type: 'success'
            });
          })
        }
      })
    },

    uproles(row){
      console.log(row)
      uproles(row.roleId,row.status).then(res=>{
        this.$message({
          showClose: true,
          message: '修改成功',
          type: 'success'
        });
      })
    },

    addrole(data,formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.showdig = false;
          addrole(data).then(res => {
            this.$message({
              showClose: true,
              message: '添加成功',
              type: 'success'
            });
            this.getList()
          })
        }
      })
    },

    setdelrow(val) {
      this.delrow=val
    },
    delUser(){
      this.showdig1=false
      var ids=[]
      for(var i=0;i<this.delrow.length;i++){
        ids[i]=this.delrow[i].roleId
      }
      delrole(ids).then(res=>{
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
