# Service

Service用于在后台做耗时操作

[代码库地址](https://github.com/18360538648/ServiceDemo)

## 1.基本使用

### 1.1 创建Service类
```
// 创建一个Service，记得在Mainifest中注册一下
public class MyService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("lsw", "MyService onCreate");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("lsw", "MyService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("lsw", "MyService onDestroy");
    }
}
```

### 1.2 启动Service

```
Intent intent = new Intent(this, MyService.class);
stopService(intent);
```

### 1.3 暂停Service

```
Intent intent = new Intent(this, MyService.class);
stopService(intent);
```

### 1.4 生命周期

oncreate -> onStartCommand -> ondestory

其中如果多次启动同一个service，oncreate只会第一次运行，之后不再运行

## 2.和对应的Activity绑定

### 2.1 绑定与声明

```
// 增加bind类
public class MyService extends Service {
    public MyBind myBind = new MyBind();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("lsw", "MyService onCreate");
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i("lsw", "MyService onBind");
        return myBind;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i("lsw", "MyService onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.i("lsw", "MyService unbindService");
        super.unbindService(conn);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("lsw", "MyService onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("lsw", "MyService onDestroy");
    }

    public class MyBind extends Binder {
        public void doDown() {
            Log.i("lsw", "下载任务----");
        }
    }
}
```

```
// 增加ServiceConnection
public class MainActivity extends AppCompatActivity {
    private MyService.MyBind myBind;
    ServiceConnection serviceConnection = new ServiceConnection() {
        // 连接
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            myBind = (MyService.MyBind) iBinder;
            myBind.doDown();
        }

        // 取消连接
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * 开始Service
     *
     * @param view
     */
    public void startService(View view) {
        Intent intent = new Intent(this, MyService.class);
        startService(intent);
    }

    /**
     * 结束Service
     *
     * @param view
     */
    public void stopService(View view) {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }

    /**
     * 绑定Service
     *
     * @param view
     */
    public void bindService(View view) {
        Intent intent = new Intent(this, MyService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

    /**
     * 解绑Service
     *
     * @param view
     */
    public void unbindService(View view) {
        unbindService(serviceConnection);
    }
}
```

### 2.2 生命周期
oncreate -> onbind -> unOnbind -> onDestory
一个Activity与一个service只能绑定一次

### 3 startService和bindService

如果在startService之后又bindService，此时如果stopService是无法销毁这个Service的，只有将这个Service unOnbind()之后才能销毁

### 4.Service和Thread的关系

两者没有关系，Service是运行于后台的处理耗时操作的，其实其是运行于主线程的，所以可以在onstartcommand或者 在自定义binder中new一个线程。而Thread是运行于子线程的。


