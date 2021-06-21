package com.example.moviecatalogue.ui.pages.movies

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatalogue.core.data.Resource
import com.example.moviecatalogue.core.ui.pages.movies.MoviesAdapter
import com.example.moviecatalogue.databinding.FragmentMoviesBinding
import com.example.moviecatalogue.ui.detail.DetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment() {
    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding as FragmentMoviesBinding
    private val viewModel: MoviesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            val movieAdapter = MoviesAdapter()
            viewModel.movies.observe(viewLifecycleOwner, { movies ->
                if (movies != null) {
                    when (movies) {
                        is Resource.Loading -> binding.contentLoading.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.contentLoading.visibility = View.GONE
                            binding.noConnection.visibility = View.GONE
                            movieAdapter.submitList(movies.data)
                        }
                        is Resource.Error -> {
                            binding.contentLoading.visibility = View.GONE
                            binding.noConnection.visibility = View.VISIBLE
                        }
                    }
                }


            })

            binding.rvMovies.apply {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = movieAdapter
            }

            movieAdapter.onItemClick = { selectedData ->
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, selectedData.id)
                intent.putExtra(DetailActivity.EXTRA_CATEGORY, 0)
                startActivity(intent)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}