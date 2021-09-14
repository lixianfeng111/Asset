package com.yhhl.asset.statusBar;

/**
 * Created by PengSong on 18/11/15.
 */

public class StatusThemeEntity {

    private boolean fitSystemWindows;
    private boolean isDark;
    private int bgColor;

    public StatusThemeEntity() {
    }

    public StatusThemeEntity(boolean fitSystemWindows, boolean isDark, int bgColor) {
        this.fitSystemWindows = fitSystemWindows;
        this.isDark = isDark;
        this.bgColor = bgColor;
    }

    public boolean isFitSystemWindows() {
        return fitSystemWindows;
    }

    public void setFitSystemWindows(boolean fitSystemWindows) {
        this.fitSystemWindows = fitSystemWindows;
    }

    public boolean isDark() {
        return isDark;
    }

    public void setDark(boolean dark) {
        isDark = dark;
    }

    public int getBgColor() {
        return bgColor;
    }

    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }
}
