package com.gitlab.PCU.PCU;

/**
 * Created by tim on 13.01.17.
 */

enum RequestTag {
    UNLOCK("UNLOCK"),
    NOT_ASSIGNED("NotAssigned"),
    SETTINGS("SETTINGS");

    private final String tag;

    RequestTag(String tag) {
        this.tag = tag;
    }

    public String get_tag_id() {
        return tag;
    }
}
