package com.example.franer.testedittext;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by franer on 2017/八月/31.
 */

public class SelectEditText extends EditText {

    public SelectEditText(Context context) {
        super(context);
    }

    public SelectEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelectEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SelectEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        if (selStart != getText().length()) setSelection(getText().length());
    }

}
