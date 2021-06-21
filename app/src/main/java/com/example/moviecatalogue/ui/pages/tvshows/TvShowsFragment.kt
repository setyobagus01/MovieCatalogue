package com.example.moviecatalogue.ui.pages.tvshows

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.ui.pages.tvshows.TvShowsAdapter
import com.example.moviecatalogue.databinding.FragmentTvShowsBinding
import com.example.moviecatalogue.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvShowsFragment : Fragment() {

    private var _binding: FragmentTvShowsBinding? = null
    private val binding get() = _binding as FragmentTvShowsBinding

    private val viewModel: TvShowsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTvShowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val tvShowsAdapter = TvShowsAdapter()
            viewModel.tvShows.observe(viewLifecycleOwner, { tvShows ->
                if (tvShows != null) {
                    when (tvShows) {
                        is Resource.Loading -> binding.contentLoading.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.contentLoading.visibility = View.GONE
                            binding.noConnection.visibility = View.GONE
                            tvShowsAdapter.submitList(tvShows.data)

                        }
                        is Resource.Error -> {
                            binding.contentLoading.visibility = View.GONE
                            binding.noConnection.visibility = View.VISIBLE
                        }
                    }
                }
            })

            binding.rvTvshows.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tvShowsAdapter
            }

            tvShowsAdapter.onItemClick = { selectedData ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, selectedData.id)
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, 1)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}