package com.android.example.mybooks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    public static String SearchQuery;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText=(EditText) findViewById(R.id.serach_field);
        Button searchButton=(Button) findViewById(R.id.Search_Button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchQuery=getText();
                Log.i("sending search query",SearchQuery);
                Intent intent=new Intent(MainActivity.this,Book_List.class);
                startActivity(intent);


            }
        });
    }

        public String getText(){
            return editText.getText().toString();
        }
}



