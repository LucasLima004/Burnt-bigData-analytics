package com.github.metro.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.databinding.RvPesquisaLocalizacaoBinding
import com.github.metro.models.LocalPesquisa

class LocalPesquisaRVAdapter (val locaisPesquisa: List<LocalPesquisa>): RecyclerView.Adapter<LocalPesquisaRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvPesquisaLocalizacaoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPesquisaLocalizacaoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (locaisPesquisa[position]) {
                binding.tvNomeLocal.text = this.nome
                binding.tvEnderecoLocal.text = this.endereco
            }
        }
    }

    override fun getItemCount(): Int {
        return locaisPesquisa.size
    }
}