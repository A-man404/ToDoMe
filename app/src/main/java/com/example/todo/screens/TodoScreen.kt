package com.example.todo.screens

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.AddEditScreen
import com.example.todo.R
import com.example.todo.SettingsScreen
import com.example.todo.room.Entity
import com.example.todo.ui.theme.BG
import com.example.todo.ui.theme.Desc
import com.example.todo.ui.theme.HighPriority
import com.example.todo.ui.theme.LowPriority
import com.example.todo.ui.theme.MainBg
import com.example.todo.ui.theme.MediumPriority
import com.example.todo.ui.theme.TaskBg
import com.example.todo.ui.theme.Title
import com.example.todo.viewModel.MainViewModel

@Composable
fun TodoScreen(mainViewModel: MainViewModel, navController: NavController, name: String) {


    val selectedItem = remember { mutableStateOf(0) }
    val navigationItem = listOf("Tasks", "Completed")

    val iceland = FontFamily(
        Font(R.font.iceland)
    )
    val items by mainViewModel.list.collectAsState(initial = emptyList())
    val completedItems by mainViewModel.completedTasks.collectAsState(initial = emptyList())

    val listToShow = remember { mutableStateOf(0) }



    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = { navController.navigate(AddEditScreen) },
            shape = RoundedCornerShape(56.dp),
            containerColor = BG,
            contentColor = Desc
        ) {
            Icon(imageVector = Icons.Default.Add, contentDescription = null)
        }
    }, bottomBar = {
        NavigationBar(containerColor = TaskBg, modifier = Modifier.height(70.dp)) {
            navigationItem.forEachIndexed { index, item ->
                NavigationBarItem(
                    colors = NavigationBarItemColors(
                        selectedIconColor = Title,
                        selectedTextColor = Title,
                        selectedIndicatorColor = MainBg,
                        unselectedIconColor = Desc,
                        unselectedTextColor = Desc,
                        disabledIconColor = Desc,
                        disabledTextColor = Desc
                    ),
                    selected = selectedItem.value == index,
                    onClick = {
                        selectedItem.value = index
                        when (item) {
                            "Tasks" -> listToShow.value = 0
                            "Completed" -> listToShow.value = 1
                            else -> Icons.Default.Home
                        }
                    },
                    icon = {
                        Icon(
                            imageVector = when (item) {
                                "Tasks" -> Icons.AutoMirrored.Default.List
                                "Completed" -> Icons.Default.CheckCircle
                                else -> Icons.Default.Home
                            },
                            contentDescription = item,
                            tint = Title,

                            )
                    },
                    //label = { Text(item) },
                )
            }
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MainBg)
                .padding(paddingValues)
                .padding(horizontal = 20.dp)
        ) {
            if (listToShow.value == 0) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(

                        "Hello ${name}!!", style = TextStyle(
                            fontSize = 48.sp, color = Title, fontFamily = iceland
                        )
                    )
                    IconButton(onClick = { navController.navigate(SettingsScreen) }) {
                        Icon(
                            painterResource(R.drawable.tune),
                            contentDescription = null,
                            tint = Desc,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        //.padding(paddingValues)
                        .background(MainBg)
                ) {
                    items(items) {
                        TodoItem(it, mainViewModel)
                        Spacer(Modifier.height(16.dp))
                    }
                }
            } else if (listToShow.value == 1) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(

                        "Completed Tasks", style = TextStyle(
                            fontSize = 48.sp, color = Title, fontFamily = iceland
                        )
                    )
                    IconButton(onClick = { navController.navigate(SettingsScreen) }) {
                        Icon(
                            painterResource(R.drawable.tune),
                            contentDescription = null,
                            tint = Desc,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MainBg)
                ) {
                    items(completedItems) {
                        TodoItem(it, mainViewModel)
                        Spacer(Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun TodoItem(
    entity: Entity,
    mainViewModel: MainViewModel
) {
    val iceland = FontFamily(
        Font(R.font.iceland)
    )
    val lato = FontFamily(
        Font(R.font.lato)
    )
    val decoration = if (entity.isChecked) TextDecoration.LineThrough else TextDecoration.None


    fun flagColor(): Color {
        return when (entity.priority) {
            1 -> HighPriority
            2 -> MediumPriority
            else -> LowPriority
        }
    }

    val isBottomSheet = remember { mutableStateOf(false) }

    Card(
        onClick = {
            isBottomSheet.value = true

        },
        modifier = Modifier
            .wrapContentHeight()
            .width(400.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = TaskBg)
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .background(TaskBg)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    entity.title, style = TextStyle(
                        fontSize = 32.sp,
                        color = Title,
                        fontFamily = iceland,
                        textDecoration = decoration
                    ), overflow = TextOverflow.Ellipsis, maxLines = 4
                )
                Checkbox(
                    checked = entity.isChecked,
                    onCheckedChange = {
                        if (it) {
                            val updatedEntity = entity.copy(isChecked = true)
                            mainViewModel.addtoCompletedTask(updatedEntity)
                        } else {
                            val updatedEntity = entity.copy(isChecked = false)
                            mainViewModel.addTodo(updatedEntity)
                        }


                    },
                    modifier = Modifier
                        .size(24.dp)
                        .padding(horizontal = 16.dp),
                    colors = CheckboxDefaults.colors(
                        checkedColor = BG, uncheckedColor = Desc, checkmarkColor = Title
                    )
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                entity.desc, style = TextStyle(
                    fontSize = 16.sp, color = Desc, fontFamily = lato, textDecoration = decoration
                ), overflow = TextOverflow.Ellipsis, maxLines = 4
            )
            Spacer(Modifier.height(32.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        entity.tag, modifier = Modifier
                            .background(
                                Color(0xff333333), shape = RoundedCornerShape(4.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 2.dp), style = TextStyle(
                            fontSize = 12.sp, color = Title, fontFamily = iceland
                        )
                    )
                    Spacer(Modifier.width(32.dp))
                    Icon(
                        painterResource(R.drawable.flag),
                        contentDescription = null,
                        tint = flagColor()
                    )
                }
                Row(
                    modifier = Modifier.wrapContentSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        tint = Desc,
                        modifier = Modifier.clickable {
                            mainViewModel.deleteTodo(entity)

                        })
                }
            }
        }

        EditBottomSheet(entity, isBottomSheet, mainViewModel)
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditBottomSheet(
    entity: Entity,
    isBottomSheet: MutableState<Boolean>,
    mainViewModel: MainViewModel
) {
    if (isBottomSheet.value) {
        ModalBottomSheet(
            containerColor = MainBg,
            onDismissRequest = { isBottomSheet.value = false }) {
            val iceland = FontFamily(Font(R.font.iceland))
            val lato = FontFamily(Font(R.font.lato))

            val isCategoryDropDownOpen = remember { mutableStateOf(false) }
            val isPriorityDropDownOpen = remember { mutableStateOf(false) }

            val selectedCategory = remember { mutableStateOf(entity.tag) } // Default category
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

            val editTitle = remember {
                mutableStateOf(entity.title)
            }
            val editDesc = remember {
                mutableStateOf(entity.desc)
            }
            val id = entity.id



            Scaffold(floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        val item = Entity(
                            id,
                            title = editTitle.value.trim(),
                            desc = editDesc.value.trim(),
                            isChecked = false,
                            tag = selectedCategory.value,
                            priority = priorityInNumber.value
                        )
                        mainViewModel.updateTodo(item)
                        isBottomSheet.value = false
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
                        "Edit item", style = TextStyle(
                            fontSize = 48.sp, color = Title, fontFamily = iceland
                        )
                    )
                    Spacer(Modifier.height(32.dp))

                    TextField(
                        value = editTitle.value,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = lato),
                        onValueChange = { editTitle.value = it },
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
                        value = editDesc.value,
                        textStyle = TextStyle(fontSize = 20.sp, fontFamily = lato),
                        onValueChange = { editDesc.value = it },
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
                        Column(modifier = Modifier.clickable {
                            isCategoryDropDownOpen.value = true
                        }) {
                            Text(
                                "Category",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Desc,
                                    fontFamily = iceland
                                )
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
                                    style = TextStyle(
                                        color = Desc,
                                        fontFamily = lato,
                                        fontSize = 16.sp
                                    )
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
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    color = Desc,
                                    fontFamily = iceland
                                )
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
                                    style = TextStyle(
                                        color = Desc,
                                        fontFamily = lato,
                                        fontSize = 16.sp
                                    )
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
    }

}

