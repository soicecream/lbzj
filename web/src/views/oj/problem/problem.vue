<template>
  <div class="problem-body">
    <div id="problem-main">
      <el-row class="problem-box" id="problem-box">

        <!--        题目信息-->
        <el-col :sm="24" :md="12" :lg="12" class="problem-left" id="problem-left">
          <el-tabs v-model="activeName" type="border-card">

            <el-tab-pane name="problemDetail">
              <span slot="label"><i class="el-icon-receiving"/> 题目描述</span>

              <div :padding="0" id="js-left" class="js-left">
                <!--              题目信息-->
                <div slot="header">
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
                    <div v-if="problemData.problem.degree !== null">
                      难度:
                      <!--                    <el-tag :color="problemData.problem.difficulty">{{ problemData.problem.difficulty }}</el-tag>-->
                      <el-tag>{{ problemData.problem.degree }}</el-tag>
                    </div>

                    <!--                  作者-->
                    <div v-if="problemData.problem.author">
                      出题人:
                      <span class="author-name">{{ problemData.problem.author }}</span>
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
                  <div v-if="problemData.problem.samples">
                    <div v-for="(sample, index) of problemData.problem.samples" :key="index">
                      <div class="flex-container example">
                        <div class="example-input">
                          <p class="title">
                            样例输入 {{ index + 1 }}
                            <a class="copy" v-clipboard:copy="sample.input" v-clipboard:success="copy_samples_success"
                               v-clipboard:error="copy_samples_error">
                              <i class="el-icon-document-copy"/>
                            </a>
                          </p>
                          <pre>{{ sample.input }}</pre>
                        </div>
                        <div class="example-output">
                          <p class="title">
                            样例输出 {{ index + 1 }}
                            <a class="copy" v-clipboard:copy="sample.output" v-clipboard:success="copy_samples_success"
                               v-clipboard:error="copy_samples_error">
                              <i class="el-icon-document-copy"/>
                            </a>
                          </p>
                          <pre> {{ sample.output }}</pre>
                        </div>
                      </div>
                    </div>
                  </div>

                  <!--                提示-->
                  <div v-if="problemData.problem.hint">
                    <p class="title"> 提示 </p>
                    <el-card>
                      <p v-html="problemData.problem.hint" class="content-title"/>
                    </el-card>
                  </div>

                </div>
              </div>
            </el-tab-pane>

            <el-tab-pane label="我的提交">
              <div id="js-submission">

              </div>
            </el-tab-pane>

            <el-tab-pane label="问题讨论"></el-tab-pane>

          </el-tabs>
        </el-col>

        <!--        调节窗口大小-->
        <div class="problem-resize" title="收缩侧边栏" id="js-center">
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

        <!--        代码-->
        <el-col :sm="24" :md="12" :lg="12" class="problem-right" id="problem-right">
          <el-card :padding="10" id="submit-code" shadow="always" class="submit-detail">
            <CodeMirror :value.sync="code" :languages="problemData.languages" :language.sync="language"
                        :theme.sync="theme" :height.sync="height" :fontSize.sync="fontSize" :tabSize.sync="tabSize"
                        @resetCode="onResetToTemplate" @changeTheme="onChangeTheme" @changeLang="onChangeLang"
                        @getUserLastAccepetedCode="getUserLastAccepetedCode" @switchFocusMode="switchFocusMode"
                        :openFocusMode.sync="openFocusMode" :openTestCaseDrawer.sync="openTestCaseDrawer"
                        :problemTestCase="problemData.problem.examples" :pid="problemData.problem.id"
                        :type="problemType" :isAuthenticated="isAuthenticated"
                        :isRemoteJudge="problemData.problem.isRemote"/>
            <div id="js-right-bottom">
              <el-row>
                <el-col :sm="24" :md="10" :lg="10" style="margin-top:4px;">

                </el-col>

                <el-col :sm="24" :md="14" :lg="14" style="margin-top:4px;">
                  <el-button type="primary" icon="el-icon-edit-outline" size="small" :loading="submitting"
                             @click.native="submitCode" :disabled="submitted" class="fl-right">
                    <span v-if="submitting">提交中</span>
                    <span v-else>提交测评</span>
                  </el-button>
                  <!--                  <el-tag type="success" :class="openTestCaseDrawer?'tj-btn active':'tj-btn non-active'"-->
                  <!--                          @click.native="openTestJudgeDrawer" effect="plain">-->
                  <!--                    <svg t="1653665263421" class="icon" viewBox="0 0 1024 1024" version="1.1"-->
                  <!--                         xmlns="http://www.w3.org/2000/svg" p-id="1656" width="12" height="12"-->
                  <!--                         style="vertical-align: middle;">-->
                  <!--                      <path-->
                  <!--                          d="M1022.06544 583.40119c0 11.0558-4.034896 20.61962-12.111852 28.696576-8.077979 8.077979-17.639752 12.117992-28.690436 12.117992L838.446445 624.215758c0 72.690556-14.235213 134.320195-42.718941 184.89915l132.615367 133.26312c8.076956 8.065699 12.117992 17.634636 12.117992 28.690436 0 11.050684-4.034896 20.614503-12.117992 28.691459-7.653307 8.065699-17.209964 12.106736-28.690436 12.106736-11.475356 0-21.040199-4.041036-28.690436-12.106736L744.717737 874.15318c-2.124384 2.118244-5.308913 4.88424-9.558703 8.283664-4.259 3.3984-13.180184 9.463536-26.78504 18.171871-13.598716 8.715499-27.415396 16.473183-41.439808 23.276123-14.029528 6.797823-31.462572 12.966313-52.289923 18.49319-20.827351 5.517667-41.446971 8.28571-61.842487 8.28571L552.801776 379.38668l-81.611739 0 0 571.277058c-21.668509 0-43.250036-2.874467-64.707744-8.615215-21.473057-5.734608-39.960107-12.749372-55.476499-21.039175-15.518438-8.289804-29.541827-16.572444-42.077328-24.867364-12.541641-8.290827-21.781072-15.193027-27.739784-20.714787l-9.558703-8.93244L154.95056 998.479767c-8.500605 8.921183-18.699897 13.386892-30.606065 13.386892-10.201339 0-19.335371-3.40454-27.409257-10.202363-8.079002-7.652284-12.437264-17.10968-13.080923-28.372188-0.633427-11.263531 2.659573-21.143553 9.893324-29.647227l128.787178-144.727219c-24.650423-48.464805-36.980239-106.699114-36.980239-174.710091L42.738895 624.207571c-11.057847 0-20.61655-4.041036-28.690436-12.111852-8.079002-8.082072-12.120039-17.640776-12.120039-28.696576 0-11.050684 4.041036-20.61962 12.120039-28.689413 8.073886-8.072863 17.632589-12.107759 28.690436-12.107759l142.81466 0L185.553555 355.156836l-110.302175-110.302175c-8.074909-8.077979-12.113899-17.640776-12.113899-28.691459 0-11.04966 4.044106-20.61962 12.113899-28.690436 8.071839-8.076956 17.638729-12.123109 28.691459-12.123109 11.056823 0 20.612457 4.052293 28.692482 12.123109l110.302175 110.302175 538.128077 0 110.303198-110.302175c8.070816-8.076956 17.632589-12.123109 28.690436-12.123109 11.050684 0 20.617573 4.052293 28.689413 12.123109 8.077979 8.070816 12.119015 17.640776 12.119015 28.690436 0 11.050684-4.041036 20.614503-12.119015 28.691459l-110.302175 110.302175 0 187.448206 142.815683 0c11.0558 0 20.618597 4.034896 28.690436 12.113899 8.076956 8.069793 12.117992 17.638729 12.117992 28.683273l0 0L1022.06544 583.40119 1022.06544 583.40119zM716.021162 216.158085 307.968605 216.158085c0-56.526411 19.871583-104.667851 59.616796-144.414087 39.733956-39.746236 87.88256-59.611679 144.411017-59.611679 56.529481 0 104.678084 19.865443 144.413064 59.611679C696.156742 111.48921 716.021162 159.631674 716.021162 216.158085L716.021162 216.158085 716.021162 216.158085 716.021162 216.158085z"-->
                  <!--                          p-id="1657" :fill="openTestCaseDrawer?'#ffffff':'#67c23a'"></path>-->
                  <!--                    </svg>-->
                  <!--                    <span style="vertical-align: middle;">在线测试</span>-->
                  <!--                  </el-tag>-->
                </el-col>
              </el-row>
            </div>
          </el-card>
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
import utils from "@/utils/utils";

