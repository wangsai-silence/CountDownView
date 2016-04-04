package com.silence.countdownview.view;

import android.widget.TextView;

/**
 * Created by silence on 16-4-4.
 */
public interface DateFormatterIntf {

    public CharSequence formatTime(TextView view, long remainMillis);

}
