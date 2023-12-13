package com.example.livetv

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.annotation.OptIn
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.MediaItem
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.DefaultRenderersFactory
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

class VideoPlayer : FragmentActivity() {
    private lateinit var  player:ExoPlayer
    private lateinit var  player2:ExoPlayer
    private lateinit var  player3:ExoPlayer
    private lateinit var  player4:ExoPlayer

    @OptIn(UnstableApi::class) override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        val playerView1 = findViewById<PlayerView>(R.id.videoPlayer1);
        val playerView2 = findViewById<PlayerView>(R.id.videoPlayer2);
        val playerView3 = findViewById<PlayerView>(R.id.videoPlayer3);
        val playerView4 = findViewById<PlayerView>(R.id.videoPlayer4);

        playerView1.useController = false
        playerView2.useController = false
        playerView3.useController = false
        playerView4.useController = false
        val playerLayout1 = findViewById<RelativeLayout>(R.id.videoFragment);
        val playerLayout2 = findViewById<RelativeLayout>(R.id.videoFragment1);
        val playerLayout3 = findViewById<RelativeLayout>(R.id.videoFragment2);
        val playerLayout4 = findViewById<RelativeLayout>(R.id.videoFragment3);

        val rf = DefaultRenderersFactory(this.applicationContext).setExtensionRendererMode(
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        )
        val rf1 = DefaultRenderersFactory(this.applicationContext).setExtensionRendererMode(
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        )
        val rf2 = DefaultRenderersFactory(this.applicationContext).setExtensionRendererMode(
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        )
        val rf3 = DefaultRenderersFactory(this.applicationContext).setExtensionRendererMode(
            DefaultRenderersFactory.EXTENSION_RENDERER_MODE_PREFER
        )

        player = ExoPlayer.Builder(this,rf).build()
        player2 = ExoPlayer.Builder(this,rf1).build()
        player3 = ExoPlayer.Builder(this,rf2).build()
        player4 = ExoPlayer.Builder(this,rf3).build()
        playerView1.player = player
        playerView2.player = player2
        playerView3.player = player3
        playerView4.player = player4

        playerLayout1.requestFocus()

        playerLayout1.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                Log.d("layout1::::", "" + hasFocus)

                val currentvolume = player.volume.toInt()
                player.volume = 1F

                val currentvolume1 = player2.volume.toInt()

                player2.volume = 0F

                val currentvolume3 = player3.volume.toInt()

                player3.volume = 0F

                val currentvolume4 = player4.volume.toInt()

                player4.volume = 0F


            }
        })

        playerLayout2.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                Log.d("layout2::::", "" + hasFocus)
                player2.volume = 1.0f
                player.volume = 0.0f
                player3.volume = 0.0f
                player4.volume = 0.0f

            }
        })
        playerLayout3.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                Log.d("layout3::::", "" + hasFocus)
                player3.volume = 1.0f
                player2.volume = 0.0f
                player.volume = 0.0f
                player4.volume = 0.0f

            }
        })

        playerLayout4.setOnFocusChangeListener(object : View.OnFocusChangeListener {
            override fun onFocusChange(v: View?, hasFocus: Boolean) {
                Log.d("layout4::::", "" + hasFocus)
                player4.volume = 1.0f
                player2.volume = 0.0f
                player3.volume = 0.0f
                player.volume = 0.0f

            }
        })


        val mediaItem = MediaItem.fromUri("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8")
        val mediaItem1 = MediaItem.fromUri("https://devstreaming-cdn.apple.com/videos/streaming/examples/img_bipbop_adv_example_fmp4/master.m3u8")
        val mediaItem2 = MediaItem.fromUri("https://demo.unified-streaming.com/k8s/features/stable/video/tears-of-steel/tears-of-steel.ism/.m3u8")
        val mediaItem3 = MediaItem.fromUri("https://cph-p2p-msl.akamaized.net/hls/live/2000341/test/master.m3u8")
// Set the media item to be played.
        player.setMediaItem(mediaItem)
        player2.setMediaItem(mediaItem1)
        player3.setMediaItem(mediaItem2)
        player4.setMediaItem(mediaItem3)
// Prepare the player.
        player.prepare()
        player2.prepare()
        player3.prepare()
        player4.prepare()
// Start the playback.
        player.play()
        player2.play()
        player3.play()
        player4.play()








    }

    override fun onPause() {
        super.onPause()
        player.pause()
        player2.pause()
        player3.pause()
        player4.pause()

    }
}