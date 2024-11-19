package com.github.metro

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.EstacaoLayoutBinding
import com.github.metro.models.LocalPesquisa

class EstacaoActivity: ComponentActivity() {
    private lateinit var binding: EstacaoLayoutBinding
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = EstacaoLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val estacao = intent.getSerializableExtra(ConstantesExtra.ESTACAO_EXTRA, LocalPesquisa::class.java)!!
        binding.tvNomeEstacao.text = estacao.nome
        binding.tvEnderecoEstacao.text = estacao.endereco
        binding.btnPesquisarRotas.setOnClickListener {
            val intent = Intent(this, PesquisaActivity::class.java)
            intent.putExtra(ConstantesExtra.LOCAL_ORIGEM_PESQUISA_EXTRA, estacao)
            intent.putExtra(ConstantesExtra.VALOR_PESQUISA_EXTRA, "")
            startActivity(intent)
        }

    }
}