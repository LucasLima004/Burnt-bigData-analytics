package com.github.metro

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.PesquisaLayoutBinding

class PesquisaActivity: ComponentActivity() {
    lateinit var binding: PesquisaLayoutBinding
    var pesquisando = true
    var visibilidadeDigitePesquisar = View.GONE
    var visibilidadeEncontrado = View.GONE
    var visibilidadePesquisando = View.GONE
    var visibilidadeNaoEncontrado = View.GONE
    val resultados: ArrayList<Any> = ArrayList()
    fun checarSeResultadosForamEncontrados() {
        // bizarrice abaixo to com preguica de mudar
        if (binding.etPesquisa.text.toString().isEmpty()) {
          visibilidadeDigitePesquisar = View.VISIBLE
            visibilidadePesquisando = View.GONE
            visibilidadeEncontrado = View.GONE

        } else if (pesquisando == true) {
            visibilidadePesquisando = View.VISIBLE
            visibilidadeEncontrado = View.GONE
            visibilidadeDigitePesquisar = View.GONE

        } else if (resultados.isNotEmpty()) {
            // se pesquisando == false e tiver resultados
            visibilidadeEncontrado = View.VISIBLE
            visibilidadePesquisando = View.GONE
            visibilidadeDigitePesquisar = View.GONE

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

    fun atualizarVisibilidades() {
        binding.tvDigiteAlgoParaPesquisar.visibility = visibilidadeDigitePesquisar
        binding.tvPesquisaNaoEncontrada.visibility = visibilidadeNaoEncontrado
        binding.tvLimparPesquisa.visibility = visibilidadeNaoEncontrado
        binding.rvEstacoesProximas.visibility = visibilidadeEncontrado
    }

    fun limparPesquisa() {
        binding.etPesquisa.setText("")
        checarSeResultadosForamEncontrados()
    }

    fun pesquisar() {
        val textoPesquisa = binding.etPesquisa.text.toString()
        checarSeResultadosForamEncontrados()
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

        pesquisar()
    }
}