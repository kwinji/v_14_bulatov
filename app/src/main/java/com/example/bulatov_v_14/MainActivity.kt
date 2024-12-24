package com.example.moviebookingapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieBookingApp()
        }
    }
}

@Composable
fun MovieBookingApp() {
    var currentScreen by remember { mutableStateOf("Home") } // Управляет текущим экраном
    var movieList by remember { mutableStateOf(listOf<String>()) } // Список фильмов

    when (currentScreen) {
        "Home" -> HomeScreen(
            onNavigateToAdd = { currentScreen = "Add" },
            onNavigateToList = { currentScreen = "List" }
        )
        "Add" -> AddMovieScreen(
            onMovieAdded = { movie ->
                movieList = movieList + movie // Добавление фильма в список
                currentScreen = "Home"
            },
            onBack = { currentScreen = "Home" }
        )
        "List" -> MovieListScreen(
            movieList = movieList,
            onBack = { currentScreen = "Home" }
        )
    }
}

@Composable
fun HomeScreen(onNavigateToAdd: () -> Unit, onNavigateToList: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = onNavigateToAdd) {
                Text("Добавить фильм")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToList) {
                Text("Посмотреть список фильмов")
            }
        }
    }
}

@Composable
fun AddMovieScreen(onMovieAdded: (String) -> Unit, onBack: () -> Unit) {
    var movieName by remember { mutableStateOf(TextFieldValue("")) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Введите название фильма")
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = movieName,
                onValueChange = { movieName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (movieName.text.isNotBlank()) {
                    onMovieAdded(movieName.text)
                }
            }) {
                Text("Добавить")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}

@Composable
fun MovieListScreen(movieList: List<String>, onBack: () -> Unit) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Список фильмов", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            movieList.forEach { movie ->
                Text(
                    text = "- $movie",
                    modifier = Modifier
                        .padding(4.dp)
                        .clickable { /* Реализуйте действия при клике, если необходимо */ }
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MovieBookingApp()
}
