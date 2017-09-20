# 解析Json数据

知识点介绍

序列化：将对象转化为可存储或传输的形式的过程
反序列化:将可存储或传输的形式转化为对象过程

## 一. Gson使用

### 1.1 引入架包

[Gson官网](https://github.com/google/gson)

```
compile 'com.google.code.gson:gson:2.7'
```

### 1.2 Gson的基本用法

#### 1.2.1  提供`gson.fromJson()`和`gson.toJson()`两个方法解析(反序列化)和生成Json(序列化)。
反序列化可以将Json对象直接转化为实体类

#### 1.2.2 属性重命名 @SerializedName 注解的使用

```
// 比如json中的字段为where，在实体类中通过@SerializedName,可以变为where1
@SerializedName("where")
private String where1;
```

#### 1.2.3 范型的使用

通常解析一个JsonArray数组，我们是将数据放入List中,这样有利于存取，T就是对应的实体类

```
List<T> stringList = gson.fromJson(jsonArray, new TypeToken<List<T>>() {}.getType());
```

### 参考文献

[你真的会用Gson吗](http://www.jianshu.com/p/e740196225a4)



## 二. FastJson使用

### 2.1 引入架包

[FastJson官网](https://github.com/alibaba/fastjson)

```
compile 'com.alibaba:fastjson:1.1.63.android'
```

### 2.2 FastJson的基本用法
#### 2.2.1  提供`JSON.parseObject()`和`JSON.toJSONString()`两个方法解析(反序列化)和生成Json(序列化)。

反序列化可以将Json对象直接转化为实体类

#### 2.2.2 范型的使用

通常解析一个JsonArray数组，我们是将数据放入List中,这样有利于存取，T就是对应的实体类

```
List<T> stringList = JSON.parseObject(data, new TypeReference<List<T>>() {
        });
```

或者

```
List<T> stringList = JSON.parseArray(data,T.class)
```


### 3. 两者比较



