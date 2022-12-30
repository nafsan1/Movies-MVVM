package com.example.themovie.view.home.genre

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.model.Genre
import com.example.themovie.R
import kotlinx.android.synthetic.main.item_list_genre.view.*
import java.util.ArrayList

class GenreAdapter :
    RecyclerView.Adapter<GenreAdapter.ListViewHolder>() {

    private var listData = ArrayList<Genre>()
    private var activePostion = 10101010


    fun setData(newListData: List<Genre>?) {
        if (newListData == null) return
        listData.clear()
        listData.addAll(newListData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ListViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_list_genre, parent, false)
        )

    override fun getItemCount() = listData.size

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listData[position]
        holder.bind(data, position)
    }

    fun getDataGenre(): ArrayList<Genre> =
        listData

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Genre, position: Int) {
            with(itemView) {
                buttonGenre.text = data.name
               // if (activePostion != 10101010) {
                    if (data.genres) {
                        buttonGenre.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.md_white_1000
                            )
                        )
                        buttonGenre.background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.btn_rounded_orange_line
                        )
                    } else {
                        buttonGenre.setTextColor(
                            ContextCompat.getColor(
                                itemView.context,
                                R.color.md_orange_500
                            )
                        )
                        buttonGenre.background = ContextCompat.getDrawable(
                            itemView.context,
                            R.drawable.btn_rounded_orange_outline
                        )
                    }
              //  }

                buttonGenre.setOnClickListener {
                    activePostion = position

                    if (data.genres) {
                        data.genres = false
                    } else {
                        data.genres = true
                    }
                    notifyDataSetChanged()

                }

            }
        }
    }


}