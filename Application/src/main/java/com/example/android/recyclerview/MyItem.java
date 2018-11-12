package com.example.android.recyclerview;

public class MyItem {

    private String mText;
    public MyItem(String text) {
        mText = text;
    }

    public String getText() {
        return mText;
    }

    public void setText(String mText) {
        this.mText = mText;
    }

}
