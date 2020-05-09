package com.cardenas.jesus.proyectofinal.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import com.cardenas.jesus.proyectofinal.R
import com.cardenas.jesus.proyectofinal.model.CiudadWAQI
import kotlinx.android.synthetic.main.row_ciudad.view.*

class AdapterDatosCiudades(
    val datos : List<CiudadWAQI>,
    val listener: (CiudadWAQI) -> Unit
) : RecyclerView.Adapter<AdapterDatosCiudades.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val idEstacion = itemView.idEstacion
        val nombreEstacion = itemView.nombreEstacion
        val botonAgregar = itemView.btnAgregar
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_ciudad, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nombreEstacion.text = datos[position].nombre
        holder.idEstacion.text = datos[position].id.toString()
        holder.botonAgregar.setOnClickListener{
            listener(datos[position])
        }
    }
}