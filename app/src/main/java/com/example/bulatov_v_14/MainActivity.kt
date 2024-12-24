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
            onNavigateToList = { currentScreen = "List" },
            onNavigateToEdit = { currentScreen = "Edit" }, // Переход в экран редактирования
            onNavigateToDelete = { currentScreen = "Delete" } // Переход в экран удаления
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
        "Edit" -> EditMovieScreen(
            onMovieEdited = { oldName, newName ->
                movieList = movieList.map { if (it == oldName) newName else it }
                currentScreen = "Home"
            },
            onBack = { currentScreen = "Home" }
        )
        "Delete" -> DeleteMovieScreen(
            movieList = movieList,
            onMovieDeleted = { movie ->
                movieList = movieList.filter { it != movie }
                currentScreen = "Home"
            },
            onBack = { currentScreen = "Home" }
        )
    }
}

@Composable
fun HomeScreen(
    onNavigateToAdd: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToEdit: () -> Unit,
    onNavigateToDelete: () -> Unit
) {
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
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToEdit) {
                Text("Редактировать фильм")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onNavigateToDelete) {
                Text("Удалить фильм")
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

@Composable
fun EditMovieScreen(onMovieEdited: (String, String) -> Unit, onBack: () -> Unit) {
    var oldMovieName by remember { mutableStateOf(TextFieldValue("")) }
    var newMovieName by remember { mutableStateOf(TextFieldValue("")) }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Введите название фильма для редактирования")
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = oldMovieName,
                onValueChange = { oldMovieName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text("Введите новое название фильма")
            Spacer(modifier = Modifier.height(8.dp))
            BasicTextField(
                value = newMovieName,
                onValueChange = { newMovieName = it },
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(0.8f)
                    .height(50.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (oldMovieName.text.isNotBlank() && newMovieName.text.isNotBlank()) {
                    onMovieEdited(oldMovieName.text, newMovieName.text)
                }
            }) {
                Text("Редактировать")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onBack) {
                Text("Назад")
            }
        }
    }
}

@Composable
fun DeleteMovieScreen(movieList: List<String>, onMovieDeleted: (String) -> Unit, onBack: () -> Unit) {
    var selectedMovie by remember { mutableStateOf("") }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text("Выберите фильм для удаления")
            Spacer(modifier = Modifier.height(8.dp))
            movieList.forEach { movie ->
                Button(
                    onClick = { selectedMovie = movie },
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(movie)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                if (selectedMovie.isNotEmpty()) {
                    onMovieDeleted(selectedMovie)
                }
            }) {
                Text("Удалить выбранный фильм")
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
