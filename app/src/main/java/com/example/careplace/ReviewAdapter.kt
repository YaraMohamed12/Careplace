package com.example.careplace

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewAdapter (val context : Context , val reviewList : List<Review>)
    : RecyclerView.Adapter<ReviewAdapter.UserViewHolder>() {
        inner class UserViewHolder(val v:View):RecyclerView.ViewHolder(v){
            val review = v.findViewById<TextView>(R.id.review_mess)
            val rate = v.findViewById<RatingBar>(R.id.ratingBar3_review)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context)
        val v = view.inflate(R.layout.review_card, parent, false)
        return UserViewHolder(v)
    }

    override fun getItemCount(): Int {
        return reviewList.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val newReview =  reviewList[position]
        holder.review.text = newReview.content
        holder.rate.rating = newReview.rating
    }

}
