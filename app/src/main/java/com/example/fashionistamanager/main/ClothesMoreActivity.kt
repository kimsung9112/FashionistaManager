package com.example.fashionistamanager.main


import android.os.Bundle
import com.example.fashionistamanager.R
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.databinding.ActivityClothesMoreBinding
import kotlinx.coroutines.CoroutineScope
import kotlin.coroutines.CoroutineContext

class ClothesMoreActivity :
    BaseActivity<ActivityClothesMoreBinding>({ ActivityClothesMoreBinding.inflate(it) }){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        viewInit()

    }

    private fun viewInit(){

    }

}