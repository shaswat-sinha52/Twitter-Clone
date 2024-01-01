package com.example.twitterclone.dataclass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.twitterclone.R
import com.example.twitterclone.TweetFragment
import de.hdodenhof.circleimageview.CircleImageView

class TweetAdapter(
    private val listoftweets:List<Tweet>
):RecyclerView.Adapter<TweetAdapter.viewholder>() {

    class viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        val tweet:TextView=itemview.findViewById(R.id.alltweet_tweet)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_tweet,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return listoftweets.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currenttweet=listoftweets[position]
        holder.tweet.text=currenttweet.tweetContent






    }

}