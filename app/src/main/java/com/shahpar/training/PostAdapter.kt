package com.shahpar.training

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.shahpar.training.databinding.ViewholderPostBinding

data class PostModel(val username: String, val image: String, val comment: String)

class PostAdapter(private var postList: ArrayList<PostModel>, private val context: Activity) :
    ArrayAdapter<PostModel>(context, R.layout.viewholder_post, postList) {

    lateinit var binding: ViewholderPostBinding

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = LayoutInflater.from(parent.context)
        binding = ViewholderPostBinding.inflate(inflater, parent, false)

        binding.txtUsername.text = postList[position].username
        binding.txtComment.text = postList[position].comment
        Glide.with(context).load(postList[position].image).into(binding.imgPicture)

        return binding.root
    }

    fun addItem(postItem: PostModel) {
        postList.add(postItem)
    }
}