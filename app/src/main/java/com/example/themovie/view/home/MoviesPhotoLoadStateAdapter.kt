package com.example.themovie.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.themovie.R
import kotlinx.android.synthetic.main.movies_load_state_footer.view.*
import kotlinx.android.synthetic.main.movies_load_state_footer.view.button_retry

class MoviesPhotoLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<MoviesPhotoLoadStateAdapter.LoadStateViewHolder>() {


    inner class LoadStateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.button_retry.setOnClickListener{
                retry.invoke()
            }
        }
        fun bind(loadState: LoadState){

           itemView.apply {
               progress_bar.isVisible = loadState is LoadState.Loading
               button_retry.isVisible = loadState !is LoadState.Loading
               text_view_error.isVisible = loadState !is LoadState.Loading
           }
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
            holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder =
        LoadStateViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.movies_load_state_footer, parent, false))
}