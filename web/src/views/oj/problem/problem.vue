<template>
  <div id="main">
    <div>
      <el-row class="problem-box">
        <el-col :sm="24" :md="12" :lg="12" class="problem-left">
          <el-tabs v-model="activeName" type="border-card">

            <el-tab-pane name="problemDetail">
              <span slot="label"><i class="el-icon-receiving"/> 题目描述</span>

              <div :padding="0" :id="'js-left' + '-' + $route.name" class="js-left">
                <!--              题目信息-->
                <div>
                  <!--                标题-->
                  <span style="font-size: 26px;">{{ problemData.problem.title }}</span>
                  <br/>

                  <!--                标签-->
                  <span v-if="!problemData.defunct">
                  <el-tag effect="plain" size="small" class="mouse-turns-hands">暂无标签</el-tag>
                </span>
                  <div class="problem-tag">
                    <el-popover v-if="problemData.tags.length > 0" placement="right-start" trigger="hover">
                      <el-tag slot="reference" effect="plain" size="small" class="mouse-turns-hands">显示标签</el-tag>
                      <el-tag v-for="(tag, i) in problemData.tags" :key="i" :color="tag.color ? tag.color : '#409eff'"
                              style="margin-right: 5px; margin-top: 2px;" effect="dark" size="small">
                        {{ tag.name }}
                      </el-tag>
                    </el-popover>
                    <el-tag v-else effect="plain" size="small" class="mouse-turns-hands">显示标签</el-tag>
                  </div>

                  <!--                菜单-->
                  <div class="problem-menu">
                  <span>
                    <el-link @click="problem_statistics" type="primary" :underline="false">
                      <i class="el-icon-pie-chart"/> 题目统计
                    </el-link>
                  </span>
                    <span>
                    <el-link @click="problem_all_submission" type="primary" :underline="false">
                      <i class="el-icon-s-data"/> 全部提交
                    </el-link>
                  </span>
                  </div>

                  <!--                题目说明-->
                  <div class="problem-explain">
                    <!--                  限制-->
                    <div>
                    <span>
                      时间限制: C/C++ {{ problemData.problem.timeLimit }}Ms,
                      其他语言: {{ problemData.problem.timeLimit * 2 }}Ms
                    </span>
                      <br>
                      <span>
                      内存限制: C/C++ {{ problemData.problem.memoryLimit }}Ms,
                      其他语言: {{ problemData.problem.memoryLimit * 2 }}Ms
                    </span>
                      <br>
                    </div>

                    <!--                  难度-->
                    <div v-if="problemData.problem.difficulty !== null">
                      难度
                      <!--                    <el-tag :color="problemData.problem.difficulty">{{ problemData.problem.difficulty }}</el-tag>-->
                      <el-tag>{{ problemData.problem.difficulty }}</el-tag>
                    </div>

                    <!--                  作者-->
                    <div v-if="problemData.problem.author">
                      出题人: {{ problemData.problem.author }}
                    </div>
                  </div>

                </div>

                <!--              题目内容-->
                <div class="problem-content">
                  <!--                描述-->
                  <div>
                    <p class="title">题目描述</p>
                    <p v-html="problemData.problem.description" class="content-title"/>
                  </div>

                  <!--                输入-->
                  <div v-if="problemData.problem.input">
                    <p class="title">输入描述</p>
                    <p v-html="problemData.problem.input" class="content-title"/>
                  </div>

                  <!--                输出-->
                  <div v-if="problemData.problem.output">
                    <p class="title">输出描述</p>
                    <p v-html="problemData.problem.output" class="content-title"/>
                  </div>

                  <!--                样例-->
                  <div v-if="problemData.problem.examples">
                    <div v-for="(example, index) of problemData.problem.examples" :key="index">
                      <div class="example">
                        <div class="example-input">
                          <p class="title">
                            样例输入 {{ index + 1 }}
                            <a class="copy" v-clipboard:copy="example.input"
                               v-clipboard:success="copy_expamle_success" v-clipboard:error="copy_expamle_error">
                              <i class="el-icon-document-copy"/>
                            </a>
                          </p>
                          <pre>{{ example.input }}</pre>
                        </div>
                        <div class="example-output">
                          <p class="title">
                            样例输出 {{ index + 1 }}
                            <a class="copy" v-clipboard:copy="example.output"
                               v-clipboard:success="copy_expamle_success" v-clipboard:error="copy_expamle_error">
                              <i class="el-icon-document-copy"/>
                            </a>
                          </p>
                          <pre> {{ example.output }}</pre>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!--                提示-->
                  <div v-if="problemData.problem.hint">
                    <p class="title"> 提示 </p>
                    <p v-html="problemData.problem.hint" class="content-title"/>
                  </div>

                </div>
              </div>
            </el-tab-pane>

            <el-tab-pane label="我的提交"></el-tab-pane>

            <el-tab-pane label="问题讨论"></el-tab-pane>
          </el-tabs>
        </el-col>

        <!--        调节窗口大小-->
        <div class="problem-resize" title="收缩侧边栏" :id="'js-center'+'-'+ $route.name">
          <span>⋮</span>
          <span>
            <el-tooltip v-if="!toResetWatch" :content="toWatchProblem ? '查看题目内容' : '只看题目内容' " placement="right">
              <el-button icon="el-icon-caret-right" circle class="right-fold fold" size="mini"
                         @click.stop="onlyWatchProblem"/>
            </el-tooltip>
            <el-tooltip v-else content="收起全屏,编写代码" placement="left">
              <el-button icon="el-icon-caret-left" circle class="left-fold fold" size="mini"
                         @click.stop="resetWatch(false)"/>
            </el-tooltip>
          </span>
        </div>

        <el-col :sm="24" :md="12" :lg="12" class="problem-right">

          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <span>代码提交</span>
              <!--                <el-button style="float: right; padding: 3px 0" type="text" icon="el-icon-refresh">重置内容</el-button>-->
              <el-select v-model="language" size="medium" class="filter-item"
                         style="width: 100px;height:30px;float: right">
                <el-option v-for="item in LanguageList" :key="item" :label="item"
                           :value="item"/>
              </el-select>
            </div>
            <div style="width: 100%;">
              <CodeMirror :value.sync="code"
                          :language="language"
                          :theme="theme"
                          @resetCode="onResetToTemplate"
                          @changeTheme="onChangeTheme"
                          @changeLang="onChangeLang">
              </CodeMirror>
            </div>
          </el-card>
          <div class="submit">
            <el-button type="success" icon="el-icon-upload" @click="submit()">提交</el-button>
          </div>
        </el-col>
      </el-row>
    </div>


    <!--    题目统计-->
    <el-dialog :visible.sync="problem_statistics_dialog">
      <span>ECharts</span>
    </el-dialog>

  </div>
