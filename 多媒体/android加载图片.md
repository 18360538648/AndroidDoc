# android加载图片

## 1. 创建Bitmap对象

### 1.1 BitmapFactory.decodeFile()

用于加载sd卡的图片

### 1.2 BitmapFactory.decodeFile()

用于加载sd卡的图片

### 1.3 BitmapFactory. decodeResource()

用于加载资源文件中的图片

## 2. 避免OOM

### 2.1 使用图片压缩技术

上述的方法，很容易造成OOM，为此每一种解析方法都提供了一个可选的BitmapFactory.Options参数，将这个参数的inJustDecodeBounds属性设置为true就可以让解析方法禁止为bitmap分配内存，返回值也不再是一个Bitmap对象，而是null。虽然Bitmap是null了，但是BitmapFactory.Options的outWidth、outHeight和outMimeType属性都会被赋值

```
BitmapFactory.Options options = new BitmapFactory.Options();  
options.inJustDecodeBounds = true;  
BitmapFactory.decodeResource(getResources(), R.id.myimage, options);  
int imageHeight = options.outHeight;  
int imageWidth = options.outWidth;  
String imageType = options.outMimeType; 
```

由于在上面知道了图片的相关属性，此时就可以对图片进行压缩了。下面是一个压缩函数

```
public static int calculateInSampleSize(BitmapFactory.Options options,  
        int reqWidth, int reqHeight) {  
    // 源图片的高度和宽度  
    final int height = options.outHeight;  
    final int width = options.outWidth;  
    int inSampleSize = 1;  
    if (height > reqHeight || width > reqWidth) {  
        // 计算出实际宽高和目标宽高的比率  
        final int heightRatio = Math.round((float) height / (float) reqHeight);  
        final int widthRatio = Math.round((float) width / (float) reqWidth);  
        // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高  
        // 一定都会大于等于目标的宽和高。  
        inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;  
    }  
    return inSampleSize;  
}  
```

通过上面的函数可得到一个合适的inSampleSize，这时对options的inSampleSize进行设置

```
public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,  
        int reqWidth, int reqHeight) {  
    // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小  
    final BitmapFactory.Options options = new BitmapFactory.Options();  
    options.inJustDecodeBounds = true;  
    BitmapFactory.decodeResource(res, resId, options);  
    // 调用上面定义的方法计算inSampleSize值  
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);  
    // 使用获取到的inSampleSize值再次解析图片  
    options.inJustDecodeBounds = false;  
    return BitmapFactory.decodeResource(res, resId, options);  
}  
```

// 将任意一张图片压缩为100*100的图片

```
mImageView.setImageBitmap(  
    decodeSampledBitmapFromResource(getResources(), R.id.myimage, 100, 100));  
```
### 2.2 使用图片缓存技术

内存缓存技术对那些大量占用应用程序宝贵内存的图片提供了快速访问的方法。其中最核心的类是LruCache (此类在android-support-v4的包中提供) 。这个类非常适合用来缓存图片，它的主要算法原理是把最近使用的对象用强引用存储在 LinkedHashMap 中，并且把最近最少使用的对象在缓存值达到预设定值之前从内存中移除。

创建LruCache对象，并设置其大小

```
private LruCache<String, Bitmap> mMemoryCache;
int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
```

将图片加入LruCache中

```
// 将图片对象加入缓存空间中
    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemoryCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }

    }
```

将图片对象从缓存空间中取出

```
// 将图片对象从缓存空间中取出
    public Bitmap getBitmapFromMemoryCache(String key) {
        return mMemoryCache.get(key);
    }
```

上面的具体列子见PicCache例子



