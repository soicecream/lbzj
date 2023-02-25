<template>
  <div>
    <!--    搜索-->
    <div style="padding: 10px 0">
      <el-input v-model="listQuery.title" placeholder="请输入标题" clearable style="width: 200px;"/>

      <el-button type="primary" @click="getList"> 搜索</el-button>
      <el-button type="warning" @click="reset"> 重置</el-button>
    </div>

    <!--    信息-->
    <el-table stripe border style="width: 100%;margin-top: 20px" :data="list">
      <el-table-column label="标题" min-width="200">
        <template slot-scope="{row}">
          <span v-if="row.isSetTop" class="isSetTopClass"> [ 置顶 ] </span>
          <span @click="getToNotice(row.id)" class="toUrl">
            {{ row.title }}
            </span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" align="center" width="200">
        <template slot-scope="{row}">
          <span @click="goToUser(row.creatorId)" class="toUrl"> {{ row.creator }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" prop="createTime" align="center" width="200"/>
    </el-table>

    <!--    标签页-->
    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize"
                style="margin-top: -20px;float: right" @pagination="getList"/>
  </div>
</template>

<script>
import noticeApi from "@/api/noticeApi";
import pagination from "@/components/Pagination";

export default {
  name: "list",

  components: {
    pagination,
  },

  data() {
    return {
      list: [],
      total: 0,
      pageNum: 1,
      pageSize: 5,

      listQuery: {
        title: '',
      },

    }
  },

  created() {
    this.getList()
  },

  methods: {
    getList() {
      noticeApi.getPageList(this.pageNum, this.pageSize, this.listQuery).then(res => {
        if (res.status === 200) {
          this.list = res.data
          this.total = res.data.length
        } else {
          this.$message.error(res.message)
        }
      })
    },

    reset() {

    },

    getToNotice(noticeId) {
      this.$router.push(`/notice/${noticeId}`)
    },

    goToUser(userId) {
      this.$router.push(`/user/${userId}`)
    },

  },
}
</script>

<style scoped>

.isSetTopClass {
  color: red;
}

.toUrl {
  color: #00b7ff;
}

.toUrl:hover {
  cursor: pointer;
}

</style>