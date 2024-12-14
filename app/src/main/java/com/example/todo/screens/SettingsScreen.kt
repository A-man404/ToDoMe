package com.example.todo.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.R
import com.example.todo.TodoScreen
import com.example.todo.ui.theme.Desc
import com.example.todo.ui.theme.MainBg
import com.example.todo.ui.theme.Title
import com.example.todo.viewModel.MainViewModel


@Composable
fun SettingsScreen(mainViewModel: MainViewModel, navController: NavController) {

    val iceland = FontFamily(
        Font(R.font.iceland)
    )
    val lato = FontFamily(
        Font(R.font.lato)
    )

    val context = LocalContext.current

    val resetAlertBox = remember { mutableStateOf(false) }
    val newNameAlertBox = remember { mutableStateOf(false) }

    Scaffold { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBg)
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(20.dp)
            ) {
                Text(
                    "Settings", style = TextStyle(
                        fontSize = 48.sp, color = Title, fontFamily = iceland
                    )
                )

                Spacer(Modifier.height(40.dp))

                Text("Change your name", style = TextStyle(
                    fontSize = 24.sp,
                    color = Desc,
                    fontFamily = lato,
                ), modifier = Modifier.clickable {
                    newNameAlertBox.value = !newNameAlertBox.value
                })
                Spacer(Modifier.height(32.dp))

                Text("Reset Everything", style = TextStyle(
                    fontSize = 24.sp,
                    color = Desc,
                    fontFamily = lato,
                ), modifier = Modifier.clickable {
                    resetAlertBox.value = !resetAlertBox.value
                }
                )
                Spacer(Modifier.height(32.dp))

                Text("Support us!", style = TextStyle(
                    fontSize = 24.sp,
                    color = Desc,
                    fontFamily = lato,
                ), modifier = Modifier.clickable { supportMe(context) })

            }
            Text(
                "Version 1.0",
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 32.dp),
                style = TextStyle(fontSize = 20.sp),
                color = Color(0x8F5C5C5D),
                fontFamily = lato
            )
        }

        NewNameDialog(newNameAlertBox, iceland)
        ResetDialog(resetAlertBox, iceland, mainViewModel, navController)
    }

}

@Composable
private fun NewNameDialog(newNameAlertBox: MutableState<Boolean>, iceland: FontFamily) {
    val name = remember { mutableStateOf("") }
    val context = LocalContext.current
    val activity = context as? Activity
    if (newNameAlertBox.value) {
        AlertDialog(onDismissRequest = { newNameAlertBox.value = false },

            shape = RectangleShape, containerColor = MainBg, title = {
                Column {
                    Text(
                        "Enter your new name",
                        style = TextStyle(
                            fontSize = 36.sp,
                            fontFamily = iceland,
                            textAlign = TextAlign.Center,
                            color = Title
                        ),
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        "Note: App will be closed",
                        style = TextStyle(color = Desc, fontFamily = iceland)
                    )
                }
            }, text = {


                val maxWords = 8
                TextField(
                    value = name.value,
                    textStyle = TextStyle(
                        fontSize = 24.sp,
                        fontFamily = iceland,
                    ),
                    onValueChange = {
                        if (it.length <= maxWords) {
                            name.value = it
                        }
                    },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Enter your name", fontSize = 20.sp) },
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedTextColor = Title,
                        unfocusedIndicatorColor = Color.Gray,
                        focusedIndicatorColor = Color.Gray
                    )
                )
            }, confirmButton = {
                IconButton(onClick = {
                    addUserNameToSharedPreference(
                        context,
                        name.value.trim()
                    )
                    newNameAlertBox.value = false
                    activity?.finishAffinity()
                }) {
                    Icon(
                        imageVector = Icons.Default.Check, contentDescription = null, tint = Desc
                    )
                }
            })
    }
}

@Composable
private fun ResetDialog(
    resetAlertBox: MutableState<Boolean>,
    iceland: FontFamily,
    mainViewModel: MainViewModel,
    navController: NavController
) {


    if (resetAlertBox.value) {
        AlertDialog(onDismissRequest = { resetAlertBox.value = false },
            shape = RectangleShape,
            containerColor = MainBg,
            confirmButton = {},
            title = {
                Text(
                    "Reset Everything?",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontFamily = iceland,
                        textAlign = TextAlign.Center,
                        color = Title
                    ),
                )
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Spacer(Modifier.height(40.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {

                        Text("No", style = TextStyle(
                            fontSize = 36.sp,
                            fontFamily = iceland,
                            textAlign = TextAlign.Center,
                            color = Title
                        ), modifier = Modifier.clickable { resetAlertBox.value = false })
                        Text("Yes", style = TextStyle(
                            fontSize = 36.sp,
                            fontFamily = iceland,
                            textAlign = TextAlign.Center,
                            color = Title
                        ),
                            modifier = Modifier.clickable {
                                mainViewModel.deleteEverything()
                                resetAlertBox.value = false
                                navController.popBackStack()


                            })
                    }
                }
            })
    }
}

fun supportMe(context: Context) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://buymeacoffee.com/aman010"))
    context.startActivity(intent)
}
