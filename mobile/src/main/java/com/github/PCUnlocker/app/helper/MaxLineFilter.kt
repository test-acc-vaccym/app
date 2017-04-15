package com.github.PCUnlocker.app.helper

import android.text.InputFilter
import android.text.Spanned

/**
 * Created by tim on 28.01.17.
 */

class MaxLineFilter(private val maxLines: Int) : InputFilter {

    override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
        var lineCount = 0
        for (c in source.toString().toCharArray()) {
            if (c == '\n') {
                lineCount++
            }
        }
        for (c in dest.toString().toCharArray()) {
            if (c == '\n') {
                lineCount++
            }
        }
        if (lineCount + 1 <= maxLines) {
            return null
        } else {
            return ""
        }
    }
}
