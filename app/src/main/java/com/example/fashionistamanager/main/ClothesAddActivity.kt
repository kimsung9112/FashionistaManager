package com.example.fashionistamanager.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.data.ClothesSaveModel
import com.example.fashionistamanager.databinding.ActivityClothesAddBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.study.poly.fashionista.utility.Constant.CATEGORY_HOOD
import com.study.poly.fashionista.utility.Constant.CATEGORY_OUTER
import com.study.poly.fashionista.utility.Constant.CATEGORY_PANTS
import com.study.poly.fashionista.utility.Constant.CATEGORY_T_SHIRT
import com.study.poly.fashionista.utility.onThrottleFirstClick
import com.study.poly.fashionista.utility.toast
import kotlin.collections.ArrayList as ArrayList

class ClothesAddActivity : BaseActivity<ActivityClothesAddBinding>({ActivityClothesAddBinding.inflate(it)}) {

    private val REQ_Gallery = 1

    private lateinit var category : String
    private lateinit var imageUri: String

    private lateinit var info: String
    private lateinit var name: String
    private lateinit var shop: String
    private lateinit var pageId: String
    private lateinit var titlepath: String
    private lateinit var size: ArrayList<String>


    private lateinit var saveDB: FirebaseFirestore

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
            categoryCheck()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == RESULT_OK){
            when(requestCode){
                REQ_Gallery -> {
                    data?.data?.let{ Uri ->
                        imageUri = Uri.toString()
                        binding.imageView.setImageURI(Uri)
                    }
                }
            }
        }

    }

    private  fun  categoryCheck() = with(binding){
        category = when{
            shirtsRdo.isSelected -> CATEGORY_T_SHIRT
            hoodRdo.isSelected -> CATEGORY_HOOD
            outerRdo.isSelected -> CATEGORY_OUTER
            pantsRdo.isSelected-> CATEGORY_PANTS
            else -> throw IllegalStateException("이외의 값은 들어올 수 없음")
        }
        sizeCheck()
    }
    private fun sizeCheck() = with(binding){
        sizeSBtn.setOnClickListener {
            if (sizeSBtn.isSelected){
                sizeSBtn.isSelected = true
                size.add(sizeSBtn.text.toString())
                binding.sizeSBtn.text = "눌림"
            } else {
                sizeSBtn.isSelected = false
                size.remove(sizeSBtn.text.toString())
            }
        }

        sizeMBtn.setOnClickListener {
            if (sizeMBtn.isSelected){
                sizeMBtn.isSelected = true
                size.add(sizeMBtn.text.toString())
            } else {
                sizeMBtn.isSelected = false
                size.remove(sizeMBtn.text.toString())
            }
        }

        sizeLBtn.setOnClickListener {
            if (sizeLBtn.isSelected){
                sizeLBtn.isSelected = true
                size.add(sizeLBtn.text.toString())
            } else {
                sizeLBtn.isSelected = false
                size.remove(sizeLBtn.text.toString())
            }
        }

        sizeXLBtn.setOnClickListener {
            if (sizeXLBtn.isSelected){
                sizeXLBtn.isSelected = true
                size.add(sizeXLBtn.text.toString())
            } else {
                sizeXLBtn.isSelected = false
                size.remove(sizeXLBtn.text.toString())
            }
        }

        sizeXXLBtn.setOnClickListener {
            if (sizeXXLBtn.isSelected){
                sizeXXLBtn.isSelected = true
                size.add(sizeXXLBtn.text.toString())
            } else {
                sizeXXLBtn.isSelected = false
                size.remove(sizeXXLBtn.text.toString())
            }
        }

        checkEmpty()
    }

    private fun checkEmpty() = with(binding){

        pageId = clothesNumEdt.text.toString()
        if (clothesNumEdt.length()==0){
            toast("빈칸을채워주세요")
        }
        name = clothesNameEdt.text.toString()
        if (clothesNameEdt.length()==0){
            toast("빈칸을채워주세요")
        }
        shop = clothesShopEdt.text.toString()
        if (clothesShopEdt.length()==0){
            toast("빈칸을채워주세요")
        }
        info = clothesInfoEdt.text.toString()
        if (clothesInfoEdt.length()==0){
            toast("빈칸을채워주세요")
        }

        setClothesInfoSave()
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
        saveDB = FirebaseFirestore.getInstance()

        val documentName = pageId + "_${System.currentTimeMillis()}"

        saveDB.collection(category).whereEqualTo("Page_ID", pageId).get()
            .addOnSuccessListener {
                if(it.isEmpty){
                    saveDB.collection(category).document(documentName).set(model)
                        .addOnSuccessListener {
                            toast("저장되었습니다")

                        }
                } else{
                    toast("이미 있는 상품코드입니다.")
                }
            }

    }

    }
