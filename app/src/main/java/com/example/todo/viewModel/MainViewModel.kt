package com.example.todo.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.repository.Repository
import com.example.todo.room.Entity
import kotlinx.coroutines.launch


class MainViewModel(val repository: Repository) : ViewModel() {

    fun addTodo(entity: Entity) {
        viewModelScope.launch { repository.addTodoToRoom(entity) }
    }

    fun deleteTodo(entity: Entity) {
        viewModelScope.launch {
            repository.deleteTodoFromRoom(entity)
        }
    }

    fun updateTodo(entity: Entity) {
        viewModelScope.launch {
            repository.updateTodoFromRoom(entity)
        }
    }


    val list = repository.getAllTodos()

    fun addtoCompletedTask(entity: Entity) {
        viewModelScope.launch {
            repository.addToCompletedTasks(entity)
        }
    }

    fun deleteFromCompletedTasks(entity: Entity) {
        viewModelScope.launch {
            repository.deleteCompletedFromRoom(entity) // Remove from completed tasks list
        }
    }

    val completedTasks = repository.getAllCompletedTasks()

    fun deleteEverything() {
        viewModelScope.launch { repository.deleteAllData() }
    }

}