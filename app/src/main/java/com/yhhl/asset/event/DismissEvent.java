package com.yhhl.asset.event;


import androidx.fragment.app.Fragment;

public class DismissEvent {
    private Fragment needDismiss;

    public DismissEvent(Fragment needDismiss) {
        this.needDismiss = needDismiss;
    }

    public Fragment getNeedDismiss() {
        return needDismiss;
    }

}
