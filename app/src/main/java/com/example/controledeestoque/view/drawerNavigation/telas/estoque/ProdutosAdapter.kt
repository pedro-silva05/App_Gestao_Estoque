package com.example.controledeestoque.view.drawerNavigation.telas.estoque

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R

class ProdutosAdapter(
    private var produtoList: ArrayList<Produto>
): RecyclerView.Adapter<ProdutosAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.produto_item,
            parent,false)

        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return produtoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dados = produtoList[position]

        holder.descricao.text = dados.descricao
        holder.codBarras.text = dados.cod_barras
        holder.categoria.text = dados.categoria
        holder.valor.text = dados.valor.toString()
        holder.quantidade.text = dados.quantidade.toString()
    }

    fun searchData(searchList: ArrayList<Produto>){
        produtoList = searchList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val descricao : TextView = itemView.findViewById(R.id.descricao_item)
        val codBarras : TextView = itemView.findViewById(R.id.codbarras_item)
        val categoria : TextView = itemView.findViewById(R.id.categoria_item)
        val valor : TextView = itemView.findViewById(R.id.valor_item)
        val quantidade : TextView = itemView.findViewById(R.id.quantidade_item)
    }

}