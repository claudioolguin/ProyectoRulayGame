package Ui

import Repository.DBHelper
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectorulaygame.R

class Activity_la_mesa_pide : AppCompatActivity() {

    private lateinit var textViewChallenge: TextView
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_la_mesa_pide_layout)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_la_mesa_pide)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        textViewChallenge = findViewById(R.id.TituloInterfazinicio) // Asegúrate de que este ID sea correcto en tu layout
        db = DBHelper(this)

        showRandomChallengeForLaMesaPide()

        val nextChallengeButton = findViewById<Button>(R.id.button_next_challenge) // Asegúrate de que este ID sea correcto
        nextChallengeButton.setOnClickListener {
            showRandomChallengeForLaMesaPide()
        }

        val backButton = findViewById<Button>(R.id.button_back) // Asegúrate de que este ID sea correcto
        backButton.setOnClickListener {
            finish()
        }
    }

    /**
     * Obtiene los retos de "La mesa pide" y muestra uno al azar.
     */
    private fun showRandomChallengeForLaMesaPide() {
        val challenges = db.getChallengesByMode("La mesa pide")

        if (challenges.isNotEmpty()) {
            val randomChallenge = challenges.random()
            textViewChallenge.text = randomChallenge.text
        } else {
            textViewChallenge.text = "No hay retos de '''La mesa pide''' disponibles"
        }
    }
}
