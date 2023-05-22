package com.example.scrollvideo.dataBindingClasses

import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.DefaultMediaSourceFactory
import androidx.media3.ui.PlayerView
import com.example.scrollvideo.other.Controller


//// extension function for show toast
//fun Context.toast(text: String){
//    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
//}

class PlayerViewAdapter {

    companion object {
        // for hold all players generated
        private var playersMap: MutableMap<Int, ExoPlayer> = mutableMapOf()

        // for hold current player
        private var currentPlayingVideo: Pair<Int, ExoPlayer>? = null
        fun releaseAllPlayers() {
            playersMap.map {
                it.value.release()
            }
        }

        fun pauseAllPlayer() {
            playersMap.map {
                it.value.pause()

                Controller.instance.audioManager.abandonAudioFocus(Controller.instance.audioFocusChangeListener)
                Controller.instance.audioManager.abandonAudioFocusRequest(Controller.instance.audioFocusRequest)
            }
        }

        fun resumeAllPlayer() {
            playersMap.map {
                it.value.play()
            }
        }


        // call when item recycled to improve performance
        fun releaseRecycledPlayers(index: Int) {
            playersMap[index]?.release()
        }

        fun pauseCurrentPlayer(index: Int) {
            playersMap[index]?.pause()
        }

        fun playCurrentPlayer(index: Int) {
            if (playersMap[index]?.isPlaying==false) {
                playersMap[index]?.play()
            }
        }


        // call when scroll to pause any playing player
        private fun pauseCurrentPlayingVideo() {
            if (currentPlayingVideo != null) {
                currentPlayingVideo?.second?.playWhenReady = false
            }
        }

        fun playIndexThenPausePreviousPlayer(index: Int) {
            if (playersMap[index]?.playWhenReady == false) {
                pauseCurrentPlayingVideo()
                playersMap[index]?.playWhenReady = true
                currentPlayingVideo = Pair(index, playersMap[index]!!)
            }

        }

        /*
        *  url is a url of stream video
        *  progressbar for show when start buffering stream
        * thumbnail for show before video start
        * */

        @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
        @JvmStatic
        @BindingAdapter(
            value = ["video_url", "on_state_change", "progressbar", "thumbnail", "item_index", "autoPlay"],
            requireAll = false
        )
        fun PlayerView.loadVideo(
            url: String,
            callback: PlayerStateCallback,
            progressbar: ProgressBar,
            thumbnail: ImageView,
            item_index: Int? = null,
            autoPlay: Boolean = false
        ) {
            if (url == null) return
            if (url=="") return
            var player: ExoPlayer

            val httpDataSourceFactory = DefaultHttpDataSource.Factory()
                .setAllowCrossProtocolRedirects(true)

            val cacheDataSourceFactory = CacheDataSource.Factory()
                .setCache(Controller.instance.simpleCache)
                .setUpstreamDataSourceFactory(httpDataSourceFactory)
                .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

            player = ExoPlayer.Builder(context)
                .setRenderersFactory(DefaultRenderersFactory(context).setEnableDecoderFallback(true))
                .setMediaSourceFactory(DefaultMediaSourceFactory(cacheDataSourceFactory)).build()
            thumbnail.visibility = View.VISIBLE
            val videoUri = MediaItem.fromUri(Uri.parse(url.orEmpty())).buildUpon().build()
            player.playWhenReady = false
            player.repeatMode = Player.REPEAT_MODE_OFF
            player.setMediaItem(videoUri)
            // When changing track, retain the latest frame instead of showing a black screen
            setKeepContentOnPlayerReset(true)
            setPlayer(player)
            // We'll show the controller, change to true if want controllers as pause and start
            this.useController = true
            // Provide url to load the video from here
//            val mediaSource = ProgressiveMediaSource.Factory(DefaultHttpDataSourceFactory("Demo")).createMediaSource(Uri.parse(url))
//            player.setMediaItem(MediaItem.fromUri(Uri.parse(url)))

            player.prepare()

            this.player = player

            // add player with its index to map
            if (playersMap.containsKey(item_index))
                playersMap.remove(item_index)
            if (item_index != null)
                playersMap[item_index] = player
            thumbnail.visibility = View.VISIBLE
//            }
            this.player!!.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == ExoPlayer.STATE_BUFFERING) {
                        callback.onVideoBuffering(player)
                        // Buffering..
                        // set progress bar visible here
                        // set thumbnail visible
//                        thumbnail.visibility = View.VISIBLE
                        progressbar.visibility = View.VISIBLE
                    }
                    if (playbackState == ExoPlayer.STATE_READY) {
                        // [PlayerView] has fetched the video duration so this is the block to hide the buffering progress bar
                        progressbar.visibility = View.GONE
                        // set thumbnail gone
//                        thumbnail.visibility = View.GONE
                        callback.onVideoDurationRetrieved(this@loadVideo.player!!.duration, player)
                    }
                    if (playbackState == Player.STATE_READY ) {
                        // [PlayerView] has started playing/resumed the video
                        callback.onStartedPlaying(player)
                    }
                    if (playbackState == Player.STATE_ENDED ) {
                        Log.e("Video Finished","Ended")
                        // [PlayerView] has started playing/resumed the video
//                        player.playWhenReady = true
                        player.seekTo(1)
                        callback.onFinishedPlaying(player)
                    }
                }
            })

//            if (type=="Music"){
//                thumbnail.visibility = View.VISIBLE
//            }

        }
    }
}