# 它是一个实时渲染磨砂玻璃效果的Actionbar 
渲染帧数请看视频 [视频地址](https://github.com/xlzhen-940218/BlurActionbar/tree/master/video/screen_recorder.mp4)
一般中高端手机下可以稳定30帧运行。
## 1.使用方法
```
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
```
## 2.Canvas 还有问题
```
<!--希望可以有一个高效的draw方式。目前这种在骁龙845CPU平台都会导致20ms的延迟。-->
Canvas canvas = new Canvas(screenshot);
v.draw(canvas);
```

## 3.如果有人想更加方便的使用，正在考虑添加
```
implementation com.xxx.xxx
```


