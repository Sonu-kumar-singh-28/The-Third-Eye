package com.thethirdeye.esport.thethirdeye.stream

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.thethirdeye.esport.thethirdeye.databinding.ActivityStreamBinding
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.FullscreenListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.utils.YouTubePlayerTracker

class StreamActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStreamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStreamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(binding.youtubePlayerView)

        binding.youtubePlayerView.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {

            override fun onReady(youTubePlayer: YouTubePlayer) {

                val url = intent.getStringExtra("videoUrl") ?: ""
                val videoId = extractYoutubeId(url)

                youTubePlayer.loadVideo(videoId, 0f)

                // 🔥 FULLSCREEN BUTTON HANDLE
                binding.btnOpenStream.setOnClickListener {
                    youTubePlayer.toggleFullscreen()
                }
            }
        })

        // Optional fullscreen listener
        binding.youtubePlayerView.addFullscreenListener(object : FullscreenListener {
            override fun onEnterFullscreen(view: android.view.View, exitFullscreen: () -> Unit) {
                supportActionBar?.hide()
            }

            override fun onExitFullscreen() {
                supportActionBar?.show()
            }
        })
    }

    private fun extractYoutubeId(url: String): String {
        return when {
            url.contains("v=") ->
                url.substringAfter("v=").substringBefore("&")

            url.contains("youtu.be/") ->
                url.substringAfter("youtu.be/").substringBefore("?")

            else -> url
        }
    }
}