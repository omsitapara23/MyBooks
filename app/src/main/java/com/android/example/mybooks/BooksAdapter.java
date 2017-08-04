package com.android.example.mybooks;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static android.R.attr.start;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by root on 3/8/17.
 */

public class BooksAdapter extends ArrayAdapter<Books> {

    public BooksAdapter(Context context, ArrayList<Books> books){
        super(context,0,books);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem==null){
            listItem= LayoutInflater.from(getContext()).inflate(R.layout.adapter_inflate,parent,false);
        }
        final Books currentBook=getItem(position);
        TextView titleText=(TextView) listItem.findViewById(R.id.title);
        titleText.setText(currentBook.getTitle());

        TextView authorText=(TextView) listItem.findViewById(R.id.author);
        authorText.setText("By:"+currentBook.getAuthor());

        ImageView imageView=(ImageView) listItem.findViewById(R.id.Image);
        imageView.setImageResource(R.drawable.image);

        return listItem;
    }


}
