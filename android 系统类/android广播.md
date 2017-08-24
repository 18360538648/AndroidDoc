# android广播

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

### 1.3 发送广播

```
Intent mIntent = new Intent("refresh");
sendBroadcast(mIntent);
```