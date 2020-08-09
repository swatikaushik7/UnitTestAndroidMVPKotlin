package com.org.waterloo.groupapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.org.waterloo.groupapp.R
import com.org.waterloo.groupapp.model.Member
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.android.synthetic.main.fragment_member_details.*

class MemberDetailsFragment : Fragment() {

    private var listener : OnDetailFragmentInteractionListener? =null
    private lateinit var member : Member
    private lateinit var memberDetailPresenter : MemberPresenter

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_member_details, container, false)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        memberDetailPresenter =this.activity?.application?.let { MemberPresenter(it) }!!
        val name = arguments?.getString("item_name")
        val position = arguments?.getInt("item_position")
        val group = arguments?.getString("group_name")
        activity?.title = group
        if(position != -1 ) {
            detailLayout.visibility = View.VISIBLE
            noItemSelected.visibility = View.GONE
            if (name != null) {
                memberDetailPresenter.onDisplayMemberDetail(name)?.observe(viewLifecycleOwner,
                    androidx.lifecycle.Observer<Member> { showDetails(it) })
            }
        }
    }

    private fun showDetails(member : Member){
        nameInfo.text = member.name
        ageInfo.text = member.age
        genderInfo.text = member.gender
        emailInfo.text = member.email
        phoneInfo.text =member.phoneNo
        deleteButton.setOnClickListener {
            memberDetailPresenter.onDeleteMemberRequest(member.name)
            listener?.onDetailFragmentInteraction(this)

        }
    }
    interface OnDetailFragmentInteractionListener{
        fun onDetailFragmentInteraction(fragment : MemberDetailsFragment)
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnDetailFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}
