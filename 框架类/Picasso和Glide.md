Picasso和Glide

两者都是android端加载图片的框架。Glide的API是仿造Picasso的，下面介绍两者的不同之处

## 1. 包体大小

Picasso的包体比Glide小的多

## 2. 缓存机制
* Picasso会去下载原图到本地缓存，然后根据控件大小重新Resize图片
* Glide也会下载图片，但是就会马上Resize图片，将Resize以后的图片存储到本地

## 3. 内存使用
Glide会比Picasso内存使用少的多，跟其缓存机制有关

## Glide的优势

Glide提供的方法更多，并且可以加载动态图

## 4. Glide相关的问题

### 4.1 访问同一个地址，但资源改变的图片，无法跟新的问题

在加载地址后面加入一个值`+ "?key=" + Math.random()`

```
.load(headUrl + "?key=" + Math.random())
```
