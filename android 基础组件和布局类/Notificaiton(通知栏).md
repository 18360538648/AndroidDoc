# Notificaiton(通知栏)

Notificaiton在应用中运用场景很多，需要对其很了解

## 1. 认识Notificaiton布局

![](http://developer.android.com/images/ui/notifications/normal_notification_callouts.png)

1. 标题   Title/Name
2. 大图标  Icon/Photo
3. 内容文字   
4. 内容信息   MESSAGE
5. 小图标 Secondary Icon
6. 通知的时间 Timestamp,默认为系统发出通知的时间，也可通过setWhen()来设置



## 2. 创建Notificaiton

Notificaiton需要用到Notification和NotificationManager两个类

Notification为通知信息类，它里面对应了通知栏的各个属性

NotificationManager为状态栏通知的管理类，负责发通知、清除通知等操作。
## 2.1 获取状态通知栏管理

```
NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
```

## 2.2 实例化通知栏构造器NotificationCompat.Builder

```
NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);  
```

## 2.3 对Builder进行配置

```
notificationCompat.setContentTitle("通知栏")// 通知栏标题
                  .setWhen(System.currentTimeMillis())// 设置通知产生的时间
                  .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                  .setOngoing(true)设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                  .setSmallIcon(R.mipmap.ic_launcher)
                  .setDefaults(Notification.DEFAULT_SOUND);// Notification.DEFAULT_VIBRATE(震动提醒)
```

## 2.4 显示通知栏

```
mNotificationManager.notify(notifyId, mBuilder.build());
```


## 3. Notificaiton的扩展

## 3.1 利用Notificaiton实现更新进度

```
// max为进度条最大数值
// progress:当前进度
// indeterminate:表示进度是否不确定,在进度可监测的情况下，设置为false，不可检测的情况下，设置为true,这种情况下，开始时调用setProgress(0,0,true),结束时调用setProgress(0, 0, false)
setProgress(int max, int progress,boolean indeterminate)
```
这里有一个注意点，progress不能变化的过于平凡，需要对其控制次数，从1到100的持续变化，暂时还能接受，如果在某个进度点持续的相同也会持续的发消息，这样导致更新的次数不止100次，这样整个系统都会很卡

## 3.2 自定义Notificaiton

## 3.3 点击事件

```
setContentIntent(PendingIntent intent)
```

如点击通知栏跳转到指定的页面

```
Intent intent = new Intent(context,XXX.class);  
PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);  
mBuilder.setContentIntent(pendingIntent) 
```
PendingIntent的使用见PendingIntent的文章





