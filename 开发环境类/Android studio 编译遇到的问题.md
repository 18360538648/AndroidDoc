# Android studio 编译遇到的问题

## 1. Process 'command '/Users/os/Library/Android/sdk/build-tools/24.0.0/aapt'' finished with non-zero exit value 1

这个问题Android studio并不会报详细的错误信息，通过使用如下的命令，才能看到详细的错误原因

```
./gradlew build --info
```

## 2. duplicate entry: Android/support/v7/appcompat/R$anim.class

```
./gradlew clean
```

## 3. 在查看源代码时，会报如下错误

```
throw new RuntimeException("Stub!")
```

解决方法：

etting->Preferences -> Appearance & Behavior -> System Settings -> Android SDK--->点击EditAndroid SDK location 然后点击下一步--->下一步-->完成


## 4. 调试的时候出现如下问题

```
Application com.steam.ecos is waiting for the debugger on port 8100
```

解决方啊：
Run-> Edit-Configurations->Miscellaneous Tab->uncheck 'skip installation if APK has not changed' 