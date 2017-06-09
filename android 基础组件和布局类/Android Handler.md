# Android Handler

在Android中，子线程处理耗时操作，更新UI只能在主线程中，在子线程中处理完任务以后，怎样通知UI线程更新UI呢？此时就要用到Handler了

## 重要知识点

* Message:消息，其中包含了消息ID，消息处理对象以及处理的数据等，由MessageQueue统一列队，终由Handler处理。
* Handler:处理者，负责Message的发送及处理
* MessageQueue:消息队列
* Looper:消息泵，不断地从MessageQueue中抽取Message执行
* Thread:线程，负责调度整个消息循环，即消息循环的执行场所

## 使用

```
// 申明handler
public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            //不传递数据
            msg.what
            //传递数据
            Bundle b = msg.getData();
            int flag = b.getInt("flag");
            String message = b.getString("result");
            if (flag == Const.HANDLERCANCEL) {     
        }
    };
```

```
// 使用handler
// 注意这是android.os的Message，且Message每次使用时都要new一个新的message
android.os.Message message = android.os.Message.obtain();
            Bundle data = new Bundle();
            // 带数据
            data.putString("result", result);
            data.putInt("flag", Const.HANDLERUPZIPSUCCESS);
            data.putString("app", appIDUtils);
            message.setData(data);
            handler.sendMessage(message);
            //不带数据
            //Const.handler.sendEmptyMessage(1);
```