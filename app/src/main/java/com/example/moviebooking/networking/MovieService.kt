package com.example.moviebooking.networking

import com.example.moviebooking.model.MoviesResponse
import com.example.moviebooking.networking.model.Movie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val MOVIE_API_KEY = "680147e80c0276a068b7e7549a9e730f"

interface MovieInterface {
    @GET("movie/popular")
    fun getPopularMovies(
        @Query("api_key") apiKey: String = "680147e80c0276a068b7e7549a9e730f",
        @Query("page") page: Int
    ): Call<MoviesResponse>

    @GET("movie/now_playing?region=IN&with_original_language=hi")
    fun getNowShowingMovies(
        @Query("api_key") apiKey: String = "680147e80c0276a068b7e7549a9e730f",
        @Query("page") page: Int
    ): Call<MoviesResponse>
}

object MovieService {
    private val movieInstance: MovieInterface

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        movieInstance = retrofit.create(MovieInterface::class.java)
    }

    fun getPopularMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        movieInstance.getPopularMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }

    fun getNowShowingMovies(
        page: Int = 1,
        onSuccess: (movies: List<Movie>) -> Unit,
        onError: () -> Unit
    ) {
        movieInstance.getNowShowingMovies(page = page)
            .enqueue(object : Callback<MoviesResponse> {
                override fun onResponse(
                    call: Call<MoviesResponse>,
                    response: Response<MoviesResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            onSuccess.invoke(responseBody.movies)
                        } else {
                            onError.invoke()
                        }
                    }
                }

                override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                    onError.invoke()
                }
            })
    }
}
