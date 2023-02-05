'use strict'
// 选择端口启动
// port = 9528 npm run dev OR npm run dev --port = 9528
const port = process.env.port || process.env.npm_config_port || 80 // dev port

module.exports = {
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: false,
  productionSourceMap: false,
  devServer: {
    port: port,
    open: true,
    proxy:{//跨域
      '/api':{
        target:'http://localhost:8080',
        changeOrigin:true,
        pathRewrite:{
          '^/api':''
        }
      }
    },
  }
}
