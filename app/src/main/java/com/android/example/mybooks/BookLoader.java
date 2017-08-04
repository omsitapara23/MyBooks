package com.android.example.mybooks;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import java.util.List;

/**
 * Created by root on 3/8/17.
 */

public class BookLoader extends AsyncTaskLoader<List<Books>> {
    private static final String LOG_TAG = BookLoader.class.getName();
    private String mUri;

    public BookLoader(Context context,String uri){
        super(context);
        mUri=uri;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Books> loadInBackground() {
        if (mUri == null) {
            return null;
        }
        List<Books> book=FetchUtils.fetchBooksData(mUri);
        return  book;

    }
}
