package com.example.livetv

import android.content.Intent
import android.media.MediaCodecInfo
import android.media.MediaCodecList
import android.media.MediaFormat
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Button
import android.widget.TextView
import androidx.annotation.OptIn
import androidx.fragment.app.FragmentActivity
import androidx.media3.common.util.UnstableApi

class SelectionActivity : FragmentActivity() {

    @OptIn(UnstableApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_selection)

        val decoder = getDecodersFor(MediaFormat.MIMETYPE_VIDEO_AVC)
        findViewById<TextView>(R.id.decoderInfo).apply {
            text = decoder.toPrintable()
        }

        findViewById<Button>(R.id.media3Player)
            .setOnClickListener {
                startActivity(Intent(this, Media3PlayerActivity::class.java))
            }

        findViewById<Button>(R.id.jwPlayer)
            .setOnClickListener {
                startActivity(Intent(this, JWPlayerActivity::class.java))
            }
    }

    private fun getDecodersFor(mimeType: String): List<MediaCodecInfo> {
        val codecInfos = MediaCodecList(MediaCodecList.ALL_CODECS).codecInfos
        return codecInfos.filter { codecInfo ->
            !codecInfo.isEncoder && codecInfo.supportedTypes.any {
                it.equals(
                    mimeType,
                    ignoreCase = true
                )
            }
        }
    }
}
