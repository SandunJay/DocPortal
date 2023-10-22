package com.sliit.docportal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sliit.docportal.fragments.RegisterPatient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.sliit.docportal.fragments.SignIn
import com.sliit.docportal.fragments.Signup



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            // If no saved instance state, initialize with the Signup fragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SignIn())
                .commit()
        }


        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, SignIn())
        fragmentTransaction.commit()


//        val fragmentTransactionlogin = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer, SignIn())
//        fragmentTransaction.commit()
//
//
//        val fragmentTransactionCourse = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.fragmentContainer, RegisterPatient())
//        fragmentTransaction.commit()


    }

//    private  lateinit var adapter:TodoAdapter
//    private lateinit var viewModel: MainActivityData
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        val recyclerView:RecyclerView = findViewById(R.id.rvToDoList)
//        val repository = TodoRepository(TodoDatabase.getInstance(this))
//
//        viewModel = ViewModelProvider(this)[MainActivityData::class.java]
//
//        viewModel.data.observe(this){
//            adapter = TodoAdapter(it, repository, viewModel)
//            recyclerView.adapter = adapter
//            recyclerView.layoutManager = LinearLayoutManager(this)
//        }
//
//        CoroutineScope(Dispatchers.IO).launch {
//            val data = repository.getAllTodoItems()
//            runOnUiThread{
//                viewModel.setData(data)
//            }
//        }
//        val addItem: Button = findViewById(R.id.btnAddPatient)
//
//        addItem.setOnClickListener{
//            displayDialog(repository)
//        }
//    }
//
//    fun displayDialog(repository: TodoRepository) {
//        val builder = AlertDialog.Builder(this)
//
//        builder.setTitle(getText(R.string.alertTitle))
//        builder.setMessage("Enter the todo item below: ")
//
//        val input = EditText(this)
//        input.inputType = InputType.TYPE_CLASS_TEXT
//        builder.setView(input)
//
//        builder.setPositiveButton("OK"){dialog,which->
//            val item = input.text.toString()
//            CoroutineScope(Dispatchers.IO).launch {
//                repository.insert(Todo(item))
//            }
//        }
//        builder.setNegativeButton("Cancel"){dialog,which->
//            dialog.cancel()
//        }
//        val alertDialog = builder.create()
//        alertDialog.show()
//    }
}