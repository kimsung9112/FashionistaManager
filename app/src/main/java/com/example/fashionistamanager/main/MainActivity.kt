package com.example.fashionistamanager.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.databinding.ActivityMainBinding
import com.example.fashionistamanager.login.IntroActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

class MainActivity : BaseActivity<ActivityMainBinding>({ ActivityMainBinding.inflate(it)}) {

    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        viewInit()

    }
    private fun viewInit(){
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()

            Toast.makeText(this,"로그아웃되었습니다.", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

//        binding.userBtn.setOnClickListener {
//            val intent = Intent(this, MemberManagementActivity::class.java)
//            startActivity(intent)
//        }

        binding.clotheBtn.setOnClickListener {
            val intent = Intent(this, ClothesAddActivity::class.java)
            startActivity(intent)
        }
    }
}