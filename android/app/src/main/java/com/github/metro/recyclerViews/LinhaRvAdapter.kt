package com.github.metro.recyclerViews

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.RotaPesquisaActivity
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RvLinhaLayoutBinding
import com.github.metro.databinding.RvPesquisaLocalizacaoBinding
import com.github.metro.recyclerViews.LocalPesquisaRVAdapter.ViewHolder

//class LinhaRvAdapter(): RecyclerView.Adapter<LinhaRvAdapter.ViewHolder>() {
//    inner class ViewHolder(val binding: RvLinhaLayoutBinding): RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): com.github.metro.recyclerViews.LocalPesquisaRVAdapter.ViewHolder {
//        val binding = RvPesquisaLocalizacaoBinding.inflate(LayoutInflater.from(parent.context))
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: com.github.metro.recyclerViews.LocalPesquisaRVAdapter.ViewHolder, position: Int) {
//        with (holder) {
//            with (locaisPesquisa[position]) {
//                binding.clLocalPesquisa.setOnClickListener {
//                    val intent = Intent(context, com.github.metro.RotaPesquisaActivity::class.java)
//                    intent.putExtra(com.github.metro.constantes.ConstantesExtra.LOCAL_PESQUISA_EXTRA, locaisPesquisa[position])
//                    context.startActivity(intent)
//                }
//                binding.tvNomeLocal.text = this.nome
//                binding.tvEnderecoLocal.text = this.endereco
//            }
//        }
//    }
//
//    override fun getItemCount(): Int {
//        return locaisPesquisa.size
//    }
//}