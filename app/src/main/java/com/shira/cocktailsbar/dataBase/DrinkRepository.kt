package com.shira.cocktailsbar.dataBase

import android.app.Application
import com.shira.cocktailsbar.model.Drink
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class DrinkRepository(application: Application, override val coroutineContext: CoroutineContext):CoroutineScope {

    private lateinit var drinkDao:DrinkDao

    init {
        val db = AppDatabase.getDatabase(application)
        if (db != null) {
            drinkDao = db.drinkDao()
        }
    }

    fun addToDatabase(cocktail: Drink){
        launch(Dispatchers.IO) {
            drinkDao.insertCocktail(cocktail)
        }
    }
    fun getAllListFavorite() = drinkDao.getAllCocktail()

    fun deleteCocktail(cocktail: Drink){
        launch (Dispatchers.IO){
            drinkDao.deleteCocktail(cocktail)
        }
        
    }

    fun getOneCocktail(id:String) = drinkDao.getOneCocktail(id)

}