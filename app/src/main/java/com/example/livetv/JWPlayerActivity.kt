package com.example.livetv

import android.os.Bundle
import android.view.View
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import com.jwplayer.pub.api.JWPlayer
import com.jwplayer.pub.api.configuration.PlayerConfig
import com.jwplayer.pub.api.license.LicenseUtil
import com.jwplayer.pub.api.media.playlists.PlaylistItem
import com.jwplayer.pub.view.JWPlayerView


class JWPlayerActivity : FragmentActivity() {

   private var videoPlayers: Array<JWPlayer?> = arrayOfNulls(4)

    private val playerViews by lazy {
        arrayOf<JWPlayerView>(
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
            listOf(PlaylistItem.Builder()
                .file("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8")
                .build()),
            listOf(PlaylistItem.Builder()
                .file("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8")
                .build()),
            listOf(PlaylistItem.Builder()
                .file("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                .build()),
            listOf(PlaylistItem.Builder()
                .file("http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ElephantsDream.mp4")
                .build())
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_jwplayer)

        LicenseUtil().setLicenseKey(this, "b6SG3DX6lzTUfu5wgFi8ghBYP2nJkvVap25b8nrFq9nZTXaW")


        videoLayouts.forEachIndexed { index, layout ->
            layout.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    videoPlayers[index]!!.mute = false
                    otherPlayersMuted(index)
                }
            }
        }

        playerViews.forEachIndexed { index, playerView ->
            val mPlayer: JWPlayer = playerView.getPlayer()
            videoPlayers[index] = mPlayer
            val config = PlayerConfig.Builder()
                .playlist(mediaItems.get(index))
                .build()
            mPlayer.setup(config)
            mPlayer.controls = false
            mPlayer.play()
        }


        videoLayouts.first().requestFocus()
    }

    override fun onPause() {
        super.onPause()
        videoPlayers.forEach { it!!.pause() }
    }

    private fun otherPlayersMuted(currentIndex: Int) {
        videoPlayers.forEachIndexed { index, player ->
            if (index != currentIndex) {
                player!!.mute = true
            }
        }
    }
}