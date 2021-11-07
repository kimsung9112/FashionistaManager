package com.example.fashionistamanager.main

import android.os.Bundle
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.databinding.ActivityMemberManagementBinding
import com.google.firebase.auth.FirebaseAuth

class MemberManagementActivity : BaseActivity<ActivityMemberManagementBinding>({ ActivityMemberManagementBinding.inflate(it)}){

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()
        setRecyclerView()
    }

    private fun viewInit() = with(binding){
        binding.titleLayout.titleTv.text="사람들"
        binding.titleLayout.btnBack.setOnClickListener { onBackPressed() }
    }

    private fun setRecyclerView(){
        //binding.emailView.profile
    }
}