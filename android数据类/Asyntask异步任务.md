# Asyntask异步任务

## Asyntask异步任务：public abstract class AsyncTask<Params, Progress, Result> {}

三种泛型类型分别代表“启动任务执行的输入参数”、“后台任务执行的进度”、“后台计算结果的类型”。在特定场合下，并不是所有类型都被使用，如果没有被使用，可以用Java.lang.Void类型代替，Progress用Integer，其他的参数需要以大写开头

一个异步任务的执行一般包括以下几个步骤：


1.execute(Params... params)，执行一个异步任务，需要我们在代码中调用此方法，触发异步任务的执行。

2.onPreExecute()，在execute(Params... params)被调用后立即执行，一般用来在执行后台任务前对UI做一些标记。比如显示开始任务

3.doInBackground(Params... params)，在onPreExecute()完成后立即执行，用于执行较为费时的操作，此方法将接收输入参数和返回计算结果。在执行过程中可以调用publishProgress(Progress... values)来更新进度信息。

4.onProgressUpdate(Progress... values)，在调用publishProgress(Progress... values)时，此方法被执行，直接将进度信息更新到UI组件上。

5.onPostExecute(Result result)，当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。


## 2.深入研究

AsyncTask主要有两个部分。一通过Handler与主线程交互，二线程的管理调度。

下面主要讲线程的管理调度。

在同一个进程中的所有AsyncTask的实例是属于进程共享的，因此在同一个进程中AsyncTask控制着进程范围内的所有子类实例。
在Android2.3以前，内部的线程池限制是5个，因此同时只能有5个线程同时运行。
Android 3.0以后。
* 通过execute()提交的任务会按先后顺序执行，一次只能运行一个
* 新增executeOnExecutor(),里面的参数为Executors.newCachedThreadPool()可为无限制的线程数，参数为AsyncTask.THREAD_POOL_EXECUTOR，线程数为5，参数为AsyncTask.SERIAL_EXECUTOR，线程数为1，效果和execute()一样