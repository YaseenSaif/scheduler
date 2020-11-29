package com.codingacademy.scheduler

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.text.DateFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val TAG = "CrimeListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [GeneralFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GeneralFragment : Fragment(),InputTaskDialog.Callbacks {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null
    private lateinit var addButton: FloatingActionButton
    private lateinit var crimeRecyclerView: RecyclerView
    private var adapter: CrimeAdapter = CrimeAdapter(emptyList())
    private val taskDetailViewModel: TaskListViewModel by lazy {
        ViewModelProviders.of(this).get(TaskListViewModel::class.java)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.fragment_general, container, false)
        crimeRecyclerView =
            view.findViewById(R.id.task_recycler_view) as RecyclerView
        crimeRecyclerView.layoutManager = LinearLayoutManager(context)
        crimeRecyclerView.adapter = adapter
        addButton = view.findViewById(R.id.addBtn)
        addButton.setOnClickListener {
            InputTaskDialog.newInstance().apply {
                setTargetFragment(this@GeneralFragment, 0)
                show(this@GeneralFragment.requireFragmentManager(), "Input")
            }
        }


        return view
    }

    override fun onStart() {
        super.onStart()
        if (param1 == 0) {
            taskDetailViewModel.task0ListLiveData.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got crimeLiveData ${tasks.size}")
                    updateUI(tasks)
                }
            })
        }
        if (param1 == 1) {
            taskDetailViewModel.task1ListLiveData.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got crimeLiveData ${tasks.size}")
                    updateUI(tasks)
                }
            })
        }
        if (param1 == 2) {
            taskDetailViewModel.task2ListLiveData.observe(viewLifecycleOwner, Observer { tasks ->
                tasks?.let {
                    Log.i(TAG, "Got crimeLiveData ${tasks.size}")
                    updateUI(tasks)
                }
            })
        }
    }

    private fun updateUI(tasks: List<Task>) {
        adapter.let {
            it.tasks = tasks
        }
        crimeRecyclerView.adapter = adapter
    }

    private inner class TaskHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var task: Task
        private var titleField: TextView = itemView.findViewById(R.id.task_title)
        private var dateField: TextView = itemView.findViewById(R.id.task_date)
        private var button1: Button = itemView.findViewById(R.id.button_1)
        private var button2: Button = itemView.findViewById(R.id.button_2)
        fun getDays(daysAgo: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

            return calendar.time
        }
        fun checkTaskStatus() {

            if (param1 == 0) {
                if(task.deadline.time < Date().time){
                this.itemView.setBackgroundResource(R.color.red)
                }
                else if (getDays(3) > task.deadline){
                    this.itemView.setBackgroundResource(R.color.lightOrange)
                }
                this.button2.setText(R.string.done_btn)
                this.button1.setText(R.string.in_progress_btn)
            } else if (param1 == 1) {
                this.itemView.setBackgroundResource(R.color.lightYellow)
                this.button2.setText(R.string.done_btn)
                this.button1.setText(R.string.to_do_btn)
            } else if (param1 == 2) {
                this.itemView.setBackgroundResource(R.color.lightGreen)
                this.button1.setText(R.string.in_progress_btn)
                this.button2.setText(R.string.to_do_btn)
            }
        }

        fun bind(task: Task) {
            this.task = task
            checkTaskStatus()
            titleField.text = this.task.title
            dateField.text = DateFormat.getDateInstance().format(this.task.deadline)
            button1.setOnClickListener {
                if(param1 == 0)
                this.task.status = 1
                else if (param1 == 1)
                this.task.status =0
                else if(param1 == 2)
                this.task.status =1
                taskDetailViewModel.updateTask(this.task)
            }
            button2.setOnClickListener {
                if(param1 == 0)
                    this.task.status = 2
                else if (param1 == 1)
                    this.task.status =2
                else if(param1 == 2)
                    this.task.status =0
                taskDetailViewModel.updateTask(this.task)
            }
        }
    }


    private inner class CrimeAdapter(var tasks: List<Task>) :
        RecyclerView.Adapter<TaskHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
                : TaskHolder {
            val layoutInflater = LayoutInflater.from(context)
            val view = layoutInflater.inflate(R.layout.list_item_task, parent, false)
            return TaskHolder(view)
        }

        override fun onBindViewHolder(holder: TaskHolder, position: Int) {
            val task = tasks[position]
            holder.bind(task)
        }

        override fun getItemCount() = tasks.size
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FirstFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            GeneralFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onTaskAdd(task: Task) {
        taskDetailViewModel.addTask(task)
    }

}