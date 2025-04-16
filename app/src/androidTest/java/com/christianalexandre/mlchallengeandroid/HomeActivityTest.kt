package com.christianalexandre.mlchallengeandroid

import android.widget.EditText
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.christianalexandre.mlchallengeandroid.modules.home.HomeActivity
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeActivityTest {

    @Test
    fun testSearch_existingItem() {
        ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.search_bar))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("iphone"))
            .perform(pressImeActionButton())

        ViewWaiter.forView(
            onView(withId(R.id.progressIndicator)), isDisplayed()
        )

        ViewWaiter.forView(
            onView(withId(R.id.search_items_recycler_view)), isDisplayed()
        )

        onView(withId(R.id.search_items_recycler_view))
            .check(matches(hasMinimumChildCount(1)))
    }

    @Test
    fun testSearch_nonExistingItem() {
        ActivityScenario.launch(HomeActivity::class.java)

        onView(withId(R.id.search_bar))
            .perform(click())

        onView(isAssignableFrom(EditText::class.java))
            .perform(typeText("mock"))
            .perform(pressImeActionButton())

        ViewWaiter.forView(
            onView(withId(R.id.progressIndicator)), isDisplayed()
        )

        ViewWaiter.forView(
            onView(withId(R.id.textView)), isDisplayed()
        )
    }
}