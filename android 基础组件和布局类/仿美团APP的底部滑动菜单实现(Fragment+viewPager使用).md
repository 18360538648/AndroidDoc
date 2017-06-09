仿美团APP的底部滑动菜单实现

在现在的APP的应用中，类似仿美团APP的底部滑动菜单，应用是挺多的，例如QQ，微信，支付宝都应用到。开发流程如下


## 1. 底部按钮

底部按钮使用RadioButton。

```
// 按钮布局
<LinearLayout
        android:id="@+id/llradiogroup"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true">

        <RadioGroup
            android:id="@+id/rg_menu"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal">

            <RadioButton
                android:id="@+id/rbtn_home"
                style="@style/RadioButton"
                android:checked="true"
                android:drawableTop="@drawable/home"
                android:text="首页" />

            <RadioButton
                android:id="@+id/rbtn_vest"
                style="@style/RadioButton"
                android:drawableTop="@drawable/investment"
                android:text="投资" />

            <RadioButton
                android:id="@+id/rbtn_photo"
                style="@style/RadioButton"
                android:drawableTop="@drawable/shooting"
                android:text="拍拍" />

            <RadioButton
                android:id="@+id/rbtn_mine"
                style="@style/RadioButton"
                android:drawableTop="@drawable/recom_member"
                android:text="我的" />

            <RadioButton
                android:id="@+id/rbtn_more"
                style="@style/RadioButton"
                android:drawableTop="@drawable/more"
                android:text="更多" />

        </RadioGroup>
```

```
// style样式
<style name="RadioButton">
    <item name="android:layout_width">match_parent</item>
    <item name="android:layout_height">60dp</item>
    <item name="android:gravity">center</item>
    <item name="android:layout_weight">1</item>
    <item name="android:button">@null</item>
    <item name="android:background">@drawable/menueselector</item>
</style>
```

style样式中的menueselector为背景选择器，使按钮选中变色

在Res目录下新建文件夹（drawable-nodpi）,在里面建立新的xml文件，选择资源类型为Drawable的selector，在每一个selector中建立item选项
  
```
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android">
     <!--选中背景颜色-->
    <item android:drawable="@color/checked" android:state_checked="true" />
    <!--未选中背景颜色-->
    <item android:drawable="@color/nochecked" android:state_checked="false" />
</selector>
```

## 2. 中间的滑动窗口

```
<android.support.v4.view.ViewPager
        android:id="@+id/viewPager"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_above="@+id/llradiogroup">
        </android.support.v4.view.ViewPager>
```
## 3. 往滑动窗口添加按钮对应的Fragment, 并监听相应事件

下面的代码注意两点

* MainActivity需继承于FragmentActivity，这样才能找到其中的getSupportFragmentManager()方法
* 在书写Fragment时切记引入的是android.support.v4.app.Fragment，而非android.app.Fragment(android.app.Fragment是在3.0引入的，为了兼容更低版本首选android.support.v4.app.Fragment)

```
package com.lsw.wealthapp.activity;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import com.lsw.wealthapp.R;
import com.lsw.wealthapp.fragment.CaptureFragment;
import com.lsw.wealthapp.fragment.HomeFragment;
import com.lsw.wealthapp.fragment.InvestmentFragment;
import com.lsw.wealthapp.fragment.MoreFragment;
import com.lsw.wealthapp.fragment.MyFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends FragmentActivity {
    // HomeFragmentIndex
    private static final int HomeViewPagerIndex = 0;
    // InvestmentFragmentIndex
    private static final int InvsetViewPagerIndex = 1;
    // CaptureFragmentIndex
    private static final int CaptureViewPagerIndex = 2;
    // MyFragmentIndex
    private static final int MyViewPagerIndex = 3;
    // MoreFragmentIndex
    private static final int MoreViewPagerIndex = 4;
    private ViewPager viewPager;
    // 主页页面
    private HomeFragment homeFragment;
    // 投资页面
    private InvestmentFragment investmentFragment;
    //拍照页面
    private CaptureFragment captureFragment;
    // 我的页面
    private MyFragment myFragment;
    // 更多页面
    private MoreFragment moreFragment;
    // Fragment集合
    private List<Fragment> fragmentList;
    // FragmentAdapter
    private MyPageFramgentAdapter myPageFramgentAdapter;
    // 菜单RadioGroup
    private RadioGroup radioGroup;
    // 主页按钮
    private RadioButton rbtnHome;
    // 投资按钮
    private RadioButton rbtnInvest;
    // 拍照按钮
    private RadioButton rbtnCapture;
    // 我的按钮
    private RadioButton rbtnMine;
    // 更多按钮
    private RadioButton rbtnMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        homeFragment = new HomeFragment();
        investmentFragment = new InvestmentFragment();
        captureFragment = new CaptureFragment();
        myFragment = new MyFragment();
        moreFragment = new MoreFragment();
        fragmentList = new ArrayList<Fragment>();
        radioGroup = (RadioGroup) findViewById(R.id.rg_menu);
        rbtnHome = (RadioButton) findViewById(R.id.rbtn_home);
        rbtnInvest = (RadioButton) findViewById(R.id.rbtn_vest);
        rbtnCapture = (RadioButton) findViewById(R.id.rbtn_photo);
        rbtnMine = (RadioButton) findViewById(R.id.rbtn_mine);
        rbtnMore = (RadioButton) findViewById(R.id.rbtn_more);
        // 按钮选中，viewPager展示对应的页面
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rbtn_home:
                        viewPager.setCurrentItem(HomeViewPagerIndex);
                        break;
                    case R.id.rbtn_vest:
                        viewPager.setCurrentItem(InvsetViewPagerIndex);
                        break;
                    case R.id.rbtn_photo:
                        viewPager.setCurrentItem(CaptureViewPagerIndex);
                        break;
                    case R.id.rbtn_mine:
                        viewPager.setCurrentItem(MyViewPagerIndex);
                        break;
                    case R.id.rbtn_more:
                        viewPager.setCurrentItem(MoreViewPagerIndex);
                        break;
                }
            }
        });
        //将Fragment加入集合中
        fragmentList.add(homeFragment);
        fragmentList.add(investmentFragment);
        fragmentList.add(captureFragment);
        fragmentList.add(myFragment);
        fragmentList.add(moreFragment);
        FragmentManager fm = getSupportFragmentManager();
        myPageFramgentAdapter = new MyPageFramgentAdapter(fm);
        viewPager.setAdapter(myPageFramgentAdapter);
        // viewPager发生改变，对应的按钮状态变为选中
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case HomeViewPagerIndex:
                        rbtnHome.setChecked(true);
                        break;
                    case InvsetViewPagerIndex:
                        rbtnInvest.setChecked(true);
                        break;
                    case CaptureViewPagerIndex:
                        rbtnCapture.setChecked(true);
                        break;
                    case MyViewPagerIndex:
                        rbtnMine.setChecked(true);
                        break;
                    case MoreViewPagerIndex:
                        rbtnMore.setChecked(true);
                        break;
                    default:
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    // viewPager所需的适配器
    class MyPageFramgentAdapter extends FragmentPagerAdapter {

        public MyPageFramgentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }


}

```

## viewPager使用

* setOffscreenPageLimit 设置当前页面两边的预加载页面个数，默认值为1(即左边可以预加载一个页面，右边也可以加载一个页面)
* 





