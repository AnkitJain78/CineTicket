package com.example.moviebooking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.moviebooking.databinding.ActivityMovieDetailsBinding
import com.example.moviebooking.networking.model.Movie

class MovieDetails : AppCompatActivity() {
    private lateinit var binding: ActivityMovieDetailsBinding
    private lateinit var currentMovie: Movie
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        currentMovie = intent.extras?.get("movie_details") as Movie
        binding.coverImage.load("https://image.tmdb.org/t/p/w342${currentMovie.backdropPath}")
        binding.movieName.text = currentMovie.title
        binding.movieDesc.text = currentMovie.overview
        binding.ratingTxt.text = currentMovie.rating.toString()
        binding.langTxt.text = buildString {
            append("Languages:      ")
            append(getLanguage(currentMovie.languages))
        }
        binding.releaseTxt.text = buildString {
            append("Release Date:      ")
            append(currentMovie.releaseDate)
        }
        binding.bookTicketBtn.setOnClickListener {
            val intent = Intent(this, ShowTheater::class.java)
            startActivity(intent)
        }
    }

    private fun getLanguage(lan: String): String {
        when (lan) {
            "hi" -> return "Hindi"
            "en" -> return "English"
            "te" -> return "Telugu"
            "kn" -> return "Kannada"
            "ta" -> return "Tamil"
        }
        return lan
    }
}