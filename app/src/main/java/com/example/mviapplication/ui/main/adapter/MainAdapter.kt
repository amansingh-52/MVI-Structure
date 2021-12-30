package com.example.mviapplication.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mviapplication.R
import com.example.mviapplication.data.model.User

class MainAdapter(
    private val context : Context,
    private val users: ArrayList<User>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {


    inner class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val userName: TextView = itemView.findViewById(R.id.textViewUserName)
        private val userEmail: TextView = itemView.findViewById(R.id.textViewUserEmail)
        private val userAvatar: ImageView = itemView.findViewById(R.id.imageViewAvatar)


        fun bind(user: User) {
            userName.text = user.name
            userEmail.text = user.email
            Glide.with(context).load(user.avatar).placeholder(R.drawable.ic_baseline_account_circle_24).into(userAvatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataViewHolder =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout, parent, false
            )
        )

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size

    fun addData(list: List<User>) {
        users.addAll(list)
    }

}