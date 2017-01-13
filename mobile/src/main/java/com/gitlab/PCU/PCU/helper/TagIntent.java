package com.gitlab.PCU.PCU.helper;

import android.content.Intent;

/**
 * Created by tim on 13.01.17.
 */

public class TagIntent extends Intent {
    private String i_tag = "NotAssigned";

    public TagIntent(Intent intent, String tag) {
        super(intent);
        i_tag = tag;
    }

    public String get_tag() {
        return i_tag;
    }
}
