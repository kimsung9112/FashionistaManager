package com.example.fashionistamanager.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.data.ClothesModel
import com.example.fashionistamanager.data.ClothesSaveModel
import com.example.fashionistamanager.databinding.ActivityClothesAddBinding
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.study.poly.fashionista.utility.Constant.USER_PATH
import com.study.poly.fashionista.utility.onThrottleFirstClick
import com.study.poly.fashionista.utility.toast
import kotlin.collections.ArrayList as ArrayList

class ClothesAddActivity : BaseActivity<ActivityClothesAddBinding>({ActivityClothesAddBinding.inflate(it)}) {

    private val REQ_Gallery = 1

    private lateinit var info: String
    private lateinit var name: String
    private lateinit var shop: String
    private lateinit var pageId: String
    private lateinit var titlepath: String
    private lateinit var size: ArrayList<String>


    private lateinit var saveDB: FirebaseFirestore
    private lateinit var db: CollectionReference
    private var clothesInfo = ClothesModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()

    }
    private fun viewInit() = with(binding){
        layoutTitle.titleTv.text = "상품추가"
        layoutTitle.btnBack.setOnClickListener { onBackPressed() }

        btnAddClothesInfo.onThrottleFirstClick {
            setClothesInfoSave()
        }

        binding.getImgBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, REQ_Gallery)
        }
        btnAddClothesInfo.setOnClickListener {
            setClothesInfoSave()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            when(requestCode){
                REQ_Gallery -> {
                    data?.data?.let{ uri->
                        binding.imageView.setImageURI(uri)
                    }
                }
            }
        }

    }
    private fun setClothesInfoSave() = with(binding) {
        val model = ClothesSaveModel(
            pageId,
            name,
            shop,
            info,
            titlepath,
            size
        )
    }

    }
