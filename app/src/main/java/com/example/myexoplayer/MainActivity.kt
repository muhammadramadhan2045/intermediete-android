package com.example.myexoplayer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.media3.common.MediaItem
import com.example.myexoplayer.databinding.ActivityMainBinding
import androidx.media3.exoplayer.ExoPlayer

class MainActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //https://github.com/dicodingacademy/assets/releases/download/release-video/VideoDicoding.mp4
        val videoItem= MediaItem.fromUri("https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4")

        val player=ExoPlayer.Builder(this).build().also { exoPlayer ->
            exoPlayer.setMediaItem(videoItem)
            exoPlayer.prepare()
        }
        binding.playerView.player=player
    }
}