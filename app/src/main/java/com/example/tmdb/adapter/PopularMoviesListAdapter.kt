package com.example.tmdb.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.tmdb.databinding.PopularMovieItemBinding
import com.example.tmdb.model.Movie

/**
 * Created by Shaheer cs on 22/03/2022.
 */
class PopularMoviesListAdapter(
    private val requestManager: RequestManager,
    private val clickEventData: (Int) -> Unit
) : PagingDataAdapter<Movie, MoviesItemViewHolder>(moviesDiffUtil) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesItemViewHolder {
        val itemView =
            PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoviesItemViewHolder(itemView, requestManager)
    }

    override fun onBindViewHolder(holder: MoviesItemViewHolder, position: Int) {
        val currentItem = getItem(position)
        currentItem?.let { movie ->
            holder.onBindView(movie)
            holder.itemView.setOnClickListener {
                clickEventData(movie.id)
            }
        }
    }

    companion object {
        private val moviesDiffUtil = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(
                oldItem: Movie,
                newItem: Movie
            ) =
                newItem.id == oldItem.id

            override fun areContentsTheSame(
                oldItem: Movie,
                newItem: Movie
            ) =
                newItem == oldItem
        }
    }
}


class MoviesItemViewHolder(
    private val viewItem: PopularMovieItemBinding,
    private val requestManager: RequestManager
) : RecyclerView.ViewHolder(viewItem.root) {
    fun onBindView(movie: Movie) {
        viewItem.movieOverview.text = movie.overview
        viewItem.movieTitle.text = movie.title
        viewItem.movieReleaseDate.text = movie.releaseDate
        setupMovieThumbnail(movie.getThumbnailImage())
    }

    private fun setupMovieThumbnail(posterPath: String) {
        requestManager
            .load(posterPath)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(viewItem.movieThumbnail)
    }
}

