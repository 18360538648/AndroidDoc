# Android ListView的深入探究


notifyDatasetChanged  

## 1. ListView优化

### 1.1 复用convertView

在ListView中的每一项都是通过getview方法渲染出来的，如果一个ListView有很多项，都要通过getview方式渲染的话，这样效率很低。android提供这样的机制，开始渲染屏幕可见的项目数，当最前面的项，移除屏幕时，这时这项就变成了conVertView，提供给新出的项复用。这样就无需渲染了。

![](http://img.my.csdn.net/uploads/201304/18/1366248240_7944.jpg)

### 1.2 使用ViewHolder类

在渲染一个布局时需要通过findViewByid查找对应控件的id，这是很耗时的，通过ViewHolder类，将listview中每一个控件的封装起来，并通过settag方法存放在convertView中，下次要用时通过convertview.getTag()方法就可以取出。这样大大的增加效率

下面是一段经典的使用上面两中方法的代码

```
 @Override
    public View getView(int i, View convertview, ViewGroup viewGroup) {
        ViewHolder holder;
        if(convertview==null){
            holder=new ViewHolder();
            convertview=minflater.inflate(R.layout.listitem,null);
            holder.img= (ImageView) convertview.findViewById(R.id.img);
            holder.textView= (TextView) convertview.findViewById(R.id.text);
            convertview.setTag(holder);
        }else{
            holder= (ViewHolder) convertview.getTag();
        }
        holder.img.setBackgroundResource(R.drawable.ic_launcher);
        holder.textView.setText(data.get(i));
        return convertview;
    }

    public class ViewHolder{
        private ImageView img;
        private TextView textView;
    }
```

## 1. ListView属性设置

divider设置分割线的颜色
dividerHeight这是分割线的高度(两者需一同使用)

android:scrollbars="none"隐藏滚动条