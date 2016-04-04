package com.silence.countdownview.view;

import com.silence.countdownview.view.CountdownTextView;

/**
 * Created by silence on 16-4-4.
 */
public interface CountdownListener {

    void onStartTick();

    void onStopTick();

    void onTick(CountdownTextView CountdownTextView, long startTime, long stopTime);
}
