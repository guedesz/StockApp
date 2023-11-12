package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Category
import com.example.stockapp.data.objects.Receita
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ReceitaRepositoryFirebase
@Inject constructor(private val receitasRef: CollectionReference)
    : ReceitaRepository {

    private var _receitas = MutableStateFlow(listOf<Receita>())
    override val receitas: Flow<List<Receita>>
        get() = _receitas.asStateFlow()

    init {
        receitasRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                // Handle the exception, log, or notify the user
                println("Error getting receitas: $exception")
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.metadata.isFromCache) {
                val receitas = mutableListOf<Receita>()
                snapshot.documents.forEach { doc ->
                    val receita = doc.toObject<Receita>()
                    if (receita != null) {
                        receita.docId = doc.id
                        receitas.add(receita)
                    }
                }
                _receitas.value = receitas
            } else {
                // Handle cache scenario if needed
            }
        }
    }

    override suspend fun set(receita: Receita) {
        try {
            // Use add() para permitir que o Firestore gere automaticamente um ID exclusivo
            val docRef = receitasRef.add(receita).await()

            // Atualizar o campo id na sua classe Receita, caso seja um número inteiro

            println("Receita salva com sucesso!")
        } catch (e: Exception) {
            // Handle the exception, log, or notify the user
            println("Error setting receita: $e")
            throw e
        }

    }

    override suspend fun delete(id: Int) {
        receitasRef.document(id.toString()).delete()
            .addOnFailureListener { e ->
                // Handle the failure, log, or notify the user
                println("Error deleting receita: $e")
            }

    }


}
