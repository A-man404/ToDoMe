package com.example.todo.repository

import com.example.todo.room.Entity
import com.example.todo.room.RoomDatabase

class Repository(val roomDatabase: RoomDatabase) {

    suspend fun addTodoToRoom(entity: Entity) {
        roomDatabase.getDao().insertTodo(entity)
    }

    suspend fun deleteTodoFromRoom(entity: Entity) {
        roomDatabase.getDao().deleteTodo(entity)
    }

    suspend fun updateTodoFromRoom(entity: Entity) {
        roomDatabase.getDao().updateTodo(entity)
    }

    fun getAllTodos() = roomDatabase.getDao().getTodoList()

    suspend fun addToCompletedTasks(entity: Entity){
        roomDatabase.getDao().addCompletedTaskTodo(entity = entity)
    }
    suspend fun deleteCompletedFromRoom(entity: Entity) {
        roomDatabase.getDao().deleteTodoFromCompleted(entity)
    }

    fun getAllCompletedTasks() = roomDatabase.getDao().getCompletedTasks()


    suspend fun deleteAllData() {
        roomDatabase.getDao().deleteAllData()
    }

}