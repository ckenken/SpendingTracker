package com.ckenken.spendingtracker.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction_table")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val timestamp: Long,
    val type: String,
    val category: String,
    val categoryColorId: Int,
    val note: String,
    val amount: Double,
    val currency: String,
)