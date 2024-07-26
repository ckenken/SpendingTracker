package com.ckenken.spendingtracker.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.ckenken.spendingtracker.ui.page.layout.BottomSheetContent
import com.ckenken.spendingtracker.ui.theme.SpendingTrackerTheme
import com.ckenken.spendingtracker.viewinfo.BottomSheetContentViewInfo
import com.ckenken.spendingtracker.viewinfo.ListPageViewInfo
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: ComponentActivity() {

    private val viewModel: ListingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerTheme {
                val transactionList = viewModel.spendingList.collectAsState(emptyList()).value
                val transactionCreatorViewInfo = viewModel.transactionCreatorViewInfo.collectAsState().value
                val searchText = viewModel.searchText.collectAsState().value
                BottomSheetContent(
                    transactionDataList = transactionList,
                    // TODO: ckenken 不要每一次都新增一個 object
                    bottomSheetContentViewInfo = BottomSheetContentViewInfo(
                        amount = transactionCreatorViewInfo.amount,
                        note = transactionCreatorViewInfo.note,
                        selectedCategory = transactionCreatorViewInfo.selectedCategory,
                        selectedType = transactionCreatorViewInfo.selectedType,
                        onClickTypeRadioButton = { viewModel.onClickTypeRadioButton(it) },
                        onNoteChanged = { viewModel.onNoteChanged(it) },
                        onAmountChanged = {
                            viewModel.onAmountChanged(it)
                        },
                    ),
                    listPageViewInfo = ListPageViewInfo(
                        searchText = searchText,
                        onContentChangeListener = {
                            viewModel.onSearchTextChanged(it)
                        },
                    ),
                    onClickDelete = ::onClickDelete,
                    onClickRefresh = ::onClickRefresh,
                )
            }
        }
    }

    private fun onClickRefresh() {
        viewModel.addData()
    }

    private fun onClickDelete() {
        viewModel.deleteAll()
    }
}


