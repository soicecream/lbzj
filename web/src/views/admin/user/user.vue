<template>
  <div>
    <el-input v-model="input" placeholder="请输入内容" style="width: 200px;margin-left: 40%"></el-input>
    <el-button icon="el-icon-search" circle></el-button>
    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="showdig=true">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete" @click="showdig1=true">删除</el-button>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>
    <el-table stripe border style="width: 100%;margin-top: 20px"
              :cell-style="{padding: '1'}" :data="list" @selection-change="setdelrow">
      <el-table-column type="selection" width="55"></el-table-column>
      <el-table-column label="用户" width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.userId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="昵称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.nick }}</div>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.regTime }}</div>
        </template>
      </el-table-column>
      <el-table-column label="角色" min-width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.nick }}</div>
        </template>
      </el-table-column>
      <el-table-column label="开放" width="200px" align="center">
        <template slot-scope="{row}">
          <el-switch v-model=row.defunct active-color="#13ce66" inactive-color="#ff4949" @change="upuser(row)"/>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button size="mini" type="primary" icon="el-icon-edit" circle @click="tableuppro(row)"></el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"
                style="margin-top: -20px;float: right" @pagination="getList"/>

    <el-dialog title="添加用户" :visible.sync="showdig">
      <el-form :model="data">
        <el-form-item label="用户编号" label-width=100px>
          <el-input v-model="data.userId" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户名称" label-width=100px>
          <el-input v-model="data.nick" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户密码" label-width=100px>
          <el-input v-model="data.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="问题难度" label-width=100px>
          <el-select v-model="data.degree" placeholder="请选择" @change="pdtype">
            <el-option v-for="item in 5" :key=item :label=item :value=item></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig = false">取 消</el-button>
        <el-button type="primary" @click="addpro(data)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改用户" :visible.sync="showdig2">
      <el-form :model="row">
        <el-form-item label="用户编号" label-width=100px>
          <el-input v-model="row.userId" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户名称" label-width=100px>
          <el-input v-model="row.nick" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="用户密码" label-width=100px>
          <el-input v-model="row.password" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="问题难度" label-width=100px>
          <el-select v-model="data.degree" placeholder="请选择" @change=pdtype>
            <el-option
                v-for="item in worktype"
                :key="item.value"
                :label="item.label"
                :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig2 = false">取 消</el-button>
        <el-button type="primary" @click="upuser(row)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
        title="提示"
        :visible.sync="showdig1"
        width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="showdig1 = false">取 消</el-button>
    <el-button type="primary" @click="delPro()">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {getusers, deluser, adduser, updateuser} from "@/api/user";

export default {
  name: 'user',
  components: {Pagination},
  created() {
    this.getList()
  },
  data() {
    return {
      list: null,
      limit: 10,
      page: 1,
      total: 10,
      listQuery: {},
      showdig: false,
      showdig1: false,
      showdig2: false,
      pd: true,
      data: {
        userId: '',
        password: '',
        nick: '',
        email: ''
      },
      row: {},
      delrow: [],


      input: "",


    }
  },
  methods: {
    getList() {
      getusers(this.page, this.limit, this.listQuery).then(response => {
        this.list = response.data.rows
        this.total = response.data.total
      })
    },
    tableuppro(row) {
      this.row = row
      this.showdig2 = true
    },

    upuser(row) {
      this.showdig2 = false
      updateuser(row).then(res => {
        this.$message({showClose: true, message: '修改成功', type: 'success'});
      })
    },
    addpro(data) {
      this.showdig = false;
      adduser(data).then(res => {
        this.$message({showClose: true, message: '添加成功', type: 'success'});
      })
    },
    delPro() {
      this.showdig1 = false
      var ids = []
      for (var i = 0; i < this.delrow.length; i++) {
        ids[i] = this.delrow[i].userId
      }
      deluser(ids).then(res => {
        this.$message({showClose: true, message: '删除成功', type: 'success'});
      })
    },
    delpro1(row) {
      this.showdig1 = true;
    },
    setdelrow(val) {
      this.delrow = val
    },
  }
}
</script>

<style>
</style>
