package com.venkat.currencyconverter.ui.view

import android.content.pm.ActivityInfo
import android.view.KeyEvent
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.venkat.currencyconverter.R
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityTestRule: ActivityTestRule<MainActivity> =
        ActivityTestRule(MainActivity::class.java)
    private lateinit var mainActivity: MainActivity
    @Before
    fun setUp() {
       mainActivity = activityTestRule.activity
    }
    @Test
    fun activity_not_null()
    {
          assertNotNull(mainActivity)
    }
    @Test
    fun validate_ui_elements_on_startup()
    {
        onView(withId(R.id.dateLayout)).check(matches(isDisplayed()))
        onView(withId(R.id.search)).check(matches(isDisplayed()))
    }
    @Test
    fun perform_search()
    {
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("2"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.listCurrency)).check(matches(isDisplayed()))
        onView(withId(R.id.dateLayout)).check(matches(isDisplayed()))

    }
    @Test
    fun verify_list_on_orientation()
    {
        onView(withId(R.id.search)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText("2"), pressKey(KeyEvent.KEYCODE_ENTER))
        onView(withId(R.id.listCurrency)).check(matches(isDisplayed()))
        mainActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onView(withId(R.id.dateLayout)).check(matches(isDisplayed()))
    }
    @After
    fun tearDown() {
    }
}