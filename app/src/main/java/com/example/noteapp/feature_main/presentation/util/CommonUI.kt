package com.example.noteapp.feature_main.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    color: Color = MaterialTheme.colors.onSurface,
    border: BorderStroke = BorderStroke(0.dp, color),
    contentPadding: PaddingValues = PaddingValues(8.dp),
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = CircleShape,
    elevation: ButtonElevation? = null,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        interactionSource = interactionSource,
        shape = shape,
        elevation = elevation,
        border = border,
        colors = ButtonDefaults.outlinedButtonColors(
            backgroundColor = color.copy(
                alpha = 0.1f,
                red = (color.red + 0.1f).coerceAtMost(0.1f),
                green = (color.green + 0.1f).coerceAtMost(0.1f),
                blue = (color.blue + 0.1f).coerceAtMost(0.1f)
            ),
            contentColor = Color.LightGray
        ),
        contentPadding = contentPadding
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "favourite",
            modifier = Modifier.fillMaxSize(),
            tint = color
        )
    }
}

