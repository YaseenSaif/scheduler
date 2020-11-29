package com.codingacademy.scheduler

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.new_dialog_task.*
import java.text.DateFormat
import java.util.*

class InputTaskDialog : DialogFragment(), DatePickerFragment.Callbacks {
    private lateinit var title: EditText
    private lateinit var detail: EditText
    private lateinit var deadLineBtn: Button
    var deadLine = Date()
    override fun onDateSelected(date: Date) {
        this.deadLine = date
        updateUI()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val v = activity?.layoutInflater?.inflate(R.layout.new_dialog_task, null)
        title = v?.findViewById(R.id.task_title) as EditText
        detail = v.findViewById(R.id.task_details) as EditText
        deadLineBtn = v.findViewById(R.id.deadline_btn) as Button
        deadLineBtn.setOnClickListener {
            DatePickerFragment.newInstance(Date()).apply {
                setTargetFragment(this@InputTaskDialog, 0)
                show(this@InputTaskDialog.requireFragmentManager(), "Input")
            }
        }
        return AlertDialog.Builder(requireContext(), R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(v)
            .setPositiveButton("add") { dialog, _ ->
                val s = Task(
                    UUID.randomUUID(),
                    title.text.toString(),
                    detail.text.toString(),
                    deadLine
                )
                targetFragment?.let {
                    (it as Callbacks).onTaskAdd(s)
                }

            }
            .setNegativeButton("cancel") { dialog, _ ->
                dialog.cancel()

            }.create()
    }

    interface Callbacks {
        fun onTaskAdd(task: Task)
        fun onStudentDelete(position: Int)
    }

    private fun updateUI() {
        deadLineBtn.text = DateFormat.getDateInstance().format(deadLine)
    }

    companion object {
        fun newInstance(): InputTaskDialog {

            return InputTaskDialog()
        }
    }


}