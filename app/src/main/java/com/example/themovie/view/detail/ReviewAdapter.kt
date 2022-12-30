package com.example.themovie.view.detail

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Genre
import com.example.core.domain.model.Review
import com.example.themovie.R
import kotlinx.android.synthetic.main.item_list_genre.view.*
import kotlinx.android.synthetic.main.item_list_review.view.*
import java.util.ArrayList

class ReviewAdapter :
    RecyclerView.Adapter<ReviewAdapter.ListViewHolder>() {

    private var listData = ArrayList<Review>()

    fun setData(newListData: List<Review>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_review, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data)
    }


    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun bind(data: Review) {
            with(itemView) {

                txtName.text = "A review by ${data.author}"
                txtWritten.text = "Written by ${data.author}"
                txtReview.text = data.content

            }
        }
    }


}