import CodeMirror from "@/components/oj/CodeMirror";
import {fetchProblem} from "@/api/problem";
import {saveSubmission} from "@/api/submission";
import {getWorkid} from "@/utils/auth";

export default {
  name: "problem",

  components: {
    CodeMirror,
  },

  data() {
    return {
      activeName: "problemDetail",

      problemID: "",
      problemData: {
        problem: {
          title: "a + b",
          timeLimit: 1,
          memoryLimit: 300,
          difficulty: 0,
          author: "admin",
          description: "给出两个整数，请你输出他们的和。",
          input: "输两个整数 A, B。",
          output: "输出一个整数表示 A+B。",
          samples: [
            {input: "1 2 564654656546546546546546545646546546546546546", output: "3"},
            {input: "3 4", output: "7"},
          ],
          hint: "a + b = ???<br>" +
              "我大抵是倦了，横竖瘦不下来，起身盛一碗饭，这忧伤没有由来的，黯黯然看着那两只空碗，一只是我吃的，另一只也是我吃的。我向来是不屑于多吃一碗饭的，而如今却也生出了贪婪的念头，也罢，大概是这饭太香了，减肥索性就不提了<br>" +
              "我大抵是倦了，横竖瘦不下来，起身盛一碗饭，这忧伤没有由来的，黯黯然看着那两只空碗，一只是我吃的，另一只也是我吃的。我向来是不屑于多吃一碗饭的，而如今却也生出了贪婪的念头，也罢，大概是这饭太香了，减肥索性就不提了<br>" +
              "我大抵是倦了，横竖瘦不下来，起身盛一碗饭，这忧伤没有由来的，黯黯然看着那两只空碗，一只是我吃的，另一只也是我吃的。我向来是不屑于多吃一碗饭的，而如今却也生出了贪婪的念头，也罢，大概是这饭太香了，减肥索性就不提了<br>" +
              "我大抵是倦了，横竖瘦不下来，起身盛一碗饭，这忧伤没有由来的，黯黯然看着那两只空碗，一只是我吃的，另一只也是我吃的。我向来是不屑于多吃一碗饭的，而如今却也生出了贪婪的念头，也罢，大概是这饭太香了，减肥索性就不提了<br>",

        },
        defunct: true, // 题目是否可用

        problemCount: {},
        tags: [
          {color: "", name: "基础",},
          {color: "", name: "easy",},
        ],
        languages: ["c++", "java", "python2", "python3"],
        codeTemplate: {},
        isRemote: true,
      },
      trainingID: null,


      problem_statistics_dialog: false,

      toResetWatch: false,
      toWatchProblem: false,
      showProblemHorizontalMenu: false,


      language: "c++",
      code: "#include <iostream>\n" +
          "\n" +
          "using namespace std;\n" +
          "\n" +
          "int main(){\n" +
          "    cout << \"The big bug flies away, The big bat cries.\";\n" +
          "    return 0;\n" +
          "}",


      theme: "solarized",
      fontSize: "14px",
      tabSize: 4,

      problemType: "",
      openFocusMode: false,
      openTestCaseDrawer: false,
      isAuthenticated: false,

      height: 500,


      submitting: false,
      submitted: false,

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
    }
  },

  mounted() {
    // 控制窗体大小
    this.dragControllerDiv();
    this.resizeWatchHeight();
    window.onresize = () => {
      this.resizeWatchHeight();
    };

  },

  created() {
    this.init();

  },

  methods: {

    init() {
      fetchProblem(this.$route.params.id).then(res => {
        res.data.samples = utils.stringToExamples(res.data.samples)
        this.problemData.problem = res.data
      })
    },

    // 题目统计
    problem_statistics() {
      this.problem_statistics_dialog = true
    },

    // 题目全部提交
    problem_all_submission() {

    },

    //复制样例
    copy_samples_success(e) {
      this.$message.success("复制成功")
    },
    copy_samples_error(e) {
      this.$message.error("复制失败")
    },


    dragControllerDiv() {
      var resize = document.getElementById("js-center");
      var left = document.getElementById("problem-left");
      var right = document.getElementById("problem-right");
      var box = document.getElementById("problem-box");
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
          var maxT = box.offsetWidth - resize.offsetWidth; // 容器宽度 - 左边区域的宽度 = 右边区域的宽度
          if (moveLen < 420) {
            moveLen = 0; // 左边区域的最小宽度为420px
            _this.toWatchProblem = true;
          } else {
            _this.toWatchProblem = false;
          }
          if (moveLen > maxT - 580) moveLen = maxT - 580; //右边区域最小宽度为580px
          let leftRadio = (moveLen / box.offsetWidth) * 100;
          resize.style.left = leftRadio + "%";
          left.style.width = leftRadio + "%"; // 设置左侧区域的宽度
          right.style.width = (100 - leftRadio) + "%";
          if (leftRadio < 100) {
            _this.toResetWatch = false;
            right.style.display = "";
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
      var resize = document.getElementById("js-center");
      var left = document.getElementById("problem-left");
      var right = document.getElementById("problem-right");
      var box = document.getElementById("problem-box");
      resize.style.left = box.clientWidth - 10 + "px";
      left.style.width = box.clientWidth - 10 + "px";
      right.style.width = "0px";
      right.style.display = "none";
      this.toResetWatch = true;
    },
    resetWatch(minLeft = false) {
      var resize = document.getElementById("js-center");
      var left = document.getElementById("problem-left");
      var right = document.getElementById("problem-right");
      var box = document.getElementById("problem-box");
      let leftWidth = 0;
      if (minLeft) {
        leftWidth = 431; // 恢复左边最小420px+滑块11px
      } else {
        leftWidth = box.clientWidth - 580; // 右边最小580px
      }
      let leftRadio = (leftWidth / box.offsetWidth) * 100;
      resize.style.left = leftRadio + "%";
      left.style.width = leftRadio + "%";
      right.style.width = (100 - leftRadio) + "%";
      right.style.display = "";
      this.toResetWatch = false;
    },
    resizeWatchHeight() {
      try {
        let headerBox = this.$parent.$parent.$parent.$refs.navBar.$el
        let headerHeight = headerBox.offsetHeight
        let headerWidth = headerBox.offsetWidth
        let totalHeight = window.innerHeight;
        let left = document.getElementById("problem-left");
        let right = document.getElementById("problem-right");
        if (headerWidth >= 992) {
          left.style.width = right.style.width = "50%";
        } else {
          left.style.width = right.style.width = "100%";
        }
        let problemLeftHight = totalHeight - (headerHeight + 64);
        if (this.showProblemHorizontalMenu) {
          let footerMenuHeight = document.getElementById("problem-footer").offsetHeight;
          problemLeftHight = problemLeftHight - footerMenuHeight;
        }
        let jsRHeaderHeight = document.getElementById("js-right-header").offsetHeight;
        let jsRBottomHeight = Math.max(48, document.getElementById("js-right-bottom").offsetHeight);
        let problemRightHight = Math.max(0, problemLeftHight - 95 - (jsRHeaderHeight - 36) - (jsRBottomHeight - 48));
        this.height = problemRightHight;
        problemLeftHight = Math.max(0, problemLeftHight);
        if (this.activeName == "problemDetail") {
          if (headerWidth >= 992) {
            document.getElementById("js-left").setAttribute("style", "height:" + problemLeftHight + "px !important");
          } else {
            document.getElementById("js-left").setAttribute("style", "height: auto");
          }
        } else if (this.activeName == "mySubmission") {
          document.getElementById("js-submission").setAttribute("style", "height:" + problemLeftHight + "px !important");
        } else if (this.activeName == "extraFile") {
          document.getElementById("js-extraFile").setAttribute("style", "height:" + problemLeftHight + "px !important");
        }
        let problem_resize = document.getElementById("js-center")
        if (headerWidth >= 992) {
          problem_resize.setAttribute("style", "top:" + problemLeftHight * 0.5 + "px !important; left:" + left.style.width + "; display: inline;");
        } else {
          problem_resize.setAttribute("style", "display: none;");
        }
      } catch (e) {
      }
    },


    onResetToTemplate() {

    },
    onChangeTheme() {

    },
    onChangeLang() {

    },

    // 提交代码
    submitCode() {
      if (this.code.trim() === "") {
        this.$message.error("代码不能为空")
        return;
      }
      if (this.code.length > 65535) {
        this.$message.error("代码长度不能超过65535")
        return;
      }

      this.solutionAndSourceCode.problemId = this.$route.params.id
      this.solutionAndSourceCode.source = this.code;
      if (this.language == "java") this.solutionAndSourceCode.language = 3;
      else this.solutionAndSourceCode.language = 1;
      this.solutionAndSourceCode.contestId = getWorkid();
      // console.log(getWorkid());
      saveSubmission(this.solutionAndSourceCode).then(response => {
        if (response.data) {
          this.$router.push('/state')
        }
      })

      // this.submissionId = "";
      // this.result = {status: 9};
      // this.submitting = true;
      // let data = {
      //   pid: this.problemID, // 如果是比赛题目就为display_id
      //   language: this.language,
      //   code: this.code,
      //   cid: this.contestID,
      //   tid: this.trainingID,
      //   gid: this.groupID,
      //   isRemote: this.isRemote,
      // };
      // if (this.captchaRequired) {
      //   data.captcha = this.captchaCode;
      // }

    },

    openTestJudgeDrawer() {
      this.openTestCaseDrawer = !this.openTestCaseDrawer;
    },

    getUserLastAccepetedCode() {

    },

    switchFocusMode() {

    },


  },

  watch: {
    activeName() {
      this.resizeWatchHeight();
    },
  },

}
</script>

<style scoped>
a {
  color: #3091f2 !important;
}

/deep/ .el-tabs--border-card > .el-tabs__content {
  padding-top: 0px;
  padding-right: 0px;
  padding-bottom: 0px;
}

/deep/ .el-card__body, .el-main {
  padding: 0px 20px;
}

#problem-main {
  height: 100%;
  width: 100%;
}

