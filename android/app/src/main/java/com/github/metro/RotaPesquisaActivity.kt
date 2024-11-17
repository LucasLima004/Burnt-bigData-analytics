package com.github.metro

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.github.metro.adapter.db.DataBaseAdapter
import com.github.metro.adapter.db.LocaleDatabaseProvider
import com.github.metro.constantes.ConstantesApi
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RotaPesquisaLayoutBinding
import com.github.metro.models.LocalPesquisa
import com.github.metro.utils.ConexaoVolley
import com.github.metro.utils.converters.FavoriteLocaleConvert
import kotlinx.coroutines.launch

class RotaPesquisaActivity: ComponentActivity() {
    lateinit var binding: RotaPesquisaLayoutBinding
    lateinit var localPesquisa: LocalPesquisa


    lateinit var convert: FavoriteLocaleConvert
    private lateinit var dataBaseAdapter: DataBaseAdapter
    private lateinit var databaseProvider: LocaleDatabaseProvider


    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RotaPesquisaLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)
        convert = FavoriteLocaleConvert()
        databaseProvider = LocaleDatabaseProvider(applicationContext)
        val favoriteLocaleDao = databaseProvider.favoriteLocaleDao
        dataBaseAdapter = DataBaseAdapter(favoriteLocaleDao)

        localPesquisa = intent.getSerializableExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, LocalPesquisa::class.java)!!

        binding.btnFavoritar.setOnClickListener {
            lifecycleScope.launch {
                try {
                    dataBaseAdapter.insertFavoriteLocal(convert.toFavoriteLocal(localPesquisa))
                    Log.i("BD", "Finalizou a adição do registro.")
                } catch (e: Exception) {
                    Log.e("BD", "Erro na adição do registro: ${e}")
                }
            }
        }

        binding.tvNomeLocal.text = localPesquisa.nome
        binding.tvEnderecoLocal.text = localPesquisa.endereco
//        val location = LocalizacaoUtils.getLocation(this)
//        pesquisarRota(location!!)
    }

    fun pesquisarRota(location: Location) {
        val request = JsonArrayRequest(
            Request.Method.GET, "${ConstantesApi.CAMINHO_LOCALIDADE}?lat=${location.latitude}&lon=${location.longitude}&localLat=${localPesquisa.lat}&localLon=${localPesquisa.lon}&veiculo=bus", null,
            { response ->
                Log.d("resposta html",response.toString())
                for (i in 0 until response.length()) {
                    val itemAtual = response.getJSONObject(i)
                    val nome = itemAtual.getString("name")
                    val lat = itemAtual.getDouble("latitude")
                    val lon = itemAtual.getDouble("longitude")
                    val endereco = itemAtual.getString("address")

//                    resultados.add(
//                        LocalPesquisa(
//                            nome,
//                            lat,
//                            lon,
//                            endereco
//                        )
//                    )
                }
            },
            {
                    error ->
                Log.e("request erro", error.toString())
//                resultados.clear()
            })
        ConexaoVolley.getInstance(this).addToRequestQueue(request)
    }
}