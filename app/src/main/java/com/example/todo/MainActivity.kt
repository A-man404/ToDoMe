package com.example.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.ui.platform.LocalContext
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.todo.repository.Repository
import com.example.todo.room.RoomDatabase
import com.example.todo.screens.AddEditTodoScreen
import com.example.todo.screens.MainScreen
import com.example.todo.screens.SettingsScreen
import com.example.todo.screens.TodoScreen
import com.example.todo.ui.theme.TodoTheme
import com.example.todo.viewModel.MainViewModel
import kotlinx.serialization.Serializable

// APP NAME: TODOME


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            TodoTheme {


                val navController = rememberNavController()
                val context = LocalContext.current
                val roomDatabase = RoomDatabase.getInstance(context)
                val repository = Repository(roomDatabase)
                val mainViewModel = MainViewModel(repository)




                NavHost(
                    navController,
                    startDestination = MainScreen,
                    enterTransition = {
                        fadeIn()
                    },
                    exitTransition = {
                        fadeOut()
                    }
                ) {
                    composable<MainScreen> {
                        MainScreen(navController, mainViewModel)
                    }
                    composable<SettingsScreen> {
                        SettingsScreen(mainViewModel, navController)
                    }
                    composable<AddEditScreen> {
                        AddEditTodoScreen(navController, mainViewModel)
                    }
                    composable<TodoScreen> {
                        TodoScreen(mainViewModel, navController, "")
                    }


                }
            }
        }
    }
}


@Serializable
object MainScreen

@Serializable
object SettingsScreen

@Serializable
object AddEditScreen

@Serializable
object TodoScreen



