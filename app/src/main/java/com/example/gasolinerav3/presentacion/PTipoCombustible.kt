package com.example.gasolinerav3.presentacion

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gasolinerav3.R
import com.example.gasolinerav3.negocio.NTipoCombustible

class PTipoCombustible : Fragment() {

    private lateinit var nTipo: NTipoCombustible
    private lateinit var listView: ListView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val vista = inflater.inflate(R.layout.fragment_tipo_combustible, container, false)

        nTipo = NTipoCombustible(requireContext())
        listView = vista.findViewById(R.id.listViewTipos)

        cargarDatos()

        return vista
    }

    private fun cargarDatos() {
        val listaNombres = nTipo.listar().map { it.nombre }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, listaNombres)
        listView.adapter = adapter
    }
}
