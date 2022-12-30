package com.example.themovie.view.detail

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.core.data.Resource
import com.example.core.domain.model.Movies
import com.example.core.utils.isRTL
import com.example.core.utils.key
import com.example.core.utils.loadImageFull
import com.example.themovie.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_detail.*

@AndroidEntryPoint
@Suppress("DEPRECATION")
class DetailActivity : AppCompatActivity() {

    private val detailViewModel: DetailViewModel by viewModels()
    private val adapter = ReviewAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val movies = intent.getParcelableExtra<Movies>(EXTRA_DATA)
        showDetailMovies(movies)
        showReview(movies)
        retry(movies)
        showDataVideo(movies)
    }


    private fun showDataVideo(movies: Movies?) {

        movies?.let {
            detailViewModel.setVideo(movies.id)
            detailViewModel.video.observe(this, Observer {
                if (it != null) {
                    when (it) {
                        is Resource.Loading -> {
                            imageView.visibility = View.VISIBLE
                        }
                        is Resource.Success -> {

                            it.data?.let { data ->
                                if (data.isNotEmpty()) {

                                    val videoId = data.get(0).key
                                    imageView.visibility = View.GONE
                                    youtube_button.visibility  = View.VISIBLE
                                    lifecycle.addObserver(youtube_button)
                                    youtube_button.addYouTubePlayerListener(object :
                                        AbstractYouTubePlayerListener() {
                                        override fun onReady(youTubePlayer: YouTubePlayer) {
                                            youTubePlayer.loadVideo(videoId, 0F)
                                            youTubePlayer.pause()
                                        }
                                    })


                                } else {
                                    youtube_button.visibility  = View.GONE
                                    imageView.visibility = View.VISIBLE
                                }
                            }
                        }
                        is Resource.Error -> {
                            showToast(getString(R.string.videonotavailable))
                            imageView.visibility = View.VISIBLE
                            youtube_button.visibility  = View.GONE
                        }
                    }
                }
            })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDetailMovies(movies: Movies?) {
        movies?.let {
            initToolbar(movies.title)
            txtDate.text = "Release date : ${movies.release_date}"
            loadImageFull(imageView, movies.poster_path)
            txtOverview.text = movies.overview
        }
    }

    private fun showReview(movies: Movies?) {
        movies?.let {
            detailViewModel.setReview(movies.id)
            rv_review.adapter = adapter
            rv_review.setHasFixedSize(true)
            setDataReview()
        }
    }

    private fun retry(movies: Movies?) {
        button_retry.setOnClickListener {
            movies?.let {
                detailViewModel.setReview(it.id)
            }
        }
    }

    private fun setDataReview() {
        detailViewModel.review.observe(this, Observer {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        showLoadingReview()
                    }
                    is Resource.Success -> {

                        it.data?.let { data ->
                            if (data.isNotEmpty()) {
                                hideLoadingReview()
                                adapter.setData(it.data)
                            } else {
                                showEmptyReview()
                            }
                        }
                    }
                    is Resource.Error -> {
                        errorLoadingReview(it.message.toString())

                    }
                }
            }
        })
    }

    private fun showEmptyReview() {
        rltvLoading.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        rv_review.visibility = View.GONE
        button_retry.visibility = View.GONE
        txtError.visibility = View.VISIBLE
        txtError.text = "No Review Available"
    }

    private fun showLoadingReview() {
        rltvLoading.visibility = View.VISIBLE
        progress_bar.visibility = View.VISIBLE
        rv_review.visibility = View.GONE
        txtError.visibility = View.GONE
        rv_review.visibility = View.GONE
        button_retry.visibility = View.GONE
    }

    private fun hideLoadingReview() {
        rltvLoading.visibility = View.GONE
        rv_review.visibility = View.VISIBLE
    }

    private fun errorLoadingReview(message: String) {
        rltvLoading.visibility = View.VISIBLE
        progress_bar.visibility = View.GONE
        rv_review.visibility = View.GONE
        button_retry.visibility = View.VISIBLE
        txtError.visibility = View.VISIBLE
        txtError.text = message

    }

    private fun initToolbar(message: String) {
        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_black_24)
        if (toolbar.navigationIcon != null) {
            toolbar.navigationIcon?.setColorFilter(
                ContextCompat.getColor(
                    this,
                    R.color.md_white_1000
                ), PorterDuff.Mode.SRC_ATOP
            )
        }
        toolbar.title = message
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.md_white_1000))
        setSupportActionBar(toolbar)

        if (supportActionBar != null)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (isRTL) {
            collapsingToolbar.collapsedTitleGravity = Gravity.END
            collapsingToolbar.expandedTitleGravity = Gravity.END or Gravity.BOTTOM
        } else {
            collapsingToolbar.collapsedTitleGravity = Gravity.START
            collapsingToolbar.expandedTitleGravity = Gravity.START or Gravity.BOTTOM
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
    fun showToast( message:String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroy() {
        super.onDestroy()
        youtube_button.release()
    }
    companion object {
        const val EXTRA_DATA = "extra_data"
    }
}