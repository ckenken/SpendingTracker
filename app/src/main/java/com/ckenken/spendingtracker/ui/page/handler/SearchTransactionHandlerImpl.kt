package com.ckenken.spendingtracker.ui.page.handler

import com.ckenken.spendingtracker.injection.SearchTransactionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Factory

@Factory
class SearchTransactionHandlerImpl: SearchTransactionHandler {
    private val _searchText = MutableStateFlow("")
    override val searchText: StateFlow<String> get() = _searchText.asStateFlow()

    override fun onSearchTextChanged(searchText: String) {
        _searchText.value = searchText
    }
}
