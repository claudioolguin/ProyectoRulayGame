package models
import android.content.ContentValues
import com.example.proyectorulaygame.Challenge // importamos la clase challenge desde challengeModel
import android.content.Context;
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper;

//-----------------------------------------------------------------------------------------------------
// Constantes de la base de datos
private const val DATABASE_NAME = "tipos_de_juegos"
private const val DATABASE_VERSION = 2 // ¡IMPORTANTE! Se incrementa la versión para recrear la BD con la nueva columna
private const val TABLE_CHALLENGES = "retos"
private const val COL_ID = "id"
private const val COL_TYPE = "tipo"
private const val COL_MODE = "modo" // Columna para el modo de juego
private const val COL_TEXT = "texto"
//-----------------------------------------------------------------------------------------------------
// Lista de retos iniciales con su modo asociado
private val INITIAL_CHALLENGES = listOf(
    // Modo NORMAL
    Challenge(1, "Verdad", "Normal", "¿Cuál es el error más embarazoso que has cometido "),
    Challenge(2, "Reto", "Normal", "Envía el último meme que guardaste a la persona equivocada."),
    Challenge(3, "Beber", "Normal", "Si usas lentes, toma 3 tragos."),
    // Modo HOT
    Challenge(4, "Reto", "Hot", "Manda un audio diciendo 'Me encanta bailar la lambada' a la primera persona en tu lista de contactos."),
    Challenge(5, "Verdad", "Hot", "¿Quién en el grupo te parece el más atractivo"),
    // Modo CULTURA CHUPÍSTICA
    Challenge(6, "Cultura", "Cultura Chupística", "Nombra 3 presidentes de cualquier país en 10 segundos o bebes."),
    Challenge(7, "Cultura", "Cultura Chupística", "Dile al grupo una capital que empiece con la letra 'B'."),
    Challenge(8, "Beber", "Normal", "Si tienes más de 3 notificaciones sin leer, toma 4 tragos.")
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









