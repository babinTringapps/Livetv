package com.example.livetv

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.C
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
        arrayOf<FrameLayout>(
            findViewById(R.id.videoFragment1),
            findViewById(R.id.videoFragment2),
            findViewById(R.id.videoFragment3),
            findViewById(R.id.videoFragment4)
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

        videoLayouts.forEachIndexed { focusedIndex, layout ->
            layout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (!hasFocus) return@OnFocusChangeListener

                videoPlayers.forEachIndexed { index, player ->
                    player.trackSelectionParameters = player.trackSelectionParameters
                        .buildUpon()
                        .setTrackTypeDisabled(C.TRACK_TYPE_AUDIO, index != focusedIndex)
                        .build();
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

    override fun onDestroy() {
        super.onDestroy()
        videoPlayers.forEach { it.release() }
    }
}
