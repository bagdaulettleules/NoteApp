package com.example.noteapp.feature_main.presentation.list.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
                text = "Title",
                isSelected = orderBy is OrderBy.Title,
                onSelect = { onOrderChange(OrderBy.Title(orderBy.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = "Date",
                isSelected = orderBy is OrderBy.Date,
                onSelect = { onOrderChange(OrderBy.Date(orderBy.orderType)) }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = "Color",
                isSelected = orderBy is OrderBy.Color,
                onSelect = { onOrderChange(OrderBy.Color(orderBy.orderType)) }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            OutlinedText(
                text = "Ascending",
                isSelected = orderBy.orderType is OrderType.Asc,
                onSelect = {
                    onOrderChange(orderBy.copy(OrderType.Asc))
                }
            )

            Spacer(modifier = Modifier.width(8.dp))

            OutlinedText(
                text = "Descending",
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
