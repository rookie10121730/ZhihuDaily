package com.seven.hzxubowen.zhihudaily.Util;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by hzxubowen on 2016/6/28.
 */

public class ImageHelper {

    public static Bitmap decImageLight(Bitmap bitmap, float lum){
        Bitmap bmp = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint paint = new Paint();

        ColorMatrix lumMatrix = new ColorMatrix();
        lumMatrix.setScale(lum, lum, lum, 1);
        paint.setColorFilter(new ColorMatrixColorFilter(lumMatrix));
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return bmp;
    }


}
