package com.example.livetv

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoPlayer : FragmentActivity() {

    private val videoPlayers by lazy { Array(4) { ExoPlayer.Builder(this).build() } }

    private val playerViews by lazy {
        arrayOf<PlayerView>(
            findViewById(R.id.videoPlayer1),
            findViewById(R.id.videoPlayer2),
            findViewById(R.id.videoPlayer3),
            findViewById(R.id.videoPlayer4)
        )
    }

    private val videoLayouts by lazy {
        arrayOf<RelativeLayout>(
            findViewById(R.id.videoFragment),
            findViewById(R.id.videoFragment1),
            findViewById(R.id.videoFragment2),
            findViewById(R.id.videoFragment3)
        )
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

        videoLayouts.forEachIndexed { index, layout ->
            layout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    videoPlayers[index].volume = 1f
                    otherPlayersMuted(index)
                }
            }
        }

        videoPlayers.forEachIndexed { index, player ->
            player.setMediaItem(mediaItems[index])
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

    private fun otherPlayersMuted(currentIndex: Int) {
        videoPlayers.forEachIndexed { index, player ->
            if (index != currentIndex) {
                player.volume = 0f
            }
        }
    }
}