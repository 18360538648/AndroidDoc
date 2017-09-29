# android广播

## 广播的特点和意义
广播的特点是只管发送，不管有没有接收和怎样处理数据。广播引入的意义是解决跨进程通讯的，比如屏幕亮度变化，手机中的的应用根据这个变化作出相应改变。

## 1. 基础使用

### 1.1 广播接受者

```
private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if(action.equals("refresh")){
            }
        }
    };
```

### 1.2 动态注册广播

```
private void registerBoradcastReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("refresh");
        registerReceiver(receiver, intentFilter);
    }
```

```
// 发送广播
Intent mIntent = new Intent("refresh");
sendBroadcast(mIntent);
```

优点：优先级比较高，销毁以后不耗资源
缺点：生命周期结束以后，就无法监听了
### 1.3 静态注册广播
在Mainifast中注册

优点：应用关闭以后，还可以监听
缺点：耗cpu和内存

