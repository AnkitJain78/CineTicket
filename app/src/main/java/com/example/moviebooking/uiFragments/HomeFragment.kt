package com.example.moviebooking.uiFragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviebooking.adapter.MovieAdapter
import com.example.moviebooking.databinding.FragmentHomeBinding
import com.example.moviebooking.networking.MovieService
import com.example.moviebooking.networking.model.Movie
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var popularMovieList = ArrayList<Movie>()
    private var nowShowingMovieList = ArrayList<Movie>()
    private lateinit var adapter: MovieAdapter
    private lateinit var adapter2: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Home"
        adapter2 = MovieAdapter(popularMovieList)
        adapter = MovieAdapter(nowShowingMovieList)
        binding.movieRecyclerView.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.movieRecyclerView2.layoutManager =
            LinearLayoutManager(activity?.applicationContext, LinearLayoutManager.HORIZONTAL, false)
        binding.movieRecyclerView.adapter = adapter
        binding.movieRecyclerView2.adapter = adapter2
        MovieService.getPopularMovies(
            onSuccess = ::onPopularMoviesFetched,
            onError = ::onError
        )
        MovieService.getNowShowingMovies(
            onSuccess = ::onNowShowingMoviesFetched,
            onError = ::onError
        )
    }

    private fun onPopularMoviesFetched(movies: List<Movie>) {
        popularMovieList = movies as ArrayList<Movie>
        Log.d("movies2", movies.toString())
        binding.movieRecyclerView2.adapter?.notifyDataSetChanged()
    }

    private fun onNowShowingMoviesFetched(movies: List<Movie>) {
        nowShowingMovieList = movies as ArrayList<Movie>
        Log.d("movies", movies.toString())
        binding.movieRecyclerView.adapter?.notifyDataSetChanged()
    }

    private fun onError() {
        Toast.makeText(activity?.applicationContext, "error in fetching movies", Toast.LENGTH_SHORT)
            .show()
    }
}

