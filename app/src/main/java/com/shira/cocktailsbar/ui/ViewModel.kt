package com.shira.cocktailsbar.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.FirebaseDatabase
import com.shira.cocktailsbar.api.ApiManager
import com.shira.cocktailsbar.dataBase.DrinkRepository
import com.shira.cocktailsbar.model.Drink
import kotlinx.coroutines.Dispatchers

class ViewModel(application: Application) : AndroidViewModel(application){

    private var randomCocktail: MutableLiveData<MutableList<Drink>> = MutableLiveData()
    private var cocktailIdLiveData: MutableLiveData<MutableList<Drink>> = MutableLiveData()
    private var cocktailForFirebaseLiveData: MutableLiveData<MutableList<Drink>> = MutableLiveData()

    private val apiManager = ApiManager()
    private var repository = DrinkRepository(application,Dispatchers.Main)


    fun getRandomCocktail():MutableLiveData<MutableList<Drink>>{
        apiManager.getRandomCocktail(randomCocktail)
        return randomCocktail
    }

    fun getDetailCocktail(id:String):MutableLiveData<MutableList<Drink>>{
        apiManager.getCocktailID(id,cocktailIdLiveData)
        return cocktailIdLiveData
    }

    fun getCocktailFromFirebase():MutableLiveData<MutableList<Drink>>{
        val database = FirebaseDatabase.getInstance().getReference("drinks")
        database.get().addOnSuccessListener {
            val cocktailList = mutableListOf<Drink>()
            for(drink in it.children){
                val cocktail = drink.getValue(Drink::class.java)
                if (cocktail != null)
                     cocktailList.add(cocktail)

            }
            cocktailForFirebaseLiveData.postValue(cocktailList)
        }
        return cocktailForFirebaseLiveData
    }

    //database
    fun getAllFavorite() = repository.getAllListFavorite()


}