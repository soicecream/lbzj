<template>
  <div >
      <div style="width: 100%;">
        <codemirror :value="value" :options="options" @change="onEditorCodeChange" ref="myEditor">
        </codemirror>
      </div>
  </div>
</template>
<script>
  import { codemirror } from 'vue-codemirror-lite'

  // theme
  import 'codemirror/theme/monokai.css'
  import 'codemirror/theme/solarized.css'
  import 'codemirror/theme/material.css'

  // mode
  import 'codemirror/mode/clike/clike.js'
  import 'codemirror/mode/python/python.js'

  // active-line.js
  import 'codemirror/addon/selection/active-line.js'

  // foldGutter
  import 'codemirror/addon/fold/foldgutter.css'
  import 'codemirror/addon/fold/foldgutter.js'
  import 'codemirror/addon/fold/brace-fold.js'
  import 'codemirror/addon/fold/indent-fold.js'

  import 'codemirror/addon/hint/anyword-hint'
  import 'codemirror/addon/hint/css-hint.js';
  import 'codemirror/addon/hint/html-hint.js';
  import 'codemirror/addon/hint/javascript-hint.js';
  import 'codemirror/addon/hint/show-hint.css';
  import 'codemirror/addon/hint/show-hint.js';

  import { saveSubmission } from '@/api/submission'

  export default {
    name: 'CodeMirror',
    components: {
      codemirror
    },
    props: {
      value: {
        type: String,
        default: ''
      },
      languages: {
        type: Array,
        default: () => {
          return [ 'C++', 'Java']
        }
      },
      language: {
        type: String,
        default: 'C++'
      },
      theme: {
        type: String,
        default: 'solarized'
      }
    },
    data () {
      return {
        options: {
          // codemirror options
          tabSize: 4,
          mode: 'python',
          theme: 'solarized',
          lineNumbers: true,
          line: true,
          // 代码折叠
          foldGutter: true,
          gutters: ['CodeMirror-linenumbers', 'CodeMirror-foldgutter'],
          // 选中文本自动高亮，及高亮方式
          styleSelectedText: true,
          lineWrapping: true,
          highlightSelectionMatches: {showToken: /\w/, annotateScrollbar: true},
          extraKeys: {"Ctrl": "autocomplete"}
        },
        mode: {
          'C++': 'text/x-csrc'
        },
        themes: [

        ],


      }
    },
    methods: {
      onEditorCodeChange (newCode) {
        this.$emit('update:value', newCode)
      },
      submit(){
        this.solutionAndSourceCode.source = this.value;
        saveSubmission(this.solutionAndSourceCode).then(response=>{
          if(response.success){
            this.$router.push('/state')
          }
        })
      }
    },
    computed: {
      editor () {
        // get current editor object
        return this.$refs.myEditor.editor
      }
    },
    watch: {
      'theme' (newVal, oldVal) {
        this.editor.setOption('theme', newVal)
      }
    }
  }
</script>

<style lang="less" scoped>
  .header {
    margin: 5px 5px 15px 5px;
    .adjust {
      width: 150px;
      margin-left: 10px;
    }
    .fl-right {
      float: right;
    }
  }
</style>

<style>
  .CodeMirror {
    height: auto !important;
  }
  .CodeMirror-scroll {
    min-height: 300px;
    max-height: 1000px;
  }
  .submit {
    margin-top: 20px;
    text-align: right;
  }
</style>