</template>

<script>
import CodeMirror from "@/components/oj/CodeMirror";
import {fetchProblem} from "@/api/problem";
import {Languages} from "@/utils/constants";
import {saveSubmission} from "@/api/submission";
import {getWorkid} from "@/utils/auth";

export default {
  name: 'Problem',
  components: {CodeMirror},
  data() {
    return {
      activeName: "problemDetail",
      problemID: '',
      problem: {
        problem_id: '',
        title: '',
        description: '',
        input: '',
        output: '',
        sample_input: '',
        sample_output: '',
        spj: '',
        hint: '',
        source: '',
        in_date: '',
        time_limit: 1000,
        memory_limit: 64,
        defunct: 'N',
        accepted: 0,
        submit: 0,
        solved: 0,
        degree: 0,
        ordernum: 0,
        author: ''
      },
      language: 'c++',
      theme: 'solarized',
      LanguageList: [],
      code: '',
      solutionAndSourceCode: {
        solution_id: null,
        problemId: '',
        userId: 'zwj',
        nick: 'zwj',
        time: null,
        memory: null,
        inDate: null,
        result: 0,
        language: 0,
        ip: null,
        contestId: getWorkid(),
        valid: 1,
        num: 1,
        codeLength: null,
        judgetime: null,
        pass_rate: null,
        lint_error: null,
        judger: 'admin',
        courseId: 0,
        source: this.code
      },
      videoHref: '',

      problem_statistics_dialog: false,

      toResetWatch: false,
      toWatchProblem: false,
      showProblemHorizontalMenu: false,


    }
  },
  created() {
    this.init();
    this.getLang()
  },
  filters: {
    msTos: function (ms) {
      let num = ms / 1000;
      num = num.toFixed(3);
      return num;
    }
  },

  mounted() {
    // 控制窗体大小
    this.dragControllerDiv();
    this.resizeWatchHeight();
  },

  methods: {
    init() {
      this.problemID = this.$route.params.id.toString()
      this.videoHref = '/problem/video/' + this.problemID
      this.solutionAndSourceCode.problemId = this.problemID
      fetchProblem(this.problemID).then(response => {
        this.problem = response.data
      })
    },
    getLang() {
      for (let lang in Languages) {
        this.LanguageList.push(Languages[lang].lang)
      }
    },
    submit() {
      this.solutionAndSourceCode.source = this.code;
      if (this.language == "java") this.solutionAndSourceCode.language = 3;
      else this.solutionAndSourceCode.language = 1;
      this.solutionAndSourceCode.contestId = getWorkid();
      console.log(getWorkid());
      saveSubmission(this.solutionAndSourceCode).then(response => {
        if (response.data) {
          this.$router.push('/state')
        }
      })
    },
    onChangeLang(newLang) {

    },
    onChangeTheme(newTheme) {

    },
    onResetToTemplate() {

    },


    dragControllerDiv() {
      var resize = document.getElementById("js-center" + "-" + this.$route.name);
      var left = document.getElementsByClassName("problem-left");
      var right = document.getElementsByClassName("problem-right");
      var box = document.getElementsByClassName("problem-box");
      const _this = this;
      // 鼠标按下事件
      resize.onmousedown = function (e) {
        //颜色改变提醒
        resize.style.background = "#818181";
        var startX = e.clientX;
        // 鼠标拖动事件
        document.onmousemove = function (e) {
          resize.left = startX;
          var endX = e.clientX;
          var moveLen = resize.left + (endX - startX); // （endx-startx）=移动的距离。resize.left+移动的距离=左边区域最后的宽度
          var maxT = box[0].clientWidth - resize.offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度
          if (moveLen < 420) {
            moveLen = 0; // 左边区域的最小宽度为420px
            _this.toWatchProblem = true;
          } else {
            _this.toWatchProblem = false;
          }
          if (moveLen > maxT - 580) moveLen = maxT - 580; //右边区域最小宽度为580px
          resize.style.left = moveLen; // 设置左侧区域的宽度
          for (let j = 0; j < left.length; j++) {
            left[j].style.width = moveLen + "px";
            let tmp = box[0].clientWidth - moveLen - 11;
            right[j].style.width = tmp + "px";
            if (tmp > 0) {
              _this.toResetWatch = false;
              right[j].style.display = "";
            }
          }
        };
        // 鼠标松开事件
        document.onmouseup = function (evt) {
          //颜色恢复
          resize.style.background = "#d6d6d6";
          document.onmousemove = null;
          document.onmouseup = null;
          resize.releaseCapture && resize.releaseCapture(); //当你不在需要继续获得鼠标消息就要应该调用ReleaseCapture()释放掉
        };
        resize.setCapture && resize.setCapture(); //该函数在属于当前线程的指定窗口里设置鼠标捕获
        return false;
      };
    },
    onlyWatchProblem() {
      if (this.toWatchProblem) {
        this.resetWatch(true);
        this.toWatchProblem = false;
        return;
      }
      var resize = document.getElementsByClassName("problem-resize");
      var left = document.getElementsByClassName("problem-left");
      var right = document.getElementsByClassName("problem-right");
      var box = document.getElementsByClassName("problem-box");
      for (let i = 0; i < resize.length; i++) {
        for (let j = 0; j < left.length; j++) {
          left[j].style.width = box[i].clientWidth - 20 + "px";
          right[j].style.width = "0px";
          right[j].style.display = "none";
        }
      }
      this.toResetWatch = true;
    },
    resetWatch(minLeft = false) {
      var resize = document.getElementsByClassName("problem-resize");
      var left = document.getElementsByClassName("problem-left");
      var right = document.getElementsByClassName("problem-right");
      var box = document.getElementsByClassName("problem-box");
      for (let i = 0; i < resize.length; i++) {
        let leftWidth = 0;
        if (minLeft) {
          leftWidth = 431; // 恢复左边最小420px+滑块11px
        } else {
          leftWidth = box[i].clientWidth - 580; // 右边最小580px
        }
        for (let j = 0; j < left.length; j++) {
          left[j].style.width = leftWidth - 20 + "px";
          right[j].style.width = box[i].clientWidth - leftWidth + "px";
          right[j].style.display = "";
        }
      }
      this.toResetWatch = false;
    },
    resizeWatchHeight() {
      try {
        let headerHeight = document.getElementById("header").offsetHeight;
        let headerWidth = document.getElementById("header").offsetWidth;
        let totalHeight = window.innerHeight;
        let problemLeftHight = totalHeight - (headerHeight + 64);
        if (this.showProblemHorizontalMenu) {
          let footerMenuHeight = document.getElementById("problem-footer").offsetHeight;
          problemLeftHight = problemLeftHight - footerMenuHeight;
        }
        let jsRHeaderHeight =
            document.getElementById("js-right-header").offsetHeight;
        let jsRBottomHeight =
            document.getElementById("js-right-bottom").offsetHeight;
        if (jsRBottomHeight < 48) {
          jsRBottomHeight = 48;
        }
        let problemRightHight = problemLeftHight - 95 - (jsRHeaderHeight - 36) - (jsRBottomHeight - 48);
        if (problemLeftHight < 0) {
          problemLeftHight = 0;
        }
        if (problemRightHight < 0) {
          problemRightHight = 0;
        }
        this.height = problemRightHight;
        if (this.activeName == "problemDetail") {
          if (headerWidth >= 992) {
            document.getElementById("js-left" + "-" + this.$route.name).setAttribute("style", "height:" + problemLeftHight + "px !important");
          } else {
            document.getElementById("js-left" + "-" + this.$route.name).setAttribute("style", "height: auto");
          }
        } else if (this.activeName == "mySubmission") {
          document.getElementById("js-submission").setAttribute("style", "height:" + problemLeftHight + "px !important");
        } else if (this.activeName == "extraFile") {
          document.getElementById("js-extraFile").setAttribute("style", "height:" + problemLeftHight + "px !important");
        }
        document.getElementById("js-center" + "-" + this.$route.name).setAttribute("style", "top:" + problemLeftHight * 0.5 + "px !important");
      } catch (e) {
      }
    },
  }
}
</script>

