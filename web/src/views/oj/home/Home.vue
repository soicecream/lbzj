<template>
  <div>
    <el-row :gutter="20">
      <el-col :md="14" :sm="24">
        <el-card>
          <div slot="header" class="clearfix">
            <span><i class="el-icon-data-board"/> 最新公告</span>
          </div>

          <el-table :data="notice.list" class="notice-list">
            <el-table-column label="标题" min-width="300" align="center">
              <template slot-scope="{row}">
                <span @click="toNotice(row.id)" class="notice-list-to">{{ row.title }}</span>
              </template>
            </el-table-column>
            <el-table-column label="创建者" width="200" align="center">
              <template slot-scope="{row}">
                <span @click="toUser(row.creatorId)" class="notice-list-to"> {{ row.creator }}</span>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" prop="createTime" width="200" align="center"/>
          </el-table>


        </el-card>
      </el-col>

      <el-col :md="10" :sm="24" class="phone-margin">
        <el-card :class="contests.length ? 'card-top' : ''">
          <div slot="header" class="clearfix">
            <span><i class="el-icon-s-data"/> 排名 </span>
          </div>

          <el-table :data="topTenUser">
            <el-table-column label="#">
              <template slot-scope="scope">
                {{ scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="用户名">
              <template slot-scope="{row}">
                <span @click="toUser(row.id)" class="notice-list-to">{{ row.userName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="个性签名" prop="introduce"></el-table-column>
          </el-table>

        </el-card>
      </el-col>

    </el-row>
  </div>
</template>

<script>
import noticeApi from "@/api/noticeApi";
import {getTenTopUser} from "@/api/user";

export default {
  name: "oj-Home",

  data() {
    return {
      notice: {
        list: [],
      },

      contests: [],
      srcHight: "440px",

      topTenUser: [],


    }
  },

  mounted() {
    let screenWidth = window.screen.width;
    if (screenWidth < 768) {
      this.srcHight = "200px";
    } else {
      this.srcHight = "440px";
    }

  },

  created() {
    this.getHomeNotice()
    this.getTenTopUser()

  },

  methods: {
    getHomeNotice() {
      noticeApi.getHomeNotice().then(res => {
        if (res.status === 200) {
          this.notice.list = Object.assign([], res.data)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    getTenTopUser() {
      getTenTopUser().then(res => {
        console.log(res)
        if (res.status === 200) {
          this.topTenUser = res.data.records
          console.log(this.topTenUser)
        } else {
          this.$message.error(res.message)
        }
      })
    },

    goToNotice(noticeId) {
      this.$router.push('/notice/' + noticeId)
    },

    toMarkdown(text) {
      return this.$markDown.render(text)
    },

    toNotice(noticeId) {
      this.$router.push(`/notice/${noticeId}`)
    },
    toUser(userId) {
      this.$router.push(`/user/${userId}`)
    },


  },

}
</script>

<style scoped>
/deep/ .el-card__header {
  padding: 0.6rem 1.25rem !important;
}

.card-top {
  margin-top: 20px;
}

.notice-list-to {
  text-decoration: none;
  color: #00b7ff;
}

.notice-list-to:hover {
  cursor: pointer;
}

@media screen and (max-width: 991px) {
  .phone-margin {
    margin-top: 20px;
  }

}


</style>
