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

        <!--        时间限制 空间限制 难度-->
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
              <el-select class="difficulty-select" placeholder="问题难度" v-model="problem.degree">
                <el-option v-for="(dif, index) in problemDifficulty" :key="dif" :label="dif" :value="index"></el-option>
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
              <!--              显示已添加的标签-->
              <el-tag v-for="tag in problemTags" closable :close-transition="false" :key="tag.value" size="small"
                      @close="closeTag(tag.value)" style="margin-right: 7px;margin-top:4px">
                {{ tag.value }}
              </el-tag>

              <!-- 输入时建议，回车，选择，光标消失触发更新 -->
              <el-autocomplete v-if="inputVisible" size="mini" class="input-new-tag" v-model="tagInput"
                               @keyup.enter.native="addTag" @click="selectTag" @select="addTag"
                               :fetch-suggestions="querySearch"/>

              <!--              添加标签按钮-->
              <el-tooltip v-else effect="dark" content="添加" placement="top">
                <el-button class="button-new-tag" size="small" @click="inputVisible = true" icon="el-icon-plus"/>
              </el-tooltip>
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
            <el-switch v-model="upload.is" active-text="上传文件" inactive-text="输入文件" style="margin: 10px 0"/>
          </el-form-item>

          <!--          文件上传-->
          <div v-show="upload.is">
            <el-col :span="24">
              <el-form-item>
                <el-upload action="" :show-file-list="false" :limit="1" accept=".zip" :http-request="uploadImport">
                  <el-button size="small" type="primary" icon="el-icon-upload">选择文件</el-button>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="24">
              <el-table :data="upload.data">
                <el-table-column label="#"></el-table-column>
                <el-table-column label="输入文件名">
                  <template slot-scope="{row}">
                    <span style="margin-left: 10px" @click="showContents(row.inputName, row.input)">
                      {{ row.inputName }}
                    </span>
                  </template>
                </el-table-column>
                <el-table-column label="输出文件名">
                  <template slot-scope="{row}">
                    <span style="margin-left: 10px" @click="showContents(row.outputName, row.output)">
                      {{ row.outputName }}
                    </span>
                  </template>
                </el-table-column>
              </el-table>
            </el-col>
          </div>

          <!--          样例输入-->
          <div v-show="!upload.is">
            <el-form-item v-for="(sample, index) in problemSamples" :key="'sample' + index">
              <Accordion :title="'后台测试样例' + (index + 1)" :isOpen="sample.isOpen" :index="index"
                         @changeVisible="changeSampleVisible">
                <el-button type="danger" size="small" icon="el-icon-delete" slot="header" @click="deleteSample(index)">
                  删除
                </el-button>
                <el-row :gutter="20">
                  <el-col :xs="24" :md="12">
                    <el-form-item label="后台测试样例输入" required>
                      <el-input :rows="5" type="textarea" placeholder="后台测试样例输入" v-model="sample.input"/>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :md="12">
                    <el-form-item label="后台测试样例输出" required>
                      <el-input :rows="5" type="textarea" placeholder="后台测试样例输出" v-model="sample.output"/>
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
        <el-button type="primary" @click="submit" size="small">保存</el-button>
        <el-button type="primary" @click.native="clearInput" size="small">重置表单</el-button>
      </el-form>
    </el-card>

    <!--    文件上传的表格显示数据-->
    <el-dialog :title="dialog.title" :visible.sync="dialog.visible" width="30%">
      <span>{{ dialog.contents }}</span>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialog.visible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Accordion from "@/components/admin/accordion";
import Editor from "@/components/admin/editor";


import utils from "@/utils/utils";
import {fetchAdminProblem, fetchProblem, getSample, insertOrUpdate, uploadSampleFile} from "@/api/problem";
import tagsApi from '@/api/tags'
import {DEGREE} from "@/utils/constants";


