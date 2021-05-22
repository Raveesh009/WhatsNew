package com.example.whatsnew

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class NewsListAdapter(private val listener: NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {
    private val items :ArrayList<News> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder { // It will be called as many time as there are views in a screen
       val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        // LayoutInflator basically converts xml file into view format.
        val viewHolder = NewsViewHolder(view)
        view.setOnClickListener{
             listener.onItemClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int { // It tells you how many item in a list gonna be there.
        return items.size
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) { // It will bind the data with the holder.
        val currentItem = items[position] // it will get currrent element
        holder.titleView.text =currentItem.title
        holder.author.text =currentItem.author
        Glide.with(holder.itemView.context).load(currentItem.urlToImage).into(holder.image) // glide is basically a image loader library , open source on github.
    }

    fun updateNews(updateNews:ArrayList<News>){
        items.clear()
        items.addAll(updateNews)

        notifyDataSetChanged()
    }
}

class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){    // created a ViewHolder class
    val titleView: TextView=itemView.findViewById(R.id.title) // in this viewholder we have inflate the data or to put the data.
    val image: ImageView=itemView.findViewById(R.id.image)
    val author: TextView=itemView.findViewById(R.id.author)
}

interface NewsItemClicked {
    fun onItemClicked(item:News)
}