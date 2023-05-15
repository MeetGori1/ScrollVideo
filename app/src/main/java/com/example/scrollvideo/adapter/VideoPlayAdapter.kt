package com.example.scrollvideo.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.media3.common.Player
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollvideo.R
import com.example.scrollvideo.dataBindingClasses.PlayerStateCallback
import com.example.scrollvideo.dataBindingClasses.PlayerViewAdapter
import com.example.scrollvideo.databinding.ItemPollBinding
import com.example.scrollvideo.databinding.ItemThisOrThatBinding
import com.example.scrollvideo.databinding.ItemVideoBinding
import com.example.scrollvideo.model.VideoModel


class VideoPlayAdapter(var context: Context,var list: ArrayList<VideoModel>,val callback: ()->Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PlayerStateCallback {
    val TAG = "VIDEO PLAY ADAPTER"
    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_VIDEO || viewType == VIEW_TYPE_MUSIC) {
            return MyVideoViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_video,parent,false)
            )
        }else if (viewType == VIEW_TYPE_POLL){
            return MyPollViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_poll,parent,false)
            )
        }
        else if (viewType == VIEW_TYPE_THIS_OR_THAT){
            return MyThisOrThatViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_this_or_that,parent,false)
            )
        }
        else{
            return MyVideoViewHolder(
                DataBindingUtil.inflate(inflater, R.layout.item_video,parent,false)
            )
        }
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val position = holder.absoluteAdapterPosition
        Log.e("TAG ", "onViewRecycled ")
        PlayerViewAdapter.releaseRecycledPlayers(position)
        super.onViewRecycled(holder)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        PlayerViewAdapter.releaseRecycledPlayers(position)
        if (list[position].type == VIEW_TYPE_VIDEO || list[position].type == VIEW_TYPE_MUSIC) {
            (holder as MyVideoViewHolder).bindItem(list[position])
        }
        else if (list[position].type == VIEW_TYPE_POLL) {
            (holder as MyPollViewHolder).bindItem(list[position])
        }
        else if (list[position].type == VIEW_TYPE_THIS_OR_THAT) {
            (holder as MyThisOrThatViewHolder).bindItem(list[position])
        }
     else {
            (holder as MyVideoViewHolder).bindItem(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
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

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
            PlayerViewAdapter.playCurrentPlayer(holder.absoluteAdapterPosition)
        Log.e("TAG ", "onViewAttachedToWindow ")
        super.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        PlayerViewAdapter.pauseCurrentPlayer(holder.absoluteAdapterPosition)
        Log.e("TAG ", "onViewDetachedFromWindow ")
        super.onViewDetachedFromWindow(holder)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class MyVideoViewHolder(val binding: ItemVideoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: VideoModel) {
            binding.apply {
                video = bean
                callback = this@VideoPlayAdapter
                index = absoluteAdapterPosition
                executePendingBindings()
            }
        }
    }
    inner class MyThisOrThatViewHolder(val binding: ItemThisOrThatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: VideoModel) {
            binding.apply {
                thisOrThat = bean
                executePendingBindings()
            }
        }
    }

    inner class MyPollViewHolder(val binding: ItemPollBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: VideoModel) {
            binding.apply {
                poll = bean
                executePendingBindings()
            }
        }
    }

    override fun onVideoDurationRetrieved(duration: Long, player: Player) {
        Log.e("onVideoDurationRetrieved","onVideoDurationRetrieved")
    }

    override fun onVideoBuffering(player: Player) {
        Log.e("onVideoBuffering","onVideoBuffering")
    }

    override fun onStartedPlaying(player: Player) {
        Log.e("onStartedPlaying","onStartedPlaying")
    }

    override fun onFinishedPlaying(player: Player) {
        callback.invoke()
Log.e("onFinishedPlaying","onFinishedPlaying")
    }

    companion object {
        //for video
        const val VIEW_TYPE_VIDEO = 1
        //for poll
        const val VIEW_TYPE_POLL = 2
        //for music && grid
        const val VIEW_TYPE_MUSIC = 3
        //for this or that
        const val VIEW_TYPE_THIS_OR_THAT = 4
    }

}