package com.narku.a2000stvshowquiz

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class InfoActivity : AppCompatActivity() {
    lateinit var synopsis: String
    lateinit var vidName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info)

        val intent: Intent = intent
        val title = intent.getStringExtra("title")
        val network = intent.getStringExtra("network")
        val yearsAired = intent.getStringExtra("yearsAired")
        val seasonNum = intent.getIntExtra("seasonNum", 0)
        synopsis = intent.getStringExtra("synopsis").toString()
        vidName = intent.getStringExtra("vidName").toString()

        val titleText: TextView = findViewById(R.id.title_text)
        titleText.text = title
        val networkText: TextView = findViewById(R.id.network_text)
        networkText.text = network
        val yearAiredText: TextView = findViewById(R.id.year_aired_text)
        yearAiredText.text = yearsAired
        val seasonNumText: TextView = findViewById(R.id.season_num_text)
        seasonNumText.text = "$seasonNum"

        val backButton: Button = findViewById(R.id.back_button)
        backButton.setOnClickListener { finish() }
        val moreButton: Button = findViewById(R.id.more_button)
        moreButton.setOnClickListener {
            val intentMore = Intent(this, MoreActivity::class.java)
            intentMore.putExtra("synopsis", synopsis)
            intentMore.putExtra("vidName", vidName)
            startActivity(intentMore) }
    }

}