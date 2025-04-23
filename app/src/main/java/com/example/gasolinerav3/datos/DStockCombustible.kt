package com.example.gasolinerav3.datos

import android.content.ContentValues
import android.content.Context

data class StockCombustible(
    val id: Int = 0,
    val estacionId: Int,
    val tipoId: Int,
    val litrosDisponibles: Double,
    val fecha: String = "" // opcional para visualización
)

class DStockCombustible(context: Context) {
    private val dbHelper = BaseDatosHelper(context)

    fun insertar(stock: StockCombustible): Boolean {
        val valores = ContentValues().apply {
            put("estacionId", stock.estacionId)
            put("tipoId", stock.tipoId)
            put("litrosDisponibles", stock.litrosDisponibles)
        }
        return dbHelper.writableDatabase.insert("StockCombustible", null, valores) > 0
    }

    fun actualizar(stock: StockCombustible): Boolean {
        val valores = ContentValues().apply {
            put("estacionId", stock.estacionId)
            put("tipoId", stock.tipoId)
            put("litrosDisponibles", stock.litrosDisponibles)
        }
        return dbHelper.writableDatabase.update(
            "StockCombustible", valores, "id=?", arrayOf(stock.id.toString())
        ) > 0
    }

    fun eliminar(id: Int): Boolean {
        return dbHelper.writableDatabase.delete("StockCombustible", "id=?", arrayOf(id.toString())) > 0
    }

    fun listar(): List<StockCombustible> {
        val lista = mutableListOf<StockCombustible>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM StockCombustible ORDER BY id DESC", null)
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val estacionId = cursor.getInt(cursor.getColumnIndexOrThrow("estacionId"))
                val tipoId = cursor.getInt(cursor.getColumnIndexOrThrow("tipoId"))
                val litros = cursor.getDouble(cursor.getColumnIndexOrThrow("litrosDisponibles"))
                val fecha = cursor.getString(cursor.getColumnIndexOrThrow("fechaActualizacion"))
                lista.add(StockCombustible(id, estacionId, tipoId, litros, fecha))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
    fun obtenerTiposPorEstacion(estacionId: Int): List<TipoCombustible> {
        val lista = mutableListOf<TipoCombustible>()
        val db = dbHelper.readableDatabase
        val query = """
        SELECT T.id, T.nombre
        FROM Bomba B
        INNER JOIN TipoCombustible T ON B.tipoId = T.id
        WHERE B.estacionId = ?
        GROUP BY T.id
    """.trimIndent()

        val cursor = db.rawQuery(query, arrayOf(estacionId.toString()))
        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                lista.add(TipoCombustible(id, nombre))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }
}
