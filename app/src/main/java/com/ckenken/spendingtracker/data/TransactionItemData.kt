package com.ckenken.spendingtracker.data

data class TransactionItemData(
    val timestamp: Long,
    val type: String,
    val category: String,
    val currency: String,
    val categoryColorId: Int = -1,
    val note: String = "",
    val amount: Double = 0.0,
)