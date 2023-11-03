package com.dicoding.picodiploma.loginwithanimation.view.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.data.response.ListStoryItem
import com.dicoding.picodiploma.loginwithanimation.databinding.UserStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailActivity

class UserStoryAdapter : ListAdapter<ListStoryItem,UserStoryAdapter.UserViewHolder>(DIFF_CALLBACK) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding=UserStoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val story=getItem(position)
        holder.bind(story)
    }
    class UserViewHolder(val binding: UserStoryBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(story:ListStoryItem){
            binding.tvName.text=story.name
            binding.tvDesc.text=story.description
            Glide.with(itemView.context)
                .load(story.photoUrl)
                .into(binding.imgUser)
            itemView.setOnClickListener{
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_STORY, story)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    itemView.context as Activity,
                    Pair(binding.imgUser,"profile"),
                    Pair(binding.tvName,"name"),
                    Pair(binding.tvDesc,"description")
                )
                itemView.context.startActivity(intent, options.toBundle())
            }


        }

    }


    companion object{
        val DIFF_CALLBACK=object : DiffUtil.ItemCallback<ListStoryItem>(){
            override fun areItemsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem==newItem
            }

            override fun areContentsTheSame(oldItem: ListStoryItem, newItem: ListStoryItem): Boolean {
                return oldItem==newItem
            }

        }
    }

}