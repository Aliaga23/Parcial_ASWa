package com.example.gasolinerav3.datos

import android.content.Context

data class TipoCombustible(val id: Int = 0, val nombre: String)

class DTipoCombustible(context: Context) {
    private val dbHelper = BaseDatosHelper(context)

    fun listar(): List<TipoCombustible> {
        val lista = mutableListOf<TipoCombustible>()
        val cursor = dbHelper.readableDatabase.rawQuery("SELECT * FROM TipoCombustible ORDER BY id", null)
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
