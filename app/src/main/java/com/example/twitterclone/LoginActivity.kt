package com.example.twitterclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.twitterclone.dataclass.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.database

class LoginActivity : AppCompatActivity() {
    private lateinit var enterEmail:EditText
    private lateinit var enterpassword:EditText
    private lateinit var btnsignup:Button
    private lateinit var btnlogin:Button
    private lateinit var mauth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        init()
        if (mauth.currentUser !=null){
            startActivity(
                Intent(this,home::class.java)
            )
            finish()
        }


        btnlogin.setOnClickListener {
            val email=enterEmail.text.toString()
            val password=enterpassword.text.toString()
            login(email,password)
        }
        btnsignup.setOnClickListener {
            val email=enterEmail.text.toString()
            val password=enterpassword.text.toString()
            signup(email,password)
        }
    }
    private fun login(email:String,password:String)
    {
        mauth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this)
            { task ->
                if (task.isSuccessful)
                {
                    val listoffollowing= mutableListOf<String>()
                    listoffollowing.add("")
                    val listofTweets= mutableListOf<String>()
                    listofTweets.add("")

                    val users=User(
                        userEmail = email,
                        userprofileimage = "",
                        listofFollowing=listoffollowing,
                        listofTweets = listofTweets,
                        uid = mauth.uid.toString()
                    )

                    Addusertodatabase(users)
                    startActivity(
                        Intent(this,home::class.java)
                    )
                    finish()
                    Toast.makeText(this,"Login Successful",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,"Wrong Credentials",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun Addusertodatabase(user: User){
        Firebase.database.getReference("Users").child(user.uid).setValue(user)
    }
    private fun signup(email: String,password: String){
        mauth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this)
            { task ->
                if (task.isSuccessful)
                {
                    Toast.makeText(this,"Account Created",Toast.LENGTH_SHORT).show()
                }
                else
                {
                    Toast.makeText(this,"Enter Valid Details",Toast.LENGTH_SHORT).show()

                }
            }
    }
    private fun init(){
        btnsignup=findViewById(R.id.btn_signup)
        btnlogin=findViewById(R.id.btn_login)
        enterEmail=findViewById(R.id.edt_enter_email)
        enterpassword=findViewById(R.id.edt_password_enter)
        mauth=Firebase.auth
    }

}