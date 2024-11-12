package com.narku.a2000stvshowquiz

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.narku.a2000stvshowquiz.data.Question
import com.narku.a2000stvshowquiz.data.QuestionController
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuizActivity : AppCompatActivity() {

    var questions = ArrayList<Question>(40)
    var score = 0
    var index = 0
    var pos = 0
    lateinit var questionText: TextView
    lateinit var scoreText:TextView
    lateinit var ct: CountDownTimer
    lateinit var musicPlayer: MediaPlayer
    //lateinit var questCont: QuestionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_quiz)

        musicPlayer = MediaPlayer.create(this, R.raw.glacier.toInt())
        musicPlayer.setVolume(100f, 100f)
        musicPlayer.start()
        fillQuestionList()
        questions.shuffle()

        //questCont = QuestionController()
        //questCont.start()

        //questCont.questions.shuffle()
        val soundButton: Button = findViewById(R.id.mute_button)
        soundButton.setOnClickListener {
            if (musicPlayer.isPlaying) {
                musicPlayer.pause()
                soundButton.setText(getString(R.string.unmute_music_text))
            } else if (!(musicPlayer.isPlaying)) {
                musicPlayer.start()
                soundButton.setText(getString(R.string.mute_music_text))
            }
        }
        val timerText: TextView = findViewById(R.id.time_text)
        ct = object : CountDownTimer(30000, 1000) {
            override fun onTick(millis: Long) {
                val seconds = millis / 1000
                timerText.setText("Time: " + "$seconds")
            }

            override fun onFinish() {
                updateQuestion()
                this.start()
            }
        }
        ct.start()

        Log.i("QuizActivity", "Index at onCreate - " + index)
        questionText = findViewById(R.id.question_text)
        if (!(questions.isEmpty()))
        {
            questionText.setText(/*questCont.*/questions.get(index).title)
        }
        else
        {
            questionText.setText("Sample Show")
        }

        val cartoonButton: Button = findViewById(R.id.cartoon_button)
        cartoonButton.setOnClickListener {
            var answer = "Cartoon Network"
            checkAnswer(answer)
            updateQuestion()
        }

        val discoveryButton: Button = findViewById(R.id.discovery_button)
        discoveryButton.setOnClickListener {
            var answer = "Discovery Kids"
            checkAnswer(answer)
            updateQuestion()
        }

        val disneyButton: Button = findViewById(R.id.disney_button)
        disneyButton.setOnClickListener {
            var answer = "Disney"
            checkAnswer(answer)
            updateQuestion()
        }

        val nickButton: Button = findViewById(R.id.nick_button)
        nickButton.setOnClickListener {
            var answer = "Nickelodeon"
            checkAnswer(answer)
            updateQuestion()
        }

        scoreText = findViewById(R.id.score_text)
        scoreText.setText("Score: $score")
    }

    fun fillQuestionList()
    {
        questions.add(Question("Dave the Barbarian", "Disney", 2004, 1,"Dave the Barbarian is a comedic cartoon series set in the medieval kingdom of Udrogoth. Dave, a powerful but cowardly barbarian, is left in charge of protecting the kingdom while his parents, the King and Queen, are away fighting evil. The problem is, Dave would much rather be cooking or knitting than battling ogres and sorcerers. He's joined by his two sisters: Candy, a fashion-obsessed princess who prefers shopping to ruling, and Fang, a rambunctious young warrior who loves smashing things. Together, this unconventional trio must defend Udrogoth from a hilarious array of threats, including a power-hungry dark lord, a talking squirrel, and a villain with an annoying laugh. Expect plenty of slapstick humor, witty dialogue, and absurd situations as Dave and his sisters try to keep their kingdom safe, all while juggling their own unique (and often clashing) personalities.", "dave"))
        questions.add(Question("Invader Zim", "Nickelodeon", 2001, 2,"Zim, an incompetent yet overzealous Irken invader, is determined to conquer Earth and prove his worth to his leaders, the Almighty Tallest. However, there's one tiny problem: they sent him to Earth as a joke, hoping to get rid of him after he nearly destroyed their home planet during Operation Impending Doom I. Unbeknownst to Zim, his mission is a sham, and his robot companion GIR is a malfunctioning mess with an obsession for snacks and Earthly oddities. Despite his ineptitude and terrible disguises, Zim attends a human school, where he constantly hatches ridiculous plans for world domination, ranging from unleashing monstrous robot bees to shrinking Earth's population with his \"Piggy suit.\" His plans are always thwarted by Dib, a paranormal-obsessed, perpetually-screaming boy who sees through Zim's disguise and is determined to expose him as an alien. Unfortunately, Dib's warnings are dismissed as crazy ramblings, leaving him to fight Zim alone", "zim"))
        questions.add(Question("Operation Junkyard", "Discovery Kids", 2002, 1,"Operation Junkyard was a thrilling TV show where teams of ingenious teens transformed piles of junk into awesome machines!  Each episode presented a unique challenge, like building a water bailing machine or a mud-slinging scooter.\n" + "\n" + "To gain an edge, teams competed in mini-challenges to earn \"bodgits\" – special parts or extra time with the on-set engineer.  With only six hours and two junkyard-filled buses, these young inventors had to brainstorm, build, and test their creations before facing off against the opposing team.\n" + "\n" + "Sparks flew, gears turned, and creativity reigned supreme in this exciting competition that proved one person's trash is another's treasure!", "junk"))
        questions.add(Question("Time Squad", "Cartoon Network", 2001, 2,"In the year 100,000,000 AD, history is a mess!  That's where Time Squad comes in. This team of misfits – the dimwitted but lovable Buck Tuddrussel, the brilliant 8-year-old Otto Osworth, and the sarcastic robot Larry 3000 –  travel through time fixing historical blunders.\n" + "\n" + "Whether it's convincing Eli Whitney to invent the cotton gin instead of a singing robot, or preventing Benedict Arnold from selling the recipe for  chocolate chip cookies to the British, Time Squad faces hilarious challenges and encounters with famous figures.  Expect wacky adventures, absurd situations, and a whole lot of historical inaccuracies getting hilariously corrected!", "timesquad"))
        questions.add(Question("Grim and Evil", "Cartoon Network", 2001, 2,"Grim & Evil was a darkly comedic animated anthology series that aired on Cartoon Network. Grim & Evil was known for its offbeat humor, macabre themes, and quirky characters. The show eventually split into two separate series, \"The Grim Adventures of Billy & Mandy\" and \"Evil Con Carne,\" allowing each to develop its own storylines and unique brand of humor.", "grim"))
    }

    fun checkAnswer(paramString: String) {
        val i: String
        Log.i("QuizActivity", "Index at checkAnswer - $index")
        if (paramString == /*questCont.*/questions.get(index).network) {
            i = "You are correct"
            score++
        } else {
            i = "You are incorrect"
        }
        Toast.makeText(this, i, Toast.LENGTH_SHORT).show()
        scoreText.setText("Score:" + " " + score)
        val intent = Intent(this, InfoActivity::class.java)
        intent.putExtra("title", questions.get(index).title)
        intent.putExtra("network",questions.get(index).network)
        intent.putExtra("yearAired", questions.get(index).yearAired)
        intent.putExtra("seasonNum", questions.get(index).seasonNum)
        intent.putExtra("synopsis", questions.get(index).synopsis)
        intent.putExtra("vidName", questions.get(index).vidName)
        startActivity(intent)
        Log.i("QuizActivity", "Index at end of checkAnswer - " + index)
    }

    private fun updateQuestion() {
        Log.i("QuizActivity", "Index at UpdateQuestion - " + index)
        index++
        questionText.setText(/*questCont.*/questions.get(index).title)
        Log.i("QuizActivity", "Index at end of updateQuestion - " + index)
    }

    override fun onDestroy() {
        musicPlayer.stop()
        musicPlayer.release()
        super.onDestroy()
    }

    override fun onPause() {
        musicPlayer.pause()
        pos = musicPlayer.getCurrentPosition()
        super.onPause()
    }

    override fun onResume() {
        musicPlayer.seekTo(this.pos)
        musicPlayer.start()
        ct.start()
        super.onResume()
    }

    override fun onSaveInstanceState(paramBundle: Bundle) {
        super.onSaveInstanceState(paramBundle)
        Log.i("QuizActivity", "onSaveInstanceState")
        paramBundle.putInt("index", index)
        paramBundle.putInt("score", score)
    }
}