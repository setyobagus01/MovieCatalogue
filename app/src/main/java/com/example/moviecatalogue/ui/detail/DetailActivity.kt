package com.example.moviecatalogue.ui.detail

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.moviecatalogue.R
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.databinding.ActivityDetailBinding
import com.example.moviecatalogue.databinding.ContentDetailActivityBinding
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_CATEGORY = "extra_movie"
    }

    private var binding: ActivityDetailBinding? = null
    private lateinit var contentBinding: ContentDetailActivityBinding
    private val viewModel: DetailViewModel by viewModels()
    private var menu: Menu? = null
    private var extraCategory: Int? = null
    private var catalogueName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)
        supportActionBar?.title = ""

        contentBinding = binding!!.detailContent

        val extras = intent.extras
        if (extras != null) {
            binding?.contentLoading?.show()
            val id = extras.getInt(EXTRA_ID, 0)
            extraCategory = extras.getInt(EXTRA_CATEGORY, 0)
            when (extraCategory) {
                0 -> {
                    viewModel.setId(id)
                    viewModel.getMovie.observe(this, { movie ->
                        if (movie != null) {
                            when (movie) {
                                is Resource.Loading -> binding?.contentLoading?.show()
                                is Resource.Success -> {
                                    binding?.contentLoading?.hide()
                                    movie.data?.let { populateMovie(it) }
                                }
                                is Resource.Error -> {
                                    binding?.contentLoading?.hide()
                                    Toast.makeText(
                                        applicationContext,
                                        movie.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
                }
                else -> {
                    viewModel.setId(id)
                    viewModel.getTvShows.observe(this, { tvShow ->
                        if (tvShow != null) {
                            when (tvShow) {
                                is Resource.Loading -> binding?.contentLoading?.show()
                                is Resource.Success -> {
                                    binding?.contentLoading?.hide()
                                    tvShow.data?.let { populateTvShows(it) }

                                }
                                is Resource.Error -> {
                                    binding?.contentLoading?.hide()
                                    Toast.makeText(
                                        applicationContext,
                                        tvShow.message,
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    })
                }
            }
        }

        binding?.toolbar?.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu
        when (extraCategory) {
            0 -> {
                viewModel.getMovie.observe(this, { movie ->
                    if (movie != null) {
                        when (movie) {
                            is Resource.Loading -> binding?.contentLoading?.show()
                            is Resource.Success -> {
                                binding?.contentLoading?.hide()
                                movie.data?.let { setBookmarkState(it.isFavorite) }
                            }
                            is Resource.Error -> {
                                binding?.contentLoading?.hide()
                                Toast.makeText(
                                    applicationContext,
                                    movie.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    }
                })

            }
            else -> {
                viewModel.getTvShows.observe(this, { tvShow ->
                    if (tvShow != null) {
                        when (tvShow) {
                            is Resource.Loading -> binding?.contentLoading?.show()
                            is Resource.Success -> {
                                binding?.contentLoading?.hide()
                                tvShow.data?.let { setBookmarkState(it.isFavorite) }

                            }
                            is Resource.Error -> {
                                binding?.contentLoading?.hide()
                                Toast.makeText(
                                    applicationContext,
                                    tvShow.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                })
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (extraCategory) {
            0 -> {
                when (item.itemId) {
                    R.id.share -> {
                        val shareIntent = Intent.createChooser(Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "$catalogueName is a good movie")
                            type = "text/plain"
                        }, null)

                        startActivity(shareIntent)
                    }
                    R.id.action_favorite -> {
                        viewModel.setFavoriteMovie()
                    }
                }
            }
            else -> {
                when (item.itemId) {
                    R.id.share -> {
                        val shareIntent = Intent.createChooser(Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT, "$catalogueName is a good tv show")
                            type = "text/plain"
                        }, null)

                        startActivity(shareIntent)
                    }
                    R.id.action_favorite -> {
                        viewModel.setFavoriteTvShow()
                    }
                }
            }
        }
        return true
    }

    private fun setBookmarkState(favorite: Boolean) {
        if (menu == null) return
        val menuItem = menu?.findItem(R.id.action_favorite)
        if (favorite) {
            menuItem?.icon = ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24)
        } else {
            menuItem?.icon =
                ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24)
        }
    }


    private fun populateMovie(movie: Catalogue) {
        val duration = StringBuilder(movie.duration.toString()).append("m")
        catalogueName = movie.name
        binding?.contentTitle?.text = movie.name
        binding?.contentGenres?.text = movie.genres
        contentBinding.contentScore.text = movie.userScore.toString()
        contentBinding.contentDuration.text = duration
        contentBinding.contentTagline.text = movie.tagline
        contentBinding.contentOverview.text = movie.overview
        binding?.let {
            Glide.with(this@DetailActivity)
                .load(movie.imgPoster)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(it.imgPoster)
        }

    }

    private fun populateTvShows(tvShow: Catalogue) {
        val duration = StringBuilder().apply {
            if (tvShow.duration.isNullOrEmpty()) {
                append("0m/episode")
            } else {
                append("${tvShow.duration}m/episode")
            }
        }
        catalogueName = tvShow.name
        if (TextUtils.isEmpty(tvShow.tagline)) contentBinding.contentTagline.visibility = View.GONE

        binding?.contentTitle?.text = tvShow.name
        binding?.contentGenres?.text = tvShow.genres
        contentBinding.contentScore.text = tvShow.userScore.toString()
        contentBinding.contentDuration.text = duration

        contentBinding.contentTagline.text = tvShow.tagline
        contentBinding.contentOverview.text = tvShow.overview
        binding?.let {
            Glide.with(this@DetailActivity)
                .load(tvShow.imgPoster)
                .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                .error(R.drawable.ic_error)
                .into(it.imgPoster)
        }
    }
}