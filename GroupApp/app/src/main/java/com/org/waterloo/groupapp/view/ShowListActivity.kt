package com.org.waterloo.groupapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.org.waterloo.groupapp.R

class ShowListActivity : AppCompatActivity(), ShowListFragment.OnListFragmentInteractionListener,MemberDetailsFragment.OnDetailFragmentInteractionListener {

    private  var groupName : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val listFragment = ShowListFragment()
        val args = Bundle()
        groupName = intent.getStringExtra("groupName")
        args.putString("group_name",groupName)
        listFragment.arguments = args
        supportFragmentManager.beginTransaction().add(R.id.fragmentContainer,listFragment).commit()

        if(findViewById<FrameLayout>(R.id.detailFragmentContainer) != null){
            val arguments = Bundle()
            arguments.putString("item_name","0")
            arguments.putInt("item_position",-1)
            arguments.putString("group_name",groupName)
            val memberDetailsFragment = MemberDetailsFragment()
            memberDetailsFragment.arguments = arguments
            supportFragmentManager.beginTransaction().add(R.id.detailFragmentContainer,memberDetailsFragment).commit()
        }
    }

    override fun onListFragmentInteraction(name : String, position : Int) {
        if(findViewById<FrameLayout>(R.id.detailFragmentContainer) == null){
            val memberDetailIntent = Intent(this, MemberDetailsActivity::class.java)
            memberDetailIntent.putExtra("item_position",position)
            memberDetailIntent.putExtra("item_name", name)
            memberDetailIntent.putExtra("group_name", groupName)
            startActivity(memberDetailIntent)
        }
        else{
            val arguments = Bundle()
            arguments.putString("item_name",name)
            arguments.putString("group_name",groupName)
            arguments.putInt("item_position",position)
            val memberDetailsFragment = MemberDetailsFragment()
            memberDetailsFragment.arguments = arguments
            val ft = supportFragmentManager.beginTransaction()
            ft.replace(R.id.detailFragmentContainer, memberDetailsFragment)
      //      ft.addToBackStack(null)
            ft.commit()
        }
    }

    override fun onDetailFragmentInteraction(fragment: MemberDetailsFragment) {
        supportFragmentManager.beginTransaction().remove(fragment).commit()
    }
}
