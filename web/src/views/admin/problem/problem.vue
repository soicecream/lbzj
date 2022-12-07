<template>
  <div class="problem">
    <el-card>
      <div slot="header">
        <span class="panel-title home-title">{{ title }}</span>
      </div>
      <el-form ref="form" :model="problem" :rules="rules" label-position="top" label-width="70px">
        <!--        题目展示id-->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item prop="problemId" label="题目展示ID" required>
              <el-input placeholder="题目展示ID" v-model="problem.problemId" :disabled="problem.isRemote"/>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        标题-->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item prop="title" label="标题" required>
              <el-input placeholder="标题" v-model="problem.title"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        比赛显示时标题和id-->
        <el-row :gutter="20" v-if="contestID">
          <el-col :md="12" :xs="24">
            <el-form-item label="比赛显示标题" required>
              <el-input placeholder="比赛显示标题" v-model="contestProblem.displayTitle"/>
            </el-form-item>
          </el-col>

          <el-col :md="12" :xs="24">
            <el-form-item label="比赛显示id" required>
              <el-input placeholder="比赛显示id" v-model="contestProblem.displayId"/>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        描述-->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item prop="description" label="描述" required>
              <Editor :value.sync="problem.description"></Editor>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        时间限制 空间限制 栈限制 难度-->
        <el-row :gutter="20">
          <el-col :md="6" :xs="24">
            <el-form-item label="时间限制(ms)" required>
              <el-input type="Number" placeholder="时间限制" v-model="problem.timeLimit" :disabled="problem.isRemote"/>
            </el-form-item>
          </el-col>
          <el-col :md="6" :xs="24">
            <el-form-item label="空间限制(mb)" required>
              <el-input type="Number" placeholder="空间限制" v-model="problem.memoryLimit"
                        :disabled="problem.isRemote"/>
            </el-form-item>
          </el-col>
          <el-col :md="6" :xs="24">
            <el-form-item label="栈限制(mb)" required>
              <el-input type="Number" placeholder="栈限制" v-model="problem.stackLimit"
                        :disabled="problem.isRemote"/>
            </el-form-item>
          </el-col>
          <el-col :md="6" :xs="24">
            <el-form-item label="难度" required>
              <el-select class="difficulty-select" placeholder="Enter the level of problem"
                         v-model="problem.difficulty">
                <el-option :label="getLevelName(key)" :value="parseInt(key)"
                           v-for="(value, key, index) in PROBLEM_LEVEL" :key="index"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        输入 输出 提示-->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item prop="input_description" label="输入描述" required>
              <Editor :value.sync="problem.input"></Editor>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="output_description" label="输出描述" required>
              <Editor :value.sync="problem.output"></Editor>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item style="margin-top: 20px" label="提示">
              <Editor :value.sync="problem.hint"></Editor>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        难度 标签-->
        <el-row :gutter="20">
          <el-col :md="4" :xs="24">
            <el-form-item label="难度">
              <el-select v-model="problem.auth" size="small">
                <el-option label="公开题目" :value="1"></el-option>
                <el-option label="隐藏题目" :value="2"></el-option>
                <el-option label="比赛题目" :value="3"></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :md="12" :xs="24">
            <el-form-item label="标签">
              <el-tag v-for="tag in problemTags" closable :close-transition="false" :key="tag.name" size="small"
                      @close="closeTag(tag.name)" style="margin-right: 7px;margin-top:4px">
                {{ tag.name }}
              </el-tag>
              <!-- 输入时建议，回车，选择，光标消失触发更新 -->
              <el-autocomplete v-if="inputVisible" size="mini" class="input-new-tag" v-model="tagInput"
                               :trigger-on-focus="true" @keyup.enter.native="addTag" @click="selectTag" @select="addTag"
                               :fetch-suggestions="querySearch"/>
              <el-tooltip effect="dark" content="添加" placement="top" v-else>
                <el-button class="button-new-tag" size="small" @click="inputVisible = true" icon="el-icon-plus"/>
              </el-tooltip>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        语言列表-->
        <el-row>
          <el-col :md="24" :xs="24">
            <el-form-item label="语言列表" :error="error.languages" required>
              <el-checkbox-group v-model="problemLanguages">
                <el-tooltip class="spj-radio" v-for="lang in allLanguage" :key="lang.name" effect="dark"
                            :content="lang.description" placement="top-start">
                  <el-checkbox :label="lang.name"/>
                </el-tooltip>
              </el-checkbox-group>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        题面样例-->
        <div>
          <div class="panel-title home-title">
            题面样例
            <el-popover placement="right" trigger="hover">
              <p>题目样例：请最好不要超过2个题目样例，题面样例不纳入评测数据。</p>
              <i slot="reference" class="el-icon-question"></i>
            </el-popover>
          </div>
          <el-form-item v-for="(example, index) in problem.examples" :key="'example' + index">
            <Accordion :title="'样例' + (index + 1)" :isOpen="example.isOpen ? true : false" :index="index"
                       @changeVisible="changeExampleVisible">
              <el-button type="danger" size="small" icon="el-icon-delete" slot="header" @click="deleteExample(index)">
                删除
              </el-button>
              <el-row :gutter="20">
                <el-col :xs="24" :md="12">
                  <el-form-item label="样例输入" required>
                    <el-input :rows="5" type="textarea" placeholder="样例输入" v-model="example.input"
                              style="white-space: pre-line"/>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :md="12">
                  <el-form-item label="样例输出" required>
                    <el-input :rows="5" type="textarea" placeholder="样例输出" v-model="example.output"/>
                  </el-form-item>
                </el-col>
              </el-row>
            </Accordion>
          </el-form-item>
        </div>
        <!--        添加题面样例数据 按钮-->
        <div class="add-example-btn">
          <el-button class="add-examples" @click="addExample()" icon="el-icon-plus" type="small">添加样例</el-button>
        </div>

        <!--        添加测试样例数据-->
        <el-row :gutter="20" v-if="!problem.isRemote">
          <!--          标题-->
          <div class="panel-title home-title">
            后台测试数据
            <el-popover placement="right" trigger="hover">
              <p>评测数据：判题机对该题目的相关提交进行评测的数据来源。</p>
              <i slot="reference" class="el-icon-question"></i>
            </el-popover>
          </div>

          <!--          手动输入 还是 上传文件-->
          <el-form-item required>
            <el-switch v-model="problem.isUploadCase" active-text="上传文件" inactive-text="输入文件" style="margin: 10px 0"/>
          </el-form-item>

          <!--          文件上传-->
          <div v-show="problem.isUploadCase">
            <el-col :span="24">
              <el-form-item :error="error.testcase">
                <el-upload :action="uploadFileUrl+'?mode='+problem.judgeCaseMode" name="file" :show-file-list="true"
                           :on-success="uploadSucceeded" :on-error="uploadFailed">
                  <el-button size="small" type="primary" icon="el-icon-upload">
                    选择文件
                  </el-button>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <!--              <vxe-table ref="xTable" stripe auto-resize :data="problem.testCaseScore" align="center"-->
              <!--                         :sort-config="{trigger: 'cell', defaultSort: {field: 'groupNum', order: 'asc'}, orders: ['desc', 'asc', null], sortMethod: customSortMethod}">-->
              <!--                <vxe-table-column field="index" title="#" width="60"/>-->
              <!--                <vxe-table-column field="input" title="输入文件名" min-width="100"/>-->
              <!--                <vxe-table-column field="output" title="输出文件名" min-width="100"/>-->
              <!--                <vxe-table-column-->
              <!--                    v-if="problem.judgeCaseMode == JUDGE_CASE_MODE.SUBTASK_LOWEST || problem.judgeCaseMode == JUDGE_CASE_MODE.SUBTASK_AVERAGE"-->
              <!--                    field="groupNum" title="$t('m.Sample_Group_Num')" sortable min-width="100">-->
              <!--                  <template v-slot="{ row }">-->
              <!--                    <el-input size="small" v-model="row.groupNum" @change="sortTestCaseList" type="number"/>-->
              <!--                  </template>-->
              <!--                </vxe-table-column>-->
              <!--                <vxe-table-column field="score" title="$t('m.Score')" v-if="problem.type == 1" min-width="100">-->
              <!--                  <template v-slot="{ row }">-->
              <!--                    <el-input size="small" placeholder="$t('m.Score')" v-model="row.score"-->
              <!--                              :disabled="problem.type != 1" type="number"/>-->
              <!--                  </template>-->
              <!--                </vxe-table-column>-->
              <!--              </vxe-table>-->
            </el-col>
          </div>

          <!--          样例输入-->
          <div v-show="!problem.isUploadCase">
            <el-form-item v-for="(sample, index) in problemSamples" :key="'sample' + index">
              <Accordion :title="'测试样例' + (sample.index)" :isOpen="sample.isOpen ? true : false" :index="index"
                         @changeVisible="changeSampleVisible">
                <el-button type="danger" size="small" icon="el-icon-delete" slot="header" @click="deleteSample(index)">
                  删除
                </el-button>
                <el-row :gutter="20">
                  <el-col :xs="24" :md="12">
                    <el-form-item label="测试样例输入" required>
                      <el-input :rows="5" type="textarea" placeholder="测试样例输入" v-model="sample.input"/>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="12">
                    <el-form-item label="测试样例输出" required>
                      <el-input :rows="5" type="textarea" placeholder="测试样例输出" v-model="sample.output"/>
                    </el-form-item>
                  </el-col>
                </el-row>
              </Accordion>
            </el-form-item>

            <!--            添加测试样例-->
            <div class="add-sample-btn">
              <el-button class="add-samples" @click="addSample()" icon="el-icon-plus" type="small">添加样例</el-button>
            </div>
          </div>
        </el-row>

        <!--        提交-->
        <el-button type="primary" @click.native="submit()" size="small">保存</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>

