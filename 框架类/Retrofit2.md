# Retrofit2

[参考链接](http://www.jianshu.com/p/73216939806a)


## 1. 注解

### 1.1 请求方法注解
|注解|说明|
|:--|:--|
|@GET|get请求|
|@POST|post请求|
|@PUT|put请求|
|@DELETE|delete请求|

### 1.2 请求头注解

|注解|说明|
|:--|:--|
|@Headers|用于添加固定请求头，可以同时添加多个。通过该注解添加的请求头不会相互覆盖，而是共同存在|
|@Header|作为方法的参数传入，用于添加不固定值的Header，该注解会更新已有的请求头|

### 1.3 请求参数注解

|注解|说明|
|:--|:--|
|@Body|多用于post请求发送非表单数据,比如想要以post方式传递json格式数据|
|@Filed|多用于post请求中表单字段,Filed和FieldMap需要FormUrlEncoded结合使用|
|@FiledMap|和@Filed作用一致，用于不确定表单参数|
|@Part|用于表单字段,Part和PartMap与Multipart注解结合使用,适合文件上传的情况|
|@PartMap|用于表单字段,默认接受的类型是Map<String,REquestBody>，可用于实现多文件上传|
|@Path|用于url中的占位符|
|@Query|用于Get中指定参数|
|@QueryMap|和Query使用类似|
|@Ur|指定请求路径|

### 1.4 请求和响应格式注解

|注解|说明|
|:--|:--|
|@FormUrlEncoded|表示请求发送编码表单数据，每个键值对需要使用@Field注解|
|@Multipart|表示请求发送multipart数据，需要配合使用@Part|
|@Streaming|表示响应用字节流的形式返回.如果没使用该注解,默认会把数据全部载入到内存中.该注解在在下载大文件的特别有用|



