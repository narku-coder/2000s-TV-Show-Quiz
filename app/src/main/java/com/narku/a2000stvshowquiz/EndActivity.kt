package com.narku.a2000stvshowquiz

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.AspectRatioFrameLayout
import androidx.media3.ui.PlayerView
import com.google.android.material.button.MaterialButton


class EndActivity : AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer

    @UnstableApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_end)
        var intent: Intent = intent
        val score = intent.getIntExtra("scoreFinal", 0)

        /*val videoView = findViewById<VideoView>(R.id.endVideoView)
        val videoUri = Uri.parse("android.resource://" + packageName + "/" + R.raw.fireworks) // Replace with your video file
        videoView.setVideoURI(videoUri)
        videoView.setOnPreparedListener { mp ->
            mp.isLooping = true
            mp.setVideoScalingMode(MediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT)
            mp.start()
        }

        videoView.layoutParams = FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT,
        )*/

        val playerView = findViewById<PlayerView>(R.id.endVideoView)

        val player = ExoPlayer.Builder(this).build()
        playerView.setPlayer(player)


// Load the video
        val videoUri =
            Uri.parse("android.resource://" + packageName + "/" + R.raw.fireworks) // For local videos
        val mediaItem = MediaItem.fromUri(videoUri)
        player.setMediaItem(mediaItem)


        player.setRepeatMode(Player.REPEAT_MODE_ONE)

        player.prepare()
        player.play()

        playerView.resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM

        val scoreText: TextView = findViewById(R.id.final_score_text)
        scoreText.text = "End Of Quiz.\n\nFinal Score: $score"

        val buttonHome = findViewById<MaterialButton>(R.id.home_button)
        buttonHome.setOnClickListener {
            val intentMain = Intent(this, MainActivity::class.java)
            startActivity(intentMain)
        }

        val buttonQuit = findViewById<MaterialButton>(R.id.exit_button)
        buttonQuit.setOnClickListener {
            finishAffinity() // Quits the entire app
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.happyrock) // Replace with your audio file
        mediaPlayer.isLooping = true
        mediaPlayer.start()

        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
}