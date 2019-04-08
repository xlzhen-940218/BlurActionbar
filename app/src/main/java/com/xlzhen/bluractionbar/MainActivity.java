package com.xlzhen.bluractionbar;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private GridView gridView;
    private ImageView blurView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        //StatusBarUtil.setRootViewFitsSystemWindows(this,true);
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(this);
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
            //这样半透明+白=灰, 状态栏的文字能看得清
            StatusBarUtil.setStatusBarColor(this, Color.TRANSPARENT);
        }
        initView();
    }

    private void initView() {
        blurView = findViewById(R.id.blurView);
        gridView = findViewById(R.id.gridView);
        gridView.setAdapter(new BaseAdapter() {

            @Override
            public int getCount() {
                return 200;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if (convertView == null) {
                    convertView = new ImageView(MainActivity.this);
                    ((ImageView) convertView).setAdjustViewBounds(true);
                }

                ((ImageView) convertView).setImageResource(position%2==0?R.mipmap.beauty:R.mipmap.beauty_1);
                return convertView;
            }
        });
        gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                try {
                    Bitmap bitmap = BlurUtils.rsBlur(MainActivity.this, gridView, 10);
                    blurView.setImageBitmap(bitmap);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }
}
