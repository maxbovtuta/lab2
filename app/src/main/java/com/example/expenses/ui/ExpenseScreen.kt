package com.example.expenses.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.expenses.viewmodel.ExpenseViewModel


@Composable
fun ExpenseScreen(viewModel: ExpenseViewModel = viewModel()) {
    val expenses by viewModel.expenses.collectAsState()
    val totalAmount by viewModel.totalAmount.collectAsState()
    val showDialogFor by viewModel.showDialogFor.collectAsState()

    var titleInput by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }

    val amountDouble = amountInput.toDoubleOrNull() ?: 0.0
    val isButtonEnabled = titleInput.isNotBlank() && amountInput.isNotBlank() && amountDouble > 0

    showDialogFor?.let { expenseId ->
        AlertDialog(
            onDismissRequest = { viewModel.dismissDialog() },
            title = { Text("Видалити витрату?") },
            text = { Text("Ви впевнені, що хочете видалити цей запис?") },
            confirmButton = {
                TextButton(onClick = { viewModel.removeExpense(expenseId) }) { Text("Так") }
            },
            dismissButton = {
                TextButton(onClick = { viewModel.dismissDialog() }) { Text("Скасувати") }
            }
        )
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(text = "Загальна сума: $totalAmount ₴", style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.primary)

            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TextField(
                    value = titleInput,
                    onValueChange = { titleInput = it },
                    label = { Text("Назва витрати") },
                    modifier = Modifier.fillMaxWidth()
                )

                TextField(
                    value = amountInput,
                    onValueChange = { amountInput = it },
                    label = { Text("Сума") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        viewModel.addExpense(titleInput, amountDouble)
                        titleInput = ""
                        amountInput = ""
                    },
                    enabled = isButtonEnabled,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Додати")
                }
            }

            Text(text = "Список витрат:", style = MaterialTheme.typography.titleMedium)

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(expenses, key = { it.id }) { expense ->
                    ExpenseCard(expense = expense, onClick = { viewModel.requestDelete(expense.id) })
                }
            }
        }
    }
}