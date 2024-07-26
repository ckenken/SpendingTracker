package com.ckenken.spendingtracker.viewinfo

data class TransactionCreatorViewInfo(
    val amount: String,
    val note: String,
    val selectedCategory: String,
    val selectedType: String,
) {
    companion object {
        fun defaultValue() = TransactionCreatorViewInfo(
            amount = "",
            note = "",
            selectedCategory = "",
            selectedType = "",
        )
    }
}
