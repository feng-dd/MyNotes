## 设置远程库

项目代码在`GitLab`先`Fork`到自己的仓库，从自己的路径检出。

```powershell
## 查看远程仓库分支的详细信息
git remote -v
## 设置远程仓库路径
git remote set-url origin http://gitlab.oud.cn/workshop/medcloud-institution.git
## 设置推送的远程库路径
git remote set-url --push origin http://gitlab.medcloud.cn/tianhui.feng/medcloud-institution.git
```

`IDEA`中开发时切换到带`dev`分支

## 暂存代码

当本来需要在`DEV`分支开发的代码在`UAT`分支开发了。

> 未提交，切换到`DEV`分支继续开发，可以通过`stash changes`暂存已修改的代码。

![image-20200702190654279](https://i.loli.net/2021/01/28/1fqN5vSRbmosin4.png)

> 切换分支到`DEV`，从暂存区取出代码

![image-20200702190758157](https://i.loli.net/2021/01/28/Cf79KL4WPYxb8cu.png)

> 已经在`UAT`提交了代码，需要切换到`DEV`提交，通过`cherry-pick`更换成`DEV`提交，再`push`，发送`mr`，仅适用一次提交的情况
>
> 当有多次连续提交时，需要多次`cherry-pick`

## 提交

```properties
operating：delete code
demand：创建机构时，正式账号的合作单位非必输
result：删除了判断合作单位必输的代码

demand：查询机构返回对象添加 是否测试账户 的字段
operating：add field
```

## 代码回滚

### 一、本地代码回滚

#### 1. 获取当前需要回滚到的commit版本号

![1284985-20181031104135311-671963514](https://i.loli.net/2021/04/29/EFRr7MP3CgzApDu.png)

#### 2. 本地回滚到当前commit

![1284985-20181031104155340-1035525822](https://i.loli.net/2021/04/29/fkyLaC8ozMnFlbT.png)

![1284985-20181031104211590-453839767](https://i.loli.net/2021/04/29/O8oUhM4ivnTHj3D.png)

> Reset Type：
>
> 1. mixed 默认方式，只保留源码，回退commit和index信息
> 2. soft 回退到某个版本，只回退了commit的信息，不会恢复到index file一级。如果还要提交，直接commit
> 3. hard 彻底回退，本地源码也会变成上一个版本内容

### 二、本地回滚代码提交远程分支

> 本地回滚后，需要提交远程分支，但是会遇到冲突，解决方法有两种

1. 强制提交

```cmd
# 强制提交
git push origin 分支名 -force
```

2. ![img](C:\Users\hzf\Desktop\个人笔记\GIT.assets\1284985-20181031104348350-881061100.png)

a. 右击项目依次选中：Git->Repository->Reset HEAD

b. 选中Reset Type:Mixed, To Commit:oldVersion；然后点击Reset按钮

c. 这时你会发现，最新版本又回到newVersion。但是代码还是oldVersion的代码，这时候重push到远程仓库就不会版本冲突了

> 两种方法对比：
>
> 1. 会将回退的提交记录抹掉
>
> 2. 会保留提交记录

