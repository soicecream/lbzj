<template>
  <div>
    <el-input v-model="listQuery.value" placeholder="请输入标签名" style="width: 200px;margin-left: 40%" clearable></el-input>
    <el-button icon="el-icon-search" circle @click="getList"></el-button>

    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="createOrEdit(null)">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete" @click="delete_dialog=true">删除</el-button>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>

    <!--    信息-->
    <el-table stripe border style="width: 100%;margin-top: 20px" :data="tagsList"
              @selection-change="handlerSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="id" prop="id" align="center" width="100px"/>
      <el-table-column label="标签名" align="center" min-width="150px">
        <template slot-scope="{row}">
          <span :style="{'color': row.color}">
            {{ row.value }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button @click="createOrEdit(row)" size="mini" type="primary" icon="el-icon-edit" circle/>
        </template>
      </el-table-column>
      <el-table-column label="删除" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button @click="MyDelete(row.id)" size="mini" type="primary" icon="el-icon-delete-solid" circle/>
        </template>
      </el-table-column>
    </el-table>

    <!--    标签页-->
    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize"
                style="margin-top: -20px;float: right" @pagination="getList"/>

    <!--    添加 修改-->
    <el-dialog :title="(dialog_ADD ? '新建' : '编辑') + '标签'" :visible.sync="form_dialog" width="30%"
               @close="clearTags_Form">
      <el-form :model="tags_from" :rules="form_rules" ref="tags_from">
        <el-form-item label="标题名称" prop="value">
          <el-input v-model="tags_from.value" clearable/>
        </el-form-item>
        <el-form-item label="颜色" prop="color">
          <el-color-picker v-model="tags_from.color"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clearTags_Form">重 置</el-button>
        <el-button @click="form_dialog = false">取 消</el-button>
        <el-button @click="form_ok" type="primary">确 定</el-button>
      </div>
    </el-dialog>

    <!--    确认删除-->
    <el-dialog title="提示" :visible.sync="delete_dialog" width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="delete_dialog = false">取 消</el-button>
    <el-button type="primary" @click="MyDeletes">确 定</el-button>
    </span>
    </el-dialog>


  </div>
</template>

<script>
import utils from "@/utils/utils";

import Pagination from '@/components/Pagination'

import tagsApi from '@/api/tagsApi'

export default {
  name: 'tags',

  components: {
    Pagination
  },

  data() {
    return {
      tagsList: [],
      pageSize: 10,
      pageNum: 1,
      total: 10,
      listQuery: {
        value: '',
      },

      form_dialog: false,
      delete_dialog: false,

      delrow: {},
      deleteRow: {},

      tags_from: {},
      dialog_ADD: true,
      form_rules: {
        value: [
          {required: true, message: '请输入标签名', trigger: 'blur'},
          {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
        ],
      },


    }
  },

  created() {
    this.getList()

  },

  methods: {
    // 获取信息
    getList() {
      tagsApi.getPageList(this.pageNum, this.pageSize, this.listQuery).then(res => {
        if (res.status === 200) {
          this.tagsList = res.data.records
          this.total = res.data.total
        }
      })
    },

    // 新建修改弹窗
    createOrEdit(row) {
      this.form_dialog = true
      this.clearTags_Form()
      this.dialog_ADD = true
      if (row) {
        this.tags_from = Object.assign({}, row)
        this.dialog_ADD = false
      }
    },
    form_ok() {
      tagsApi.createOrUpdate(this.tags_from).then(res => {
        if (res.status === 200) {
          this.$message.success("修改成功")
          this.form_dialog = false
          this.getList()
        }
      })
    },
    clearTags_Form() {
      if (this.$refs.tags_from)
        this.$refs.tags_from.resetFields()
      this.tags_from = Object.assign({}, {})
    },


    // 复选框选中
    handlerSelectionChange(val) {
      this.delrow = val
    },

    //删除
    MyDelete(id) {
      this.deleteRow = [{'id': id}]
      this.DeleteIds()
    },
    MyDeletes() {
      this.deleteRow = this.delrow
      this.delete_dialog = false
      this.DeleteIds()
    },
    DeleteIds() {
      if (!this.deleteRow) {
        this.$message.error("请选择要删除的内容")
        return false
      }
      let ids = this.deleteRow.map(v => v.id)
      if (ids.length == 0) {
        this.$message.error("请选择要删除的内容")
      } else {
        tagsApi.deleteByIds(ids).then(res => {
          if (res.status === 200) {
            this.$message.success("删除成功")
            this.getList()
          }
        })
      }
    },


  }
}
</script>

<style>
</style>
