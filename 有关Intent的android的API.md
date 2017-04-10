# 有关Intent的android的API

## 1. Intent

Intent(意图)，这在android中十分常用，常用于激活系统组件(如不同的Activity，broadcast和Service）和在系统组件之间传递数据

```
Intent intent = new Intent();
intent.setClass(MainActivity.this, SecondActivity.class);
intent.putExtra("url", url);
// 通过String url=getIntent().getExtras().getString("url");取值
startActivity(intent);
```
分类：

* 显式意图：调用Intent.setComponent()或Intent.setClass()方法明确指定了组件名的Intent为显式意图，显式意图明确指定了Intent应该传递给哪个组件。（如上面的代码）

* 隐式意图：没有明确指定组件名的Intent为隐式意图。 Android系统会根据隐式意图中设置的动作(action)、类别(category)、数据（URI和数据类型）找到最合适的组件来处理这个意图。比如装APK，打电话的操作

## 2. PendingIntent（等待的，未决定的Intent）

PendingIntent 可以看作是对intent的包装，通常通过getActivity,getBroadcast ,getService来得到pendingintent的实例，当前activity并不能马上启动它所包含的intent,而是在外部执行 pendingintent时，调用intent的

```
// requestCode 区分不同的intent
// flags 
1. FLAG_CANCEL_CURRENT 如果当前系统中已经存在一个相同的PendingIntent对象，那么就将先将已有的PendingIntent取消，然后重新生成一个PendingIntent对象
2. FLAG_NO_CREATE:如果当前系统中不存在相同的PendingIntent对象，系统将不会创建该PendingIntent对象而是直接返回null
3. FLAG_ONE_SHOT:该PendingIntent只作用一次。在该PendingIntent对象通过send()方法触发过后，PendingIntent将自动调用cancel()进行销毁，那么如果你再调用send()方法的话，系统将会返回一个SendIntentException
4. FLAG_UPDATE_CURRENT:如果系统中有一个和你描述的PendingIntent对等的PendingInent，那么系统将使用该PendingIntent对象，但是会使用新的Intent来更新之前PendingIntent中的Intent对象数据，例如更新Intent中的Extras。
PendingIntent.getActivity（Context context, int requestCode, Intent intent, int flags）
```

