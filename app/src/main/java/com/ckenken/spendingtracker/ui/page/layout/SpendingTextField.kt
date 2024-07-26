package com.ckenken.spendingtracker.ui.page.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ckenken.spendingtracker.R
import com.ckenken.spendingtracker.ui.theme.SpendingTheme
import com.ckenken.spendingtracker.ui.theme.SpendingTrackerTheme

@Composable
fun SearchBar(
    text: String,
    height: Int,
    onContentChangeListener: (String) -> Unit,
) {
    BasicTextField(
        value = text,
        onValueChange = {
            onContentChangeListener.invoke(it)
        },
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 2.dp, horizontal = 8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = stringResource(id = R.string.search_hint),
                )
                Spacer(
                    modifier = Modifier.padding(end = 4.dp)
                )
                Box(
                    contentAlignment = Alignment.CenterStart,
                ) {
                    if (text.isEmpty()) {
                        Text(
                            text = stringResource(id = R.string.search_hint),
                            style = MaterialTheme.typography.bodyMedium,
                            color = SpendingTheme.colors.textHintColor,
                        )
                    }
                    innerTextField()
                }
            }
        },
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .background(MaterialTheme.colorScheme.surface, CircleShape)
            .height(height.dp)
            .fillMaxWidth()
    )
}

@Preview(showBackground = true)
@Composable
fun SearchBarSample() {
    SpendingTrackerTheme {
        Box(
            modifier = Modifier
                .height(40.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center,
        ) {
            SearchBar(
                text = "",
                height = 30,
                onContentChangeListener = {},
            )
        }
    }
}