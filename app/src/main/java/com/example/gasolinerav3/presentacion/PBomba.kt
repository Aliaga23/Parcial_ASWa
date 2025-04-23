package com.example.gasolinerav3.presentacion

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gasolinerav3.R
import com.example.gasolinerav3.negocio.NBomba

class PBomba : Fragment() {

    private lateinit var nBomba: NBomba
    private lateinit var listView: ListView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val vista = inflater.inflate(R.layout.fragment_bomba, container, false)

        nBomba = NBomba(requireContext())
        listView = vista.findViewById(R.id.listViewBombas)

        cargarBombas()

        return vista
    }

    private fun cargarBombas() {
        val bombas = nBomba.listar()
        val datos = bombas.map {
            "Estación: ${it.estacionNombre}\nTipo: ${it.tipoNombre}\nCantidad: ${it.cantidad}"
        }

        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, datos)
        listView.adapter = adapter
    }
}
