package com.ckenken.spendingtracker.database

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {

    companion object {
        private const val DATABASE_NAME = "transaction_database"

        @Volatile
        lateinit var INSTANCE: TransactionDatabase

        fun init(application: Application) {
            INSTANCE = Room.databaseBuilder(
                application .applicationContext,
                TransactionDatabase::class.java,
                DATABASE_NAME,
            ).build()
        }
    }

    abstract fun transactionDao(): TransactionDao
}