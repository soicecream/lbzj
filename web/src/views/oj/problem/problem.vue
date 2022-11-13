<template>
  <div class="container">
    <el-row :gutter="20">
      <el-col :span="18">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <h2>{{ problem.title }}</h2>
            </div>
            <div>
              <h4>题目描述</h4>
              <div v-html="problem.description" style="white-space:pre-wrap;"></div>
              <h4>输入</h4>
              <div v-html="problem.input" style="white-space:pre-wrap;"></div>
              <h4>输出</h4>
              <div v-html="problem.output" style="white-space:pre-wrap;"></div>
              <h4>样例输入</h4>
              <div v-html="problem.sampleInput" style="white-space:pre-wrap;"></div>
              <h4>样例输出</h4>
              <div v-html="problem.sampleOutput" style="white-space:pre-wrap;"></div>
              <el-divider></el-divider>
            </div>
            <div style="width: 100%;">
            </div>
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
          </el-card>
        </div>
      </el-col>
      <el-col :span="6">
        <div>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <h3>详细信息</h3>
            </div>
            <div>
              <span>
               时间限制：{{ problem.timeLimit }} sec
              </span>
              <el-divider></el-divider>
              <span>
                内存限制：{{ problem.memoryLimit }} MB
              </span>
              <el-divider></el-divider>
              <span>
                出题人：{{ problem.author }}
              </span>
              <el-divider></el-divider>
              <span>
                来源/分类：{{ problem.source }}
              </span>
            </div>
          </el-card>
          <el-card class="box-card">
            <div slot="header" class="clearfix">
              <h3>视频详解</h3>
            </div>
            <div>
              <a target="_blank" v-bind:href="videoHref">点击此处打开视频</a>
            </div>
          </el-card>
        </div>
      </el-col>
    </el-row>
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
      videoHref: ''
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
  }
}
</script>

<style>
.container {
  width: 95%;
  margin: 0.5rem auto;
}

</style>
