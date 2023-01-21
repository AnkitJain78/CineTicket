package com.example.moviebooking.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.moviebooking.MovieDetails
import com.example.moviebooking.R
import com.example.moviebooking.networking.model.Movie

class MovieAdapter(private val movieList: List<Movie>) :
    RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: AppCompatTextView = itemView.findViewById(R.id.movieTitle)
        var image: ImageView = itemView.findViewById(R.id.movieImage)
        var cardView: CardView = itemView.findViewById(R.id.movieCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return MovieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie: Movie = movieList[position]
        holder.image.load("https://image.tmdb.org/t/p/w342${currentMovie.posterPath}")
        holder.title.text = currentMovie.title
        holder.cardView.setOnClickListener {
            val intent = Intent(holder.itemView.context, MovieDetails::class.java)
            intent.putExtra("movie_details", currentMovie as java.io.Serializable)
            holder.itemView.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}