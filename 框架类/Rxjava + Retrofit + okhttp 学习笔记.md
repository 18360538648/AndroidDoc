# Rxjava + Retrofit + okhttp 学习笔记

## 1. Rxjva

### 1.1 一些基本接口

#### 1.1.1 Subscription

这是`Subscriber`实现的一个接口，用于取消订阅，在此方法调用之后`Subscriber`将不再接收事件。在`subscriber()`之后,`Observerable`会持有`Subscriber`的引用，如果这个引用不及时释放，很容易造成内存泄漏，因此最好在不用时且合适的地方调用`unsubscribe()`来解除引用。

```
if (null != subscription && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
}

```

## 2. Retrofit

## 3. 三者结合