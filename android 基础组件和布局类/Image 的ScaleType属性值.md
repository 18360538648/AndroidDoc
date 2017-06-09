# Image 的ScaleType属性值

## 1. android:scaleType="center"

* 图片大于ImageView的宽高：以图片的中心点和ImageView的中心点为基准，按照图片的原大小居中显示，不缩放，用ImageView的大小截取图片的居中部分。
* 当图片小于ImageView的宽高：直接居中显示该图片

## 2. android:scaleType="centerCrop"

ps(crop 为修剪的意思)

* 当图片大于ImageView的宽高：以图片的中心点和ImageView的中心点为基准，按比例缩小图片，直到图片的宽高有一边等于ImageView的宽高，则对于另一边，图片的长度大于或等于ImageView的长度，最后用ImageView的大小居中截取该图片。
* 当图片小于ImageView的宽高：以图片的中心店和ImageView的中心点为基准，按比例扩大图片，直到图片的宽高大于或等于ImageView的宽高，并按ImageView的大小居中截取该图片。

## 3. android:scaleType="centerInside"

* 当图片大于ImageView的宽高：以图片的中心和ImageView的中心点为基准，按比例缩小图片，使图片宽高都等于或者小于ImagevView的宽高，直到将图片的内容完整居中显示。（注意为都小于或等于,所以这种图片scaleType的用户体验是最好的，又能完整展示，又能按比例来，不会出现拉伸的情况）
* 当图片小于ImageView的宽高：直接居中显示该图片。

## 4. android:scaleType="fitCenter"

* 把图片按比例扩大（缩小）到ImageView的宽度，居中显示（注意是宽度）

## 5. android:scaleType="fitStart"

* 把图片按比例扩大（缩小）到ImageView的宽度，在ImageView的上方显示

## 6. android:scaleType="fitEnd"

* 把图片按比例扩大（缩小）到ImageView的宽度，在ImageView的下方显示

## 7. android:scaleType="fitXY"

* 表示把图片按指定的大小在ImageView中显示，拉伸或收缩图片，不保持原比例，填满ImageView

总结：

1.对于小图片而言，center，centerInside效果一样属于不缩放类型a

参考链接：


[http://blog.csdn.net/larryl2003/article/details/6919513]()