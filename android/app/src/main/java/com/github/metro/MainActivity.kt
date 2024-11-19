package com.github.metro

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.github.metro.adapter.db.DataBaseAdapter
import com.github.metro.adapter.db.LocaleDatabaseProvider
import com.github.metro.constantes.ConstantesApi
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.MainLayoutBinding
import com.github.metro.models.FavoriteLocal
import com.github.metro.models.LocalPesquisa
import com.github.metro.recyclerViews.EstacaoRvAdapter
import com.github.metro.recyclerViews.FavoritosRvAdapter
import com.github.metro.utils.ConexaoVolley
import com.github.metro.utils.LocalizacaoUtils
import com.github.metro.utils.converters.FavoriteLocaleConvert
import com.github.metro.viewmodels.MainActivityViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var dataBaseAdapter: DataBaseAdapter
    private lateinit var databaseProvider: LocaleDatabaseProvider
    private lateinit var convert: FavoriteLocaleConvert
    lateinit var binding: MainLayoutBinding
    lateinit var viewModel: MainActivityViewModel

    lateinit var adapter: EstacaoRvAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainActivityViewModel(this)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        convert = FavoriteLocaleConvert()
        databaseProvider = LocaleDatabaseProvider(applicationContext)
        val favoriteLocaleDao = databaseProvider.favoriteLocaleDao
        dataBaseAdapter = DataBaseAdapter(favoriteLocaleDao)
        
        binding.btnPesquisar.setOnClickListener {
            val textoPesquisa = binding.etPesquisa.text.toString()
            if (textoPesquisa.isEmpty()) {
                Toast.makeText(this, "Digite algo antes de pesquisar.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val pesquisaActivityIntent = Intent(this, PesquisaActivity::class.java)
            pesquisaActivityIntent.putExtra(ConstantesExtra.VALOR_PESQUISA_EXTRA, textoPesquisa)
            startActivity(pesquisaActivityIntent)
        }
//        val intent = Intent(this, RotaPesquisaActivity::class.java)
//        intent.putExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, LocalPesquisa(nome="Centro Universitário Estácio do Recife", lat=-8.062037, lon=-34.9161162, endereco="Av. Eng. Abdias de Carvalho, 1678 - Madalena Recife - PE, 50720-225, Brazil"))
//        startActivity(intent)
        setupRvEstacoesProximas()
        pesquisarEstacoesProximas()
        setupRvFavoritos()
    }

    override fun onResume() {
        super.onResume()
        adquirirFavoritos()
    }

    fun setupRvEstacoesProximas() {
        binding.rvEstacoesProximas.layoutManager = LinearLayoutManager(this)
    }

    fun mostrarEstacoesProximas(estacoes: ArrayList<LocalPesquisa>) {
        adapter = EstacaoRvAdapter(this, estacoes)
        binding.rvEstacoesProximas.adapter = adapter
    }

    fun setupRvFavoritos() {
        binding.rvFavoritos.layoutManager = LinearLayoutManager(this)
    }

    fun mostrarFavoritos(favoritos: List<FavoriteLocal>) {
        binding.rvFavoritos.adapter = FavoritosRvAdapter(favoritos)
    }

    fun adquirirFavoritos() {
        lifecycleScope.launch {
            try {
                mostrarFavoritos(dataBaseAdapter.getAllFavoriteLocale())
            } catch (e: Exception) {
                Log.e("BD", "Erro na adição do registro: ${e}")
            }
        }
    }

    fun pesquisarEstacoesProximas() {
        val localizacao = LocalizacaoUtils.getLocation(this)!!
        val textoPesquisa = binding.etPesquisa.text.toString()

        val request = JsonObjectRequest(
            Request.Method.GET, "${ConstantesApi.CAMINHO_STATION}?localLat=${localizacao.latitude}&localLon=${localizacao.longitude}&type=bus_station", null,
            { response ->
                response
                val resultados = response.getJSONArray("results")
                val resultadosList = ArrayList<LocalPesquisa>()
                for (i in 0 until resultados.length()) {
                    val resultado = resultados.getJSONObject(i)
                    val geometry = resultado.getJSONObject("geometry")
                    val location = geometry.getJSONObject("location")
                    val lat = location.getDouble("lat")
                    val lon = location.getDouble("lng")
                    val name = resultado.getString("name")
                    resultadosList.add(LocalPesquisa(nome = name, lat = lat, lon = lon, endereco = ""))
                }
                mostrarEstacoesProximas(resultadosList)
            },
            {
                    error ->
                Log.e("request erro", error.toString())
            })
        ConexaoVolley.getInstance(this).addToRequestQueue(request)
    }
}
