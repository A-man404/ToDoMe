package com.example.todo.screens

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.todo.TodoScreen
import com.example.todo.viewModel.MainViewModel


@Composable
fun MainScreen(navController: NavController, mainViewModel: MainViewModel) {
    val context = LocalContext.current
    val savedName = getUserName(context)
    val isFirstTimeUser = isFirstTimeUser(context)
    if (savedName.isNotEmpty() && savedName != "Guest" && !isFirstTimeUser) {
        TodoScreen(mainViewModel, navController, savedName)
    } else {
        firstTimeUserNameScreen {
            addUserNameToSharedPreference(context, it.trim())
            markUserAsNotFirstTime(context)
            navController.navigate(TodoScreen)

        }
    }

}


fun addUserNameToSharedPreference(context: Context, userName: String) {
    val sharedPreferences = context.getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
    val sharedPreferencesEditor = sharedPreferences.edit()
    sharedPreferencesEditor.putString("username", userName)
    sharedPreferencesEditor.apply()

}

fun getUserName(context: Context): String {
    val sharedPreferences = context.getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("username", "") ?: ""
}

fun markUserAsNotFirstTime(context: Context) {
    val sharedPreferences = context.getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
    val sharedPreferencesEditor = sharedPreferences.edit()
    sharedPreferencesEditor.putBoolean(
        "isFirstTimeUser",
        false
    ) // Mark user as not a first-time user
    sharedPreferencesEditor.apply()
}

fun isFirstTimeUser(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("UsersPrefs", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean(
        "isFirstTimeUser",
        true
    ) // Default to true for first-time users
}