# 安装

## node js 下载安装

​	[node js 官网](https://nodejs.org/en/)

下载 LTS 或 Current 版本

压缩包版本的需要设置环境变量，`npm.cmd`的路径配置到`path`中。

## whistle 安装

管理员身份运行`cmd`

输入 `npm install whistle -g -- registry=https://registry.npm.taobao.org` ，利用淘宝镜像安装。

```shell
## whistle
## 启动
w2 start
## 重启
w2 restart
## 关闭
w2 stop
## 更多帮助
w2 help
## 启动且修改端口 默认8899
w2 start -p 8090
```

`chrome`输入 访问`127.0.0.1:8899`进入管理平台

如果启动出现如下错误

```
w2 : 无法加载文件 C:\Users\hzf\AppData\Roaming\npm\w2.ps1，因为在此系统上禁止运行脚本。有关详细信息，请参阅 https:/go.m
icrosoft.com/fwlink/?LinkID=135170 中的 about_Execution_Policies。
所在位置 行:1 字符: 1
+ w2 start
+ ~~
    + CategoryInfo          : SecurityError: (:) []，PSSecurityException
    + FullyQualifiedErrorId : UnauthorizedAccess
```

输入`set-ExecutionPolicy RemoteSigned`更改执行策略，继续输入`Y`。

##  SwitchyOmega 下载安装

​	[google 插件下载](https://github.com/FelisCatus/SwitchyOmega/releases)

下载 `crx` 文件，下载完成后将扩展名改为`zip`，打开`chrome`的扩展程序，打开开发者模式，将压缩包拖入即可安装。



## 使用代理将UAT环境的请求转发到本地服务

1. 在`Whistle Web Debugger`上新建规则。

   ![image-20200630213600570](https://i.loli.net/2021/01/28/WSDdckOHoIvupya.png)

2. `SwitchyOmega`选择系统代理

![image-20200630213701618](https://i.loli.net/2021/01/28/hUWNFecHTtbJSYr.png)

3. `whistle`插件开启代理，选择使用的规则

![image-20200630213810887](https://i.loli.net/2021/01/28/poXibtNhgke73xI.png)