package com.ckenken.spendingtracker.data

data class TransactionData(
    val date: String,
    val items: List<TransactionItemData>
)
