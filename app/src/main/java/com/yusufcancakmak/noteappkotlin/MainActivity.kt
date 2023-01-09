package com.yusufcancakmak.noteappkotlin

import android.app.AlertDialog
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yusufcancakmak.noteappkotlin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapterr=AdapterNotes()
        binding.rv.apply {
            layoutManager=LinearLayoutManager(this@MainActivity)
            adapter=adapterr.apply {

            }
        }
        viewModel=ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.readAllNotes.observe(this, Observer { note->
            adapterr.setData(note)
        })
        //fab button onclick item
        binding.fabAddNewNote.setOnClickListener {
            showAddNoteDialog()
        }
        adapterr.OnItemClick ={notes -> showActionDialog(notes) }
    }

    private fun showActionDialog(notes: Notes) {
        val builder =AlertDialog.Builder(this)
        builder.setTitle("Selection Action")
        
        builder.setPositiveButton("Delete"){_,_->
            viewModel.deleteData(notes)
        }
        builder.setNegativeButton("Update"){_,_->
            showUpdateDialog(notes)
        }
        builder.setNeutralButton("Cancel"){_,_->
            Toast.makeText(this,"Cancel",Toast.LENGTH_SHORT).show()
        }


        builder.create().show()
    }

    private fun showUpdateDialog(notes: Notes) {
        //create Dialog object
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_update_note)
        dialog.setCancelable(true)
        //Dialog window is setting
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        //Dialog item a add ıd location
        val etNoteTitle: EditText = dialog.findViewById(R.id.etNoteTitle)
        val etNoteDescriptions: EditText = dialog.findViewById(R.id.etNoteDescriptions)

        etNoteTitle.setText(notes.noteTitle.toString())
        etNoteDescriptions.setText(notes.noteDescription.toString())

        dialog.findViewById<Button>(R.id.btnUPDATE).setOnClickListener {
            if (inputCheck(etNoteTitle.text.toString(), etNoteDescriptions.text.toString())) {
                val notes =Notes(notes.id,etNoteTitle.text.toString(),etNoteDescriptions.text.toString())
                viewModel.updateData(notes)
                Toast.makeText(this," update added ",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Please enter data",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window!!.attributes = layoutParams
        }




    private fun showAddNoteDialog() {
        //create Dialog object
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_add_new_note)
        dialog.setCancelable(true)
        //Dialog window is setting
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        //Dialog item a add ıd location
        val etNoteTitle: EditText = dialog.findViewById(R.id.etNoteTitle)
        val etNoteDescriptions: EditText = dialog.findViewById(R.id.etNoteDescriptions)
        val btnCancel: Button = dialog.findViewById(R.id.btnCancel)
        val btnAdd: Button = dialog.findViewById(R.id.btnAdd)
        btnCancel.setOnClickListener {
            dialog.dismiss()
        }
        btnAdd.setOnClickListener {
            if (inputCheck(etNoteTitle.text.toString(), etNoteDescriptions.text.toString())) {
                val notes =Notes(0,etNoteTitle.text.toString(),etNoteDescriptions.text.toString())
                viewModel.addData(notes)
                Toast.makeText(this," data added ",Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }else{
                Toast.makeText(this,"Please enter data",Toast.LENGTH_SHORT).show()
            }
        }
        dialog.show()
        dialog.window!!.attributes = layoutParams
    }
    //Dialog editText line is empty or full check
    private fun inputCheck(noteTitle: String, noteDescription: String): Boolean {
        return !(TextUtils.isEmpty(noteTitle) && TextUtils.isEmpty(noteDescription))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.delete_all,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.deleteAllNotes){
            viewModel.deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }
}