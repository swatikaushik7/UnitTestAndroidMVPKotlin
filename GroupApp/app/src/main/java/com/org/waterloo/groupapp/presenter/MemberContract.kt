package com.org.waterloo.groupapp.presenter

import androidx.lifecycle.LiveData
import com.org.waterloo.groupapp.model.Group
import com.org.waterloo.groupapp.model.Member

interface MemberContract {

    interface ViewActions{
        fun onAddMemberRequest(member: Member)
        fun onDisplayMemberListRequest(groupName : String?) : LiveData<List<String>>?
        fun onDisplayMemberDetail(name : String) : LiveData<Member>?
        fun onDeleteMemberRequest(name: String?)
    }

    interface View{

        fun onOperationSuccess()
        fun onOperationFailure(err:String)
    }

    interface  GroupActions{
        fun onAddGroupRequest(group: Group)
        fun onDisplayGroupListRequest() : LiveData<List<String>>?
    }

}