package com.example.kotlin_todo_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class TodoList : Fragment() {
    lateinit var db: AppDatabase

    override fun onAttach(context: Context) {
        super.onAttach(context)

        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "tododb")
            .allowMainThreadQueries()
            .build()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_todo_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val todos: RecyclerView = view.findViewById(R.id.listView)
        todos.layoutManager = LinearLayoutManager(context)
        todos.addItemDecoration(
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        )

        val adapter = ListAdapter(db.todoDao())
        todos.adapter = adapter

        val newTodo: TextView = view.findViewById(R.id.newTodo)
        val addButton: Button = view.findViewById(R.id.add)

        addButton.setOnClickListener {
            adapter.add(newTodo.text.toString())
            hideKeyboard()
        }
    }

    private fun hideKeyboard() {
        val inputManager: InputMethodManager = this.requireActivity()
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        val currentFocusedView = this.requireActivity().currentFocus
        if (currentFocusedView != null) {
            inputManager.hideSoftInputFromWindow(
                currentFocusedView.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}