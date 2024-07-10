package com.ckenken.spendingtracker.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: TransactionEntity)

    @Insert
    suspend fun insertAll(vararg transactions: TransactionEntity)

    @Query("DELETE FROM transaction_table")
    suspend fun deleteAll()

    @Query("SELECT * FROM transaction_table")
    suspend fun getAllTransactions(): List<TransactionEntity>

    @Query("SELECT * FROM transaction_table")
    fun getAllTransactionsFlow(): Flow<List<TransactionEntity>>
}