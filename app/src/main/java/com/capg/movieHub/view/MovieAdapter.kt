package com.capg.movieHub.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capg.movieHub.Model.Movie
import com.capg.movieHub.R
import kotlinx.android.synthetic.main.movie_item.view.*

/**
 * To show list of movies
 * Created by Soundhar on 22/06/22
 */

class MovieAdapter(private val itemList: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var context: Context? = null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.movie_item, parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {
            val movie = itemList[adapterPosition]

            Glide.with(context!!)
                .load(movie.poster)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(itemView.poster_movie_item)
            itemView.title_movie_item.text = movie.title

        }
    }
}