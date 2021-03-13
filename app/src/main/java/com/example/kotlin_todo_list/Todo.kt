package com.example.kotlin_todo_list

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(val title: String) {
    @PrimaryKey(autoGenerate = true)
    var id : Long = 0
}