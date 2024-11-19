package com.github.metro.recyclerViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.RotaPesquisaActivity
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RvPesquisaLocalizacaoBinding
import com.github.metro.models.LocalPesquisa

class LocalPesquisaRVAdapter (val context:Context, val locaisPesquisa: List<LocalPesquisa>, val localOrigemPesquisa: LocalPesquisa?): RecyclerView.Adapter<LocalPesquisaRVAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: RvPesquisaLocalizacaoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvPesquisaLocalizacaoBinding.inflate(LayoutInflater.from(parent.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (locaisPesquisa[position]) {
                binding.clLocalPesquisa.setOnClickListener {
                    val intent = Intent(context, RotaPesquisaActivity::class.java)
                    intent.putExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, locaisPesquisa[position])
                    intent.putExtra(ConstantesExtra.LOCAL_ORIGEM_PESQUISA_EXTRA, localOrigemPesquisa)
                    context.startActivity(intent)
                }
                binding.tvNomeLocal.text = this.nome
                binding.tvEnderecoLocal.text = this.endereco
            }
        }
    }

    override fun getItemCount(): Int {
        return locaisPesquisa.size
    }
}