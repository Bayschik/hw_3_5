package com.example.lesson_3_5

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson_3_5.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private var adapter = ImageAdapter(mutableListOf())
    private lateinit var binding: ActivityMainBinding
    var page = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()
    }

    private fun initClickers() {
        with(binding) {
            btnPage.setOnClickListener {
                page++
                requestImage()
            }
            btnSearch.setOnClickListener {
                firstRequestImages()
            }

            imageRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val layoutManager = imageRecycler.layoutManager as LinearLayoutManager
                    val visibleImage = layoutManager.childCount
                    val totalCount = layoutManager.itemCount
                    val firstImage = layoutManager.findFirstVisibleItemPosition()

                    if ((visibleImage + firstImage) >= totalCount
                        && firstImage >= 0
                        && totalCount >= PAGE_SIZE
                    ) {
                        page++
                        requestImage()
                    }
                }
            })
        }
    }

    private fun ActivityMainBinding.firstRequestImages() {
        RetrofitService.api.getImages(keyWord = etImage.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            adapter.reloadImages(it.hits)
                            imageRecycler.adapter = adapter
                        }
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {

                }

            })
    }

    private fun ActivityMainBinding.requestImage() {
        RetrofitService.api.getImages(keyWord = etImage.text.toString(), page = page)
            .enqueue(object : Callback<PixaModel> {
                override fun onResponse(
                    call: Call<PixaModel>,
                    response: Response<PixaModel>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            adapter.addImages(it.hits)
                            imageRecycler.adapter = adapter
                        }
                    }
                }

                override fun onFailure(call: Call<PixaModel>, t: Throwable) {

                }

            })
    }
}