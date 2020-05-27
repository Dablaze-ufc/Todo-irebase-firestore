package com.epiafric.todofirebasefirestore

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ListActivityViewModel: ViewModel() {

    private val db = Firebase.firestore
    private val listRef = db.collection("todoListItems")
    private var  _todoListItems = MutableLiveData<Query>()
    val todoListItems: LiveData<Query>
    get() = _todoListItems

    private fun getTodoItems(){
      _todoListItems.value = listRef.orderBy("tittle")
    }


    init {
        getTodoItems()
    }
}