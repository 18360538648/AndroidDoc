# MAC反编译apk包

1. apktool d apk路径，就可以得到反编译以后的文件，可以看到一些布局文件和资源文件
2. 将apk包的后缀改为zip，然后解压，将其中的classes.dex文件拷贝到apktool／dex2jar-0.0.9.15目录下面，进入这个目录，然后运行sh dex2jar.sh classes.dex命令,执行成功后会生成一个classes_dex2jar.jar,利用jd-gui工具打开这个jar包，就可以看到源码了。

