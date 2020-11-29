package com.codingacademy.scheduler.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.codingacademy.scheduler.Task
import java.util.*

@Dao
interface TaskDao {

    @Query("SELECT * FROM Task")
    fun getTasks(): LiveData<List<Task>>

    @Query("SELECT * FROM Task WHERE status=(:status_no)")
    fun getTask(status_no: Int): LiveData<List<Task>>

    @Update
    fun updateTask(Task: Task)

    @Insert
    fun addTask(Task: Task)
}