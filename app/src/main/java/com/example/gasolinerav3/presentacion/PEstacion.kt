package com.example.gasolinerav3.presentacion

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gasolinerav3.R
import com.example.gasolinerav3.negocio.NEstacion

class PEstacion : Fragment() {

    private lateinit var nEstacion: NEstacion
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val vista = inflater.inflate(R.layout.fragment_estacion, container, false)

        nEstacion = NEstacion(requireContext())
        listView = vista.findViewById(R.id.listViewEstaciones)

        cargarEstaciones()

        return vista
    }

    private fun cargarEstaciones() {
        val estaciones = nEstacion.listar()
        val nombres = estaciones.map {
            "${it.nombre} - ${it.direccion}\nLat: ${it.latitud}, Lng: ${it.longitud}"
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, nombres)
        listView.adapter = adapter
    }
}
