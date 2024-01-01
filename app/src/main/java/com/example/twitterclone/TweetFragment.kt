package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.dataclass.Tweet
import com.example.twitterclone.dataclass.TweetAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetFragment:Fragment() {

    private lateinit var tweetAdapter: TweetAdapter
    private lateinit var rvTweet:RecyclerView
    private val listoftweets= mutableListOf<Tweet>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view=inflater.inflate(R.layout.fragment_tweet,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTweet=view.findViewById(R.id.rvtweet)

        FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoffollowingsuids=snapshot.child("listofFollowing").value as MutableList<String>
                    listoffollowingsuids.forEach{
                        getTweetfromuids(it)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })
    }
    private fun getTweetfromuids(uid:String){
        FirebaseDatabase.getInstance().getReference().child("Users").child(uid)
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var tweetlist= mutableListOf<String>()
                    snapshot.child("listofTweets").value?.let {
                        tweetlist=it as MutableList<String>
                    }

                    tweetlist.forEach{
                        if(!it.isNullOrBlank()){
                            listoftweets.add(Tweet(it))
                        }
                    }
                    tweetAdapter= TweetAdapter(listoftweets)
                    rvTweet.layoutManager=LinearLayoutManager(requireContext())
                    rvTweet.adapter=tweetAdapter


                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })

    }
}