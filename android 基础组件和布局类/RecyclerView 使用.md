# RecyclerView 使用

## 1. RecyclerView的优点与缺点

### 1.1 优点

* RecyelerView封装了viewholder的回收和复用，是复用变得更加简单
* RecyelerView高度解耦和灵活
* RecyelerView可以控制增删动画

### 1.1 缺点

* RecyelerView需要自己画分割线，实现起来优点麻烦，需要各种绘制
* RecyelerView相对于ListView,GridView使用会复杂

## 2. RecyclerView的基本使用

### 2.1 添加引用

```
compile 'com.android.support:recyclerview-v7:25.3.1'
```

### 2.2.1 创建adapter

```
// RecyclerView.Adapter后面的范型为自定义的继承于RecyclerView.ViewHolder的ViewHolder
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    private List<String> list;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(List<String> list) {
        this.list = list;
    }
    // 创建ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 创建ViewHolder
        MyViewHolder myViewHolder = new MyViewHolder(View.inflate(parent.getContext(), android.R.layout.simple_expandable_list_item_1, null));
        return myViewHolder;
    }
    // 绑定ViewHolder，设置对应数据
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.tv.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

}

```

### 2.3 设置adapter

```
// 一定要设置LayoutManager，否则无法绘制出UI
// ListView 效果
recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
// GrideView 效果
//recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
// 瀑布流效果，每一个项大小不一致,通过适配器里面设置每一个item的大小不一致
recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL));
recyclerView.setAdapter(new MyAdapter(list));
```

## 3. RecyclerView的高级使用

## 3.1 设置分割线

### 3.1.1 设置类似Listview的效果的分割线

#### 3.1.1.1 自定义RecyclerView.ItemDecoration

```
public class DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int mOrientaion;
    // 这里使用Listview的分割线，但是通过重写样式可以使用自己定义的分割线样式
    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;

    public DividerItemDecoration(Context context, int orientation) {
        this.mContext = context;
        this.mOrientaion = orientation;
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        // 维护TypedArray池，typedArray对象从TypedArray池中获取，在用完以后回收供其他模块复用
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientaion == LinearLayoutManager.HORIZONTAL) {
            drawVertical(c, parent);
        } else {
            drawHorizonal(c, parent);
        }
        super.onDraw(c, parent, state);
    }

    /**
     * 给竖直布局画水平分割线
     *
     * @param c
     * @param parent
     */
    private void drawHorizonal(Canvas c, RecyclerView parent) {
        // parent为整个RecyclerView容器对象
        int left = parent.getPaddingLeft();
        // 用整个parent的宽度减去paddingRight
        int right = parent.getWidth() - parent.getPaddingRight();
        int childrenNum = parent.getChildCount();
        for (int i = 0; i < childrenNum; i++) {
            // 得到每一项View对象
            View childrenView = parent.getChildAt(i);
            int top = childrenView.getBottom();
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 给水平布局画竖直分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int top = parent.getPaddingTop();
        // 整个parent的高度减去paddingBottom
        int bottom = parent.getHeight() - parent.getPaddingBottom();
        int childrenNum = parent.getChildCount();
        for (int i = 0; i < childrenNum; i++) {
            View childrenView = parent.getChildAt(i);
            // 子item到整个父布局的右边距离+ 分割线可能存在的与子item之间的分割线
            int left = childrenView.getRight();
            int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        // 1. 调用此方法，outRect即为分割矩形
        // 2. 这里画矩形传入的参数有的歧义
        if (mOrientaion == LinearLayoutManager.VERTICAL) {
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }
}
```

### 3.1.2 设置类似GrideView的效果的分割线

```
public class DividerGridItemDecoration extends RecyclerView.ItemDecoration {
    private Context mContext;
    private int[] attrs = new int[]{
            android.R.attr.listDivider
    };
    private Drawable mDivider;

    public DividerGridItemDecoration(Context context) {
        this.mContext = context;
        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        // 维护TypedArray池，typedArray对象从TypedArray池中获取，在用完以后回收供其他模块复用
        typedArray.recycle();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent);
        drawHorizonal(c, parent);
        super.onDraw(c, parent, state);
    }

    /**
     * 画竖直分割线
     *
     * @param c
     * @param parent
     */
    private void drawVertical(Canvas c, RecyclerView parent) {
        int childrenNum = parent.getChildCount();
        for (int i = 0; i < childrenNum; i++) {
            View childrenView = parent.getChildAt(i);
            int left = childrenView.getRight();
            int top = childrenView.getTop();
            int right = left + mDivider.getIntrinsicWidth();
            // 高为整个控件的高度
            int bottom = childrenView.getBottom();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 画水平分割线
     *
     * @param c
     * @param parent
     */
    private void drawHorizonal(Canvas c, RecyclerView parent) {
        int childrenNum = parent.getChildCount();
        for (int i = 0; i < childrenNum; i++) {
            View childrenView = parent.getChildAt(i);
            int left = childrenView.getLeft();
            int top = childrenView.getBottom();
            // 长为整个控件的长度距离
            int right = childrenView.getRight();
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
        super.getItemOffsets(outRect, itemPosition, parent);
        // 1. 调用此方法，outRect即为分割矩形
        int right = mDivider.getIntrinsicHeight();
        int bottom = mDivider.getIntrinsicWidth();
        if (isLastColum(itemPosition, parent)) {
            right = 0;
        }
        if (isLastRow(itemPosition, parent)) {
            bottom = 0;
        }
        outRect.set(0, 0, right, bottom);
    }

    /**
     * 是否为最后一行
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    private boolean isLastRow(int itemPosition, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int lastRowNum = parent.getChildCount() % spanCount;
        if (lastRowNum < spanCount || lastRowNum == 0) {
            return true;
        }
        return false;
    }

    /**
     * 是否为最后一列
     *
     * @param itemPosition
     * @param parent
     * @return
     */
    private boolean isLastColum(int itemPosition, RecyclerView parent) {
        if (((itemPosition + 1) % getSpanCount(parent)) == 0) {
            return true;
        }
        return false;
    }

    /**
     * 获取每一行的item个数
     *
     * @param parent
     * @return
     */
    public int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManger = parent.getLayoutManager();
        if (layoutManger instanceof GridLayoutManager) {
            // 将RecyclerView.LayoutManager强转为GridLayoutManager，这样可以取到每一行的个数
            GridLayoutManager gm = (GridLayoutManager) layoutManger;
            return gm.getSpanCount();
        }
        return 0;
    }

}
```





