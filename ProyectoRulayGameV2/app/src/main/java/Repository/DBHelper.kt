package Repository
import Models.Challenge
import android.content.ContentValues
import android.content.Context;
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper;

//-----------------------------------------------------------------------------------------------------
// Constantes de la base de datos
private const val DATABASE_NAME = "tipos_de_juegos"
private const val DATABASE_VERSION = 6 // Versión incrementada para forzar la actualización de datos cada vez que actualizemos la DBHelper se incrementa en 1
private const val TABLE_CHALLENGES = "retos"
private const val COL_ID = "id"
private const val COL_TYPE = "tipo"
private const val COL_MODE = "modo" // Columna para el modo de juego
private const val COL_TEXT = "texto"
//-----------------------------------------------------------------------------------------------------
// Lista de retos iniciales con su modo asociado
private val INITIAL_CHALLENGES = listOf(
    // Modo Normal
    Challenge(1, "Verdad", "Normal", "¿Cuál es el error más embarazoso que has cometido "),
    Challenge(2, "Reto", "Normal", "Envía el último meme que guardaste a la persona equivocada."),
    Challenge(3, "Beber", "Normal", "Si usas lentes, toma 3 tragos."),
    Challenge(4, "Reto", "Normal", "Baila la Macarena o bebe 2 tragos."),
    Challenge(5, "Verdad", "Normal", "¿Cuál es la última mentira que has dicho?"),
    Challenge(14, "Beber", "Normal", "Si tienes más de 3 notificaciones sin leer, toma 4 tragos."),
    Challenge(15, "Reto", "Normal", "Imita a un animal que el grupo elija por 1 minuto."),
    Challenge(16, "Verdad", "Normal", "¿Cuál es tu placer culposo más vergonzoso?"),
    Challenge(17, "Beber", "Normal", "Si eres el más joven del grupo, bebe 2 tragos."),
    Challenge(18, "Reto", "Normal", "Deja que alguien te dibuje un bigote con un marcador que se pueda borrar."),
    Challenge(19, "Verdad", "Normal", "¿Alguna vez has fingido estar enfermo para no ir a algún sitio?"),

    // Modo La mesa pide
    Challenge(6,"Reto", "La mesa pide", "La mesa pide que dejen en la mesa monedas de cualquier tipo si no bebe"),
    Challenge(7, "Verdad", "La mesa pide", "La mesa pide que dejen el carnet en la mesa (el que no tegna bebe)"),
    Challenge(8, "Beber", "La mesa pide", "La mesa pide el que tenga menos porcentaje de bateria pierde, bebe 3 tragos."),
    Challenge(9, "Beber", "La mesa pide", "La mesa pide que La persona más alta del grupo eliga a alguien para que beba o reto."),
    Challenge(20, "Beber", "La mesa pide", "La mesa pide que el que tenga el nombre más largo bebe."),
    Challenge(21, "Reto", "La mesa pide", "La mesa pide que Todos deben decir un trabalenguas. El que se equivoque, bebe."),
    Challenge(22, "Beber", "La mesa pide", "La mesa pide que el que se haya despertado más temprano hoy, elige quien bebe 1 trago. con baucher"),
    Challenge(23, "Reto", "La mesa pide", "La mesa pide La persona a tu derecha te hace una pregunta. Si no respondes, bebes."),
    Challenge(24, "Beber", "La mesa pide", "La mesa pide que si alguien ha viajado a otro continente, elige quien bebe."),

    // Modo Cultura Chupística
    Challenge(10, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar presidentes de cualquier país  o bebes."),
    Challenge(11, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar marcas de autos de cualquier marca o bebe."),
    Challenge(12, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar peliculas de terror o bebe."),
    Challenge(13, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar artistas musicales de chile o bebe"),
    Challenge(25, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar marcas de zapatillas o bebe "),
    Challenge(26, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar canciones carro o bebe"),
    Challenge(27, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar drogas o bebe"),
    Challenge(28, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar paises o bebes"),
    Challenge(29, "Cultura", "Cultura Chupística", "La cultura chupistica pide nombrar dioses o personajes reconocidos de distintas mitologias mitologias o bebe")
)
//-----------------------------------------------------------------------------------------------------

class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    // Crea la tabla y las columnas
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_CHALLENGES (" +
                "$COL_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "$COL_TYPE TEXT," +
                "$COL_MODE TEXT," + // Columna 'modo' agregada
                "$COL_TEXT TEXT)"

        db.execSQL(createTable)
        insertInitialChallenges(db)
    }

//--------------------------------------------------------------------------------
    // Recrea la base de datos si la versión cambia
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CHALLENGES")
        onCreate(db)
    }
//--------------------------------------------------------------------------------
    //usamos : import android.content.ContentValues
    // Inserta los retos iniciales
    private fun insertInitialChallenges(db: SQLiteDatabase) {
        INITIAL_CHALLENGES.forEach { challenge ->
            val values = ContentValues().apply {
                put(COL_TYPE, challenge.type)
                put(COL_MODE, challenge.mode) // Valor del modo agregado
                put(COL_TEXT, challenge.text)
            }
            db.insert(TABLE_CHALLENGES, null, values)
        }
    }
//--------------------------------------------------------------------------------
    // Método para obtener todos los retos de la BD (con fines de demostración)
    fun getAllChallenges(): List<Challenge> {
        return getChallengesByMode(null)
    }

    // Método para obtener retos FILTRADOS por MODO. Si mode es null, trae todos.
    fun getChallengesByMode(mode: String?): List<Challenge> {
        val challenges = mutableListOf<Challenge>()
        val db = this.readableDatabase
        val selectionArgs: Array<String>?
        val selectQuery: String
//--------------------------------------------------------------------------------
        if (mode != null) {
            // Filtrar por modo específico
            selectQuery = "SELECT * FROM $TABLE_CHALLENGES WHERE $COL_MODE = ?"
            selectionArgs = arrayOf(mode)
        } else {
            // No filtrar (traer todos)
            selectQuery = "SELECT * FROM $TABLE_CHALLENGES"
            selectionArgs = null
        }
//--------------------------------------------------------------------------------
        val cursor = db.rawQuery(selectQuery, selectionArgs)

        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    try {
                        val idIndex = it.getColumnIndex(COL_ID)
                        val typeIndex = it.getColumnIndex(COL_TYPE)
                        val modeIndex = it.getColumnIndex(COL_MODE)
                        val textIndex = it.getColumnIndex(COL_TEXT)

                        if (idIndex != -1 && typeIndex != -1 && modeIndex != -1 && textIndex != -1) {
                            val id = it.getInt(idIndex)
                            val type = it.getString(typeIndex)
                            val modeValue = it.getString(modeIndex)
                            val text = it.getString(textIndex)
                            challenges.add(Challenge(id, type, modeValue, text))
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                } while (it.moveToNext())
            }
        }
        return challenges
    }
}//fin logica




//esta clase me permitira concectarme ala base de datos
//nos permitira crear la base de datos
