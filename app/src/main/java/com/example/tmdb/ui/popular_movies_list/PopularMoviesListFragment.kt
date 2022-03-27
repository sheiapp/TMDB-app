package com.example.tmdb.ui.popular_movies_list

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.bumptech.glide.RequestManager
import com.example.tmdb.R
import com.example.tmdb.adapter.MoviesLoadStateAdapter
import com.example.tmdb.adapter.PopularMoviesListAdapter
import com.example.tmdb.databinding.FragmentPopularMoviesListBinding
import com.example.tmdb.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PopularMoviesListFragment : Fragment(R.layout.fragment_popular_movies_list) {
    @Inject
    lateinit var glideRequestManager: RequestManager
    private val adapter by lazy {
        PopularMoviesListAdapter(glideRequestManager) { movieId ->
            navigateToDetailFragment(movieId)
        }
    }
    private var _binding: FragmentPopularMoviesListBinding? = null
    private val viewModel: PopularMoviesListViewModel by viewModels()
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPopularMoviesListBinding.bind(view)
        setupPopularMoviesList()
        _binding?.includedLayout?.retryButton?.setOnClickListener { adapter.retry() }
    }

    private fun setupPopularMoviesList() {
        _binding?.popularMovieRecyclerView?.adapter =
            adapter.withLoadStateFooter(footer = MoviesLoadStateAdapter { adapter.retry() })
        collectLatestLifecycleFlow(viewModel.popularMoviesLiveDataList) { _moviePagingData ->
            _moviePagingData?.let { it -> adapter.submitData(viewLifecycleOwner.lifecycle, it) }
        }
        collectLatestLifecycleFlow(adapter.loadStateFlow) { loadState ->
            val isListEmpty =
                (loadState.refresh is LoadState.NotLoading) && adapter.itemCount == COUNT_ZERO
            val errorState = loadState.source.append as? LoadState.Error
                ?: loadState.source.prepend as? LoadState.Error
                ?: loadState.append as? LoadState.Error
                ?: loadState.prepend as? LoadState.Error
                ?: loadState.refresh as? LoadState.Error
            _binding?.apply {
                /**
                 *  show empty list
                 */

                includedLayout.errorMsg.text =
                    errorState?.error?.message ?: getString(R.string.no_results)
                includedLayout.errorMsg.isVisible =
                    isListEmpty || loadState.refresh is LoadState.Error
                /**
                 *  Only show the list if refresh succeeds
                 */
                popularMovieRecyclerView.isVisible =
                    loadState.refresh !is LoadState.Error && loadState.source.refresh !is LoadState.Loading
                /**
                 *  Show loading spinner during initial load or refresh.
                 */
                includedLayout.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
                /**
                 * Show the retry state if initial load or refresh fails.
                 */
                includedLayout.retryButton.isVisible =
                    loadState.refresh is LoadState.Error || adapter.itemCount == COUNT_ZERO
            }
        }
    }


    private fun navigateToDetailFragment(movieId: Int) {
        val navigateToMovieDetails =
            PopularMoviesListFragmentDirections.actionMoviesListFragmentToMovieDetailsFragment()
        navigateToMovieDetails.movieId = movieId
        findNavController().navigate(navigateToMovieDetails)

    }

    companion object {
        private const val COUNT_ZERO = 0
    }
}