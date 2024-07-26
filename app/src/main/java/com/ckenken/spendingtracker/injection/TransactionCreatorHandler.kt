package com.ckenken.spendingtracker.injection

import com.ckenken.spendingtracker.viewinfo.TransactionCreatorViewInfo
import kotlinx.coroutines.flow.StateFlow

interface TransactionCreatorHandler {

    val transactionCreatorViewInfo: StateFlow<TransactionCreatorViewInfo>

    fun onClickTypeRadioButton(type: String)
    fun onAmountChanged(amountText: String)
    fun onNoteChanged(note: String)
}