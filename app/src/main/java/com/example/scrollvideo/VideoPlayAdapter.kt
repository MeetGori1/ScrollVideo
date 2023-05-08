package com.example.scrollvideo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.DefaultHlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsDataSourceFactory
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollvideo.databinding.ItemVideoBinding


class VideoPlayAdapter() :
    RecyclerView.Adapter<VideoPlayAdapter.MyViewHolder>() {
    lateinit var list: ArrayList<String>
    fun setItems(dataList: List<String>) {
        list = dataList as ArrayList<String>
        notifyDataSetChanged()
    }

    fun upDateItem(dataList: ArrayList<String>) {
        list.addAll(dataList)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MyViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: String) {
            val dataSourceFactory: HlsDataSourceFactory.Factory = DefaultHlsDataSourceFactory.Factory()
            val temp ="https://vz-113d2b6a-c38.b-cdn.net/9c218eb2-0411-4e52-bf6d-ee26a47bd151/playlist.m3u8"
//            val hlsMediaSource =
//                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(temp))
            val hlsMediaSource =
                HlsMediaSource.Factory(dataSourceFactory)
                    .setAllowChunklessPreparation(false)
                    .createMediaSource(MediaItem.fromUri(temp))
//            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri("https://vz-113d2b6a-c38.b-cdn.net/9c218eb2-0411-4e52-bf6d-ee26a47bd151/playlist.m3u8"))
            val player = ExoPlayer.Builder(itemView.context).build()
            player.setMediaItem(hlsMediaSource)
            binding.media3PlayerView.player = player

            // Prepare the player.
            player.prepare()
            // Start the playback.
            player.play()

        }
    }
}