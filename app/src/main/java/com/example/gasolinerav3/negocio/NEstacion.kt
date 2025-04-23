package com.example.gasolinerav3.negocio

import android.content.Context
import com.example.gasolinerav3.datos.DEstacion
import com.example.gasolinerav3.datos.Estacion

class NEstacion(context: Context) {
    private val dEstacion = DEstacion(context)

    fun listar(): List<Estacion> = dEstacion.listar()
}
