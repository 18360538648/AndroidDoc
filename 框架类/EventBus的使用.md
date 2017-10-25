# EventBus的使用

[官网地址](https://github.com/greenrobot/EventBus)
[参考文档](http://www.jianshu.com/p/54c635b3a33a)

## 1. EventBus的概述

`EventBus`是`Android`端优化`publish/subscribe`消息总线，简化应用程序各个组件之间，组件和后台线程间的通信

## 2. 基础用法

### 2.1 添加依赖

```
compile 'org.greenrobot:eventbus:3.0.0'
```

### 2.2 注册

在Activity中的oncreate()中。切记不要重复注册，否则会造成程序奔溃(自己血的教训)

```
EventBus.getDefault().register(MainActivity.this);
```

### 2.3 注销

在Activity中的onDestory()中

```
EventBus.getDefault().unregister(MainActivity.this);
```

### 2.4 定义消息实体类

```
public class EventBusMsg {
  public String name;

  public EventBusMsg(String name) {
      this.name = name;
  }
}
```
### 2.5 发送事件

```
EventBus.getDefault().post(new EventBusMsg("我是EventBus发送的数据"));
```

### 2.6 定义接收事件

```
@Subscribe(threadMode = ThreadMode.MAIN)
public void onMessageEvent(EventBusMsg event) {
    Log.d(TAG, "接收到信息");
    tv_console.setText("EventBus 数据 : " + event.name);
}
```

## 3 EventBus使用进阶

### 3.1 线程切换

通过在订阅事件方法中加入`threadMode = **` 可以切换订阅事件所处的线程

* ThreadMode.POSTING 接收方法和发送事件处于同一个线程，不需要线程线程.优势是开销最小
* ThreadMode.MAIN 接收方法将切换至UI线程
* ThreadMode.BACKGROUND 接收方法将运行于一个子线程
* ThreadMode.ASYNC 没有看懂

