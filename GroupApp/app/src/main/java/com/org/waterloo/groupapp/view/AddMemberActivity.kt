package com.org.waterloo.groupapp.view

import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.Toast
import com.org.waterloo.groupapp.R
import com.org.waterloo.groupapp.model.Member
import com.org.waterloo.groupapp.presenter.MemberContract
import com.org.waterloo.groupapp.presenter.MemberPresenter
import kotlinx.android.synthetic.main.activity_add_member.*

class AddMemberActivity : AppCompatActivity(), MemberContract.View {

    private lateinit var presenter : MemberPresenter
    private lateinit var progressBar : ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_member)
        presenter = MemberPresenter(application)
        presenter.attachView(this)
        val groupName = intent.getStringExtra("groupName")
        title = groupName
        submitButton.setOnClickListener {
            initProgressBar()
            val genderSelected : RadioButton= findViewById(genderRadioGroup.checkedRadioButtonId)
            val member = Member(nameEdit.text.toString(),
                ageEdit.text.toString(),
                genderSelected.text.toString(),
                emailEdit.text.toString(),
                phoneEdit.text.toString(),groupName)
            presenter.onAddMemberRequest(member)
        }
    }

    override fun onDestroy(){
        presenter.detachView()
        super.onDestroy()
    }

    private fun initProgressBar(){
        progressBar = ProgressBar(this, null,android.R.attr.progressBarStyleSmall)
        progressBar.isIndeterminate = true
        val params = RelativeLayout.LayoutParams(Resources.getSystem().displayMetrics.widthPixels,250)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        this.addContentView(progressBar,params)
        showProgressBar()
    }
    private fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }
    private fun hideProgressBar(){
        progressBar.visibility = View.INVISIBLE
    }
    private fun showToast( msg : String){
        val toast  = Toast.makeText(applicationContext,"Member added successfully!", Toast.LENGTH_SHORT)
        if(msg == ""){
            toast.show()
        }else{
            toast.setText(msg)
            toast.show()
        }
    }

    override fun onOperationSuccess() {
        hideProgressBar()
        showToast("")
        finish()
    }

    override fun onOperationFailure(err:String) {
        hideProgressBar()
        showToast(err)
    }
}
