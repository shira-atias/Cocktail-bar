package com.shira.cocktailsbar.dataBase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shira.cocktailsbar.model.Drink

@Dao
interface DrinkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: Drink)

    @Query("SELECT * FROM cocktails ")
    fun getAllCocktail(): LiveData<List<Drink>>


    @Query("SELECT * FROM cocktails WHERE idDrink IN (:id)")
    fun getOneCocktail(id:String) : Drink

    @Delete
    fun deleteCocktail(cocktail: Drink)
}