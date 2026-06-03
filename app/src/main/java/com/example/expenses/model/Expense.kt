package com.example.expenses.model

import java.util.UUID

data class Expense(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double
)