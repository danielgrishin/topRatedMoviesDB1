package com.example.topratedmoviesdb

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import android.widget.Toast


private const val API_KEY="a07e22bc18f5cb106bfe4cc1f83ad8ed"

class TopRatedMovieFragment: Fragment(),  OnListFragmentInteractionListener{

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_rated_movie_list,container,false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 2)
        updateAdapter(progressBar,recyclerView)
        return view

    }
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()
        val params = RequestParams()
        // Using the client, perform the HTTP request
        client[
                "https://api.themoviedb.org/3/movie/top_rated?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
                params,
                object : JsonHttpResponseHandler()
                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models


                        // Look for this in Logcat:
                        Log.d("TopRatedMovieFragment", "response successful")
                        Log.d("TopRatedMovieFragment", json.toString())

                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                        val moviesRawJSON : String = resultsJSON.toString()

                        Log.d("TopRatedMovieFragment",moviesRawJSON)

                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<TopRatedMovie>>() {}.type
                        val models: List<TopRatedMovie> = gson.fromJson(moviesRawJSON,arrayMovieType)
                        recyclerView.adapter = TopRatedMovieRecyclerViewAdapter(models, this@TopRatedMovieFragment)
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("TopRatedMovieFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular book is clicked.
*/
    override fun onItemClick(item: TopRatedMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
        //PUT INTENT HERE
        //val movie = item
        //val intent = Intent(context, DetailActivity::class.java)
        //intent.putExtra("movie_extra",movie)
        //context?.startActivity(intent)
    }

}