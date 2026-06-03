package com.example.expenses.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.expenses.model.Expense
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ExpenseViewModel : ViewModel() {

    private val _expenses = MutableStateFlow<List<Expense>>(emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses.asStateFlow()

    private val _showDialogFor = MutableStateFlow<String?>(null)
    val showDialogFor: StateFlow<String?> = _showDialogFor.asStateFlow()

    val totalAmount: StateFlow<Double> = _expenses
        .map { list -> list.sumOf { it.amount } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0.0
        )

    fun addExpense(title: String, amount: Double) {
        val newExpense = Expense(title = title, amount = amount)
        _expenses.value = _expenses.value + newExpense
    }

    fun removeExpense(id: String) {
        _expenses.value = _expenses.value.filter { it.id != id }
        dismissDialog()
    }

    fun requestDelete(id: String) {
        _showDialogFor.value = id
    }

    fun dismissDialog() {
        _showDialogFor.value = null
    }
}