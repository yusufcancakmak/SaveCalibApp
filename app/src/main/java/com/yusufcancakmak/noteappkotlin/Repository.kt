package com.yusufcancakmak.noteappkotlin

import androidx.lifecycle.LiveData

class Repository(private val dao: Dao) {
     suspend fun addData(notes:Notes){
        dao.addData(notes)
    }
    val readAllNotes:LiveData<List<Notes>> =dao.readAllNotes()

    suspend fun updateData(notes: Notes){
        dao.updateData(notes)
    }
    suspend fun deleteData(notes: Notes){
        dao.DeleteData(notes)
    }
    suspend fun deleteAllDATA(){
        dao.deleteAllNotes()
    }
}