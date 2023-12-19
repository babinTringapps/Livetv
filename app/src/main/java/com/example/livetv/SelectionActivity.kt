package com.example.livetv

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.FragmentActivity

class SelectionActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        findViewById<Button>(R.id.media3Player)
            .setOnClickListener {
                startActivity(Intent(this, VideoPlayer::class.java))
            }

        findViewById<Button>(R.id.jwplayer)
            .setOnClickListener {
                startActivity(Intent(this, JWPlayerActivity::class.java))

            }
    }
}