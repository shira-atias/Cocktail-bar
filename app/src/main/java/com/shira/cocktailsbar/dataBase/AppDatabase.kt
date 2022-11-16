package com.shira.cocktailsbar.dataBase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shira.cocktailsbar.model.Drink


@Database(entities = [Drink::class], version = 2, exportSchema = false)
abstract class AppDatabase :RoomDatabase(){

    abstract fun drinkDao(): DrinkDao

    companion object{
        private var instant:AppDatabase? = null

        fun getDatabase(context: Context):AppDatabase?{
            if (instant == null)
                synchronized(AppDatabase::class.java){
                    instant = Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java,"cocktail_database").allowMainThreadQueries().build()
                }
            return instant
        }
    }

}