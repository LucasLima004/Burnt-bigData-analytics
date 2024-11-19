package com.github.metro.recyclerViews

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.github.metro.RotaPesquisaActivity
import com.github.metro.adapter.db.DataBaseAdapter
import com.github.metro.adapter.db.LocaleDatabaseProvider
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RvFavoritosBinding
import com.github.metro.models.FavoriteLocal
import com.github.metro.utils.converters.FavoriteLocaleConvert
import kotlinx.coroutines.launch

class FavoritosRvAdapter(val contexto: ComponentActivity, val favoritos: List<FavoriteLocal>, val notificarRemocao: () -> Unit): RecyclerView.Adapter<FavoritosRvAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: RvFavoritosBinding): RecyclerView.ViewHolder(binding.root)

    private lateinit var dataBaseAdapter: DataBaseAdapter
    private lateinit var databaseProvider: LocaleDatabaseProvider


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RvFavoritosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return favoritos.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        databaseProvider = LocaleDatabaseProvider(contexto)
        val favoriteLocaleDao = databaseProvider.favoriteLocaleDao
        dataBaseAdapter = DataBaseAdapter(favoriteLocaleDao)
        with (holder) {
            with (favoritos[position]) {
                binding.tvNomeLocal.text = this.nome
                binding.tvEnderecoLocal.text = this.endereco
                binding.clFavorito.setOnClickListener {
                    val intent = Intent(contexto, RotaPesquisaActivity::class.java)
                    intent.putExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, FavoriteLocaleConvert.toLocalPesquisa(this))
                    contexto.startActivity(intent)
                }
                binding.btnRemover.setOnClickListener {
                    contexto.lifecycleScope.launch {
                            try {
                                dataBaseAdapter.delete(favoritos[position])
                                notificarRemocao()
                                Log.i("BD", "Finalizou a adição do registro.")
                            } catch (e: Exception) {
                                Log.e("BD", "Erro na adição do registro: ${e}")
                            }
                        }

                }
            }
        }
    }

}