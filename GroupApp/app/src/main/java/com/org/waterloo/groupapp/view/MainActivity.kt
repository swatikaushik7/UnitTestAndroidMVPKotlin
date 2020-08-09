package com.org.waterloo.groupapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.org.waterloo.groupapp.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addButton.setOnClickListener {
            startActivity(Intent(this, AddGroupActivity::class.java)) }
        showButton.setOnClickListener { startActivity(Intent(this, ShowGroupListActivity::class.java))}
    }
}