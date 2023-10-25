package com.example.stockapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stockapp.data.objects.Category
import com.example.stockapp.data.repositories.CategoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel
@Inject constructor(val repository: CategoryRepository) : ViewModel() {

    var category: Category = Category()

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    init {
        viewModelScope.launch {
            repository.categories.collect { categories ->
                _categories.value = categories
            }
        }
    }

    fun new() {
        println("New category instance created")
        category = Category()
    }

    fun edit(category: Category) {
        this.category = category
    }

    fun set() = viewModelScope.launch {
        println("Category saved in database")
        repository.set(category)
        new()
    }

    fun delete(id: Int) = viewModelScope.launch {
        println("Category deleted from database")
        repository.delete(id)
        new()
    }
}