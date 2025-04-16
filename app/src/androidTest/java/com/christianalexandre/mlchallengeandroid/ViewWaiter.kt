package com.christianalexandre.mlchallengeandroid

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Matcher
import java.util.concurrent.TimeUnit

object ViewWaiter {
    private const val TIMEOUT = 5L

    fun forView(view: ViewInteraction, condition: Matcher<View>, timeout: Long = TIMEOUT) {
        val end = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(timeout)
        while (System.currentTimeMillis() < end) {
            try {
                view.check(matches(condition))
                return
            } catch (e: Throwable) {
                Thread.sleep(250)
            }
        }
        view.check(matches(condition))
    }
}
