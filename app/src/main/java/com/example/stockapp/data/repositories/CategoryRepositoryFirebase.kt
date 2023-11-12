package com.example.stockapp.data.repositories

import com.example.stockapp.data.objects.Category
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ktx.toObject
import dagger.Module
import dagger.hilt.InstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class CategoryRepositoryFirebase
@Inject constructor(private val categoryRef: CollectionReference)
    : CategoryRepository {

    private var _categories = MutableStateFlow(listOf<Category>())
    override val categories: Flow<List<Category>>
        get() = _categories.asStateFlow()

    init {
        categoryRef.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                // Handle the exception, log, or notify the user
                println("Error getting categories: $exception")
                return@addSnapshotListener
            }

            if (snapshot != null && !snapshot.metadata.isFromCache) {
                val categories = mutableListOf<Category>()
                snapshot.documents.forEach { doc ->
                    val category = doc.toObject<Category>()
                    if (category != null) {
                        category.docId = doc.id
                        categories.add(category)
                    }
                }
                _categories.value = categories
            } else {
                // Handle cache scenario if needed
            }
        }
    }
    override suspend fun set(category: Category) {
        if (category.docId.isNullOrEmpty()) {
            val doc = categoryRef.document()
            category.docId = doc.id
            doc.set(category).addOnFailureListener { e ->
                // Handle the failure, log, or notify the user
                println("Error setting category: $e")
            }
        } else {
            categoryRef.document(category.docId).set(category)
                .addOnFailureListener { e ->
                    // Handle the failure, log, or notify the user
                    println("Error setting category: $e")
                }
        }
    }
    override suspend fun delete(id: Int) {
        categoryRef.document(id.toString()).delete()
            .addOnFailureListener { e ->
                // Handle the failure, log, or notify the user
                println("Error deleting category: $e")
            }
    }
    override suspend fun getCategoryByName(name: String): Category {
        return try {
            val querySnapshot = categoryRef
                .whereEqualTo("name", name)
                .get()
                .await()

            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents[0]
                val category = document.toObject(Category::class.java)
                category?.apply {
                    docId = document.id
                } ?: throw NoSuchElementException("Category not found")
            } else {
                throw NoSuchElementException("Category not found")
            }
        } catch (e: Exception) {
            // Handle the exception, log, or notify the user
            println("Error getting category by name: $e")
            throw e
        }
    }

}
