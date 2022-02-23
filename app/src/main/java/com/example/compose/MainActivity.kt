package com.example.compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose.ui.theme.ComposeTheme

val data = listOf(

    TodoItem("yes i will do it", TodoIcon.Done),
    TodoItem("yes i will do it"),
    TodoItem("yes i will do it", TodoIcon.Event),
    TodoItem("yes i will do it", TodoIcon.Privacy),
    TodoItem("yes i will do it", TodoIcon.Trash),
    TodoItem("yes i will do it", TodoIcon.Done),

    )


@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {

    private val todoViewModel by viewModels<TodoViewModel>()



    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun TodoScreenOuter() {
        val items: List<TodoItem> by todoViewModel.todoItems.observeAsState(listOf())

        ToDoScreen(
            items = items,
            onAddItem = { todoViewModel.addItem(it) },
            onRemoveItem = { todoViewModel.removeItem(it) }
        )
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTheme {
                TodoScreenOuter()
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    ComposeTheme {
//        ToDoScreen(items = data)
//    }
//}