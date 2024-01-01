package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class home : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var vPadapter: VPadapter
    private lateinit var addbutton:FloatingActionButton
    private lateinit var mauth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        init()

        addbutton.setOnClickListener{
            startActivity(
                Intent(this,TweetActivity::class.java)
            )
        }

        TabLayoutMediator(tabLayout,viewPager){tab:TabLayout.Tab,position:Int ->
            when(position){
                0->tab.text="People You May Know"
                else ->tab.text="Tweets"
            }
        }.attach()

    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.loginmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        when (item.itemId)
        {
            R.id.Logout ->
            {
                mauth.signOut()

                startActivity(
                    Intent(this,LoginActivity::class.java)
                )
                finish()
            }
            R.id.Profile ->{
                startActivity(
                    Intent(this,ProfileActivity::class.java)
                )
            }
        }
        return true
    }
    private fun init(){
        mauth=Firebase.auth
        addbutton=findViewById(R.id.floatingbtn)
        vPadapter=VPadapter(this)
        viewPager=findViewById(R.id.viewpager)
        viewPager.adapter=vPadapter
        tabLayout=findViewById(R.id.tablayout)
    }

}