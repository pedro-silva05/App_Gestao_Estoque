package com.example.controledeestoque.view.drawerNavigation.telas.fiado

import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R

class AdapterFiado(private var fiadosList: ArrayList<Fiados>) : RecyclerView.Adapter<AdapterFiado.MyViewHolder>() {

    private lateinit var iListener : onItemClickListener

    interface onItemClickListener{

        fun onItemClick(position: Int)

    }

    fun setOnItemClickListener(listener: onItemClickListener){
        iListener = listener
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.fiado_item, parent, false)
        return MyViewHolder(itemView, iListener)
    }

    override fun getItemCount(): Int {
        return fiadosList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val dados = fiadosList[position]

        holder.nome.text = dados.nome
        holder.limite_divida.text = dados.limiteD.toString()
        holder.atual_divida.text = dados.atualD.toString()
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView){
        val nome : TextView = itemView.findViewById(R.id.nome_item)
        val limite_divida : TextView = itemView.findViewById(R.id.limite_divida_item)
        val atual_divida : TextView = itemView.findViewById(R.id.atual_divida_item)

        init {
            itemView.setOnClickListener{
                listener.onItemClick(adapterPosition)
            }
        }
    }
}