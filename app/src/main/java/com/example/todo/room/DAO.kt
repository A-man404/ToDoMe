package com.example.todo.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface DAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(entity: Entity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCompletedTaskTodo(entity: Entity)

    @Delete
    suspend fun deleteTodo(entity: Entity)

    @Delete
    suspend fun deleteTodoFromCompleted(entity: Entity)

    @Update
    suspend fun updateTodo(entity: Entity)

    @Query("SELECT * FROM todo_table WHERE isChecked = 0")
    fun getTodoList(): Flow<List<Entity>>

    @Query("SELECT * FROM todo_table WHERE isChecked = 1")
    fun getCompletedTasks(): Flow<List<Entity>>


    @Query("DELETE FROM todo_table")
    suspend fun deleteAllData()


}