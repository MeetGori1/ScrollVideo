package com.example.scrollvideo

import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.example.scrollvideo.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoPlayAdapter
    lateinit var layoutManager: GridLayoutManager
    val snapHelper: SnapHelper = PagerSnapHelper()

    var list: ArrayList<VideoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        layoutManager = GridLayoutManager(this, 1)
        layoutManager.initialPrefetchItemCount = 10
        binding.rwRecyclerView.layoutManager = layoutManager
        val temp1 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp2 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp3 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerEscapes.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp4 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerFun.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp5 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerJoyrides.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp6 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/VolkswagenGTIReview.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp7 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp8 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WhatCarCanYouGetForAGrand.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp9 = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerMeltdowns.mp4",
            "https://picsum.photos/200/300",false
        )
        val temp10 = VideoModel(
            "https://actions.google.com/sounds/v1/science_fiction/ringing_ambient_background.ogg",
            "https://picsum.photos/200/300",true
        )

        list = arrayListOf(temp10,temp1, temp2, temp3, temp4, temp6, temp7, temp8, temp9,temp5)
        setRecyclerViewScrollListener()

//        adapter.setItems(list)

        adapter = VideoPlayAdapter(list)
        binding.rwRecyclerView.adapter = adapter
        binding.rwRecyclerView.isNestedScrollingEnabled=false

        snapHelper.attachToRecyclerView(binding.rwRecyclerView)
//        scrollListener = object : RecyclerViewScrollListener() {
//            override fun onItemIsFirstVisibleItem(index: Int) {
//                Log.d("visible item index", index.toString())
//                // play just visible item
//                if (index != -1)
//                    PlayerViewAdapter.playIndexThenPausePreviousPlayer(index)
//            }
//
//        }


//        for (i in 1..10) {
//            list.add("http://content.jwplatform.com/manifests/vM7nH0Kl.m3u8")
//            list.add("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8")
//            list.add("http://playertest.longtailvideo.com/adaptive/wowzaid3/playlist.m3u8")
//            list.add("https://assets.afcdn.com/video49/20210722/v_645516.m3u8")
//            list.add("http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8")
//        }


    }


    private fun setRecyclerViewScrollListener() {

//        scrollListener = object : RecyclerViewScrollListener() {
//            override fun onItemIsFirstVisibleItem(index: Int) {
//                Log.d("visible item index", index.toString())
//                // play just visible item
//                if (index != -1)
//                    PlayerViewAdapter.playIndexThenPausePreviousPlayer(index)
//            }
//
//        }


//        val scrollListener = object : RecyclerView.OnScrollListener() {
//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                val totalItemCount = recyclerView.layoutManager?.itemCount
//                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
////                if (totalItemCount == lastVisibleItemPosition + 1) {
//                Log.d("MyTAG", "Load new list")
//                PlayerViewAdapter.playIndexThenPausePreviousPlayer(lastVisibleItemPosition)
////                binding.rwRecyclerView.removeOnScrollListener(this)
////                }
//            }
//        }
//        binding.rwRecyclerView.addOnScrollListener(scrollListener)
    }

    override fun onPause() {
        super.onPause()
        PlayerViewAdapter.pauseAllPlayer()
//        Controller.instance.audioManager.abandonAudioFocusRequest(Controller.instance.audioFocusRequest)
    }

    override fun onResume() {
        val position = layoutManager.findLastVisibleItemPosition()
        Log.e("current position", position.toString())
        PlayerViewAdapter.playCurrentPlayer(position)
        super.onResume()
    }
}