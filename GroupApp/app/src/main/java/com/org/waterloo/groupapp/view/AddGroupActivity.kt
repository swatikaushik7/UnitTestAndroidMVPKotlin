package com.org.waterloo.groupapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.org.waterloo.groupapp.R
import com.org.waterloo.groupapp.model.Group
import com.org.waterloo.groupapp.presenter.MemberContract
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.android.synthetic.main.activity_add_group.*

class AddGroupActivity : AppCompatActivity(), MemberContract.View{

    private lateinit var presenter : MemberPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_group)
        presenter = MemberPresenter(application)
        presenter.attachView(this)

        addNew.setOnClickListener {
            val groupName = groupNameEdit.text.toString()
            if(groupName.isEmpty())
                Toast.makeText(this, "Group name cannot be empty!",Toast.LENGTH_LONG).show()
            else {
                val intent = Intent(this, AddMemberActivity::class.java)
                intent.putExtra("groupName", groupNameEdit.text.toString())
                Log.d("AddGroupActivity", groupNameEdit.text.toString())
                presenter.onAddGroupRequest(Group(groupName, 0))
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun onOperationSuccess() {
        startActivity( Intent(this, ShowGroupListActivity::class.java))
        finish()
    }

    override fun onOperationFailure(err: String) {
        finish()
    }
}
