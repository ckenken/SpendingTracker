package com.ckenken.spendingtracker.database

import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Single

@Single
class TransactionRepository {
    private val transactionDao: TransactionDao = TransactionDatabase.INSTANCE.transactionDao()

    suspend fun insert(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    suspend fun insertAll(transactions: List<TransactionEntity>) {
        transactionDao.insertAll(*transactions.toTypedArray())
    }

    suspend fun deleteAll() {
        transactionDao.deleteAll()
    }

    suspend fun getAllTransactions(): List<TransactionEntity> {
        return transactionDao.getAllTransactions()
    }

    suspend fun getAllTransactionsFlow(): Flow<List<TransactionEntity>> {
        return transactionDao.getAllTransactionsFlow()
    }
}