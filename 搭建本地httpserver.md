# 使用NanoHTTPD搭建android本地httpserver

搭建本地httpserver是为了解决android不支持本地ajax访问(当然可以通过对webview的设置也可以支持本地ajax访问，见文章末尾)

[NanoHTTPD架包下载地址]()

## 1. 启动httpserver


```
// 在一个子线程中启动这个httpserver
mhttpserver = HttpServer.getHttpServer(8080, MainActivity.this);
try {
                    mhttpserver.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
```

## 2. 销毁httpserver

```
@Override
    protected void onDestroy() {
        super.onDestroy();
        if (mhttpserver != null) {
            mhttpserver.stop();
        }
    }
```

## 3. httpserver实例

```
public class HttpServer extends NanoHTTPD {

    private static HttpServer httpServerinstance = null;

    public static HttpServer getHttpServer(int port, Context context) {
        if (null == httpServerinstance) {
            httpServerinstance = new HttpServer(port, context);
        }
        return httpServerinstance;
    }

    private HttpServer(int port, Context context) {
        super(port);
    }

    @Override
    public Response serve(IHTTPSession session) {

        Log.i("lsw", session.getUri());
        return responseCourseStream(session);
    }


    public Response responseCourseStream(IHTTPSession session) {

        String uri = session.getUri();
        String mime = getMimeTypeForFile(uri);
        Log.i("lsw", "mime:" + mime);
        uri = uri.substring(1);
        try {
//            InputStream is = mContext.getResources().getAssets().open(uri);
// 根据不同的路径进行修改
            FileInputStream is = new FileInputStream(Environment.getExternalStorageDirectory() + "/ubcoll/game/" + uri);
            return newChunkedResponse(Response.Status.OK, mime, is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return newFixedLengthResponse("404");
    }
}
```

## 4. webview调用

```
// path 需与3中的路径连接起来，连成完整路径即可
// 比如这里的path只传index.html,3中的路径就要指到index.html父目录的路径为止
wv.loadUrl("http://localhost:8080/" + path);
```

## 5. 设置webview，支持本地ajax访问

```
//解决XMLHttpRequest cannot load file from android asset folder
ws.setJavaScriptEnabled(true);
ws.setPluginState(PluginState.ON);
ws.setAllowFileAccess(true);
ws.setAllowContentAccess(true);
ws.setAllowFileAccessFromFileURLs(true);
ws.setAllowUniversalAccessFromFileURLs(true);
```




