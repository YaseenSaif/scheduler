package com.codingacademy.scheduler

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Task(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "Doing Home Work",
                 var details: String = "",
                 var deadline: Date = Date(),
                 var status: Int = 0)