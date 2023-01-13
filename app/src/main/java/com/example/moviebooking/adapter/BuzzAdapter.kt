package com.example.moviebooking.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.browser.customtabs.CustomTabsIntent
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviebooking.networking.model.Article
import com.example.moviebooking.R


class BuzzAdapter(private val buzzList: List<Article>, val context: Context, val recyclerView: RecyclerView) : RecyclerView.Adapter<BuzzAdapter.BuzzViewHolder>() {
    class BuzzViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var buzzImage : AppCompatImageView = itemView.findViewById(R.id.buzzImage)
        var cardView : CardView = itemView.findViewById(R.id.buzzCardView)
        var buzzTitle : TextView = itemView.findViewById(R.id.buzzTitle)
        //var BuzzShare : ImageButton = itemView.findViewById(R.id.newsImageButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuzzViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.buzz_design, parent, false)
        return BuzzViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuzzViewHolder, position: Int) {
        val currentBuzz = buzzList[position]
        Glide.with(context).load(currentBuzz.urlToImage).into(holder.buzzImage)
        holder.buzzTitle.text= currentBuzz.title
        holder.cardView.setOnClickListener {
            val url: String = currentBuzz.url
           //val builder = CustomTabsIntent.Builder()
           //val customTabsIntent = builder.build()
           //customTabsIntent.launchUrl(context, Uri.parse(url))
           //val customTabsSession = CustomTabsSession()
            val customTabsIntent = CustomTabsIntent.Builder()
                .build();
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            customTabsIntent.launchUrl(context, Uri.parse(url))
        }
    }
    override fun getItemCount(): Int {
        return buzzList.size
    }

}
