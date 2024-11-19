package com.github.metro.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.github.metro.constantes.ConstantesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivityViewModel(val context: Context): ViewModel() {
    private var jobPesquisar: Job = Job()
        get() = field
    private var pesquisa = ""
        get() = field
        set(value) {
            field = value
        }
    var resultadosPesquisa: JSONObject? = null
        get() = field


}