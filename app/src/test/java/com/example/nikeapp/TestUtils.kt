package com.example.nikeapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import kotlin.coroutines.ContinuationInterceptor
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

/**
 * Observes a [LiveData] until the `block` is done executing.
 */
fun <T> LiveData<T>.observeForTesting(block: () -> Unit) {
  val observer = Observer<T> { }
  try {
    observeForever(observer)
    block()
  } finally {
    removeObserver(observer)
  }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule : TestWatcher(), TestCoroutineScope by TestCoroutineScope() {

  val testDispatcher = this.coroutineContext[ContinuationInterceptor] as CoroutineDispatcher

  override fun starting(description: Description?) {
    super.starting(description)
    Dispatchers.setMain(testDispatcher)
  }

  override fun finished(description: Description?) {
    super.finished(description)
    Dispatchers.resetMain()
  }
}