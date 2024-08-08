package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R
import com.example.controledeestoque.view.drawerNavigation.telas.estoque.Produto

class ListaProdutosAdapter(
    private var produtoList: ArrayList<Produto>
) : RecyclerView.Adapter<ListaProdutosAdapter.MyViewHolder>() {

    private var iListener: onItemClickListener? = null

    interface onItemClickListener {
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        iListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.produto_item, parent, false)
        return MyViewHolder(itemView, iListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val produto = produtoList[position]
        holder.bind(produto)
    }

    override fun getItemCount(): Int {
        return produtoList.size
    }

    fun searchData(searchList: ArrayList<Produto>) {
        produtoList = searchList
        notifyDataSetChanged()
    }

    class MyViewHolder(itemView: View, listener: onItemClickListener?) : RecyclerView.ViewHolder(itemView) {
        private val descricao: TextView = itemView.findViewById(R.id.descricao_item)
        private val codBarras: TextView = itemView.findViewById(R.id.codbarras_item)
        private val categoria: TextView = itemView.findViewById(R.id.categoria_item)
        private val valor: TextView = itemView.findViewById(R.id.valor_item)
        private val quantidade: TextView = itemView.findViewById(R.id.quantidade_item)

        init {
            itemView.setOnClickListener {
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bind(produto: Produto) {
            descricao.text = produto.descricao
            codBarras.text = produto.cod_barras
            categoria.text = produto.categoria
            valor.text = produto.valor.toString()
            quantidade.text = produto.quantidade.toString()
        }
    }
}
