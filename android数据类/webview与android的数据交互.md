# WebView与Android的数据交互
# 1. 申明权限

```
<uses-permission android:name="android.permission.INTERNET"/>
```

# 2. 将写好的Webview相关文件放在assets目录下面

通过如下代码引用(file:///android_asset/为固定格式不可改变)

```
 webview.loadUrl("file:///android_asset/index.html");
```

# 3. WebView相关属性申明

```
//设置WebView属性，能够执行Javascript脚本
webview.getSettings().setJavaScriptEnabled(true);
//Android与js交互的接口函数，具体使用请见5. WebView传递数据到native中
//addJavascriptInterface方法有两个参数，第一个参数就是我们一般会实现一个自己的类，类里面提供我们要提供给javascript访问的方法；
//第二个参数是访问我们在obj中声明的方法时候所用到的js对象，调用模式为window.interfaceName.方法名()
webview.addJavascriptInterface(new MyObject(), "displayFormJs");
```

# 4. Android传递数据到WebView中

调用js中申明的函数并传入需要传递的数据(其中的格式为“javascript:<Android需要调用js中的函数名>('"+传递参数+"‘)”)

```
webview.loadUrl("javascript:displayFormNative(' " + displaytext + " ')");
```
js中的代码

```
//显示native传递过来的数据
function displayFormNative(arg){
	 document.getElementById("content").innerHTML +=   
         ("<br\>"+arg);
}
```

![native传递数据到WebView中](http://chuantu.biz/t5/35/1474886953x988815626.png)
# 5. WebView传递数据到Android中

```
function summitInftonative() {
		  var text = document.getElementById("displayinnative").value;
		  //将WebView数据传递到Android中，并得到Android返回值,显示到WebView页面上
	      var result =window.displayFormJs.displaytextView(text);
		  alert(result);
		}
```

```
class MyObject {
        @JavascriptInterface
        public String displaytextView(final String text) {
             String tohtml=null;
            if(text.equals("pn")){
                tohtml=getAppInfo();
            }
            return tohtml;

        }
    }
```
```
//获取应用包名
    private String getAppInfo() {
        try {
            String pkName = this.getPackageName();
            return pkName ;
        } catch (Exception e) {
        }
        return null;
    }
```
![数据传递](http://chuantu.biz/t5/35/1474954254x988815626.png)
![数据传递](http://chuantu.biz/t5/35/1474954597x988815626.png)

