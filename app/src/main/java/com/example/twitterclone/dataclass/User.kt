package com.example.twitterclone.dataclass

data class User(
    val userEmail:String="",
    val userprofileimage:String="",
    val listofFollowing:List<String> = listOf(),
    val listofTweets:List<String> = listOf(),
    val uid:String=""
)
