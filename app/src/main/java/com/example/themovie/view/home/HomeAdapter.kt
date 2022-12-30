package com.example.themovie.view.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Movies
import com.example.core.utils.loadImage
import com.example.themovie.R
import kotlinx.android.synthetic.main.item_list_movies.view.*
import timber.log.Timber
import java.util.ArrayList

class HomeAdapter(private val listener: OnItemClickListener) :
    PagingDataAdapter<Movies, HomeAdapter.ListViewHolder>(MOVIES_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_movies, parent, false)
        )


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = getItem(position)
        if (data != null) {
            holder.bind(data)

        }
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Movies) {
            with(itemView) {
                txtTitle.text = data.title
                txtDate.text = data.release_date
                txtOverview.text = data.overview
                data.poster_path.let { loadImage(imageView, it) }

            }
            itemView.setOnClickListener {
                listener.onItemClick(data)
            }
        }

    }

    interface OnItemClickListener {
        fun onItemClick(movies: Movies)
    }

    companion object {
        private val MOVIES_COMPARATOR = object : DiffUtil.ItemCallback<Movies>() {
            override fun areItemsTheSame(oldItem: Movies, newItem: Movies) =
                oldItem.id == newItem.id


            override fun areContentsTheSame(oldItem: Movies, newItem: Movies) =
                oldItem.id == newItem.id

        }
    }

}