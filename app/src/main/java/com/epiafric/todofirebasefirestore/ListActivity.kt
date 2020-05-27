package com.epiafric.todofirebasefirestore

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.SimpleCallback
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        val viewModel = ViewModelProvider(this).get(ListActivityViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(this)
        viewModel.todoListItems.observe(this, Observer {
            val options = FirestoreRecyclerOptions.Builder<TodoData>()
                .setQuery(it, TodoData::class.java).setLifecycleOwner { lifecycle }.build()
            val adapter = TodoAdapter(options){snapshot ->
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("todoItemId", snapshot.reference.path)
                startActivity(intent)
            }
            recyclerView.adapter = adapter
            val touchHelperCallback =
                object : SimpleCallback(0, ItemTouchHelper.RIGHT
                ) {
                    override fun onMove(
                        recyclerView: RecyclerView,
                        viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder
                    ): Boolean {
                        return false
                    }

                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        if (direction ==ItemTouchHelper.RIGHT  ) {
                            adapter.delete(viewHolder.adapterPosition)
                        }

//                            MaterialAlertDialogBuilder(this@ListActivity).setMessage("Do you want to delete this Item?")
//                                .setPositiveButton("Yes") { _: DialogInterface, _: Int ->
//                                    DialogInterface.OnClickListener { _, _ -> adapter.delete(viewHolder.adapterPosition) }
//                                }.setNegativeButton("No") { _: DialogInterface, _: Int ->
//                                    DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() }
//                                }.show()}

                    }
                }

            val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)



        })
        fab.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

    }
}
