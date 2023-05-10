package com.example.scrollvideo

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
    private lateinit var scrollListener: RecyclerViewScrollListener

    var list: ArrayList<VideoModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        layoutManager = GridLayoutManager(this, 1)
        layoutManager.initialPrefetchItemCount = 5
        binding.rwRecyclerView.layoutManager = layoutManager
        val temp1=VideoModel("http://content.jwplatform.com/manifests/vM7nH0Kl.m3u8","https://picsum.photos/200/300")
        val temp2=VideoModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4","https://picsum.photos/200/300")
        val temp3=VideoModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4","https://picsum.photos/200/300")
        val temp4=VideoModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4","https://picsum.photos/200/300")
        val temp5=VideoModel("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4","https://picsum.photos/200/300")

        list= arrayListOf(temp1,temp2,temp3,temp4,temp5)
        setRecyclerViewScrollListener()

//        adapter.setItems(list)

        adapter = VideoPlayAdapter(list)
        binding.rwRecyclerView.adapter = adapter
        val snapHelper: SnapHelper = PagerSnapHelper()
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

        scrollListener = object : RecyclerViewScrollListener() {
            override fun onItemIsFirstVisibleItem(index: Int) {
                Log.d("visible item index", index.toString())
                // play just visible item
                if (index != -1)
                    PlayerViewAdapter.playIndexThenPausePreviousPlayer(index)
            }

        }


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
    }

    override fun onResume() {
        PlayerViewAdapter.resumeAllPlayer()
        super.onResume()
    }
}