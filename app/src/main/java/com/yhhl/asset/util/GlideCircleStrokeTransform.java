package com.yhhl.asset.util;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.graphics.Shader.TileMode;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

import java.security.MessageDigest;

import androidx.annotation.ColorInt;

public class GlideCircleStrokeTransform extends BitmapTransformation {
    private int mBorderWidth = 4;
    private int mBorderColor = -1;

    public GlideCircleStrokeTransform() {
    }

    public GlideCircleStrokeTransform(int borderWidth, @ColorInt int borderColor) {
        this.mBorderWidth = borderWidth;
        this.mBorderColor = borderColor;
    }

    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        int size = Math.min(toTransform.getWidth(), toTransform.getHeight());
        int x = (toTransform.getWidth() - size) / 2;
        int y = (toTransform.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(toTransform, x, y, size, size);
        Bitmap bitmap = Bitmap.createBitmap(size, size, toTransform.getConfig() != null ? toTransform.getConfig() : Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, TileMode.CLAMP, TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Style.STROKE);
        mBorderPaint.setStrokeWidth((float)this.mBorderWidth);
        mBorderPaint.setColor(this.mBorderColor);
        mBorderPaint.setStrokeCap(Cap.ROUND);
        mBorderPaint.setAntiAlias(true);
        float r = (float)size / 2.0F;
        float r1 = (float)(size - 2 * this.mBorderWidth) / 2.0F;
        canvas.drawCircle(r, r, r1, paint);
        canvas.drawCircle(r, r, r1, mBorderPaint);
        squaredBitmap.recycle();
        return bitmap;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
    }
}
