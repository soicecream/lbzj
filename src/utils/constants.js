export const JUDGE_STATUS = {
  '0': {
    name: '排队中',
    short: 'WT0',
    loading:true,
    type: 'primary'
  },
  '1': {
    name: '排队中',
    short: 'WT1',
    loading:true,
    type: 'primary'
  },
  '2': {
    name: '编译中',
    short: 'CI',
    loading:false,
    type: 'warning'
  },
  '3': {
    name: '运行中',
    short: 'RI',
    loading:false,
    type: 'warning'
  },
  '4': {
    name: '正确',
    short: 'TLE',
    loading:false,
    type: 'success'
  },
  '5': {
    name: '格式错误',
    short: 'PE',
    loading:false,
    type: 'warning'
  },
  '6': {
    name: '答案错误',
    short: 'RE',
    loading:false,
    type: 'danger'
  },
  '7': {
    name: '时间超限',
    short: 'TLE',
    loading:false,
    type: 'danger'
  },
  '8': {
    name: '内存超限',
    short: 'MLE',
    loading:false,
    type: 'danger'
  },
  '9': {
    name: '输出超限',
    short: 'OLE',
    loading:false,
    type: 'danger'
  },
  '10': {
    name: '运行错误',
    short: 'RE',
    loading:false,
    type: 'warning'
  },
  '11': {
    name: '编译错误',
    short: 'CE',
    loading:false,
    type: 'warning'
  }
}

export const Languages ={
  "1":{
    lang:'c++'
  },
  "3":{
    lang:'java'
  }
}

