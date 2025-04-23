package com.example.gasolinerav3.negocio

import android.content.Context
import com.example.gasolinerav3.datos.Bomba
import com.example.gasolinerav3.datos.DBomba
import com.example.gasolinerav3.datos.TipoCombustible

class NBomba(context: Context) {
    private val dBomba = DBomba(context)

    fun listar(): List<Bomba> = dBomba.listar()



}
