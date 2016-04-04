package com.silence.countdownview.formatter;

import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.widget.TextView;

import com.silence.countdownview.view.DateFormatterIntf;

/**
 * 带有圆角背景样式的倒计时
 * silence
 */
public class SpannableTimeFormatter implements DateFormatterIntf {
    private DateFormatterIntf mWrappedFormatter;

    private SparseArray<RoundBackgroundSpan> mBgSpanArray;

    //数字的颜色
    private int mNumForegroundColor;
    private int mNumBackgroundColor;

    //分割符的颜色
    private int mSplitForegroundColor;
    private int mSplitBackgroundColor;

    public SpannableTimeFormatter(DateFormatterIntf wrappedFormatter) {
        mWrappedFormatter = wrappedFormatter;
        mBgSpanArray = new SparseArray<>();
    }

    @Override
    public CharSequence formatTime(TextView view, long remainSeconds) {
        SpannableStringBuilder timeStr = new SpannableStringBuilder();

        timeStr.append(mWrappedFormatter.formatTime(view, remainSeconds));

        addSpanStyle(view.getTextSize(), timeStr);
        return timeStr;
    }

    private void addSpanStyle(float textSize, SpannableStringBuilder orgString) {

        float textPadding = textSize / 6;

        for (int i = 0; i < orgString.length(); i++) {
            RoundBackgroundSpan curSpan = mBgSpanArray.get(i);
            if (curSpan == null) {
                try {
                    Integer.valueOf(orgString.subSequence(i, i + 1).toString());
                    curSpan = new RoundBackgroundSpan(mNumBackgroundColor,
                            mNumForegroundColor,
                            new float[]{textPadding, textPadding, textPadding, textPadding, textPadding});
                } catch (Exception e) {
                    curSpan = new RoundBackgroundSpan(mSplitBackgroundColor,
                            mSplitForegroundColor,
                            new float[]{0, textPadding, 0, textPadding, textPadding});
                }

                mBgSpanArray.append(i, curSpan);
            }
            orgString.setSpan(curSpan, i, i + 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
    }

    public void setNumColor(int foregroundColor, int backgroundColor) {
        mNumForegroundColor = foregroundColor;
        mNumBackgroundColor = backgroundColor;
    }

    public void setSplitColor(int foregroundColor, int backgroundColor) {
        mSplitForegroundColor = foregroundColor;
        mSplitBackgroundColor = backgroundColor;
    }
}
