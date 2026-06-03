package com.example.expenses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.expenses.ui.ExpenseScreen
import com.example.expenses.ui.theme.ExpensesDayTheme // Перевір назву своєї теми (якщо підкреслить червоним, просто видали цей імпорт і натисни Alt+Enter)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExpensesDayTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ExpenseScreen() // Наш головний екран витрат
                }
            }
        }
    }
}