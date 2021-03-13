package com.example.kotlin_todo_list

import androidx.room.*

@Dao
interface TodoDao {
    @Query("SELECT * FROM Todo")
    fun getAll(): List<Todo>

    @Query("SELECT * FROM Todo WHERE id = :id ORDER BY id DESC")
    fun get(id: Long): Todo

    @Insert
    fun insert(todo: Todo): Long

    @Query("UPDATE Todo SET title=:title WHERE id = :id")
    fun update(id: Long, title: String)

    @Delete
    fun delete(todo: Todo)
}
