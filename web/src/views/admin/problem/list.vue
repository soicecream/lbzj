<template>
  <div>
    <div style="padding: 10px 0">
      <el-input v-model="listQuery.problemId" placeholder="请输入题目id" clearable style="width: 150px;"/>
      <el-input v-model="listQuery.title" placeholder="请输入题目标题" class="ml-5" clearable style="width: 150px;"/>

      <el-select v-model="listQuery.degree" placeholder="请选择题目难度" class="ml-5" clearable style="width: 150px;">
        <el-option v-for="(item, index) in problemDifficulty" :key="item" :label="item" :value="index"/>
      </el-select>

      <el-select v-model="listQuery.defunct" placeholder="请选择题目状态" clearable class="mrl-5" style="width: 150px;">
        <el-option label="启用" value="true"/>
        <el-option label="禁用" value="false"/>
      </el-select>

      <el-button type="primary" @click="getList"> 搜索</el-button>
      <el-button type="warning" @click="reset"> 重置</el-button>
    </div>

    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="to_Problem_createOrEdit(null)">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete" @click="delete_dialog=true">删除</el-button>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>

    <!--    信息-->
    <el-table stripe border style="width: 100%;margin-top: 20px" :data="problemList"
              @selection-change="handlerSelectionChange">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="题目编号" prop="problemId" align="center" width="100"/>
      <el-table-column label="标题" prop="title" align="center" min-width="300"/>
      <el-table-column label="难度" align="center" min-width="100">
        <template slot-scope="{row}">
          {{ problemDifficulty[row.degree] }}
        </template>
      </el-table-column>
      <el-table-column label="总提交数" prop="submit" align="center" width="100"/>
      <el-table-column label="开放" width="100" align="center">
        <template slot-scope="{row}">
          <el-switch v-model="row.defunct" active-color="#13ce66" inactive-color="#ff4949" @change="changeEnabel(row)"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="{row}">
          <el-tooltip class="item" effect="dark" content="编辑" placement="top">
            <el-button @click="to_createOrEdit(row.problemId)" size="mini" type="primary" icon="el-icon-edit"/>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="下载测试样例" placement="top">
            <el-button @click="downloadSample(row.problemId)" size="mini" type="success" icon="el-icon-download"/>
          </el-tooltip>

          <el-tooltip class="item" effect="dark" content="删除" placement="top">
            <el-button @click="delete_problems(row.problemId)" size="mini" type="danger" icon="el-icon-delete-solid"/>
          </el-tooltip>
        </template>
      </el-table-column>
    </el-table>

    <!--    标签页-->
    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize"
                style="margin-top: -20px;float: right" @pagination="getList"/>

    <!--    确认删除-->
    <el-dialog title="提示" :visible.sync="delete_dialog" width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="delete_dialog = false">取 消</el-button>
    <el-button type="primary" @click="delete_problem">确 定</el-button>
    </span>
    </el-dialog>

  </div>
</template>

<script>
import utils from "@/utils/utils";

import Pagination from '@/components/Pagination'
import {changEnable, delPro, downloadSample, getAdminprolist, getFile, insertOrUpdate} from "@/api/problem";


export default {
  name: 'list',

  components: {
    Pagination
  },

  data() {
    return {
      problemList: [],
      pageSize: 10,
      pageNum: 1,
      total: 10,
      listQuery: {},

      delete_dialog: false,

      delrow: {},
      problemDifficulty: ['未知', '简单', '中等', '一般', '困难', '地狱'],


    }
  },

  created() {
    this.getList()

  },

  methods: {
    // 获取信息
    getList() {
      getAdminprolist(this.pageNum, this.pageSize, this.listQuery).then(res => {
        console.log(res)
        this.problemList = res.data.records
        this.total = res.data.total
      })
    },

    reset() {
      Object.keys(this.listQuery).forEach(key => (this.listQuery[key] = ""))
      this.getList()
    },

    // 复选框选中
    handlerSelectionChange(val) {
      this.delrow = val
    },

    // 去删除
    delete_problem() {
      this.delete_dialog = false
      var ids = this.delrow.map(v => v.problemId)
      delPro(ids).then(res => {
        this.$message.success('删除成功')
        this.getList()
      })
    },
    delete_problems(id) {
      delPro([id]).then(res => {
        this.$message.success("删除成功")
        this.getList()
      })
    },

    // 难度选择
    pdtype() {

    },

    // 打开关闭 问题添加修改弹窗
    to_createOrEdit(problemId) {
      if (problemId) {
        this.$router.push('/admin/pro/edit/' + problemId)
      } else {
        this.$router.push('/admin/pro/create')
      }
    },

    // 修改状态
    changeEnabel(row) {
      changEnable(row).then(res => {
        if (res.status === 200) {
          this.$message.success("修改成功")
        }
      })
    },

    downloadSample(id) {
      window.open(`http://localhost/api/problem/download/sample/${id}`)
    },

    // 时间戳 => yyyy-MM-dd HH:mm:ss
    timeToDate(time) {
      if (time) {
        var date = new Date(time)

        var Y = date.getFullYear()
        var M = date.getMonth() + 1
        M = M < 10 ? '0' + M : M
        var D = date.getDate()
        D = D < 10 ? '0' + D : D
        var h = date.getHours()
        var m = date.getMinutes()
        m = m < 10 ? '0' + m : m
        var s = date.getSeconds()
        s = s < 10 ? '0' + s : s

        return Y + '-' + M + '-' + D + ' ' + h + ':' + m + ':' + s
      } else {
        return ''
      }
    },

  }
}
</script>

<style>
</style>
