package com.example.topratedmoviesdb

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.topratedmoviesdb.R.id


class TopRatedMovieRecyclerViewAdapter (
    private val movies: List<TopRatedMovie>,
    private val mListener:OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<TopRatedMovieRecyclerViewAdapter.MovieViewHolder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TopRatedMovieRecyclerViewAdapter.MovieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_top_rated_movie,parent,false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View): RecyclerView.ViewHolder(mView){
        var mItem:TopRatedMovie?=null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        //val mDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
        //val mRating: TextView = mView.findViewById<View>(id.rating) as TextView
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem=movie
        holder.mMovieTitle.text=movie.title
        //holder.mDescription.text=movie.description
        //holder.mRating.text=movie.rating.toString()+"/10"

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500/"+movie.movieImageUrl)
            .fitCenter()
            .into(holder.mMovieImage)

        /*
        if (movie.rating>=7.0){
            holder.mView.setBackgroundColor(Color.parseColor("#4a964d"))
        }
        if (movie.rating<7.0){
            holder.mView.setBackgroundColor(Color.parseColor("#91964a"))
        }
        if (movie.rating<6.0){
            holder.mView.setBackgroundColor(Color.parseColor("#96504a"))

        }
         */
        holder.mView.setOnClickListener{
            holder.mItem?.let{ movie ->
                mListener?.onItemClick(movie)
            }
        }
    }

    override fun getItemCount(): Int {
        return movies.size
    }

}
