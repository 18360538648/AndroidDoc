# RxJava学习笔记
[RxJava](https://github.com/ReactiveX/RxJava)

[RxAndroid](https://github.com/ReactiveX/RxAndroid)

[参考文献链接](https://gank.io/post/560e15be2dca930e00da1083#toc_5)

[参考文献链接](http://www.jianshu.com/u/c50b715ccaeb)
## 1. RxJava2简介
RxJava是实现异步操作的库，其作用是使代码变的简洁，并且随着程序逻辑变的越来越复杂，它依然能保持代码的简洁。RxJava的异步实现其原理为扩展的观察者模式来实现的,其观察者模式有四个基本的概念：`Observable`(被观察者)，`Observer`(观察者)，`Subscribe`(订阅)，事件。`Observable`通过`Subscribe()`订阅`Observer`,从而`Observable`可以在需要的时候发出事件通知`Observer`。RxJava有如下事件:

* `onNext()`:普通事件
* `onCompleted()`:事件队列完结标志，RxJava规定当没有新的`onNext()`发出时，需触发`onCompleted()`方法作为标志

* `onError()`:事件处理队列发生异常时，会触发`onError()`，队列会自动终止，不允许有事件发出

ps：一个事件处理队列中，`onCompleted()`和`onError()`保持唯一和互斥原则




## 2. RxJava2一些基本接口

### 2.1 创建`Observable`

// 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1111");
                e.onNext("2222");
                e.onNext("3333");
                // next完成之后一定要complete
                e.onComplete();
            }
        });

#### 2.2 创建`Observer`

```
// 创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("lsw", "onSubscribe---");
            }

            @Override
            public void onNext(String s) {
                Log.i("lsw", "onNext---" + s);
            }


            @Override
            public void onError(Throwable e) {
                Log.i("lsw", "onError---");
            }

            @Override
            public void onComplete() {
                Log.i("lsw", "onComplete--");
            }
        };
```

### 2.3 `Observable` 订阅 `Observer`

```
observable.subscribe(observer);
```

### 3. RxJava2线程控制

默认情况下，`Observable`在哪个线程发送事件，`Observer`就在哪个线程接受事件，即观察者和被观察者处于同一个线程

```
// 创建被观察者
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                Log.i("lsw", "observable***" + Thread.currentThread().getName());
                e.onNext("1111");
                e.onNext("2222");
                e.onNext("3333");
                // next完成之后一定要complete
                e.onComplete();
            }
        });
        // 创建观察者
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.i("lsw", "onSubscribe---");
            }

            @Override
            public void onNext(String s) {
                Log.i("lsw", "observer" + Thread.currentThread().getName());
                Log.i("lsw", "onNext---" + s);
            }


            @Override
            public void onError(Throwable e) {
                Log.i("lsw", "onError---");
            }

            @Override
            public void onComplete() {
                Log.i("lsw", "onComplete--");
            }
        };
        // 被观察者订阅观察者
        observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
```

Rxjava 提供的线程

* Schedulers.io() 代表io操作的线程, 通常用于网络,读写文件等io密集型的操作
* Schedulers.computation() 代表CPU计算密集型的操作, 例如需要大量计算的操作
* Schedulers.newThread() 代表一个常规的新线程
* AndroidSchedulers.mainThread() 代表Android的主线程


### 4. RxJava2操作符

#### 4.1 Map

Map是对被观察者发送的每一个事件应用于一个函数，使得每一个事件都按指定的函数去变化

```
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(11);
                e.onNext(22);
                e.onComplete();
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(@NonNull Integer integer) throws Exception {
                return "this result is " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.i("lsw", "s----" + s);
            }
        });
```

#### 4.2 FlatMap

FlatMap使用一个指定的函数对原始Observable发射的每一项数据执行变换操作，这个指定的函数返回一个也是发射数据的Observable，然后FlatMap合并这些Observables发射的数据，最后将合并后的结果当作自己的数据序列发射。FlatMap并不保证事件的顺序，事件2可能在事件1前面。(ps:concatMap是可以的保证事件顺序执行的)

对这个FlatMap不是很懂，需要进一步理解

```
Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                e.onNext(11);
                e.onNext(22);
                e.onNext(33);
            }
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(@NonNull Integer integer) throws Exception {
                List<String> list = new ArrayList<String>();
                for (int i = 0; i < 3; i++) {
                    list.add("i am value" + integer);
                }
                return Observable.fromIterable(list).delay(30, TimeUnit.MILLISECONDS);
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                Log.i("lsw", "s:" + s);
            }
        });
```

ps: FlatMap转变得到的是一个`Observable`对象，而Map得到是一个普通的结果
#### 4.3 zip

`zip`通过一个函数将多个`Observable`发送的事件结合在一起，然后发送这些组合到一起的事件，它只会发射与发射数据项最少的那个`Observable`一样的多的数据

使用场景举例，一个界面需要展示用户的一些基本信息，这些基本信息都需要从服务器获取，等到两个都获取到才进行展示,这时候就要用`zip`了。

#### 4.4 Flowable

为了解决上下游流速不均衡

```
        // 与observable创建方式不同的是后面加了一个参数，这个参数是背压使用的，如果上下流速度不均匀怎么处理
        // 比如BackpressureStrategy.ERROR会在出现上下游流速不均衡的时候直接抛出一个异常
        Flowable.create(new FlowableOnSubscribe<String>() {
            @Override
            public void subscribe(FlowableEmitter<String> e) throws Exception {
                e.onNext("11");
                Log.i("lsw", "onComplete-----");
                e.onNext("22");
                Log.i("lsw", "22-----");
                e.onNext("33");
                Log.i("lsw", "33-----");
                e.onComplete();
                Log.i("lsw", "onComplete flowable-----");
            }
        }, BackpressureStrategy.ERROR).subscribe(new Subscriber<String>() {
            @Override
            public void onSubscribe(Subscription s) {
                // 响应式拉取，观察者需要多少个
                s.request(1);
            }

            @Override
            public void onNext(String s) {
                Log.i("lsw", "s" + s);
            }

            @Override
            public void onError(Throwable t) {

            }

            @Override
            public void onComplete() {
                Log.i("lsw", "onComplete-----");
            }
        });
```







