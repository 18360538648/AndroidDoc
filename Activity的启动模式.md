# Activity的启动模式

在Android中每个界面都是一个Activity，切换界面操作其实是多个不同Activity之间的实例化操作。在Android中Activity的启动模式决定了Activity的启动运行方式。一共有四种启动模式

```
<activity android:name=".MainActivity" android:launchMode="standard" />
```

## 1. Standard(默认)

默认启动模式，每次激活Activity时都会创建Activity，并放入任务栈中。

## 2. SingleTop(栈顶复用模式)

如果在任务的栈顶正好存在该Activity的实例， 就重用该实例，否者就会创建新的实例并放入栈顶。
比如有两个Activity名为B1，B2，两个Activity 的内容一样，都有两个按钮可以跳到B1和B2。唯一的不同是B1为standard，B2为singleTop。当打开的顺序为B1->B2->B2，实际打开的顺序为B1->B2（后一次意图打开B2，实际只调用了前一个的onNewIntent方法）；若打开的顺序为B1->B2->B1->B2，则实际打开的顺序与意图的一致，为B1->B2->B1->B2。

应用场景：比如点击通知栏的信息会启动一个Activity，如果此时再有消息来的，点击时又会出现一个新的Activity,这样就造成了浪费，设置这个Activity为SingleTop就不会有这个问题了

## 3. SingleTask

任务栈中只有一个实例，在同一个应用程序中启动他的时候，若Activity不存在，则会在当前task创建一个新的实例，若存在，则会把task中在其之上的其它Activity destory掉并调用它的onNewIntent方法

例子暂时没有找到

## 4. SingleInstance(单一实例模式)

只有一个实例，并且这个实例独立运行在一个task中，这个task只有这个实例，不允许有别的Activity存在

程序有三个ActivityD1,D2,D3，三个Activity可互相启动，其中D2为singleInstance模式，那么程序从D1开始运行，假设D1的taskId为200，那么从D1启动D2时，D2会新启动一个task，即D2与D1不在一个task中运行。假设D2的taskId为201，再从D2启动D3时，D3的taskId为200，也就是说它被压到了D1启动的任务栈中。

应用场景：适合需要与程序分离开的页面,呼叫来电界面,闹铃提醒