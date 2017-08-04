package com.android.example.mybooks;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

import static com.android.example.mybooks.MainActivity.LOG_TAG;

/**
 * Created by root on 3/8/17.
 */

public class FetchUtils {
    private FetchUtils(){

    }
    public  static ArrayList<Books> fetchBooksData(String request_url) {


        URL url=createUrl(request_url);try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String jsonResponse="";
        jsonResponse=makeHttpRequest(url);
        ArrayList<Books> Books=fetchBooks(jsonResponse);
        return  Books;

    }


    private static URL createUrl(String stringUrl){
        URL url=null;
        try {
            url=new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
        return url;
    }

    private static String makeHttpRequest(URL url){
        String jsonResponse="";
        if(url==null){
            return jsonResponse;
        }
        HttpURLConnection httpURLConnection=null;
        InputStream inputStream=null;
        try {
            httpURLConnection=(HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.connect();
            if(httpURLConnection.getResponseCode()==200){
                inputStream = httpURLConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Error response code: " + httpURLConnection.getResponseCode());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(url!=null){
                httpURLConnection.disconnect();
            }
            if(inputStream!=null){
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream)throws IOException {
        StringBuilder output=new StringBuilder();
        if(inputStream!=null){
            InputStreamReader inputStreamReader=new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader=new BufferedReader(inputStreamReader);
            String line=bufferedReader.readLine();
            while(line!=null){
                output.append(line);
                line=bufferedReader.readLine();
            }
        }
        return  output.toString();
    }



    public static ArrayList<Books> fetchBooks(String jsonResponse){
        ArrayList<Books> book=new ArrayList<>();
        try {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONArray booksArray=jsonObject.getJSONArray("items");
            for (int i = 0; i <booksArray.length() ; i++){
                JSONObject currentBook= booksArray.getJSONObject(i);
                JSONObject volumeInfo=currentBook.getJSONObject("volumeInfo");
                String title= volumeInfo.getString("title");
                String uri=volumeInfo.getString("previewLink");
                JSONArray bookAuthors=null;
                try{
                    bookAuthors=volumeInfo.getJSONArray("authors");
                }catch (JSONException ignored){

                }
                String bookAuthorsString = "";
                // If the author is empty, set it as "Unknown"
                if (bookAuthors == null) {
                    bookAuthorsString = "Unknown";
                } else {
                    // Format the authors as "author1, author2, and author3"
                    int countAuthors = bookAuthors.length();
                    for (int e = 0; e < countAuthors; e++) {
                        String author = bookAuthors.getString(e);
                        if (bookAuthorsString.isEmpty()) {
                            bookAuthorsString = author;
                        } else if (e == countAuthors - 1) {
                            bookAuthorsString = bookAuthorsString + " and " + author;
                        } else {
                            bookAuthorsString = bookAuthorsString + ", " + author;
                        }


                    }
                }








                Books books=new Books(title,bookAuthorsString,uri);
                book.add(books);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return book;
    }
}
