package com.example.todo.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.R
import com.example.todo.room.Entity
import com.example.todo.ui.theme.BG
import com.example.todo.ui.theme.Desc
import com.example.todo.ui.theme.MainBg
import com.example.todo.ui.theme.TaskBg
import com.example.todo.ui.theme.Title
import com.example.todo.viewModel.MainViewModel

@Composable
fun AddEditTodoScreen(navController: NavController, mainViewModel: MainViewModel) {

    val iceland = FontFamily(Font(R.font.iceland))
    val lato = FontFamily(Font(R.font.lato))
    val context = LocalContext.current

    val isCategoryDropDownOpen = remember { mutableStateOf(false) }
    val isPriorityDropDownOpen = remember { mutableStateOf(false) }

    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val selectedCategory = remember { mutableStateOf("Work") } // Default category
    val categories = listOf("Work", "Personal", "Shopping", "Study", "Others")

    val selectedPriority = remember { mutableStateOf("Low") }
    val priorityCategories = listOf("Low", "Medium", "High")

    val priorityInNumber = remember { mutableStateOf(3) }

    when (selectedPriority.value) {
        "High" -> {
            priorityInNumber.value = 1
        }

        "Medium" -> {
            priorityInNumber.value = 2
        }

        "Low" -> {
            priorityInNumber.value = 3
        }
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (title.value.isNotEmpty() && description.value.isNotEmpty() && title.value.isNotBlank() && description.value.isNotBlank()) {
                    val item = Entity(
                        title = title.value.trim(),
                        desc = description.value.trim(),
                        isChecked = false,
                        tag = selectedCategory.value,
                        priority = priorityInNumber.value
                    )
                    mainViewModel.addTodo(item)
                    navController.popBackStack()
                } else {
                    Toast.makeText(context, "Please enter all the details", Toast.LENGTH_SHORT)
                        .show()
                }
            },
            shape = RoundedCornerShape(56.dp),
            containerColor = BG,
            contentColor = Desc
        ) {
            Icon(imageVector = Icons.Default.Check, contentDescription = null)
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBg)
                .padding(20.dp)
                .padding(paddingValues)
        ) {
            Text(
                "Item Info", style = TextStyle(
                    fontSize = 48.sp, color = Title, fontFamily = iceland
                )
            )
            Spacer(Modifier.height(32.dp))

            TextField(
                value = title.value,
                textStyle = TextStyle(fontSize = 20.sp, fontFamily = lato),
                onValueChange = { title.value = it },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter the title", fontSize = 20.sp) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Title,
                    unfocusedTextColor = Title,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray
                )
            )
            Spacer(Modifier.height(32.dp))
            TextField(
                value = description.value,
                textStyle = TextStyle(fontSize = 20.sp, fontFamily = lato),
                onValueChange = { description.value = it },
                singleLine = false,
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter the description", fontSize = 20.sp) },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedTextColor = Title,
                    unfocusedTextColor = Title,
                    unfocusedIndicatorColor = Color.Gray,
                    focusedIndicatorColor = Color.Gray
                )
            )
            Spacer(Modifier.height(40.dp))

            // Row for category and priority dropdowns
            Row(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween, // Space between items
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Category Dropdown
                Column(modifier = Modifier.clickable { isCategoryDropDownOpen.value = true }) {
                    Text(
                        "Category",
                        style = TextStyle(fontSize = 16.sp, color = Desc, fontFamily = iceland)
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(TaskBg, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            selectedCategory.value,
                            style = TextStyle(color = Desc, fontFamily = lato, fontSize = 16.sp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Desc,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier.background(TaskBg),
                        expanded = isCategoryDropDownOpen.value,
                        onDismissRequest = { isCategoryDropDownOpen.value = false }) {
                        categories.forEach { category ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedCategory.value = category
                                    isCategoryDropDownOpen.value = false
                                },
                                text = {
                                    Text(
                                        category,
                                        style = TextStyle(fontSize = 16.sp, color = Desc)
                                    )
                                })
                        }
                    }
                }

                // Priority Dropdown
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.clickable { isPriorityDropDownOpen.value = true }) {
                    Text(
                        "Priority",
                        style = TextStyle(fontSize = 16.sp, color = Desc, fontFamily = iceland)
                    )
                    Spacer(Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                            .clip(
                                RoundedCornerShape(8.dp)
                            )
                            .background(TaskBg, shape = RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Text(
                            selectedPriority.value,
                            style = TextStyle(color = Desc, fontFamily = lato, fontSize = 16.sp)
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = Desc,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    DropdownMenu(
                        modifier = Modifier.background(TaskBg),
                        expanded = isPriorityDropDownOpen.value,
                        onDismissRequest = { isPriorityDropDownOpen.value = false }) {
                        priorityCategories.forEach { priority ->
                            DropdownMenuItem(
                                onClick = {
                                    selectedPriority.value = priority
                                    isPriorityDropDownOpen.value = false
                                },
                                text = {
                                    Text(
                                        priority,
                                        style = TextStyle(fontSize = 16.sp, color = Desc)
                                    )
                                })
                        }
                    }
                }
            }
        }
    }
}
