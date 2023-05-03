package com.example.noteapp.feature_main.presentation.list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.noteapp.feature_main.domain.model.Note
import com.example.noteapp.feature_main.presentation.CircleButton
import com.example.noteapp.ui.theme.BurntSienna
import com.example.noteapp.ui.theme.NoteAppTheme

@Composable
fun NoteMinimal(
    modifier: Modifier = Modifier,
    note: Note,
    shape: Shape = RoundedCornerShape(
        topStart = 0.dp,
        topEnd = 56.dp,
        bottomStart = 56.dp,
        bottomEnd = 56.dp
    ),
    contentColor: Color = MaterialTheme.colors.onSurface,
    border: BorderStroke? = null,
    elevation: Dp = 0.dp,
    onDeleteClick: () -> Unit
) {
    val iconSize = 48.dp
    Card(
        modifier = modifier,
        shape = shape,
        backgroundColor = Color(note.color),
        contentColor = contentColor,
        border = border,
        elevation = elevation
    ) {
        Box(
            modifier = Modifier
                .heightIn(min = 200.dp)
                .fillMaxWidth()
                .padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 20.dp,
                    bottom = 16.dp
                )
        ) {
            Column{
                Text(
                    text = note.title,
                    maxLines = 2,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Update 2h ago",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.body2,
                    fontWeight = FontWeight.Thin
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = note.content,
                    style = MaterialTheme.typography.body1,
                    color = MaterialTheme.colors.onSurface,
                    maxLines = 10,
                    overflow = TextOverflow.Ellipsis
                )
            }

            CircleButton(
                modifier = Modifier
                    .size(iconSize)
                    .align(Alignment.BottomEnd),
                icon = Icons.Outlined.DeleteOutline,
                color = MaterialTheme.colors.onSurface,
                contentPadding = PaddingValues(iconSize / 4),
                onClick = onDeleteClick
            )
        }
    }
}

@Preview
@Composable
fun NoteMinimalPreview() {
    NoteAppTheme {
        NoteMinimal(
            note = Note(
                "Plan for The Day",
                "Bla bla bla bla",
                3000L,
                BurntSienna.toArgb(),
                true,
                1
            ),
            onDeleteClick = {}
        )
    }
}