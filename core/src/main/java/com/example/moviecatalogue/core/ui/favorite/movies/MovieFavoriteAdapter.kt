package com.example.moviecatalogue.core.ui.favorite.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.academy.core.R
import com.example.academy.core.databinding.MovieItemBinding
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.utils.DataModification

class MovieFavoriteAdapter :
    PagedListAdapter<Catalogue, MovieFavoriteAdapter.MovieFavoriteViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Catalogue) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Catalogue>() {
            override fun areItemsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean =
                oldItem == newItem
        }
    }


    inner class MovieFavoriteViewHolder(private val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: Catalogue) {
            with(binding) {
                movieTitle.text = movie.name
                releaseDate.text = movie.releaseDate.let { DataModification.yearConverter(it) }
                overview.text = movie.overview
                Glide.with(itemView.context)
                    .load(movie.imgPoster)
                    .apply(RequestOptions.placeholderOf(R.drawable.ic_loading))
                    .error(R.drawable.ic_error)
                    .into(imgPoster)
            }


        }

        init {
            binding.root.setOnClickListener {
                getItem(bindingAdapterPosition)?.let { it1 -> onItemClick?.invoke(it1) }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieFavoriteViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MovieFavoriteViewHolder,
        position: Int
    ) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }



    fun getSwipedData(swipedPosition: Int): Catalogue? = getItem(swipedPosition)

}