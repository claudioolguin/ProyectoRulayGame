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

class Activity_Reto_Bebe : AppCompatActivity() {


    private lateinit var textViewChallenge: TextView // Declara una variable para el TextView que mostrará el reto. '''lateinit''' significa que se inicializará más tarde.
    private lateinit var db: DBHelper // Declara una variable para el DBHelper, que manejará la interacción con la base de datos.



    override fun onCreate(savedInstanceState: Bundle?) {  // Este método se llama cuando la actividad se crea por primera vez.
        super.onCreate(savedInstanceState) // Llama al método onCreate de la clase padre (AppCompatActivity).
        enableEdgeToEdge() // Habilita el modo de pantalla completa de borde a borde para una apariencia más inmersiva.

        setContentView(R.layout.activity_reto_o_bebe_layout) // Establece el layout (diseño) de la actividad desde el archivo XML.


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main2)) { v, insets ->    // Configura un listener para manejar los "insets" de la ventana (áreas ocupadas por las barras del sistema).
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars()) // Obtiene las dimensiones de las barras del sistema (barra de estado, barra de navegación).
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom) // Aplica un padding a la vista principal para que el contenido no quede oculto detrás de las barras del sistema.
            insets  // Devuelve los insets para que el sistema continúe procesándolos.
        }


        textViewChallenge = findViewById(R.id.TituloInterfazinicio)// Inicializa textViewChallenge encontrando el TextView en el layout por su ID.
        db = DBHelper(this) // Inicializa el DBHelper, pasándole el contexto actual (esta actividad).

        showRandomChallenge() // Llama a la función para mostrar un reto aleatorio tan pronto como se crea la actividad.


        val nextChallengeButton = findViewById<Button>(R.id.button_next_challenge) // Busca el botón para el siguiente reto por su ID y lo guarda en una variable.
        nextChallengeButton.setOnClickListener { // Establece un listener de clic para el botón. El código dentro de las llaves se ejecutará cuando se haga clic.
            showRandomChallenge()  // Llama a la función para mostrar un nuevo reto aleatorio.
        }
        val backButton = findViewById<Button>(R.id.button_back) // Busca el botón para volver atrás por su ID.
        backButton.setOnClickListener {   // Establece un listener de clic para el botón de volver.
            finish() // Cierra la actividad actual y regresa a la pantalla anterior.
        }
    }


    private fun showRandomChallenge() {  // Función privada para obtener y mostrar un reto aleatorio de la base de datos.
        val challenges = db.getChallengesByMode("Normal") // Obtiene una lista de retos filtrados por el modo "Normal".

        if (challenges.isNotEmpty()) {// Comprueba si la lista de retos no está vacía.
            val randomChallenge = challenges.random()   // Si hay retos, selecciona uno al azar de la lista.
            textViewChallenge.text = randomChallenge.text   // Establece el texto del TextView con el texto del reto seleccionado.
        } else {
            textViewChallenge.text = "No hay retos de modo 'Normal' disponibles"   // Si no hay retos en la base de datos, muestra un mensaje indicándolo.
        }
    }

}
