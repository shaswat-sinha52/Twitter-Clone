package com.example.twitterclone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import com.bumptech.glide.Glide
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView
import java.util.UUID

class ProfileActivity : AppCompatActivity() {
    private lateinit var opengallery:Button
    private lateinit var profileimage: CircleImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        init()

        opengallery.setOnClickListener {
            val intent=Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent,101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==101 && resultCode== RESULT_OK){
            profileimage.setImageURI(data?.data)
            uploadprofileimage(data?.data)

        }
    }
    private fun uploadprofileimage(uri: Uri?){
        val profileimagename=UUID.randomUUID().toString()+".jpg"
        val storageref=FirebaseStorage.getInstance().getReference().child("profileimage/$profileimagename")

        storageref.putFile(uri!!).addOnSuccessListener {
            val result=it.metadata?.reference?.downloadUrl
            result?.addOnSuccessListener {
                FirebaseDatabase.getInstance().reference.child("Users").child(Firebase.auth.uid.toString())
                    .child("userprofileimage").setValue(it.toString())
            }
        }

    }
    private fun init(){
        opengallery=findViewById(R.id.btn_gallery)
        profileimage=findViewById(R.id.profile_image)

        FirebaseDatabase.getInstance().reference.child("Users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val link=snapshot.child("userprofileimage").value.toString()
                    if (!link.isNullOrBlank()){
                        Glide.with(this@ProfileActivity)
                            .load(link)
                            .into(profileimage)
                    }
                    else{
                        profileimage.setImageResource(R.drawable.background)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }


            })
    }
}