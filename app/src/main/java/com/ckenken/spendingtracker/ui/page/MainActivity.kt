package com.ckenken.spendingtracker.ui.page

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.collectAsState
import com.ckenken.spendingtracker.ui.ListingViewModel
import com.ckenken.spendingtracker.ui.page.layout.ListingScreen
import com.ckenken.spendingtracker.ui.theme.SpendingTrackerTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity: ComponentActivity() {

    private val viewModel: ListingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpendingTrackerTheme {
                val listState = viewModel.spendingList.collectAsState()
                ListingScreen(listState.value, ::onClickRefresh, ::onClickDelete)
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


