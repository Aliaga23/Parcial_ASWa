package com.example.gasolinerav3.negocio

import android.content.Context
import com.example.gasolinerav3.datos.DFila
import com.example.gasolinerav3.datos.Estacion
import com.example.gasolinerav3.datos.Fila
import com.google.android.gms.maps.model.LatLng

class NFila(context: Context) {
    private val dFila = DFila(context)

    fun calcularEstimacion(
        longitudMetros: Double,
        stockDisponible: Double,
        estacionId: Int,
        tipoId: Int,
        litrosPorAuto: Double = 30.0,
        largoAuto: Double = 5.0,
        minutosPorAuto: Int = 3
    ): Map<String, Any> {
        val autosEstimados = (longitudMetros / largoAuto).toInt()
        val litrosNecesarios = autosEstimados * litrosPorAuto
        val alcanza = litrosNecesarios <= stockDisponible
        val cantidadBombas = dFila.obtenerCantidadBombas(estacionId, tipoId).coerceAtLeast(1)
        val tiempoEstimado = (autosEstimados * minutosPorAuto) / cantidadBombas

        dFila.insertar(Fila(estacionId = estacionId, tipoId = tipoId, tiempoEstimado = tiempoEstimado, alcanzaCombustible = alcanza))

        return mapOf(
            "autos" to autosEstimados,
            "litrosNecesarios" to litrosNecesarios,
            "alcanza" to alcanza,
            "tiempoEstimado" to tiempoEstimado
        )
    }

    fun listar(): List<Fila> = dFila.listar()

    fun obtenerStockDisponible(estacionId: Int, tipoId: Int): Double? {
        return dFila.obtenerStockDisponible(estacionId, tipoId)
    }

    fun calcularLongitudEnMetros(puntos: List<LatLng>): Double {
        var distancia = 0.0
        for (i in 1 until puntos.size) {
            val res = FloatArray(1)
            val p1 = puntos[i - 1]
            val p2 = puntos[i]
            android.location.Location.distanceBetween(
                p1.latitude, p1.longitude,
                p2.latitude, p2.longitude,
                res
            )
            distancia += res[0]
        }
        return distancia
    }
    fun estacionesConStockPorTipo(tipoId: Int): List<Estacion> {
        return dFila.estacionesConStockPorTipo(tipoId)
    }

}
