package com.ckenken.spendingtracker

import android.app.Application
import com.ckenken.spendingtracker.database.TransactionDatabase
import com.ckenken.spendingtracker.di.AppModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.ksp.generated.module

class SpendingApp : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SpendingApp)
            androidLogger()
            modules(
                AppModule().module,
            )
        }

        TransactionDatabase.init(this)
    }
}