package com.ivanalvarado.architecture_components.ui.user_list

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.ivanalvarado.architecture_components.repository.models.UserModel
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.ivanalvarado.architecture_components.R
import com.squareup.picasso.Picasso

class UserListAdapter constructor(private val context: Context, private var users: List<User>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val itemView = inflater.inflate(R.layout.user_list_item, parent, false)
        val userImage = itemView.findViewById<ImageView>(R.id.user_list_profile_image)
        val userName = itemView.findViewById<TextView>(R.id.user_list_name_text_view)
        val likeIt = itemView.findViewById<LottieAnimationView>(R.id.like_it_animation_view)
        likeIt.setOnClickListener {
            likeIt.playAnimation()
        }
        val userImageUrl = users[position].imageUrl
        userName.text = users[position].userName
        Picasso.get().load(userImageUrl).into(userImage)
        return itemView
    }

    override fun getItem(position: Int): User {
        return users[position]
    }

    override fun getItemId(position: Int): Long {
        return users.indexOf(getItem(position)).toLong()
    }

    override fun getCount(): Int {
        return users.size
    }

    fun setUserList(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }
}
