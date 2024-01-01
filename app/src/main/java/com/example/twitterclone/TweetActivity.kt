package com.example.twitterclone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TweetActivity : AppCompatActivity() {
    private lateinit var tweetbox:EditText
    private lateinit var tweetupload:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)
        init()

        tweetupload.setOnClickListener {
            val tweet=tweetbox.text.toString()
            uploadtweet(tweet)
        }
    }
    private fun uploadtweet(tweet:String){
        FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoftweets=snapshot.child("listofTweets").value as MutableList<String>
                    listoftweets.add(tweet)
                    uploadtweetlist(listoftweets)
                }

                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }

            })
    }
    private fun uploadtweetlist(listoftweets:List<String>){
        FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.uid.toString())
            .child("listofTweets").setValue(listoftweets)
        Toast.makeText(this,"Tweet Uploaded",Toast.LENGTH_SHORT).show()

    }
    private fun init(){
        tweetbox=findViewById(R.id.edt_tweet_box)
        tweetupload=findViewById(R.id.btn_tweet_upload)
    }
}