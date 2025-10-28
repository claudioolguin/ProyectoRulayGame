package Ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectorulaygame.R

class InterfazInicioActivty : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.interfazinicio_layout)

        // Asegúrate de que el ID en tu XML (R.id.button) coincida.
        val button = findViewById<Button>(R.id.Iniciar)
        button.setOnClickListener {
        // Reemplaza HomeActivity::class.java si tu Activity tiene otro nombre.
            val intent = Intent(this, activity_Home::class.java)
            startActivity(intent)
        }
    }
}