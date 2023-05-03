package com.example.noteapp.feature_main.presentation.list.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OutlinedText(
    text: String,
    isSelected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
    enabledColor: Color = MaterialTheme.colors.primary,
    disabledColor: Color = MaterialTheme.colors.onPrimary
) {
    val color by animateColorAsState(
        targetValue = if (isSelected) disabledColor else enabledColor,
        animationSpec = tween(1000)
    )
    OutlinedButton(
        onClick = onSelect,
        modifier = modifier,
        enabled = !isSelected,
        border = BorderStroke(1.dp, color),
        shape = RoundedCornerShape(50),
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = Color.Black,
            contentColor = enabledColor,
            disabledContentColor = disabledColor
        )
    ) {
        Text(text = text, style = MaterialTheme.typography.body1, color = color)
    }
}