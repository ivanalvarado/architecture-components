package com.ivanalvarado.architecture_components.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ivanalvarado.architecture_components.repository.UserModel
import android.view.LayoutInflater
import android.widget.TextView
import com.ivanalvarado.architecture_components.R

class UserListAdapter constructor(private val context: Context, private val users: List<UserModel>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = inflater.inflate(R.layout.user_list_item, parent, false)
        val userName = itemView.findViewById<TextView>(R.id.user_name)
        userName.text = users[position].userName
        return itemView
    }

    override fun getItem(position: Int): UserModel {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return users.indexOf(getItem(position)).toLong()
    }

    override fun getCount(): Int {
        return users.size
    }
}
