package com.yusufcancakmak.noteappkotlin

import android.content.Context
import androidx.room.Room
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Notes::class], version = 1, exportSchema = false)
abstract class Database: RoomDatabase() {

    abstract fun dao():Dao

    companion object{
        @Volatile
        private var INSTANCE: com.yusufcancakmak.noteappkotlin.Database? =null

        fun getDatabase(context: Context): com.yusufcancakmak.noteappkotlin.Database {
            val instance = INSTANCE
            if (instance!=null){
                return instance
            }
            synchronized(this){
                val instance=Room.databaseBuilder(
                    context.applicationContext,com.yusufcancakmak.noteappkotlin.Database::class.java,"notes").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}