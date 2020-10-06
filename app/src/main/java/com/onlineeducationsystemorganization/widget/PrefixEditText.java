package com.onlineeducationsystemorganization.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

import java.util.Locale;

public class PrefixEditText extends AppCompatEditText {

    private ColorStateList mPrefixTextColor;

    public PrefixEditText(Context context) {
        this(context, null);
    }

    public PrefixEditText(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public PrefixEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mPrefixTextColor = getTextColors();
    }

    public void setPrefix(String prefix) {
        if (Locale.getDefault().getLanguage().equalsIgnoreCase("en"))
            setCompoundDrawables(new TextDrawable(prefix + " "), null, null, null);
        else if (Locale.getDefault().getLanguage().equalsIgnoreCase("ar"))
            setCompoundDrawables(null, null, new TextDrawable(" " + prefix), null);
    }

    public void setPrefixTextColor(int color) {
        mPrefixTextColor = ColorStateList.valueOf(color);
    }

    private class TextDrawable extends Drawable {
        private String mText = "";

        TextDrawable(String text) {
            mText = text;
            setBounds(0, 0, (int) getPaint().measureText(mText) + 3, (int) getTextSize());
        }

        @Override
        public void draw(Canvas canvas) {
            Paint paint = getPaint();
            paint.setColor(mPrefixTextColor.getColorForState(getDrawableState(), 0));
            int lineBaseline = getLineBounds(0, null);
            canvas.drawText(mText, 0, canvas.getClipBounds().top + lineBaseline, paint);
        }

        @Override
        public void setAlpha(int alpha) {/* Not supported */}

        @Override
        public void setColorFilter(ColorFilter colorFilter) {/* Not supported */}

        @Override
        public int getOpacity() {
            return 1;
        }
    }
}
