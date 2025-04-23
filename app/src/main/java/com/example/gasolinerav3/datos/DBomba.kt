package com.example.gasolinerav3.datos

import android.content.Context

data class Bomba(
    val id: Int = 0,
    val estacionNombre: String,
    val tipoNombre: String,
    val cantidad: Int
)

class DBomba(context: Context) {
    private val dbHelper = BaseDatosHelper(context)

    fun listar(): List<Bomba> {
        val lista = mutableListOf<Bomba>()
        val db = dbHelper.readableDatabase
        val query = """
            SELECT B.id, E.nombre AS estacionNombre, T.nombre AS tipoNombre, B.cantidad
            FROM Bomba B
            INNER JOIN Estacion E ON B.estacionId = E.id
            INNER JOIN TipoCombustible T ON B.tipoId = T.id
            ORDER BY B.id
        """.trimIndent()

        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val estacionNombre = cursor.getString(1)
                val tipoNombre = cursor.getString(2)
                val cantidad = cursor.getInt(3)
                lista.add(Bomba(id, estacionNombre, tipoNombre, cantidad))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }


}
