package com.example.kotlin_todo_list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(val todoDao : TodoDao)
    : RecyclerView.Adapter<ListAdapter.TodoHolder>() {

    val todos: MutableList<Todo>
    init {
        todos = todoDao.getAll().toMutableList()
    }

    class TodoHolder(v: View, val todos: MutableList<Todo>)
        : RecyclerView.ViewHolder(v) {

        val title: TextView = v.findViewById(R.id.title)
        val delete: Button = v.findViewById(R.id.delete)

        init {
            title.setOnClickListener(::details)
        }

        private fun details(v: View) {
            val todo = todos[adapterPosition]

            val args = Bundle()
            args.putLong("todoID", todo.id)

            v.findNavController().navigate(R.id.details_list, args)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_todo_list_item, parent, false)

        return TodoHolder(view, todos)
    }

    override fun onBindViewHolder(holder: TodoHolder, position: Int) {
        holder.title.text = todos[position].title

        holder.delete.setOnClickListener {
            delete(position)
        }
    }

    override fun getItemCount() = todos.size

    fun delete(position: Int) {
        val todo = todos[position]

        todoDao.delete(todo)
        todos.remove(todo)

        notifyItemRemoved(position)
        notifyItemRangeChanged(position, itemCount - position)
    }

    fun add(title: String) {
        val todo = Todo(title)

        todo.id = todoDao.insert(todo)
        todos.add(todo)
        notifyItemInserted(0)
        notifyItemRangeChanged(0, itemCount)
    }

}