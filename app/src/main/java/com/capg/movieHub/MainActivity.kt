package com.capg.movieHub

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.capg.movieHub.Model.Movie
import com.capg.movieHub.Model.Search
import com.capg.movieHub.databinding.ActivityMainBinding
import com.capg.movieHub.ombdApiService.MovieApi
import com.capg.movieHub.view.MovieAdapter
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


/**
* Root class of this application.
* Created by Soundhar on 22/06/22
*/

const val apiUrl = "https://www.omdbapi.com/"
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listOfMovies: MutableList<Movie> = mutableListOf()
    lateinit var movieAdapter: MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        initRecyclerView()

    }

    private fun initRecyclerView() {
        movieAdapter = MovieAdapter(listOfMovies)
        recyclerView_mainActivity.apply {
            hasFixedSize()
            layoutManager = GridLayoutManager(context, 2)
            adapter = movieAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = "Search a movie"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String): Boolean {
                app_info.text = resources.getString( R.string.loading )
                progress_spinner.visibility = View.VISIBLE
                if(newText == "") {
                    progress_spinner.visibility = View.GONE
                    resetList()
                    refreshRecyclerView()
                    recyclerView_mainActivity.visibility = View.GONE
                    app_info.text = resources.getString( R.string.app_info )
                }
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                if(query != "") {
                    retrofitSearch(query)
                }else{
                    Toast.makeText(applicationContext, "Please enter the movie name", Toast.LENGTH_LONG).show()
                }
                return false
            }
        })

        return true
    }

    fun retrofitSearch(searchText: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val resultSearch = retrofit.create(MovieApi::class.java).search(searchText)
        resultSearch.enqueue(object : Callback<Search> {
            override fun onFailure(call: Call<Search>, t: Throwable) {
                Log.e("Search", "Error: ${t}")
            }

            override fun onResponse(call: Call<Search>, response: Response<Search>) {
                resetList()
                val allSearch = response.body()?.resultSearch
                recyclerView_mainActivity.visibility = View.VISIBLE
                allSearch?.let {
                    for (movie in allSearch) {
                        if (movie.title != null && movie.poster != null) { listOfMovies.add(movie)
                        }
                        Log.i("Response : ", "$movie")
                    }
                    progress_spinner.visibility = View.GONE
                    refreshRecyclerView()
                }
            }
        })
    }

    fun resetList() {
        listOfMovies.clear()
    }

    fun refreshRecyclerView() {
        movieAdapter.notifyDataSetChanged()
    }
}