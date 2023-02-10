<template>
  <div>
    <el-input v-model="listQuery.title" placeholder="请输入内容" style="width: 200px;margin-left: 40%"></el-input>
    <el-button icon="el-icon-search" circle @click="getList"></el-button>

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
      <el-table-column label="题目编号" prop="problemId" align="center" width="100px"/>
      <el-table-column label="标题" prop="title" align="center" min-width="150px"/>
      <el-table-column label="总提交数" prop="submit" align="center" width="150px"/>
      <el-table-column label="开放" width="200px" align="center">
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

    <!--    添加 修改-->
    <div>
      <!--    <el-dialog :title="problemTitle_dialog + '题目'" :visible.sync="problem_dialog">-->
      <!--      <el-form :model="problem" label-width="100px" ref="problem">-->
      <!--        <el-form-item label="题目名称" prop="title">-->
      <!--          <el-input v-model="problem.title" autocomplete="off"></el-input>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="时间限制" prop="timeLimit">-->
      <!--          <el-input v-model="problem.timeLimit" autocomplete="off"></el-input>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="内存限制" prop="memoryLimit">-->
      <!--          <el-input v-model="problem.memoryLimit" autocomplete="off"></el-input>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="问题难度" prop="degree">-->
      <!--          <el-select v-model="problem.degree" placeholder="请选择" @change="pdtype">-->
      <!--            <el-option v-for="item in worktype" :key="item.value" :label="item.label" :value="item.value"></el-option>-->
      <!--          </el-select>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="题目描述" prop="description">-->
      <!--          <el-input v-model="problem.description" type="textarea" :autosize="{minRows: 4, maxRows: 4}"/>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="输入描述" prop="input">-->
      <!--          <el-input v-model="problem.input" type="textarea" :autosize="{minRows: 4, maxRows: 4}"/>-->
      <!--        </el-form-item>-->

      <!--        <el-form-item label="输出描述" prop="output">-->
      <!--          <el-input v-model="problem.output" type="textarea" :autosize="{minRows: 4,maxRows: 4}"/>-->
      <!--        </el-form-item>-->


      <!--        <el-form-item v-for="(sample, index) in problem.samples" :key="index" :label="'题目样例' + (index + 1)"-->
      <!--                      :prop="'samples' + index">-->
      <!--          <el-row :gutter="10">-->
      <!--            <el-col :span="10">-->
      <!--              <el-input v-model="sample.input" type="textarea" :autosize="{minRows: 3,maxRows: 3}"/>-->
      <!--            </el-col>-->
      <!--            <el-col :span="10">-->
      <!--              <el-input v-model="sample.output" type="textarea" :autosize="{minRows: 3,maxRows: 3}"/>-->
      <!--            </el-col>-->
      <!--            <el-col :span="3">-->
      <!--              <el-button @click="removeSample(index)" style="margin-top: 15px;">删除</el-button>-->
      <!--            </el-col>-->
      <!--          </el-row>-->
      <!--        </el-form-item>-->
      <!--        <el-divider content-position="left">仅为题目中测试的样例 不加入测试用例</el-divider>-->

      <!--        <el-form-item label="提示" prop="hint">-->
      <!--          <el-input v-model="problem.hint" type="textarea" :autosize="{minRows: 4,maxRows: 4}"/>-->
      <!--        </el-form-item>-->

      <!--      </el-form>-->
      <!--      <div slot="footer" class="dialog-footer">-->
      <!--        <el-button @click="resetProblem_dialog">重置表单</el-button>-->
      <!--        <el-button @click="addSample">添加样例</el-button>-->
      <!--        <el-button @click="closeProblem_dialog">取 消</el-button>-->
      <!--        <el-button @click="problem_form_ok" type="primary">确 定</el-button>-->
      <!--      </div>-->
      <!--    </el-dialog>-->
    </div>

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

      problem_dialog: false,
      delete_dialog: false,
      problemFiles_dialog: false,

      data: {
        title: '',
        description: '',
        input: '',
        output: '',
        samples: [{input: "", output: ""}],
        sampleInput: '',
        sampleOutput: '',
        time_limit: '',
        memory_limit: '',
        degree: ''
      },
      row: {},
      delrow: {},
      files: [],

      worktype: [],
      input: "",


      problemTitle_dialog: "",
      problem: {
        title: '', // 标题
        timeLimit: '', // 时间限制
        memoryLimit: '', // 空间限制
        degree: '', // 难度
        description: '', // 内容
        input: '',  // 输入描述
        output: '', // 输出描述
        samples: [
          {input: "", output: ""},
        ], // 样例
        hint: '', // 提示
      },
      problem_samples_init: "<input></input><output></output>",

    }
  },

  created() {
    this.getList()

  },

  methods: {
    // 获取信息
    getList() {
      getAdminprolist(this.pageNum, this.pageSize, this.listQuery).then(res => {
        this.problemList = res.data.rows
        this.total = res.data.total
      })
    },

    // 获取文件
    // getFile(id) {
    //   this.problemFiles_dialog = true
    //   getFile(id).then(res => {
    //     this.files = res.data
    //   })
    // },

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
