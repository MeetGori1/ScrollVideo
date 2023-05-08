package com.example.scrollvideo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.scrollvideo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoPlayAdapter
    lateinit var layoutManager: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        layoutManager = GridLayoutManager(this, 1)
        binding.rwRecyclerView.layoutManager = layoutManager

        adapter = VideoPlayAdapter()
        binding.rwRecyclerView.adapter = adapter
        val snapHelper: SnapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.rwRecyclerView)

        val list = arrayListOf<String>()
        for (i in 1..10) {
            list.add("https://sirqil.b-cdn.net/posts-file/1683548504569_683633/1683548504569_683633.mp4")
        }

        adapter.setItems(list)
    }
}