# Mac配置环境变量

## 1. 进入根目录

## 2. 如果没有.bash_profile文件需要创建.bash_profile文件

```
touch .bash_profile
```

## 3. 编辑.bash_profile文件

```
open -e .bash_profile
```

##  4. save file 

## 5. 更新刚配置的环境变量

```
source .bash_profile
```

参考模版

```
[[ -s "$HOME/.profile" ]] && source "$HOME/.profile" # Load the default .profile
[[ -s "$HOME/.bashrc" ]] && source "$HOME/.bashrc"
export PATH=$PATH:/Users/os/luosw/apache-tomcat-7.0.70/bin
GRADLE_HOME=/Users/os/luosw/gradle-2.4
export GRADLE_HOME
export PATH=$PATH:$GRADLE_HOME/bin
export NDK_ROOT=/Users/os/Downloads/android-ndk-r13b
export ANDROID_SDK_ROOT=/Users/os/Downloads/android-sdk-macosx
export COCOS_CONSOLE_ROOT=/Users/os/Downloads/cocos2d-x-3.13.1/tools/cocos2d-console/bin
export PATH=$COCOS_CONSOLE_ROOT:$PATH
export COCOS_TEMPLATES_ROOT=/Users/Users/os/Downloads/cocos2d-x-3.13.1/templates
export PATH=$COCOS_TEMPLATES_ROOT:$PATH
export ANT_ROOT=/Users/os/Downloads/apache-ant-1.9.7/bin  

```