<style>
.problem-box {
  height: 100%;
  width: 100%;
  overflow: hidden;
}

.problem-left {
  width: calc(50% - 13px); /*左侧初始化宽度*/
  height: 100%;
  overflow: auto;
  float: left;
}

/deep/ .el-tabs__content {
  padding-top: 0px;
  padding-right: 0px;
  padding-bottom: 0px;
}

.js-left {
  padding-right: 15px;
  max-height: 87vh;
  min-height: 87vh;
  overflow-y: auto;
}

.problem-tag {
  display: inline;
}

.problem-menu {
  float: right;
}

a {
  color: #3091f2 !important;
}

.problem-menu span {
  margin-left: 5px;
}

.problem-explain {
  margin-top: 10px;
  margin-bottom: 10px;
  border-radius: 4px;
  border: 1px solid #ddd;
  border-left: 2px solid #3498db;
  background: #fafafa;
  padding: 10px;
  line-height: 1.8;
  font-size: 14px;
}

.problem-content {
  margin-top: -20px;
}

.title {
  font-size: 16px;
  font-weight: 600;
  margin: 25px 0 8px 0;
  color: #3091f2;
}

.content-title {
  margin-left: 25px;
  margin-right: 20px;
  font-size: 15px;
}

.example {
  display: flex;
}

