package com.example.kotlin_todo_list

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.room.Room

class TodoDetails : Fragment() {

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
        return inflater.inflate(R.layout.fragment_todo_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dao = db.todoDao()
        val todoID = arguments?.getLong("todoID")

        if(todoID != null) {
            val todo = dao.get(todoID)

            val title: TextView = view.findViewById(R.id.title)
            val new_todo_title : TextView = view.findViewById(R.id.new_todo_title)

            title.text = "Editando ".plus(todo.title)
            new_todo_title.text = todo.title

            val saveButton: Button = view.findViewById(R.id.save)
            saveButton.setOnClickListener {
                val todo_title = new_todo_title.text.toString()

                dao.update(todoID, todo_title)
                view.findNavController().navigate(R.id.todoList)
            }
        }
    }
}