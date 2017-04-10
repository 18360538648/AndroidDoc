Android ButterKnife的使用

ButterKnife的引入是为了不用繁琐的findViewById,提高开发效率

# 1. ButterKnife引入

## 1.1 在project中的build.gradle中加入

```
classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
```

## 1.2 在module中的build.gradle的开头加入

```
apply plugin: 'android-apt'
```

## 1.3 在module中的build.gradle的dependencies标签中添加

```
compile 'com.jakewharton:butterknife:8.4.0'
apt 'com.jakewharton:butterknife-compiler:8.4.0'
```

## 2. 如何使用

在Activity中申明

```
ButterKnife.bind(this);
```

在Fragment中申明

```
//绑定fragment
ButterKnife.bind( this , view ) ;
```

###  2.1  @BindView（）

```
@BindView( R.id.button1 )
public Button button1 ;
```

###  2.2  @BindViews（）

```
@BindViews({ R.id.button1  , R.id.button2 ,  R.id.button3 })
public List<Button> buttonList ;

// 对应的使用
buttonList.get( 0 ).setText( "hello 1 ");
```

###  2.3  @OnClick( )

```
@OnClick(R.id.button1 )   //给 button1 设置一个点击事件
public void showToast(){
  Toast.makeText(this, "is a click", Toast.LENGTH_SHORT).show();
}
```
## 3. 快捷生成

有一个zelezny的插件，可以不用写代码，自动生成当前布局中的寻找布局代码

## 3.1 zelezny插件安装

![](http://img.blog.csdn.net/20161031141739545)

![](http://img.blog.csdn.net/20161031141833705)

插件完成以后，会提示重启AS，重启之后，将光标移动到setContentView()函数，右键Generate，选择ButterKnife zelezny可以自动生成寻找控件代码


[butterknife](http://jakewharton.github.io/butterknife/)
