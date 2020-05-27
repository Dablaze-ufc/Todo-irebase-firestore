package com.epiafric.todofirebasefirestore

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivityViewModel : ViewModel() {
    private val db = Firebase.firestore
    private val _status = MutableLiveData<Boolean>()
    val status: LiveData<Boolean>
        get() = _status
    private val item = MutableLiveData<TodoData>()

    fun save(tittle: String, message: String) {
        val todo = hashMapOf<String, String>("tittle" to tittle, "message" to message)

        db.collection("todoListItems")
            .add(todo).addOnSuccessListener {
                Log.d("ViewModel", "onSuccess: ${it.id}")
                _status.value = true
            }
            .addOnFailureListener {
                Log.d("ViewModel", "onSuccess: ${it.localizedMessage}")
                _status.value = false
            }
    }

    fun getDocumentFirestone(path : String) : LiveData<TodoData>{
        db.document(path).get().addOnSuccessListener {documentSnapshot ->
            if (documentSnapshot != null){
                 item.value = TodoData(documentSnapshot.getString("tittle"), documentSnapshot.getString("message"))
            }

        }
        return item
    }

    fun update(tittle: String, message: String, path: String) {
        val todoItem = TodoData(tittle, message)
        db.document(path).set(todoItem).addOnSuccessListener{
            _status.value = true
        }.addOnFailureListener {
            _status.value = false
        }

    }
}