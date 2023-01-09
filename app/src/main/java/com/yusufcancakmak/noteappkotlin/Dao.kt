package com.yusufcancakmak.noteappkotlin

import androidx.lifecycle.LiveData
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update


@Dao
interface Dao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
  suspend fun addData(notes:Notes)

  @Query("SELECT *FROM notes ORDER BY id ASC")
   fun readAllNotes():LiveData<List<Notes>>

   @Update
   suspend fun updateData(notes: Notes)

   @Delete
   suspend fun DeleteData(notes: Notes)

   @Query("DELETE FROM notes ")
   suspend fun deleteAllNotes()


}