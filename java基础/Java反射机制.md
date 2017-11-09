# Java反射机制

## 1. 反射的概念

程序可以访问，检测和修改它本身状态或行为的一种能力，并能根据自身行为的状态和结果，调整或修改应用所描述行为的状态和相关的语义

## 2. 反射机制的作用

### 2.1 反编译

.classs -- > .java

### 2.2 通过反射机制访问java对象的属性，方法，构造方法

## 3. 具体的使用

### 3.1 获取类

```
1. method1
Class c1 = Class.forName("Employee")
2. method2 
Class c2 = Employee.class
3. method3

Employee e = new Employee();
Class c3 = e.getClass();

```

### 3.2 创建对象

```
Class c = Class.forName("Employee");

Object o= c.newInstance();
```

### 3.3 获取属性，方法，构造方法

