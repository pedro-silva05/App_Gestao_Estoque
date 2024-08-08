package com.example.controledeestoque.view.drawerNavigation.telas.realizarVenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.controledeestoque.R

class CarrinhoAdapter(private var carrinhoList: List<EntidadeCarrinho>) : RecyclerView.Adapter<CarrinhoAdapter.CarrinhoViewHolder>() {

    class CarrinhoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val descricao: TextView = itemView.findViewById(R.id.descricao_item)
        private val codBarras: TextView = itemView.findViewById(R.id.codbarras_item)
        private val valor: TextView = itemView.findViewById(R.id.valor_item)
        private val quantidade: TextView = itemView.findViewById(R.id.quantidade_item)

        fun bind(item: EntidadeCarrinho) {
            descricao.text = item.descricao
            codBarras.text = item.cod_barras
            valor.text = item.preco.toString()
            quantidade.text = item.quantidade.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarrinhoViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.produto_carrinho, parent, false)
        return CarrinhoViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarrinhoViewHolder, position: Int) {
        val item = carrinhoList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return carrinhoList.size
    }

    // Atualiza a lista de itens do carrinho e notifica o RecyclerView
    fun setCarrinhoList(newList: List<EntidadeCarrinho>) {
        carrinhoList = newList
        notifyDataSetChanged()
    }
}
