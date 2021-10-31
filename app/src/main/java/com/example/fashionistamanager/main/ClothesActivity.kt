package com.example.fashionistamanager.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.fashionistamanager.R
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.databinding.ActivityClothesBinding

class ClothesActivity : BaseActivity<ActivityClothesBinding>({ ActivityClothesBinding.inflate(it)}) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        viewInit()
    }

    private fun viewInit(){
        binding.pantsBtn.setOnClickListener {
            val intent = Intent(this, ClothesMoreActivity::class.java)
            startActivity(intent)
        }

        binding.shirtsBtn.setOnClickListener {
            val intent = Intent(this, ClothesMoreActivity::class.java)
            startActivity(intent)
        }

        binding.outerBtn.setOnClickListener {
            val intent = Intent(this, ClothesMoreActivity::class.java)
            startActivity(intent)
        }

        binding.pantsBtn.setOnClickListener {
            val intent = Intent(this, ClothesAddActivity::class.java)
            startActivity(intent)
        }
    }
}