.js-left {
  padding-right: 15px;
}

.js-left .author-name {
  font-size: 14px !important;
  color: #909399 !important;
}

.problem-explain {
  margin-top: 30px;
  border-radius: 4px;
  border: 1px solid #ddd;
  border-left: 2px solid #3498db;
  background: #fafafa;
  padding: 10px;
  line-height: 1.8;
  margin-bottom: 10px;
  font-size: 14px;
}

.problem-content {
  margin-top: -20px;
}

.problem-content .title {
  font-size: 16px;
  font-weight: 600;
  margin: 25px 0 8px 0;
  color: #3091f2;
}

.problem-content .copy {
  padding-left: 8px;
}

.content-title {
  margin-left: 25px;
  margin-right: 20px;
  font-size: 15px;
}

.flex-container {
  display: flex;
  width: 100%;
  max-width: 100%;
  justify-content: space-around;
  align-items: flex-start;
  flex-flow: row nowrap;
}

.example {
  align-items: stretch;
}

.example-input, .example-output {
  width: 50%;
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
  min-height: 20px;
}

.submit-detail {
  height: 100%;
}

.submit-detail /deep/ .el-card__body {
  padding: 21px 20px 4px 20px;
}

@media screen and (min-width: 992px) {
  .problem-body {
    margin-left: -2%;
    margin-right: -2%;
  }

  .js-left {
    height: 730px !important;
    overflow-y: auto;
  }

  #js-submission {
    overflow-y: auto;
  }

  .submit-detail {
    overflow-y: auto;
  }

  .js-right {
    height: 635px !important;
  }

  #js-right-bottom {
    height: 49px;
  }

  .problem-tag {
    display: inline;
  }

  .problem-menu {
    float: right;
  }

  .problem-menu span {
    margin-left: 10px;
  }

  .problem-explain {
    margin-top: 6px;
  }
}

@media screen and (min-width: 992px) {
  .problem-box {
    height: 100%;
    width: 100%;
    overflow: hidden;
  }

  .problem-left {
    width: 50%;
    height: 100%;
    overflow-y: auto;
    overflow-x: hidden;
    float: left;
  }

  .problem-right {
    height: 100%;
    width: 50%;
    float: left;
  }

  .problem-resize {
    cursor: col-resize;
    position: absolute;
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

  .problem-resize:hover {
    color: #444444;
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

}

@media screen and (max-width: 992px) {
  .submit-detail {
    padding-top: 20px;
  }

  .submit-detail {
    height: 100%;
  }
}

#submit-code {
  height: auto;
}

.tj-btn {
  margin-right: 15px;
  float: right;
  cursor: pointer;
}

.tj-btn.non-active {
  border: 1px solid #32ca99;
}

.tj-btn.non-active:hover {
  background-color: #d5f1eb;
}

.tj-btn.active {
  background-color: #67c23a;
  border-color: #67c23a;
  color: #fff;
}


</style>