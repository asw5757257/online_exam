module.exports={
    publicPath:'',
    outputDir: 'dist',
  assetsDir: 'static',
    
    devServer: {
        port:3000,
        proxy: {
          '/api': {
            target: 'http://localhost:8080', //这里面是你要访问的IP地址
           // target: 'http://60.205.176.95:8080', //这里面是你要访问的IP地址
            changeOrigin: true,     //开启代理
            pathRewrite: {
              '^/api': '/api'
          }

    }
}
    }

}