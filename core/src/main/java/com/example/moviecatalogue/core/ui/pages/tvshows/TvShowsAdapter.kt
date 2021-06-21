package com.example.moviecatalogue.core.ui.pages.tvshows

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.academy.core.R
import com.example.academy.core.databinding.TvShowsItemBinding
import com.example.moviecatalogue.core.domain.model.Catalogue
import com.example.moviecatalogue.core.utils.DataModification

class TvShowsAdapter :
    PagedListAdapter<Catalogue, TvShowsAdapter.TvShowViewHolder>(DIFF_CALLBACK) {

    var onItemClick: ((Catalogue) -> Unit)? = null

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Catalogue>() {
            override fun areItemsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Catalogue, newItem: Catalogue): Boolean =
                oldItem == newItem
        }
    }

    inner class TvShowViewHolder(private val binding: TvShowsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(tvShow: Catalogue) {
            with(binding) {
                tvshowTitle.text = tvShow.name
                releaseDate.text = tvShow.releaseDate.let { DataModification.yearConverter(it) }
                overview.text = tvShow.overview
                Glide.with(itemView.context)
                    .load(tvShow.imgPoster)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvShowViewHolder {
        val binding = TvShowsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TvShowViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TvShowViewHolder, position: Int) {
        val tvShow = getItem(position)
        if (tvShow != null) {
            holder.bind(tvShow)
        }

    }


}