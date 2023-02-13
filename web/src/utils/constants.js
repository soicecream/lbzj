export const JUDGE_STATUS = {
  '0': {
    name: '排队中',
    short: 'WT0',
    loading:true,
    type: 'primary',
    show: true,
  },
  '1': {
    name: '排队中',
    short: 'WT1',
    loading:true,
    type: 'primary',
    show: false,
  },
  '2': {
    name: '编译中',
    short: 'CI',
    loading:false,
    type: 'warning',
    show: true,
  },
  '3': {
    name: '运行中',
    short: 'RI',
    loading:false,
    type: 'warning',
    show: true,
  },
  '4': {
    name: '正确',
    short: 'TLE',
    loading:false,
    type: 'success',
    show: true,
  },
  '5': {
    name: '格式错误',
    short: 'PE',
    loading:false,
    type: 'warning',
    show: true,
  },
  '6': {
    name: '答案错误',
    short: 'RE',
    loading:false,
    type: 'danger',
    show: true,
  },
  '7': {
    name: '时间超限',
    short: 'TLE',
    loading:false,
    type: 'danger',
    show: true,
  },
  '8': {
    name: '内存超限',
    short: 'MLE',
    loading:false,
    type: 'danger',
    show: true,
  },
  '9': {
    name: '输出超限',
    short: 'OLE',
    loading:false,
    type: 'danger',
    show: true,
  },
  '10': {
    name: '运行错误',
    short: 'RE',
    loading:false,
    type: 'warning',
    show: true,
  },
  '11': {
    name: '编译错误',
    short: 'CE',
    loading:false,
    type: 'warning',
    show: true,
  },
  '12': {
    name: '全部',
    type: 'primary',
    show: false,
  },

}

export const Languages ={
  "1":{
    lang:'c++'
  },
  "3":{
    lang:'java'
  }
}

