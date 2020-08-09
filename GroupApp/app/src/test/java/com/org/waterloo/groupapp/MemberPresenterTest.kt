package com.org.waterloo.groupapp

import android.app.Application
import com.org.waterloo.groupapp.model.Group
import com.org.waterloo.groupapp.model.Member
import com.org.waterloo.groupapp.model.MemberRepository
import com.org.waterloo.groupapp.presenter.MemberContract
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.junit.MockitoRule

@RunWith(MockitoJUnitRunner::class)
class MemberPresenterTest {

    private lateinit var presenter : MemberPresenter

    @JvmField @Rule var mockitoRule: MockitoRule = MockitoJUnit.rule()
    @Mock
    private lateinit var view  : MemberContract.View
    @Mock
    private lateinit var mockedApplication: Application
    @Mock
    private lateinit var repository : MemberRepository

    @Before
    fun setUp(){
        MockitoAnnotations.initMocks(this)
        repository = mock(MemberRepository::class.java)
        view = mock(MemberContract.View::class.java)
        `when`(mockedApplication.applicationContext).thenReturn(mockedApplication)
        presenter = MemberPresenter(mockedApplication)
        presenter.attachView(view)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @Test
    fun onAddGroupRequestTest(){
        val dummyGroup = Group("dummyGroup",0)
        presenter.onAddGroupRequest(dummyGroup)
        verify(view,times(1)).onOperationSuccess()
    }

    @Test
    fun onAddMemberRequestTest(){
        val dummyMember = Member("Alicia","45","Female","alicia291@gmail.com","6478273128","dummyGroup")
        presenter.onAddMemberRequest(dummyMember)
        verify(view,times(1)).onOperationSuccess()
    }
    @Test
    fun onAddMemberRequestFailureTest(){
        val dummyMember = Member("Alicia","1","Female","alicia291@gmail.com","6478273128","dummyGroup")
        presenter.onAddMemberRequest(dummyMember)
        verify(view,times(1)).onOperationFailure("Invalid age")
    }

    @Test

    fun onDisplayGroupListRequestTest(){
        presenter.onDisplayGroupListRequest()
        verify(view,times(1)).onOperationSuccess()

    }


    @Test
    fun onDisplayMemberListRequestTest(){
        val groupName = "dummyGroup"
        presenter.onDisplayMemberListRequest(groupName)
        verify(view,times(1)).onOperationSuccess()
    }
}