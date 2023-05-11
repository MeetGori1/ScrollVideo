package com.example.scrollvideo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.media3.common.Player
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollvideo.databinding.ItemVideoBinding


class VideoPlayAdapter(var list: ArrayList<VideoModel>) :
    RecyclerView.Adapter<VideoPlayAdapter.MyViewHolder>(), PlayerStateCallback {
    val TAG = "VIDEO PLAY ADAPTER"
    override fun onCreateViewHolder(
        parent: ViewGroup, viewType: Int,
    ): MyViewHolder {
        val binding =
            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val position = holder.absoluteAdapterPosition
        Log.e("TAG ", "ononBindViewHolderViewRecycled ")
        PlayerViewAdapter.releaseRecycledPlayers(position)
        super.onViewRecycled(holder)
        holder.bindItem(list[position])
    }
    override fun onViewRecycled(holder: VideoPlayAdapter.MyViewHolder) {
        val position = holder.absoluteAdapterPosition
        Log.e("TAG ", "onViewRecycled ")
        PlayerViewAdapter.releaseRecycledPlayers(position)
        super.onViewRecycled(holder)
    }
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
//        PlayerViewAdapter.playCurrentPlayer(recyclerView.getChildAdapterPosition(recyclerView))
        Log.e("TAG ", "onAttachedToRecyclerView ")
        super.onAttachedToRecyclerView(recyclerView)
    }
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
//        PlayerViewAdapter.pauseCurrentPlayer(recyclerView.getChildAdapterPosition(recyclerView))
        Log.e("TAG ", "onDetachedFromRecyclerView ")
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun onViewAttachedToWindow(holder: MyViewHolder) {
        PlayerViewAdapter.playCurrentPlayer(holder.absoluteAdapterPosition)
        Log.e("TAG ", "onViewAttachedToWindow ")
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: MyViewHolder) {
        PlayerViewAdapter.pauseCurrentPlayer(holder.absoluteAdapterPosition)
        Log.e("TAG ", "onViewDetachedFromWindow ")
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: VideoModel) {

            binding.apply {
                video = bean
                callback = this@VideoPlayAdapter
                index = absoluteAdapterPosition
                executePendingBindings()
            }

//            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()
//
//            val hlsMediaSource =
//                HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(bean))
//
//            val player = ExoPlayer.Builder(itemView.context).build()
//
//            player.setMediaSource(hlsMediaSource)
//            binding.media3PlayerView.player = player
//
//            player.prepare()
//            player.play()


//           BasePlayer.play(itemView.context,binding.media3PlayerView,bean)


        }
    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {

    }

    override fun onVideoBuffering(player: Player) {

    }

    override fun onStartedPlaying(player: Player) {

    }

    override fun onFinishedPlaying(player: Player) {

    }
}