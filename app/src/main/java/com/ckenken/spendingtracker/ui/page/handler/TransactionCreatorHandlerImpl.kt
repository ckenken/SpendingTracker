package com.ckenken.spendingtracker.ui.page.handler

import com.ckenken.spendingtracker.injection.TransactionCreatorHandler
import com.ckenken.spendingtracker.viewinfo.TransactionCreatorViewInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.koin.core.annotation.Factory

@Factory
class TransactionCreatorHandlerImpl: TransactionCreatorHandler {

    private val _transactionCreatorViewInfo = MutableStateFlow(TransactionCreatorViewInfo.defaultValue())
    override val transactionCreatorViewInfo: StateFlow<TransactionCreatorViewInfo>
        get() = _transactionCreatorViewInfo.asStateFlow()

    override fun onClickTypeRadioButton(type: String) {
        _transactionCreatorViewInfo.value = _transactionCreatorViewInfo.value.copy(
            selectedType = type,
        )
    }

    override fun onAmountChanged(amountText: String) {
        _transactionCreatorViewInfo.value = _transactionCreatorViewInfo.value.copy(
            amount = amountText,
        )
    }

    override fun onNoteChanged(note: String) {
        _transactionCreatorViewInfo.value = _transactionCreatorViewInfo.value.copy(
            note = note,
        )
    }
}
