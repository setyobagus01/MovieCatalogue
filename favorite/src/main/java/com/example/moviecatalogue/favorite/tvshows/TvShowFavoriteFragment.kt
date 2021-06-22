package com.example.moviecatalogue.favorite.tvshows

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviecatalogue.core.ui.favorite.tvshows.TvShowFavoriteAdapter
import com.example.moviecatalogue.di.FavoriteModuleDependencies
import com.example.moviecatalogue.favorite.ViewModelFactory
import com.example.moviecatalogue.favorite.databinding.FragmentTvShowFavoriteBinding
import com.example.moviecatalogue.favorite.di.DaggerFavoriteComponent
import com.example.moviecatalogue.ui.detail.DetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject


class TvShowFavoriteFragment : Fragment() {
    private var _binding: FragmentTvShowFavoriteBinding? = null
    private val binding get() = _binding as FragmentTvShowFavoriteBinding

    private var tvShowsAdapter: TvShowFavoriteAdapter? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: TvShowFavoriteViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        DaggerFavoriteComponent.builder()
            .context(context)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    context.applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemTouchHelper.attachToRecyclerView(binding.rvTvShowFavorite)
        if (activity != null) {
            tvShowsAdapter = TvShowFavoriteAdapter()
            viewModel.favoriteTvShows.observe(viewLifecycleOwner, { tvShows ->
                tvShowsAdapter?.submitList(tvShows)
                binding.noData.visibility = if (tvShows.isNotEmpty()) View.GONE else View.VISIBLE

            })
            with(binding.rvTvShowFavorite) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowsAdapter

            }

            tvShowsAdapter?.onItemClick = { selectedData ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, selectedData.id)
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, 1)
                startActivity(intent)
            }
        }
    }

    private val itemTouchHelper =

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT)

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = true

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                if (view != null) {
                    val swipedPosition = viewHolder.bindingAdapterPosition
                    val tvShow = tvShowsAdapter?.getSwipedData(swipedPosition)
                    tvShow?.let { viewModel.setFavoriteTvShow(it) }

                    val snackbar = Snackbar.make(view as View, "Undo Delete", Snackbar.LENGTH_LONG)
                    snackbar.setAction("Ok") { v ->
                        tvShow?.let { viewModel.setFavoriteTvShow(it) }
                    }
                    snackbar.show()
                }
            }

        })


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        tvShowsAdapter = null
    }
}