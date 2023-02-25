<template>
  <div>
    <el-card class="box-card">
      <div slot="header" class="top-title">
        <span>{{ notice.title }}</span>
        <div class="bottom time">
          <span @click="toUserInformation(notice.creator)" class="toUserName">{{ notice.creator }}</span>
          <span> 创建于 </span>
          <span>{{ notice.createTime }}</span>
        </div>
      </div>
      <div v-katex v-highlight v-html="notice.content" key="content" class="content-container markdown-body"></div>

    </el-card>
  </div>
</template>

<script>
import noticeApi from "@/api/noticeApi";

export default {
  name: "notice",
  data() {
    return {
      notice: {},

    }
  },

  created() {
    this.getInformation()
  },

  methods: {
    getInformation() {
      noticeApi.getInformation(this.$route.params.noticeId).then(res => {
        if (res.status === 200) {
          this.notice = res.data
          this.notice.content = this.$markDown.render(res.data.content)
        } else {
          this.$message.error(res.message)
        }
      })
    },

    toUserInformation(userId) {
      this.$router.push(`/user/${userId}`)
    },

  },
}
</script>

<style scoped>
.box-card {
  width: 50%;
  margin: auto;
}

.content-text {
  margin-left: 25px;
  margin-right: 20px;
  font-size: 15px;
}

.top-title {
  text-align: center;
  font-size: 38px;
}

.content-container {
  padding: 0 20px 20px 20px;
}

/deep/ .content-container a {
  color: #00b7ff;
  text-decoration: none;
}

.toUserName {
  color: #00b7ff;
}

.toUserName:hover {
  cursor: pointer;
}

.bottom {
  margin-top: 13px;
  line-height: 12px;
}

.time {
  font-size: 13px;
  color: #999;
}


</style>