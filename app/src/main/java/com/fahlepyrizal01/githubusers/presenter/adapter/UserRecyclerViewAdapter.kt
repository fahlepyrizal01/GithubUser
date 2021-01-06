package com.fahlepyrizal01.githubusers.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fahlepyrizal01.core.domain.model.User
import com.fahlepyrizal01.githubusers.R
import com.fahlepyrizal01.githubusers.databinding.ItemUserBinding

class UserRecyclerViewAdapter : PagedListAdapter<User, UserRecyclerViewAdapter.ListViewHolder>(
    DIFF_CALLBACK
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder =
        ListViewHolder(
            ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun getItemViewType(position: Int) = position

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class ListViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        fun bind(data: User) = with(binding) {
            Glide.with(itemView.context).load(data.avatarUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .into(ivAvatar)

            tvName.text = data.login
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<User> =
            object : DiffUtil.ItemCallback<User>() {
                override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                    val old = oldItem.id
                    val new = newItem.id

                    return old == new
                }

                override fun areContentsTheSame(oldItem: User, newItem: User) = oldItem == newItem
            }
    }

}