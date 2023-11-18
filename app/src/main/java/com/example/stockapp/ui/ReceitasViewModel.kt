package com.example.stockapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.repositories.ReceitaRepository
import com.example.stockapp.data.repositories.ReceitaRepositorySQlite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceitasViewModel
@Inject constructor(val repository: ReceitaRepository, val localRepository: ReceitaRepositorySQlite) : ViewModel() {

    var receita: Receita = Receita()

    private val _receitas = MutableStateFlow<List<Receita>>(emptyList())
    val receitas: StateFlow<List<Receita>> = _receitas

    private var isUpdatingLocalData = false

    init {
        viewModelScope.launch {
            repository.receitas.collect { firebaseReceitas ->
                if (!isUpdatingLocalData) {
                    println("FIREBASE INIT")
                    isUpdatingLocalData = true
                    localRepository.updateLocalData(firebaseReceitas)
                    isUpdatingLocalData = false
                }
            }
        }

        viewModelScope.launch {
            localRepository.receitas.collect { localReceitas ->
                if (!isUpdatingLocalData) {
                    println("LOCAL INIT")
                    _receitas.value = localReceitas
                }
            }
        }
    }

    fun new() {
        println("New receita instance created")
        this.receita = Receita()
    }

    fun edit(receita: Receita) {
        this.receita = receita
    }

    fun set() = viewModelScope.launch {
        println("receita saved in database")
        println(receita)
        localRepository.set(receita)
        repository.set(receita)

        new()
    }

    fun delete(receita: Receita) = viewModelScope.launch {
        println("receita deleted from database")
        repository.delete(receita)
        localRepository.delete(receita)
        new()
    }
}