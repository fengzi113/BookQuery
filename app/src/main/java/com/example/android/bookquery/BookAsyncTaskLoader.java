package com.example.android.bookquery;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import java.util.List;


public class BookAsyncTaskLoader extends AsyncTaskLoader<List<Book>> {
    String murl = null;
    private static final String SAMPLE_URL1 =
            "https://api.douban.com/v2/book/search?q";

    public BookAsyncTaskLoader(Context context, Bundle args) {
        super(context);
        if (args != null) {
            murl = args.getString("url");
        } else {
            murl = null;
        }
        Log.d("BookAsyncTaskLoader", "=============BookAsyncTaskLoader=======" + murl);
    }

    @Override
    public List<Book> loadInBackground() {
        Log.d("BookAsyncTaskLoader", "==============loadInBackground==============" + murl);
        if (murl != null) {
            return QueryUtils.fetchBookData(murl);
        } else {
            return null;
        }

    }

    @Override
    protected void onStartLoading() {
        Log.d("BookAsyncTaskLoader", "==============loadInBackground==============");
        forceLoad();
    }
}
