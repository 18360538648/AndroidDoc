# Android沉浸式状态栏

这个特效是从android4.4(API19)开始使用的


## 1. 在布局文件的最外层Layout上加上两个属性

这是为了防止标题栏整体往上移动，占据状态栏的空间

```
android:clipToPadding="true"
android:fitsSystemWindows="true"
```

## 2. setContentView方法之后加入两个方法
```
//透明状态栏  
getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//透明导航栏也可以做getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
```

## 3. setContentView方法之前加入设置无标题栏方法

```
requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题栏
```