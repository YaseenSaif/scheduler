package com.codingacademy.scheduler

import androidx.lifecycle.ViewModel

class TaskListViewModel : ViewModel() {

    private val taskRepository = TaskRepository.get()
    val taskListLiveData = taskRepository.getTasks()
    val task0ListLiveData = taskRepository.getTask(0)
    val task1ListLiveData = taskRepository.getTask(1)
    val task2ListLiveData = taskRepository.getTask(2)


    fun addTask(task:Task){
        TaskRepository.get().addTask(task)
    }
    fun updateTask(task:Task){
        TaskRepository.get().updateTask(task)
    }
}