<template>
  <div class="problem">
    <el-card>
      <div slot="header">
        <span class="panel-title home-title">{{ (mode === 'add' ? '创建题目' : '修改题目') }}</span>
      </div>
      <el-form ref="form" :model="problem" :rules="rules" label-position="top" label-width="70px">
        <!--        标题-->
        <el-row :gutter="20">
          <el-col :span="24">
            <el-form-item prop="title" label="标题" required>
              <el-input placeholder="标题" v-model="problem.title"/>
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
            <el-form-item prop="timeLimit" label="时间限制(ms)" required>
              <el-input type="Number" placeholder="时间限制" v-model="problem.timeLimit"/>
            </el-form-item>
          </el-col>
          <el-col :md="6" :xs="24">
            <el-form-item prop="memoryLimit" label="空间限制(mb)" required>
              <el-input type="Number" placeholder="空间限制" v-model="problem.memoryLimit"/>
            </el-form-item>
          </el-col>
          <el-col :md="6" :xs="24">
            <el-form-item label="难度" required>
              <el-select class="difficulty-select" placeholder="问题难度" v-model="problem.difficulty">
                <el-option label="简单" :value="1"></el-option>
                <el-option label="中等" :value="2"></el-option>
                <el-option label="困难" :value="3"></el-option>
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

        <!--        状态 标签-->
        <el-row :gutter="20">
          <!--          状态-->
          <el-col :md="4" :xs="24">
            <el-form-item label="状态">
              <el-switch v-model="problem.defunct" active-text="公开题目" inactive-text="隐藏题目"/>
            </el-form-item>
          </el-col>

          <!--          标签-->
          <el-col :md="12" :xs="24">
            <el-form-item label="标签">
              <el-tag v-for="tag in problemTags" closable :close-transition="false" :key="tag.name" size="small"
                      @close="closeTag(tag.name)" style="margin-right: 7px;margin-top:4px">
                {{ tag.name }}
              </el-tag>
              <!-- 输入时建议，回车，选择，光标消失触发更新 -->
              <el-autocomplete v-if="inputVisible" size="mini" class="input-new-tag" v-model="tagInput"
                               @keyup.enter.native="addTag" @click="selectTag" @select="addTag"
                               :trigger-on-focus="true" :fetch-suggestions="querySearch"/>
              <el-tooltip v-else effect="dark" content="添加" placement="top">
                <el-button class="button-new-tag" size="small" @click="inputVisible = true" icon="el-icon-plus"/>
              </el-tooltip>
            </el-form-item>
          </el-col>
        </el-row>

        <!--        语言列表-->
        <!--        <el-row>-->
        <!--          <el-col :md="24" :xs="24">-->
        <!--            <el-form-item label="语言列表" :error="error.languages" required>-->
        <!--              <el-checkbox-group v-model="problemLanguages">-->
        <!--                <el-tooltip class="spj-radio" v-for="lang in allLanguage" :key="lang.name" effect="dark"-->
        <!--                            :content="lang.description" placement="top-start">-->
        <!--                  <el-checkbox :label="lang.name"/>-->
        <!--                </el-tooltip>-->
        <!--              </el-checkbox-group>-->
        <!--            </el-form-item>-->
        <!--          </el-col>-->
        <!--        </el-row>-->

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
            <Accordion :title="'样例' + (index + 1)" :isOpen="example.isOpen" :index="index"
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
        <el-row :gutter="20">
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
            <el-switch v-model="isUploadCase" active-text="上传文件" inactive-text="输入文件" style="margin: 10px 0"/>
          </el-form-item>

          <!--          文件上传-->
          <div v-show="isUploadCase">
            <el-col :span="24">
              <el-form-item :error="error.testcase">
                <el-upload :action="uploadFileUrl+'?mode='+problem.judgeCaseMode" name="file" :show-file-list="true"
                           :on-success="uploadSucceeded" :on-error="uploadFailed">
                  <el-button size="small" type="primary" icon="el-icon-upload">选择文件</el-button>
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
          <div v-show="!isUploadCase">
            <el-form-item v-for="(sample, index) in problemSamples" :key="'sample' + index">
              <Accordion :title="'测试样例' + (sample.index)" :isOpen="sample.isOpen" :index="index"
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
        <el-button type="primary" @click="submit()" size="small">保存</el-button>
        <el-button type="primary" @click.native="clearInput()" size="small">重置表单</el-button>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import Accordion from "@/components/admin/accordion";
import Editor from "@/components/admin/editor";


import utils from "@/utils/utils";
import {fetchProblem, insertOrUpdate} from "@/api/problem";


