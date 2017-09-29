Paint 使用

Paint mPaint = new Paint();

## 1. 设置连接处样式
```
mPaint.setStrokeJoin(parm)
```
* Paint.Join.MITER // 锐角
* Paint.Join.ROUND // 圆角
* Paint.Join.BEVEL // 直线

## 2. 设置画笔的样式
mPaint.setStyle(parm);

* Paint.Style.STROKE // 描边
* Paint.Style.FILL // 填充
* Paint.Style.FILL_AND_STROKE // 描边加填充 

