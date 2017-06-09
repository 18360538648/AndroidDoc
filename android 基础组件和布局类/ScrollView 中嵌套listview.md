# ScrollView 中嵌套listview

在ScrollView中嵌套listview会出现以下问题

## 1. 无法计算出listview的总高度，只会计算第一个listview的高度


```
   /**
     * 根据listView计算当前listView的高度
     *
     * @param listView
     */
    public void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }
```

## 2. Listview中的Textview只会计算一行的行高，当每一项的Textview大于一行时，会导致Listview的总体高度计算不正确，此时需要自定义Textview

```
public class TextViewPlus extends TextView{
    public TextViewPlus(Context context) {
        super(context);
    }


    public TextViewPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public TextViewPlus(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        Layout layout = getLayout();
        if (layout != null) {
            int height = (int) Math.ceil(getMaxLineHeight(this.getText().toString()))
                    + getCompoundPaddingTop() + getCompoundPaddingBottom();
            int width = getMeasuredWidth();
            setMeasuredDimension(width, height);
        }
    }

    /**
    * 注意其中的RelativeLayout要对应Textview的父布局做相应的改变
    */
    private float getMaxLineHeight(String str) {
        float height = 0.0f;
        float screenW = ((Activity)getContext()).getWindowManager().getDefaultDisplay().getWidth();
        float paddingLeft = ((RelativeLayout)this.getParent()).getPaddingLeft();
        float paddingReft = ((RelativeLayout)this.getParent()).getPaddingRight();
        //这里具体this.getPaint()要注意使用，要看你的TextView在什么位置，这个是拿TextView父控件的Padding的，为了更准确的算出换行
        int line = (int) Math.ceil( (this.getPaint().measureText(str)/(screenW-paddingLeft-paddingReft))); height = (this.getPaint().getFontMetrics().descent-this.getPaint().getFontMetrics().ascent)*line; return height;}

}
```