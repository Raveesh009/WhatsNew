package com.example.whatsnew

import MySingleton
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter // to make the variable global

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       recyclerView.layoutManager= LinearLayoutManager(this) // It tells that the app has only linear layout(vertical)
         fetchData()
        mAdapter = NewsListAdapter(this) // object
        recyclerView.adapter= mAdapter // connecting recylerView with adapter
    }
    // To call the api volley library is used.
    private fun fetchData(){
       val url ="https://saurav.tech/NewsAPI/top-headlines/category/health/in.json" // API ,that this app is using
       val jsonObjectRequest = JsonObjectRequest(
           Request.Method.GET,
           url,
           null,
           Response.Listener{
            val newsJsonArray = it.getJSONArray("articles")
               val newsArray = ArrayList<News>()
               for(i in 0 until newsJsonArray.length()){
                   val newsJsonObject =newsJsonArray.getJSONObject(i)
                   val news =News(
                       newsJsonObject.getString("title"),
                       newsJsonObject.getString("author"),
                       newsJsonObject.getString("url"),
                       newsJsonObject.getString("urlToImage")
                   )
                   newsArray.add(news)
               }
               mAdapter.updateNews(newsArray)
           },
           Response.ErrorListener{

           }
       )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest) // Mysingleton class is used just to have 1 instance of a volley
    }

    override fun onItemClicked(item: News) {

        val builder =  CustomTabsIntent.Builder(); // customTabsIntent helps to open the browser in your app
        val customTabsIntent  = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.url));


    }
}