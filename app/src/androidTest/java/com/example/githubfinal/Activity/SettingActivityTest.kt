package com.example.githubfinal.Activity

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubfinal.R
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingActivityTest {


    @Before
    fun setup(){
        ActivityScenario.launch(SettingActivity::class.java)
    }

    @Test
    fun assertGetCircumference() {
        onView(withId(R.id.switch_theme)).perform(click())
    }
}