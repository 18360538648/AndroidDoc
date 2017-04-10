# 在mac系统安装Apache Tomcat的详细步骤

## 1.下载地址

[tomcat下载地址](http://tomcat.apache.org/download-70.cgi)

## 2. 下载完成后 ，然后随意放目录，比如我放在/Users/os/luosw/目录下

## 3.配置环境变量

在终端中的根目录下面执行 

```
pico .bash_profile
```
加入如下路径

```
export PATH=$PATH:/Users/os/luosw/apache-tomcat-7.0.70/bin
```

编辑完后，control+x   （保存）    继续 ：y   （同意）     回车；

## 4.启动tamcat

在终端中的根目录下面执行

```
startup.sh
```

注意：
如果当 startup.sh后出现类似 “Permission denied” 字样，那么需要你对此目录进行权限设置：

启动终端：输入   sudo chmod 755 xxx/bin/*.sh     (xxx表示你tomcat放至的路径) 回车；

打开如下网址： http://localhost:8080/显示tomcat的界面即说明成功了


