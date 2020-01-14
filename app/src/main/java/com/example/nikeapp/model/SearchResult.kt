package com.example.nikeapp.model


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

data class SearchResult(
  val author: String,
  @field:Json(name = "current_vote")
  val currentVote: String,
  val defid: Int,
  val definition: String,
  val example: String,
  val permalink: String,
  @field:Json(name =  "sound_urls")
  val soundUrls: List<String>,
  @field:Json(name =  "thumbs_down")
  val thumbsDown: Int,
  @field:Json(name =  "thumbs_up")
  val thumbsUp: Int,
  val word: String,
  @field:Json(name = "written_on")
  val writtenOn: String
)