package com.gitlab.PCU.PCU.helper;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * Created by tim on 28.01.17.
 */

public class MaxLineFilter implements InputFilter {
    private int maxLines;

    public MaxLineFilter(int maxLines) {
        this.maxLines = maxLines;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        int lineCount = 0;
        for (char c : source.toString().toCharArray()) {
            if (c == '\n') {
                lineCount++;
            }
        }
        for (char c : dest.toString().toCharArray()) {
            if (c == '\n') {
                lineCount++;
            }
        }
        if ((lineCount + 1) <= maxLines) {
            return null;
        } else {
            return "";
        }
    }
}
