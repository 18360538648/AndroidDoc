# Android 线程

## 1. 线程在Android中的应用
在Android开发中线程是一个十分重要的概念，当有耗时操作时，就需要在子线程中执行，一般我们这样开启一个子线程,当子线程结束时，GC会自动回收该线程。

```
new Thread(new Runnable() {
            @Override
            public void run() {
                //do sth .
            }
        }).start();
```

当一个应用中，开启的子线程不多时，一切还好。但当一个程序需要由很多地方开启大量的子线程来处理任务，如果还用上面的方式创建线程处理耗时操作的话，那么整个系统会变的很糟糕。

* 线程的创建和销毁都需要时间，当有大量的线程创建和销毁时，那么这些时间的消耗则比较明显，将导致性能上的缺失
* 大量的线程创建、执行和销毁是非常耗cpu和内存的，这样将直接影响系统的吞吐量，导致性能急剧下降，如果内存资源占用的比较多，还很可能造成OOM
* 大量的线程的创建和销毁很容易导致GC频繁的执行，从而发生内存抖动现象，而发生了内存抖动(大量的对象被创建或者回收的现象)，对于移动端来说，最大的影响就是造成界面卡顿针对上述现象，最后的解决方法是:重用已有的线程，从而减少线程的创建。


## 2. 线程池(ExecutorService)

线程池的优点

* 线程的创建和销毁由线程池维护，一个线程在完成任务后并不会立即销毁，而是由后续的任务复用这个线程，从而减少线程的创建和销毁，节约系统的开销
* 线程池旨在线程的复用，这就可以节约我们用以往的方式创建线程和销毁所消耗的时间，减少线程频繁调度的开销，从而节约系统资源，提高系统吞吐量
* 在执行大量异步任务时提高了性能
* Java内置的一套ExecutorService线程池相关的api，可以更方便的控制线程的最大并发数、线程的定时任务、单线程的顺序执行等

ExecutorService，它是一个接口，其实如果要从真正意义上来说，它可以叫做线程池的服务，而真正意义上的线程池就是：ThreadPoolExecutor，它实现了ExecutorService接口，并封装了一系列的api使得它具有线程池的特性，其中包括工作队列、核心线程数、最大线程数等。而创建一个线程池不用new ThreadPoolExecutor(…)，这样配置很多参数。而使用Executors的工厂方法来创建线程池，它里面封装好了众多功能不一样的线程池，从而使得我们创建线程池非常的简便。

* newFixedThreadPool()。该方法返回一个固定线程数量的线程池，该线程池中的线程数量始终不变，即不会再创建新的线程，也不会销毁已经创建好的线程，自始自终都是那几个固定的线程在工作，所以该线程池可以控制线程的最大并发数。

```
ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
    for (int i = 1; i <= 10; i++) {
        final int index = i;
        fixedThreadPool.execute(new Runnable() {
             @Override
             public void run() {
                 String threadName = Thread.currentThread().getName();
                 Log.v("zxy", "线程："+threadName+",正在执行第" + index + "个任务");
                 try {
                        Thread.sleep(2000);
                 } catch (InterruptedException e) {
                        e.printStackTrace();
                 }
             }
         });
     }
```
* newCachedThreadPool()。该方法返回一个可以根据实际情况调整线程池中线程的数量的线程池。即该线程池中的线程数量不确定，是根据实际情况动态调整的。如果线程池中的空闲线程的空闲时间超过该“保存活动时间”则立刻停止该线程，而该线程池默认的“保持活动时间”为60s。

```
ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cachedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程：" + threadName + ",正在执行第" + index + "个任务");
                    try {
                        long time = index * 500;
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
```
* newSingleThreadExecutor()。该方法返回一个只有一个线程的线程池，即每次只能执行一个线程任务，多余的任务会保存到一个任务队列中，等待这一个线程空闲，当这个线程空闲了再按FIFO方式顺序执行任务队列中的任务。

```
ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();
        for (int i = 1; i <= 10; i++) {
            final int index = i;
            singleThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    String threadName = Thread.currentThread().getName();
                    Log.v("zxy", "线程："+threadName+",正在执行第" + index + "个任务");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
```
* newScheduledThreadPool()。 该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池

```
ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(3);
        //延迟2秒后执行该任务
        scheduledThreadPool.schedule(new Runnable() {
            @Override
            public void run() {

            }
        }, 2, TimeUnit.SECONDS);
        //延迟1秒后，每隔2秒执行一次该任务
        scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {

            }
        }, 1, 2, TimeUnit.SECONDS);
```
* newSingleThreadScheduledExecutor()。该方法返回一个可以控制线程池内线程定时或周期性执行某任务的线程池。只不过和上面的区别是该线程池大小为1，而上面的可以指定线程池的大小

```
ScheduledExecutorService singleThreadScheduledPool = Executors.newSingleThreadScheduledExecutor();
        //延迟1秒后，每隔2秒执行一次该任务
        singleThreadScheduledPool.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                String threadName = Thread.currentThread().getName();
                Log.v("zxy", "线程：" + threadName + ",正在执行");
            }
        },1,2,TimeUnit.SECONDS);
```






