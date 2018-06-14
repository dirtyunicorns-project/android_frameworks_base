package com.android.systemui.statusbar.policy;

import android.app.StatusBarManager;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

import com.android.systemui.Dependency;

public class ClockCenter extends Clock {

    private boolean mClockVisibleByPolicy = true;
    private boolean mClockVisibleByUser = true;

    public ClockCenter(Context context) {
        this(context, null);
    }

    public ClockCenter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ClockCenter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setClockVisibleByUser(boolean visible) {
        mClockVisibleByUser = visible;
        updateClockVisibility();
    }

    public void setClockVisibilityByPolicy(boolean visible) {
       mClockVisibleByPolicy = visible;
        updateClockVisibility();
    }

    protected void updateClockVisibility() {
        boolean visible = mClockStyle == STYLE_CLOCK_CENTER && mShowClock
                && mClockVisibleByPolicy && mClockVisibleByUser;
        Dependency.get(IconLogger.class).onIconVisibility("center_clock", visible);
        int visibility = visible ? View.VISIBLE : View.GONE;
        setVisibility(visibility);
    }

    @Override
    public void disable(int state1, int state2, boolean animate) {
        boolean clockVisibleByPolicy = (state1 & StatusBarManager.DISABLE_CLOCK) == 0;
        if (clockVisibleByPolicy != mClockVisibleByPolicy) {
            setClockVisibilityByPolicy(clockVisibleByPolicy);
        }
    }
}