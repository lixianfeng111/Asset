package com.yhhl.asset.base;

import androidx.annotation.LayoutRes;

/**
 * Created by PengSong on 17/12/21.
 */

public abstract class BaseTitleBar {
    public abstract  @LayoutRes
    int getTitleBarViewRes();

    public abstract void createTitleBar(ViewManager viewManager);
}
