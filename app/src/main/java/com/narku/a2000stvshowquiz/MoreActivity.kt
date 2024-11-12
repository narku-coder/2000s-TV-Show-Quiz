package com.narku.a2000stvshowquiz

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.Firebase
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.storage
import com.narku.a2000stvshowquiz.databinding.ActivityMoreBinding
import java.io.File

class MoreActivity : AppCompatActivity() {

    var storage: FirebaseStorage = Firebase.storage("gs://kids-tv-quiz-2000s.appspot.com")
    var vidFile: File = File("")
    var position: Int = 0
    lateinit var showVideoView: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more)

        var intent: Intent = intent
        val synopsis = intent.getStringExtra("synopsis")
        val vidName = intent.getStringExtra("vidName")


        val synopsisText: TextView = findViewById(R.id.synopsis_text)
        synopsisText.text = synopsis

        showVideoView = findViewById(R.id.video_player)
        var mediaController = MediaController(this)
        if (mediaController == null) {
            mediaController.setAnchorView(showVideoView)
            showVideoView.setMediaController(mediaController)
        }
        try {
            val gsReference = storage.getReferenceFromUrl("gs://kids-tv-quiz-2000s.appspot.com/" + vidName.toString() + ".mp4")
            vidFile = File.createTempFile(vidName, "mp4")
            gsReference.getFile(vidFile)
                .addOnSuccessListener(OnSuccessListener<FileDownloadTask.TaskSnapshot?> {
                    Log.i("Successful download", "The download was successful")
                    showVideoView.setVideoURI(Uri.fromFile(vidFile))
                    showVideoView.requestFocus()
                    showVideoView.setOnPreparedListener(MediaPlayer.OnPreparedListener { param1MediaPlayer ->
                        showVideoView.seekTo(position)
                        if (position === 0) showVideoView.start()
                    })
                }).addOnFailureListener(OnFailureListener { exception -> // Handle any errors
                    Log.e("Download Error", exception.message.toString())
                    exception.printStackTrace()
                })
        } catch (exception: Exception) {
            Log.e("Error", exception.message.toString())
            exception.printStackTrace()
        }


        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener { finish() }


    }

}