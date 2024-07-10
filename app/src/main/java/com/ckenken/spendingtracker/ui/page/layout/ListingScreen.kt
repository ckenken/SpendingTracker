package com.ckenken.spendingtracker.ui.page.layout

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ckenken.spendingtracker.R
import com.ckenken.spendingtracker.data.SampleData
import com.ckenken.spendingtracker.data.TransactionData
import com.ckenken.spendingtracker.data.TransactionItemData
import com.ckenken.spendingtracker.ui.theme.SpendingTrackerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingScreen(
    productVMPackagesState: List<TransactionData>,
    onClickRefresh: () -> Unit,
    onClickDelete: () -> Unit,
) {
    Scaffold(
        topBar = {
            Surface(
                shadowElevation = 4.dp,
            ) {
                TopAppBar(
                    title = {
                        Text("七月")
                    },
                    navigationIcon = {
                        IconButton(
                            onClick = { onClickDelete.invoke() }
                        ) {
                            Icon(Icons.Default.Menu, contentDescription = "Menu")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.inversePrimary,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onClickRefresh.invoke() }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        },
    ) { paddingValue ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .padding(horizontal = 8.dp)
        ) {
            SpendingList(productVMPackagesState)
        }
    }
}

@Composable
fun HeaderSection() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.inverseOnSurface),
                    shape = RoundedCornerShape(4.dp),
                )
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp),
        ) {
            HeaderContentItem("收入", "0")
            VerticalDivider()
            HeaderContentItem("支出", "9496")
            VerticalDivider()
            HeaderContentItem("結餘", "-9496")
        }
    }
}

@Composable
fun RowScope.HeaderContentItem(title: String, number: String) {
    Column(
        modifier = Modifier.weight(1f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium)
        Text(text = number, style = MaterialTheme.typography.titleLarge)
    }
}

@Composable
fun VerticalDivider() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Spacer(
            modifier = Modifier
                .width(1.dp)
                .height(50.dp)
                .background(MaterialTheme.colorScheme.onSurface)
        )
    }
}

@Composable
fun SpendingList(transactionDataList: List<TransactionData>) {
    LazyColumn {
        item {
            Spacer(
                modifier = Modifier.height(8.dp),
            )
            HeaderSection()
            Spacer(
                modifier = Modifier.height(8.dp),
            )
        }
        items(transactionDataList) { transaction ->
            SpendingItem(transactionData = transaction)
        }
    }
}

@Composable
fun SpendingItem(transactionData: TransactionData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    BorderStroke(1.dp, MaterialTheme.colorScheme.inverseOnSurface),
                    shape = RoundedCornerShape(4.dp)
                )
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, top = 4.dp, end = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = transactionData.date, style = MaterialTheme.typography.bodySmall)
                Text(
                    text = stringResource(
                        id = R.string.sum_of_a_day,
                        sum(transactionData).toString(),
                    ),
                    style = MaterialTheme.typography.bodySmall,
                )
            }
            Divider(
                color = MaterialTheme.colorScheme.inverseOnSurface,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                transactionData.items.forEachIndexed { index, item ->
                    TransactionItem(item)
                    if (index < transactionData.items.size - 1) {
                        Divider(
                            color = MaterialTheme.colorScheme.inverseOnSurface,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    }
                }
                Spacer(
                    modifier = Modifier.height(8.dp),
                )
            }
        }
    }
}

private fun sum(data: TransactionData) = data.items.sumOf { it.amount }

@Composable
fun TransactionItem(item: TransactionItemData) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                Toast
                    .makeText(context, "Hello, World!", Toast.LENGTH_SHORT)
                    .show()
            }
            .padding(all = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(24.dp)  // 設置背景圓圈的大小
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.inversePrimary),  // 設置背景顏色
                contentAlignment = Alignment.Center  // 圓圈內部居中對齊
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_food),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = item.name, style = MaterialTheme.typography.bodyMedium)
        }
        Text(
            text = "${item.amount}",
            style = MaterialTheme.typography.bodyMedium,
            color = if (item.amount > 0) {
                MaterialTheme.colorScheme.onSurface
            } else {
                MaterialTheme.colorScheme.error
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    SpendingTrackerTheme {
        ListingScreen(productVMPackagesState = SampleData.sampleTransactionData, {}, {})
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview2() {
    SpendingTrackerTheme {
        ListingScreen(productVMPackagesState = SampleData.sampleTransactionData, {}, {})
    }
}