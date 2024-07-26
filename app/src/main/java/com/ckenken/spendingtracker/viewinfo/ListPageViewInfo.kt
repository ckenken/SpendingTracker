package com.ckenken.spendingtracker.viewinfo

data class ListPageViewInfo(
    val searchText: String,
    val onContentChangeListener: (String) -> Unit,
) {
    companion object {
        fun defaultValue() = ListPageViewInfo(
            searchText = "",
            onContentChangeListener = {},
        )
    }
}