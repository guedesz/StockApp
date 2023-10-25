package com.example.stockapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.repositories.ReceitaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceitasViewModel
@Inject constructor(val repository: ReceitaRepository) : ViewModel() {

    var receita: Receita = Receita()

    private val _receitas = MutableStateFlow<List<Receita>>(emptyList())
    val receitas: StateFlow<List<Receita>> = _receitas

    init {
        viewModelScope.launch {
            repository.receitas.collect { categories ->
                _receitas.value = categories
            }
        }
    }

    fun new() {
        println("New receita instance created")
        receita = Receita()
    }

    fun edit(receita: Receita) {
        this.receita = receita
    }

    fun set() = viewModelScope.launch {
        println("receita saved in database")
        repository.set(receita)
        new()
    }

    fun delete(id: Int) = viewModelScope.launch {
        println("receita deleted from database")
        repository.delete(id)
        new()
    }
}