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
    <el-table stripe border style="width: 100%;margin-top: 20px" :cell-style="{padding: '1'}" :data="list"
              @selection-change="setdelrow">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column label="题目编号" prop="index" align="center" width="100px">
        <template slot-scope="{row,$index}">
          <div>{{ row.problemId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="150px" align="center">
        <template slot-scope="{row}">
          <diV>{{ row.title }}</diV>
        </template>
      </el-table-column>
      <el-table-column label="总提交数" width="150px" align="center">
        <template slot-scope="{row}">
          <div>{{ row.submit }}</div>
        </template>
      </el-table-column>
      <el-table-column label="开放" width="200px" align="center">
        <template slot-scope="{row}">
          <el-switch
              v-model=row.defunct
              active-color="#13ce66"
              inactive-color="#ff4949"
              @change="udpro(row)">
          </el-switch>
        </template>
      </el-table-column>
      <el-table-column label="编辑" width="100px" align="center">
        <template slot-scope="{row}">
          <el-button @click="showuppro(row)" size="mini" type="primary" icon="el-icon-edit" circle></el-button>
        </template>
      </el-table-column>
      <el-table-column label="测试数据" width="100px" align="center">
        <template slot-scope="{row}">
          <div><i class="el-icon-s-order" @click="getfile(row.problemId)"></i></div>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"
                style="margin-top: -20px;float: right"
                @pagination="getList"/>

    <el-dialog title="添加题目" :visible.sync="showdig">
      <el-form :model="data">
        <el-form-item label="题目名称" label-width=100px>
          <el-input v-model="data.title" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间限制" label-width=100px>
          <el-input v-model="data.time_limit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="内存限制" label-width=100px>
          <el-input v-model="data.memory_limit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="题目描述" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="data.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="样例输入" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="data.sampleInput" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="样例输出" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="data.sampleOutput" autocomplete="off"></el-input>
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
        <el-button @click="showdig = false">取 消</el-button>
        <el-button type="primary" @click="addpro(data)">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog title="修改题目" :visible.sync="showdig2">
      <el-form :model="delrow">
        <el-form-item label="题目名称" label-width=100px>
          <el-input v-model="delrow.title" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="时间限制" label-width=100px>
          <el-input v-model="delrow.timeLimit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="内存限制" label-width=100px>
          <el-input v-model="delrow.memoryLimit" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="题目描述" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="delrow.description" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="样例输入" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="delrow.sampleInput" autocomplete="off"></el-input>
        </el-form-item>

        <el-form-item label="样例输出" label-width=100px>
          <el-input type="textarea"
                    :autosize="{ minRows: 4}" v-model="delrow.sampleOutput" autocomplete="off"></el-input>
        </el-form-item>


        <el-form-item label="问题难度" label-width=100px>
          <el-select v-model="delrow.degree" placeholder="请选择" @change=pdtype>
            <el-option
                v-for="item in 5"
                :key="item"
                :label="item"
                :value="item">
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="showdig2 = false">取 消</el-button>
        <el-button type="primary" @click="udpro(delrow)">确 定</el-button>
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

    <el-dialog
        title="文件列表"
        :visible.sync="showdig3"
        width="50%">
      <el-table
          border
          :data="file"
          @selection-change="">
        <el-table-column label="文件名" prop="index" align="center" width="100px">
          <template slot-scope="{row,$index}">
            <div>
              {{ row.name }}
            </div>
          </template>
        </el-table-column>
        <el-table-column label="大小" min-width="150px" align="center">
          <template slot-scope="{row}">
            <diV>{{ row.size }}bytes</diV>
          </template>
        </el-table-column>
        <el-table-column label="修改日期" width="150px" align="center">
          <template slot-scope="{row}">
            <div>{{ row.time }}</div>
          </template>
        </el-table-column>
        <el-table-column label="编辑" width="100px" align="center">
          <template slot-scope="{row}">
            <el-button @click="" size="mini" type="primary" icon="el-icon-edit" circle></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {addPro, delPro, getAdminprolist, getFile, updatePro} from "@/api/problem";


export default {
  name: 'ProblemList',
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
      showdig3: false,
      pd: true,
      data: {
        title: '',
        description: '',
        input: '',
        output: '',
        sampleInput: '',
        sampleOutput: '',
        time_limit: '',
        memory_limit: '',
        degree: ''
      },
      row: {},
      delrow: [],
      file: {}
    }
  },
  methods: {
    getList() {
      getAdminprolist(this.page, this.limit, this.listQuery).then(response => {
        this.list = response.data.rows
        this.total = response.data.total
      })
    },
    getfile(id) {
      this.showdig3 = true
      getFile(id).then(res => {
        this.file = res.data
      })
    },
    tableuppro(row) {
      this.showdig1 = true;
      this.row = row
    },
    showuppro(row) {
      this.showdig2 = true
      this.delrow = row
    },
    udpro(row) {
      updatePro(row).then(res => {
        this.showdig2 = false;
        this.$message("修改成功")
      })
    },
    addpro(data) {
      this.showdig = false;
      addPro(data).then(res => {
      })
    },
    delPro() {
      this.showdig1 = false
      var ids = []
      for (var i = 0; i < this.delrow.length; i++) {
        ids[i] = this.delrow[i].problemId
      }
      delPro(ids).then(res => {
        this.$message({
          showClose: true,
          message: '删除成功',
          type: 'success'
        });
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
