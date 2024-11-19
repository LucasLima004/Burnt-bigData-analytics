package com.github.metro.recyclerViews

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.databinding.RvPesquisaLocalizacaoBinding
import com.github.metro.databinding.RvRotaPesquisaBinding
import com.github.metro.models.Rota

class LinhaRvAdapter(val rotas: ArrayList<Rota>): RecyclerView.Adapter<LinhaRvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvRotaPesquisaBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LinhaRvAdapter.ViewHolder  {
        val binding = RvRotaPesquisaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (rotas[position]) {
                binding.tvNumeroLinhas.text = "Rota ${position+1}"
                binding.tvLinha.text = this.linhas.joinToString {
                    linha ->
                    return@joinToString "\n" + linha.nome + " (" + linha.codigo + ")"
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return rotas.size
    }
}