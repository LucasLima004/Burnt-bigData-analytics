package com.github.metro

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.github.metro.constantes.ConstantesExtra
import com.github.metro.databinding.MainLayoutBinding
import com.github.metro.viewmodels.MainActivityViewModel

class MainActivity : ComponentActivity() {
    lateinit var binding: MainLayoutBinding
    lateinit var viewModel: MainActivityViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = MainActivityViewModel(this)
        binding = MainLayoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
}
