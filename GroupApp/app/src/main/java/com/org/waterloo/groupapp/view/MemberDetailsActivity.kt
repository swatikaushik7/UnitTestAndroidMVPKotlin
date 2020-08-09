package com.org.waterloo.groupapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.waterloo.groupapp.R

class MemberDetailsActivity : AppCompatActivity(), MemberDetailsFragment.OnDetailFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member_details)

        val memberDetailsFragment = MemberDetailsFragment()
        val arguments = Bundle()
        arguments.putString("group_name",intent.getStringExtra("group_name"))
        arguments.putString("item_name", intent.getStringExtra("item_name"))
        arguments.putInt("item_position",intent.getIntExtra("item_position",-1))
        memberDetailsFragment.arguments = arguments
        supportFragmentManager.beginTransaction().add(R.id.detailFragmentContainer,memberDetailsFragment).commit()
    }

    override fun onDetailFragmentInteraction(fragment: MemberDetailsFragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
        finish()
    }
}
