package com.example.android.bookquery;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Book>> {
    ArrayAdapter<Book> mAdapter;
    private static final String SAMPLE_URL = "https://api.douban.com/v2/book/search?q=";
    private boolean isConnected;
    private EditText name;
    private TextView emptyView;
    private String url;
    private Bundle bundle = null;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 添加下面的语句，让 MainActivity 能在每次转屏后获取已经加载的 Loader
        getLoaderManager().initLoader(0, bundle, MainActivity.this);

        //指示器隐藏
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);
        //设置空视图
        emptyView = (TextView) findViewById(R.id.empty_view);
        emptyView.setText(getResources().getString(R.string.no_book_found));
        ListView bookListView = (ListView) findViewById(R.id.list);
        bookListView.setEmptyView(emptyView);

        mAdapter = new BookAdapter(this, new ArrayList<Book>());
        bookListView.setAdapter(mAdapter);
        //判断网络是否连接
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        emptyView = (TextView) findViewById(R.id.empty_view);
        name = (EditText) findViewById(R.id.bookSearchName);
        //设置按钮监听器
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //隐藏键盘
                InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(name.getWindowToken(), 0);
                url = SAMPLE_URL + name.getText().toString();
                bundle = new Bundle();
                bundle.putString("url", url);
                if (isConnected == true && !name.getText().toString().isEmpty()) {
                    Log.d("MainActivity", "========restartLoader==============");
                    progressBar.setVisibility(View.VISIBLE);
                    url = SAMPLE_URL + name.getText().toString();
                    bundle = new Bundle();
                    bundle.putString("url", url);
                    getLoaderManager().restartLoader(0, bundle, MainActivity.this).forceLoad();
                } else if(name.getText().toString().isEmpty()){
                    mAdapter.clear();
                    emptyView.setText(getString(R.string.no_input));
                    progressBar.setVisibility(View.GONE);
                }else{
                    mAdapter.clear();
                    emptyView.setText(getString(R.string.no_network));
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public Loader<List<Book>> onCreateLoader(int i, Bundle bundle) {
        Log.d("MainActivity", "========onCreateLoader==============");
        return new BookAsyncTaskLoader(MainActivity.this, bundle);
    }

    @Override
    public void onLoadFinished(Loader<List<Book>> loader, List<Book> books) {
        Log.d("MainActivity", "========onLoadFinishedIN==============");
        // 清除之前数据
        mAdapter.clear();
        //指示器隐藏
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progressbar);
        progressBar.setVisibility(View.GONE);

        if (books != null && !books.isEmpty()) {
            Log.d("MainActivity", "========isEmpty==============");
            mAdapter.addAll(books);
        }
        Log.d("MainActivity", "========onLoadFinished==============");
    }

    @Override
    public void onLoaderReset(Loader<List<Book>> loader) {
        Log.d("MainActivity", "========onLoaderReset==============");
        mAdapter.clear();
        return;
    }

}
