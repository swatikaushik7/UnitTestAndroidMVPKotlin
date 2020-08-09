package com.org.waterloo.groupapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.org.waterloo.groupapp.R
import com.org.waterloo.groupapp.adapter.ItemListAdapter
import com.org.waterloo.groupapp.presenter.MemberContract
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.android.synthetic.main.activity_show_group_list.*

class ShowGroupListActivity : AppCompatActivity(), MemberContract.View,
    ItemListAdapter.OnMemberItemClickListener {

    private lateinit var showListPresenter : MemberPresenter
    private lateinit var adapter : ItemListAdapter
    private lateinit var groupNames : List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_group_list)
        showListPresenter = MemberPresenter(application)
        showListPresenter.attachView(this)
        showListPresenter.onDisplayGroupListRequest()?.observe(this) {showGroupList(it)}

    }

    override fun onOperationSuccess() {

    }

    override fun onOperationFailure(err: String) {

    }

    override fun onMemberItemClick(position: Int, view: View?) {
        val intent = Intent(this, ShowListActivity::class.java)
        intent.putExtra("groupName",groupNames[position])
        Log.d("ShowGroupList",groupNames[position])
        startActivity(intent)
    }

    private fun showGroupList(groupList : List<String>) {

        if (groupList.isNotEmpty()) {
            adapter = ItemListAdapter(groupList, this)
            groupNames = groupList
            val layoutManager = LinearLayoutManager(this)
            groupListView.layoutManager = layoutManager
            groupListView.adapter = adapter
        }
        else{
            noGroupExistText.visibility = View.VISIBLE
            groupListView.visibility =View.GONE
        }
    }
}
