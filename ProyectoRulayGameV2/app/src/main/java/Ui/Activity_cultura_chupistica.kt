package Ui

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectorulaygame.R
import Repository.DBHelper

class Activity_cultura_chupistica : AppCompatActivity() {

    private lateinit var db: DBHelper
    private lateinit var textViewChallenge: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cultura_chupistica_layout)

        db = DBHelper(this)
        textViewChallenge = findViewById(R.id.textView_challenge_cultura)

        val buttonSortear: Button = findViewById(R.id.button_next_challenge_cultura)
        buttonSortear.setOnClickListener {
            showRandomChallengeForCulturaChupistica()
        }

        val buttonVolver: Button = findViewById(R.id.button_back_cultura)
        buttonVolver.setOnClickListener {
            finish()
        }

        // Muestra un reto inicial al cargar la actividad
        showRandomChallengeForCulturaChupistica()
    }

    private fun showRandomChallengeForCulturaChupistica() {
        val challenges = db.getChallengesByMode("Cultura Chupística")
        if (challenges.isNotEmpty()) {
            val randomChallenge = challenges.random()
            textViewChallenge.text = randomChallenge.text
        } else {
            textViewChallenge.text = "No hay retos de '''Cultura Chupística''' disponibles."
        }
    }
}
