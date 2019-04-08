package com.xlzhen.bluractionbar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;

import java.util.Date;

public class BlurUtils {

    public synchronized static Bitmap rsBlur(Context context, View view, int radius) {

        Bitmap inputBmp = screenView(view);
        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript, inputBmp);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        scriptIntrinsicBlur.setInput(input);
        scriptIntrinsicBlur.setRadius(radius);
        scriptIntrinsicBlur.forEach(output);
        output.copyTo(inputBmp);
        renderScript.destroy();

        return inputBmp;
    }

    /**
     * 获取当前View截图
     *
     * @param view
     */
    public static Bitmap screenView(View view) {
        Bitmap bmp = loadBitmapFromView(view);
        bmp=Bitmap.createScaledBitmap(bmp,Math.round(bmp.getWidth()*0.05f),Math.round(bmp.getHeight()*0.05f),false);
        view.destroyDrawingCache();
        return bmp;
    }

    public static Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        //从View中取出缓存图非常耗时，图片越大越耗时，建议不要取太大的图
        screenshot = Bitmap.createBitmap(v.getWidth(),DensityUtils.dp2px(72), Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(screenshot);
        //canvas.translate(-v.getScrollX(), -v.getScrollY());//我们在用滑动View获得它的Bitmap时候，获得的是整个View的区域（包括隐藏的），如果想得到当前区域，需要重新定位到当前可显示的区域
        v.draw(canvas);// 将 view 画到画布上

        return screenshot;
    }
}
