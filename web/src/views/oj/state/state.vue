<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;">
    <div slot="header" class="clearfix pd-10">
      <span style="font-size: 1.4rem">提交列表</span>

      <!-- 搜索栏 -->
      <div class="pd-10" style="float: right">
        <el-switch v-model="listQuery.isAll" active-text="全部" inactive-text="自己" class="mrl-10" @change="getList"/>

        <el-input v-model="listQuery.problemId" placeholder="请输入题目id" clearable style="width: 150px;"/>

        <el-input v-model="listQuery.nickName" placeholder="请输入用户" :disabled="!listQuery.isAll" clearable class="ml-5"
                  style="width: 150px;"/>

        <el-select v-model="listQuery.result" clearable placeholder="请选择结果" class="ml-5">
          <el-option v-for="(item, index) in results" :key="index" :label="item.name" :value="index" v-if="item.show"/>
        </el-select>

        <el-select v-model="listQuery.language" clearable placeholder="请选择语言" class="mrl-5">
          <el-option v-for="(item, index) in languages" :key="index.lang" :label="item.lang" :value="index"/>
        </el-select>

        <el-button type="primary" @click="getList"> 搜索</el-button>
        <el-button type="warning" @click="reset"> 重置</el-button>
      </div>

    </div>

    <el-table style="margin-top: -20px" v-loading="listLoading" :data="list">
      <el-table-column label="提交编号" prop="solutionId" align="center" width="100px">
        <template slot-scope="{row}">
          <div>
            {{ row.solutionId }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="用户" width="200px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.nick }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="题目编号" min-width="100px">
        <template slot-scope="{row}">
          <div>
            {{ row.problemId }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="结果" width="250px" align="center">
        <template slot-scope="{row}">
          <div>
            <el-button :type="row.result| resultType" :loading="row.result | resultLoad" size="small">
              {{ row.result | resultName }}
            </el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="内存" width="125px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.memory | memory }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="耗时" width="125px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.time | time }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="语言" width="125px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.language | lang }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="代码长度" width="150px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.codeLength | codeLength }}
          </div>
        </template>
      </el-table-column>
      <el-table-column label="提交时间" width="200px" align="center">
        <template slot-scope="{row}">
          <div>
            {{ row.inDate | toTime }}
          </div>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit" style="float: right"
                @pagination="getList()"/>

  </el-card>
</template>

<script>
import NavBar from '../../../components/oj/NavBar'
import pagination from '@/components/Pagination'
import {JUDGE_STATUS, Languages} from '@/utils/constants'
import {mapGetters} from "vuex";
import {fetchSubmissionsList} from "@/api/submission";

export default {
  name: 'state',
  components: {NavBar, pagination},
  data() {
    return {
      list: null,
      listQuery: {
        isAll: true,
        problemId: '',
        nickName: '',
        result: '',
        language: '',
        sort: "-time",
      },
      listLoading: true,
      limit: 10,
      page: 1,
      total: 10,
      time: null,

      results: JUDGE_STATUS,
      languages: Languages,


    }
  },
  created() {
    this.getList()
  },
  filters: {
    memory: function (kb) {
      return kb + 'KB'
    },
    time: function (t) {
      return t + 'ms'
    },
    codeLength: function (len) {
      return len + 'bytes'
    },
    resultName: function (num) {
      return JUDGE_STATUS[num + ""].name
    },
    resultType: function (num) {
      return JUDGE_STATUS[num + ""].type
    },
    resultLoad: function (num) {
      return JUDGE_STATUS[num + ""].loading
    },
    lang: function (num) {
      return Languages[num + ""].lang
    },
    toTime: function (t) {
      return new Date(t).toLocaleString();
    }
  },
  methods: {
    ...mapGetters(['getSolutions']),
    getList() {
      if (this.listQuery.isAll) {
        this.listQuery.nickName = ""
        this.listQuery.nick = ""
      } else {
        this.listQuery.nick = this.listQuery.nickName
      }

      fetchSubmissionsList(this.page, this.limit, this.listQuery).then(res => {
        this.list = res.data.records
        this.total = res.data.total
      })
      this.listLoading = false
    },

    reset() {
      this.listQuery = Object.assign({}, {
        isAll: true,
        problemId: '',
        nickName: '',
        result: '',
        language: '',
        sort: "-time",
      });
      this.getList()
    },
  }
}
</script>

<style>
</style>
