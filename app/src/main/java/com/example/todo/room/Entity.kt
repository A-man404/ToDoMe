package com.example.todo.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Entity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val desc: String,
    val isChecked: Boolean,
    val tag: String,
    val priority: Int, //1 for high,2 for medium
)