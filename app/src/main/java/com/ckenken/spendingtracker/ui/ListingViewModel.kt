package com.ckenken.spendingtracker.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckenken.spendingtracker.data.SampleData
import com.ckenken.spendingtracker.data.TransactionData
import com.ckenken.spendingtracker.data.TransactionItemData
import com.ckenken.spendingtracker.database.TransactionEntity
import com.ckenken.spendingtracker.database.TransactionRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

import org.koin.android.annotation.KoinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@KoinViewModel
class ListingViewModel(
    private val repository: TransactionRepository,
): ViewModel() {

    private val _spendingList = MutableStateFlow<List<TransactionData>>(emptyList())
    val spendingList: StateFlow<List<TransactionData>> get() = _spendingList.asStateFlow()

    init {
        fetchData()
    }

    fun addData() = viewModelScope.launch {
        repository.insertAll(
            flattenTransactionData(SampleData.sampleTransactionData)
        )
//        repository.insert(convertVieItemToEntity(transactionData))
    }

    private fun flattenTransactionData(transactionDataList: List<TransactionData>): List<TransactionEntity> {
        return transactionDataList.flatMap { transactionData ->
            transactionData.items.map { item ->
                convertViewItemToEntity(transactionData.date, item)
            }
        }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    private fun fetchData() = viewModelScope.launch {
        repository.getAllTransactionsFlow()
            .flowOn(Dispatchers.IO)
            .collect {
                _spendingList.value = convertEntitiesToViewList(it)
            }
    }

    private fun convertEntitiesToViewList(list: List<TransactionEntity>): List<TransactionData> {
        val entityMap = list.groupBy { it.date }
        return entityMap.map { (date, entities) ->
            TransactionData(
                date = date,
                items = entities.map { entity ->
                    TransactionItemData(
                        name = entity.note.ifEmpty { entity.category },
                        amount = entity.amount,
                    )
                },
            )
        }
    }

    private fun convertViewItemToEntity(date: String, transactionItemData: TransactionItemData): TransactionEntity {
        return TransactionEntity(
            date = date,
            timestamp = convertDateToTimestamp(date),
            type = "支出",
            category = "餐飲", // getString by category
            categoryColorId = 1,
            note = transactionItemData.name,
            amount = transactionItemData.amount,
            currency = "TWD",
        )
    }

    private fun convertDateToTimestamp(dateString: String): Long {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date? = dateFormat.parse(dateString)
        return date?.time ?: 0L
    }
}