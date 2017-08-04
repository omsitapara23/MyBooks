package com.android.example.mybooks;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.example.mybooks.FetchUtils.fetchBooks;

public class Book_List extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Books>>{
    public static String URL_REQUEST_BOOK="https://www.googleapis.com/books/v1/volumes?q=";
    public static String finalReaquestUrl;
    private static int Book_loader_Id=1;
    private TextView mEmptyStateTextView;
    private BooksAdapter mAdapter;
    private ListView listView;
    private TextView clickApproved;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book__list);
        mAdapter=new BooksAdapter(this,new ArrayList<Books>());
        listView=(ListView) findViewById(R.id.list);
        listView.setAdapter(mAdapter);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);
        clickApproved=(TextView) findViewById(R.id.click_approved);
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo= connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(Book_loader_Id, null, this);

        }
        else {
            View locationIndicator=(View) findViewById(R.id.loading_indicator);
            locationIndicator.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.no_internet);
        }
    }



    @Override
    public Loader<List<Books>> onCreateLoader(int id, Bundle args) {
        finalReaquestUrl=URL_REQUEST_BOOK+MainActivity.SearchQuery;
        Log.i("final requets url",finalReaquestUrl);
        return  new BookLoader(Book_List.this,finalReaquestUrl);
    }

    @Override
    public void onLoadFinished(Loader<List<Books>> loader,final List<Books> data) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        clickApproved.setVisibility(View.VISIBLE);


        if(mAdapter!=null){
            mAdapter.clear();
        }
        mEmptyStateTextView.setText(R.string.no_books);

        if (data!= null &&  !data.isEmpty()) {
            mAdapter.addAll(data);
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Books book=data.get(position);
                Uri uri=Uri.parse(book.getmUri());
                Intent intent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(intent);
            }
        });


    }

    @Override
    public void onLoaderReset(Loader<List<Books>> loader) {
        mAdapter.clear();


    }


}
