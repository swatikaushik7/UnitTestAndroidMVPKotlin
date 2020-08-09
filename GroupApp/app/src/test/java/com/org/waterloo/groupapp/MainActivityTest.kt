package com.org.waterloo.groupapp

import android.content.Intent
import android.os.Build
import android.widget.Button
import com.org.waterloo.groupapp.view.*

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk= [Build.VERSION_CODES.P])
class MainActivityTest {
    private lateinit var activity : MainActivity

    @Before
    @Throws (Exception::class)
    fun setup() {
        activity = Robolectric.buildActivity(MainActivity::class.java).create().resume().get()
    }
    @Test
    @Throws(Exception::class)
    fun shouldNotBeNull(){
        assertNotNull(activity)
    }
    @Test
    @Throws(Exception::class)
    fun addButtonShouldNotBeNull(){
        assertNotNull(activity.findViewById(R.id.addButton))
    }
    @Test
    @Throws(Exception::class)
    fun addButtonClickShouldStartAnotherActivity(){
        val button :Button =  activity.findViewById(R.id.addButton)
        button.performClick()
        val intent : Intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        assertEquals(AddGroupActivity::class.java.canonicalName,intent.component?.className)
    }
    @Test
    @Throws(Exception::class)
    fun showButtonClickShouldStartAnotherActivity(){
        val button: Button = activity.findViewById(R.id.showButton)
        button.performClick()
        val intent : Intent = Shadows.shadowOf(activity).peekNextStartedActivity()
        assertEquals(ShowGroupListActivity::class.java.canonicalName, intent.component?.className)
    }
}