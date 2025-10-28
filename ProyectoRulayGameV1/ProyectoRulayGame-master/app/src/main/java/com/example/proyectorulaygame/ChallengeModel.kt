package com.example.proyectorulaygame
// Clase de datos simple para representar un reto del juego.
// Usamos data class para obtener automáticamente métodos útiles como toString().
data class Challenge(
    val id: Int = 0,
    val type: String,  // Ejemplo: "Verdad", "Reto", "Beber"
    val mode: String, // Modo de juego (Ej: "Normal", "Hot", "Cultura Chupística")
    val text: String
)