package com.silence.countdownview.formatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.text.style.ReplacementSpan;

import java.util.regex.Pattern;

/**
 * 圆角边框 首页倒计时banner的显示样式
 */
public class RoundBackgroundSpan extends ReplacementSpan {

    /**
     * 数字采取统一的宽度，防止倒计时的时候，宽度不断变化
     */
    private float numberSize;

    /**
     * left top right bottom 后方的间隙 用于确定文字和边框的相对位置
     */
    private float[] paddings;

    /**
     * 背景色
     */
    private int backgroundColor;

    /**
     * 文字颜色
     */
    private int forgroundColor;

    public RoundBackgroundSpan(int backgroundColor, int forgroundColor, float[] paddings) {
        this.backgroundColor = backgroundColor;
        this.forgroundColor = forgroundColor;
        this.paddings = paddings;

        numberSize = 0;
    }

    private boolean isNumeric(CharSequence str, int start, int end) {
        for (int i = start; i < end; i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    private float measureText(Paint paint, CharSequence text, int start, int end) {

        if (isNumeric(text, start, end)) {
            if (numberSize == 0)
                measureNumberSize(paint);

            return numberSize + paddings[0] + paddings[2] + paddings[4];
        } else {
            return paint.measureText(text, start, end) + paddings[0] + paddings[2] + paddings[4];
        }
    }

    private void measureNumberSize(Paint paint) {
        for (int i = 0; i < 10; i++) {
            float size = paint.measureText(String.valueOf(i));
            if (size > numberSize)
                numberSize = size;
        }
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetricsInt) {
        if (fontMetricsInt != null) {
            paint.getFontMetricsInt(fontMetricsInt);
        }

        return Math.round(measureText(paint, text, start, end));
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        RectF rect = new RectF(x, top, x + measureText(paint, text, start, end) - paddings[4], bottom + paddings[1] + paddings[3]);
        //查看裁剪区域，部分手机上发现，此处的canvas是经过裁剪了的，导致圆角矩形不能完全显示
        RectF clipRect = new RectF(canvas.getClipBounds());
        if (clipRect != null && !clipRect.contains(rect)) {
            float diff = 0;
            if (rect.bottom > clipRect.bottom)
                diff += rect.bottom - clipRect.bottom;
            if (rect.top < clipRect.top)
                diff += clipRect.top - rect.top;

            if (diff > 0) {
                paddings[1] -= diff / 2;
                if (paddings[1] < 0)
                    paddings[1] = 0;

                paddings[3] -= diff / 2;
                if (paddings[3] < 0)
                    paddings[3] = 0;

            }

            rect.intersect(clipRect);
        }

        paint.setColor(backgroundColor);
        float cornerR = paint.getTextSize() / 4;
        canvas.drawRoundRect(rect, cornerR, cornerR, paint);

        paint.setColor(forgroundColor);
        canvas.drawText(text, start, end, x + paddings[0], y + paddings[1], paint);
    }
}
