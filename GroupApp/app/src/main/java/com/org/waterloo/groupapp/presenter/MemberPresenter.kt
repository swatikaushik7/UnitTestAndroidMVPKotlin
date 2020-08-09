package com.org.waterloo.groupapp.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import com.org.waterloo.groupapp.model.Group

import com.org.waterloo.groupapp.model.Member
import com.org.waterloo.groupapp.model.MemberRepository
import com.org.waterloo.groupapp.util.Constants

class MemberPresenter (application: Application) : MemberContract.ViewActions,MemberContract.GroupActions{

    private var memberRepository : MemberRepository = MemberRepository(application)
    private var mv : MemberContract.View? = null

    fun attachView(view : MemberContract.View){
        this.mv = view
    }

    fun detachView(){
        this.mv = null
    }

    override fun onAddMemberRequest(member: Member) {
        //check validity of input
        if(validateMemberDetails(member)) {
            memberRepository.addMembers(member)
            mv?.onOperationSuccess()
        }
    }

    override fun onDisplayMemberListRequest(groupName:String?) :LiveData<List<String>>? {
        mv?.onOperationSuccess()
        return memberRepository.getMembers(groupName)
    }

    override fun onDisplayMemberDetail(name: String) : LiveData<Member>?
    {
        mv?.onOperationSuccess()
        return memberRepository.getMemberDetail(name)
    }

    override fun onDeleteMemberRequest(name: String?)  {
        if (name != null) {
            memberRepository.deleteMember(name)
            mv?.onOperationSuccess()
        }
    }
    override fun onAddGroupRequest(group: Group) {
        memberRepository.addGroup(group)
        mv?.onOperationSuccess()
    }

    override fun onDisplayGroupListRequest() : LiveData<List<String>>?
    {
        val result = memberRepository.getGroups()
        mv?.onOperationSuccess()
        return result
    }

    private fun validateMemberDetails(member : Member):Boolean{
        var check = validateInputString(member.name)
        if(check == Constants.EMPTY) {
            mv?.onOperationFailure("Name cannot be left blank")
            return false
        }
        if(check == Constants.INVALID){
            mv?.onOperationFailure("Invalid name")
            return false
        }
        check = validateInputString(member.gender)
        if(check == Constants.EMPTY) {
            mv?.onOperationFailure("Select gender")
            return false
        }
        check = validateAge(member.age)
        if(check == Constants.EMPTY) {
            mv?.onOperationFailure("Age cannot be left blank")
            return false
        }
        if(check == Constants.INVALID){
            mv?.onOperationFailure("Invalid age")
            return false
        }
        check = validateEmail(member.email)
        if(check == Constants.EMPTY) {
            mv?.onOperationFailure("email cannot be left blank")
            return false
        }
        if(check == Constants.INVALID){
            mv?.onOperationFailure("Invalid email address")
            return false
        }
        check = validatePhone(member.phoneNo)
        if(check == Constants.EMPTY) {
            mv?.onOperationFailure("phone cannot be left blank")
            return false
        }
        if(check == Constants.INVALID){
            mv?.onOperationFailure("Invalid phone number")
            return false
        }
      return true
    }

    private fun validateInputString(input: String): Constants{
        if(input.isEmpty())
            return Constants.EMPTY
        if(input.length < 2)
            return Constants.INVALID
        if(input.matches("-?\\d+\\.".toRegex()))
            return Constants.INVALID
        return Constants.SUCCESS
    }

    private fun validateAge(age: String) : Constants{
        if(age.isEmpty())
            return Constants.EMPTY
        try{
            if(age.length > 2 ||  age.toInt() < 18)
                return Constants.INVALID
        }catch(e:NumberFormatException ){
            return Constants.INVALID
        }
        return Constants.SUCCESS
    }

    private fun validateEmail(email:String):Constants{
        if(email.isEmpty())
            return Constants.EMPTY
        if(!email.contains("@"))
            return Constants.INVALID
        return Constants.SUCCESS
    }

    private fun validatePhone(phone:String):Constants{
        if(phone.isEmpty())
            return Constants.EMPTY
        if(phone.length < 10)
            return Constants.INVALID
        return Constants.SUCCESS
    }
}