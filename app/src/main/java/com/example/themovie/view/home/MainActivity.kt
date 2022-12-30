package com.example.themovie.view.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.paging.LoadState
import androidx.paging.map
import androidx.recyclerview.widget.GridLayoutManager
import com.example.core.data.Resource
import com.example.core.domain.model.Movies
import com.example.themovie.R
import com.example.themovie.view.detail.DetailActivity
import com.example.themovie.view.home.genre.GenreAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_bottom_sheet_genre.view.*
import timber.log.Timber
import java.lang.StringBuilder
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), HomeAdapter.OnItemClickListener {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getMovies()
        onClick()
    }

    private fun onClick() {
        fabMovies.setOnClickListener {
            showBottomSheetDialog()
        }
    }

    private fun getMovies() {
        val adapter = HomeAdapter(this)
        rv_movies.setHasFixedSize(true)
        rv_movies.adapter = adapter.withLoadStateHeaderAndFooter(
            header = MoviesPhotoLoadStateAdapter { adapter.retry() },
            footer = MoviesPhotoLoadStateAdapter { adapter.retry() }
        )

        button_retry.setOnClickListener {
            adapter.retry()
        }

        adapter.addLoadStateListener { loadState ->
            linearShimmer.isVisible = loadState.source.refresh is LoadState.Loading
            rv_movies.isVisible = loadState.source.refresh is LoadState.NotLoading
            button_retry.isVisible = loadState.source.refresh is LoadState.Error
            text_view_error.isVisible = loadState.source.refresh is LoadState.Error

            if (loadState.source.refresh is LoadState.NotLoading &&
                loadState.append.endOfPaginationReached &&
                adapter.itemCount < 1
            ) {
                rv_movies.isVisible = false
                text_view_empty.isVisible = true
            } else {
                text_view_empty.isVisible = false
            }
        }
        homeViewModel.movies.observe(this, Observer {
            adapter.submitData(this.lifecycle, it)

        })
    }

    private fun showBottomSheetDialog() {
        val bottomSheetDialog = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )
        val view = LayoutInflater.from(applicationContext)
            .inflate(
                R.layout.layout_bottom_sheet_genre, findViewById(R.id.bottomSheetContainer)
            )


        val adapterGenre = GenreAdapter()

        view.imgBtnCheck.setOnClickListener {
            val sb = StringBuilder()
            for ((index, value) in adapterGenre.getDataGenre().withIndex()) {
                if (value.genres) {
                    sb.append("${value.id},")
                }
            }
            var data = ""
            if (sb.length > 0) {
                data = sb.substring(0, sb.length - 1)
            } else {
                data = sb.toString()
            }
            homeViewModel.setMoviesGenre(data)
            bottomSheetDialog.hide()
        }

        view.imgBtnClose.setOnClickListener {
            bottomSheetDialog.hide()
        }

        view.rv_genre.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = adapterGenre
        }
        showDataGenre(view, adapterGenre)
        view.buttonRetry.setOnClickListener {
            showDataGenre(view, adapterGenre)
        }


        bottomSheetDialog.setContentView(view)
        bottomSheetDialog.show()
    }

    private fun showDataGenre(view: View, adapterGenre: GenreAdapter) {
        homeViewModel.genres.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        view.linearLoading.visibility = View.VISIBLE
                        view.progress_bar.visibility = View.VISIBLE
                        view.rv_genre.visibility = View.GONE
                        view.buttonRetry.visibility = View.GONE
                        view.txtError.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        view.linearLoading.visibility = View.GONE
                        view.progress_bar.visibility = View.GONE
                        view.rv_genre.visibility = View.VISIBLE

                        adapterGenre.setData(it.data)
                    }
                    is Resource.Error -> {
                        view.linearLoading.visibility = View.VISIBLE
                        view.progress_bar.visibility = View.GONE
                        view.rv_genre.visibility = View.GONE
                        view.buttonRetry.visibility = View.VISIBLE
                        view.txtError.visibility = View.VISIBLE
                        view.txtError.text = it.message.toString()
                    }
                }
            }
        })
    }


    override fun onItemClick(movies: Movies) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.EXTRA_DATA, movies)
        startActivity(intent)
    }
}