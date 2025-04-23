package com.example.gasolinerav3.datos

import android.content.Context

data class Estacion(
    val id: Int = 0,
    val nombre: String,
    val direccion: String,
    val latitud: Double,
    val longitud: Double
)

class DEstacion(context: Context) {
    private val dbHelper = BaseDatosHelper(context)

    fun listar(): List<Estacion> {
        val lista = mutableListOf<Estacion>()
        val db = dbHelper.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM Estacion ORDER BY id", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                val direccion = cursor.getString(cursor.getColumnIndexOrThrow("direccion"))
                val latitud = cursor.getDouble(cursor.getColumnIndexOrThrow("latitud"))
                val longitud = cursor.getDouble(cursor.getColumnIndexOrThrow("longitud"))
                lista.add(Estacion(id, nombre, direccion, latitud, longitud))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}
