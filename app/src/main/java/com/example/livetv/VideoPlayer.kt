package com.example.livetv

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.OptIn
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.C
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.util.EventLogger
import androidx.media3.ui.PlayerView


@UnstableApi
class VideoPlayer : FragmentActivity() {

    companion object {
        private const val NUMBER_OF_MULTI_VIEW = 4
    }

    private val defaultRenderersFactory by lazy {
        DefaultRenderersFactory(this.applicationContext)
            /*.setExtensionRendererMode(DefaultRenderersFactory.EXTENSION_RENDERER_MODE_ON)
            .forceEnableMediaCodecAsynchronousQueueing()*/
    }

    private val videoPlayers by lazy {
        Array(NUMBER_OF_MULTI_VIEW) {
            ExoPlayer.Builder(this, defaultRenderersFactory).build()
        }
    }

    private val playerViews by lazy {
        val playerViewIds = arrayOf(
            R.id.videoPlayer1,
            R.id.videoPlayer2,
            R.id.videoPlayer3,
            R.id.videoPlayer4
        )
        arrayListOf<PlayerView>().apply {
            videoPlayers.forEachIndexed { index, _ ->
                add(findViewById(playerViewIds[index]))
            }
        }
    }

    private val videoLayouts by lazy {
        val videoLayoutIds = arrayOf(
            R.id.videoFragment1,
            R.id.videoFragment2,
            R.id.videoFragment3,
            R.id.videoFragment4
        )
        arrayListOf<FrameLayout>().apply {
            videoPlayers.forEachIndexed { index, _ ->
                add(findViewById(videoLayoutIds[index]))
            }
        }
    }

    private val mediaItems by lazy {
        listOf(
            MediaItem.fromUri("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"),
            MediaItem.fromUri("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8"),
            MediaItem.fromUri("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8"),
            MediaItem.fromUri("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoLayouts.forEachIndexed { focusedIndex, layout ->
            layout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) return@OnFocusChangeListener

                videoPlayers.forEachIndexed { index, player ->
                    player.trackSelectionParameters = player.trackSelectionParameters
                        .buildUpon()
                        .setTrackTypeDisabled(C.TRACK_TYPE_AUDIO, index != focusedIndex)
                        .build()
                }
            }
        }

        videoPlayers.forEachIndexed { index, player ->
            player.setMediaItem(mediaItems[index])
            player.addAnalyticsListener(EventLogger("EventLogger_${index + 1}"))
            player.prepare()
            player.play()
        }

        playerViews.forEachIndexed { index, playerView ->
            playerView.useController = false
            playerView.player = videoPlayers[index]
        }

        videoLayouts.first().requestFocus()
    }

    override fun onPause() {
        super.onPause()
        videoPlayers.forEach { it.pause() }
    }

    override fun onDestroy() {
        super.onDestroy()
        videoPlayers.forEach { it.release() }
    }
}
