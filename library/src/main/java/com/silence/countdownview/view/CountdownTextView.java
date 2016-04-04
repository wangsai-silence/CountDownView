package com.silence.countdownview.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.TextView;

import com.silence.countdownview.formatter.DefaultDateFormatter;

/**
 * Created by silence on 16-4-4.
 */
public class CountdownTextView extends TextView {

    //in Millis
    private static final int DEFAULT_INTERVAL = 1000;

    private int mInterval = DEFAULT_INTERVAL;

    private long mDeadlineTime;

    private CountdownListener mCountdownListener;

    private DateFormatterIntf mDateHelper;

    public CountdownTextView(Context context) {
        super(context);
    }

    public CountdownTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CountdownTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public CountdownTextView setCountdownDeadineTime(long deadlineTime) {
        mDeadlineTime = deadlineTime;
        return this;
    }

    private CountdownTextView setTimeInterval(int interval) {
        mInterval = interval;
        return this;
    }

    public CountdownTextView setDataFormater(@NonNull DateFormatterIntf formater) {
        mDateHelper = formater;
        return this;
    }

    public CountdownTextView setCountdownListener(CountdownListener countdownListener) {
        this.mCountdownListener = countdownListener;
        return this;
    }

    public void start() {

        post(countdownImpl);
        if (mCountdownListener != null)
            mCountdownListener.onStartTick();
    }

    public void stop() {
        this.removeCallbacks(countdownImpl);
        if (mCountdownListener != null)
            mCountdownListener.onStopTick();
    }

    private void updateText(CharSequence text) {
        setText(text, BufferType.SPANNABLE);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
    }

    private Runnable countdownImpl = new Runnable() {
        @Override
        public void run() {
            long currentTimeMillis = System.currentTimeMillis();
            long remainMillis = mDeadlineTime - currentTimeMillis;

            if (mDateHelper == null)
                mDateHelper = new DefaultDateFormatter();

            updateText(mDateHelper.formatTime(CountdownTextView.this, remainMillis < 0 ? 0 : remainMillis));

            if (mCountdownListener != null)
                mCountdownListener.onTick(CountdownTextView.this, currentTimeMillis, mDeadlineTime);

            if (remainMillis <= 0) {
                stop();
            } else {
                postDelayed(this, mInterval);
            }
        }
    };
}

