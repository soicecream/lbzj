<template>
  <div>
    <el-container>
      <el-container>
        <el-main>
          <d-player ref="player" id="dplayer" :options="options"></d-player>
        </el-main>
      </el-container>
      <el-aside>
        <el-card :body-style="{ height: '520px' }">
          <div slot="header" class="clearfix">
            <span>相关知识点视频</span>
          </div>
          <div style="height:100%;overflow:auto;width: 100%">
          </div>
        </el-card>
      </el-aside>
    </el-container>
  </div>
</template>

<script>
import {getVideo} from "@/api/problem";

export default {
  name: 'problem-video',
  data() {
    return {
      problemId: '',
      options: {
        container: document.getElementById("dplayer"), //播放器容器
        theme: "#b7daff", // 风格颜色，例如播放条，音量条的颜色
        loop: false, // 是否自动循环
        lang: "zh-cn", // 语言，'en', 'zh-cn', 'zh-tw'
        screenshot: true, // 是否允许截图（按钮），点击可以自动将截图下载到本地
        hotkey: true, // 是否支持热键，调节音量，播放，暂停等
        preload: "auto", // 自动预加载
        volume: 0.7, // 初始化音量
        playbackSpeed: [0.5, 0.75, 1, 1.25, 1.5, 2, 3], //可选的播放速度，可自定义
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
      isCollapse: false,
      count: 0
    }
  },
  created() {
    this.video_preview()

  },
  methods: {
    video_preview() {
      this.problemId = this.$route.params.id

      getVideo(this.problemId).then(res => {
        const blobUrl = URL.createObjectURL(res)
        this.options.video.url = blobUrl
        // 在DOM渲染完成之后再调用，确保能够获取到dplayer的DOM元素
        this.$nextTick(() => {
          const player = this.$refs.player
          if (player.dp) {
            player.dp.switchVideo({url: blobUrl})
          }
        })
      })
    },
  }
}
</script>

<style>

</style>
