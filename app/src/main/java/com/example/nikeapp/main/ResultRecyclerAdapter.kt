package com.example.nikeapp.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nikeapp.R
import com.example.nikeapp.model.SearchResult
import kotlinx.android.synthetic.main.item_result.view.result_definition
import kotlinx.android.synthetic.main.item_result.view.result_downvote
import kotlinx.android.synthetic.main.item_result.view.result_upvote
import kotlinx.android.synthetic.main.item_result.view.result_word

class ResultRecyclerAdapter :
  RecyclerView.Adapter<ResultRecyclerAdapter.ResultViewHolder>() {

  class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val word = itemView.result_word
    val definition = itemView.result_definition
    val upVote = itemView.result_upvote
    val downVote = itemView.result_downvote
  }

  var searchResults: List<SearchResult> = emptyList()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
    val itemView = LayoutInflater.from(parent.context)
      .inflate(R.layout.item_result, parent, false)

    return ResultViewHolder(itemView)
  }

  override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
    val searchResult = searchResults[position]

    holder.word.text = searchResult.word
    holder.definition.text = searchResult.definition
    holder.upVote.text = searchResult.thumbsUp.toString()
    holder.downVote.text = searchResult.thumbsDown.toString()
  }

  override fun getItemCount() = searchResults.size
}