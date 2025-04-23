package com.example.gasolinerav3.datos

import android.content.ContentValues
import android.content.Context

data class Fila(
    val id: Int = 0,
    val estacionId: Int,
    val tipoId: Int,
    val tiempoEstimado: Int,
    val alcanzaCombustible: Boolean,
    val fecha: String = ""
)

class DFila(context: Context) {
    private val dbHelper = BaseDatosHelper(context)

    fun insertar(fila: Fila): Boolean {
        val valores = ContentValues().apply {
            put("estacionId", fila.estacionId)
            put("tipoId", fila.tipoId)
            put("tiempoEstimado", fila.tiempoEstimado)
            put("alcanzaCombustible", if (fila.alcanzaCombustible) 1 else 0)
        }
        return dbHelper.writableDatabase.insert("Fila", null, valores) > 0
    }

    fun listar(): List<Fila> {
        val lista = mutableListOf<Fila>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM Fila ORDER BY id DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(0)
                val estacionId = cursor.getInt(1)
                val tipoId = cursor.getInt(2)
                val tiempo = cursor.getInt(3)
                val alcanza = cursor.getInt(4) == 1
                val fecha = cursor.getString(5)
                lista.add(Fila(id, estacionId, tipoId, tiempo, alcanza, fecha))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }

    fun obtenerCantidadBombas(estacionId: Int, tipoId: Int): Int {
        val cursor = dbHelper.readableDatabase.rawQuery(
            "SELECT cantidad FROM Bomba WHERE estacionId = ? AND tipoId = ?",
            arrayOf(estacionId.toString(), tipoId.toString())
        )
        var total = 0
        if (cursor.moveToFirst()) {
            do {
                total += cursor.getInt(0)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return total
    }

    fun obtenerStockDisponible(estacionId: Int, tipoId: Int): Double? {
        val cursor = dbHelper.readableDatabase.rawQuery(
            "SELECT litrosDisponibles FROM StockCombustible WHERE estacionId = ? AND tipoId = ?",
            arrayOf(estacionId.toString(), tipoId.toString())
        )
        val stock = if (cursor.moveToFirst()) cursor.getDouble(0) else null
        cursor.close()
        return stock
    }
    fun estacionesConStockPorTipo(tipoId: Int): List<Estacion> {
        val lista = mutableListOf<Estacion>()
        val db = dbHelper.readableDatabase
        val query = """
        SELECT E.id, E.nombre, E.direccion, E.latitud, E.longitud
        FROM Estacion E, StockCombustible S
        WHERE E.id = S.estacionId AND S.tipoId = ?
        GROUP BY E.id
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(tipoId.toString()))
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
