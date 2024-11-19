package com.github.metro.recyclerViews

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.EstacaoActivity
import com.github.metro.RotaPesquisaActivity
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RvFavoritosBinding
import com.github.metro.databinding.RvTerminalLayoutBinding
import com.github.metro.models.FavoriteLocal
import com.github.metro.utils.converters.FavoriteLocaleConvert

class FavoritosRvAdapter(val contexto: Context, val favoritos: List<FavoriteLocal>): RecyclerView.Adapter<FavoritosRvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvFavoritosBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvFavoritosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoritos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with (holder) {
            with (favoritos[position]) {
                binding.tvNomeLocal.text = this.nome
                binding.tvEnderecoLocal.text = this.endereco
                binding.clFavorito.setOnClickListener {
                    val intent = Intent(contexto, RotaPesquisaActivity::class.java)
                    intent.putExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, FavoriteLocaleConvert.toLocalPesquisa(this))
                    contexto.startActivity(intent)
                }
            }
        }
    }

}