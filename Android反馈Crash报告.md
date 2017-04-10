# Android反馈Crash报告

## 做Anroid应用程序，尽量避免Crash，自己测或者不会出现Crash的现象，当投放到市场中，就很可能出现Crash现象，基于这个原因，一般的应用程序，都有一个Crash反馈的机制，程序员可以更加反馈意见的结果，对当前的代码进行更改，使发布的下一个版本更加稳定。

## 1. 使用Thread.UncaughtExceptionHandler

当 Thread 因未捕获的异常而突然终止时，调用处理程序的接口

```
public class ThreadException implements Thread.UncaughtExceptionHandler {

    private static String LOGTAG = "exception";
    private Context context;
    private Thread.UncaughtExceptionHandler exceptionHandler;
    // 异常信息
    private static String exceptionStr = "";
    // 保存异常信息的文件路径
    private static String exceptionStorgeFileName = "";

    public ThreadException(Context context) {
        context = null;
        exceptionHandler = null;
        this.context = context;
        exceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
    }

    public void uncaughtException(Thread thread, Throwable ex) {
        StringWriter stringwriter = new StringWriter();
        PrintWriter printwriter = new PrintWriter(stringwriter);
        ex.printStackTrace(printwriter);
        exceptionStr = stringwriter.toString();
        printwriter.close();
        exceptionStorgeFileName = BasicUtil.statExFileName(context);
        BasicUtil.dataToFile(exceptionStorgeFileName, exceptionStr);
        exceptionHandler.uncaughtException(thread, ex);
    }
// 将异常信息写入json数据中，以便传给服务器
    public static JSONObject getExceptionJsonObject(String session) {
        JSONObject jsonobject = null;
        try {
            // 返回异常信息
            if (TextUtils.isEmpty(exceptionStr)) {
                exceptionStr = BasicUtil.dataToRAM(exceptionStorgeFileName);
            }
            if (!TextUtils.isEmpty(exceptionStr) && !TextUtils.isEmpty(session)) {
                jsonobject = new JSONObject();
                jsonobject.put(session, exceptionStr);
            }
            exceptionStr = "";
        } catch (Exception exception) {
            LogUtil.log(LOGTAG, exception);
        } catch (Error error) {
            LogUtil.log(LOGTAG, error);
        }
        return jsonobject;
    }

}

```


## 2. 使用Thread.UncaughtExceptionHandler

```
Thread.setDefaultUncaughtExceptionHandler(
                        new ThreadException(mContext));
```

