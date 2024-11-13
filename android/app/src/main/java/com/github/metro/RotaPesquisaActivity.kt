package com.github.metro

import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.github.metro.constantes.ConstantesApi
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.RotaPesquisaLayoutBinding
import com.github.metro.models.LocalPesquisa
import com.github.metro.utils.ConexaoVolley
import com.github.metro.utils.LocalizacaoUtils

class RotaPesquisaActivity: ComponentActivity() {
    lateinit var binding: RotaPesquisaLayoutBinding
    lateinit var localPesquisa: LocalPesquisa

//    val resultados = ArrayList()
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = RotaPesquisaLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        localPesquisa = intent.getSerializableExtra(ConstantesExtra.LOCAL_PESQUISA_EXTRA, LocalPesquisa::class.java)!!

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