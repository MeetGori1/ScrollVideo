package com.example.scrollvideo

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.media3.common.MediaItem
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.DataSource
import androidx.media3.datasource.DefaultDataSource
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.datasource.FileDataSource
import androidx.media3.datasource.cache.CacheDataSink
import androidx.media3.datasource.cache.CacheDataSource
import androidx.media3.datasource.cache.NoOpCacheEvictor
import androidx.media3.datasource.cache.SimpleCache
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.hls.HlsMediaSource
import androidx.media3.ui.PlayerView
import java.io.File

open class BasePlayer:AppCompatActivity() {



        var exoPlayer:ExoPlayer ?=null
        @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
        fun  play(context:Context,exoPlayer1: PlayerView,bean:String){
//            if (exoPlayer?.isPlaying == true) return
            val DOWNLOAD_CONTENT_DIRECTORY = "downloads"

//            if (downloadCache==null){
//                val downloadContentDirectory =
//                    File(context.getExternalFilesDir(null),DOWNLOAD_CONTENT_DIRECTORY)
//                var downloadCache =
//                    SimpleCache(downloadContentDirectory, NoOpCacheEvictor(), StandaloneDatabaseProvider(context))
//            }


//             var simpleCache: SimpleCache? = null
//
//
//                if (simpleCache == null) {
//                    val cacheFolder = File(context.getExternalFilesDir(null), DOWNLOAD_CONTENT_DIRECTORY)
//                    simpleCache = SimpleCache(cacheFolder, NoOpCacheEvictor())
//                }
//
//
//            val cacheSink = CacheDataSink.Factory()
//                .setCache(simpleCache)
//            val upstreamFactory = DefaultDataSource.Factory(context, DefaultHttpDataSource.Factory())
//            val downStreamFactory = FileDataSource.Factory()
//            val cacheDataSourceFactory  =
//                CacheDataSource.Factory()
//                    .setCache(simpleCache)
//                    .setCacheWriteDataSinkFactory(cacheSink)
//                    .setCacheReadDataSourceFactory(downStreamFactory)
//                    .setUpstreamDataSourceFactory(upstreamFactory)
//                    .setFlags(CacheDataSource.FLAG_IGNORE_CACHE_ON_ERROR)

            val dataSourceFactory: DataSource.Factory = DefaultHttpDataSource.Factory()

            val hlsMediaSource = HlsMediaSource.Factory(dataSourceFactory).createMediaSource(MediaItem.fromUri(bean))

            exoPlayer = ExoPlayer.Builder(context).build()

            exoPlayer?.setMediaSource(hlsMediaSource)
            exoPlayer1.player = exoPlayer

            exoPlayer?.prepare()
            exoPlayer?.play()
        }

        fun pausePlayer(){
            exoPlayer?.pause()
        }

        fun stopPlayer(){
            exoPlayer?.stop()
        }

        fun resumePlayer(){
//            if (exoPlayer?.isPlaying == false) return
            exoPlayer?.play()
        }
}