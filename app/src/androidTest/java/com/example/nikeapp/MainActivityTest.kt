package com.example.nikeapp

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.example.nikeapp.main.MainActivity

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Rule

/**
 * Simple instrumented test checking whether the search edit text and button are displayed. Button has Go text.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun searchWidgetsDisplayed() {
        onView(withId(R.id.search_edit)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(isDisplayed()))
        onView(withId(R.id.search_button)).check(matches(withText(R.string.go)))
    }
}
