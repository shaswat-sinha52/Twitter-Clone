package com.example.twitterclone.dataclass

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide

import com.example.twitterclone.R
import de.hdodenhof.circleimageview.CircleImageView

class SuggestedAccountAdapter(
    private val listofaccount:List<suggestedAccount>,
    private val context: Context,
    private val clickListner: ClickListner
):RecyclerView.Adapter<SuggestedAccountAdapter.viewholder>() {

    class viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        val image:CircleImageView=itemview.findViewById(R.id.suggested_account_profile)
        val email:TextView=itemview.findViewById(R.id.suggested_account_email)
        val btnfollow:Button=itemview.findViewById(R.id.suggested_Account_follow)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewholder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.layout_suggested_account,parent,false)
        return viewholder(view)
    }

    override fun getItemCount(): Int {
        return listofaccount.size
    }

    override fun onBindViewHolder(holder: viewholder, position: Int) {
        val currentuser=listofaccount[position]
        holder.email.text=currentuser.email
        Glide.with(context)
            .load(currentuser.profile)
            .into(holder.image)
        holder.btnfollow.setOnClickListener {
            clickListner.onfollowclicked(currentuser.uid)

        }

    }
    interface ClickListner{
        fun onfollowclicked(uid:String)
    }
}