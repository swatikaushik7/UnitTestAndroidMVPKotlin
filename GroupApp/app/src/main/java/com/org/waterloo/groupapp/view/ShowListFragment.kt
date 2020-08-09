package com.org.waterloo.groupapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.org.waterloo.groupapp.R
import com.org.waterloo.groupapp.adapter.ItemListAdapter
import com.org.waterloo.groupapp.presenter.MemberContract
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.android.synthetic.main.fragment_show_list.*

/**
 * A fragment representing a list of Items.
 * Activities containing this fragment MUST implement the
 * [ShowListFragment.OnListFragmentInteractionListener] interface.
 */
class ShowListFragment : Fragment(), MemberContract.View,
    ItemListAdapter.OnMemberItemClickListener {

    private var listener: OnListFragmentInteractionListener? = null
    private lateinit var showListPresenter : MemberPresenter
    private lateinit var adapter : ItemListAdapter
    private lateinit var memberRecyclerView: RecyclerView
    private lateinit var memberNames : List<String>
    private lateinit var groupName : String

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    @SuppressLint("UseRequireInsteadOfGet")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_show_list, container, false)
        memberRecyclerView = view.findViewById(R.id.memberRecycler)
        showListPresenter = this.activity?.application?.let { MemberPresenter(it) }!!
        showListPresenter.attachView(this)
        groupName = arguments?.getString("group_name").toString()
        Log.d("ShowListFragment", " $groupName")
        activity?.setTitle(groupName)
        showListPresenter.onDisplayMemberListRequest(groupName)?.observe(viewLifecycleOwner,
            Observer<List<String>>{showList(it)})
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.show_list_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId){
            R.id.add_item-> {
                val intent = Intent(activity, AddMemberActivity::class.java)
                intent.putExtra("groupName",groupName)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson
     * [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnListFragmentInteractionListener {
        //a method to show details
        fun onListFragmentInteraction(name: String, position : Int)
    }

    override fun onOperationSuccess() {

    }

    override fun onOperationFailure(err: String) {

    }

    private fun showList(memberList : List<String>?) {
        if (memberList != null && memberList.isNotEmpty()) {
            adapter = ItemListAdapter(memberList, this)
            memberNames = memberList
            val layoutManager = LinearLayoutManager(this.activity)
            memberRecyclerView.layoutManager = layoutManager
            memberRecyclerView.adapter = adapter
        }
        else{
            noMembersExistText.visibility = View.VISIBLE
            memberRecyclerView.visibility =View.GONE
        }
    }

    override fun onMemberItemClick(position : Int, view : View?) {
        //display new fragment for member details
        //Toast.makeText(this.activity, "item clicked! $position", Toast.LENGTH_SHORT).show()
        listener?.onListFragmentInteraction(memberNames[position],position)
    }
}