import Accordion from "@/components/admin/accordion";
import Editor from "@/components/admin/editor";

export default {
  name: "Problem",
  components: {
    Accordion,
    Editor
  },

  data() {
    return {
      title: "",

      rules: {
        title: {required: true, message: "标题不能为空", trigger: "blur",},
      },
      backPath: "",
      loadingCompile: false,
      mode: "", // 该题目是编辑或者创建
      contest: {},
      codeTemplate: {},
      pid: null, // 题目id，如果为创建模式则为null
      contestID: null, // 比赛id
      contestProblem: {
        displayId: null,
        displayTitle: null,
        cid: null,
        pid: null,
      }, // 比赛题目的相关属性
      problem: {
        id: null,
        title: "",
        problemId: "",
        description: "",
        input: "",
        output: "",
        timeLimit: 1000,
        memoryLimit: 256,
        stackLimit: 128,
        difficulty: 0,
        auth: 1,
        codeShare: true,
        examples: [], // 题面上的样例输入输出
        spjLanguage: "",
        spjCode: "",
        spjCompileOk: false,
        uploadTestcaseDir: "",
        testCaseScore: [],
        isRemote: false,
        isUploadCase: true,
        type: 0,
        hint: "",
        source: "",
        cid: null,
        isRemoveEndBlank: false,
        openCaseResult: true,
        judgeMode: "default",
        judgeCaseMode: "default",
        userExtraFile: "",
        judgeExtraFile: "",
      },
      problemTags: [], //指定问题的标签列表
      problemLanguages: [], //指定问题的编程语言列表
      problemSamples: [], // 判题机使用的样例
      problemCodeTemplate: [],
      reProblem: {},
      testCaseUploaded: false,
      allLanguage: [], //所有编程语言
      allSpjLanguage: [], // 所有可以用特殊判题的语言
      allTags: [],
      allTagsTmp: [],
      inputVisible: false,
      tagInput: "",

      spjMode: "",
      disableRuleType: false,
      routeName: "",
      uploadTestcaseDir: "",
      uploadFileUrl: "",
      error: {
        tags: "",
        spj: "",
        languages: "",
        testCase: "",
      },
      PROBLEM_LEVEL: {},
      JUDGE_CASE_MODE: {},
      spjRecord: {
        spjCode: "",
        spjLanguage: "",
      },
      addUserExtraFile: false,
      addJudgeExtraFile: false,
      userExtraFile: null,
      judgeExtraFile: null,
      judgeCaseModeRecord: "default",
      sampleIndex: 1,
    };
  },

  mounted() {
    // this.PROBLEM_LEVEL = Object.assign({}, PROBLEM_LEVEL);
    // this.JUDGE_CASE_MODE = Object.assign({}, JUDGE_CASE_MODE);
    this.routeName = this.$route.name;
    let contestID = this.$route.params.contestId;
    this.uploadFileUrl = "/api/file/upload-testcase-zip";
    if (this.routeName === "admin-edit-problem" || this.routeName === "admin-edit-contest-problem") {
      this.mode = "edit";
    } else {
      this.mode = "add";
    }
    // api.admin_getAllProblemTagList("ALL").then((res) => {
    //   this.allTags = res.data.data;
    //   for (let tag of res.data.data) {
    //     this.allTagsTmp.push({value: tag.name, oj: tag.oj});
    //   }
    // }).catch(() => {
    // });
    // api.getLanguages(this.$route.params.problemId, false).then((res) => {
    //   let allLanguage = res.data.data;
    //   this.allLanguage = allLanguage;
    //   for (let i = 0; i < allLanguage.length; i++) {
    //     if (allLanguage[i].isSpj == true) {
    //       this.allSpjLanguage.push(allLanguage[i]);
    //     }
    //   }
    //   this.problem = this.reProblem = {
    //     id: null,
    //     problemId: "",
    //     title: "",
    //     description: "",
    //     input: "",
    //     output: "",
    //     timeLimit: 1000,
    //     memoryLimit: 256,
    //     stackLimit: 128,
    //     difficulty: 0,
    //     auth: 1,
    //     codeShare: true,
    //     examples: [],
    //     spjLanguage: "",
    //     spjCode: "",
    //     spjCompileOk: false,
    //     isUploadCase: true,
    //     uploadTestcaseDir: "",
    //     testCaseScore: [],
    //     contestProblem: {},
    //     type: 0,
    //     hint: "",
    //     source: "",
    //     cid: null,
    //     isRemoveEndBlank: false,
    //     openCaseResult: true,
    //     judgeMode: "default",
    //     judgeCaseMode: "default",
    //     userExtraFile: null,
    //     judgeExtraFile: null,
    //   };
    //
    //   this.contestID = contestID;
    //   if (contestID) {
    //     this.problem.cid = this.reProblem.cid = contestID;
    //     this.problem.auth = this.reProblem.auth = 3;
    //     this.disableRuleType = true;
    //     api.admin_getContest(contestID).then((res) => {
    //       this.problem.type = this.reProblem.type = res.data.data.type;
    //       this.contest = res.data.data;
    //     });
    //   }
    //   this.problem.spjLanguage = "C";
    //   this.init();
    // });
  },

  watch: {
    $route() {
      this.routeName = this.$route.name;
      if (this.routeName === "admin-edit-problem" || this.routeName === "admin-edit-contest-problem") {
        this.mode = "edit";
      } else {
        this.mode = "add";
      }
      this.$refs.form.resetFields();
      this.problem = this.reProblem;
      this.problemTags = []; //指定问题的标签列表
      this.problemLanguages = []; //指定问题的编程语言列表
      this.problemSamples = [];
      this.problemCodeTemplate = [];
      this.codeTemplate = [];
      this.init();
    },

    problemLanguages(newVal) {
      let data = {};
      // use deep copy to avoid infinite loop
      let languages = JSON.parse(JSON.stringify(newVal)).sort();
      for (let item of languages) {
        if (this.codeTemplate[item] === undefined) {
          let langConfig = this.allLanguage.find((lang) => {
            return lang.name === item;
          });
          let codeTemp;
          let problemCodeTemplate = this.problemCodeTemplate;
          if (problemCodeTemplate) {
            codeTemp = problemCodeTemplate.find((temp) => {
              return temp.lid == langConfig.id;
            });
          }
          if (codeTemp == undefined) {
            data[item] = {
              id: null,
              status: false,
              code: langConfig.codeTemplate,
              mode: langConfig.contentType,
            };
          } else {
            data[item] = {
              id: codeTemp.id,
              status: true,
              code: codeTemp.code,
              mode: langConfig.contentType,
            };
          }
        } else {
          data[item] = this.codeTemplate[item];
        }
      }
      this.codeTemplate = data;
    },

    "problem.spjLanguage"(newVal) {
      if (this.allSpjLanguage.length && this.problem.judgeMode != "default") {
        this.spjMode = this.allSpjLanguage.find((item) => {
          return item.name == this.problem.spjLanguage && item.isSpj == true;
        })["contentType"];
      }
    },
  },

  methods: {
    init() {
      this.sampleIndex = 1;
      if (this.mode === "edit") {
        this.pid = this.$route.params.problemId;
        this.backPath = this.$route.query.back;
        this.title = "修改题目";
        let funcName = {
          "admin-edit-problem": "admin_getProblem",
          "admin-edit-contest-problem": "admin_getContestProblem",
        }[this.routeName];
        // api[funcName](this.pid).then((problemRes) => {
        //   let data = problemRes.data.data;
        //   data.spjCompileOk = false;
        //   data.uploadTestcaseDir = "";
        //   data.testCaseScore = [];
        //   if (!data.spjCode) {
        //     data.spjCode = "";
        //   }
        //   data.spjLanguage = data.spjLanguage || "C";
        //   this.spjRecord.spjLanguage = data.spjLanguage;
        //   this.spjRecord.spjCode = data.spjCode;
        //   this.judgeCaseModeRecord = data.judgeCaseModeRecord;
        //   this.problem = data;
        //   this.problem["examples"] = utils.stringToExamples(data.examples);
        //   if (this.problem["examples"].length > 0) {
        //     this.problem["examples"][0]["isOpen"] = true;
        //   }
        //   this.testCaseUploaded = true;
        //   if (this.problem.userExtraFile) {
        //     this.addUserExtraFile = true;
        //     this.userExtraFile = JSON.parse(this.problem.userExtraFile);
        //   }
        //   if (this.problem.judgeExtraFile) {
        //     this.addJudgeExtraFile = true;
        //     this.judgeExtraFile = JSON.parse(this.problem.judgeExtraFile);
        //   }
        //   api.admin_getProblemCases(this.pid, this.problem.isUploadCase).then((res) => {
        //     if (this.problem.isUploadCase) {
        //       this.problem.testCaseScore = res.data.data;
        //       this.problem.testCaseScore.forEach((item, index) => {
        //         item.index = index + 1;
        //       });
        //       this.$refs.xTable.sort("groupNum", "asc");
        //     } else {
        //       this.problemSamples = res.data.data;
        //       if (this.problemSamples != null && this.problemSamples.length > 0) {
        //         this.problemSamples[0]["isOpen"] = true;
        //         this.problemSamples.forEach((item, index) => {
        //           item.index = index + 1;
        //         });
        //         this.sampleIndex = this.problemSamples.length + 1;
        //       }
        //     }
        //   });
        // });
        if (funcName === "admin_getContestProblem") {
          // api.admin_getContestProblemInfo(this.pid, this.contestID).then((res) => {
          //   this.contestProblem = res.data.data;
          // });
        }
        this.getProblemCodeTemplateAndLanguage();

        // api.admin_getProblemTags(this.pid).then((res) => {
        //   this.problemTags = res.data.data;
        // });
      } else {
        this.addExample();
        this.testCaseUploaded = false;
        this.title = "创建题目";
        for (let item of this.allLanguage) {
          this.problemLanguages.push(item.name);
        }
      }
    },

    async getProblemCodeTemplateAndLanguage() {
      const that = this;
      // await api.getProblemCodeTemplate(that.pid).then((res) => {
      //   that.problemCodeTemplate = res.data.data;
      // });
      // api.getProblemLanguages(that.pid).then((res) => {
      //   let Languages = res.data.data;
      //   for (let i = 0; i < Languages.length; i++) {
      //     that.problemLanguages.push(Languages[i].name);
      //   }
      // });
    },

    //tab搜索
    querySearch(queryString, cb) {
      var ojName = "ME";
      if (this.problem.isRemote) {
        ojName = this.problem.problemId.split("-")[0];
      }
      var restaurants = this.allTagsTmp.filter((item) => item.oj == ojName);
      var results = queryString ? restaurants.filter((item) => item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) : restaurants;
      cb(results);
    },

    // 获取难度名
    getLevelName(difficulty) {
      return utils.getLevelName(difficulty);
    },

    // 选择标签
    selectTag(item) {
      for (var i = 0; i < this.problemTags.length; i++) {
        if (this.problemTags[i].name == item.value) {
          this.$message.warning("添加标签错误")
          // myMessage.warning(this.$i18n.t("m.Add_Tag_Error"));
          this.tagInput = "";
          return;
        }
      }
      this.tagInput = item.value;
    },

    // 添加标签
    addTag() {
      let newTag = {name: this.tagInput,};
      if (this.tagInput) {
        for (var i = 0; i < this.problemTags.length; i++) {
          if (this.problemTags[i].name == this.tagInput) {
            this.$message.warning("添加标签错误")
            // myMessage.warning(this.$i18n.t("m.Add_Tag_Error"));
            this.tagInput = "";
            return;
          }
        }
        this.problemTags.push(newTag);
        this.inputVisible = false;
        this.tagInput = "";
      }
    },

    // 根据tag name从题目的tags列表中移除
    closeTag(tag) {
      this.problemTags.splice(this.problemTags.map((item) => item.name).indexOf(tag), 1);
    },


    // 添加题目样例 和 判题机的测试样例
    addExample() {
      this.problem.examples.push({input: "", output: "", isOpen: true});
    },
    addSample() {
      let len = this.sampleIndex;
      if (this.mode === "edit") {
        this.problemSamples.push({
          input: "",
          output: "",
          score: this.problem.type == 0 ? null : 0,
          groupNum: this.problem.type == 0 ? null : len,
          pid: this.pid,
          isOpen: true,
          index: len
        });
      } else {
        this.problemSamples.push({
          input: "",
          output: "",
          score: this.problem.type == 0 ? null : 0,
          groupNum: this.problem.type == 0 ? null : len,
          pid: this.pid,
          isOpen: true,
          index: len
        });
      }
      this.sampleIndex = len + 1;
      this.sortManualProblemSampleList();
    },

    // 修改当前样例 和 判题机的测试样例 是否显示
    changeExampleVisible(index, isOpen) {
      isOpen = !isOpen;
      this.problem.examples[index]["isOpen"] = isOpen;
    },
    changeSampleVisible(index, isOpen) {
      this.problemSamples[index]["isOpen"] = isOpen;
    },

    //根据下标删除特定的题目样例 和 判题机测试样例
    deleteExample(index) {
      this.problem.examples.splice(index, 1);
    },
    deleteSample(index) {
      this.problemSamples.splice(index, 1);
    },


    // 上传成功 和 失败
    uploadSucceeded(response) {
      if (response.status != 200) {
        this.$message.error(response.msg);
        // myMessage.error(response.msg);
        this.testCaseUploaded = false;
        return;
      }
      this.$message.success("上传成功")
      // myMessage.success(this.$i18n.t("m.Upload_Testcase_Successfully"));
      let fileList = response.data.fileList;
      let averSorce = parseInt(100 / fileList.length);
      let add_1_num = 100 - averSorce * fileList.length;
      for (let i = 0; i < fileList.length; i++) {
        if (averSorce) {
          if (i >= fileList.length - add_1_num) {
            fileList[i].score = averSorce + 1;
          } else {
            fileList[i].score = averSorce;
          }
        }
        if (!fileList[i].output) {
          fileList[i].output = "-";
        }
        fileList[i].pid = this.problem.id;
      }
      this.problem.testCaseScore = fileList;
      this.problem.testCaseScore.forEach((item, index) => {
        item.index = index + 1;
      });
      this.testCaseUploaded = true;
      this.problem.uploadTestcaseDir = response.data.fileListDir;
    },
    uploadFailed() {
      this.$message.error("上传失败")
      // myMessage.error(this.$i18n.t("m.Upload_Testcase_Failed"));
    },


    // 排序
    sortTestCaseList() {
      this.$refs.xTable.clearSort();
      this.$refs.xTable.sort("groupNum", "asc");
    },

    customSortMethod({data, sortList}) {
      const sortItem = sortList[0];
      const {property, order} = sortItem;
      let list = [];
      list = data.sort(function (a, b) {
        var value1 = a.groupNum,
            value2 = b.groupNum;
        if (value1 === value2) {
          return a.index - b.index;
        }
        if (order == "desc") {
          return value2 - value1;
        } else {
          return value1 - value2;
        }
      });
      return list;
    },
    sortManualProblemSampleList() {
      this.problemSamples = this.problemSamples.sort(function (a, b) {
        var value1 = a.groupNum,
            value2 = b.groupNum;
        if (value1 === value2) {
          return a.index - b.index;
        }
        return value1 - value2;
      });
    },
    submit() {
    },
  },

  computed: {},

};
</script>

