<template>
  <div>
    <el-input v-model="input" placeholder="请输入内容" style="width: 200px;margin-left: 40%"></el-input>
    <el-button icon="el-icon-search" circle></el-button>

    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="showProblem_dialog(null)">添加</el-button>
      <el-button type="danger" size="mini" icon="el-icon-delete" @click="delete_dialog=true">删除</el-button>
      <el-button size="mini" type="info" icon="el-icon-download">导入</el-button>
      <el-button type="warning" size="mini" icon="el-icon-upload2">导出</el-button>
    </div>

    <!--    信息-->
    <el-table stripe border style="width: 100%;margin-top: 20px" :data="list" @selection-change="setdelrow">
      <el-table-column type="selection" width="55"/>
      <el-table-column label="题目编号" prop="problemId" align="center" width="100px"/>
      <el-table-column label="标题" prop="title" align="center" min-width="150px"/>
      <el-table-column label="总提交数" prop="submit" align="center" width="150px"/>
      <el-table-column label="开放" width="200px" align="center">
        <template slot-scope="{row}">
          <el-switch v-model="row.defunct" active-color="#13ce66" inactive-color="#ff4949"
                     @change="changedeFunct(row)"/>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button @click="showProblem_dialog(row)" size="mini" type="primary" icon="el-icon-edit" circle/>
        </template>
      </el-table-column>
      <el-table-column label="测试数据" width="100px" align="center">
        <template slot-scope="{row}">
          <div><i class="el-icon-s-order" @click="getFile(row.problemId)"/></div>
        </template>
      </el-table-column>
    </el-table>

    <!--    标签页-->
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"
                style="margin-top: -20px;float: right" @pagination="getList"/>

    <!--    添加 修改-->
    <el-dialog :title="problemTitle_dialog + '题目'" :visible.sync="problem_dialog">
      <el-form :model="problem" label-width="100px" ref="problem">
        <el-form-item label="题目名称" prop="title">
          <el-input v-model="problem.title" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间限制" prop="timeLimit">
          <el-input v-model="problem.timeLimit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="内存限制" prop="memoryLimit">
          <el-input v-model="problem.memoryLimit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="问题难度" prop="degree">
          <el-select v-model="problem.degree" placeholder="请选择" @change="pdtype">
            <el-option v-for="item in worktype" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="题目描述" prop="description">
          <el-input v-model="problem.description" type="textarea" :autosize="{minRows: 4, maxRows: 4}"/>
        </el-form-item>

        <el-form-item label="输入描述" prop="input">
          <el-input v-model="problem.input" type="textarea" :autosize="{minRows: 4, maxRows: 4}"/>
        </el-form-item>

        <el-form-item label="输出描述" prop="output">
          <el-input v-model="problem.output" type="textarea" :autosize="{minRows: 4,maxRows: 4}"/>
        </el-form-item>


        <el-form-item v-for="(sample, index) in problem.samples" :key="index" :label="'题目样例' + (index + 1)"
                      :prop="'samples' + index">
          <el-row :gutter="10">
            <el-col :span="10">
              <el-input v-model="sample.input" type="textarea" :autosize="{minRows: 3,maxRows: 3}"/>
            </el-col>
            <el-col :span="10">
              <el-input v-model="sample.output" type="textarea" :autosize="{minRows: 3,maxRows: 3}"/>
            </el-col>
            <el-col :span="3">
              <el-button @click="removeSample(index)" style="margin-top: 15px;">删除</el-button>
            </el-col>
          </el-row>
        </el-form-item>
        <el-divider content-position="left">仅为题目中测试的样例 不加入测试用例</el-divider>

        <el-form-item label="提示" prop="hint">
          <el-input v-model="problem.hint" type="textarea" :autosize="{minRows: 4,maxRows: 4}"/>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="resetProblem_dialog">重置表单</el-button>
        <el-button @click="addSample">添加样例</el-button>
        <el-button @click="closeProblem_dialog">取 消</el-button>
        <el-button @click="problem_form_ok" type="primary">确 定</el-button>
      </div>
    </el-dialog>

    <!--    确认删除-->
    <el-dialog title="提示" :visible.sync="delete_dialog" width="30%">
      <span>确认删除</span>
      <span slot="footer" class="dialog-footer">
     <el-button @click="delete_dialog = false">取 消</el-button>
    <el-button type="primary" @click="delete_problem">确 定</el-button>
    </span>
    </el-dialog>

    <!--    测试数据-->
    <el-dialog title="文件列表" :visible.sync="problemFiles_dialog" width="50%">
      <el-table border :data="files" @selection-change="">
        <el-table-column label="文件名" prop="name" align="center" width="100px"/>
        <el-table-column label="大小" min-width="150px" align="center">
          <template slot-scope="{row}">
            <diV>{{ row.size }}bytes</diV>
          </template>
        </el-table-column>
        <el-table-column label="修改日期" prop="time" width="150px" align="center"/>
        <el-table-column label="编辑" width="100px" align="center">
          <template slot-scope="{row}">
            <el-button @click="updateFile" size="mini" type="primary" icon="el-icon-edit" circle>编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import utils from "@/utils/utils";

import Pagination from '@/components/Pagination'
import {delPro, getAdminprolist, getFile, insertOrUpdate} from "@/api/problem";


export default {
  name: 'ProblemList',
  components: {Pagination},
  created() {
    this.getList()
  },
  data() {
    return {
      list: [],
      limit: 10,
      page: 1,
      total: 10,
      listQuery: {},

      problem_dialog: false,
      delete_dialog: false,
      problemFiles_dialog: false,

      showdig2: false,
      pd: true,
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
  methods: {
    // 获取信息
    getList() {
      getAdminprolist(this.page, this.limit, this.listQuery).then(res => {
        this.list = res.data.rows
        this.total = res.data.total
      })
    },

    // 获取文件
    getFile(id) {
      this.problemFiles_dialog = true
      getFile(id).then(res => {
        this.files = res.data
      })
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

    setdelrow(val) {
      this.delrow = val
    },

    pdtype() {

    },


    // 打开关闭 问题添加修改弹窗
    showProblem_dialog(data) {
      this.resetProblem_dialog()

      this.problemTitle_dialog = "添加"
      if (data) {
        this.problemTitle_dialog = "修改"
        this.problem = data

        this.problem.samples = utils.stringToExamples(data.samples)
      }

      this.problem_dialog = true
    },
    closeProblem_dialog() {
      this.resetProblem_dialog()

      this.problem_dialog = false
    },

    // 添加删除样例
    addSample() {
      this.problem.samples.push({input: "", output: ""})
    },
    removeSample(index) {
      if (index != -1) {
        this.problem.samples.splice(index, 1)
      }
    },

    // 保存 新增或修改
    problem_form_ok() {
      this.problem.samples = utils.examplesToString(this.problem.samples)
      insertOrUpdate(this.problem).then(res => {
        this.closeProblem_dialog()
        this.getList()
      })
    },
    // 修改状态
    changedeFunct(row) {
      insertOrUpdate(row).then(res => {
      })
    },

    // 重置
    resetProblem_dialog() {
      if (this.$refs.problem) {
        this.$refs.problem.resetFields()

        this.problem.samples = this.problem_samples_init
      }
    },

    updateFile() {

    },

  }
}
</script>

<style>
</style>
