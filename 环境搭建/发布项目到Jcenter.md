# 发布项目到Jcenter

## 1. 导语

在Android Studio引入第三方库，通过compile方式就可以了，这样使引入第三方库很简单,我一直在想别人是怎么发布Jcenter库的，于是就花时间研究了一下。

## 2. 发布流程

## 2.1 注册账户

访问 [bintray](https://bintray.com/)，点击For an Open Sourse Account Sign Up Here,请使用gmail邮箱，国内会邮箱报错

ps：别点左边的START YOUR FREE TRIAL，这是企业账户，这是一个大坑

## 2.2 点击 add New Repository 

其中name填写maven，type选Maven

## 2.3 新建一个Android工程

## 2.4 在新建的Android工程中新建一个Module

ps：选择Android Library

## 2.5 在Android工程的build.gradle加入如下库

```
classpath 'com.novoda:bintray-release:0.3.4'
```

## 2.6 在Module的build.gradle加入如下库与配置信息

```
apply plugin: 'com.android.library'
apply plugin: 'com.novoda.bintray-release'//添加
android {
    ...
}

dependencies {
    ...
}
//添加
publish {
    userOrg = '******'// bintray.com用户名，注意不是邮箱
    // 以下三个配置信息(groupId, artifactId, publishVersion)将是最后compile的地址
    groupId = 'com.test'//jcenter上的路径
    artifactId = 'test'//项目名称
    publishVersion = '1.0.0'//版本号
    desc = '测试'//描述，不重要
    website = 'https://github.com/18360538648/jcenttest'//网站，可填github地址}

```

## 2.7 执行上传命令
在Android Studio 中的terminal中执行下面代码

PbintrayUser后面填用户名，PbintrayKey后面填API Key(进入bintray，登录成功以后点击头像，选择Edit profile 选择API Key，然后输入密码，可得API Key)

```
./gradlew clean build bintrayUpload -PbintrayUser=**** -PbintrayKey=******* -PdryRun=false
```
## 2.8 增加到Jcenter

如果2.7执行成功，回到bintray官网点开Maven，可以在Linked to 一栏看到 Add to JCenter，点开它，填写一些介绍，点击发送，进行审核。

## 2.9 审核成功以后

Linked to 下面就会出现Jcenter标志,这样就可以通过compile方式引入自己的库

## 3 遇到的坑

### 3.1 点击START YOUR FREE TRIAL 进行注册，这会遇到上传成功以后没有Add to Jcenter按钮

### 3.2 填写用户名时误填邮箱,执行脚本时报name is empty

### 3.3 报maven was not found。

先执行2.2，再执行上传工程操作



 