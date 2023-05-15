package com.example.scrollvideo.other

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.media3.database.StandaloneDatabaseProvider
import androidx.media3.datasource.cache.LeastRecentlyUsedCacheEvictor
import androidx.media3.datasource.cache.SimpleCache


class Controller : Application() {
    lateinit var simpleCache: SimpleCache
    lateinit var audioManager: AudioManager
    lateinit var audioFocusRequest: AudioFocusRequest
    lateinit var audioFocusChangeListener: AudioManager.OnAudioFocusChangeListener

    @androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
    override fun onCreate() {
        super.onCreate()
        instance = this
         audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
//         audioFocusRequest = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
//            .build()
        //code to pause other app's sound while our app is playing
         audioFocusChangeListener = AudioManager.OnAudioFocusChangeListener { focusChange ->
            when (focusChange) {
                AudioManager.AUDIOFOCUS_GAIN -> {
                    // Resume playback
                }

                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK,
                AudioManager.AUDIOFOCUS_LOSS_TRANSIENT,
                AudioManager.AUDIOFOCUS_LOSS -> {
                    // Pause playback
                }
            }
        }

        audioFocusRequest =
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setOnAudioFocusChangeListener(audioFocusChangeListener)
                .build()

        val result =
            audioManager.requestAudioFocus(audioFocusRequest)

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // Start playback
        }

        //code for exoplayer cache
        lateinit var leastRecentlyUsedCacheEvictor: LeastRecentlyUsedCacheEvictor
        lateinit var exoDatabaseProvider: StandaloneDatabaseProvider

        val exoPlayerCacheSize: Long = 90 * 1024 * 1024

        leastRecentlyUsedCacheEvictor = LeastRecentlyUsedCacheEvictor(exoPlayerCacheSize)
        exoDatabaseProvider = StandaloneDatabaseProvider(applicationContext)
        simpleCache = SimpleCache(cacheDir, leastRecentlyUsedCacheEvictor, exoDatabaseProvider)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.getDefaultNightMode())
        registerActivityLifecycleCallbacks(activeActivityCallbacks)
    }

    override fun onTerminate() {
        unregisterActivityLifecycleCallbacks(activeActivityCallbacks)
        Log.e("terminated","terminated")
        instance.audioManager.abandonAudioFocusRequest(instance.audioFocusRequest)
        super.onTerminate()
    }

    companion object {
        var numberOfActionToOpenAppOfferDialog = 0
        lateinit var instance: Controller
        private val TAG = Controller::class.java.name
        var isShowRatingDialogPerSession = true
    }


    private val activeActivityCallbacks = ActiveActivityLifecycleCallbacks()

    internal class ActiveActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        private var activeActivity: Activity? = null

        fun getActiveActivity(): Activity? = activeActivity

        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            activeActivity = activity
        }

        override fun onActivityStarted(p0: Activity) {
            activeActivity = p0
        }

        override fun onActivityResumed(p0: Activity) {
        }

        override fun onActivityPaused(p0: Activity) {
        }

        override fun onActivityStopped(p0: Activity) {
        }

        override fun onActivitySaveInstanceState(p0: Activity, p1: Bundle) {
        }

        override fun onActivityDestroyed(activity: Activity) {
            if (activity === activeActivity) {
                activeActivity = null
            }
        }
    }
}

