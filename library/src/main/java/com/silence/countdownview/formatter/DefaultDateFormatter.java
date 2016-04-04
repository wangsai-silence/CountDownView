package com.silence.countdownview.formatter;

import android.widget.TextView;

import com.silence.countdownview.view.DateFormatterIntf;

/**
 * Created by silence on 16-4-4.
 */
public class DefaultDateFormatter implements DateFormatterIntf {

    public static final String FORMAT_DD_HH_MM_SS = "dd:HH:mm:ss";

    public static final String FORMAT_HH_MM_SS = "HH:mm:ss";

    static final String defaultFormatStr = FORMAT_HH_MM_SS;

    private String mFormatStr;

    public DefaultDateFormatter(String formatStr) {
        mFormatStr = formatStr;
    }

    public DefaultDateFormatter() {
        this(defaultFormatStr);
    }

    @Override
    public CharSequence formatTime(TextView view, long remainMillis) {

        String formatted = mFormatStr;

        long remainSec = remainMillis / 1000;

        long sec = remainSec % 60;

        long min = remainSec / 60 % 60;

        long hour = remainSec / 60 / 60 % 24;

        long day = remainSec / 60 / 60 / 24;

        if (formatted.contains("dd")) {
            if (day == 0) {
                int ddIndex = formatted.indexOf("dd");
                String ddStr = formatted.substring(ddIndex, ddIndex + 3);
                formatted = formatted.replace(ddStr, "");
            } else {
                formatted = formatted.replace("dd", formatNum(day));
            }
        } else {
            hour += day * 24;
        }

        if (formatted.contains("HH")) {
            formatted = formatted.replace("HH", formatNum(hour));
        } else {
            min += hour * 60;
        }

        if (formatted.contains("mm")) {
            formatted = formatted.replace("mm", formatNum(min));
        } else {
            sec += min * 60;
        }

        if (formatted.contains("ss")) {
            formatted = formatted.replace("ss", formatNum(sec));
        }

        return formatted;
    }

    private String formatNum(long num) {
        if (num > 9)
            return num + "";
        else
            return "0" + num;
    }

}