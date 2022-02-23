package com.example.compose

import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import java.util.*
import kotlin.random.Random

fun genRandomItem(): TodoItem {
    val message = listOf(
        "Learn compose",
        "Learn state",
        "Build Dynamic UIs",
        "Learn Unindirectional Data Flow",
        "Integrate LiveData",
        "Integrate ViewModel",
        "remember to savedState",
        "Build stateless composables",
        "Use state from stateless composables"
    ).random()
    val icon = TodoIcon.values().random()
    return TodoItem(message, icon)
}


data class TodoItem(
    val task: String,
    val icon: TodoIcon = TodoIcon.Default,
    val id: UUID = UUID.randomUUID()
)


enum class TodoIcon(
    val imageVector: ImageVector,
    @StringRes val contentDescription: Int
) {

    Square(Icons.Default.CropSquare, R.string.cd_crop_square),
    Done(Icons.Default.Done, R.string.cd_done),
    Event(Icons.Default.Event, R.string.cd_event),
    Privacy(Icons.Default.PrivacyTip, R.string.cd_privacy),
    Trash(Icons.Default.RestoreFromTrash, R.string.cd_restore);

    companion object {
        val Default = Square
    }

}


@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@Composable
fun ToDoScreen(
    items: List<TodoItem>,
    onAddItem: (TodoItem) -> Unit,
    onRemoveItem: (TodoItem) -> Unit
) {

    Column() {
        TodoItemInputBackground(elevate = true) {
            TodoItemEntryInput(onItemComplete = onAddItem)
        }


        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(top = 8.dp)


        ) {
            items(items) {
                TodoRow(
                    todo = it,
                    modifier = Modifier.fillParentMaxWidth(),
                    onItemClicked = onRemoveItem
                )
            }
        }

        Button(
            onClick = {
                onAddItem(genRandomItem())
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            Text(text = "Add Random Item")
        }
    }

}

@Composable
fun TodoRow(
    todo: TodoItem,
    onItemClicked: (TodoItem) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onItemClicked(todo)
            },
        horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Text(text = todo.task)

        val iconAlpha: Float = remember(todo.id) { randomTint() }
        Icon(
            imageVector = todo.icon.imageVector,
            tint = LocalContentColor.current.copy(alpha = iconAlpha),
            contentDescription = stringResource(id = todo.icon.contentDescription)
        )
    }
}

private fun randomTint(): Float {
    return Random.nextFloat().coerceIn(0.3f, 0.9f)
}