package com.yhhl.asset.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hss01248.dialog.StyledDialog;
import com.hss01248.dialog.bottomsheet.BottomSheetBean;
import com.hss01248.dialog.config.BottomSheetStyle;
import com.hss01248.dialog.config.ConfigBean;
import com.hss01248.dialog.interfaces.MyDialogListener;
import com.hss01248.dialog.interfaces.MyItemDialogListener;
import com.yhhl.asset.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

/**
 */

public class DialogUtils {

    private static List<String> optionsPhoto;
    private static List<BottomSheetBean> shareBottoms;
    static {
        optionsPhoto = new ArrayList<>();
        optionsPhoto.add("拍照");
        optionsPhoto.add("我的相册");
    }

    //单例
    private static DialogUtils instance = null;

    private DialogUtils() {
    }

    public static DialogUtils getInstance() {
        if (instance == null) {
            synchronized (DialogUtils.class) {
                if (instance == null) {
                    instance = new DialogUtils();
                }
            }
        }
        return instance;
    }

    private AlertDialog.Builder getAlertBuilder(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        return builder;
    }

    /**
     * Android 样式的弹出提示框
     * @param activity
     * @param title
     * @param message
     * @param layoutRes
     * @param clickConfirmListener
     * @return
     */
    private AlertDialog showAlertDialog(@NonNull Activity activity, @NonNull String title, String message, @LayoutRes int layoutRes,
                                        final OnClickConfirmListener clickConfirmListener) {
        View customView = null;
        AlertDialog.Builder builder = getAlertBuilder(activity);
        builder.setTitle(title);
        if (!TextUtils.isEmpty(message)) {
            builder.setMessage(message);
        }
        if (layoutRes != 0) {
            customView = LayoutInflater.from(activity).inflate(layoutRes,null,false);
            builder.setView(customView);
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    dismissDialog(dialog,clickConfirmListener.clickConfirm(dialog,which));
            }
        });
        builder.setCancelable(false);
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismissDialog(dialog,true);
            }
        });
        final AlertDialog ad = builder.create();
        ad.show();
        return ad;
    }

    /**
     * ios风格
     * @param hintContent
     * @param clickListener
     */
    public static void showDefaultAlertDialog(String hintContent, String confirm, String cancel, final ClickListener clickListener){
        StyledDialog.buildIosAlert("删除", hintContent, new MyDialogListener() {
            @Override
            public void onFirst() {
            }

            @Override
            public void onSecond() {
                clickListener.clickConfirm();
            }
        }).setBtnColor(R.color.colorBlockRgb242424,R.color.colorBlueRgb3285ff, R.color.colorBlueRgb3285ff).setBtnText(cancel,confirm).show();
    }


    /**
     * ios风格  单个按钮
     * @param hintContent
     * @param clickListener
     */
    public static void showDefaultAlertDialog(String hintContent, String confirm, final ClickListener clickListener){
        StyledDialog.buildIosAlert("提示", hintContent, new MyDialogListener() {
            @Override
            public void onFirst() {
                clickListener.clickConfirm();
            }

            @Override
            public void onSecond() {

            }
        }).setBtnColor(R.color.color_3,R.color.colorBlueRgb3285ff,R.color.colorBlueRgb3285ff)
                .setBtnText(confirm).show();
    }

    /**
     * ios风格
     * @param hintContent
     * @param clickListener
     */
    public static void showDefaultAlertDialog(String hintContent, final ClickListener clickListener){
        showDefaultAlertDialog(hintContent,"确定","取消",clickListener);
    }



    /**
     * 显示加载提示框
     * @param content
     * @return
     */
    public static Dialog showLoadingDialog(String content){
        Dialog dialog = StyledDialog.buildMdLoading(content).show();
        return dialog;
    }

    /**
     * 带取消按钮的 底部弹出选择框
     * @param listContent
     * @param listener
     */
    public static void showBottomDialog(List<String> listContent, MyItemDialogListener listener){
        StyledDialog.buildBottomItemDialog(listContent, "取消", listener).show();
    }

    /**
     * 选照片提示框
     * @param listener
     */
    public static void showBottomDialogForChoosePhoto(MyItemDialogListener listener){
        showBottomDialog(optionsPhoto,listener);
    }

    /**
     * 分享dialog
     * @param listener
     */
    public static void showShareDialog(final ShareDialogClickListener listener){
        ConfigBean configBean = StyledDialog.buildBottomSheetGv("分享", shareBottoms, "取消", 3, new MyItemDialogListener() {
            @Override
            public void onItemClick(CharSequence charSequence, int i) {
                if (listener == null){
                    return;
                }
                if (i == 0){
                    listener.clickWeixin();
                } else if (i == 1){
                    listener.clickPengyouQuan();
                } else if ( i == 2){
                    listener.clickDownload();
                }
            }
        });

        BottomSheetStyle sheetStyle = BottomSheetStyle.newBuilder().iconSizeDp(30).build();
        configBean.setBottomSheetStyle(sheetStyle);
        configBean.show();

    }

    //网络加载
    public static Dialog showLoading(Context context) {
        ViewGroup view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.show_loading, null, false);
        final AlertDialog dialog = new AlertDialog
                .Builder(context)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//设置背景透明
        dialog.show();
        return dialog;
    }


    public interface ShareDialogClickListener{
        void clickWeixin();
        void clickPengyouQuan();
        void clickDownload();
    }
    /**
     * 隐藏
     * @param dialog
     * @param dismiss
     */
    private void dismissDialog(DialogInterface dialog, boolean dismiss){
        try {
            Field field = dialog.getClass().getSuperclass().getDeclaredField("mShowing");
            field.setAccessible(true);
            //   将mShowing变量设为false，表示对话框已关闭
            field.set(dialog, dismiss);
            dialog.dismiss();

        } catch (Exception e) {
        }
    }

    /**
     * 一般提示框
     *
     * @param activity
     * @param title
     * @param message
     * @param clickConfirmListener
     */
    public void showAlertDialog(@NonNull Activity activity, @NonNull String title, String message,
                                OnClickConfirmListener clickConfirmListener) {
        showAlertDialog(activity, title, message, 0, clickConfirmListener);
    }


    /**
     * 自定义弹出框
     *
     * @param activity
     * @param title
     * @param layoutRes
     * @param clickConfirmListener
     */
    public AlertDialog showCustomDialog(@NonNull Activity activity, @NonNull String title, @LayoutRes int layoutRes,
                                        OnClickConfirmListener clickConfirmListener) {
        return showAlertDialog(activity, title, null, layoutRes, clickConfirmListener);
    }

    public interface OnClickConfirmListener{
        boolean clickConfirm(DialogInterface dialog, int which);
    }

    public interface ClickListener{
        void clickConfirm();
//        void clickCancel();
    }


    /**
     * 带进度条的提示
     * @param message
     */
    public static ProgressDialog pd = null;
    public static ProgressDialog showDownloadDialog(Context context, String message, boolean isDismissOnTouchOut){
        if (pd == null){
            pd = new ProgressDialog(context);
        }
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setOnDismissListener(null);
        pd.setCancelable(isDismissOnTouchOut);
        pd.setCanceledOnTouchOutside(isDismissOnTouchOut);
        pd.setMessage(message);
        pd.setMax(100);
        pd.show();
        return pd;
    }


    public static void setDownloadPrompt(String message){
        if (pd != null){
            pd.setMessage(message);
        }
    }

    /**
     * 设置条形进度条值，，和showDownloadDialog一起用
     * @param count
     */
    public static void setProgressValue(int count){
        if (pd != null) {
            pd.setProgress(count);
        }
    }

    public static void DismissLoadDialog() {
        if (pd != null && pd.isShowing()) {
            pd.dismiss();
            pd.cancel();
        }
    }

}