//package com.example.fashionistamanager.main
//
//import android.content.Intent
//import android.os.Bundle
//import android.util.Log
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.fashionistamanager.adapter.MoreClothesAdapter
//import com.example.fashionistamanager.base.BaseActivity
//import com.example.fashionistamanager.databinding.ActivityClothesMoreBinding
//import com.google.firebase.firestore.CollectionReference
//import com.google.firebase.firestore.FirebaseFirestore
//import com.study.poly.fashionista.data.entity.ClothesEntity
//import com.study.poly.fashionista.utility.*
//import kotlinx.coroutines.*
//import kotlinx.coroutines.tasks.await
//import java.lang.Exception
//import java.net.UnknownHostException
//import kotlin.coroutines.CoroutineContext
//
//class ClothesMoreActivity :
//    BaseActivity<ActivityClothesMoreBinding>({ ActivityClothesMoreBinding.inflate(it) }),
//    CoroutineScope {
//
//    companion object {
//        const val CLOTHES_TYPE = "clothes_type"
//    }
//
//    private lateinit var db: CollectionReference
//    private val clothesList = ArrayList<ClothesEntity>()
//    private var path: String = ""
//    private val job: Job = Job()
//    override val coroutineContext: CoroutineContext
//        get() = Dispatchers.Main + job
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        viewInit()
//    }
//
//    private fun viewInit() = with(binding) {
//
//        val titleName = intent.getStringExtra(CLOTHES_TYPE)
//
//        path = when (titleName) {
//
//            ClothesType.HOOD.clothes -> {
//                "HOOD_INFO"
//            }
//            ClothesType.OUTER.clothes -> {
//                "OUTER_INFO"
//            }
//            ClothesType.PANTS.clothes -> {
//                "PANTS_INFO"
//            }
//            else -> {
//                "T_SHIRT_INFO"
//            }
//        }
//
//        titleLayout.titleTv.text = titleName
//        titleLayout.btnBack.setOnClickListener { onBackPressed() }
//
//        db = FirebaseFirestore.getInstance().collection(path)
//
//        notNetworkLayout.refreshBtn.onThrottleFirstClick {
//            notNetworkLayout.root.hideUI()
//            getData()
//        }
//        getData()
//        dialogShow()
//    }
//
//    private fun getData() {
//
//        launch {
//            try {
//                showProgress()
//                getList()
//            } catch (e: UnknownHostException) {
//                binding.notNetworkLayout.root.visibleUI()
//            } catch (e: Exception) {
//                toast("error:$e")
//            }
//        }
//    }
//
//    private suspend fun showProgress() = withContext(coroutineContext) {
//        binding.loadingBar.root.visibleUI()
//    }
//
//    private suspend fun dismissProgress() = withContext(coroutineContext) {
//        binding.loadingBar.root.hideUI()
//    }
//
//    private suspend fun getList() = withContext(Dispatchers.IO) {
//
//        clothesList.clear()
//
//        db.get().await().documents.forEach { document ->
//            document.toObject(ClothesEntity::class.java)?.let { data ->
//                clothesList.add(data)
//            }
//        }.run {
//            withContext(Dispatchers.Main) {
//                setRecyclerView()
//                dismissProgress()
//            }
//        }
//    }
//
//    private fun setRecyclerView() = with(binding) {
//
//        val nextIntent = Intent(this@ClothesMoreActivity, ClothesDetailActivity::class.java)
//
//        clothesRecyclerview.let { list ->
//            list.layoutManager = LinearLayoutManager(this@ClothesMoreActivity)
//            list.adapter = MoreClothesAdapter(clothesList) { url ->
//                nextIntent.putExtra(ClothesDetailActivity.CATEGORY_PATH, path)
//                nextIntent.putExtra(ClothesDetailActivity.IMAGE_PATH, url)
//                startActivity(nextIntent)
//                moveNextAnim()
//            }
//        }
//    }
//
//
//    override fun onDestroy() {
//        coroutineContext.cancel()
//        super.onDestroy()
//    }
//}