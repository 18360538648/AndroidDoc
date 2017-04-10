Android 布局篇

由于自工作以来自己写的布局很少，导致自己写布局的能力很差劲，只会一些特别基础的。通过金融助手这个项目我发现自己的写布局的能力太差了，一定要加强训练。在此做些笔记，记录写布局的心得。

## android:gravity=""和android:layout_gravity=""的区别

android:gravity=""是针对控件中的元素，用于控制控件元素在该控件里面的显示位置，如可以设置按钮中的文字处于按钮的左边

```
android:gravity="left"
```

android:layout_gravity=""是针对控件的本身而言，用于该控件在其父控件的显示位置

## 在EditText中插入图片

```
android:drawableLeft="@drawable/account"
```

## 使并列的控件平分并列的空间大小

// 这个针对LinearLayout有效，对于RelativeLayout无效

```
android:layout_weight="1"
```

## 如何画一条横线

```
<View
android:layout_width="match_parent"
android:layout_height="1dp">
</View>
```

## 图片scaleType属性

* center 如果图片大于ImageView的范围，则取图片中间的位置，在ImageView中显示；如果图片小于ImageView的范围，在ImageView的中间直接显示
* centerCrop 如果图片大于ImageView的范围，等比例的缩小该图片的宽高，直到有一边等于ImageView的的宽高，最后用ImageView的大小去裁剪改图片；如果图片的小于ImageView，会等比例的扩大ImageView的大小，直到有一边等于ImageView的的宽高，最后用ImageView的大小去裁剪改图片。
* fitCenter 将图片按比例扩大(缩小)ImageView的宽度，居中显示
* centerInside 当图片大于ImageView的宽和高，等比例的缩放，直到将图片的内容完整的居中显示；当小于ImageView的宽高，直接居中显示图片
* fitStart 将图片按比例扩大(缩小)ImageView的宽度，在ImageView的上方显示
* fitEnd 将图片按比例扩大(缩小)ImageView的宽度，在ImageView的下方显示
* fitXY 扩大或者收缩，不保持比例，填满ImageView

  [scaleType属性参考链接](http://blog.csdn.net/lirui0822/article/details/38423423)
  
  
 ## layout_alignBottom和layout_alignParentBottom
 
 * layout_alignParentBottom是控件的底部与父控件的底部对齐
 
 * layout_alignBottom是控件的底部与指定控件的底部对齐

 
 ## 常用控件局中
 
 ```
 // 注意不能再其中再套一层布局，否则会失效
 <RelativeLayout
        android:id="@+id/exercise_title"
        android:layout_width="match_parent"
        android:layout_height="50dp"
        android:background="@color/colorPrimary"
        android:orientation="horizontal">

        <ImageButton
            android:id="@+id/ibtn_exercise_back"
            android:layout_width="20dp"
            android:layout_height="20dp"
            android:layout_centerVertical="true"
            android:layout_marginLeft="10dp"
            android:background="@color/colorPrimary"
            android:scaleType="fitXY"
            android:src="@drawable/back" />

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:text="课后练习"
            android:textColor="#ffffff"
            android:textSize="20sp" />
    </RelativeLayout>
 ```
 
 
  ## layout_weight针对LinearLayout有效,而对RelativeLayout无效
 
 