<style scoped>
/deep/ .el-form-item__label {
  padding: 0 !important;
}

.el-form-item {
  margin-bottom: 10px !important;
}

.difficulty-select {
  width: 120px;
}

.input-new-tag {
  width: 120px;
}

.button-new-tag {
  height: 24px;
  line-height: 22px;
  padding-top: 0;
  padding-bottom: 0;
}

.accordion {
  margin-bottom: 10px;
}

.add-examples {
  width: 100%;
  background-color: #fff;
  border: 1px dashed #2d8cf0;
  outline: none;
  cursor: pointer;
  color: #2d8cf0;
  height: 35px;
  font-size: 14px;
}

.add-examples i {
  margin-right: 10px;
}

.add-examples:hover {
  border: 0px;
  background-color: #2d8cf0 !important;
  color: #fff;
}

.add-example-btn {
  margin-bottom: 10px;
}

.add-samples {
  width: 100%;
  background-color: #fff;
  border: 1px dashed #19be6b;
  outline: none;
  cursor: pointer;
  color: #19be6b;
  height: 35px;
  font-size: 14px;
}

.add-samples i {
  margin-right: 10px;
}

.add-samples:hover {
  border: 0px;
  background-color: #19be6b !important;
  color: #fff;
}

.add-sample-btn {
  margin-bottom: 10px;
}

.dialog-compile-error {
  width: auto;
  max-width: 80%;
  overflow-x: scroll;
}
</style>
