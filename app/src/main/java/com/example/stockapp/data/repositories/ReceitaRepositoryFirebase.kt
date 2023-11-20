package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Category
import com.example.stockapp.data.objects.Receita
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Named

class ReceitaRepositoryFirebase
@Inject constructor(@Named("receitas") private val receitasRef: CollectionReference)
    : ReceitaRepository {

    private var _receitas = MutableStateFlow(listOf<Receita>())
    override val receitas: Flow<List<Receita>>
        get() = _receitas.asStateFlow()

    init {
        receitasRef.addSnapshotListener { snapshot, _ ->
            if(snapshot != null){
                var receitas = mutableListOf<Receita>()
                snapshot.documents.forEach{ doc ->
                    val receita = doc.toObject<Receita>()
                    if (receita != null){
                        receita.docId = doc.id
                        receitas.add(receita)
                    }
                }
                _receitas.value = receitas
            }else{
                _receitas = MutableStateFlow(listOf())
            }
        }
    }

    override suspend fun set(receita: Receita) {
        try {
            if (receita.docId.isNullOrEmpty()) {
                // Se o docId estiver vazio ou nulo, é uma nova receita, então crie um novo documento
                val docRef = receitasRef.add(receita).await()
                receita.docId = docRef.id
            } else {
                // Se o docId já estiver definido, atualize o documento existente
                receitasRef.document(receita.docId!!).set(receita).await()
            }

            println("Receita salva com sucesso! DocId: ${receita.docId}")
        } catch (e: Exception) {
            // Lide com a exceção, faça log ou notifique o usuário
            println("Erro ao definir a receita: $e")
            throw e
        }
    }

    override suspend fun delete(receita: Receita) {

        receitasRef.document(receita.docId).delete()
            .addOnSuccessListener {
                receita.docId = ""
            }

            .addOnFailureListener { e ->
                // Handle the failure, log, or notify the user
                println("Error deleting receita: $e")
            }

    }

    private val firestore = FirebaseFirestore.getInstance()
    private val receitasCollection = firestore.collection("receitas")

    override suspend fun getReceitas(): List<Receita> {
        return try {
            val querySnapshot = receitasCollection.get().await()
            val receitas = mutableListOf<Receita>()

            for (document in querySnapshot.documents) {
                val receita = document.toObject(Receita::class.java)
                receita?.let { receitas.add(it) }
            }

            receitas
        } catch (e: Exception) {
            // Handle exceptions (e.g., network issues, data parsing)
            emptyList()
        }
    }
}

