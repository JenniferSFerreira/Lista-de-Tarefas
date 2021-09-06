package com.example.listadetarefas.ui

import android.os.Bundle
import android.util.Log
import android.util.TimeFormatException
import androidx.appcompat.app.AppCompatActivity
import com.example.listadetarefas.databinding.ActivityAddTaskBinding
import com.example.listadetarefas.datasource.TaskDataSource
import com.example.listadetarefas.extensions.format
import com.example.listadetarefas.extensions.text
import com.example.listadetarefas.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class AddTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        insertListeners()

    }
    private fun insertListeners() {
        binding.date.editText?.setOnClickListener {
           val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {

                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.date.text = Date(it + offset).format()

            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }

        binding.hour.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H).build()

            timePicker.addOnPositiveButtonClickListener {
                binding.hour.text = "${timePicker.hour}:${timePicker.minute}"
            }
            timePicker.show(supportFragmentManager, null)
        }
        binding.cancelButton.setOnClickListener {
            finish()
        }

        binding.btnNewTask.setOnClickListener {
            val task = Task(
                title = binding.title.text,
                date = binding.date.text,
                hour = binding.hour.text,
            )
            TaskDataSource.insertTask(task)
            Log.e("TAG", "insertListeners: " + TaskDataSource.getList() )
        }
    }

}