export default {
  name: "Problem",
  components: {
    Accordion,
    Editor
  },

  data() {
    return {
      pid: null, // 题目id，如果为创建模式则为null
      mode: "", // 该题目是编辑或者创建
      problem: {
        problemId: "",
        title: "", // 标题
        description: "", // 描述
        timeLimit: "1000", // 时间限制
        memoryLimit: "256", // 空间限制
        degree: "未知", // 难度
        input: "", // 输入描述
        output: "", // 输出描述
        hint: "", // 提示
        examples: [], // 样例
        defunct: 0, // 题目状态
      }, // 题目信息
      rules: {
        title: {required: true, message: "标题不能为空", trigger: "blur",},
        timeLimit: {required: true, message: "时间限制不能为空", trigger: "blur",},
        memoryLimit: {required: true, message: "空间限制不能为空", trigger: "blur",},
      },
      problemDifficulty: DEGREE.title,
      problemTags: [], // 题目标签
      inputVisible: false, // 标签的光标
      tagInput: "", // 标签输入框
      allTagsTmp: [], // 所有标签

      // 文件上传
      upload: {
        is: true, // 是否上传
        data: [], // 返回的数据
      },
      // 弹窗
      dialog: {
        visible: false,
        title: "",
        contents: "",
      },

      problemSamples: [], // 判题机样例
      sampleIndex: 1, // 判题机样例长度

    }
  },

  created() {
    this.routeProblemId = this.$route.params.problemId
    this.mode = "add";
    if (this.routeProblemId === undefined) {
    } else {
      this.mode = "edit";
      this.pid = this.$route.params.problemId
      this.init_problem_information()
    }
    this.init_tags_all()
  },

  methods: {
    // 加载题目信息
    init_problem_information() {
      if (this.pid) {
        fetchAdminProblem(this.pid).then(res => {
          const data = res.data.problem
          if (data.examples) {
            data.examples = utils.stringToExamples(data.examples)
            for (var i = 0; i < data.examples.length; i++) {
              data.examples[i].isOpen = true
            }
          } else {
            data.examples = Object.assign([], [])
          }
          this.problem = Object.assign({}, data)
          this.problemTags = res.data.tagsList

          if (this.problemTags === undefined || this.problemTags.length === 0) {
            this.problemTags = Object.assign([], []);
          }

          this.problemSamples = res.data.samples
          this.upload.is = true
          if (this.problemSamples === undefined || this.problemSamples.length === 0) {
            this.problemSamples = Object.assign([], [])
          } else {
            this.upload.is = false
          }

        })
      }
    },
    // 获取全部标签
    init_tags_all() {
      tagsApi.getAllList().then(res => {
        if (res.status === 200) {
          this.allTagsTmp = res.data
        }
      })
    },


    // 上传
    uploadImport(param) {
      let formData = new FormData()
      formData.append("file", param.file)

      uploadSampleFile(formData).then(res => {
        if (res.status === 200) {
          this.upload.data = res.data
          this.$message.success("上传成功")
        } else {
          this.$message.error(res.message)
        }
      })
    },
    showContents(title, contents) {
      this.dialog.visible = true
      this.dialog.title = title
      this.dialog.contents = contents
    },

    // 标签
    querySearch(queryString, cb) {
      var restaurants = this.allTagsTmp
      var results = queryString ? restaurants.filter(item => item.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1) : restaurants
      cb(results)
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
      let newTag = {value: this.tagInput,};
      if (this.tagInput) {
        for (var i = 0; i < this.problemTags.length; i++) {
          if (this.problemTags[i].value == this.tagInput) {
            this.$message.warning("添加标签失败")
            this.tagInput = "";
            return;
          }
        }
        for (var i = 0; i < this.allTagsTmp.length; i++) {
          if (this.tagInput == this.allTagsTmp[i].value) {
            newTag.id = this.allTagsTmp[i].id
            newTag.color = this.allTagsTmp[i].color
          }
        }
        this.problemTags.push(newTag);
        this.inputVisible = false
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
      // console.log(this.problem.title, this.problem.description, this.problem.input, this.problem.output, this.problem.timeLimit, this.problem.memoryLimit)
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

        let problemDto = {}
        if (this.problem.degree === '未知') {
          this.problem.degree = 0
        }
        problemDto.problem = Object.assign({}, this.problem)
        problemDto.problem.examples = utils.examplesToString(this.problem.examples)

        problemDto.tagsList = this.problemTags
        problemDto.samples = this.problemSamples

        problemDto.isUploadCase = this.upload.is
        if (problemDto.isUploadCase) {
          let res = Object.assign([], [])
          for (var i = 0; i < this.upload.data.length; i++) {
            res.push({input: this.upload.data[i].input, output: this.upload.data[i].output})
          }
          problemDto.uploadCase = Object.assign([], res)
        }

        insertOrUpdate(problemDto).then(res => {
          if (res.status === 200) {
            this.$message.success("ok")

            this.clearInput()
            this.$router.push('/admin/prolist')
          } else {
            this.$message.error(res.message)
          }
        })
      }
    },

    // 判断、清空输入
    checkInput(s, message) {
      if (!s) {
        this.$message.error(message)
        return false;
      }
      return true;
    },
    clearInput() {
      this.$refs.form.resetFields()
      this.problem.title = ""
      this.problem.description = ""
      this.problem.input = ""
      this.problem.output = ""
      this.problem.examples = Object.assign([], [])
      this.problem.timeLimit = "1000"
      this.problem.memoryLimit = "256"
      this.problem.degree = "未知"
      this.problem.hint = ""
      this.problem.defunct = 0
      this.problemSamples = Object.assign([], [])
    },

  },


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
