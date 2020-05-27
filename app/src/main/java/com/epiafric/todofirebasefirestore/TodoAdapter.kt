package com.epiafric.todofirebasefirestore


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.android.synthetic.main.list_item.view.*

class TodoAdapter(options: FirestoreRecyclerOptions<TodoData>, private val listener:  (DocumentSnapshot) -> Unit) : FirestoreRecyclerAdapter<TodoData, TodoAdapter.ViewHolder>(
    options
){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: TodoData) {
        holder.bind(model)


    }

    fun delete(position: Int) {
        snapshots.getSnapshot(position).reference.delete()
    }


      inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(todoItem: TodoData) {
            itemView.textView_tittle.text = todoItem.tittle
            itemView.textView_message.text = todoItem.message
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION){
                   listener.invoke(snapshots.getSnapshot(position))
                }
            }
        }
    }
}
