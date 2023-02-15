<template>
  <el-card class="box-card" style="width: 95%;margin: 0.5rem auto;height: 88%">
    <!--    搜索-->
    <div style="padding: 10px 0">
      <!--      <el-input v-model="listQuery.problemId" placeholder="请输入题目id" clearable style="width: 150px;"/>-->
      <!--      <el-input v-model="listQuery.title" placeholder="请输入题目标题" clearable style="width: 150px; margin-left: 5px;"/>-->
      <!---->
      <!--      <el-select v-model="listQuery.degree" placeholder="请选择题目难度" clearable style="width: 150px; margin-left: 5px;">-->
      <!--        <el-option v-for="(item, index) in problemDifficulty" :key="item" :label="item" :value="index"/>-->
      <!--      </el-select>-->

      <!--      <el-button type="primary" @click="getList"> 搜索</el-button>-->
      <!--      <el-button type="warning" @click="reset"> 重置</el-button>-->
    </div>

    <!--    <div slot="header" class="clearfix">-->
    <!--      <span style="font-size: 1.4rem">题目列表</span>-->

    <!--      <el-input v-model="listQuery.title" placeholder="请输入标题" size="small"-->
    <!--                style="float: right;width: 200px;height: 20px" @change="getList">-->
    <!--      </el-input>-->

    <!--      <el-input v-model="listQuery.problemId" placeholder="请输入问题编号" size="small"-->
    <!--                style="float: right;width: 150px;height: 20px" @change="getList">-->
    <!--      </el-input>-->


    <!--&lt;!&ndash;      <el-select size="small" v-model="listQuery.degree" placeholder="难度" clearable&ndash;&gt;-->
    <!--&lt;!&ndash;                 style="width: 90px;margin-right: 1rem;float: right" class="filter-item">&ndash;&gt;-->
    <!--&lt;!&ndash;        <el-option v-for="(item, index) in degreeOptions" :key="item" :label="item" :value="index + 1"/>&ndash;&gt;-->
    <!--&lt;!&ndash;      </el-select>&ndash;&gt;-->
    <!--    </div>-->

    <el-table style="width: 100%;margin-top: -20px" :data="list">
      <el-table-column label="#" prop="index" align="center" width="100">
        <template slot-scope="{row}">
          <div @click="ToPage(row.problemId)">{{ row.problemId }}</div>
        </template>
      </el-table-column>
      <el-table-column label="标题" width="1000" align="center">
        <template slot-scope="{row}">
          <div @click="ToPage(row.problemId)">{{ row.title }}</div>
        </template>
      </el-table-column>
      <el-table-column label="难度" min-width="150" align="center" @change="getList">
        <template slot-scope="{row}">
          <el-rate v-model="row.degree" disabled :colors="degreeColor">
          </el-rate>
        </template>
      </el-table-column>
      <el-table-column label="总提交数" width="100" align="center">
        <template slot-scope="{row}">
          <div>{{ row.submit }}</div>
        </template>
      </el-table-column>
      <el-table-column label="通过率" width="100" align="center">
        <template slot-scope="{row}">
          <div>{{ row.accepted, row.submit | acRate }}</div>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="page" :limit.sync="limit"
                style="margin-top: -20px;float: right" @pagination="getList"/>
  </el-card>

</template>

<script>
import Pagination from '@/components/Pagination'
import {fetchProblemsList} from "@/api/problem";
import {removeWorkid} from '@/utils/auth'
import {JUDGE_STATUS, Languages, DEGREE} from "@/utils/constants";

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
      degreeColor: DEGREE.color,
    }
  },
  filters: {
    acRate(solved, submit) {
      if (submit != 0)
        return (solved / submit * 100.0).toFixed(2) + "%"
      return 0 + "%";
    },
  },
  methods: {
    getList() {
      fetchProblemsList(this.page, this.limit, this.listQuery).then(res => {
        this.list = res.data.rows
        this.total = res.data.total
      })
    },
    ToPage(id) {
      this.$router.push('/problem/' + id)
      removeWorkid();
    },
    toCreate() {
      this.$router.push('/problemCreate')
    }
  }
}
</script>

<style>
</style>
