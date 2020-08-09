package com.org.waterloo.groupapp


import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.org.waterloo.groupapp.view.AddMemberActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class AddMemberActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(AddMemberActivity::class.java)

    @Test
    fun checkErrorToastBlankName(){
        onView(withId(R.id.addMember)).check(matches(isDisplayed()))
        onView(withId(R.id.nameEdit)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(click())
        onView(withText("Name cannot be left blank"))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
    @Test
    fun checkErrorToastInvalidName(){
        onView(withId(R.id.addMember)).check(matches(isDisplayed()))
        onView(withId(R.id.nameEdit)).perform(typeText("d"), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(scrollTo()).perform(click())
        onView(withText("Invalid name"))
            .inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }

    @Test
    fun checkSuccessToast(){
        onView(withId(R.id.addMember)).check(matches(isDisplayed()))
        onView(withId(R.id.nameEdit)).perform(typeText("test"), closeSoftKeyboard())
        onView(withId(R.id.ageEdit)).perform(typeText("45"), closeSoftKeyboard())
        onView(withText("Male")).perform(click())
        onView(withId(R.id.emailEdit)).perform(typeText("test@testdomain.com"), closeSoftKeyboard())
        onView(withId(R.id.phoneEdit)).perform(typeText("579887323232"), closeSoftKeyboard())
        onView(withId(R.id.submitButton)).perform(scrollTo()).perform(click())
        onView(withText("Member added successfully!")).inRoot(ToastMatcher()).check(matches(isDisplayed()))
    }
}

