<template>
  <div>
    <!--    搜索-->
    <div style="padding: 10px 0">
      <el-input v-model="listQuery.title" placeholder="请输入标题" clearable style="width: 200px;"/>

      <el-select v-model="listQuery.state" placeholder="请选择公告状态" class="ml-5" clearable style="width: 200px;">
        <el-option label="启用" value="true"/>
        <el-option label="禁用" value="false"/>
      </el-select>

      <el-select v-model="listQuery.isSetTop" placeholder="请选择公告置顶状态" clearable class="mrl-5" style="width: 200px;">
        <el-option label="启用" value="true"/>
        <el-option label="禁用" value="false"/>
      </el-select>

      <el-button type="primary" @click="getList"> 搜索</el-button>
      <el-button type="warning" @click="reset"> 重置</el-button>
    </div>

    <!--    操作-->
    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="createOrEdit(null)">添加</el-button>
      <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="#ff0000"
                     title="您确定删除吗？" class="mrl-10" @confirm="MyDeletes">
        <el-button slot="reference" type="danger" size="mini" icon="el-icon-delete">删除</el-button>
      </el-popconfirm>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>

    <!--    信息-->
    <el-table stripe border style="width: 100%;margin-top: 20px" :data="list"
              @selection-change="handlerSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="id" prop="id" align="center" width="100"/>
      <el-table-column label="标题" prop="title" align="center" min-width="200"/>
      <el-table-column label="创建者" prop="creator" align="center" width="100"/>
      <el-table-column label="创建时间" prop="createTime" align="center" width="200"/>
      <el-table-column label="修改时间" prop="updateTime" align="center" width="200"/>
      <el-table-column label="修改者" prop="updator" align="center" width="100"/>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="{row}">
          <el-switch v-model="row.state" @change="changeByOne(row)" active-color="#13ce66" inactive-color="#ff4949"/>
        </template>
      </el-table-column>
      <el-table-column label="置顶" align="center" width="100">
        <template slot-scope="{row}">
          <el-switch v-model="row.isSetTop" @change="changeByOne(row)" active-color="#13ce66" inactive-color="#ff4949"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="{row}">
          <el-tooltip effect="dark" content="编辑" placement="top">
            <el-button @click="createOrEdit(row)" size="mini" type="primary" icon="el-icon-edit" circle/>
          </el-tooltip>

          <el-tooltip effect="dark" content="删除问题" placement="top">
            <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info" icon-color="#ff0000"
                           title="您确定删除吗？" class="ml-10" @confirm="MyDelete(row.id)">
              <template #reference>
                <el-button size="mini" type="primary" icon="el-icon-delete-solid" circle/>
              </template>
            </el-popconfirm>
          </el-tooltip>
        </template>
      </el-table-column>

    </el-table>

    <!--    标签页-->
    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize"
                style="margin-top: -20px;float: right" @pagination="getList"/>

    <!--    添加 修改-->
    <el-dialog :title="(dialog.ADD ? '新建' : '修改')" :visible.sync="dialog.is" width="70%" @close="clearNotice_Form">
      <el-form :model="dialog.form" :rules="dialog.rules" ref="notice">
        <el-form-item label="公告名称" prop="title">
          <el-input v-model="dialog.form.title" clearable/>
        </el-form-item>
        <el-form-item label="公告内容" prop="content" required>
          <el-col :span="24">
            <Editor :value.sync="dialog.form.content"></Editor>
          </el-col>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="clearNotice_Form">重 置</el-button>
        <el-button @click="dialog.is = false">取 消</el-button>
        <el-button @click="form_ok" type="primary">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import Editor from "@/components/admin/editor"

import noticeApi from "@/api/noticeApi";

export default {
  name: 'tags',

  components: {
    Pagination,
    Editor
  },

  data() {
    return {
      list: [],
      pageSize: 10,
      pageNum: 1,
      total: 10,
      listQuery: {
        title: '',
        state: '',
        isSetTop: '',
      },

      delrow: {},
      deleteRow: {},

      dialog: {
        ADD: true,
        is: false,
        form: {},
        rules: {
          title: [
            {required: true, message: '请输入公告标题', trigger: 'blur'},
            {min: 1, max: 20, message: '长度在 1 到 20 个字符', trigger: 'blur'}
          ],
          content: [
            {required: true, message: '请输入公告内容', trigger: 'blur'},
          ],
        },
      },

    }
  },

  created() {
    this.getList()

  },

  methods: {
    // 获取信息
    getList() {
      noticeApi.getPage(this.pageNum, this.pageSize, this.listQuery).then(res => {
        if (res.status === 200) {
          this.list = res.data.records
          this.total = res.data.total
        } else {
          this.$message.error(res.message)
        }
      })
    },
    reset() {
      Object.keys(this.listQuery).forEach(key => (this.listQuery[key] = ""))
      this.getList()
    },

    // 新建修改弹窗
    createOrEdit(row) {
      this.dialog.is = true
      this.clearNotice_Form()

      this.dialog.ADD = true
      if (row) {
        this.dialog.form = Object.assign({}, row)
        this.dialog.ADD = false
      }
    },
    form_ok() {
      noticeApi.createOrUpdate(this.dialog.form).then(res => {
        if (res.status === 200) {
          this.$message.success((this.dialog.ADD ? "新增" : '修改') + '成功')
          this.dialog.is = false
          this.getList()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    changeByOne(form) {
      noticeApi.createOrUpdate(form).then(res => {
        if (res.status === 200) {
          this.$message.success('修改成功')
        } else {
          this.$message.error(res.message)
        }
      })
    },
    clearNotice_Form() {
      if (this.$refs.notice)
        this.$refs.notice.resetFields()
      this.dialog.form = Object.assign({}, {})
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
        noticeApi.deleteByIds(ids).then(res => {
          if (res.status === 200) {
            this.$message.success("删除成功")
            this.getList()
          } else {
            this.$message.success(res.message)
          }
        })
      }
    },


  }
}
</script>

<style>
</style>