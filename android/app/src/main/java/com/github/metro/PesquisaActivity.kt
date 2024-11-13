package com.github.metro

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.github.metro.constantes.ConstantesApi
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.PesquisaLayoutBinding
import com.github.metro.models.LocalPesquisa
import com.github.metro.recyclerViews.LocalPesquisaRVAdapter
import com.github.metro.utils.ConexaoVolley
import org.json.JSONArray

class PesquisaActivity: ComponentActivity() {
    lateinit var binding: PesquisaLayoutBinding
    var pesquisando = true
    var visibilidadeDigitePesquisar = View.GONE
    var visibilidadeEncontrado = View.GONE
    var visibilidadePesquisando = View.GONE
    var visibilidadeNaoEncontrado = View.GONE
    var resultados: ArrayList<LocalPesquisa> = ArrayList()

    private lateinit var rvAdapter: LocalPesquisaRVAdapter
    fun checarSeResultadosForamEncontrados() {
        // bizarrice abaixo to com preguica de mudar
        if (binding.etPesquisa.text.toString().isEmpty()) {
          visibilidadeDigitePesquisar = View.VISIBLE
            visibilidadePesquisando = View.GONE
            visibilidadeEncontrado = View.GONE

        } else if (pesquisando) {
            visibilidadePesquisando = View.VISIBLE
            visibilidadeEncontrado = View.GONE
            visibilidadeDigitePesquisar = View.GONE

        } else if (resultados.isNotEmpty()) {
            // se pesquisando == false e tiver resultados
            visibilidadeEncontrado = View.VISIBLE
            visibilidadePesquisando = View.GONE
            visibilidadeDigitePesquisar = View.GONE
            atualizarItensRecyclerView(resultados)

        } else {
            // se pesquisando == false e nao tiver resultados
            visibilidadeEncontrado = View.GONE
            visibilidadePesquisando = View.GONE
            visibilidadeDigitePesquisar = View.GONE

        }

        // Visivel se visibilidadeEncontrado nao for visivel
        visibilidadeNaoEncontrado = if (visibilidadeDigitePesquisar == View.GONE && visibilidadeEncontrado == View.GONE) {View.VISIBLE} else {View.GONE}

        atualizarVisibilidades()
    }

    fun setupRecyclerViewPesquisa() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        binding.rvLocaisPesquisa.layoutManager = layoutManager
    }

    fun atualizarItensRecyclerView(resultados: ArrayList<LocalPesquisa>) {
        rvAdapter = LocalPesquisaRVAdapter(
            this, resultados)
        binding.rvLocaisPesquisa.adapter = rvAdapter
    }

    fun atualizarVisibilidades() {
        binding.tvDigiteAlgoParaPesquisar.visibility = visibilidadeDigitePesquisar
        binding.tvPesquisaNaoEncontrada.visibility = visibilidadeNaoEncontrado
        binding.tvLimparPesquisa.visibility = visibilidadeNaoEncontrado
        binding.rvLocaisPesquisa.visibility = visibilidadeEncontrado
    }

    fun limparPesquisa() {
        binding.etPesquisa.setText("")
        checarSeResultadosForamEncontrados()
    }

    fun pesquisar() {
        val textoPesquisa = binding.etPesquisa.text.toString()

        val request = JsonArrayRequest(
            Request.Method.GET, "${ConstantesApi.CAMINHO_SEARCH}?location=${textoPesquisa}", null,
            { response ->
                Log.d("resposta html",response.toString())
                resultados.clear()
                pesquisando = true
                for (i in 0 until response.length()) {
                    val itemAtual = response.getJSONObject(i)
                    val nome = itemAtual.getString("name")
                    val lat = itemAtual.getDouble("latitude")
                    val lon = itemAtual.getDouble("longitude")
                    val endereco = itemAtual.getString("address")

                    resultados.add(
                        LocalPesquisa(
                            nome,
                            lat,
                            lon,
                            endereco
                        )
                    )
                }
                pesquisando = false
                checarSeResultadosForamEncontrados()
            },
            {
            error ->
                Log.e("request erro", error.toString())
                resultados.clear()
                checarSeResultadosForamEncontrados()
            })
        ConexaoVolley.getInstance(this).addToRequestQueue(request)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PesquisaLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val textoPesquisa = intent.getStringExtra(ConstantesExtra.VALOR_PESQUISA_EXTRA)
        if (textoPesquisa == null) {
            finish()
            return
        }

        binding.etPesquisa.setText(textoPesquisa)
        binding.tvLimparPesquisa.setOnClickListener {
            limparPesquisa()
        }
        binding.btnPesquisar.setOnClickListener {
            pesquisar()
        }
        setupRecyclerViewPesquisa()
        pesquisar()
    }
}