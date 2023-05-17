package com.example.noteapp.feature_main.presentation.list.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.noteapp.R
import com.example.noteapp.feature_main.domain.util.OrderBy
import com.example.noteapp.feature_main.domain.util.OrderType

@Composable
fun OrderBySection(
    modifier: Modifier = Modifier,
    orderBy: OrderBy = OrderBy.Date(OrderType.Desc),
    onOrderChange: (OrderBy) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row {
            OutlinedText(
                text = stringResource(R.string.title),
                isSelected = orderBy is OrderBy.Title,
                onSelect = { onOrderChange(OrderBy.Title(orderBy.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = stringResource(R.string.date),
                isSelected = orderBy is OrderBy.Date,
                onSelect = { onOrderChange(OrderBy.Date(orderBy.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = stringResource(R.string.color),
                isSelected = orderBy is OrderBy.Color,
                onSelect = { onOrderChange(OrderBy.Color(orderBy.orderType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            OutlinedText(
                text = stringResource(R.string.ascending),
                isSelected = orderBy.orderType is OrderType.Asc,
                onSelect = {
                    onOrderChange(orderBy.copy(OrderType.Asc))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = stringResource(R.string.descending),
                isSelected = orderBy.orderType is OrderType.Desc,
                onSelect = {
                    onOrderChange(orderBy.copy(OrderType.Desc))
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewOrderBySection() {
    OrderBySection(
        modifier = Modifier
            .fillMaxSize(),
        orderBy = OrderBy.Color(OrderType.Desc),
        onOrderChange = {}
    )
}
