package com.ckenken.spendingtracker.injection

import kotlinx.coroutines.flow.StateFlow

interface SearchTransactionHandler {
    val searchText: StateFlow<String>
    fun onSearchTextChanged(searchText: String)
}

