<template>
  <div>
    <!--    搜索-->
    <div style="padding: 10px 0">
      <el-input v-model="listQuery.problemId" placeholder="请输入题目id" clearable style="width: 150px;"/>
      <el-input v-model="listQuery.title" placeholder="请输入题目标题" class="ml-5" clearable style="width: 150px;"/>

      <el-select v-model="listQuery.degree" placeholder="请选择题目难度" class="ml-5" clearable style="width: 150px;">
        <el-option v-for="(item, index) in degrees.title" :key="item" :label="item" :value="index"/>
      </el-select>

      <el-select v-model="listQuery.defunct" placeholder="请选择题目状态" clearable class="mrl-5" style="width: 150px;">
        <el-option label="启用" value="true"/>
        <el-option label="禁用" value="false"/>
      </el-select>

      <el-button type="primary" @click="getList"> 搜索</el-button>
      <el-button type="warning" @click="reset"> 重置</el-button>
    </div>

    <!--    操作-->
    <div>
      <el-button type="success" size="mini" icon="el-icon-plus" @click="to_createOrEdit(null)">添加</el-button>
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
          <span :style="{'color': degrees.color[row.degree]}">
          {{ degrees.title[row.degree] }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="总提交数" prop="submit" align="center" width="100"/>
      <el-table-column label="状态" width="100" align="center">
        <template slot-scope="{row}">
          <el-switch v-model="row.defunct" active-color="#13ce66" inactive-color="#ff4949" @change="changeEnabel(row)"/>
        </template>
      </el-table-column>
      <el-table-column label="视频状态" width="100" align="center">
        <template slot-scope="{row}">
          <el-switch v-model="row.videoDefunct" @change="changeEnabel(row)" :disabled="!row.videoIsUpload"
                     active-color="#13ce66" inactive-color="#ff4949"/>
        </template>
      </el-table-column>

      <el-table-column label="操作" width="250" align="center">
        <template slot-scope="{row}">
          <el-tooltip effect="dark" content="编辑问题" placement="top">
            <el-button @click="to_createOrEdit(row.problemId)" size="mini" type="primary" icon="el-icon-edit"/>
          </el-tooltip>

          <el-tooltip effect="dark" content="下载测试样例" placement="top">
            <el-button @click="downloadSample(row.problemId)" size="mini" type="success" icon="el-icon-download"/>
          </el-tooltip>

          <el-tooltip effect="dark" content="删除问题" placement="top">
            <el-popconfirm confirm-button-text='确定' cancel-button-text='我再想想' icon="el-icon-info"
                           icon-color="#ff0000"
                           title="您确定删除吗？" class="ml-10" @confirm="delete_problems(row.problemId)">
              <template #reference>
                <el-button size="mini" type="danger" icon="el-icon-delete-solid"/>
              </template>
            </el-popconfirm>
          </el-tooltip>
        </template>
      </el-table-column>

      <el-table-column label="视频操作" width="250" align="center">
        <template slot-scope="{row}">

          <el-tooltip effect="dark" content="上传视频" placement="top">
            <el-button @click="to_video_upload(row.problemId)" size="mini" type="primary" icon="el-icon-edit"/>
          </el-tooltip>


          <el-tooltip effect="dark" :content="'预览视频' + (row.videoIsUpload ? '' : '前 请先上传')" placement="top">
            <el-button @click="video_preview(row.problemId)" :disabled="!row.videoIsUpload" size="mini" type="warning"
                       icon="el-icon-view"/>
          </el-tooltip>

          <el-tooltip effect="dark" content="下载视频" placement="top">
            <el-button @click="video_download(row.problemId)" :disabled="!row.videoIsUpload" size="mini" type="success"
                       icon="el-icon-download"/>
          </el-tooltip>

          <el-tooltip effect="dark" content="删除视频" placement="top">
            <el-popconfirm @confirm="video_delete(row.problemId)" confirm-button-text='确定'
                           cancel-button-text='我再想想'
                           icon="el-icon-info" icon-color="#ff0000" title="您确定删除吗？" class="ml-10">
              <template #reference>
                <el-button :disabled="!row.videoIsUpload" size="mini" type="danger" icon="el-icon-delete-solid"/>
              </template>
            </el-popconfirm>
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

    <!--    视频上传-->
    <el-dialog title="视频上传" :visible.sync="video.dialog.upload" width="30%">
      <div class="importDialog-content">
        <el-upload action="" :http-request="video_upload" drag :limit="1" :auto-upload="false" accept=".mp4"
                   ref="importVideo">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip" slot="tip">只能上传mp4文件</div>
          <div class="el-upload__tip" slot="tip">用已覆盖的形式上传</div>
        </el-upload>
      </div>
      <span slot="footer">
        <el-button @click="video_upload_close">取 消</el-button>
        <el-button type="primary" @click="video_upload_ok">确定上传</el-button>
      </span>
    </el-dialog>

    <el-dialog title="视频预览" :visible.sync="video.dialog.preview" width="30%"
               @visible-change="videoDialogVisibleChange">
      <d-player ref="player" id="dplayer" :options="video.options"></d-player>
    </el-dialog>

  </div>
</template>

<script>
import utils from "@/utils/utils";
import axios from "axios";

import Pagination from '@/components/Pagination'
import {
  adminGetVideo,
  changEnable,
  delPro, deleteVideo,
  downloadSample,
  getAdminprolist,
  getFile, getVideo,
  insertOrUpdate,
  uploadSampleFile, uploadVideo,
} from "@/api/problem";
import {DEGREE} from "@/utils/constants";
import request from "@/utils/request";
import {getToken} from "@/utils/auth";


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
      degrees: DEGREE,

      video: {
        dialog: {
          upload: false,
          preview: false,
        },
        problemId: null,

        options: {
          container: document.getElementById("dplayer"), //播放器容器
          theme: "#b7daff", // 风格颜色，例如播放条，音量条的颜色
          loop: false, // 是否自动循环
          lang: "zh-cn", // 语言，'en', 'zh-cn', 'zh-tw'
          screenshot: true, // 是否允许截图（按钮），点击可以自动将截图下载到本地
          hotkey: true, // 是否支持热键，调节音量，播放，暂停等
          preload: "auto", // 自动预加载
          volume: 0.7, // 初始化音量
          playbackSpeed: [0.5, 1, 1.5, 2], //可选的播放速度，可自定义
          video: {
            url: '', // 播放视频的路径
            defaultQuality: 0, // 默认是高清
            quality: [
              // 设置多个质量的视频
              {
                name: "高清",
                url: '',
                type: "auto", // 'auto', 'hls', 'flv', 'dash', 'webtorrent', 'normal' 或 其他自定义类型
              }
            ],
          },
        },

      }

    }
  },

  created() {
    this.getList()

  },

  methods: {
    // 获取信息
    getList() {
      getAdminprolist(this.pageNum, this.pageSize, this.listQuery).then(res => {
        // console.log(res.data.records)
        this.problemList = res.data.records
        this.total = res.data.total
      })
    },

    // 重置
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

    // 下载后台测试样例
    downloadSample(id) {
      // downloadSample(id).then(res => {
      //   console.log(res)
      // })
      // window.open(`http://localhost/api/problem/download/sample/${id}`)
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

    // 视频操作
    to_video_upload(problemId) {
      this.video.problemId = problemId
      this.video.dialog.upload = true
    },
    video_upload_ok() {
      this.$refs.importVideo.submit()
    },
    video_upload_close() {
      this.video.problemId = null
      this.video.dialog.upload = false
    },
    video_upload(param) {
      let formData = new FormData()
      formData.append("file", param.file)

      uploadVideo(this.video.problemId, formData).then(res => {
        if (res.status === 200) {
          this.$message.success("上传成功")
          this.video_upload_close()
          this.getList()
        } else {
          this.$message.error(res.message)
        }
      })
    },

    videoDialogVisibleChange(visible) {
      if (visible) {
        const player = this.$refs.player;
        if (!player.dp) { // dp是DPlayer对象，若存在则表示已经初始化过了，不需要再初始化
          player.initPlayer();
        }
      }
    },
    video_preview(problemId) {
      this.video.dialog.preview = true
      this.video.problemId = problemId

      axios({
        url: 'http://localhost:8080/problem/video',
        method: 'GET',
        responseType: 'blob',
        headers: {
          'Authorization': getToken(),
        }
      })
          .then(response => {
            const blobUrl = URL.createObjectURL(response.data)
            this.video.options.video.url = blobUrl
            this.$nextTick(() => { // 在DOM渲染完成之后再调用，确保能够获取到dplayer的DOM元素
              const player = this.$refs.player;
              if (player.dp) {
                player.dp.switchVideo({
                  url: blobUrl,
                });
              }
            })
          })
          .catch(error => {
            console.log(error)
          })
    },

    video_download(problemId) {
      // window.open(`http://localhost/api/problem/admin/get/video/${problemId}`)
    },
    video_delete(problemId) {
      deleteVideo(problemId).then(res => {
        if (res.status === 200) {
          this.$message.success("删除成功")
          this.getList()
        } else {
          this.$message.error(res.message)
        }
      })
    },

  }
}
</script>

<style>
</style>
