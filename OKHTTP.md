# OKHTTP

## 1. OKHTTP Get请求

```
Request request = new Request.Builder().url(urlAddress).build();
request.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lsw", "get请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.i("lsw", "get请求失败");
            }
        });
```

## 2. OKHTTP Post请求

```

public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
// 放入String类型的json数据， 通过create方法可以转换为标准json数据
RequestBody body = RequestBody.create(JSON, value);
Request request = new Request.Builder().url(urlAddress).post(body).addHeader("Content-Type", "application/json;charset=utf-8").build();
request.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lsw", "post请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) {
                Log.i("lsw", "post请求失败");
            }
        });
```

## 3. OKHTTP 监听进度

相应的监听函数在okhttp工程中
[github地址](https://github.com/18360538648/okhttpdemo)

```

ProgressHelper.addProgressResponseListener(clickOKHttpClient, new UIProgressListener() {
                    @Override
                    public void onUIProgress(long currentBytes, long contentLength, boolean done) {
                        Log.i("lsw", "下载更新进度");
                        int process = (int) ((100 * currentBytes) / contentLength);
                        if (contentLength != -1) {
                            //长度未知的情况下回返回-1
                            Log.e("TAG", (100 * currentBytes) / contentLength + "% done");
                        }
                    }

                    @Override
                    public void onUIFinish(long currentBytes, long contentLength, boolean done) {
                        super.onUIFinish(currentBytes, contentLength, done);
                       // 监听结束时, 这与实际的完成情况还是有点差距的，如果要做到完全正确的监听，可以在下载完成以后，进行相应的UI操作
                    }

                    @Override
                    public void onUIStart(long currentBytes, long contentLength, boolean done) {
                        super.onUIStart(currentBytes, contentLength, done);
                        // 监听开始时
                    }
                }
        ).newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("lsw", "文件下载失败")；
            }

            @Override
            public void onResponse(Call call, Response response) {
            Log.i("lsw", "文件下载成功")；
               }
           }
        });
```

扩展链接

[扩展链接1](http://blog.csdn.net/qq_19431333/article/details/53141013)
[扩展链接2](https://gold.xitu.io/entry/5769f978d342d300580f4328)


## 4. OKHTTP 上传多个文件

```
MultipartBody.Builder mbody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("type", "" + type).addFormDataPart("user_id", "" + userId).addFormDataPart("note", "" + note);
        for (String fileName : photos) {

            File mediaFile = new File(fileName);
            Log.i("lsw", "mediaFile:" + mediaFile.getName());
            mbody.addFormDataPart("file" + i, mediaFile.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), mediaFile));
            i++;
        }
        RequestBody requestBody = mbody.build();
        Request request = new Request.Builder()
                .url(urlAddress)
                .post(requestBody)
                .build();
```

## 5. OKHTTP支持https

```
private static OkHttpClient mOKHttpClient = new OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .sslSocketFactory(createSSLSocketFactory())
            .hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            }).build();
```

```
private static SSLSocketFactory createSSLSocketFactory() {
        SSLSocketFactory ssfFactory = null;

        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, new TrustManager[]{new TrustAllCerts()}, new SecureRandom());

            ssfFactory = sc.getSocketFactory();
        } catch (Exception e) {
        }

        return ssfFactory;
    }
```

```
public class TrustAllCerts implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}

```





