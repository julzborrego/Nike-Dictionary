package com.example.nikeapp.main

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nikeapp.R
import com.example.nikeapp.model.Result
import kotlinx.android.synthetic.main.activity_main.alt_state_text
import kotlinx.android.synthetic.main.activity_main.result_recycler_view
import kotlinx.android.synthetic.main.activity_main.search_button
import kotlinx.android.synthetic.main.activity_main.search_edit
import kotlinx.android.synthetic.main.activity_main.search_progress
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

  private val mainViewModel: MainViewModel by viewModel()

  private val resultListAdapter by lazy { ResultRecyclerAdapter() }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    initRecyclerView()
    observeSearchResults()
    initSearchActions()
  }

  private fun initRecyclerView() {
    result_recycler_view.layoutManager = LinearLayoutManager(this)
    result_recycler_view.adapter = resultListAdapter
  }

  private fun observeSearchResults() {
    mainViewModel.searchResults.observe(this, Observer {

      setLoading(it is Result.Loading)

      when (it) {
        is Result.Success -> {
          val searchResults = it.data
          if (searchResults.list.isNotEmpty()) {
            alt_state_text.visibility = View.GONE
            //Set data to adapter and update
            resultListAdapter.searchResults = searchResults.list
            resultListAdapter.notifyDataSetChanged()
          } else {
            //Empty state
            showAltState(R.string.empty_state_text)
          }
        }
        //Error State
        is Result.Error -> showAltState(R.string.error_state_text)
      }

    })
  }

  private fun initSearchActions() {
    search_button.setOnClickListener {
      searchInput()
    }

    search_edit.setOnEditorActionListener { _, _, _ ->
      searchInput()
      true
    }
  }

  private fun searchInput() {
    mainViewModel.setSearchInput(search_edit.text.toString().trim())
    hideKeyboard()
  }

  private fun showAltState(altTextResId: Int) {
    resultListAdapter.searchResults = emptyList()
    resultListAdapter.notifyDataSetChanged()

    alt_state_text.text = getString(altTextResId)
    alt_state_text.visibility = View.VISIBLE
  }

  private fun setLoading(loading: Boolean) {
    search_progress.visibility = if (loading) View.VISIBLE else View.GONE
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    val inflater: MenuInflater = menuInflater
    inflater.inflate(R.menu.menu_sort, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    // Handle item selection
    when (item.itemId) {
      R.id.sort_upvote -> {
        resultListAdapter.searchResults = resultListAdapter.searchResults.sortedByDescending { it.thumbsUp }
      }
      R.id.sort_downvote -> {
        resultListAdapter.searchResults = resultListAdapter.searchResults.sortedByDescending { it.thumbsDown }
      }
    }
    resultListAdapter.notifyDataSetChanged()
    return true
  }

  //Hide Keyboard Code taken from stack overflow
  private fun hideKeyboard() {
    val view = this.currentFocus
    view?.let { v ->
      val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
      imm?.hideSoftInputFromWindow(v.windowToken, 0)
    }
  }
}
