package com.codingacademy.scheduler

import android.app.Application

class SchedulerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        TaskRepository.initialize(this)
    }
}