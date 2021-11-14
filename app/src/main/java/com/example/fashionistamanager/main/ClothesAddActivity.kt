package com.example.fashionistamanager.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import com.example.fashionistamanager.base.BaseActivity
import com.example.fashionistamanager.data.ClothesSaveModel
import com.example.fashionistamanager.databinding.ActivityClothesAddBinding
import com.example.fashionistamanager.utility.Constant.CATEGORY_HOOD
import com.example.fashionistamanager.utility.Constant.CATEGORY_OUTER
import com.example.fashionistamanager.utility.Constant.CATEGORY_PANTS
import com.example.fashionistamanager.utility.Constant.CATEGORY_T_SHIRT
import com.example.fashionistamanager.utility.Constant.PATH_HOOD
import com.example.fashionistamanager.utility.Constant.PATH_OUTER
import com.example.fashionistamanager.utility.Constant.PATH_PANTS
import com.example.fashionistamanager.utility.Constant.PATH_T_SHIRT
import com.example.fashionistamanager.utility.onThrottleFirstClick
import com.example.fashionistamanager.utility.toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.CoroutineContext

class ClothesAddActivity :
    BaseActivity<ActivityClothesAddBinding>({ ActivityClothesAddBinding.inflate(it) }),
    CoroutineScope {

    private val job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job
    private val REQ_Gallery = 1

    private lateinit var category: String
    private lateinit var path: String

    private lateinit var Info: String
    private lateinit var Name: String
    private lateinit var Shop: String
    private lateinit var Page_ID: String
    private lateinit var Titlepath: String
    private var Size = ArrayList<String>()

    private var imageUri: Uri? = null
    private val storage = FirebaseStorage.getInstance()
    private val fireStore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewInit()

    }

    private fun viewInit() = with(binding) {
        layoutTitle.titleTv.text = "상품추가"
        layoutTitle.btnBack.setOnClickListener { onBackPressed() }

        binding.getImgBtn.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            startActivityForResult(intent, REQ_Gallery)
        }
        btnAddClothesInfo.onThrottleFirstClick {
            categoryCheck()
            sizeCheck()
            checkEmpty()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                REQ_Gallery -> {
                    data?.data?.let { Uri ->
                        imageUri = Uri
                        binding.imageView.setImageURI(Uri)
                    }
                }
            }
        }

    }

    private fun categoryCheck() = with(binding) {
        category = when {
            shirtsRdo.isChecked -> CATEGORY_T_SHIRT
            hoodRdo.isChecked -> CATEGORY_HOOD
            outerRdo.isChecked -> CATEGORY_OUTER
            pantsRdo.isChecked -> CATEGORY_PANTS
            else -> throw IllegalStateException("이외의 값은 들어올 수 없음")
        }

        path = when {
            shirtsRdo.isChecked -> PATH_T_SHIRT
            hoodRdo.isChecked -> PATH_HOOD
            outerRdo.isChecked -> PATH_OUTER
            pantsRdo.isChecked -> PATH_PANTS
            else -> throw IllegalStateException("이외의 값은 들어올 수 없음")
        }
    }

    private fun sizeCheck() = with(binding) {
        if(sizeSCb.isChecked){
            Size.add(sizeSCb.text.toString())
        }else{
            Size.remove(sizeSCb.text.toString())
        }

        if(sizeMCb.isChecked){
            Size.add(sizeMCb.text.toString())
        }else{
            Size.remove(sizeMCb.text.toString())
        }

        if(sizeLCb.isChecked){
            Size.add(sizeLCb.text.toString())
        }else{
            Size.remove(sizeLCb.text.toString())
        }

        if(sizeXLCb.isChecked){
            Size.add(sizeXLCb.text.toString())
        }else{
            Size.remove(sizeXLCb.text.toString())
        }

        if(sizeXXLCb.isChecked){
            Size.add(sizeXXLCb.text.toString())
        }else{
            Size.remove(sizeXXLCb.text.toString())
        }
    }

    private fun checkEmpty() = with(binding) {

        Page_ID = clothesNumEdt.text.toString()
        if (clothesNumEdt.length() == 0) {
            toast("빈칸을채워주세요")
        }
        Name = clothesNameEdt.text.toString()
        if (clothesNameEdt.length() == 0) {
            toast("빈칸을채워주세요")
        }
        Shop = clothesShopEdt.text.toString()
        if (clothesShopEdt.length() == 0) {
            toast("빈칸을채워주세요")
        }
        Info = clothesInfoEdt.text.toString()
        if (clothesInfoEdt.length() == 0) {
            toast("빈칸을채워주세요")
        }
        sendData()
    }

    private fun sendData() {

        launch {
            try {
                addProduct()
            } catch (e: Exception) {
                toast("error:$e")
            }
        }
    }

    private suspend fun addProduct() {
        val timeStamp = SimpleDateFormat("yyyyMMdd+HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_.png"
        val storageMainRef = storage.reference.child("main_img/").child(path).child(imageFileName)
        val storageProductRef = storage.reference.child("product_img/").child(path).child(imageFileName)

//        Titlepath = storageMainRef.downloadUrl.toString()

        imageUri?.let { uri ->
            storageProductRef.putFile(uri).await()
            storageMainRef.putFile(uri).await().also {
                storageMainRef.downloadUrl.await().let { imgPath ->

                    val model = ClothesSaveModel(
                        Info,
                        Name,
                        Shop,
                        Page_ID,
                        imgPath.toString(),
                        Size
                    )

                    fireStore.collection(category).document().set(model).await().also {
                        withContext(Dispatchers.Main) {
                            toast("추가완료")
                            val intent = Intent(this@ClothesAddActivity, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }
                }
            }
        }
    }
}
