package Ui

import Ui.Activity_Reto_Bebe
import Ui.Activity_cultura_chupistica
import Ui.Activity_la_mesa_pide
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectorulaygame.R

class activity_Home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_layout)

        // Botón para "Reto o Bebe"
        val botonRetoOBebe: Button = findViewById(R.id.button_reto_bebe)
        botonRetoOBebe.setOnClickListener {
            val intent = Intent(this, Activity_Reto_Bebe::class.java)
            startActivity(intent)
        }

        // Botón para "La mesa pide"
        val botonLaMesaPide: Button = findViewById(R.id.buttonlamesapide) // Usando el ID correcto del layout
        botonLaMesaPide.setOnClickListener {
            val intent = Intent(this, Activity_la_mesa_pide::class.java)
            startActivity(intent)
        }

        // Botón para "Cultura Chupistica"
        val botonCulturaChupistica: Button = findViewById(R.id.button3) // Usando el ID correcto del layout
        botonCulturaChupistica.setOnClickListener {
            val intent = Intent(this, Activity_cultura_chupistica::class.java)
            startActivity(intent)
        }
    }
}
