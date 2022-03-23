package com.example.tmdb.ui.movie_details

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.R
import com.example.tmdb.utils.Result
import com.example.tmdb.databinding.FragmentMovieDetailsBinding
import com.example.tmdb.utils.collectLatestLifecycleFlow
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieDetailsFragment : Fragment(R.layout.fragment_movie_details) {

    @Inject
    lateinit var glideRequestManager: RequestManager
    private val viewModel: MovieDetailsViewModel by viewModels()
    private var _binding: FragmentMovieDetailsBinding? = null
    private val args: MovieDetailsFragmentArgs by navArgs()

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMovieDetailsBinding.bind(view)
        val movieId = args.movieId
        setupMoviesDetailsUi()
        viewModel.getMovieDetails(movieId)
        _binding?.includedLayout?.retryButton?.setOnClickListener {
            viewModel.getMovieDetails(movieId)
        }
    }

    private fun setupMoviesDetailsUi() {
        collectLatestLifecycleFlow(viewModel.movieDetails) {
            Log.d("movie_details", it.toString())
            if (it is Result.Success) {
                _binding?.apply {
                    setupThumbnailImage(it.data?.getThumbnailImage())
                    movieTitle.text = it.data?.title
                    movieReleaseDate.text = it.data?.releaseDate
                    movieBudget.text = it.data?.releaseDate
                    movieGenres.text =
                        it.data?.genres?.map { genres -> genres.name }.toString()
                            .removeSurrounding("[", "]")
                    movieAverageVote.text = it.data?.voteAverage.toString()
                    movieOverview.text = it.data?.overview
                }
            }
            _binding?.includedLayout?.progressBar?.isVisible = it is Result.Loading
            _binding?.includedLayout?.errorMsg?.isVisible = it is Result.Error
            _binding?.includedLayout?.retryButton?.isVisible = it is Result.Error
            _binding?.includedLayout?.errorMsg?.text =
                if (it is Result.Error) it.message else ""
        }
    }

    private fun setupThumbnailImage(imageUrl: String?) {
        _binding?.movieThumbnail?.let {
            glideRequestManager
                .load(imageUrl)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(it)
        }
    }

}