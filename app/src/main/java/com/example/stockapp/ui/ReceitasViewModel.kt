package com.example.stockapp.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.daos.ReceitaDao
import com.example.stockapp.data.objects.Receita
import com.example.stockapp.data.repositories.ReceitaRepository
import com.example.stockapp.data.repositories.ReceitaRepositorySQlite
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceitasViewModel
@Inject constructor(
    val repository: ReceitaRepository,
    val localRepository: ReceitaRepositorySQlite,
    val context: Context,
    val receitasDao: ReceitaDao
) : ViewModel() {

    var receita: Receita = Receita()

    private val _receitas = MutableStateFlow<List<Receita>>(emptyList())
    val receitas: StateFlow<List<Receita>> = _receitas

    private var isUpdatingLocalData = false

    init {

        print("INICIANDO RECEITA VIEW MODEL")
        loadBase()
        iniciarObservacaoConectividade()
    }

    private fun iniciarObservacaoConectividade() {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                syncWithFirebase()

            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    fun loadBase() {

        viewModelScope.launch {
            repository.receitas.collect { firebaseReceitas ->
                println("RECEITAS FIREBASE" + firebaseReceitas)

                if (isNetworkAvailable(context)) {
                    localRepository.updateLocalData(firebaseReceitas)
                }
                _receitas.value = firebaseReceitas
            }
        }

        viewModelScope.launch {
            localRepository.receitas.collect { localReceitas ->
                if (!isUpdatingLocalData) {
                    _receitas.value = localReceitas
                }
            }
        }
    }

    private fun syncWithFirebase() = viewModelScope.launch {
        // Obtém todas as receitas não sincronizadas do banco local

        val unsyncedReceitas = localRepository.getUnsyncedReceitas()

        // Sincroniza cada receita não sincronizada com o Firebase
        for (receita in unsyncedReceitas) {
            if (isNetworkAvailable(context)) {
                receita.isSynced = true
                repository.set(receita)

                if (receita.isDeleted) {
                    repository.delete(receita)
                }

                println("Sincronizado com o Firebase: $receita")
            } else {
                println("Sem conexão de internet. Sincronização adiada.")
                return@launch
            }
        }

        // Atualiza a flag isSynced para true no banco local
        localRepository.updateSyncStatus(unsyncedReceitas, true)
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

        receita.isSynced = false

        if (isNetworkAvailable(context)) {
            receita.isSynced = true
            repository.set(receita)
            println("passou pelo Firebase")
        } else {
            println("Sem conexão de internet. Operação no Firebase adiada.")
        }

        localRepository.set(receita)
        println(receita)

        new()
    }

    fun delete(receita: Receita) = viewModelScope.launch {
        println("receita deleted from database")

        receita.isSynced = false

        if (isNetworkAvailable(context)) {
            receita.isSynced = true
            repository.delete(receita)
            println("passou pelo Firebase")
        } else {
            println("Sem conexão de internet. Operação no Firebase adiada.")
        }

        localRepository.delete(receita)

        new()
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities =
            connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}