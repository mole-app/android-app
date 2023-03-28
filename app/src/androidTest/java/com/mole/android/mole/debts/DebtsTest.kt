package com.mole.android.mole.debts

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import com.mole.android.mole.MoleMainActivity
import com.mole.android.mole.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DebtsTest : TestCase() {

    @Test
    fun test() =
        run {
            step("Open Main Screen") {
                ActivityScenario.launch(MoleMainActivity::class.java)
            }

            step("Try click VK auth") {
                Espresso.onView(ViewMatchers.withId(R.id.vk_button)).perform(ViewActions.click())
            }

            step("Check webView displayed") {
                Espresso.onView(ViewMatchers.withId(R.id.web_view))
                    .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
            }
        }
}