export default {
  name: "Problem",
  components: {
    Accordion,
    Editor
  },

  data() {
    return {
      pid: null, // 题目id，如果为创建模式则为null
      problem: {
        problemId: "",
        title: "", // 标题
        description: "", // 描述
        timeLimit: "1000", // 时间限制
        memoryLimit: "256", // 空间限制
        difficulty: "", // 难度
        input: "", // 输入描述
        output: "", // 输出描述
        hint: "", // 提示
        examples: [], // 样例
        defunct: 0, // 题目状态
      }, // 题目信息
      problemTags: [], // 题目标签
      tagInput: "",
      allTagsTmp: [],

      isUploadCase: true, // 是否文件上传

      PROBLEM_LEVEL: {}, // 题目难度
      inputVisible: false, //
      error: {},
      uploadFileUrl: "", // 上传文件的路径

      problemSamples: [], // 判题机样例
      sampleIndex: 1, // 判题机样例长度

      rules: {
        title: {required: true, message: "标题不能为空", trigger: "blur",},
        timeLimit: {required: true, message: "时间限制不能为空", trigger: "blur",},
        memoryLimit: {required: true, message: "空间限制不能为空", trigger: "blur",},
      },

      mode: "", // 该题目是编辑或者创建

      testCaseUploaded: false,


    }
  },

  mounted() {
    this.routeName = this.$route.name;
    this.mode = "add";
    if (this.routeName === "admin-edit-problem") {
      this.mode = "edit";
      this.pid = this.$route.params.problemId
      this.init_problem_information()
    }

  },

  watch: {},

  methods: {
    // 加载题目信息
    init_problem_information() {
      if (this.pid) {
        fetchProblem(this.pid).then(res => {
          const data = res.data
          if (data.examples) {
            data.examples = utils.stringToExamples(data.examples)
            for (var i = 0; i < data.examples.length; i++) {
              data.examples[i].isOpen = true
            }
          }
          if (!data.hint) {
            data.hint = ""
          }
          this.problem = data
        })
      }
    },

    // 上传返回
    uploadSucceeded(res) {
      if (res.status !== 200) {
        this.$message.error(res.msg)
        this.testCaseUploaded = false;
        return false
      }
      this.$message.success("上传成功")
    },
    uploadFailed() {
      this.$message.error("上传失败")
    },

    // 标签
    querySearch(queryString, cb) {
      var ojName = "ME";
      if (this.problem.isRemote) {
        ojName = this.problem.problemId.split("-")[0];
      }
      var restaurants = this.allTagsTmp.filter((item) => item.oj == ojName);
      var results = queryString ? restaurants.filter((item) => item.value.toLowerCase().indexOf(queryString.toLowerCase()) === 0) : restaurants;
      cb(results);
    },
    selectTag(item) {
      for (var i = 0; i < this.problemTags.length; i++) {
        if (this.problemTags[i].name == item.value) {
          this.$message.warning("添加标签失败")
          this.tagInput = "";
          return;
        }
      }
      this.tagInput = item.value;
    },
    addTag(item) {
      let newTag = {name: this.tagInput,};
      if (this.tagInput) {
        for (var i = 0; i < this.problemTags.length; i++) {
          if (this.problemTags[i].name == this.tagInput) {
            this.$message.warning("添加标签失败")
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
      this.problemSamples.push({input: "", output: "", isOpen: true,});
      this.sampleIndex = this.sampleIndex + 1;
    },
    // 修改当前样例 和 判题机的测试样例 是否显示
    changeExampleVisible(index, isOpen) {
      this.problem.examples[index]["isOpen"] = !isOpen;
    },
    changeSampleVisible(index, isOpen) {
      this.problemSamples[index]["isOpen"] = !isOpen;
    },
    //根据下标删除特定的题目样例 和 判题机测试样例
    deleteExample(index) {
      this.problem.examples.splice(index, 1);
    },
    deleteSample(index) {
      this.problemSamples.splice(index, 1);
    },

    // 提交或者保存修改
    submit() {
      console.log(this.problem.title, this.problem.description, this.problem.input, this.problem.output, this.problem.timeLimit, this.problem.memoryLimit)
      if (!(this.checkInput(this.problem.title, "标题不能为空") &&
          this.checkInput(this.problem.description, "题目描述不能为空") &&
          this.checkInput(this.problem.input, "输入描述不能为空") &&
          this.checkInput(this.problem.output, "输出描述不能为空") &&
          this.checkInput(this.problem.timeLimit, "时间限制不能为空") &&
          this.checkInput(this.problem.memoryLimit, "空间限制不能为空") &&
          this.checkInput(this.problem.description, "题目难度不能为空")
      )) {
        return false
      } else {
        let res = this.problem.examples
        this.problem.examples = utils.examplesToString(res)
        insertOrUpdate(this.problem).then(res => {
          if (res.status === '200') {
            this.$message.success("ok")

            this.clearInput()
            this.$router.push('/admin/problem/list')
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },
    // 判断输入
    checkInput(s, message) {
      if (!s) {
        this.$message.error(message)
        return false;
      }
      return true;
    },

    // 清空输入
    clearInput() {
      this.$refs.form.resetFields()
      this.problem.title = ""
      this.problem.description = ""
      this.problem.input = ""
      this.problem.output = ""
      this.problem.examples = []
      this.problem.timeLimit = "1000"
      this.problem.memoryLimit = "256"
      this.problem.difficulty = ""
      this.problem.hint = ""
      this.problem.defunct = 0
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
  width: 200px;
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
