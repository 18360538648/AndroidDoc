# Android的WebView深入探究



## 1. 不同Android版本的WebView区别

在Android 4.4以下(不包含4.4)系统WebView底层实现是采用WebKit(http://www.webkit.org/)内核，

而在Android 4.4及其以上Google 采用了chromium(http://www.chromium.org/)作为系统WebView的底层内核支持。

参考链接 :[Android 各个版本WebView](http://blog.csdn.net/typename/article/details/40425275)

## 2. WebView的基础设置

```
ws.setJavaScriptEnabled(true);
ws.setBuiltInZoomControls(false);// 显示缩放按钮
ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);// 排版适应屏幕
ws.setUseWideViewPort(false);// 可任意比例缩放
```

## 3. WebViewClient和WebChromeClient

* WebViewClient主要帮助WebView处理各种通知、请求事件
* 主要辅助WebView处理JavaScript的对话框、网站图标、网站title、加载进度



## 3. WebView的拦截请求(重写WebViewClient方法)

```
@Override
        public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
        WebResourceResponse response = null;
        response = InterceptUtils.interceptPic("i/student/homepage/video.jpg", url,
                    ClientActivity.this, response);
        return response;
        }
```


```
// 拦截图片
    public static WebResourceResponse interceptPic(String interceptFileName, String url,
            Context context,
            WebResourceResponse response) {

        if (url.contains(interceptFileName)) {
            try {
                String name = url.substring(url.lastIndexOf(".") + 1);
                Log.i(interceptFileName, "来自本地" + name);
                InputStream localCopy = context.getAssets()
                        .open("steam/" + interceptFileName);
                // FileInputStream inputStream = new FileInputStream(
                // new File(Environment.getExternalStorageDirectory().getPath()
                // + "/html1/cover_piano.jpg"));
                response = new WebResourceResponse("image/jpeg", "UTF-8", localCopy);
                Log.i(interceptFileName, response.toString());
            } catch (Exception e) {
                Log.i(interceptFileName, e.toString());
            }
        }
        return response;
    }
```
## 3. 遇到的问题以及解决方案：
## 3.1 退出webview以后还有声音

```
webView.loadUrl("about:blank");
        webView.stopLoading();
        webView.setWebChromeClient(null);
        webView.setWebViewClient(null);
        webView.destroy();
        webView = null;
```

## 3.2 XMLHttpRequest cannot load file from android asset 
由于Chromium不支持本地ajax访问，通过对webview设置一些属性即可解决这个问题

```
ws.setJavaScriptEnabled(true);
ws.setPluginState(PluginState.ON);
ws.setAllowFileAccess(true);
ws.setAllowContentAccess(true);
ws.setAllowFileAccessFromFileURLs(true);
ws.setAllowUniversalAccessFromFileURLs(true);
```



## 3.3 有些机子出现第二次连续打卡webview空白现象

### 3.3.1 先设置软件绚烂

```
<WebView
        android:id="@+id/wv"
        android:layerType="software"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></WebView>
```

### 3.3.2 在页面完成加载时，开启硬件加速(不可取)

```
wv.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                wv.setLayerType(View.LAYER_TYPE_HARDWARE, null);
            }
        });
```
```

## 3.4 长按出现复制粘贴框

```
mWebView.setOnLongClickListener(new OnLongClickListener() {  
            
          @Override  
          public boolean onLongClick(View v) {  
              return true;  
          }  
      }); 
```

## 3.3 有些机子出现播放本地音频无效

在做项目时，发现有些机子在播放本地音频无效，但是播放在线音频有效。
尽量写浏览器兼容的写法(这种做法网上有人说也没用)，这是webview的对audio标签的支持问题。最后想了一个绕行方法，通过js调用本地的音频播放方法，这样就正常了

```
<audio controls="controls">  
<source src="song.ogg" type="audio/ogg">  
<source src="song.mp3" type="audio/mpeg">  
Your browser does not support the audio tag.  
</audio>  
```

```
// 这里加入map进行时间判断，使为了解决短时间内访问同一个资源，造成资源没有准备的问题
private SoundPool soundPool = new SoundPool(100, AudioManager.STREAM_MUSIC, 0);
@JavascriptInterface
        public void nativePlayAudio(String audioName) {
            if (audioName != null) {
                if (map.containsKey(audioName)) {
                    long lasttime = (long) map.get(audioName);
                    if (System.currentTimeMillis() - lasttime > 300) {
                        if (path != null) {
                            map.put(audioName, System.currentTimeMillis());
                            File audio = new File(path + "/" + audioName);
                            if (audio.exists()) {
                                final int soundID1 = soundPool.load(path + "/" + audioName, 1);
// 一定要加入准备完成函数，否则会导致无法播放
soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                    @Override
                                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
// param1 :声音源
// param2 :左声道
// param3 :右声道
// param4 :优先级
// param5 :-1，为无限重复，0，为一次，
// param5 :速度(0-1.0f)
soundPool.play(soundID1, 1.0f, 1.0f, 100, 0, 1.0f);
                                    }
                                });

                            }
                        }
                    }

                } else {
                    if (path != null) {
                        map.put(audioName, System.currentTimeMillis());
                        File audio = new File(path + "/" + audioName);
                        if (audio.exists()) {
                            final int soundID1 = soundPool.load(path + "/" + audioName, 1);
                            soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                                @Override
                                public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                                    soundPool.play(soundID1, 1.0f, 1.0f, 100, 0, 1.0f);
                                }
                            });

                        }
                    }
                }

            }


        }
```

