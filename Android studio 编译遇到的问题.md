# Android studio 编译遇到的问题

## 1. Process 'command '/Users/os/Library/Android/sdk/build-tools/24.0.0/aapt'' finished with non-zero exit value 1

这个问题Android studio并不会报详细的错误信息，通过使用如下的命令，才能看到详细的错误原因

```
./gradlew build --info
```