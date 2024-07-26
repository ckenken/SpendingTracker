package com.ckenken.spendingtracker.ui.page.layout

import android.content.res.Configuration
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ckenken.spendingtracker.R
import com.ckenken.spendingtracker.data.SampleData
import com.ckenken.spendingtracker.data.TransactionData
import com.ckenken.spendingtracker.data.TransactionItemData
import com.ckenken.spendingtracker.ui.theme.SpendingTrackerTheme
import com.ckenken.spendingtracker.viewinfo.BottomSheetContentViewInfo
import com.ckenken.spendingtracker.viewinfo.ListPageViewInfo
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetContent(
    bottomSheetContentViewInfo: BottomSheetContentViewInfo,
    transactionDataList: List<TransactionItemData>,
    listPageViewInfo: ListPageViewInfo,
    onClickRefresh: () -> Unit,
    onClickDelete: () -> Unit,
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false),
    )

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            SheetContent(
                scaffoldState = scaffoldState,
                bottomSheetContentViewInfo = bottomSheetContentViewInfo,
            )
        },
        sheetPeekHeight = 0.dp,
        sheetDragHandle = {
            BottomSheetDefaults.DragHandle(shape = ShapeDefaults.Medium)
        },
    ) {
        val bottomSheetState = scaffoldState.bottomSheetState
        BackHandler(enabled = bottomSheetState.currentValue == SheetValue.Expanded) {
            scope.launch {
                scaffoldState.bottomSheetState.hide()
            }
        }

        ListingScreen(
            transactionDataList = transactionDataList,
            onClickRefresh = {
                scope.launch {
                    scaffoldState.bottomSheetState.expand()
                    onClickRefresh.invoke()
                }
            },
            listPageViewInfo = listPageViewInfo,
            onClickDelete = onClickDelete
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetContent(
    scaffoldState: BottomSheetScaffoldState,
    bottomSheetContentViewInfo: BottomSheetContentViewInfo,
) {
//    var amount by remember { mutableStateOf(TextFieldValue("")) }
//    var note by remember { mutableStateOf(TextFieldValue("")) }
//    var selectedCategory by remember { mutableStateOf("") }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
//    var textFieldValue by remember { mutableStateOf(TextFieldValue(bottomSheetContentViewInfo.amount.toString())) }

    // 使用 LaunchedEffect 在组件首次组合时请求焦点
//    LaunchedEffect(scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
//        if (scaffoldState.bottomSheetState.currentValue == SheetValue.Expanded) {
//            focusRequester.requestFocus()
//            keyboardController?.show()
//        }
//    }
    var isAmountInputError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = bottomSheetContentViewInfo.selectedType == "支出",
                    onClick = {
                        bottomSheetContentViewInfo.onClickTypeRadioButton.invoke("支出")
                    }
                )
                Text("支出")
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                RadioButton(
                    selected = bottomSheetContentViewInfo.selectedType == "收入",
                    onClick = {
                        bottomSheetContentViewInfo.onClickTypeRadioButton.invoke("收入")
                    },
                )
                Text("收入")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 8.dp)
            ) {
                OutlinedTextField(
                    value = bottomSheetContentViewInfo.amount,
                    onValueChange = {
                        Log.d("ckenken", "it = $it")
                        bottomSheetContentViewInfo.onAmountChanged.invoke(it)
                        isAmountInputError = it.toDoubleOrNull() == null
                    },
                    label = { Text(text = "金額") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    ),
                    shape = RoundedCornerShape(
                        topStart = 4.dp,
                        bottomStart = 4.dp
                    ),
                    isError = isAmountInputError,
                    modifier = Modifier.fillMaxWidth()
                        .focusRequester(focusRequester),
                )
            }
            Column(
                modifier = Modifier
                    .size(56.dp) // Match the height of OutlinedTextField
                    .clip(
                        shape = RoundedCornerShape(
                            topEnd = 4.dp,
                            bottomEnd = 4.dp,
                        ),
                    )
                    .border(
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        shape = RoundedCornerShape(
                            topEnd = 4.dp,
                            bottomEnd = 4.dp,
                        ),
                    )
                    .background(color = MaterialTheme.colorScheme.inverseSurface)
                    .clickable {

                    },
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calculate), // Replace with your icon resource
                    contentDescription = "計算機",
                    tint = MaterialTheme.colorScheme.inverseOnSurface,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                )
            }
        }
//        if (true) {
//            Text(
//                text = "Invalid number format",
//                color = MaterialTheme.colorScheme.error,
//                style = MaterialTheme.typography.bodySmall,
//            )
//        }

        OutlinedTextField(
            value = bottomSheetContentViewInfo.note,
            onValueChange = {
                bottomSheetContentViewInfo.onNoteChanged.invoke(it)
            },
            label = { Text(text = "備註") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "類別", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
//            CategoryButton("餐飲", selectedCategory) { selectedCategory = "餐飲" }
//            CategoryButton("交通", selectedCategory) { selectedCategory = "交通" }
//            CategoryButton("約會", selectedCategory) { selectedCategory = "約會" }
//            CategoryButton("房租", selectedCategory) { selectedCategory = "房租" }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            // 處理送出邏輯
        }, modifier = Modifier.align(Alignment.End)) {
            Text(text = "送出")
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CategoryButton(category: String, selectedCategory: String, onSelect: () -> Unit) {
    Button(
        onClick = onSelect,
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selectedCategory == category) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
        )
    ) {
        Text(text = category, style = MaterialTheme.typography.bodySmall)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun SheetContentPreview() {
    SpendingTrackerTheme {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false),
        )
        SheetContent(
            scaffoldState = scaffoldState,
            BottomSheetContentViewInfo(
                amount = 100.0.toString(),
                note = "我家",
                selectedCategory = "類別",
                selectedType = "支出",
                onClickTypeRadioButton = {},
                onNoteChanged = {},
                onAmountChanged = {},
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun SheetContentPreview2() {
    SpendingTrackerTheme {
        val scaffoldState = rememberBottomSheetScaffoldState(
            bottomSheetState = rememberStandardBottomSheetState(skipHiddenState = false),
        )
        SheetContent(
            scaffoldState = scaffoldState,
            BottomSheetContentViewInfo(
                amount = 100.0.toString(),
                note = "我家",
                selectedCategory = "類別",
                selectedType = "支出",
                onClickTypeRadioButton = {},
                onNoteChanged = {},
                onAmountChanged = {},
            ),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    SpendingTrackerTheme {
        BottomSheetContent(
            transactionDataList = SampleData.sampleTransactionData,
            bottomSheetContentViewInfo = BottomSheetContentViewInfo(
                amount = 100.0.toString(),
                note = "我家",
                selectedCategory = "類別",
                selectedType = "支出",
                onClickTypeRadioButton = {},
                onNoteChanged = {},
                onAmountChanged = {},
            ),
            listPageViewInfo = ListPageViewInfo(
                searchText = "searchText",
                onContentChangeListener = {},
            ),
            onClickDelete = {},
            onClickRefresh = {},
        )
    }
}