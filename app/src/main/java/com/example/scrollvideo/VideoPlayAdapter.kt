package com.example.scrollvideo

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.media3.common.Player
import androidx.recyclerview.widget.RecyclerView
import com.example.scrollvideo.databinding.ItemPollBinding
import com.example.scrollvideo.databinding.ItemVideoBinding


class VideoPlayAdapter(var context: Context,var list: ArrayList<VideoModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(), PlayerStateCallback {
    val TAG = "VIDEO PLAY ADAPTER"
    private val inflater = LayoutInflater.from(context)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == VIEW_TYPE_ONE || viewType == VIEW_TYPE_THREE) {
            return MyVideoViewHolder(
                DataBindingUtil.inflate(inflater,R.layout.item_video,parent,false)
            )
        }
        else{
            return MyPollViewHolder(
                DataBindingUtil.inflate(inflater,R.layout.item_poll,parent,false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (list[position].type == VIEW_TYPE_ONE || list[position].type == VIEW_TYPE_THREE) {
            (holder as MyVideoViewHolder).bindItem(list[position])
        } else {
            (holder as MyPollViewHolder).bindItem(list[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type
    }
//    override fun onCreateViewHolder(
//        parent: ViewGroup, viewType: Int,
//    ): MyViewHolder {
//        val binding =
//            ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return MyViewHolder(binding)
//    }

//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val position = holder.absoluteAdapterPosition
//        Log.e("TAG ", "ononBindViewHolderViewRecycled ")
//        PlayerViewAdapter.releaseRecycledPlayers(position)
//        super.onViewRecycled(holder)
//        holder.bindItem(list[position])
//    }
//    override fun onViewRecycled(holder: VideoPlayAdapter.MyViewHolder) {
//        val position = holder.absoluteAdapterPosition
//        Log.e("TAG ", "onViewRecycled ")
//        PlayerViewAdapter.releaseRecycledPlayers(position)
//        super.onViewRecycled(holder)
//    }
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

    inner class MyPollViewHolder(val binding: ItemPollBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(bean: VideoModel) {
            binding.apply {
                video = bean
                executePendingBindings()
            }
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

    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
        const val VIEW_TYPE_THREE = 3
        const val VIEW_TYPE_FOUR = 4
    }

}