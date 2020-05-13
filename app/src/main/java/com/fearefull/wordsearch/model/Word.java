package com.fearefull.wordsearch.model;

import java.io.Serializable;

/**
 * Created by Aref Hosseini on 08/07/17.
 */

public class Word implements Serializable {

    private int mId;
    private String mString;

    public Word() {
        this(-1, "");
    }

    public Word(int id, String string) {
        mId = id;
        mString = string;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getString() {
        return mString;
    }

    public String getShortString() {
        return mString.replaceAll(" ", "").replaceAll("_", "");
    }

    public void setString(String string) {
        mString = string;
    }
}
