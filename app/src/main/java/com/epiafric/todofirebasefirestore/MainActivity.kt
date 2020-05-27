package com.epiafric.todofirebasefirestore

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)

        val intent = intent.getStringExtra("todoItemId")
        if (intent  != null){
            viewModel.getDocumentFirestone(intent).observe(this, Observer {
                tittle.setText(it.tittle)
                message.setText(it.message)
            })

            saveButton.text = "Update"
        }






        status()

        saveButton.setOnClickListener {
            if(intent == null)
           { viewModel.save(tittle.text.toString(), message.text.toString())}
            else{
                viewModel.update(tittle.text.toString(), message.text.toString(), intent)
            }
        }
    }

    private fun status(){
        viewModel.status.observe(this, Observer {
            if (it == true){
                Toast.makeText(this, "Saved Successfully", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this, "Not Successfully", Toast.LENGTH_SHORT).show()
        })
    }
}
