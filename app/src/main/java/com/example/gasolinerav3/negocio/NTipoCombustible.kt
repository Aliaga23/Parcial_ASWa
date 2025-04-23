package com.example.gasolinerav3.negocio

import android.content.Context
import com.example.gasolinerav3.datos.DTipoCombustible
import com.example.gasolinerav3.datos.TipoCombustible

class NTipoCombustible(context: Context) {
    private val dTipo = DTipoCombustible(context)

    fun listar(): List<TipoCombustible> = dTipo.listar()
}