.example-input, .example-output {
  width: 50%;
}

.copy {
  padding-left: 8px;
}

.example pre {
  flex: 1 1 auto;
  padding: 5px 10px;
  margin-top: 10px;
  margin-bottom: 10px;
  overflow: auto;
  background: #f1f1f1;
  border: 1px dashed #e9eaec;
  font-size: 1.1em;
  margin-right: 7%;
}

.problem-resize {
  cursor: col-resize;
  float: left;
  position: relative;
  top: 330px;
  background-color: #d6d6d6;
  border-radius: 5px;
  width: 10px;
  height: 50px;
  background-size: cover;
  background-position: center;
  font-size: 32px;
  color: white;
}

.problem-resize:hover .right-fold {
  display: block;
}

.problem-resize:hover .fold:before {
  content: "";
  position: absolute;
  display: block;
  width: 6px;
  height: 24px;
  left: -6px;
}

.right-fold {
  position: absolute;
  display: none;
  font-weight: bolder;
  margin-left: 15px;
  margin-top: -35px;
  cursor: pointer;
  z-index: 1000;
  text-align: center;
}

.left-fold {
  position: absolute;
  font-weight: bolder;
  margin-left: -40px;
  margin-top: 10px;
  cursor: pointer;
  z-index: 1000;
  text-align: center;
}

.fold:hover {
  color: #409eff;
  background: #fff;
}

</style>
