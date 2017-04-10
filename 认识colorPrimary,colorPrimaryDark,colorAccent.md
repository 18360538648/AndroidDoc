# 认识colorPrimary,colorPrimaryDark,colorAccent

最近看别人的项目，想改别人项目的控件的颜色，花了很长时间才找到设置颜色的地方，原来是在样式中设置的
其中有三个重要的属性colorPrimary,colorPrimaryDark,colorAccent

# 1. 各自对应的部位

colorPrimary为导航栏颜色

colorPrimaryDark为系统状态栏的颜色

colorAccent为控件选中颜色

![](http://img.blog.csdn.net/20160708140705717?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast)


# 2. 代码

```
// style.xml
<style name="AppTheme" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
```


```
// color.xml
<color name="colorPrimary">#3F51B5</color>
    <color name="colorPrimaryDark">#303F9F</color>
    <color name="colorAccent">#EE9A00</color>
```


