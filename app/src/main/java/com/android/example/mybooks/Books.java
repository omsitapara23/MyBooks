package com.android.example.mybooks;

import android.graphics.Bitmap;
import android.widget.ImageView;



/**
 * Created by root on 3/8/17.
 */

public class Books {
    private String mTitle,mAuthor,mUri;

    public Books(String title,String Author,String Uri){

        mTitle=title;
        mAuthor=Author;
        mUri=Uri;

    }

    public String getTitle(){ return mTitle;}
    public String getAuthor(){ return mAuthor;}
    public String getmUri(){ return mUri;}
}
