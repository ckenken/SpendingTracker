package com.ckenken.spendingtracker.ui.page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ckenken.spendingtracker.data.SampleData
import com.ckenken.spendingtracker.data.TransactionData
import com.ckenken.spendingtracker.data.TransactionItemData
import com.ckenken.spendingtracker.database.TransactionEntity
import com.ckenken.spendingtracker.database.TransactionRepository
import com.ckenken.spendingtracker.injection.SearchTransactionHandler
import com.ckenken.spendingtracker.injection.TransactionCreatorHandler
import com.ckenken.spendingtracker.ui.page.handler.SearchTransactionHandlerImpl
import com.ckenken.spendingtracker.ui.page.handler.TransactionCreatorHandlerImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import org.koin.android.annotation.KoinViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@KoinViewModel
class ListingViewModel(
    private val repository: TransactionRepository,
    private val transitionCreatorHandler: TransactionCreatorHandlerImpl,
    private val searchTransactionHandler: SearchTransactionHandlerImpl,
): ViewModel(),
    TransactionCreatorHandler by transitionCreatorHandler,
    SearchTransactionHandler by searchTransactionHandler {

    private val _originalSpendingList = MutableStateFlow<List<TransactionItemData>>(emptyList())

    val spendingList: Flow<List<TransactionItemData>>
        get() = combine(
            _originalSpendingList, searchTransactionHandler.searchText
        ) { list, searchText ->
            if (searchText.isEmpty()) {
                list
            } else {
                list.filter {
                    it.date.contains(searchText) ||
                        it.note.contains(searchText) ||
                        it.category.contains(searchText)
                }
            }
        }

    init {
        fetchData()
    }

    fun addData() = viewModelScope.launch {
        repository.insertAll(
            flattenTransactionData(SampleData.sampleTransactionData)
        )
//        repository.insert(convertVieItemToEntity(transactionData))
    }

    private fun flattenTransactionData(transactionDataList: List<TransactionItemData>): List<TransactionEntity> {
        return transactionDataList.map { transactionData ->
            convertViewItemToEntity(transactionData)
        }
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

    private fun fetchData() = viewModelScope.launch {
        repository.getAllTransactionsFlow()
            .flowOn(Dispatchers.IO)
            .collect {
                _originalSpendingList.value = convertEntitiesToViewList(it)
            }
    }

    private fun convertEntitiesToViewList(list: List<TransactionEntity>): List<TransactionItemData> {
//        val entityMap = list.groupBy { it.date }
        return list.map { entity ->
            TransactionItemData(
                date = entity.date,
                note = entity.note.ifEmpty { entity.category },
                amount = entity.amount,
                timestamp = entity.timestamp,
                type = entity.type,
                category = entity.category,
                currency = entity.currency,
                categoryColorId = entity.categoryColorId,
            )
        }
    }

    private fun convertViewItemToEntity(
        transactionItemData: TransactionItemData,
    ): TransactionEntity {
        return TransactionEntity(
            date = transactionItemData.date,
            timestamp = convertDateToTimestamp(transactionItemData.date),
            type = "支出",
            category = "餐飲", // getString by category
            categoryColorId = 1,
            note = transactionItemData.note,
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