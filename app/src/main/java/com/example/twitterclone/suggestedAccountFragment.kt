package com.example.twitterclone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.twitterclone.dataclass.SuggestedAccountAdapter
import com.example.twitterclone.dataclass.User
import com.example.twitterclone.dataclass.suggestedAccount
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class suggestedAccountFragment:Fragment(),SuggestedAccountAdapter.ClickListner {
    private lateinit var suggestedAccountAdapter: SuggestedAccountAdapter
    private lateinit var rvsuggestedAccount:RecyclerView
    private var listofAccounts= mutableListOf<suggestedAccount>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_suggested_account,container,false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvsuggestedAccount=view.findViewById(R.id.rvsuggested)

        FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoffollowings=snapshot.child("listofFollowing").value as MutableList<String>

                    FirebaseDatabase.getInstance().getReference().child("Users")
                        .addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (datasnapshot in snapshot.children){
                                    val user=datasnapshot.getValue(User::class.java)

                                    if (user?.uid.toString() !=FirebaseAuth.getInstance().uid.toString() && !listoffollowings.contains(user?.uid.toString())){
                                        val suggestedAccount=suggestedAccount(user?.userprofileimage.toString(),user?.userEmail.toString(),user?.uid.toString())
                                        listofAccounts.add(suggestedAccount)
                                        suggestedAccountAdapter= SuggestedAccountAdapter(listofAccounts,requireContext(),this@suggestedAccountFragment)
                                        rvsuggestedAccount.adapter=suggestedAccountAdapter
                                        rvsuggestedAccount.layoutManager=LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
                                    }
                                }
                            }
                            override fun onCancelled(error: DatabaseError) {
                                //TODO("Not yet implemented")
                            }
                        })
                }
                override fun onCancelled(error: DatabaseError) {
                    //TODO("Not yet implemented")
                }
            })
    }
    private fun followUser(uid:String){
        FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.uid.toString())
            .addListenerForSingleValueEvent(object :ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val listoffollowings = snapshot.child("listofFollowing").value as? MutableList<String> ?: mutableListOf()
                    listoffollowings.add(uid)
                    FirebaseDatabase.getInstance().getReference().child("Users").child(Firebase.auth.uid.toString())
                        .child("listofFollowing")  // Corrected child name
                        .setValue(listoffollowings)

                    Toast.makeText(requireContext(),"Followed Successfully",Toast.LENGTH_SHORT).show()
                }
                override fun onCancelled(error: DatabaseError) {
                   // TODO("Not yet implemented")
                }
            })
    }
    override fun onfollowclicked(uid:String) {
        followUser(uid)
    }
}