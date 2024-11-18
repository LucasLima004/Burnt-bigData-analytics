package com.github.metro.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.databinding.RvLinhaLayoutBinding
import com.github.metro.databinding.RvTerminalLayoutBinding
import com.github.metro.models.LocalPesquisa

class EstacaoRvAdapter(val estacoes: ArrayList<LocalPesquisa>): RecyclerView.Adapter<EstacaoRvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvTerminalLayoutBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvTerminalLayoutBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return estacoes.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (estacoes[position]) {
                binding.tvNomeEstacao.text = this.nome
            }
        }
    }

}