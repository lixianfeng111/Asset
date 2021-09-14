package com.yhhl.asset.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.yhhl.asset.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

//使用这个类来结合Glide可以比较简单地实现毛玻璃效果~
//implementation 'jp.wasabeef:glide-transformations:3.1.1'

public class GlideUtil {
    private static Context mContext;

    public GlideUtil() {

    }

    //在Application中初始化GlideHelper
    public static void initGlideHelper(Context context) {
        mContext = context;
    }

    //加载网络图片
    public static void showImage(ImageView view, String url, int isRound, Target target) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.ALL).timeout(60000).priority(Priority.HIGH);
        if (isRound == 1) {  //圆形带有边框效果
            options.transform(new GlideCircleStrokeTransform());
        } else if (isRound == 2) {  //圆形无边框效果
            options.transform(new CircleCrop());
        } else if (isRound == 3) {  //自定义所加载View的弧度
            options.transforms(new Transformation[]{new CenterCrop(), new RoundedCorners(mContext.getResources().getDimensionPixelOffset(0))});
        }

        RequestBuilder builder = Glide.with(mContext).load(url).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //加载本地资源图片
    public static void showImage(ImageView view, int resourceId, int isRound, Target target) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.ALL).timeout(60000).priority(Priority.HIGH);
        if (isRound == 1) {
            options.transform(new GlideCircleStrokeTransform());
        } else if (isRound == 2) {
            options.transform(new CircleCrop());
        } else if (isRound == 3) {
            options.transforms(new Transformation[]{new CenterCrop(), new RoundedCorners(mContext.getResources().getDimensionPixelOffset(0))});
        }

        RequestBuilder builder = Glide.with(mContext).load(resourceId).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //设置默认占位图和加载出现错误时的缺省图
    public static void showImage(ImageView view, String url, int placeHolder, int isRound, Target target) {
        RequestOptions options = (new RequestOptions()).timeout(60000).placeholder(placeHolder).error(placeHolder).diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH);
        if (isRound == 1) {
            options.transform(new GlideCircleStrokeTransform());
        } else if (isRound == 2) {
            options.transform(new CircleCrop());
        } else if (isRound == 3) {
            options.transforms(new Transformation[]{new CenterCrop(), new RoundedCorners(mContext.getResources().getDimensionPixelOffset(0))});
        }

        RequestBuilder builder = Glide.with(mContext).load(url).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //加载Gif
    public static void showGifImage(ImageView view, String url) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.RESOURCE).timeout(60000).priority(Priority.HIGH);
        Glide.with(mContext).load(url).apply(options).into(view);
    }

    //加载Gif,可传Target,Target可在此方法内创建,也可由外部创建再传入
    public static void showGifImage(ImageView view, String url, Target target) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.ALL).timeout(60000).priority(Priority.HIGH);
        RequestBuilder builder = Glide.with(mContext).asGif().load(url).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //加载毛玻璃效果,BlurTransformation()括号内数字为毛玻璃效果的程度
    public static void showGaosImage(ImageView view, int url, Target target) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.ALL).timeout(60000).transforms(new Transformation[]{new BlurTransformation(50)}).priority(Priority.HIGH);
        RequestBuilder builder = Glide.with(mContext).load(url).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //加载本地图片
    public static void showImage(ImageView view, int resourceId, Target target) {
        RequestOptions options = (new RequestOptions()).diskCacheStrategy(DiskCacheStrategy.ALL).timeout(60000).priority(Priority.HIGH);
        RequestBuilder builder = Glide.with(mContext).load(resourceId).apply(options);
        if (target == null) {
            builder.into(view);
        } else {
            builder.into(target);
        }
    }

    //ImageView加载本地图片
    public static void showImage(ImageView view, String url) {
        showImageWithTarget(view, url, (Target)null);
    }

    //View加载网络图片
    public static void showImageWithTarget(ImageView view, String url, Target target) {
        if (!TextUtils.isEmpty(url)) {
            showImage(view, url, 0, target);
        }
    }

    //View加载本地图片
    public static void showImageWithTarget(ImageView view, int resourceId, int isRound, Target target) {
        showImage(view, resourceId, isRound, target);
    }

    //内部创建Target对象
    public static void showTargetImage(final View view, String url) {
        Target target = new SimpleTarget<Drawable>() {
            public void onResourceReady(Drawable resource, Transition transition) {
                view.setBackgroundDrawable(resource);
            }
        };
        showImageWithTarget((ImageView)null, url, target);
    }

    //View加载本地图片资源
    public static void showTargetImage(final View view, int resourceId, int isRound) {
        Target target = new SimpleTarget<Drawable>() {
            public void onResourceReady(Drawable resource, Transition transition) {
                view.setBackgroundDrawable(resource);
            }
        };
        showImageWithTarget((ImageView)null, resourceId, isRound, target);
    }

    //View加载网络图片,圆形有边框
    public static void showRoundImageWithStroke(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            (new RequestOptions()).timeout(60000).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).diskCacheStrategy(DiskCacheStrategy.ALL);
            RequestOptions options = RequestOptions.bitmapTransform(new GlideCircleStrokeTransform()).priority(Priority.HIGH);
            Glide.with(mContext).load(url).apply(options).into(view);
        }
    }

    //加载图片圆形无边框
    public static void showRoundImageNoStroke(ImageView view, String url) {
        if (!TextUtils.isEmpty(url)) {
            showImage(view, url, 2, (Target)null);
        }
    }

    //加载图片圆形无边框
    public static void showRoundImageNoStroke(ImageView view, String url, int placeHolder) {
        if (!TextUtils.isEmpty(url)) {
            showImage(view, url, placeHolder, 2, (Target)null);
        }
    }

    public static void showImage(Context context, ImageView imageView, String url){
        Glide.with(context).load(url).into(imageView);
    }
}