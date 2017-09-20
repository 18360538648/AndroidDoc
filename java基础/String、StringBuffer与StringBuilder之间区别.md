# String、StringBuffer与StringBuilder之间区别



## 1.三者区别

### 1.1 类型

String: 字符串常量,不可改变对象,当执行+时，会创建新的对象保存运算后的结果，前面的对象会被gc回收

StringBuffer：字符创变量

StringBuilder：字符创变量

### 1.2 速度
StringBuilder > StringBuffer > String，因此在单线程中拼接字符串首选StringBuilder

### 1.3 安全性

StringBuilder：线程非安全的

StringBuffer：线程安全的
