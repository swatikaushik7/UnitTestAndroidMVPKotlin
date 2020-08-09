package com.org.waterloo.groupapp

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.org.waterloo.groupapp.model.Member
import com.org.waterloo.groupapp.model.MemberDao
import com.org.waterloo.groupapp.model.MemberDatabase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.lang.Exception

@RunWith(AndroidJUnit4ClassRunner::class)
class MemberDaoTest {

    @get: Rule
    val instantTaskExecutorRule : InstantTaskExecutorRule = InstantTaskExecutorRule ()

    private lateinit var memberDatabase :MemberDatabase
    private lateinit var memberDao :MemberDao

    @Before
    @Throws (Exception::class)
    fun initDatabase(){
        memberDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            MemberDatabase::class.java
        ).allowMainThreadQueries().build()
        memberDao = memberDatabase.memberDao()
    }

    @After
    @Throws (Exception::class)
    fun closeDatabase(){
        memberDatabase.close()
    }

    @Test
    fun testIfEmpty(){
        val liveData =  memberDatabase.memberDao().retrieveMembers("group")
        val items = LiveDataTestUtil.getValue( liveData)
        assertTrue(items.isEmpty())
    }

    @Test
    fun onInsertingMembersCheckIfRowCountIsCorrect(){
        val member = Member("fakeName", "29","male","fakemail@nomail.com","192382481334","fakeGroup")
        memberDatabase.memberDao().addMemberDetails(member)
        assertEquals(1,LiveDataTestUtil.getValue(memberDatabase.memberDao().retrieveMembers("fakeGroup")).size)
    }

    @Test
    fun onDeletingMembersCheckIfRowCountIsCorrect(){
        val member = Member("fakeName", "29","male","fakemail@nomail.com","192382481334","fakeGroup")
        memberDatabase.memberDao().addMemberDetails(member)
        memberDatabase.memberDao().deleteMember(member.name)
        assertEquals(0,LiveDataTestUtil.getValue(memberDatabase.memberDao().retrieveMembers("fakeGroup")).size)

    }

}