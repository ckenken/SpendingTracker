package com.ckenken.spendingtracker.viewinfo

data class BottomSheetContentViewInfo(
    val amount: String,
    val note: String,
    val selectedCategory: String,
    val selectedType: String,
    val onClickTypeRadioButton: (String) -> Unit,
    val onAmountChanged: (String) -> Unit,
    val onNoteChanged: (String) -> Unit,
)