package com.example.scrollvideo.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.example.scrollvideo.adapter.VideoPlayAdapter
import com.example.scrollvideo.dataBindingClasses.PlayerViewAdapter
import com.example.scrollvideo.databinding.ActivityMainBinding
import com.example.scrollvideo.model.VideoModel


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var adapter: VideoPlayAdapter
    lateinit var layoutManager: LinearLayoutManager
    private val snapHelper: SnapHelper = PagerSnapHelper()


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        layoutManager = LinearLayoutManager(this)
//        layoutManager.initialPrefetchItemCount = 10
        binding.rwRecyclerView.layoutManager = layoutManager

        val music = VideoModel(
            "https://www.kozco.com/tech/piano2-CoolEdit.mp3",
            "https://picsum.photos/200/300", 3, "", "", "", "", ""
        )

        val poll = VideoModel(
            "", "", 2, "", "", "What you Like more ?", "Tea", "Coffee"
        )

        val thisOrThat = VideoModel(
            "",
            "",
            4,
            "https://picsum.photos/200",
            "https://picsum.photos/seed/picsum/200/300",
            "",
            "",
            ""
        )

        val video = VideoModel(
            "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/WeAreGoingOnBullrun.mp4",
            "https://picsum.photos/200/300", 1, "", "", "", "", ""
        )

        var list =
            arrayListOf(thisOrThat, video, music, poll, thisOrThat, poll, video, thisOrThat, video)
        setRecyclerViewScrollListener()

//        adapter.setItems(list)

        adapter = VideoPlayAdapter(this, list) {
            layoutManager.isSmoothScrollbarEnabled = true
            var position = layoutManager.findLastVisibleItemPosition()
            layoutManager.scrollToPosition(position + 1)
        }
        binding.rwRecyclerView.adapter = adapter
        binding.rwRecyclerView.isNestedScrollingEnabled = false

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